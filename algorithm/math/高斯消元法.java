package algorithm.math;

public class 高斯消元法 {
    /**
     * 求解线性方程组，
     * 高斯约旦消元法：把系数矩阵消成主对角矩阵，再除以主元
     * 每轮循环，主元所在行不变，主元所在列消成 0
     * 解分三种情况：
     * 1.唯一解：i 可以枚举完 n 行
     * 2.无解：a[i][i] = 0 && b != 0
     * 3.无穷多解：a[i][i] = 0 && b == 0
     *
     * 时间复杂度：O(n ^ 3)
     *
     * 补充：
     * 三对角矩阵算法（也称为托马斯算法）：一种特殊的矩阵，
     * 只有主对角线以及主对角线上下相邻的两个对角线有值，其他地方均为 0
     * 时间复杂度：O(n)
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
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                double t = (double) a[k][i] / a[i][i];
                for (int j = 0; j <= n; j++) {
                    a[k][j] -= t * a[i][j];
                }
            }
        }
        for (int i = 0; i < n; i++) a[i][n] /= a[i][i];//除以主元，矩阵最后一列即是方程组的答案
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

}
