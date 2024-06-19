package algorithm.math;

import java.util.*;

public class 中国剩余定理 {
    /**
     * 中国剩余定理
     * 求解线性同余方程组
     * x ≡ r1 (mod m1)
     * x ≡ r2 (mod m2)
     * ...
     * x ≡ rn (mod mn)
     * 其中模数 m1,m2,...,mn 两两互质
     * <p>
     * 1.计算所有模数的乘积 M
     * 2.计算第 i 个方程的 ci = M / mi
     * 3.计算 ci 在模 mi 意义下的乘法逆元 ci-1
     *
     *       n
     * 4.x = ∑   ri * ci * ci-1 (mod M)
     *       i = 1
     */


    static long x, y;

    //扩展欧几里得算法
    static long exgcd(long a, long b) {
        if (b == 0) {
            x = 1;
            y = 0;
            return a;
        }
        long d;
        d = exgcd(b, a % b);
        long x1 = x, y1 = y;
        x = y1;
        y = x1 - (a / b) * y1;
        return d;
    }

    static long CRT(long[] m, long[] r) {
        long M = 1, ans = 0;
        int n = m.length;
        for (int i = 0; i < n; i++) {
            M *= m[i];
        }
        for (int i = 0; i < n; i++) {
            long c = M / m[i];
            exgcd(c, m[i]);
            ans = (ans + r[i] * c * x % M) % M;
        }
        return (ans + M) % M;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

    }

}
