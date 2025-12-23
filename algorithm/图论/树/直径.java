package algorithm.图论.树;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @date 2024/6/30 11:32
 * 求树的直径，两边 bfs
 **/
public class 直径 {
    public static int[] diameter() {
        int[] t = bfs(1);//此时 t[0] = 直径的一个端点
        int a = t[0];
        t = bfs(t[0]);//此时 t[0] = 直径的另一个端点，t[1] = 直径的值
        int b = t[0], val = t[1];
        //System.out.println(a + " " + b + " " + val);
        return new int[]{a, b, val};//直径的两个端点 a,b 以及直径长度 val
    }

    static int n;
    static List<Integer>[] ed;


    //树的直径
    static int[] bfs(int s) {
        int max = -1, idx = -1;
        boolean[] vis = new boolean[n + 1];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{s, 0});
        while (!q.isEmpty()) {
            int[] t = q.poll();
            int u = t[0], w = t[1];
            if (vis[u]) continue;
            vis[u] = true;
            if (w > max) {
                max = w;
                idx = u;
            }
            for (int v : ed[u]) {
                q.offer(new int[]{v, w + 1});
            }
        }
        return new int[]{idx, max};//返回距离 s 最远的点以及具体的距离
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
