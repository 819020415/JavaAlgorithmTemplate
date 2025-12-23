package algorithm.线段树;

import java.util.Scanner;

public class FenwickTree {
    /**
     * 树状数组，支持单点修改以及查询前缀和，
     * 经过一些改造可以支持区间修改以及区间查询
     * 空间复杂度 O(N)
     * 查询修改时间复杂度 O(logN)
     */

    static int N;
    static int[] s;

    //返回 x 的低位 2 次幂
    static int lowbit(int x) {
        return x & -x;
    }

    //单点修改，向后修
    static void change(int x, int k) {
        while (x <= N) {
            s[x] += k;
            x += lowbit(x);
        }
    }

    //查询前缀和，向前查
    static int query(int x) {
        int ret = 0;
        while (x > 0) {
            ret += s[x];
            x -= lowbit(x);
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        s = new int[N + 1];
        int m = sc.nextInt();
        //数组下标从 1 开始
        for (int i = 1; i <= N; i++) {
            int v = sc.nextInt();
            change(i, v);
        }
        for (int i = 0; i < m; i++) {
            int t = sc.nextInt(), x = sc.nextInt(), y = sc.nextInt();
            if (t == 1) change(x, y);//a[x] += y
            else {
                System.out.println(query(y) - query(x - 1));//sum[x ~ y]
            }
        }
    }

}
