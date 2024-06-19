package algorithm.博弈论;

import java.util.*;

public class Nim {
    /**
     * 博弈论，Nim 游戏
     * n 堆石头，每次可以从一堆石头中拿任意个，谁拿不了了就输了
     * 必胜状态：a[1] ^ a[2] ^ ... ^ a[n] != 0
     * 必败状态：a[1] ^ a[2] ^ ... ^ a[n] == 0
     *
     * 1.必胜状态的后继状态必有至少一个必败状态（当前在必胜态，必能制造一个必败态留给对手）
     * 当前 a[1] ^ a[2] ^ ... ^ a[n] = xor != 0，假设 xor 中 1 的最高位为 k，
     * 至少有一个 a[i] 的第 k 位为 1，令 a[i] = a[i] ^ xor,
     * 那么有  a[1] ^ a[2] ^ ... ^ a[i] ^ xor ^ ... ^ a[n] =
     *        a[1] ^ a[2] ^ ... ^ a[i] ^  ... ^ a[n] ^ xor = xor ^ xor = 0
     * 同时有 a[i] ^ xor < a[i]
     *
     * 2.必败状态的后继状态必都为必胜状态
     * 当前 a[1] ^ a[2] ^ ... ^ a[n] == 0，当取了石头后，
     * 必有 a[1] ^ a[2] ^ ... ^ a[n] != 0
     *
     * 扩展：
     * 台阶型 Nim 游戏
     * 有 N 个台阶（1 ~ N），每个台阶上都有一些石子，每次可以将某一个台阶上的任意数量的石子移到下一个台阶，
     * 移到第 0 阶就不能动了，问是否先手必胜
     * 先手必胜条件：a[1] ^ a[3] ^ ... != 0 （奇数级台阶的 XOR 和不为 0）
     *
     * 例题：poj 1704
     *
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int xor = 0;
            for (int i = 0; i < n; i++) {
                xor ^= sc.nextInt();
            }
            System.out.println(xor == 0 ? "No" : "Yes");

        }
    }

}
