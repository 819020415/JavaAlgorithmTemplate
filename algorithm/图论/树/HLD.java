package algorithm.图论.树;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Date: 2025/12/31 9:55
 * 重链剖分 + 线段树
 * 支持：子树修改，查询
 *      路径修改，查询
 */
public class HLD {

    //---------------Segment Tree--------------------
    static void initTree(int n, int[] a) {
        int len = 4 * n;
        lazy = new int[len];
        sum = new long[len];
        max = new long[len];
        min = new long[len];
        build(1, 0, n - 1, a);//默认根节点编号为 1，区间左端点为 0 ，右端点为 n - 1
    }

    static void build(int u, int le, int ri, int[] a) {
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
    static void pushUp(int u) {
        max[u] = Math.max(max[u << 1], max[u << 1 | 1]);
        min[u] = Math.min(min[u << 1], min[u << 1 | 1]);
        sum[u] = sum[u << 1] + sum[u << 1 | 1];
    }

    //下传懒标记
    static void pushDown(int u, int l, int r) {
        if (lazy[u] == 0) return;
        int mid = l + r >> 1;
        pd(u << 1, lazy[u], l, mid);
        pd(u << 1 | 1, lazy[u], mid + 1, r);
        lazy[u] = 0;
    }

    static void pd(int u, int lz, int l, int r) {
        sum[u] += lz * (r - l + 1);
        lazy[u] += lz;
        max[u] += lz;
        min[u] += lz;
    }

    //区间修改 区间 + v
    static void updateRange(int u, int le, int ri, int v, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            pd(u, v, l, r);
            return;
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= le) updateRange(u << 1, le, ri, v, l, mid);
        if (mid < ri) updateRange(u << 1 | 1, le, ri, v, mid + 1, r);
        pushUp(u);
    }


    //单点修改,将下标 idx 处的值变成 v
    static void update1(int u, int idx, int v, int l, int r) {
        if (l == idx && r == idx) {
            sum[u] = max[u] = min[u] = v;
            return;
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        if (mid >= idx) update1(u << 1, idx, v, l, mid);
        else update1(u << 1 | 1, idx, v, mid + 1, r);
        pushUp(u);
    }


    //区间和查询
    static long queryRangeSum(int u, int le, int ri, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            return sum[u];
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        long ret = 0;
        if (mid >= le) ret += queryRangeSum(u << 1, le, ri, l, mid);
        if (mid < ri) ret += queryRangeSum(u << 1 | 1, le, ri, mid + 1, r);
        return ret;
    }

    //查询树上两点路径上的点权和
    static long queryPath(int u, int v) {
        long sum = 0;
        while (top[u] != top[v]) {//两节点不在同一条重链上
            if (dep[top[u]] < dep[top[v]]) {//保证 u 的链头的深度更大
                int t = u;
                u = v;
                v = t;
            }
            sum += queryRangeSum(1, id[top[u]], id[u], 0, n - 1);
            u = fa[top[u]];//将 u 跳到它的链头的父节点
        }
        if (dep[u] < dep[v]) {//保证 u 的链头的深度更大
            int t = u;
            u = v;
            v = t;
        }
        sum += queryRangeSum(1, id[v], id[u], 0, n - 1);
        return sum;
    }

    //u 到 v 路径上的所有点的点权 + k
    static void updatePath(int u, int v, int k) {
        while (top[u] != top[v]) {//两节点不在同一条重链上
            if (dep[top[u]] < dep[top[v]]) {//保证 u 的链头的深度更大
                int t = u;
                u = v;
                v = t;
            }
            updateRange(1, id[top[u]], id[u], k, 0, n - 1);
            u = fa[top[u]];//将 u 跳到它的链头的父节点
        }
        if (dep[u] < dep[v]) {//保证 u 的链头的深度更大
            int t = u;
            u = v;
            v = t;
        }
        updateRange(1, id[v], id[u], k, 0, n - 1);
    }

    //区间最大值查询
    static long queryRangeMax(int u, int le, int ri, int l, int r) {
        if (le <= l && r <= ri) {//完全覆盖
            return max[u];
        }
        pushDown(u, l, r);
        int mid = l + r >> 1;
        long ret = Long.MIN_VALUE;
        if (mid >= le) ret = Math.max(ret, queryRangeMax(u << 1, le, ri, l, mid));
        if (mid < ri) ret = Math.max(ret, queryRangeMax(u << 1 | 1, le, ri, mid + 1, r));
        return ret;
    }

    //查询 u -> v 路径最大点权
    static long queryPathMax(int u, int v) {
        long max = -1L << 60;
        while (top[u] != top[v]) {//两节点不在同一条重链上
            if (dep[top[u]] < dep[top[v]]) {//保证 u 的链头的深度更大
                int t = u;
                u = v;
                v = t;
            }
            max = Math.max(max, queryRangeMax(1, id[top[u]], id[u], 0, n - 1));
            u = fa[top[u]];//将 u 跳到它的链头的父节点
        }
        if (dep[u] < dep[v]) {//保证 u 的链头的深度更大
            int t = u;
            u = v;
            v = t;
        }
        max = Math.max(max, queryRangeMax(1, id[v], id[u], 0, n - 1));
        return max;
    }

    //子树 u 的所有节点 += k
    static void updateSubTree(int u, int k) {
        updateRange(1, id[u], id[u] + sz[u] - 1, k, 0, n - 1);
    }


    static int[] lazy;
    static long[] sum, max, min;
    //---------------Segment Tree--------------------
    //------------------HLD--------------------------
    static int n;//树的节点数，编号为 1 ~ n
    static int[] fa;
    static int[] dep;
    static int[] son;
    static int[] sz;
    static int[] top;
    static int[] id;//重链剖分后的节点新编号，新节点编号是 [0,n-1]
    static int[] nw;//新权值
    static int[] w;//节点权值
    static int cnt;
    static List<Integer>[] ed;//保存 u 的邻点


    static void dfs1(int u, int father) {
        fa[u] = father;
        dep[u] = dep[father] + 1;
        sz[u] = 1;
        for (int v : ed[u]) {
            if (v == father) continue;
            dfs1(v, u);
            sz[u] += sz[v];
            if (sz[son[u]] < sz[v]) son[u] = v;
        }
    }

    static void dfs2(int u, int t) {//t 为链头
        top[u] = t;
        id[u] = ++cnt;
        nw[cnt] = w[u];
        if (son[u] == 0) return;//无重儿子 返回
        dfs2(son[u], t);//搜重儿子
        for (int v : ed[u]) {//枚举当前节点的轻儿子
            if (v == fa[u] || v == son[u]) continue;
            dfs2(v, v);//搜轻儿子
        }
    }
    //------------------HLD--------------------------

    static void init() {
        //建树
        n = sc.nextInt();
        fa = new int[n + 1];//存 u 的父节点
        dep = new int[n + 1];//存 u 的深度
        son = new int[n + 1];//存 u 的重儿子
        sz = new int[n + 1];//存以 u 为根的子树的节点数
        top = new int[n + 1];//存 u 所在重链的顶点
        id = new int[n + 1];
        nw = new int[n + 1];
        w = new int[n + 1];
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new LinkedList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);
            ed[v].add(u);
        }
        for (int i = 0; i < n; i++) {
            w[i + 1] = sc.nextInt();//节点权值
        }

        cnt = -1;
        int root = 1;//注意题目是否给定根节点
        dfs1(root, 0);
        dfs2(root, root);

        initTree(n, nw);

    }


    static Scanner sc = new Scanner(System.in);
}


