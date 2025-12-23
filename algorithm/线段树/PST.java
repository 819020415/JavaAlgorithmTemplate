package algorithm.线段树;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Date: 2025/7/8 9:13
 * 基于值域的 可持久化线段树，用于查询区间 [l,r] 第 k 小值
 * 数组下标从 1 开始
 */
public class PST {

    static int[] l, r, sum;//节点的左右值域以及区间内数字出现的总次数

    static int[] root;  // root[i]：第 i 个前缀对应的版本根节点下标
    static int[] a;  // 原数组
    static int[] b;   // 离散化后数组
    static int idx = 0;                 // 当前已用节点数

    static int build(int ll, int rr) {
        int cur = ++idx;
        if (ll == rr) {
           //tr[cur].v = a[l];
            return cur;
        }
        int m = (ll + rr) >>> 1;
        l[cur] = build(ll, m);
        r[cur] = build(m + 1, rr);
        return cur;
    }

    // 静态初始化，给每个 tr[i] 分配空间

    /**
     * 在版本 x 的基础上插入 pos，返回新版本的根节点下标
     * @param x    原版本根节点
     * @param ll    当前节点代表的区间左端点
     * @param rr    当前节点代表的区间右端点
     * @param pos  要插入的离散化后值的位置
     * @return 新版本根节点下标
     */
    static int insert(int x, int ll, int rr, int pos) {
        int y = ++idx;
        // 复制节点 x 到节点 y
        l[y] = l[x];
        r[y] = r[x];
        sum[y] = sum[x] + 1;

        if (ll == rr) {
            // 叶节点，直接返回
            return y;
        }
        int m = (ll + rr) >>> 1;
        if (pos <= m) {
            // 插入左子树
            l[y] = insert(l[x], ll, m, pos);
        } else {
            // 插入右子树
            r[y] = insert(r[x], m + 1, rr, pos);
        }
        return y;
    }

    /**
     * 在两个版本 x (l-1 前缀) 和 y (r 前缀) 上查询第 k 小
     * @param x  版本 l-1 的根节点
     * @param y  版本 r 的根节点
     * @param ll  当前区间左端点
     * @param rr  当前区间右端点
     * @param k  第 k 小
     * @return 离散化后的位置
     */
    static int query(int x, int y, int ll, int rr, int k) {
        if (ll == rr) {
            return ll;
        }
        int cntLeft = sum[l[y]] - sum[l[x]];
        int m = (ll + rr) >>> 1;
        if (k <= cntLeft) {
            return query(l[x], l[y], ll, m, k);
        } else {
            return query(r[x], r[y], m + 1, rr, k - cntLeft);
        }
    }

    //求 log2(X)
    static int log2(long x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    static void init(int n) {
        int log = log2(n);
        a = new int[n + 5];
        b = new int[n + 5];
        root = new int[n + 5];
        l = new int[n * (log + 3)];
        r = new int[n * (log + 3)];
        sum = new int[n * (log + 3)];

    }


    public static void main(String[] args) throws IOException {

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
            b[i] = a[i];
        }


        // 离散化
        Arrays.sort(b, 1, n + 1);
        int bn = 1;
        for (int i = 2; i <= n; i++) {
            if (b[i] != b[bn]) {
                b[++bn] = b[i];
            }
        }

        // 构建每个前缀的版本
        // root[0] 默认为 0（空树）
        for (int i = 1; i <= n; i++) {
            int pos = Arrays.binarySearch(b, 1, bn + 1, a[i]);
            if (pos < 0) pos = -pos - 1;
            root[i] = insert(root[i - 1], 1, bn, pos);
        }

        // 处理查询
        while (m-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            int k = sc.nextInt();
            int idxB = query(root[l - 1], root[r], 1, bn, k);
            out.println(b[idxB]);
        }

        out.flush();
        out.close();

    }

    static Kattio sc = new Kattio();
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    static class Kattio {
        static BufferedReader r;
        static StringTokenizer st;

        public Kattio() {
            r = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreTokens()) {
                    st = new StringTokenizer(r.readLine());
                }
                return st.nextToken();
            } catch (Exception e) {
                return null;
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}

