package algorithm.平面几何;

import java.util.*;

/**
 * Andrew 算法求凸包的周长
 * 1. 将点按照 x 升序，y 升序排序
 * 2. 顺序枚举所有点，求下凸包，用栈维护当前凸包上的点，
 * 新点入栈前，如果新点在直线上或直线右侧，就弹出栈顶的点
 * 3. 逆序枚举求上凸包，类似
 **/
public class 凸包 {

    static double[][] p;
    static int n, top;
    static int[] s;

    static double cross(double[] a, double[] b, double[] c) {//叉积
        return (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
    }

    static double dis(double[] a, double[] b) {//两点距离
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
    }

    static double Andrew() {
        Arrays.sort(p, (o1, o2) -> {//按照 x 升序，y 升序排序
            if (o1[0] != o2[0]) return Double.compare(o1[0], o2[0]);
            return Double.compare(o1[1], o2[1]);
        });
        for (int i = 0; i < n; i++) {
            while (top > 1 && cross(p[s[top - 1]], p[s[top]], p[i]) <= 0) top--;//top > 1 保证栈中至少有 2 个点
            s[++top] = i;
        }
        int t = top;
        for (int i = n - 2; i >= 0; i--) {
            while (top > t && cross(p[s[top - 1]], p[s[top]], p[i]) <= 0) top--;//top > t 保证不弹出下凸包的点
            s[++top] = i;
        }
        double ans = 0;
        for (int i = 0; i < top; i++) {
            ans += dis(p[s[i]], p[s[i + 1]]);
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        p = new double[n][2];
        s = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                p[i][j] = sc.nextInt();
            }
        }
        Andrew();
    }


}
