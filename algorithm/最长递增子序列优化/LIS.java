package algorithm.最长递增子序列优化;

import java.util.*;

public class LIS {
    //最长递增子序列 LIS 二分优化版
    static int lengthOfLIS(int[] nums) {
        //greedy + binary search
        int n = nums.length;
        int[] d = new int[n + 1];//d[i]表示长度为 i 的子序列的第 i 个元素最小是 d[i]
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = (int) -1e9;
        d[1] = nums[0];//长度为1的子序列的最小的最大值是 nums[0]
        //d 数组总是有序的，保存的是当前最优的最长子序列
        //最优的意思是对于所有长度为最长的子序列，最优的最长子序列每一位上的数字都是尽可能小
        int[] dp = new int[n];
        dp[0] = 1;
        int ans = 1;
        for (int i = 1; i < n; i++) {
            int ret = bs(d, nums[i], n + 1);
            dp[i] = ret + 1;
            d[ret + 1] = Math.min(d[ret + 1], nums[i]);
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    static int bs(int[] d, int num, int n) {
        int l = 0, r = n;//[]
        int ret = 0;
        while (l <= r) {
            int mid = l + r >> 1;
            if (d[mid] <= num) {//注意，这里是 <= 就是求的最长非递减子序列， < 就是求的最长递增子序列
                ret = mid;
                l = mid + 1;
            } else r = mid - 1;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] a={100,1,2};
        System.out.println(lengthOfLIS(a));
    }
}
