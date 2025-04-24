package algorithm.质数筛;


public class MillerRabin {
    //只能检验 int
    // 检查给定数是否为素数
    public static boolean isPrime(int n) {
        // 边界情况处理
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;


        // Miller-Rabin 基底
        //long[] bases = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};
        long[] bases = {2, 7, 61};

        // 分解 n-1 = d * 2^s
        int d = n - 1;
        int s = 0;
        while (d % 2 == 0) {
            d /= 2;
            s++;
        }

        // 执行 Miller-Rabin 测试
        for (long base : bases) {
            long a = base;
            if (a >= n) continue; // 跳过不必要的基底
            if (!millerTest(a, n, d, s)) {
                return false; // 如果某个基底失败，立即返回非素数
            }
        }

        return true; // 所有基底都通过，判断为素数
    }

    // 单次 Miller-Rabin 测试
    private static boolean millerTest(long a, int n, int d, int s) {
        //  BigInteger x = a.modPow(d, n); // 计算 a^d % n
        long x = pow(a, d, n);
        if (x == 1 || x == n - 1) return true;
        for (int r = 1; r < s; r++) {
            x = x * x % n;
            if (x == n - 1) return true;// 如果 x 回到 n-1，继续测试
        }
        return false; // 未通过测试，返回非素数
    }

    static long pow(long x, long n, int mod) {
        if (n == 0) return 1;
        if (n % 2 == 0) return pow(x * x % mod, n / 2, mod);
        else return pow(x * x % mod, n / 2, mod) * x % mod;
    }

    //判断数字是否为质数
    static boolean isPrime1(int d) {
        if (d == 1) return false;
        for (int i = 2; i * i <= d; i++) {
            if (d % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 1e9; i++) {
            if(i % 10000000 == 0) System.out.println(i / 10000000);
            if (isPrime1(i) != isPrime(i)) {
                System.out.println(i);
                break;
            }
        }

    }
}
