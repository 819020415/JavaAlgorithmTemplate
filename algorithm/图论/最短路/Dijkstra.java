package algorithm.图论.最短路;

import java.util.*;

public class Dijkstra {

    static void dijkstra(List<int[]>[] ed, int n, int s) {
        //求起点 s 到所有点的最短路径
        int max = Integer.MAX_VALUE / 2;
        //dijkstra 朴素版
        long[] dis = new long[n];
        int[] vis = new int[n];
        Arrays.fill(dis, max);
        dis[s] = 0;
        for (int i = 0; i < n; i++) {
            int u = 0;
            for (int j = 0; j < n; j++) {
                if (vis[j] == 1) continue;
                if (dis[j] < dis[u]) u = j;

            }
            vis[u] = 1;
            for (int[] e : ed[u]) {
                int v = e[0], w = e[1];
                if (dis[v] > dis[u] + w) dis[v] = dis[u] + w;
            }
        }
    }

    static long[] dijkstraHeap(List<int[]>[] ed, int n, int s) {
        //求起点 s 到所有点的最短路径
        long max = Long.MAX_VALUE / 2;
        //dijkstra 最小堆版
        long[] dis = new long[n];
        boolean[] vis = new boolean[n];
        Arrays.fill(dis, max);
        dis[s] = 0;
        PriorityQueue<long[]> p = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        p.offer(new long[]{s, 0});
        while (!p.isEmpty()) {
            long[] t = p.poll();
            int u = (int) t[0];
            if (vis[u]) continue;
            vis[u] = true;
            for (int[] e : ed[u]) {
                int v = e[0], w = e[1];
                if (dis[v] > dis[u] + w) {//只有发生松弛了才入堆
                    dis[v] = dis[u] + w;
                    p.offer(new long[]{v, dis[v]});
                }
            }
        }
        return dis;
    }


}
