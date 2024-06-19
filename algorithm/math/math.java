package algorithm.math;

public class math {


    static int mod = (int) (1e9 + 7);

    //求 x ^ n
    static long pow(long x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2);
        else return pow(x * x % mod, n / 2) * x % mod;
    }

    //求 x ^ n
    static long pow1(long x, long n) {
        long ans = 1;
        for (; n > 0; n >>= 1) {
            if ((n & 1) == 1) ans = ans * x % mod;
            x = x * x % mod;
        }
        return ans % mod;
    }

    //求 x 的乘法逆元，当要除以 x 的时候，就相当于乘上它的乘法逆元
    static long inv(long x) {
        return pow(x, mod - 2) % mod;
    }

    //求 a 和 b 的最小公倍数, lcm * gcd = a * b
    static long getLCM(long a, long b) {
        return a * b / getGCD(a, b);
    }

    //求 a 和 b 的最大公约数,做法：辗转相除法
    static long getGCD(long a, long b) {
        if (b == 0) return a;
        if (a % b == 0) return b;
        return getGCD(b, a % b);
    }

    //求 log2(X)
    static double log2(long x) {
        return Math.log(x) / Math.log(2);
    }

    //判断数字是否为质数
    static boolean isPrime(int d) {
        if (d == 1) return false;
        for (int i = 2; i * i <= d; i++) {
            if (d % i == 0) return false;
        }
        return true;
    }

    //给定三个点坐标，求三角形面积
    static double triangleSquare(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0;
    }

}
