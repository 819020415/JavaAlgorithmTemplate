package algorithm;

import java.util.*;

/**
 * @date 2024/6/17 15:52
 * 将一颗树重新编号，编号后满足一个性质：任何节点 u 的所有子节点在它右边的一个区间内
 * 此时修改 u 及其子树等价于修改区间
 * [dfn[u],dfn[u] + sz[u] - 1]
 * 可以用线段树加速修改
 **/
public class dfn序 {
    static int[] dfn, sz, pos;
    static List<Integer>[] ed;
    static int tot;

    static void dfs(int u, int p) {
        dfn[u] = tot;
        pos[tot] = u;
        sz[u] = 1;
        tot++;
        for (int v : ed[u]) {
            if (v == p) continue;
            dfs(v, u);
            sz[u] += sz[v];
        }
    }


}
