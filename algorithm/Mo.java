package algorithm;

import java.util.Arrays;

/**
 * Date: 2025/5/30 15:09
 * 莫队
 */
public class Mo {


    int[] cnt, ans;
    int sum;

    /**
     *
     * @param k a 数组值域
     * @param a a 数组
     * @param q 查询数组 [l,r,idx]
     */
    int[] calc(int k, int[] a, int[][] q) {
        cnt = new int[k + 1];
        int m = q.length;
        int[] ans = new int[m];
        int n = a.length;
        int sqrt = (int) Math.sqrt(n);
        Arrays.sort(q, (o1, o2) -> {
            int v1 = o1[0] / sqrt, v2 = o2[0] / sqrt;
            if (v1 != v2) return v1 - v2;
            if (v1 % 2 == 0) return o1[1] - o2[1];
            else return o2[1] - o1[1];
        });
        int l = 0, r = -1;
        for (int i = 0; i < m; i++) {
            while (l > q[i][0]) add(a[--l]);//左扩展
            while (r < q[i][1]) add(a[++r]);//右扩展
            while (l < q[i][0]) del(a[l++]);//左删除
            while (r > q[i][1]) del(a[r--]);//右删除
            ans[q[i][2]] = sum;
        }
        return ans;
    }

    //add del 函数需要根据题目要求自行修改
    void add(int x) {
        if (cnt[x] == 0) sum++;
        cnt[x]++;

    }

    void del(int x) {
        cnt[x]--;
        if (cnt[x] == 0) sum--;
    }

}


