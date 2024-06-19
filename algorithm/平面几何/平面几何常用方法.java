package algorithm.平面几何;

/**
 * @date 2024/4/13 16:49
 **/
public class 平面几何常用方法 {


    //计算 (b - a) X (c - a)，判断 a,b,c 三点是否共线，如果叉积 = 0 就共线
    static double cross(double[] a, double[] b, double[] c) {//叉积
        return (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
    }



    //两点曼哈顿距离
   static int ManhattanDis(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }


    //两点欧式距离
    double dis(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
