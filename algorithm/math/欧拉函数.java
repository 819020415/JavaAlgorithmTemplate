package algorithm.math;

/**
 * @date 2024/4/25 13:32
 **/
public class 欧拉函数 {
    /**
     * 定义：[1,n] 中与 n 互质( gcd(i,n)==1 )的数的个数称为欧拉函数，记为 φ(n)
     * 欧拉函数的性质：
     * 1.若 p 是质数，则 φ(p) = p - 1
     * 2.若 p 是质数，则 φ(p ^ k) = (p - 1) * p ^ (k - 1)
     * 3.积性函数：若 gcd(m,n) = 1,则 φ(m * n) = φ(m) * φ(n)
     */

    int[] φ;
    //欧式筛，线性筛
    static int[] p;
    static boolean[] st;//st[i]=true 表示 i 是质数
    static int[] phi;//欧拉函数


    static void f(int n) {
        int cnt = 0;
        p = new int[n + 1];
        st = new boolean[n + 1];//st[i]=true 表示 i 是质数
        for (int i = 2; i <= n; i++) {
            if (!st[i]) {
                p[cnt++] = i;
                phi[i] = i - 1;
            }
            for (int j = 0; p[j] * i <= n; j++) {
                int m = p[j] * i;
                st[m] = true;
                if (i % p[j] == 0) {
                    phi[m] = p[j] * phi[i];
                    break;
                } else {
                    phi[m] = (p[j] - 1) * phi[i];
                }
            }
        }
    }


    public static void main(String[] args) {
        f(100000);
    }

}
