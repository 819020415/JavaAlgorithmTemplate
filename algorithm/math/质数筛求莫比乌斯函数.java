package algorithm.math;

public class 质数筛求莫比乌斯函数 {
    /**
     * 莫比乌斯函数定义
     * u[i] = 1, i = 1
     *      = 0 ,i 包含相同的质因子
     *      = -1 ^ s, s 为不同质因子的个数
     *
     */

    static int[] p, vis, mu;//p 保存质数，vis 辅助数组，mu 存莫比乌斯函数值
    static int cnt;

    static void get_mu(int n) {
        n += 5;
        p = new int[n];
        vis = new int[n];
        mu = new int[n];
        mu[1] = 1;
        for (int i = 2; i < n; i++) {
            if (vis[i] == 0) {
                p[++cnt] = i;
                mu[i] = -1;
            }
            for (int j = 1; i * p[j] < n; j++) {
                int m = i * p[j];
                vis[m] = 1;
                if (i % p[j] == 0) {
                    mu[m] = 0;
                    break;
                } else mu[m] = -mu[i];
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(i + " " + mu[i]);
        }
    }

    public static void main(String[] args) {
        get_mu(100);
    }

}
