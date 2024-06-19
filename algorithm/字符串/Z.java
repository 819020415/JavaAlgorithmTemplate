package algorithm.字符串;

import java.util.*;

public class Z {
    //O（N) 的时间内求出 s 与其后缀 s[i,n] 的最长前缀的长度
    static int[] Z(String s) {
        int n = s.length();
        int[] z = new int[n];
        //z[i]表示 s 与其后缀s[i,n]的最长公共前缀的长度
        //维护右端点最靠右的匹配段（盒子），盒内加速，盒外暴力
        //时间复杂度：O(n)
        //Z-BOX : 对于 i ,区间[i,i + z[i] - 1]是 i 的匹配段，也叫 Z-BOX
        z[0] = n;
        //l,r 盒子的左右边界
        for (int i = 1, l = 0, r = 0; i < n; i++) {
            if (i <= r) z[i] = Math.min(z[i - l], r - i + 1);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            //超出当前盒子的部分要暴力枚举。
            if (i + z[i] - 1 > r) {//如果当前元素的盒子超出了之前盒子范围 r，就更新盒子
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    static int[] Z(char[] s) {
        int n = s.length;
        int[] z = new int[n];
        //z[i]表示 s 与其后缀s[i,n]的最长公共前缀的长度
        //维护右端点最靠右的匹配段（盒子），盒内加速，盒外暴力
        //时间复杂度：O(n)
        //Z-BOX : 对于 i ,区间[i,i + z[i] - 1]是 i 的匹配段，也叫 Z-BOX
        z[0] = n;
        //l,r 盒子的左右边界
        for (int i = 1, l = 0, r = 0; i < n; i++) {
            if (i <= r) z[i] = Math.min(z[i - l], r - i + 1);
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) z[i]++;
            //超出当前盒子的部分要暴力枚举。
            if (i + z[i] - 1 > r) {//如果当前元素的盒子超出了之前盒子范围 r，就更新盒子
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int[] z = Z(s);
        for (int i : z) System.out.println(i);
    }
}
