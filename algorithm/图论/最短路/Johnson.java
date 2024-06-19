package algorithm.图论.最短路;

import java.util.*;

public class Johnson {

    /**
     * Johnson 全源最短路径算法
     * 可以求解带负权边的图
     * <p>
     * 1.新建一个虚拟源点 0,从该点向其他所有点连一条边权为 0 的边，
     * 再用 spfa 算法求出从 0 号点到其他所有点的最短路 h[i]
     * 2.将新图的边权改造为 : w'(u,v) = w(u,v) + h(u) - h(v), 这样能确保边权非负
     * 3.以每个点为源点，跑 n 轮 dijkstra-heap 算法，求出任意两点之间的最短路
     * 4.最终结果任意两点最短路 d'(u,v) = d(u,v) + h(u) - h(v)
     * d'(u,v) 为新图的最短路，d(u,v) 为旧图的最短路
     */


    void Johnson(List<int[]>[] ed, int n) {
        //注意所给的点编号为 1 ~ n ,如果是 0 ~ n - 1,虚拟源点可以考虑为 n
        for (int i = 1; i <= n; i++) {
            ed[0].add(new int[]{i, 0});
        }
        spfa(0, n, ed);
        if (f) return;
        //改造边权
        for (int u = 1; u <= n; u++) {
            int size = ed[u].size();
            for (int i = 0; i < size; i++) {
                int v = ed[u].get(i)[0];
                ed[u].get(u)[1] += h[u] - h[v];
            }
        }
        //跑 n 遍 dijkstraHeap
        for (int i = 1; i <= n; i++) {
            dijkstraHeap(ed, n, i);
            //每跑完一次 dijkstraHeap，当前 dis 数组保存的就是以 i 为源点的所有节点的最短路径
            for (int j = 1; j <= n; j++) {
                if (dis[j] == max) System.out.print(max + " ");
                else System.out.print(dis[j] + h[j] - h[i] + " ");
            }
            System.out.println();


        }


    }


    int[] dis;
    int[] h;//势能函数
    int max = Integer.MAX_VALUE / 2;
    boolean f = false;

    void spfa(int s, int n, List<int[]>[] ed) {
        dis = new int[n + 1];
        int[] vis = new int[n];//用于标记某个点是否在队列中
        int[] cnt = new int[n];//用于判断是否有负环

        Arrays.fill(h, max);
        h[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        vis[s] = 1;
        while (!q.isEmpty()) {
            int u = q.poll();
            vis[u] = 0;
            for (int[] t : ed[u]) {
                int v = t[0], w = t[1];
                if (h[v] > h[u] + w) {
                    h[v] = h[u] + w;//松弛操作
                    cnt[v] = cnt[u] + 1;
                    if (cnt[v] >= n) {
                        f = true;
                        return;
                    }
                    if (vis[v] == 0) {
                        q.offer(v);
                        vis[v] = 1;
                    }
                }
            }
        }
    }

    void dijkstraHeap(List<int[]>[] ed, int n, int s) {
        //求起点 s 到所有点的最短路径
        int max = Integer.MAX_VALUE / 2;
        //dijkstra 最小堆版
        // int[] dis = new int[n];
        int[] vis = new int[n];
        Arrays.fill(dis, max);
        dis[s] = 0;
        PriorityQueue<int[]> p = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        p.offer(new int[]{s, 0});
        while (!p.isEmpty()) {
            int[] t = p.poll();
            int u = t[0];
            if (vis[u] == 1) continue;
            vis[u] = 1;
            for (int[] e : ed[u]) {
                int v = e[0], w = e[1];
                if (dis[v] < dis[u] + w) {//只有发生松弛了才入堆
                    dis[v] = dis[u] + w;
                    p.offer(new int[]{v, dis[v]});
                }

            }
        }

    }
}
