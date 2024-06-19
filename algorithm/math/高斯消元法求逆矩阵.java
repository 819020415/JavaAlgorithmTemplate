package algorithm.math;

public class 高斯消元法求逆矩阵 {
    /**
     * 矩阵 B 满足 BA = AB = I ,则称 B 是 A 的逆矩阵， B = A ^ -1，I 是单位矩阵
     * AX = I
     * A ^ -1 AX = A ^ -1
     * IX = A ^ -1
     * 构造 n * 2n 的矩阵，左边为 A ，右边为 I ，将左边矩阵变为 I，右边矩阵就是逆矩阵了
     * 如果在变换过程中某一行全为 0 了，说明矩阵 A 没有逆矩阵
     */


    static double epsilon = 1e-6;
    static double[][] a;//a 是增广矩阵，是 n 行 n + 1 列的，最后一列是方程结果那列
    static int n;

    static int Gauss_Jordan() {
        for (int i = 0; i < n; i++) {
            int r = i;
            for (int k = i; k < n; k++) {//找主元非 0 行
                if (Math.abs(a[k][i]) > epsilon) {
                    r = k;
                    break;
                }
            }
            if (r != i) swap(r, i);//交换两行
            if (Math.abs(a[i][i]) < epsilon) return a[i][n] == 0 ? 0 : -1;//0 表示无穷多解，-1 表示无解
            double x = pow(a[i][i], mod - 2);//逆元
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                double t = a[k][i] * x % mod;
                for (int j = 0; j <2* n; j++) {
                    a[k][j] = ((a[k][j] - t * a[i][j]) % mod + mod) % mod;
                }
            }
            for (int j = 0; j <2* n; j++)
                a[i][j] = a[i][j] * x % mod;
        }

        return 1;//存在唯一解
    }

    static void swap(int x, int y) {
        double t;
        for (int i = 0; i <= n; i++) {
            t = a[x][i];
            a[x][i] = a[y][i];
            a[y][i] = t;
        }
    }

    static int mod = (int) (1e9 + 7);

    static double pow(double x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2);
        else return pow(x * x % mod, n / 2) * x % mod;
    }




}
