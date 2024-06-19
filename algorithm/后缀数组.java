package algorithm;

import java.io.*;
import java.util.*;

public class 后缀数组 {
    /**
     * 一个长度为 N 的字符串一共有 N 个后缀，
     * 后缀的编号是后缀在原串中的起始位置，
     * 把 N 个后缀按照字典序升序排序
     * <p>
     * 后缀数组 sa[i]，sa[i] = j 表示按字典序排序后，排名第 i 的后缀的编号为 j
     * 名次数组 rk[i] 表示后缀 i 的排名
     * 有 rk[sa[i]] = sa[rk[i]] = i
     * 高度数组 height[i] = lcp(sa[i],sa[i - 1])
     * 第 i 名后缀和第 i - 1 名后缀的最长公共前缀的长度
     * 高度数组表示两个后缀的相似程度，
     * 排序后相邻的两个后缀相似度最高
     *
     * 洛谷 P3809
     */
    static int N = 1000010;
    static int n, m;//n 后缀个数，m 桶的个数
    static char[] s;
    static int[] x, y, c, sa, rk, height;

    //桶数组 x，辅助数组 y，计数数组 c
    static void get_sa() {

        for (int i = 1; i <= n; i++) c[x[i] = s[i]]++;
        for (int i = 1; i <= m; i++) {
            c[i] += c[i - 1];
        }
        for (int i = n; i > 0; i--) {
            sa[c[x[i]]--] = i;
        }
        for (int k = 1; k <= n; k <<= 1) {
            Arrays.fill(c, 0);
            for (int i = 1; i <= n; i++) y[i] = sa[i];
            for (int i = 1; i <= n; i++) c[x[y[i] + k]]++;
            for (int i = 1; i <= m; i++) c[i] += c[i - 1];
            for (int i = n; i > 0; i--) sa[c[x[y[i] + k]]--] = y[i];
            Arrays.fill(c, 0);
            for (int i = 1; i <= n; i++) y[i] = sa[i];
            for (int i = 1; i <= n; i++) c[x[y[i]]]++;
            for (int i = 1; i <= m; i++) c[i] += c[i - 1];
            for (int i = n; i > 0; i--) sa[c[x[y[i]]]--] = y[i];
            //把后缀放入桶数组
            for (int i = 1; i <= n; i++) y[i] = x[i];
            int i;
            for (i = 1, m = 0; i <= n; i++)
                if (y[sa[i]] == y[sa[i - 1]] &&
                        y[sa[i] + k] == y[sa[i - 1] + k]) x[sa[i]] = m;
                else x[sa[i]] = ++m;
            if (m == n) break;//已排好
        }

    }

    static void get_height() {
        int i, j, k;
        for (i = 1; i <= n; i++) rk[sa[i]] = i;
        for (i = 1, k = 0; i <= n; i++) { //枚举后缀i
            if (rk[i] == 1) continue;//第一名height为0
            if (k > 0) k--;//上一个后缀的height值减1
            j = sa[rk[i] - 1];//找出后缀i的前邻后缀j
            while (i + k <= n && j + k <= n && s[i + k] == s[j + k]) k++;
            height[rk[i]] = k;
        }
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        char[] z = sc.next().toCharArray();
        n = z.length;
        m = 122;
        s = new char[n + 1];
        for (int i = 1; i <= n; i++) s[i] = z[i - 1];
        for (int i = 1; i < n; i++) s[i] = z[i - 1];
        x = new int[N];
        y = new int[N];
        c = new int[N];
        sa = new int[N];
        rk = new int[N];
        height = new int[N];
        get_sa();
        get_height();
        for (int i = 1; i <= n; i++) out.print(sa[i] - 1 + " ");
        out.println();
        for (int i = 1; i <= n; i++) out.print(height[i] + " ");

        out.close();
    }


    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public OutputWriter println(long i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println(double i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println(String i) {
            writer.println(i);
            return this;
        }

        public OutputWriter println() {
            writer.println();
            return this;
        }

        public OutputWriter print(String i) {
            writer.print(i);
            return this;
        }

        public OutputWriter print(long i) {
            writer.print(i);
            return this;
        }

        public OutputWriter print(char i) {
            writer.print(i);
            return this;
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public double[] nextDoubleArray(int size) {
            double[] a = new double[size];
            for (int i = 0; i < size; i++) a[i] = nextDouble();
            return a;
        }

        public int[] nextIntArray(int size) {
            int[] a = new int[size];
            for (int i = 0; i < size; i++) a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int size) {
            long[] a = new long[size];
            for (int i = 0; i < size; i++) a[i] = nextLong();
            return a;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            return Integer.parseInt(this.next(), 10);
        }

        public long nextLong() {
            return Long.parseLong(this.next(), 10);
        }

        public double nextDouble() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new InputMismatchException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        private static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}
