package algorithm.图论.最短路;

import java.util.*;

public class Floyd {
    int[][] p;

    //Floyd 全源最短路径 O(n^3) ,能处理负权边，能判断负环
    long[][] Floyd(int[][] g) {
        int n= g.length;
        for (int[] pp : p) Arrays.fill(pp, -1);
        //动态规划 插点法
        long[][] f = new long[n][n];
        for (long[] ff : f) Arrays.fill(ff, (long) 1e9);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) f[i][j] = 0;
                else f[i][j] = g[i][j];
            }
        }
        for (int k = 0; k < n; k++) {
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    //  f[x][y] = Math.min(f[x][y], f[x][k] + f[k][y]);
                    if (f[x][y] > f[x][k] + f[k][y]) {
                        f[x][y] = f[x][k] + f[k][y];
                        p[x][y] = k;//记录插点
                    }
                }
            }
        }
        return f;
    }

    void path(int i, int j) {//输出 i -> j 的路径
        if (p[i][j] == -1) return;//表示 i,j 之间没有插点
        int k = p[i][j];
        //中序遍历
        path(i, k);
        System.out.println(k);
        path(k, j);

    }

}
