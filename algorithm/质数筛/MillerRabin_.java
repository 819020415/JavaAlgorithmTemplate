package algorithm.质数筛;

import java.math.BigInteger;

//用于判断 long 是否为质数
public class MillerRabin_ {
    // 检查给定数是否为素数
    public static boolean isPrime(long v) {
        BigInteger n = BigInteger.valueOf(v);
        // 边界情况处理
        if (n.compareTo(BigInteger.TWO) < 0) return false;
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) return true;
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        // Miller-Rabin 基底
        long[] bases = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};

        // 分解 n-1 = d * 2^s
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            s++;
        }

        // 执行 Miller-Rabin 测试
        for (long base : bases) {
            BigInteger a = BigInteger.valueOf(base);
            if (a.compareTo(n) >= 0) continue; // 跳过不必要的基底
            if (!millerTest(a, n, d, s)) {
                return false; // 如果某个基底失败，立即返回非素数
            }
        }

        return true; // 所有基底都通过，判断为素数
    }

    // 单次 Miller-Rabin 测试
    private static boolean millerTest(BigInteger a, BigInteger n, BigInteger d, int s) {
        BigInteger x = a.modPow(d, n); // 计算 a^d % n
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) return true;

        for (int r = 1; r < s; r++) {
            x = x.multiply(x).mod(n); // 平方
            if (x.equals(n.subtract(BigInteger.ONE))) return true; // 如果 x 回到 n-1，继续测试
        }

        return false; // 未通过测试，返回非素数
    }

    public static void main(String[] args) {
        System.out.println(isPrime(13082761331670030L));
    }

}
