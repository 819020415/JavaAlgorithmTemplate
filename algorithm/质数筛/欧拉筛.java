package algorithm.质数筛;

public class 欧拉筛 {
    //欧式筛，线性筛
    static  int[] p;
    static boolean[] st ;//st[i]=true 表示 i 是质数

    static void f(int n) {
        int cnt = 0;
        p = new int[n + 1];
        st = new boolean[n + 1];//st[i]=true 表示 i 是质数
        for (int i = 2; i <= n; i++) {
            if (!st[i]) p[cnt++] = i;
            for (int j = 0; p[j] * i <= n; j++) {
                st[p[j] * i] = true;
                if (i % p[j] == 0) break;
            }
        }
    }


    public static void main(String[] args) {
        f(100000);
    }
}
