package algorithm.线段树;



public class SegmentTree {

    void init(int n) {
        int len = 4 * n;
        lazy = new long[len];
        sum = new long[len];
        max = new long[len];
        min = new long[len];
        r = n - 1;
        N = n;
        build(1, 0, n - 1);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }

    void build(int u, int le, int ri) {
        if (le == ri) {
            return;
        }
        int mid = le + ri >> 1;
        build(u << 1, le, mid);
        build(u << 1 | 1, mid + 1, ri);
        pushUp(u);
    }

    void init(int n, int[] a) {
        int len = 4 * n;
        lazy = new long[len];
        sum = new long[len];
        max = new long[len];
        min = new long[len];
        r = n - 1;
        N = n;
        build(1, 0, n - 1, a);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }

    void build(int u, int le, int ri, int[] a) {
        if (le == ri) {
            sum[u] = max[u] = min[u] = a[le];
            return;
        }
        int mid = le + ri >> 1;
        build(u << 1, le, mid, a);
        build(u << 1 | 1, mid + 1, ri, a);
        pushUp(u);
    }

    //子节点信息更新父节点信息
    void pushUp(int u) {
        max[u] = Math.max(max[u << 1], max[u << 1 | 1]);
        min[u] = Math.min(min[u << 1], min[u << 1 | 1]);
        sum[u] = sum[u << 1] + sum[u << 1 | 1];
    }

    //下传懒标记
    void pushDown(int u, int l, int r) {
        if (lazy[u] == 0) return;
        int mid = l + r >> 1;
        pd(u << 1, lazy[u], l, mid);
        pd(u << 1 | 1, lazy[u], mid + 1, r);
        lazy[u] = 0;
    }

    void pd(int u, long lz, int l, int r) {
        sum[u] += lz * (r - l + 1);
        lazy[u] += lz;
        max[u] += lz;
        min[u] += lz;
    }

    //区间修改 区间 + v
    void updateRange(int le, int ri, int v) {
        ur(1, le, ri, v, 0, r);
    }

    void ur(int u, int le, int ri, int v, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            pd(u, v, l, r);
            return;
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= le) ur(u << 1, le, ri, v, l, mid);
        if (mid < ri) ur(u << 1 | 1, le, ri, v, mid + 1, r);
        pushUp(u);
    }

    //单点修改,将下标 idx 处的值加上 v
    void update(int idx, int v) {
        u(1, idx, v, 0, r);
    }

    void u(int u, int idx, int v, int l, int r) {
        if (l == idx && r == idx) {
            pd(u, v, l, r);
            return;
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= idx) u(u << 1, idx, v, l, mid);
        else u(u << 1 | 1, idx, v, mid + 1, r);
        pushUp(u);
    }

    //单点修改,将下标 idx 处的值变成 v
    void update1(int idx, int v) {
        u1(1, idx, v, 0, r);
    }

    void u1(int u, int idx, int v, int l, int r) {
        if (l == idx && r == idx) {
            sum[u] = max[u] = min[u] = v;
            return;
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= idx) u1(u << 1, idx, v, l, mid);
        else u1(u << 1 | 1, idx, v, mid + 1, r);
        pushUp(u);
    }

    //单点查询
    long querySingle(int x) {
        return qs(1, x, 0, r);
    }


    long qs(int u, int x, int l, int r) {
        if (l == x && r == x) return sum[u];
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= x) return qs(u << 1, x, l, mid);
        else return qs(u << 1 | 1, x, mid + 1, r);
    }

    //区间和查询
    long queryRangeSum(int le, int ri) {
        return qrs(1, le, ri, 0, r);
    }

    long qrs(int u, int le, int ri, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            return sum[u];
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        long ret = 0;
        if (mid >= le) ret += qrs(u << 1, le, ri, l, mid);
        if (mid < ri) ret += qrs(u << 1 | 1, le, ri, mid + 1, r);
        return ret;
    }


    //区间最大值查询
    long queryRangeMax(int le, int ri) {
        return qrmax(1, le, ri, 0, r);
    }

    long qrmax(int u, int le, int ri, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            return max[u];
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        long ret = Long.MIN_VALUE;
        if (mid >= le) ret = Math.max(ret, qrmax(u << 1, le, ri, l, mid));
        if (mid < ri) ret = Math.max(ret, qrmax(u << 1 | 1, le, ri, mid + 1, r));
        return ret;
    }

    //区间最小值查询
    long queryRangeMin(int le, int ri) {
        return qrmin(1, le, ri, 0, r);
    }

    long qrmin(int u, int le, int ri, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            return min[u];
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        long ret = Long.MAX_VALUE;
        if (mid >= le) ret = Math.min(ret, qrmin(u << 1, le, ri, l, mid));
        if (mid < ri) ret = Math.min(ret, qrmin(u << 1 | 1, le, ri, mid + 1, r));
        return ret;
    }

    //查询当前情况
    void look() {
        for (int i = 0; i < N; i++) {
            System.out.print(querySingle(i) + " ");
        }
        System.out.println();
    }

    long[] lazy;
    long[] sum, max, min;
    int r;
    int N;

}