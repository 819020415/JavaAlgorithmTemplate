package algorithm.高精度;

public class Subtract {

    int[] a, b, c;
    int N = 100005, lc;


    StringBuilder calc(char[] A, char[] B) {
        a = new int[N];
        b = new int[N];
        c = new int[N];
        StringBuilder sb = new StringBuilder();
        if (!cmp(A, B)) {
            char[] t = A;
            A = B;
            B = t;
            sb.append('-');//结果为负
        }
        lc = Math.max(A.length, B.length);
        reverse(A);
        reverse(B);
        for (int i = 0; i < A.length; i++) a[i] = A[i] - '0';
        for (int i = 0; i < B.length; i++) b[i] = B[i] - '0';
        sub();
        for (int i = lc; i >= 0; i--) sb.append(c[i]);
        return sb;
    }

    public Subtract(int n) {
        N = n;
    }

    //计算减法
    void sub() {
        for (int i = 0; i < lc; i++) {
            if (a[i] < b[i]) {//低位向高位借位
                a[i + 1]--;
                a[i] += 10;
            }
            c[i] = a[i] - b[i];
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


    //比较 A 和 B 的大小，如果 A < B ，就交换，保证 A >= B
    static boolean cmp(char[] A, char[] B) {
        if (A.length != B.length) return A.length > B.length;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > B[i]) return true;
            else if (A[i] < B[i]) return false;
        }
        return true;
    }


}
