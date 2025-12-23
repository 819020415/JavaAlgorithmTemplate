package algorithm.线段树;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Date: 2025/5/9 15:58
 * 动态开点线段树 数组实现版
 * 功能：区间加，区间求和
 */
public class DynamicSegmentTree {

    //ls[i] = 节点 i 的左儿子 rs[i] = 节点 i 的右儿子
    static int[] ls, rs, sum, tag;

    static void init(int m) {
        //需要的空间 = 2 * m * log(n) , n 是值域
        int len = m * 60;//m 是查询次数，30 = log n ,n 是值域，当 n = 1e9 时 log(n) = 30
        ls = new int[len];
        rs = new int[len];
        sum = new int[len];
        tag = new int[len];
    }

   /* //[x,y] 表示当前节点的区间，[l,r] 表示待修改区间 , 将区间 [l,r] 的值修改为 k
    static void change1(int tr, int l, int r, int x, int y, int k) {
        if (l <= x && y <= r) {
            tag[tr] = k;
            sum[tr] = k * (y - x + 1);
            return;
        }
        pushDown(tr, x, y);
        int mid = x + y >> 1;
        if (l <= mid) change1(ls[tr], l, r, x, mid, k);
        if (mid < r) change1(rs[tr], l, r, mid +1 , y, k);
        pushUp(tr);
    }*/
    //区间 [l,r] += k
    static void change(int tr, int l, int r, int x, int y, int k) {
        if (l <= x && y <= r) {
            tag[tr] += k;
            sum[tr] += k * (y - x + 1);
            return;
        }
        pushDown(tr, x, y);
        int mid = x + y >> 1;
        if (l <= mid) change(ls[tr], l, r, x, mid, k);
        if (mid < r) change(rs[tr], l, r, mid +1 , y, k);
        pushUp(tr);
    }

    //区间 [l,r] 的和
    static long sum(int tr,int l,int r,int x,int y) {
        if(l <= x && y <= r) {
            return sum[tr];
        }
        pushDown(tr, x, y);
        int mid = x + y >> 1;
        long s = 0;
        if(l <= mid) s+= sum(ls[tr], l, r, x, mid);
        if(mid < r) s += sum(rs[tr], l, r, mid +1 , y);
        pushUp(tr);
        return s;
    }


    static void pushDown(int tr, int l, int r) {
        if (ls[tr] == 0) ls[tr] = ++tot;
        if (rs[tr] == 0) rs[tr] = ++tot;
        if (tag[tr] == 0) return;
        int mid = l + r >> 1;
        sum[ls[tr]] = (mid + 1 - l) * tag[tr];
        sum[rs[tr]] = (r - mid) * tag[tr];
        tag[ls[tr]] = tag[rs[tr]] = tag[tr];
        tag[tr] = 0;
    }

    static void pushUp(int tr) {
        sum[tr] = sum[ls[tr]] + sum[rs[tr]];
    }

    static int root = 0, tot = 1;



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


