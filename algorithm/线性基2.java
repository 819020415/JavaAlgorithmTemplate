package algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Date: 2025/7/15 16:47
 *
 * 异或线性基，支持插入元素，求第 k 小 XOR
 */
public class 线性基2 {

    static final int MAXB = 61;        // 支持到 2^60
    static long[] basis = new long[MAXB];
    static int rank = 0;

    /**
     * 向线性基里插入 x（在线增量）：
     * 返回 true 表示 x 线性无关，秩 +1；false 表示 x 冗余。
     */
    static boolean insertBasis(long x) {
        // 1. 用现有 basis 完全消元 x
        for (int b = MAXB - 1; b >= 0; --b) {
            if (((x >> b) & 1) == 1 && basis[b] != 0) {
                x ^= basis[b];
            }
        }
        // 如果消成了 0，就是冗余的
        if (x == 0) return false;

        // 2. 找到新的 pivot 位 b
        int b = 63 - Long.numberOfLeadingZeros(x);  // x.bitLength()-1

        // 3. 把新 pivot 位 b 在其他所有基向量里也消去
        for (int i = 0; i < MAXB; ++i) {
            if (i != b && ((basis[i] >> b) & 1) == 1) {
                basis[i] ^= x;
            }
        }

        // 4. 把 x 存入 basis[b]
        basis[b] = x;
        ++rank;
        return true;
    }

    /**
     * 拿到“从大到小”（按 pivot 位降序）的一维基向量列表
     */
    static List<Long> getSortedBasis() {
        List<Long> lb = new ArrayList<>();
        for (int b = MAXB - 1; b >= 0; --b) {
            if (basis[b] != 0) lb.add(basis[b]);
        }
        return lb;
    }

    //求第 k 小 xor
    static long kthSmallestXOR(long k,List<Long>a ) {
        k--;
        if (k >= (1L << rank)) {
            return -1;//不存在
        }
        long ans = 0;
        for (int j = 0; j < rank; j++) {
            if ((k >> j & 1) == 1) {
                ans ^= a.get(rank - j - 1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {

            int T = sc.nextInt();
            if (T == 1) {
                long x = sc.nextLong();
                insertBasis(x);
            } else {
                long k = sc.nextLong();
                if (k > 1L << rank) {
                    out.println(-1);
                    continue;
                }
                long l = kthSmallestXOR(k,getSortedBasis());
                out.println(l);


            }
        }
        out.close();
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


