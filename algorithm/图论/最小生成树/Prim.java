package algorithm.图论.最小生成树;

import java.util.*;

public class Prim {
    /**
     * 最小生成树
     * 基于贪心思想，类似 dijkstra
     * 有两个集合，初始集合 A 只有一个起点，集合 B 有剩余的点，
     * 维护一个 dis 数组，保存集合 B 的点到集合 A 的点的最小距离
     * 每次都将集合 B 距离最小的那个点加入集合 A，然后用这个点更新 dis 数组
     */

    boolean Prim(int s, int n, List<int[]>[] ed) {
        //点编号为 0 ~ n - 1
        int[] dis = new int[n];
        int[] vis = new int[n];
        int ans = 0;//形成最小生成树的最小边权和
        int max = Integer.MAX_VALUE / 2;
        Arrays.fill(dis, max);
        dis[s] = 0;
        PriorityQueue<int[]> p = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        p.offer(new int[]{s, 0});
        int cnt = 0;
        while (!p.isEmpty()) {
            int[] t = p.poll();
            int u = t[0];
            if (vis[u] == 1) continue;
            vis[u] = 1;
            ans += dis[u];
            cnt++;
            for (int[] r : ed[u]) {
                int v = r[0], w = r[1];
                if (dis[v] > w) {
                    dis[v] = w;
                    p.offer(new int[]{v, dis[v]});
                }
            }
        }
        return cnt == n;//cnt == n 表示能构成 MST ，否则表示图不连通
    }
}
