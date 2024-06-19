package algorithm.字符串;

public class 最小表示法 {

    /**
     * 循环同构串 最小表示法
     * 一个可以循环的字符串，求它的字典序最小的字符串
     * 处理循环串的技巧：复制加倍，破环成链
     * 返回
     */
    int getMin(String s) {
        int n = s.length();
        char[] c = new char[2 * n];
        for (int i = 0; i < n; i++) c[i] = c[n + i] = s.charAt(i);
        int i = 0, j = 1, k = 0;//i,j 指针，k 匹配长度
        while (i < n && j < n) {
            while (k < n && c[i + k] == c[j + k]) k++;
            if (c[i + k] > c[j + k]) i = i + k + 1;
            else j = j + k + 1;
            // c[i + k] > c[j + k] ? i = i = k + 1 : j = j + k + 1;
            if (i == j) j++;
        }
        return Math.min(i, j);
    }
}
