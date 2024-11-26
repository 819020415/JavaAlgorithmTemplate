package algorithm.math;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Date: 2024/11/26 11:26
 * 佩尔方程
 * x^2 - d*y^2 = 1
 * 其中 d 不是完全平方数
 * 1.求最小整数解 (x1,y1)
 * (1) 求 根号 d 的连分数周期
 * (2)找到连分数的收敛分数： 每一段连分数的收敛分数可以写成： P[k] / Q[k]
 * 递推式
 * P[k] = a[k]*P[k-1] + P[k-2]
 * Q[k] = a[k]*Q[k-1] + Q[k-2]
 * 初始值
 * P[-2] = 0, P[-1] = 1, Q[-2] = 1, Q[-1] = 0
 * <p>
 * 2.求方程的所有解
 * 递推式
 * * x[n+1] = x1*x[n] + d*y1*y[n]
 * * y[n+1] = y1*x[n] + x1*y[n]
 * * (x1,y1) 是满足方程的最小正整数解
 */
public class 佩尔方程 {

    public static void main(String[] args) {
        int d = sc.nextInt();
        g(d);
        f(d);
        getSolution(x1,y1,d);
        out.close();
    }

    static int cnt = 0;

    //2.求方程的所有解
    static void getSolution(BigInteger x, BigInteger y, int d) {
        System.out.println(x +" " + y);
        BigInteger x2 = new BigInteger(String.valueOf(x1));
        x2 = x2.multiply(x);
        BigInteger temp = new BigInteger(String.valueOf(d));
        temp = temp.multiply(y);
        temp = temp.multiply(y1);
        x2 = x2.add(temp);

        BigInteger y2 = new BigInteger(String.valueOf(y1));
        y2 = y2.multiply(x);
        temp = new BigInteger(String.valueOf(x1));
        temp = temp.multiply(y);
        y2 = y2.add(temp);
        cnt++;
        if (cnt < 20) getSolution(x2, y2, d);

    }

    static BigInteger x1, y1;

    //通过不断尝试连分数来求解佩尔方程
    static void f(int d) {
        // p / q 的递推，不是  x^2 - D*y^2 = 1 中 x,y 的递推
        /*long p0 = 1, q0 = 0;
        long p = (long) Math.sqrt(d), q = 1;
        int idx = 0;
        int sz = list.size();
        while (true) {
            if (p * p - d * q * q == 1) return p;
            long a = list.get(idx) * p + p0;
            long b = list.get(idx) * q + q0;
            p0 = p;
            q0 = q;
            p = a;
            q = b;
            idx = (idx + 1) % sz;
        }*/

        BigInteger p0 = new BigInteger(String.valueOf(1));
        BigInteger q0 = new BigInteger(String.valueOf(0));
        BigInteger p = new BigInteger(String.valueOf((long) Math.sqrt(d)));
        BigInteger q = new BigInteger(String.valueOf(1));
        int idx = 0;
        int sz = list.size();
        while (true) {
            BigInteger v = new BigInteger(p.toByteArray());
            v = v.multiply(p);
            BigInteger v1 = new BigInteger(String.valueOf(d));
            v1 = v1.multiply(q);
            v1 = v1.multiply(q);
            v = v.subtract(v1);
            if (v.toString().equals("1")) {
                x1 = p;
                y1 = q;
                return;
            }
            BigInteger a = new BigInteger(p0.toByteArray());
            BigInteger temp = new BigInteger(p.toByteArray());
            temp = temp.multiply(BigInteger.valueOf(list.get(idx)));
            a = a.add(temp);
            BigInteger b = new BigInteger(q0.toByteArray());
            temp = new BigInteger(q.toByteArray());
            temp = temp.multiply(BigInteger.valueOf(list.get(idx)));
            b = b.add(temp);
            p0 = p;
            q0 = q;
            p = a;
            q = b;
            idx = (idx + 1) % sz;
        }

    }

    //计算连分数的周期
    public static void g(int i) {
        set.clear();
        ok = false;
        list.clear();
        int v = (int) Math.sqrt(i);
        f(i, -v, 1);
    }

    static List<Integer> list = new ArrayList<>();
    static Set<Long> set = new HashSet<>();
    static boolean ok;

    //(√a + b) / c
    static void f(int a, int b, int c) {
        if (ok) return;
        long h = hash(b, c);
        if (set.contains(h)) {
            ok = true;
            return;
        }
        set.add(h);
        int d = a - b * b;
        int f = -b;
        int gcd = (int) getGCD(c, d);
        d /= gcd;
        int z = (int) Math.sqrt(a);
        z += f;
        int v = z / d;
        list.add(v);
        f -= v * d;
        f(a, f, d);
    }

    static long hash(int b, int c) {
        return (long) b * (1 << 20) + c;
    }

    //求 a 和 b 的最大公约数,做法：辗转相除法
    static long getGCD(long a, long b) {
        if (b == 0) return a;
        if (a % b == 0) return b;
        return getGCD(b, a % b);
    }


    static Kattio sc = new Kattio();
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    static class Kattio {
        static BufferedReader r;
        static StringTokenizer st;

        public Kattio() {
            r = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreTokens()) {
                    st = new StringTokenizer(r.readLine());
                }
                return st.nextToken();
            } catch (Exception e) {
                return null;
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}


