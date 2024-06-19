package algorithm.高精度;

import java.util.*;

public class Multiply {
     int[] a, b, c;
     int N = 100005, la, lb, lc;

    public Multiply(int n) {
        N = n;
    }

    StringBuilder calc(char[] A, char[] B) {
        Scanner sc = new Scanner(System.in);
        a = new int[N];
        b = new int[N];
        c = new int[N];
        la = A.length;
        lb = B.length;
        lc = la + lb;
        reverse(A);
        reverse(B);
        for (int i = 0; i < A.length; i++) a[i] = A[i] - '0';
        for (int i = 0; i < B.length; i++) b[i] = B[i] - '0';
        mul();
        StringBuilder sb = new StringBuilder();
        for (int i = lc; i >= 0; i--) sb.append(c[i]);
        return sb;
    }




    //计算乘法
     void mul() {
        for (int i = 0; i < la; i++) {
            for (int j = 0; j < lb; j++) {
                c[i + j] += a[i] * b[j];
                c[i + j + 1] += c[i + j] / 10;
                c[i + j] %= 10;
            }
        }
        while (lc > 0 && c[lc] == 0) lc--;
    }

    //旋转数字，高位变低位
     char[] reverse(char[] x) {
        int n = x.length;
        int l = 0, r = n - 1;
        while (l < r) {
            char t = x[l];
            x[l] = x[r];
            x[r] = t;
            l++;
            r--;
        }
        return x;
    }

}
