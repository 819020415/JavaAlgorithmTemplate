package algorithm.字符串;

public class Manacher {
    /**
     * Manacher 算法，可以在 O(n) 时间内求出一个字符串的最长回文串
     * <p>
     * 概念：
     * 回文半径 d[i]: 以 i 为中心的最长回文串的长度的一半
     * 加速盒子 [l,r]:
     * 算法过程中我们维护右端点最靠右的最长回文串
     * 利用盒子，借助之前的状态来加速计算新的状态
     * 盒内 d[i] 可以利用 对称点 的 d 值转移、盒外暴力
     * <p>
     * <p>
     * 流程：
     * 1.改造字符串：在字符之间和两端插入 #,改造后，都变成奇回文串，方便统一处理
     * s[0] = $ 是哨兵（边界）
     * eg: aba  ->  #a#b#a#
     * abba -> #a#b#b#a#
     * 2.计算完前 i - 1 个 d 函数，维护盒子 [l,r]
     * 如果 i <= r (在盒内), i 的对称点为 r - i + l
     * (1)若 d[r - i + l] < r - i + 1,则 d[i] = d[r - i + 1]
     * (2)若 d[r - i + l] >= r - i + 1,则 d[i] = r - i + 1
     * 如果 i > r (在盒外),则从 i 开始暴力枚举
     * 3.求出 d[i] 后，如果 i + d[i] - 1 > r ，则更新盒子 l = i - d[i] + 1,r = i + d[i] - 1;
     */

    static int[] d;

    static int Manacher(String s) {
        int n = s.length();
        int len = 2 * n + 2;
        char[] chars = new char[len];
        d = new int[len];
        int k = 0;
        chars[k++] = '$';
        chars[k++] = '#';
        for (int i = 0; i < n; i++) {
            chars[k++] = s.charAt(i);
            chars[k++] = '#';
        }
        getD(chars, len);

        int ret = 0;
        for (int i = 0; i < 2 * n + 2; i++) {
            ret = Math.max(ret, d[i]);
        }
        return ret - 1;
    }

    static void getD(char[] chars, int n) {
        d[1] = 1;
        for (int i = 2, l = 0, r = 1; i < n; i++) {
            if (i <= r) d[i] = Math.min(d[r - i + l], r - i + 1);
            while (i + d[i] < n && chars[i - d[i]] == chars[i + d[i]]) d[i]++;
            if (i + d[i] - 1 > r) {
                l = i - d[i] + 1;
                r = i + d[i] - 1;
            }
        }
    }

    public static void main(String[] args) {
        String s= "110110110011";
        Manacher(s);
    }


}
