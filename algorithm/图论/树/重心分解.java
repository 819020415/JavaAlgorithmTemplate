package algorithm.图论.树;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 基于重心的分治
 * 用于解决树上统计点对的题目
 */
public class 重心分解 {

    public static void centroidDecomposition() {
        buildTree();
        size = new int[n + 1];
        weight = new int[n + 1];
        centroid = new int[2];
        vis = new boolean[n + 1];
        getCentroid(1, 0);


    }


    static void dfs4(int u, int p) {
        size[u] = weight[u] = 0;
        N++;
        for (int v : ed[u]) {
            if (v == p || vis[v]) continue;
            dfs4(v, u);
        }
    }

    static int N = 0;

    static void getCentroid(int u, int p) {
        N = 0;//当前子树的大小
        centroid[0] = centroid[1] = 0;
        dfs4(u, p);//重置 size 数组和 weight 数组，并且统计当前子树的大小
        dfs(u, p, N);//求重心
        int cent = centroid[0];
        vis[cent] = true;
        //计算贡献-----------------------------------------------------------------------------------


        //-----------------------------------------------------------------------------------------

        //    System.out.println(cent + " " + ans);
        //分治
        for (int v : ed[cent]) {
            if (v == p || vis[v]) continue;
            getCentroid(v, cent);
        }
    }

    static boolean[] vis;

    static int[] size;//保存以当前节点为根的子树大小
    static int[] weight;//保存节点的所有儿子节点中的最大节点数
    static int[] centroid;//保存重心编号，重心最多两个

    //找重心
    static void dfs(int u, int p, int n) {
        size[u] = 1;
        for (int v : ed[u]) {
            if (v == p || vis[v]) continue;
            dfs(v, u, n);
            weight[u] = Math.max(weight[u], size[v]);
            size[u] += size[v];
        }
        weight[u] = Math.max(weight[u], n - size[u]);
        if (weight[u] <= n / 2) {//根据重心的定义，以树的重心为根时，所有子树的大小都不超过整棵树大小的一半。
            if (centroid[0] == 0) centroid[0] = u;
            else centroid[1] = u;
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
    static int n;


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


