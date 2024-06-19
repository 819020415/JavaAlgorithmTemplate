package algorithm.图论.最短路;

import java.util.*;

public class BellmanFord {

    public static void main(String[] args) {
        int n = 3;
        int s = 1;
        List<int[]>[] ed = new List[4];
        for (int i = 0; i < 4; i++) {
            ed[i] = new ArrayList<>();
        }
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            ed[u].add(new int[]{v,w});
        }
        bellmanFord(s,n+1,ed);
    }

    static  boolean bellmanFord(int s, int n, List<int[]>[] ed) {
        int[] dis = new int[n];
        int max = Integer.MAX_VALUE / 2;
        Arrays.fill(dis, max);
        dis[s] = 0;
        boolean f = false;
        for (int i = 0; i < n; i++) {//最多执行 n 轮操作
            f = false;
            for (int u = 0; u < n; u++) {//枚举所有点
                if (dis[u] == max) continue;
                for (int[] t : ed[u]) {
                    int v = t[0], w = t[1];
                    if (dis[v] > dis[u] + w) {
                        dis[v] = dis[u] + w;//松弛操作
                        f = true;
                    }
                }
            }
            if (!f) break;
        }
        return f;//n 轮操作后 f = true 表示图中有负环，f = false 的话 ，dis 数组就是单源最短路径数组
    }


    //spfa优化
    boolean spfa(int s, int n, List<int[]>[] ed) {
        int[] dis = new int[n];
        int[] vis = new int[n];//用于标记某个点是否在队列中
        int[] cnt = new int[n];//用于判断是否有负环
        int max = Integer.MAX_VALUE / 2;
        Arrays.fill(dis, max);
        dis[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        vis[s] = 1;
        while (!q.isEmpty()) {
            int u = q.poll();
            vis[u] = 0;
            for (int[] t : ed[u]) {
                int v = t[0], w = t[1];
                if (dis[v] > dis[u] + w) {
                    dis[v] = dis[u] + w;//松弛操作
                    cnt[v] = cnt[u] + 1;
                    if (cnt[v] >= n) return true;//当前点更新次数 >= n,有负环
                    if (vis[v] == 0) {
                        q.offer(v);
                        vis[v] = 1;
                    }
                }
            }
        }
        return false;
    }


}
