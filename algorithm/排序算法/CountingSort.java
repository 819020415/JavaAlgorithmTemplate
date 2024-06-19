package algorithm.排序算法;

//no good
public class CountingSort {
    /**
     * 计数排序
     * 时间复杂度O(N+W) N数组长度，W数组值域
     *
     * @param nums 待排序数组
     * @param len  数组值域
     * @return 排序后数组
     */
    public int[] sort(int[] nums, int len) {
        int[] c = new int[len];
        int n = nums.length;
        int[] b = new int[n];
        for (int i = 0; i < n; i++) c[nums[i]]++;
        for (int i = 1; i < len; i++) c[i] += c[i - 1];
        for (int i = n - 1; i >= 0; i--) b[c[nums[i]]--] = nums[i];
        return b;

    }
}
