package algorithm.快速幂;

public class Pow {
    //快速幂
    static int mod = (int) (1e9 + 7);

    static long pow(long x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2);
        else return pow(x * x % mod, n / 2) * x % mod;
    }

    static long pow1(long x, long n) {
        long res = 1;
        while (n > 0) {
            if ((n & 1) == 1) res = res * x % mod;
            x = (x * x) % mod;
            n >>= 1;
        }
        return res;
    }


}
