package algorithm.图论.最近公共祖先;

import java.util.*;

public class 倍增 {
    /**
     * 倍增算法求解最近公共祖先
     */
    static int n = (int) 5e5;//点数
    static List<Integer>[] ed;
    static int[] dep;//深度
    static int[][] fa = new int[20][n];//fa[i][j] = 节点 j 的第 2 ^ i 个祖先


    //创建 ST 表
    static void dfs(int u, int p) {
        dep[u] = dep[p] + 1;
        fa[0][u] = p;
        for (int i = 1; i <= 19; i++) {
            //先跳一半，再跳一半
            fa[i][u] = fa[i - 1][fa[i - 1][u]];
        }
        for (int v : ed[u]) {
            if (v == p) continue;
            dfs(v, u);
        }
    }

   //求 u 和 v 的 lca
    static int lca(int u, int v) {
        if (dep[u] < dep[v]) {//保证 u 的深度大
            int t = u;
            u = v;
            v = t;
        }
        //先将 u 跳到和 v 同一层
        for (int i = 19; i >= 0; i--) {
            if (dep[fa[i][u]] >= dep[v]) u = fa[i][u];
        }
        if (u == v) return v;//v 是 u 的祖先
        //两个节点一起跳，最后会跳到 lca 的下一层
        for (int i = 19; i >= 0; i--) {
            if (fa[i][u] != fa[i][v]) {//保证不跳过
                u = fa[i][u];
                v = fa[i][v];
            }
        }
        return fa[0][u];
    }

    //求 u 和 v 的距离
    static int dis(int u, int v) {
        if (dep[u] < dep[v]) {//保证 u 的深度大
            int t = u;
            u = v;
            v = t;
        }
        //先将 u 跳到和 v 同一层
        int ans = 0;
        for (int i = 19; i >= 0; i--) {
            if (dep[fa[i][u]] >= dep[v]) {
                ans += 1 << i;
                u = fa[i][u];
            }
        }
        if (u == v) return ans;//v 是 u 的祖先
        //两个节点一起跳，最后会跳到 lca 的下一层
        for (int i = 19; i >= 0; i--) {
            if (fa[i][u] != fa[i][v]) {//保证不跳过
                u = fa[i][u];
                v = fa[i][v];
                ans += 1 << (i + 1);
            }
        }
        return ans + 2;
    }
        return fa[0][u];
    }

}
