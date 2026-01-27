package algorithm;

public class bitwise {
    //一些位运算技巧
    // a + b = a ^ b + 2 * (a & b)
    // a + b = - a ^ b + 2 * (a | b)
    // a + b = a & b + a | b

    //状压 dp 中，枚举所有状态，再枚举该状态的所有子集的时间复杂度为 O(3 ^ n)


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
        for (int i = state; true; i = (i - 1) & state) {
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
        long comb = (1L << k) - 1;
        while (comb < 1L << n) {
            System.out.println(Long.toBinaryString(comb));
            long x = comb & -comb, y = comb + x;//x = comb 二进制中最低位的 1，
            comb = ((comb & ~y) / x >> 1) | y;
        }
    }

    //使用数组记录每个数字的 bitCount
    static void bitCount() {
        int n = 1 << 20;
        int[] cnt = new int[n];
        for (int i = 1; i < n; i++) {
            cnt[i] = cnt[i & i - 1] + 1;
        }
    }

    //获得 [1,x] 二进制表示中 1 的个数和
    static long countOne(long x) {
        long res = 0;
        int sum = 0;

        for (int i = 60; i >= 0; i--) {
            if ((1L << i & x) != 0) {
                res += sum * (1L << i);
                sum += 1;
                if (i > 0) {
                    res += i * (1L << (i - 1));
                }
            }
        }
        res += sum;
        return res;
    }

    //获得 [1,x] 二进制表示中所有数的数位对幂的贡献之和, 1011 的贡献为 3 + 1 + 0 = 4
    static long countPow(long x) {
        long res = 0;
        int sum = 0;
        for (int i = 60; i >= 0; i--) {
            if ((1L << i & x) != 0) {
                res += sum * (1L << i);
                sum += i;
                if (i > 0) {
                    res += (long) i * (i - 1) / 2 * (1L << (i - 1));
                }
            }
        }
        res += sum;
        return res;
    }

    //获取 [1,x] 二进制表示中所有数的每个位的 1 的个数和
    static long[] countBinaryOne(long x) {
        long[] cnt = new long[60];
        for (int i = 0; i < 60; i++) {
            cnt[i] += (x + 1) / (1L << (i + 1)) * (1L << i);
            long left = (x + 1) % (1L << (i + 1));
            if (left > 1L << i) {
                cnt[i] += left - (1L << i);
            }
        }
        return cnt;
    }

    //求 [1,x] 所有数字的 xor 和
    static long xorSum(long x) {
        if (x % 4 == 0) return x;
        else if (x % 4 == 1) return 1;
        else if (x % 4 == 2) return x + 1;
        else return 0;
    }


    public static void main(String[] args) {
        int state = Integer.parseInt("10110", 2);
        int n = 5, k = 3;
        //getSubSet(state);
        //  getSubSet2(7);
        getKSubSet(49, 5);

    }
}
