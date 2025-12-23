package algorithm.图论.双连通分量;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class tarjan割边 {
    /**
     * Tarjan 割边
     * 割点：对于一个无向图，如果把一个点删除后，连通块的数量增加了，那么这个点就是割点（割顶）
     * 割点判定法则：如果 x 不是根节点，当搜索树中存在 x 的一个节点 y，满足 low[y] >= dfn[x]，那么 x 就是割点
     * 如果 x 是根节点，当搜索树中存在 x 的两个节点 y1,y2，满足上述条件，那么 x 就是割点
     *
     * ps:很容易爆栈
     */

    static int n;
    static List<Integer>[] ed;//ed[i] 保存的是边的编号，而不是它的邻点
    static List<int[]> edges;//保存每一条边
    static int[] dfn, low;//时间戳，追溯值
    static int tot, root;//时间戳指针，根节点
    static int[][] bri;//桥 bri 按照第一维升序排序，再按照第二维升序排序
    static int cnt;
    static int[] dcc;//记录双连通分量
    static int cc;
    static Stack<Integer> stack;


    static void tarjan(int x, int in_edge) {
        dfn[x] = low[x] = ++tot;//盖戳
        stack.push(x);
        for (int num : ed[x]) {
            int[] t = edges.get(num);
            int y = t[1];
            if (dfn[y] == 0) {//y 尚未访问
                tarjan(y, num);
                low[x] = Math.min(low[x], low[y]);
                if (low[y] > dfn[x]) {//判定割边
                    bri[cnt][0] = x;
                    bri[cnt][1] = y;
                    cnt++;
                }
            } else if (num != (in_edge ^ 1)) {//不是反边
                low[x] = Math.min(low[x], dfn[y]);
            }
        }
        if (dfn[x] == low[x]) {
            ++cc;
            while (true) {
                int y = stack.pop();
                dcc[y] = cc;
                if (y == x) break;
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
        edges = new ArrayList<>();
        dfn = new int[n + 1];
        low = new int[n + 1];
        dcc = new int[n + 1];
        bri = new int[n][2];
        stack = new Stack<>();


        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            edges.add(new int[]{u, v});
            ed[u].add(edges.size() - 1);
            edges.add(new int[]{v, u});
            ed[v].add(edges.size() - 1);
        }
        for (int i = 1; i <= n; i++) if (dfn[i] == 0) tarjan(i, 0);
    }


    static List<Integer>[] ed1;

    //建立新图，将原图中的环都缩成了点，新图是一棵树
    static void buildTree() {
        ed1 = new List[cc + 1];
        for (int i = 0; i <= cc; i++) {//cc = 双连通分量的数量，也就是新图中点的数量
            ed1[i] = new ArrayList<>();
        }
        for (int i = 0; i < cnt; i++) {//cnt = 桥的数量
            int u = dcc[bri[i][0]], v = dcc[bri[i][1]];
            ed1[u].add(v);
            ed1[v].add(u);
        }
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
