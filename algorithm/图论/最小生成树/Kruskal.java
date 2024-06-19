package algorithm.图论.最小生成树;

import java.util.*;

public class Kruskal {
    /**
     * 最小生成树
     * 贪心思想，把所有边按照边权升序排序
     * 按顺序枚举每一条边，如果当前边的两个点不在同一集合中，就把这条边加入 MST
     * 并且合并这两个集合；否则直接跳过。
     * 重复执行以上操作，直到选取了 n - 1 条边为止。
     * 利用并查集判断两点是否在同一集合中
     */

    boolean Kruskal(int[][] edges, int n) {
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        int[] pa = new int[n];
        for (int i = 0; i < n; i++) {
            pa[i] = i;
        }
        int ans = 0;
        int cnt = 0;
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            int p1 = find(pa, u);
            int p2 = find(pa, v);
            if (p1 != p2) {
                pa[p1] = p2;
                ans += w;
                cnt++;
                //if (cnt == n - 1) break;
            }
        }
        return cnt == n - 1;

    }


    int find(int[] pa, int i) {
        while (pa[i] != i) {
            pa[i] = pa[pa[i]];
            i = pa[i];
        }
        return pa[i];
    }

    void union(int[] pa, int i1, int i2) {
        pa[find(pa, i1)] = find(pa, i2);
    }


}
