package algorithm.图论.二分图;

import java.util.*;

public class 最大匹配 {
    /**
     * 二分图的最大匹配 匈牙利算法
     * 概念：
     * 设 G 为二分图，若在 G 的子图 M 中，任意两条边都没有公共节点，
     * 那么称 M 为 G 的一组匹配。在二分图中，包含边数最多的一组匹配叫做
     * 二分图的最大匹配
     * <p>
     * 交替路：从一个未匹配点出发，依次经过未匹配边、匹配边...形成的路径叫做叫做交替路
     * <p>
     * 增广路：从一个未匹配点出发，走交替路，若能到达另一个未匹配点，则这条路径叫做增广路
     * <p>
     * 匈牙利算法：通过不停找增广路来增加匹配边，找不到增广路时达到了最大匹配
     * 可以用 DFS 或 BFS 实现
     * <p>
     * 时间复杂度：O(m * n) m，n 是两个集合的点数，实际会快很多
     * <p>
     * 补充：
     * 二分图最小点覆盖（König 定理）
     * 最小点覆盖：选最少的点，满足每条边至少有一个端点被选。
     *
     * 二分图中，最小点覆盖 = 最大匹配。
     *
     * 二分图最大独立集
     * 最大独立集：选最多的点，满足两两之间没有边相连。
     *
     * 因为在最小点覆盖中，任意一条边都被至少选了一个顶点，所以对于其点集的补集，任意一条边都被至多选了一个顶点，
     * 所以不存在边连接两个点集中的点，且该点集最大。因此二分图中，最大独立集 = m + n - 最小点覆盖 ，) m，n 是两个集合的点数，
     *
     * 思考：如果给了 N 个点的二分图，如何求最大匹配？
     *
     * @param args
     */
    static List<Integer>[] ed;
    static int[] match;//保存当前点的匹配点
    static int[] vis;//判断当前点是否在增广路上，是否已经访问

    static boolean dfs(int u) {
        for (int v : ed[u]) {
            if (vis[v] == 1) continue;
            vis[v] = 1;
            if (match[v] == 0 || dfs(match[v])) {
                match[v] = u;
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt(), e = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);//加单向边即可
        }
        match = new int[m + 1];
        vis = new int[m + 1];
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, 0);
            if (dfs(i)) ans++;
        }
        System.out.println(ans);
    }


}
