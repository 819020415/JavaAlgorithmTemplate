package algorithm.图论.树;

import java.util.*;

public class 重心 {
    /**
     * 树的重心
     * 定义
     * 如果在树中选择某个节点并删除，这棵树将分为若干棵子树，统计子树节点数并记录最大值。
     * 取遍树上所有节点，使此最大值取到最小的节点被称为整个树的重心。
     * <p>
     * （这里以及下文中的「子树」若无特殊说明都是指无根树的子树，即包括「向上」的那棵子树，
     * 并且不包括整棵树自身。）
     * <p>
     * 性质
     * 树的重心如果不唯一，则至多有两个，且这两个重心相邻。
     * 以树的重心为根时，所有子树的大小都不超过整棵树大小的一半。
     * 树中所有点到某个点的距离和中，到重心的距离和是最小的；如果有两个重心，那么到它们的距离和一样。
     * 把两棵树通过一条边相连得到一棵新的树，那么新的树的重心在连接原来两棵树的重心的路径上。
     * 在一棵树上添加或删除一个叶子，那么它的重心最多只移动一条边的距离。
     * <p>
     * 求法
     * 在 DFS 中计算每个子树的大小，记录「向下」的子树的最大大小，利用总点数 -
     * 当前子树（这里的子树指有根树的子树）的大小得到「向上」的子树的大小，
     * 然后就可以依据定义找到重心了。
     */

    static int N;
    static int[] size;//保存以当前节点为根的子树大小
    static int[] weight;//保存节点的所有儿子节点中的最大节点数
    static int[] centroid;//保存重心编号，重心最多两个
    static List<Integer>[] ed;

    static void dfs(int u, int p) {
        size[u] = 1;
        for (int v : ed[u]) {
            if (v == p) continue;
            dfs(v,u);
            weight[u] = Math.max(weight[u], size[v]);
            size[u] += size[v];
        }
        weight[u] = Math.max(weight[u], N - size[u]);
        if (weight[u] <= N / 2) {//根据重心的定义，以树的重心为根时，所有子树的大小都不超过整棵树大小的一半。
            if (centroid[0] == 0) centroid[0] = u;
            else centroid[1] = u;
        }
    }


}
