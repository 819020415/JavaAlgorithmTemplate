package algorithm.math;

public class 扩展欧几里得定理 {
    /**
     * 扩展欧几里得算法
     * 用于求解 ax + by = gcd(a,b) 的特解
     * <p>
     * 扩展：
     * 求解 ax + by = c 的特解
     * 如果 c %  gcd(a,b) == 0,那么先求 ax + by = gcd(a,b) 的一组特解 (x1,y1)，
     * 令 k = c / gcd(a,b),那么原方程的特解为 (k * x1,k * y1)
     * 如果 c %  gcd(a,b) != 0,那么没有整数解
     */

    public static void main(String[] args) {
        int d = exgcd(8, 6);//d = gcd(a,b),(x,y) 为 ax + by = 1 的一组特解
        System.out.println(d + " " + x + " " + y);
        //输出 2 1 -1 表示 8 和 6 的 gcd 为 2 , (1,-1) 是方程 8 * x + 6 * y = 2 的一组特解
    }

    static int x, y;

    static int exgcd(int a, int b) {
        if (b == 0) {
            x = 1;
            y = 0;
            return a;
        }
        int d = exgcd(b, a % b);
        int x1 = x, y1 = y;
        x = y1;
        y = x1 - (a / b) * y1;
        return d;
    }
}
