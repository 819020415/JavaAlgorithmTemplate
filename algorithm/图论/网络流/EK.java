package algorithm.图论.网络流;

import java.io.*;
import java.util.*;

public class EK {
    /**
     * 网络流 最大流 EK 算法
     * <p>
     * 概念：
     * 网络：一个有向图，其中有两个特殊节点：源点 S 和汇点 T
     * 容量：c(x,y)
     * 流量：f(x,y)
     * 剩余流量：c(x,y) - f(x,y)
     * <p>
     * 可行流应满足：
     * 1.容量限制：0 <= f(x,y) <= c(x,y)
     * 2.流量守恒：∑ f(u,x) = ∑ f(x,v) ,x ≠ S && x ≠ T （流入 x 的流量 = 从 x 流出的流量）
     * <p>
     * ∑ f(S,v) 称为整个网络的流量
     * <p>
     * 最大流：从源点流向汇点的最大流量
     * 增广路：一条从源点到汇点的所有边的剩余容量 >= 0 的路径
     * 残留网：由网络中所有节点和剩余容量 > 0 的边构成的子图，这里的边包括有向边和其反向边
     * 建图时每条有向边 (x,y) 都构建一条反向边 (y,x),初始容量 c(y,x) = 0.
     * 构建反向边的目的是提供一个 “退流管道” ，一旦前面的增广路堵死可行流，可以通过 “退流管道”
     * 退流，提供了 "反悔机制"
     */
    static class Edges {
        int u, v, c, i;//{v,c,i} 出边，当前边的容量，当前边的编号

        public Edges(int u, int v, int c, int i) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.i = i;
        }
    }


    static int n, m, S, T;//点数 边数 源点 汇点
    static long[] maxFlow;//maxFlow[v] 存 S -> v 的路径上的流量上限
    static int[] prev;//存点的前驱边
    static List<Edges>[] ed;
    static int index;//编号
    static Map<Integer, Edges> map;//用于获取反边

    static void add(int u, int v, int c) {
        Edges e = new Edges(u, v, c, ++index);
        ed[u].add(e);
        map.put(index, e);
    }

    static boolean bfs() {//找增广路
        Arrays.fill(maxFlow, 0);
        maxFlow[S] = Long.MAX_VALUE;
        Queue<Integer> q = new LinkedList<>();
        q.offer(S);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edges t : ed[u]) {
                int v = t.v, c = t.c, idx = t.i;
                if (maxFlow[v] == 0 && c > 0) {
                    maxFlow[v] = Math.min(maxFlow[u], c);
                    prev[v] = idx;
                    q.offer(v);
                    if (v == T) return true;
                }
            }
        }
        return false;
    }

    static long EK() {//累加可行流
        long flow = 0;
        while (bfs()) {
            int v = T;
            while (v != S) {//更新残留网
                int i = prev[v];
                Edges e = map.get(i);
                e.c -= maxFlow[T];
                Edges e1 = map.get(i ^ 1);//取反边
                e1.c += maxFlow[T];
                v = e1.v;
            }
            flow += maxFlow[T];
        }
        return flow;
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        index = 1;//边的编号从 2 开始，2 3 为一对，4 5 为一对...
        n = sc.nextInt();
        m = sc.nextInt();
        S = sc.nextInt();
        T = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        map = new HashMap<>();
        maxFlow = new long[n + 1];
        prev = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), c = sc.nextInt();
            add(u, v, c);
            add(v, u, 0);
        }
        out.println(EK());
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
            double[] array = new double[size];
            for (int i = 0; i < size; i++) {
                array[i] = readDouble();
            }
            return array;
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

        public double readDouble() {
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

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}
