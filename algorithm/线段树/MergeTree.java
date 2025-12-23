package algorithm.线段树;

import java.io.*;
import java.util.InputMismatchException;

public class MergeTree {
    /**
     * 类似线段树，不过每个节点保存的不是单个值，而是一个排序后的区间
     * 建树的过程和归并排序类似，因此叫做归并树
     *
     * 可以解决查询 [l,r] 第 k 小的值 的问题，单次查询的时间复杂度为 O(logN ^ 3)
     * 每次查询二分第 k 大的值 x。然后进入查询，对于每个区间，如果完全覆盖了，二分获得当前区间小于等于 x 的个数
     * 如果不相交就直接返回 0，否则继续递归子节点
     *
     */
    static class MT {
        int[] a;
        long[] ps;
        int l, r;
      //  int c1,c2;//逆序对，非逆序对
    //    int lazy;//区间翻转懒标记
    }

    static int[] build(int u, int l, int r, int[] a) {
        int len = r - l + 1;
        tr[u].a = new int[len];
        tr[u].ps = new long[len + 1];
        tr[u].l = l;
        tr[u].r = r;
        if (l == r) {
            tr[u].a[0] = a[l];
            tr[u].ps[1] = a[l];
            return tr[u].a;
        }
        int mid = l + r >> 1;
        int[] le = build(u << 1, l, mid, a);
        int[] ri = build(u << 1 | 1, mid + 1, r, a);
        int p1 = 0, p2 = 0, p3 = 0;
        while (p1 < le.length && p2 < ri.length) {
            if (le[p1] < ri[p2]) tr[u].a[p3++] = le[p1++];
            else tr[u].a[p3++] = ri[p2++];
        }
        while (p1 < le.length) tr[u].a[p3++] = le[p1++];
        while (p2 < ri.length) tr[u].a[p3++] = ri[p2++];
        for (int i = 1; i <= len; i++) {
            tr[u].ps[i] = tr[u].ps[i - 1] + tr[u].a[i - 1];
        }
        return tr[u].a;

    }

    //求 a[l ~ r] 区间内小于等于 x 的值的个数
    static int query(int u, int l, int r, int x) {
        if (tr[u].l > r || tr[u].r < l) return 0;
        if (l <= tr[u].l && tr[u].r <= r) {//完全覆盖，二分获得当前区间满足条件的个数
            int le = 0, ri = tr[u].a.length - 1, ret = -1;
            while (le <= ri) {
                int m = le + ri >> 1;
                if (tr[u].a[m] <= x) {
                    ret = m;
                    le = m + 1;
                } else ri = m - 1;
            }
            return ret + 1;
        }
        int sum = 0;
        sum += query(u << 1, l, r, x);
        sum += query(u << 1 | 1, l, r, x);
        return sum;
    }

    //求 a[l ~ r] 区间内小于等于 x 的值的和
    static long querySum(int u, int l, int r, long x) {
        if (tr[u].l > r || tr[u].r < l) return 0;
        if (l <= tr[u].l && tr[u].r <= r) {//完全覆盖，二分获得当前区间满足条件的个数
            int le = 0, ri = tr[u].a.length - 1, ret = -1;
            while (le <= ri) {
                int m = le + ri >> 1;
                if (tr[u].a[m] <= x) {
                    ret = m;
                    le = m + 1;
                } else ri = m - 1;
            }
            return tr[u].ps[ret + 1];
        }
        long sum = 0;
        sum += querySum(u << 1, l, r, x);
        sum += querySum(u << 1 | 1, l, r, x);
        return sum;
    }

    static MT[] tr;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        int n = sc.nextInt(), m = sc.nextInt();
        int[] a = new int[n];
        tr = new MT[4 * n];
        for (int i = 0; i < 4 * n; i++) tr[i] = new MT();
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        build(1, 0, n, a);
        for (int i = 0; i < m; i++) {
            int le = sc.nextInt() - 1, ri = sc.nextInt() - 1, k = sc.nextInt();//求区间 [le,ri] 第 k 小的值
            int l = 0, r = n, ans = 0;//二分答案
            while (l <= r) {
                int mid = l + r >> 1;
                if (query(1, le, ri, mid) >= k) {
                    ans = mid;
                    r = mid - 1;
                } else l = mid + 1;
            }
            out.println(ans);
        }
        out.close();
    }


    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public OutputWriter println(long i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println(double i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println(String i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println() {
            writer.println();
            return this;
        }

        public OutputWriter print(String i) {
            writer.print(i);
            return this;
        }

        public OutputWriter print(long i) {
            writer.print(i);
            return this;
        }

        public OutputWriter print(char i) {
            writer.print(i);
            return this;
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public double[] nextDoubleArray(int size) {
            double[] array = new double[size];
            for (int i = 0; i < size; i++) {
                array[i] = readDouble();
            }
            return array;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            return Integer.parseInt(this.next(), 10);
        }

        public long nextLong() {
            return Long.parseLong(this.next(), 10);
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        private static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public double readDouble() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new InputMismatchException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}
