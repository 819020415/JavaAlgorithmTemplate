package algorithm.DP;

public class 单调队列优化DP {
    /**
     * 单调队列优化 dp：
     * 当转移方程刑如：dp[i] = min(dp[j] + a[i] + b[j]) , 0 <= j < i
     * 可以使用单调队列
     * 单调队列：队尾进队出队，队头出队（维护子序列的单调性）
     * 1.队尾出队的条件：队列不为空且新元素更优，队中就元素队尾出队
     * 2.队尾入队：无条件
     * 3.队头出队的条件：队头元素滑出了窗口
     * 注意：队列中存储元素的下标，方便判断队头出队
     * <p>
     * 应用：数组求长度为 k 的所有子数组的最值；
     * 多重背包优化
     *
     * 单调队列 例题：
     * Cf 372c、487b、1237d、1077f2、1918d
     * Luogu 1886、3957、1419、1725、2254、2569
     * loj 10175、10176、10177、10178、10180、10181、10182、10183、10186
     */

    public static int[] f(int[] a, int k) {//k 窗口长度
        //单调队列,求子数组最大值
        int h = 0, t = -1;//头指针，尾指针，这种写法，当 h == t 时队列中有一个元素
        int n = a.length;
        int[] q = new int[n];//单调队列
        //q保存的都是下标，而不是值
        //要求的是窗口的最大值，q 按照 a[i] 降序排序，队头元素值最大
        int[] ans = new int[n - k + 1];
        for (int i = 0; i < n; i++) {
            //如果要求窗口的最小值，只需要把 a[q[t]] <= a[i] 改成 a[q[t]] >= a[i] 即可
            while (h <= t && a[q[t]] <= a[i]) t--;//队尾出队(队列不为空 且当前元素更优 )
            q[++t] = i;//队尾入队，存储下标，方便队头出队
            if (q[h] < i - k + 1) h++;//队头出队，队头元素下标已经不在窗口中，就要出队，i - k + 1 是当前窗口的最小下标（左端点下标）
            if (i >= k - 1) ans[i - k + 1] = a[q[h]];
        }
        return ans;
    }

    /**
     * 单调队列优化 dp：
     * 当转移方程刑如：dp[i] = min(dp[j] + a[i] + b[j]) , 0 <= j < i
     * 可以使用单调队列
     * 多重背包优化
     * @param items 物品：体积 价值 数量
     * @param m     背包容量
     *              时间复杂度：O(m*n) n 物品个数，m 背包容量
     */
    static void backpack(int[][] items, int m) {
        int n = items.length;
        int[] f = new int[m + 1];
        int[] q = new int[m];
        for (int i = 1; i <= n; i++) {//枚举物品
            int[] g = f.clone();
            int v = items[i][0], val = items[i][1], s = items[i][2];//当前物品的体积，价值，数量
            for (int j = 0; j < v; j++) {//将背包容量拆分成 v 类
                int h = 0, t = -1;
                for (int k = j; k <= m; k += v) {//对每类物品做单调队列
                    //q[h] 不在窗口 [k - s * v,k - v] 内，队头出队
                    if (h <= t && q[h] < k - s * v) h++;
                    if (h <= t) f[k] = Math.max(g[k], g[q[h]] + (k - q[h]) / v * val);//g 数组保存的是旧值，用旧值更新
                    while (h <= t && g[k] >= g[q[t]] + (k - q[t]) / v * val) t--;//当前元素更优，队尾出队
                    q[++t] = k;//下标队尾入队
                }
            }
        }
        System.out.println(f[m]);
    }


}
