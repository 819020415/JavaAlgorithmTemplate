package algorithm;

/**
 * Date: 2024/12/1 16:18
 * 区间加等差数列，假设在 [l,r] 区间 + [a0,a0+d,...,a0+(r-l)*d]
 * 等价于在它的差分数组 diff :
 * sup[l] += a0
 * diff[l]~diff[r] += d
 * sup[r+1] -= a0+(r-l)*d
 * 而 diff[l]~diff[r] += d 可以通过另一个差分数组做
 * 等价于 d2[l] += d,d2[r+1] -= d
 */
public class 区间加等差数列 {
    public static void init(int n) {
        diff = new long[n + 5];
        sup = new long[n + 5];
        d2 = new long[n + 5];
    }

    static int n;
    static long[] diff, d2;
    static long[] sup;//用于存储边界的值，这个值不能放在 diff 数组里，不然还原的时候就出错了

    //在 [l,r] 区间 + [a0,a0+d,...,a0+(r-l)*d]
    static void add(int l, int r, int a0, int d) {
        sup[l] += a0;
        sup[r + 1] -= a0 + (long) (r - l) * d;
        d2[l + 1] += d;
        d2[r + 1] -= d;
    }

    static long[] getRes() {
        //先通过 d2 还原 diff
        diff[0] += d2[0];
        for (int i = 1; i < n; i++) {
            diff[i] += diff[i - 1] + d2[i];
        }
        //再通过 diff 还原原数组
        long[] a = new long[n];
        a[0] = diff[0] + sup[0];
        for (int i = 1; i < n; i++) {
            a[i] = a[i - 1] + diff[i] + sup[i];
        }
        return a;


    }

}


