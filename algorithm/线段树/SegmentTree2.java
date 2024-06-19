package algorithm.线段树;

public class SegmentTree2 {
    //线段树 模板2 支持区间加、乘，区间查询
    static int[] l, r;
    static long[] sum, add, mul;

    static void init() {
        int len = 4 * N;
        l = new int[len];
        r = new int[len];
        sum = new long[len];
        add = new long[len];
        mul = new long[len];
        build(1, 0, N - 1);

    }

    static void build(int u, int le, int ri) {
        l[u] = le;
        r[u] = ri;
        mul[u] = 1;
        if (le == ri) return;
        int mid = le + ri >> 1;
        build(u << 1, le, mid);
        build(u << 1 | 1, mid + 1, ri);
        pushUp(u);
    }

    static void init(int[] a) {
        int len = 4 * N;
        l = new int[len];
        r = new int[len];
        sum = new long[len];
        add = new long[len];
        mul = new long[len];
        build(1, 0, N - 1);

    }

    static void build(int u, int le, int ri, int[] a) {
        l[u] = le;
        r[u] = ri;
        mul[u] = 1;
        if (le == ri) {
            a[u] = le;
            return;
        }
        int mid = le + ri >> 1;
        build(u << 1, le, mid);
        build(u << 1 | 1, mid + 1, ri);
        pushUp(u);
    }

    static void calc(int u, long a, long m) {
        sum[u] = (sum[u] * m + (r[u] - l[u] + 1) * a) % mod;
        mul[u] = mul[u] * m % mod;
        add[u] = (add[u] * m + a) % mod;
    }

    //区间加乘，区间加的时候 mul=1；区间乘的时候 add=0
    static void update(int u, int le, int ri, int add, int mul) {
        if (le <= l[u] && r[u] <= ri) {
            calc(u, add, mul);
            return;
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        if (le <= mid) update(u << 1, le, ri, add, mul);
        if (ri > mid) update(u << 1 | 1, le, ri, add, mul);
        pushUp(u);
    }

    static void pushDown(int u) {
        calc(u << 1, add[u], mul[u]);
        calc(u << 1 | 1, add[u], mul[u]);
        add[u] = 0;
        mul[u] = 1;
    }

    static void pushUp(int u) {
        sum[u] = (sum[u << 1] + sum[u << 1 | 1]) % mod;
    }

    //区间和查询，也可单点查询
    static long query(int u, int le, int ri) {
        if (le <= l[u] && r[u] <= ri) return sum[u];
        pushDown(u);
        long ret = 0;
        int mid = l[u] + r[u] >> 1;
        if (le <= mid) ret += query(u << 1, le, ri);
        if (ri > mid) ret += query(u << 1 | 1, le, ri);
        return ret;
    }

    static int mod = (int) (1e9 + 7);
    static int N = 100001;
}
