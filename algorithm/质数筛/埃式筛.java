package algorithm.质数筛;

/**
 * 埃拉托斯特尼筛法（埃氏筛）
 */
public class 埃式筛 {

    static int[] p;
    static int index = 0;

    //求 [1~N] 的所有质数
    static void eratosthenesSieve(int N) {
        boolean[] a = new boolean[N + 1];
        p = new int[N];
        for (int i = 2; i <= N; i++) {
            if (!a[i]) {
                p[index++] = i;
                for (int j = i + i; j <= N; j += i) {
                    a[j] = true;
                }
            }
        }
    }


    public static void main(String[] args) {
        eratosthenesSieve(100000);
    }

}
