package algorithm.排序算法;

public class ChooseSort {
    public static void main(String[] args) {
        sort(new int[]{4,2,2,1,3});
    }
    public static int[] sort(int[] nums){
        int index=0;
        int n=nums.length;
        while(index<n){
            int min=Integer.MAX_VALUE;
            int curIndex=-1;
            for(int i=index;i<n;i++){
                if(nums[i]<min){
                    min=nums[i];
                    curIndex=i;
                }
            }
            int temp=nums[index];
            nums[index]=nums[curIndex];
            nums[curIndex]=temp;
            index++;
        }
        return nums;
    }
}
