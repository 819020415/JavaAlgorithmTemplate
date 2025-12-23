package algorithm.图论.树;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Date: 2025/7/11 11:07
 */
public class 树上启发式合并 {

    static int n;
    static int[] fa;//存 u 的父节点
    static int[] dep;//存 u 的深度
    static int[] son;//存 u 的重儿子
    static int[] sz;//存以 u 为根的子树的节点数
    static int[] cnt;//频数数组


    static void add(int u, int p, int s) {
        //累加贡献...

        for (int v : ed[u]) {
            if (v == p || v == s) continue;
            add(v, u, s);//注意这里的重儿子还是穿的 s 而不是 son[v] ，保证了只有第一次不会走重儿子，递归进轻儿子后就会走重儿子了
        }
    }

    static void sub(int u, int p) {
        //减去贡献

        for (int v : ed[u]) {
            if (v == p) continue;
            sub(v, u);
        }
    }

    static void dfs2(int u, int p, int op) {
        for (int v : ed[u]) {
            if (v == p || v == son[u]) continue;
            dfs2(v, u, 0);//先搜轻儿子
        }
        if (son[u] != 0) dfs2(son[u], u, 1);//如果有重儿子，搜重儿子
        add(u, p, son[u]);//累加 u 和轻子树的贡献
        //计算答案-----------------------------------------------


        //-----------------------------------------------------
        if (op == 0) sub(u, p);//减去轻子树的贡献
    }


    //处理处 fa,sz,son，dep 数组
    static void dfs1(int u, int p) {
        fa[u] = p;
        dep[u] = dep[p] + 1;
        sz[u] = 1;
        for (int v : ed[u]) {
            if (v == p) continue;
            dfs1(v, u);
            sz[u] += sz[v];
            if (sz[son[u]] < sz[v]) son[u] = v;
        }
    }

    //建图
    static void buildTree() {
        n = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);
            ed[v].add(u);
        }
        a = new int[n + 1];
        for (int i = 0; i < n; i++) a[i + 1] = sc.nextInt();
    }

    static int[] a;
    static List<Integer>[] ed;

    static void init() {
        buildTree();
        fa = new int[n + 1];//存 u 的父节点
        dep = new int[n + 1];//存 u 的深度
        son = new int[n + 1];//存 u 的重儿子
        sz = new int[n + 1];//存以 u 为根的子树的节点数
        cnt = new int[n + 1];

        dfs1(1, 0);
        dfs2(1, 0, 0);

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


