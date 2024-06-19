package algorithm;

public class 整除分块 {
    /**
     * 问题：求 ∑(n/i) 下取整的和
     * 性质 1: 分块的块数 <= 2 * sqrt(n) 下取整
     * 性质 2: i 所在块的右端点为 n / (n / i) ，两次除法都是下取整
     * <p>
     * 如果要求 ∑ f(i) * (n/i) 的值，可以先预处理出来 f 的前缀和
     * 每个块的贡献 = (s[r+1] - s[l]) * (n/r) ; l,r 为当前块的左右端点
     *
     */
    public static void main(String[] args) {
        int n = 21;
        long ans = 0;
        int r;
        //求  ∑(n/i), i ∈ [1,n]
        for (int l = 1; l <= n; l = r + 1) {
            r = (n / (n / l));//当前块的右端点
            ans += (r - l + 1) * (n / r);
        }
        System.out.println(ans);
    }

}
