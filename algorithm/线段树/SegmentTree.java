package algorithm.线段树;

public class SegmentTree {


    static void init(int n) {
        int len = 4 * n;
        l = new int[len];
        r = new int[len];
        lazy = new int[len];
        sum = new long[len];
        max = new long[len];
        min = new long[len];
        build(1, 0, n - 1);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }

    static void build(int u, int le, int ri) {
        l[u] = le;
        r[u] = ri;
        if (le == ri) return;
        int mid = le + ri >> 1;
        build(u << 1, le, mid);
        build(u << 1 | 1, mid + 1, ri);
        pushUp(u);
    }

    static void init(int n, int[] a) {
        int len = 4 * n;
        l = new int[len];
        r = new int[len];
        lazy = new int[len];
        sum = new long[len];
        max = new long[len];
        min = new long[len];
        build(1, 0, n - 1, a);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }

    static void build(int u, int le, int ri, int[] a) {
        l[u] = le;
        r[u] = ri;
        if (le == ri) {
            sum[u] = max[u] = min[u] = a[le];
            return;
        }
        int mid = le + ri >> 1;
        build(u << 1, le, mid,a);
        build(u << 1 | 1, mid + 1, ri,a);
        pushUp(u);
    }

    //子节点信息更新父节点信息
    static void pushUp(int u) {
        max[u] = Math.max(max[u << 1], max[u << 1 | 1]);
        min[u] = Math.min(min[u << 1], min[u << 1 | 1]);
        sum[u] = sum[u << 1] + sum[u << 1 | 1];
    }

    //下传懒标记
    static void pushDown(int u) {
        if (lazy[u] == 0) return;
        pd(u << 1, lazy[u]);
        pd(u << 1 | 1, lazy[u]);
        lazy[u] = 0;
    }

    static void pd(int u, int lz) {
        sum[u] += lz * (r[u] - l[u] + 1);
        lazy[u] += lz;
        max[u] += lz;
        min[u] += lz;
    }

    //区间修改 区间 + v
    static void updateRange(int u, int le, int ri, int v) {
        if (le <= l[u] && r[u] <= ri) {//完全覆盖
            pd(u, v);
            return;
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        if (mid >= le) updateRange(u << 1, le, ri, v);
        if (mid < ri) updateRange(u << 1 | 1, le, ri, v);
        pushUp(u);
    }

    //单点修改,将下标 idx 处的值加上 v
    static void update(int u, int idx, int v) {
        if (l[u] == idx && r[u] == idx) {
            pd(u, v);
            return;
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        if (mid >= idx) update(u << 1, idx, v);
        else update(u << 1 | 1, idx, v);
        pushUp(u);
    }

    //单点查询
    static long querySingle(int u, int x) {
        if (l[u] == x && r[u] == x) return sum[u];
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        if (mid >= x) return querySingle(u << 1, x);
        else return querySingle(u << 1 | 1, x);
    }

    //区间和查询
    static long queryRangeSum(int u, int le, int ri) {
        if (le <= l[u] && r[u] <= ri) {//完全覆盖
            return sum[u];
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        long ret = 0;
        if (mid >= le) ret += queryRangeSum(u << 1, le, ri);
        if (mid < ri) ret += queryRangeSum(u << 1 | 1, le, ri);
        return ret;
    }

    //区间最大值查询
    static long queryRangeMax(int u, int le, int ri) {
        if (le <= l[u] && r[u] <= ri) {//完全覆盖
            return max[u];
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        long ret = Long.MIN_VALUE;
        if (mid >= le) ret = Math.max(ret, queryRangeMax(u << 1, le, ri));
        if (mid < ri) ret = Math.max(ret, queryRangeMax(u << 1 | 1, le, ri));
        return ret;
    }

    //区间最小值查询
    static long queryRangeMin(int u, int le, int ri) {
        if (le <= l[u] && r[u] <= ri) {//完全覆盖
            return min[u];
        }
        pushDown(u);
        int mid = l[u] + r[u] >> 1;
        long ret = Long.MAX_VALUE;
        if (mid >= le) ret = Math.min(ret, queryRangeMin(u << 1, le, ri));
        if (mid < ri) ret = Math.min(ret, queryRangeMin(u << 1 | 1, le, ri));
        return ret;
    }

    static int[] l, r, lazy;
    static long[] sum, max, min;
}
