package algorithm.字符串;

import java.util.*;

//字符串匹配
public class KMP {
    /**
     * KMP 算法 ： 在 O(n + m) 的时间内求出字符串 p 在字符串 s 中出现的所有位置的下标
     * <p>
     * next 函数：next[i] 表示模式串 P[0,i]中相等前后缀的最长长度
     * 计算 next 函数：使用双指针，i 扫描模式串，j 扫描前缀
     * <p>
     * KMP 匹配
     * 双指针，i 扫描主串，j 扫描模式串
     */

    //输入主串和模式串，返回所有匹配成功的下标
    static List<Integer>  kmp(String str, String pattern) {
        List<Integer> l = new ArrayList<>();//保存匹配成功的 s 的起始下标
        int m = str.length(), n = pattern.length();
        char[] s = new char[m + 1];
        char[] p = new char[n + 1];
        //字符串下标都是从 1 开始的
        for (int i = 0; i < n; i++) {
            p[i + 1] = pattern.charAt(i);
        }
        for (int i = 0; i < m; i++) {
            s[i + 1] = str.charAt(i);
        }
        //计算 next 函数
        int[] next = new int[n + 1];
        next[1] = 0;
        for (int i = 2, j = 0; i <= n; i++) {
            //p[i] 和 p[j + 1] 比较，是一种预判
            while (j != 0 && p[i] != p[j + 1]) j = next[j];
            if (p[i] == p[j + 1]) j++;
            next[i] = j;
        }
        //KMP 匹配
        for (int i = 1, j = 0; i <= m; i++) {
            while (j != 0 && s[i] != p[j + 1]) j = next[j];
            if (s[i] == p[j + 1]) j++;
            if (j == n) {
                l.add(i - n);
                j = next[j];
            }
        }
        return l;
    }

}
