package algorithm.排序算法;

public class InsertSort {
    public static void main(String[] args) {
        sort(new int[]{4, 2, 2, 1, 3});
    }

    public static int[] sort(int[] nums) {
        int index = 1;
        int n = nums.length;
        while (index < n) {
            for (int i = index; i > 0; i--) {
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
