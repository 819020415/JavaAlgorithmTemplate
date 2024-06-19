package algorithm;

public class 二维差分 {
    /**
     * 对于一个数组 a,它的差分数组 b 定义为
     * b[0] = a[0] ,b[i] = a[i] - a[i - 1]
     * b 是 a 的差分数组，a 是 b 的前缀和数组，差分和前缀和是一对互逆运算
     * <p>
     * 差分思想：
     * 当要在一个数组的 [l,r] 区间 + v 时，等价于在它的差分数组上进行
     * b[l] += v, b[r + 1] -= v 操作
     * 这样单次操作的时间复杂度就降到 O(1) 了
     * <p>
     * 二维差分：洛谷 P3397
     * 注意这种差分，默认矩阵下标从 1 开始，左上角下标为 (1,1)
     * 如果题目下标从 0 开始需要转化一下
     * <p>
     * 如果同时涉及矩阵修改以及矩阵查询，需要使用二维线段树
     */

    static int m, n;
    static int[][] diff;

    static void init(int[][] a) {
        m = a.length;
        n = a[0].length;
        diff = new int[m + 2][n + 2];
    }

    static void change(int x1, int y1, int x2, int y2, int v) {
        diff[x1][y1] += v;
        diff[x1][y2 + 1] -= v;
        diff[x2 + 1][y1] -= v;
        diff[x2 + 1][y2 + 1] += v;
    }

    static void recover() {
        //有用的数据 最后一行为 m,最有一列为 n
        //第 m + 1 行以及第 n + 1 列无用
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];

    }


}
