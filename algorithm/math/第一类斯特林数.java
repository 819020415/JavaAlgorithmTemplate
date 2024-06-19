package algorithm.math;

import java.util.*;


public class 第一类斯特林数 {
    /**
     * 第一类斯特林数 斯特林轮换数 Stirling
     * 将 n 个 不同 元素，划分为 m 个非空圆排列的方案数，
     * 记作 S(n,m)
     * 有递推式：S(n,m) = S(n - 1,m - 1) + (n - 1) * S(n - 1,m)
     * 当前格子的值由它左上角元素和上面的元素转移
     * 注意初始化时 S(0,0) = 1,S(i,0) = 0
     * <p>
     * n 个人坐 m 张圆桌的方案数，考虑第 n 个人单独坐一张桌或坐到已经有人的桌
     * 1.若单独坐一桌，前面 n - 1 个人坐 m - 1 个桌
     * 2.若坐到已经有人的桌，就先让前 n - 1 个人坐 m 个桌，第 n 个人可以坐到前 n - 1 个人中任意一个人的左边，
     * 有 n - 1 中坐法
     * <p>
     * 洛谷 P4609
     */

    static int n = 50005, m = 205, mod = (int) (1e9 + 7);

    static long[][] S, c;//斯特林数，组合数

    static void init() {
        S = new long[n][m];
        c = new long[m][m];
        S[0][0] = 1;
        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++)
                S[i][j] = (S[i - 1][j - 1] + (i - 1) * S[i - 1][j]) % mod;
        for (int i = 0; i < m; i++) c[i][0] = 1;
        for (int i = 1; i < m; i++)
            for (int j = 1; j < m; j++)
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % mod;
    }

    public static void main(String[] args) {
        init();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = sc.nextInt(), a = sc.nextInt(), b = sc.nextInt();
            sb.append(S[n - 1][a + b - 2] * c[a + b - 2][a - 1] % mod).append('\n');
        }
        System.out.println(sb);
    }


}
