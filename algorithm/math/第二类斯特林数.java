package algorithm.math;

import java.util.*;

public class 第二类斯特林数 {
    /**
     * 第一类斯特林数 斯特林子集数 Stirling
     * 将 n 个 不同 元素，划分为 m 个非空集合的方案数，
     * 记作 S(n,m)
     * 有递推式：S(n,m) = S(n - 1,m - 1) + m * S(n - 1,m)
     * 当前格子的值由它左上角元素和上面的元素转移
     * 注意初始化时 S(0,0) = 1,S(i,0) = 0
     * <p>
     * n 个人进 m 个房间的方案数，考虑第 n 个人单独在一个房间或进入已经有人的房间
     * 1.若单独在一个房间，前面 n - 1 个人在 m - 1 个房间
     * 2.若进入已经有人的房间，就先让前 n - 1 个人在 m 个房间，第 n 个人可以进入任意一个房间，
     * 有 m 中方案
     * <p>
     * 洛谷 P3904
     */

    //高精度
    static int N = 55, M = 55;
    static long[][][] S;//S[i][j][k] = S(i,j) 的第 k 位的数字
    static int[][] L;//L[i][j] = S(i,j) 的最高位

    static void add(int x, int y) {
        int high = Math.max(L[x - 1][y - 1], L[x - 1][y]);
        for (int i = 0; i < high; i++) {
            S[x][y][i] += (S[x - 1][y - 1][i] + S[x - 1][y][i] * y);
            S[x][y][i + 1] += S[x][y][i] / 10;
            S[x][y][i] %= 10;
        }
        L[x][y] = high;
        while (S[x][y][L[x][y]] > 0) {
            S[x][y][L[x][y] + 1] = S[x][y][L[x][y]] / 10;
            S[x][y][L[x][y]] %= 10;
            L[x][y]++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        if (n < m) {
            System.out.println(0);
            return;
        }
        S = new long[N][M][100];
        L = new int[N][M];
        S[0][0][0] = 1;
        L[0][0] = 1;
        for (int i = 1; i < N; i++)
            for (int j = 1; j < M; j++)
                add(i, j);
        StringBuilder sb = new StringBuilder();
        for (int i = L[n][m] - 1; i >= 0; i--) {
            sb.append(S[n][m][i]);
        }
        System.out.println(sb);

    }


}
