package algorithm;

/**
 * Date: 2024/12/1 16:47
 * <p>
 * 区间 [l,r] + 1^2,2^2,...,(r-l+1)^2
 * d 数组上表现为 1,3,5,7,9...
 * d2数组上表现为   2,2,2,2...
 * <p>
 * sup[l] += 1
 * sup[r + 1] -= 2 * (r - l) + 1;
 * sup2[r + 1] -= (long) (r - l + 1) * (r - l + 1);
 * d3[l + 1] += 2
 * d3[r + 1] -= 2
 * 依次还原 d2,d,a 即可
 */
public class 区间加平方数列 {

    public static void init(int n) {
        d = new long[n + 5];
        d2 = new long[n + 5];
        d3 = new long[n + 5];
        sup = new long[n + 5];
        sup2 = new long[n + 5];
    }

    static int n;
    static long[] d, d2, d3, sup, sup2;

    static void add(int l, int r) {
        sup[l] += 1;
        sup[r + 1] -= 2 * (r - l) + 1;
        sup2[r + 1] -= (long) (r - l + 1) * (r - l + 1);
        d3[l + 1] += 2;
        d3[r + 1] -= 2;
    }

    static long[] getRes(int n) {
        d2[0] += d3[0];
        for (int i = 1; i < n; i++) {
            d2[i] += d2[i - 1] + d3[i];
        }
        for (int i = 0; i < n; i++) {
            d2[i] += sup[i];
        }
        d[0] = d2[0];
        for (int i = 1; i < n; i++) {
            d[i] = d[i - 1] + d2[i];
        }

        long[] ans = new long[n];
        ans[0] = d[0];
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] + d[i] + sup2[i];
        }
        return ans;


    }

}


