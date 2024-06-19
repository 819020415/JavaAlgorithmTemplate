package algorithm.图论.二分图;

import java.util.*;

public class BipartiteGraph {
    //判断给定的图是否是二分图
    static boolean isBipartiteGraph(List<Integer>[] ed, int n) {
        DSU dsu = new DSU(n + 1);
        for (int i = 0; i < n; i++) {
            if (ed[i].size() == 0) continue;
            int x = ed[i].get(0);
            for (int u : ed[i]) {
                if (dsu.same(u, i)) {
                    return false;
                }
                dsu.union(x, u);
            }
        }
        return true;
    }

    //判断给定的图是否是二分图
    static boolean isBipartiteGraph(int[][] g, int n) {
        DSU dsu = new DSU(n + 1);
        for (int i = 0; i < n; i++) {
            int x = -1;
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) {
                    if (x == -1) x = j;
                    if (dsu.same(i, j)) {
                        return false;
                    }
                    dsu.union(x, j);
                }

            }
        }
        return true;
    }

    static class DSU {
        DSU(int n) {
            init(n);
        }

        private int[] p, sz;

        void init(int n) {
            p = new int[n];
            sz = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = i;
                sz[i] = 1;
            }
        }

        //用于连接两个连通分量
        void union(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) return;
            sz[x] += sz[y];
            p[y] = x;
        }

        //用于查询某个元素所在连通分量的根节点
        int find(int x) {
            while (p[x] != x) {
                p[x] = p[p[x]];
                x = p[x];
            }
            return x;
        }

        //查询两个元素是否在同一连通分量中
        boolean same(int x, int y) {
            return find(x) == find(y);
        }

        //返回 x 所在连通分量的元素个数
        int size(int x) {
            return sz[find(x)];
        }

    }
}
