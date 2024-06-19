package algorithm.图论.最近公共祖先;

import java.util.*;

public class Tarjan {
    /**
     * tarjan 算法，离线模式，使用并查集
     * 时间复杂度 O(m + n)
     */

    static List<Integer>[] ed;
    static List<int[]>[] query;
    static int[] fa, vis, ans;//fa 保存每个节点的父节点，vis 用于保证深搜时只往下走，不往回走，ans 保存每次查询的结果

    //n 个点 m 个查询
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt(), s = sc.nextInt();//n 个点，m 个查询 ,根节点为 s
        ed = new List[n + 1];//节点编号 1 ~ n
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {//一共 n - 1 条边
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);
            ed[v].add(u);
        }
        query = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            query[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            //每个查询加两次，保证能查出来
            query[u].add(new int[]{v, i});
            query[v].add(new int[]{u, i});
        }
        fa = new int[n + 1];
        vis = new int[n + 1];
        ans = new int[m];
        for (int i = 0; i <= n; i++) fa[i] = i;
        tarjan(s);
    }

    static void tarjan(int u) {
        vis[u] = 1;
        for (int v : ed[u]) {
            if (vis[v] == 1) continue;
            tarjan(v);
            fa[v] = u;
        }
        for (int[] q : query[u]) {
            int v = q[0], i = q[1];
            if (vis[v] == 1)
                ans[i] = find(v);
        }
    }

    static int find(int u) {
        while (fa[u] != u) {
            fa[u] = fa[fa[u]];
            u = fa[u];
        }
        return u;
    }


}
