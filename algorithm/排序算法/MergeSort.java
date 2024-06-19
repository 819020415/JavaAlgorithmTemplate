package algorithm.排序算法;

/**
 * 归并排序,可用于查找逆序对数量
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] a={3,2,4,1};
        merge(a);
    }


    static int[] b;
    static  int[] a;
    static long ans;

    static int[] merge(int[] nums) {
        int n = nums.length;
        a = nums;
        b = new int[n];
        mergeSort(0, n - 1);
        return b;
    }



    static void mergeSort(int l, int r) {
        if (l >= r) return;
        int mid = (l + r) / 2;
        mergeSort(l, mid);
        mergeSort(mid + 1, r);
        //合并两个数组，i指针指向左数组起点，j指针指向右数组起点，k指针指向b数组起点
        //排序
        int i = l, j = mid + 1, k = l;
        while (i <= mid && j <= r) {
            if (a[i] <= a[j]) b[k++] = a[i++];
            else {
                b[k++] = a[j++];
                // ans += mid - i + 1;//使用归并排序求逆序对
            }
        }
        while (i <= mid) b[k++] = a[i++];
        while (j <= r) b[k++] = a[j++];

        for (int m = l; m <= r; m++) a[m] = b[m];
    }


}
