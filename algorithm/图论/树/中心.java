package algorithm.图论.树;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Date: 2024/12/15 20:12
 * 在一个有边权的树上，求一点 u ，满足 u 到所有点的距离的
 * 最大值最小
 */
public class 中心 {

    public static void main(String[] args) {
        buildGraph();
        dfs(1, 0);
        dfs2(1, 0);
        getRes();
    }

    //建图
    static void buildGraph() {
        n = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            ed[u].add(new int[]{v, w});
            ed[v].add(new int[]{u, w});
        }
        d1 = new int[n + 1];
        d2 = new int[n + 1];
        son = new int[n + 1];
        up = new int[n + 1];
    }

    static int n;
    static List<int[]>[] ed;
    static int ans = 1 << 30;
    static int centroid;
    static int[] d1, d2, son, up;//d1[u] = u 子树的最长路径长度， d2[u] = u 子树的次长路径长度，
    //son[u] = u 子树的最长路径的那个儿子节点
    //up[u] = u 向上的最长路径

    static int dfs(int u, int p) {
        for (int[] t : ed[u]) {
            int v = t[0], w = t[1];
            if (v == p) continue;
            int d = dfs(v, u) + w;
            if (d > d1[u]) {
                d2[u] = d1[u];
                d1[u] = d;
                son[u] = v;
            } else if (d > d2[u]) {
                d2[u] = d;
            }
        }
        return d1[u];
    }

    static void dfs2(int u, int p) {
        for (int[] t : ed[u]) {
            int v = t[0], w = t[1];
            if (v == p) continue;
            if (son[u] == v) {//v 在 u 的子树最长路径上
                up[v] = w + Math.max(up[u], d2[u]);
            } else {
                up[v] = w + Math.max(up[u], d1[u]);
            }
            dfs2(v, u);
        }
    }

    static void getRes() {
        for (int i = 1; i <= n; i++) {
            if (ans > Math.max(up[i], d1[i])) {
                ans = Math.max(up[i], d1[i]);
                centroid = i;
            }
        }
        System.out.println(ans + " " + centroid);
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


