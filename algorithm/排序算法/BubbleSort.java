package algorithm.排序算法;

public class BubbleSort {
    public static void main(String[] args) {
        sort(new int[]{4, 5, 2, 3, 6, 1, 2});
    }

    public static int[] sort(int[] nums) {
        int index = 0;
        int n = nums.length;
        while (index < n) {
            for (int i = n - 1; i > index; i--) {
                if (nums[i] < nums[i - 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i - 1];
                    nums[i - 1] = temp;
                }
            }
            index++;
        }
        return nums;

    }
}
