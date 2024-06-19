package algorithm;

public class combination {
    public static void main(String[] args) {
        c(5, 2);
    }

    //求Cnk 组合数,从n个数中拿k个数的方案数,时间复杂度 O(n ^ 2)
    static void c(int n, int k) {
        long[][] f = new long[n + 1][k + 1];
        //f[i][j]表示Cij
        for (int i = 0; i <= n; i++) {
            f[i][0] = 1;//Ci0=1
        }
        for (int i = 1; i <= k; i++) {
            f[i][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i && j <= k; j++) {
                f[i][j] = f[i - 1][j - 1] + f[i - 1][j];
            }
        }
    }

    /**
     * 方法 2 ：快速幂
     * <p>
     * Cnm = n! /((n - m)! * m!) = n 个数里取 m 个数的方案数
     * 用 f[x] 存 x! (mod p) 的值,f[x] = x!
     * 用 g[x] 存 (x!) ^ -1 (mod p) 的值,也就是 f[x] 的逆元 ，满足 (f[x] * g[x]) % mod = 1
     * <p>
     * 因为 p 是质数且 n,m 都小于 p，即 n,m 与 p 互质
     * 所以根据费马小定理 a * a ^ (p - 2) ≡ 1 (mod p)
     * 可以用快速幂求逆元
     * <p>
     * Cnm = f[n] * g[n - m] * g[m] (mod p)
     * <p>
     * 时间复杂度：O(N *log mod)
     */

    static int mod = (int) (1e9 + 7);
    static int N = (int) 1e5;//n 和 m 的上界
    static long[] f, g;

    //Cnm = n! /((n - m)! * m!) = n 个数里取 m 个数的方案数
    static long getC(int n, int m) {
        return f[n] * g[m] % mod * g[n - m] % mod;
    }

    //获得 f,g 数组
    static void init() {
        f = new long[N];
        g = new long[N];
        f[0] = g[0] = 1;
        for (int i = 1; i < N; i++) {
            f[i] = f[i - 1] * i % mod;
            g[i] = g[i - 1] * inv(i) % mod;
        }
    }

    static long pow(long x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2);
        else return pow(x * x % mod, n / 2) * x % mod;
    }

    //求 x 的乘法逆元，当要除以 x 的时候，就相当于乘上它的乘法逆元
    static long inv(long x) {
        return pow(x, mod - 2);
    }

    /**
     * 方法 3 ：卢卡斯定理
     *   m     m / p     m % p
     * C n ≡ C n / p * C n % p (mod p) ,其中 p 为质数
     * <p>
     *   m / p
     * C n / p 可以继续用 Lucas 定理求解
     *            x
     * 引理 1 ：C p  ≡ 0 (mod p)
     * 引理 2 ：(1 + x) ^ p ≡ 1 + x ^ p (mod p)
     * <p>
     * 时间复杂度 ：O(p*log p + log(p)n)
     */

    static long lucas(long n, long m) {
        if (m == 0) return 1;
        return lucas(n / mod, m / mod) * getC((int) (n % mod), (int) (m % mod)) % mod;
    }


}
