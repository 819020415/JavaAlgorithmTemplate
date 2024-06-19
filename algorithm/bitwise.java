package algorithm;

public class bitwise {
    //一些位运算技巧
    // a + b = a ^ b + 2 * (a & b)
    // a + b = - a ^ b + 2 * (a | b)
    // a + b = a & b + a | b

    //获取状态 state 的所有子集
    static void getSubSet(int state) {
        int s = state;
        do {
            System.out.println(Integer.toBinaryString(s));
            s = (s - 1) & state;
        } while (s != state);
    }

    //获取所有子集的第二种写法
    static void getSubSet2(int state) {
        //获取所有子集的第二种写法
        for (int i = state; i >= 0; i = (i - 1) & state) {
            System.out.println(Integer.toBinaryString(i));
            if (i == 0) break;
        }
    }

    //获得 x 二进制表示的最低位的 1
    static int lowbit(int x) {
        return x & -x;
    }

    // 使用位运算获得 n 个元素所包含的所有大小为 k 的子集的方法,comb 是按照字典序从小到大的
    static void getKSubSet(int n, int k) {
        int comb = (1 << k) - 1;
        while (comb < 1 << n) {
            System.out.println(Integer.toBinaryString(comb));
            int x = comb & -comb, y = comb + x;//x = comb 二进制中最低位的 1，
            comb = ((comb & ~y) / x >> 1) | y;
        }
    }

    public static void main(String[] args) {
        int state = Integer.parseInt("10110", 2);
        int n = 5, k = 3;
        //  getSubSet(state);
        //  getSubSet2(state);
        getKSubSet(n, k);

    }
}
