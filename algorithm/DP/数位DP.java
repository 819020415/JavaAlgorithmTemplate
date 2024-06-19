package algorithm.DP;

import java.util.*;

public class 数位DP {
    //数位dp 灵神模板
    static long[][] memo;//记忆化数组，memo[i][j] 表示 前 i 个数的状态为 j ，后面的数随便选，一共的方案数
    //memo 初始化为 -1
    static char[] s;//把上界 n 转化成一个字符数组

    /**
     * @param i       当前位
     * @param mask    所选数字状态,也可能是别的约束条件,也可能没有约束条件
     * @param isLimit 当前位置可添的数字是否受到之前填的数字影响，
     *                如果之前填的数字和上界一样，那当前的数字范围为[0,s[i]]，否则为 [0,9]
     * @param isNum   标记之前是否已经填了数字，如果没填，当前数字就不能填 0 ，否则可以，
     *                用于处理前导 0 ，
     * @return
     */
    static long f(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0;//当数字 0 也是种合法方案时，直接返回 1 即可
        if (!isLimit && memo[i][mask] >= 0) return memo[i][mask];//注意一定要加 !isLimit
        long ret = 0;
        //首次进入函数 f ,就会不断再进入 f，先把位数低的情况搞出来，位数高的就可以通过记忆化加速
        if (!isNum) ret += f(i + 1, mask, false, false);
        int up = isLimit ? s[i] - '0' : 9;//当前可选数字的上界
        for (int d = isNum ? 0 : 1; d <= up; d++) {
            if ((mask >> d & 1) == 0) {//如果满足约束条件，就进入，不同题目约束条件是不同的
                //这里当 isLimit 是 true 并且 d ==up 的时候， isLimit 才能继续保持 true
                //意思就是只有始终选择等于上界的那个数，才能满足约束，否则就没有约束，下一位可选 [0,9]
                ret += f(i + 1, mask | 1 << d, isLimit && d == up, true);
            }
        }
        if (!isLimit && isNum) memo[i][mask] = ret;
        return ret;
    }

    static long init(char[] n) {
        s = n;
        int len = s.length;
        memo = new long[len][len];//memo 数组第二维看情况，有可能第二维是表示一个 mask，有可能没有第二维
        for (long[] m : memo) Arrays.fill(m, -1);
        return f(0, 0, true, false);
    }

    static long init(long n) {
        s = String.valueOf(n).toCharArray();
        int len = s.length;
        memo = new long[len][len];//memo 数组第二维看情况，有可能第二维是表示一个 mask，有可能没有第二维
        for (long[] m : memo) Arrays.fill(m, -1);
        return f(0, 0, true, false);
    }

    //递归入口 f(0, 0, true, false);
}
