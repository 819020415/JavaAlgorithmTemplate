package algorithm;

/**
 * ST 表（Sparse Table 稀疏表），主要用来解决 RMQ（区间最大值 / 最小值 查询） 问题，主要应用倍增思想
 * 时间复杂度：预处理 O(NlogN) ,查询 O(1)
 * 缺点：不支持修改操作
 */
public class RMQ {
    public RMQ(int[] a) {
        init(a);
    }

    int[][] f;

    void init(int[] a) {
        int n = a.length;
        int m = log2(n) + 1;
        f = new int[n][m];//f[i][j] = 以 i 为起点，长度为 2 ^ j 的区间的最大值
        for (int i = 0; i < n; i++) f[i][0] = a[i];
        for (int j = 1; j < m; j++) //枚举区间长度
            for (int i = 0; i + (1 << j) - 1 < n; i++) //枚举起点
                f[i][j] = Math.max(f[i][j - 1], f[i + (1 << (j - 1))][j - 1]);
    }

    //求区间 [l,r] 最大值
    int query(int l, int r) {
        int k = log2(r - l + 1);
        return Math.max(f[l][k], f[r - (1 << k) + 1][k]);
    }

    //求 log2(X)
    int log2(long x) {
        return (int) (Math.log(x) / Math.log(2));
    }
}


