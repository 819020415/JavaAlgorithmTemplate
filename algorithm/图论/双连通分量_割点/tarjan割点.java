package algorithm.图论.双连通分量_割点;

import java.util.*;

public class tarjan割点 {
    /**
     * Tarjan 割点
     * 割点：对于一个无向图，如果把一个点删除后，连通块的数量增加了，那么这个点就是割点（割顶）
     * 割点判定法则：如果 x 不是根节点，当搜索树中存在 x 的一个节点 y，满足 low[y] >= dfn[x]，那么 x 就是割点
     * 如果 x 是根节点，当搜索树中存在 x 的两个节点 y1,y2，满足上述条件，那么 x 就是割点
     */

    int n;
    List<Integer>[] ed;
    int[] dfn, low;//时间戳，追溯值
    int tot, root;//时间戳指针，根节点
    int[] cut;//记录每个点是否是割点

    void tarjan(int x) {
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
}
