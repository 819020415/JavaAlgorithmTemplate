package algorithm;

class Comb {
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
     * 时间复杂度：O(N) 预处理，O(1) 查询
     */
    Comb(int N, int mod) {
        this.N = N;
        this.mod = mod;
        init();
    }

    int mod;
    int N;
    long[] f, g;//f[i] = i! g[i] = f[i] ^ -1 , i! 的逆元

    //Cnm = n! /((n - m)! * m!) = n 个数里取 m 个数的方案数
    long c(int n, int m) {
        return f[n] * g[m] % mod * g[n - m] % mod;
    }

    //Pnm = n! /(n - m)! = n 个数里取 m 个数的排列数
    long p(int n, int m) {
        return f[n] * g[n - m] % mod;
    }

    //获得 f,g 数组
    void init() {
        f = new long[N];
        g = new long[N];
        f[0] = g[0] = 1;
        for (int i = 1; i < N; i++) f[i] = f[i - 1] * i % mod;
        g[N - 1] = inv(f[N - 1]);
        for (int i = N - 2; i > 0; i--) g[i] = (g[i + 1] * (i + 1)) % mod;
    }

    long pow(long x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2);
        else return pow(x * x % mod, n / 2) * x % mod;
    }

    //求 x 的乘法逆元，当要除以 x 的时候，就相当于乘上它的乘法逆元
    long inv(long x) {
        return pow(x, mod - 2);
    }
}