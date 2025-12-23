package algorithm.图论.双连通分量;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class tarjan割点 {
    /**
     * Tarjan 割点
     * 割点：对于一个无向图，如果把一个点删除后，连通块的数量增加了，那么这个点就是割点（割顶）
     * 割点判定法则：如果 x 不是根节点，当搜索树中存在 x 的一个节点 y，满足 low[y] >= dfn[x]，那么 x 就是割点
     * 如果 x 是根节点，当搜索树中存在 x 的两个节点 y1,y2，满足上述条件，那么 x 就是割点
     */

    static int n;
    static List<Integer>[] ed;
    static int[] dfn, low;//时间戳，追溯值
    static int tot, root;//时间戳指针，根节点
    static int[] cut;//记录每个点是否是割点

    static void tarjan(int x) {
        dfn[x] = low[x] = ++tot;//盖戳
        int child = 0;//子树个数
        for (int y : ed[x]) {
            if (dfn[y] == 0) {//y 尚未访问
                tarjan(y);
                low[x] = Math.min(low[x], low[y]);
                if (low[y] >= dfn[x]) {//判定割点
                    child++;
                    if (x != root || child > 1) {
                        cut[x] = 1;
                    }
                }
            } else {
                low[x] = Math.min(low[x], dfn[y]);
            }
        }
    }

    static void init() {
        n = sc.nextInt();
        int m = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i < n; i++) {
            ed[i + 1] = new ArrayList<>();
        }
        dfn = new int[n + 1];
        low = new int[n + 1];


        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);
            ed[v].add(u);
        }
        for (int i = 1; i <= n; i++) if (dfn[i] == 0) tarjan(i);
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
