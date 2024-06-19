package algorithm.字符串;

public class StringHash {

    /**
     * 字符串哈希：把不同的字符串映射成不同的整数
     * 1.把字符串映射成一个 p 进制数字，
     * hash 函数 ：h(s) = ∑ s[i] * p ^ (n - i) mod M
     * eg：字符串 abc 的 hash 函数值为 ：ap^2 + bp + c
     * 即：97 * 131^2 + 98 * 131^1 + 99
     * <p>
     * 2.如果两个字符串不一样，hash 函数值却一样，这样的现象称为 哈希碰撞 (hashing collision)
     * <p>
     * 3.解决哈希碰撞的方法：
     * 巧妙设置 p 和 M 的值，保证 p 和 M 互质
     * p 通常取质数 131,13331,
     * M 取大整数 2^64，让其自动溢出，等价于取模
     * <p>
     * 求哈希函数
     * 1.求一个字符串的哈希值相当于求前缀和
     * 求一个字符串的子串的哈希值相当于求区间和
     * <p>
     * 2.前缀和 h[i] = h[i - 1] * p + s[i], h[0] = 0 (字符串下标从 1 开始)
     * <p>
     * 3.区间和 h[l,r] = h[r] - h[l - 1] * p ^ (r - l + 1)
     * <p>
     * 时间复杂度：计算前缀和 O(n) 查询区间和 O(1)
     */
    /**
     * 如何用字符串哈希判断某个字符串是否为回文串：
     * 令 hash 函数 ：h(s) = ∑ s[i] * p ^ (n - i) mod M
     * 再定义一个 hash 函数 h2(s) = ∑ s[i] * p ^ (i - 1) mod M
     * 如果有 h(s) == h2(s),那么很大概率 s 是个回文串
     */
    //自然溢出，如果有 hash 冲突，可以取模 1e9+7, 1e13+7

    public StringHash(String s) {
        init(s.toCharArray());
    }

    int P = 131;
    long[] h, p;//h[i] = s[1~i] 的 hash 值，p[i] = p ^ i

    //预处理 hash 函数的前缀和
    void init(char[] s) {
        int n = s.length;
        h = new long[n + 1];
        p = new long[n + 1];
        p[0] = 1;
        h[0] = 0;
        for (int i = 1; i <= n; i++) {
            p[i] = p[i - 1] * P;
            h[i] = h[i - 1] * P + s[i - 1];
        }
    }

    //获得 s[l~r] 的 hash 值，字符串 "abc" 的子串 "bc" 的 hash = getHash(1,2),字符串下标从 0 开始
    long getHash(int l, int r) {
        return h[r + 1] - h[l] * p[r - l + 1];
    }

    //判断两子串是否相同
    boolean isSame(int l1, int r1, int l2, int r2) {
        return getHash(l1, r1) == getHash(l2, r2);
    }

    //获取一个字符串的 hash 值
    long evaluate(String s) {
        long h = 0;
        for (char c : s.toCharArray()) h = h * P + c;
        return h;
    }


}
