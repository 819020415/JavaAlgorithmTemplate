package algorithm.图论.最近公共祖先;

import java.util.*;

public class 重链剖分 {
    /**
     * 树链剖分
     * 概念：
     * 重儿子：父节点的所有儿子中子树节点数目最多的节点
     * 轻儿子：父节点中除了重儿子以外的儿子
     * 重边：父节点与重儿子连成的边
     * 轻边：父节点与轻儿子连成的边
     * 重链：由多条重边连接而成的路径
     * <p>
     * 1.整棵树会被剖分成若干条重链
     * 2.轻儿子一定是每条重链的顶点
     * 3.任意一条路径被切分成不超过 logN 条链
     * <p>
     * 流程：
     * 1.第一遍 dfs，求出 fa,dep,son 数组
     * 2.第二遍 dfs，求出 top 数组
     * 3.求两个点的 lca，让两个游标沿着各自的重链向上跳，
     * 跳到同一条重链上时，深度较小的那个游标所指的点就是 lca
     * <p>
     * 时间复杂度：O(n + m * log n) ,m 为查询次数
     */

    int n;//树的节点数，编号为 1 ~ n
    int[] fa = new int[n + 1];//存 u 的父节点
    int[] dep = new int[n + 1];//存 u 的深度
    int[] son = new int[n + 1];//存 u 的重儿子
    int[] size = new int[n + 1];//存以 u 为根的子树的节点数
    int[] top = new int[n + 1];//存 u 所在重链的顶点
    List<Integer>[] ed;//保存 u 的邻点

    void dfs1(int u, int father) {
        fa[u] = father;
        dep[u] = dep[father] + 1;
        size[u] = 1;
        for (int v : ed[u]) {
            if (v == father) continue;
            dfs1(v, u);
            size[u] += size[v];
            if (size[son[u]] < size[v]) son[u] = v;
        }
    }

    void dfs2(int u, int t) {//t 为链头
        top[u] = t;
        if (son[u] == 0) return;//无重儿子 返回
        dfs2(son[u], t);//搜重儿子
        for (int v : ed[u]) {//枚举当前节点的轻儿子
            if (v == fa[u] || v == son[u]) continue;
            dfs2(v, v);//搜轻儿子
        }
    }

    int lca(int u, int v) {//求 u 和 v 的 lca
        while (top[u] != top[v]) {//两节点不在同一条重链上
            if (dep[top[u]] < dep[top[v]]) {//保证 u 的链头的深度更大
                int t = u;
                u = v;
                v = t;
            }
            u = fa[top[u]];//将 u 跳到它的链头的父节点
        }
        return dep[u] < dep[v] ? u : v;
    }
}
