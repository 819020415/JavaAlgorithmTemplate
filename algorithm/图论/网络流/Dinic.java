package algorithm.图论.网络流;

import java.io.*;
import java.util.*;

public class Dinic {


    /**
     * 最大流 Dinic 算法
     * <p>
     * 优化：
     * 1.搜索顺序优化（分层限制搜索深度）
     * 2.当前弧优化（剪枝）
     * 3.剩余流量优化（剪枝）
     * 4.残枝优化（剪枝）
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
    static int[] curr;//存点的当前出边
    static int[] d;//存点所在图层
    static List<Edges>[] ed;
    static int index = 1;//边的编号从 2 开始，2 3 为一对，4 5 为一对...
    static Map<Integer, Edges> map;//用于获取反边

    static void add(int u, int v, int c) {
        Edges e = new Edges(u, v, c, ++index);
        ed[u].add(e);
        map.put(index, e);
    }

    static boolean bfs() {//对点分层，找增广路
        Arrays.fill(d, 0);
        Queue<Integer> q = new LinkedList<>();
        q.offer(S);
        d[S] = 1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edges e : ed[u]) {
                int v = e.v, c = e.c, idx = e.i;
                if (d[v] == 0 && c > 0) {//如果当前邻点没有访问过，并且当前边的剩余流量大于 0
                    d[v] = d[u] + 1;
                    q.offer(v);
                    if (v == T) return true;
                }
            }
        }
        return false;
    }

    static long dfs(int u, long maxFlow) {//多路增广
        if (u == T) return maxFlow;
        long sum = 0;
        for (int i = curr[u]; i < ed[u].size(); i++) {
            curr[u] = i;//当前弧优化
            Edges e = ed[u].get(i);
            int v = e.v, c = e.c, idx = e.i;
            if (d[v] == d[u] + 1 && c > 0) {//bfs 分层保证 dfs 路径不会太长
                long f = dfs(v, Math.min(maxFlow, c));
                e.c -= f;
                Edges e1 = map.get(idx ^ 1);//获取反边
                e1.c += f;//更新残留网
                sum += f;
                maxFlow -= f;//减少 u 的剩余流量
                if (maxFlow == 0) break;//剩余流量优化
            }
        }
        if (sum == 0) d[u] = 0;//残枝优化
        return sum;
    }

    static long Dinic() {
        long flow = 0;
        while (bfs()) {
            Arrays.fill(curr, 0);
            flow += dfs(S, Long.MAX_VALUE);
        }
        return flow;
    }


    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        index = 1;
        n = sc.nextInt();
        m = sc.nextInt();
        S = sc.nextInt();
        T = sc.nextInt();
        ed = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            ed[i] = new ArrayList<>();
        }
        map = new HashMap<>();
        curr = new int[n + 1];
        d = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), c = sc.nextInt();
            add(u, v, c);
            add(v, u, 0);
        }


        out.println((Dinic()));
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
