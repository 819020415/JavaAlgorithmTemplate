package algorithm.高精度;

public class Add {

    int[] a, b, c;
    int N;
    int lc = 0;

    //传入两个以数组形式表示的数字，返回字符串形式的两数之和
    StringBuilder calc(char[] x, char[] y) {
        a = new int[N];
        b = new int[N];
        c = new int[N];
        reverse(x);
        reverse(y);
        lc = Math.max(x.length, y.length);
        for (int i = 0; i < x.length; i++) a[i] = x[i] - '0';
        for (int i = 0; i < y.length; i++) b[i] = y[i] - '0';
        for (int i = 0; i < N - 1; i++) {
            c[i] += a[i] + b[i];
            c[i + 1] += c[i] / 10;
            c[i] %= 10;
        }
        if (c[lc] > 0) lc++;
        StringBuilder sb = new StringBuilder();
        for (int i = lc - 1; i >= 0; i--) sb.append(c[i]);
        return sb;
    }


    public Add(int n) {
        N = n;
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
