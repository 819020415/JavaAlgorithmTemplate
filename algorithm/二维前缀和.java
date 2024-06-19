package algorithm;

public class 二维前缀和 {
    //二维 前缀和
    //洛谷 P2280
    long[][] ps;
    int m, n;

    void init(int[][] a) {
        m = a.length;
        n = a[0].length;
        ps = new long[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                ps[i][j] = ps[i][j - 1] + ps[i - 1][j] - ps[i - 1][j - 1] + a[i - 1][j - 1];
    }
    //(x1,y1) 左上角下标 (x2,y2) 右下角下标
    long query(int x1, int y1, int x2, int y2) {
        return ps[x2 + 1][y2 + 1] - ps[x1][y2 + 1] - ps[x2 + 1][y1] + ps[x1][y1];
    }


}
