package algorithm.快速幂;

public class MatrixPow {
    //矩阵快速幂 时间复杂度：O(n ^ 3 * logK)
    //求矩阵 A 的 K 次幂
    static int n;//矩阵的长度和宽度 n * n 的矩阵
    static int mod = (int) (1e9 + 7);
    static long[][] A;
    static long[][] res;

    //两矩阵相乘
    static long[][] multi(long[][] a, long[][] b) {
        long[][] t = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    t[i][j] += a[i][k] * b[k][j];
                    t[i][j] %= mod;
                }
            }
        }
        return t;
    }

    //k 个矩阵相乘
    static void quickPow(long k) {
        for (int i = 0; i < n; i++) res[i][i] = 1;//构造单位矩阵
        while (k > 0) {
            if ((k & 1) == 1) res = multi(res, A);
            A = multi(A, A);
            k >>= 1;
        }
    }

    //入口，传入一个矩阵和一个整数 k ，返回该矩阵的 k 次幂
    static long[][] getRes(int[][] a, long k) {
        n = a.length;
        A = new long[n][n];
        res = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = a[i][j];
            }
        }
        quickPow(k);
        return res;
    }


}
