package algorithm.最长递增子序列优化;

import java.util.*;

public class LIS2 {
    //线段树优化 LIS，用于求 LIS 最大值，而不是最长的子序列长度.(也可用于求最长子序列长度)
    //线段树保存的是以某个值为最后一个值时，它的 LIS 的最大和
    static class Tree {
        int l, r;
        long max;
    }


    static void init(int n, int[] a) {
        int len = 4 * n;
        tr = new Tree[len];
        for (int i = 0; i < len; i++) {
            tr[i] = new Tree();
        }
        build(1, 0, n - 1, a);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }


    static void build(int u, int l, int r, int[] a) {
        tr[u].l = l;
        tr[u].r = r;
        if (l == r) return;
        int mid = l + r >> 1;
        build(u << 1, l, mid, a);
        build(u << 1 | 1, mid + 1, r, a);
        pushUp(u);
    }

    static void pushUp(int u) {
        tr[u].max = Math.max(tr[u << 1].max, tr[u << 1 | 1].max);
    }

    //单点修改,将下标 x 处的值加上 v
    static void update(int u, int x, long v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].max = Math.max(tr[u].max, v);
            return;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (mid >= x) update(u << 1, x, v);
        else update(u << 1 | 1, x, v);
        pushUp(u);
    }

    //区间最大值查询
    static long queryMax(int u, int l, int r) {
        if (l <= tr[u].l && tr[u].r <= r) {//完全覆盖
            return tr[u].max;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        long ret = (int) -1e9;
        if (mid >= l) ret = Math.max(ret, queryMax(u << 1, l, r));
        if (mid < r) ret = Math.max(ret, queryMax(u << 1 | 1, l, r));
        return ret;
    }

    static Tree[] tr;

    static long maxBalancedSubsequenceSum(int[] a) {
        int n = a.length;
        boolean f = false;
        int mmax = (int) -1e9;
        for (int i = 0; i < n; i++) {
            if (a[i] > 0) f = true;
            else mmax = Math.max(mmax, a[i]);
        }
        if (!f) {
            return mmax;
        }

        //离散化下标
        int[] b = a.clone();
        for (int i = 0; i < n; i++) {
            b[i] -= i;
        }
        int[] c = b.clone();
        Arrays.sort(b);
        Map<Integer, Integer> map = new HashMap<>();//key a[i] val 离散化后的下标
        for (int i = 0; i < n; i++) {
            map.put(b[i], i);
        }
        init(n, a);
        for (int i = 0; i < n; i++) {
            int idx = map.get(c[i]);//离散化后的下标
            long max = queryMax(1, 0, idx);//这里相邻元素可以相等，如果要严格递增
            //就应该是 long max = queryMax(1, 0, idx - 1);
            update(1, idx, max + a[i]);//这里如果是  update(1, idx, max + 1); 就是求最大长度
        }
        return queryMax(1, 0, n);
    }


}
