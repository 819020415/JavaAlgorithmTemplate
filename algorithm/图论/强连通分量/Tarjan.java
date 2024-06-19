package algorithm.图论.强连通分量;

import java.util.*;

public class Tarjan {
    /**
     * 强连通分量(SCC) Tarjan 算法
     * <p>
     * 概念：
     * 强连通：若一张有向图的节点两两互相可达，则称这张图是强连通的
     * 强连通分量(SCC)：极大的强连通子图
     * 搜索树：对图深搜时，每一个节点只访问一次，被访问过的节点与边构成 搜索树 (DFS 生成树)
     * <p>
     * 有向边按访问情况分为 4 类：
     * 1.树边(tree edge)：搜索树的边
     * 2.返祖边(back edge)：指向祖先节点的边
     * 3.横叉边(cross edge)：右子树指向左子树的边
     * 4.前向边(forward edge)：指向子树中节点的边
     * <p>
     * 返祖边和树边必构成环，横叉边可能与树边构成环，前向边无用
     * <p>
     * 如果节点 x 是某个强连通分量在搜索树中遇到的第一个节点，
     * 那么这个强连通分量的其余节点肯定是在搜索树中以 x 为根的子树中。
     * 节点 x 称为这个强连通分量的根。
     *
     * 目的：获得 scc 数组，即获得每个点所在的强连通分量
     * 洛谷 P2863
     */
    static int n;//树的节点数，编号为 1 ~ n
    static List<Integer>[] ed;//存邻点
    static int[] dfn = new int[n + 1];//时间戳数组，节点 x 第一次被访问的顺序
    static int[] low = new int[n + 1];//从节点 x 出发，所能访问到的最早时间戳
    static int tot = 0;//时间戳编号
    static int[] stk = new int[n + 1];//栈
    static int[] inStk = new int[n + 1];//标记某个节点是否在栈中
    static int top = 0;//栈指针
    static int[] scc = new int[n + 1];//记录每个点所在的强连通分量
    static int[] size = new int[n + 1];//记录每个强连通分量的大小
    static int cnt = 0;//强连通分量编号

    static void tarjan(int x) {
        //入 x时，盖戳，入栈
        tot++;
        dfn[x] = low[x] = tot;
        top++;
        stk[top] = x;
        inStk[x] = 1;

        for (int y : ed[x]) {
            if (dfn[y] == 0) {//如果 y 尚未访问
                tarjan(y);
                low[x] = Math.min(low[x], low[y]);
            } else if (inStk[y] != 0) {//如果 y 已访问，并且在栈中
                low[x] = Math.min(low[x], dfn[y]);//这里如果使用 low[y] 更新 low[x]，在 SCC 都可以，在双连通分量会出错
            }
        }

        //离 x 时，记录 SCC
        if (dfn[x] == low[x]) {//如果 x 是 SCC 的根
            int y;
            cnt++;
            do {
                y = stk[top--];//获得栈顶的点
                inStk[y] = 0;//标记为已出栈
                scc[y] = cnt;//SCC 编号
                size[cnt]++;//更新 SCC 大小
            } while (y != x);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        int m = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            ed[u].add(v);
        }
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjan(i);
            }
        }
        for (int i = 1; i <= n; i++) {
            System.out.println(i + " " + scc[i]);
        }
    }
}
