package algorithm.线段树;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Date: 2025/7/8 9:13
 * 基于下标的 可持久化线段树，用于查询历史版本的某个值，修改历史版本的某个值
 * 数组下标从 1 开始
 */

public class PST2 {
    static final int N = 1_000_005;
    // 最多节点数 ≈ N * logN * 更新次数，这里取 25 倍上界
    static final int MAXNODE = N * 23;


    static int[] L = new int[MAXNODE];
    static int[] R = new int[MAXNODE];
    static int[] V = new int[MAXNODE];

    static int[] root = new int[N];
    static int[] a = new int[N];
    static int idx = 0;  // 全局节点计数


    /**
     * 在 a[l..r] 区间上根据初始数组建树，返回当前版本根节点索引
     */
    static int build(int l, int r) {
        int cur = ++idx;
        if (l == r) {
            V[cur] = a[l];
            return cur;
        }
        int m = (l + r) >>> 1;
        L[cur] = build(l, m);
        R[cur] = build(m + 1, r);
        return cur;
    }

    /**
     * 在 prev 版本的基础上，将位置 pos 改为 v，返回新版本根节点索引
     */
    static int modify(int prev, int l, int r, int pos, int v) {
        int cur = ++idx;
        // 先把 prev 节点内容完整复制到 cur
        L[cur] = L[prev];
        R[cur] = R[prev];
        // v[cur] = tr[prev].v;
        if (l == r) {
            // 到叶子直接更新
            V[cur] = v;
            return cur;
        }
        int m = (l + r) >>> 1;
        if (pos <= m) {
            L[cur] = modify(L[prev], l, m, pos, v);
        } else {
            R[cur] = modify(R[prev], m + 1, r, pos, v);
        }
        return cur;
    }

    /**
     * 在版本 x 上查询位置 pos 的值
     */
    static int query(int x, int l, int r, int pos) {
        if (l == r) {
            return V[x];
        }
        int m = (l + r) >>> 1;
        if (pos <= m) {
            return query(L[x], l, m, pos);
        } else {
            return query(R[x], m + 1, r, pos);
        }
    }

    public static void main(String[] args) throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();

        // 读入初始数组
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        // 建第 0 号版本
        root[0] = build(1, n);

        for (int i = 1; i <= m; i++) {
            int ver = sc.nextInt(), op = sc.nextInt();
            if (op == 1) {
                // 修改操作：读 x, y，将版本 ver 的 a[x] 更新为 y
                int x = sc.nextInt();
                int y = sc.nextInt();
                root[i] = modify(root[ver], 1, n, x, y);
            } else {
                // 查询操作：读 x，输出版本 ver 的 a[x]
                int x = sc.nextInt();
                int ans = query(root[ver], 1, n, x);
                out.println(ans);
                // 查询之后，新版本等同于 ver
                root[i] = root[ver];
            }
        }

        out.flush();
        out.close();
    }

    static FastReader sc = new FastReader(System.in);
    static FastWriter out = new FastWriter(System.out);

    // 快读
    public static class FastReader {
        InputStream is;
        private byte[] inbuf = new byte[1024];
        public int lenbuf = 0;
        public int ptrbuf = 0;

        public FastReader(final InputStream is) {
            this.is = is;
        }

        public int nextByte() {
            if (lenbuf == -1) {
                throw new InputMismatchException();
            }
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0) {
                    return -1;
                }
            }
            return inbuf[ptrbuf++];
        }

        public int nextInt() {
            return (int) nextLong();
        }

        public long nextLong() {
            long num = 0;
            int b;
            boolean minus = false;
            while ((b = nextByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = nextByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = nextByte();
            }
        }
    }

    // 快写
    public static class FastWriter {
        private static final int BUF_SIZE = 1 << 13;
        private final byte[] buf = new byte[BUF_SIZE];
        private OutputStream out;
        private Writer writer;
        private int ptr = 0;

        public FastWriter(Writer writer) {
            this.writer = new BufferedWriter(writer);
            out = new ByteArrayOutputStream();
        }

        public FastWriter(OutputStream os) {
            this.out = os;
        }

        public FastWriter(String path) {
            try {
                this.out = new FileOutputStream(path);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FastWriter");
            }
        }

        public FastWriter write(byte b) {
            buf[ptr++] = b;
            if (ptr == BUF_SIZE) {
                innerflush();
            }
            return this;
        }

        public FastWriter write(String s) {
            s.chars().forEach(c -> {
                buf[ptr++] = (byte) c;
                if (ptr == BUF_SIZE) {
                    innerflush();
                }
            });
            return this;
        }

        private static int countDigits(long l) {
            if (l >= 1000000000000000000L) {
                return 19;
            }
            if (l >= 100000000000000000L) {
                return 18;
            }
            if (l >= 10000000000000000L) {
                return 17;
            }
            if (l >= 1000000000000000L) {
                return 16;
            }
            if (l >= 100000000000000L) {
                return 15;
            }
            if (l >= 10000000000000L) {
                return 14;
            }
            if (l >= 1000000000000L) {
                return 13;
            }
            if (l >= 100000000000L) {
                return 12;
            }
            if (l >= 10000000000L) {
                return 11;
            }
            if (l >= 1000000000L) {
                return 10;
            }
            if (l >= 100000000L) {
                return 9;
            }
            if (l >= 10000000L) {
                return 8;
            }
            if (l >= 1000000L) {
                return 7;
            }
            if (l >= 100000L) {
                return 6;
            }
            if (l >= 10000L) {
                return 5;
            }
            if (l >= 1000L) {
                return 4;
            }
            if (l >= 100L) {
                return 3;
            }
            if (l >= 10L) {
                return 2;
            }
            return 1;
        }

        public FastWriter write(long x) {
            if (x == Long.MIN_VALUE) {
                return write("" + x);
            }
            if (ptr + 21 >= BUF_SIZE) {
                innerflush();
            }
            if (x < 0) {
                write((byte) '-');
                x = -x;
            }
            int d = countDigits(x);
            for (int i = ptr + d - 1; i >= ptr; i--) {
                buf[i] = (byte) ('0' + x % 10);
                x /= 10;
            }
            ptr += d;
            return this;
        }

        public FastWriter writeln(long x) {
            return write(x).writeln();
        }

        public FastWriter writeln() {
            return write((byte) '\n');
        }

        private void innerflush() {
            try {
                out.write(buf, 0, ptr);
                ptr = 0;
            } catch (IOException e) {
                throw new RuntimeException("innerflush");
            }
        }

        public void flush() {
            innerflush();
            try {
                if (writer != null) {
                    writer.write(((ByteArrayOutputStream) out).toString());
                    out = new ByteArrayOutputStream();
                    writer.flush();
                } else {
                    out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException("flush");
            }
        }

        public FastWriter println(long x) {
            return writeln(x);
        }

        public void close() {
            flush();
            try {
                out.close();
            } catch (Exception e) {
            }
        }
    }
}
