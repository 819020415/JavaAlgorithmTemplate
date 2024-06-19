package algorithm.博弈论;

import java.util.*;

public class SG {
    /**
     * 有向图游戏：
     * 给你一个有向图，图中只有一个起点，在起点上放一个棋子，玩家轮流移动棋子，
     * 谁不能移动谁就输了，问先手是否必胜
     * <p>
     * mex 运算（minimum exclusion）
     * mex(S) = 不属于集合 S 的最小非负整数
     * mex({0,1,2}) = 3,mex({1,2}) = 0
     * <p>
     * SG 函数
     * 设状态 x 有 k 个后继状态（子节点）y1,y2...yk
     * SG(x) = mex({SG(y1),SG(y2)...SG(yk)})
     * <p>
     * SG 定理
     * 由 n 个有向图游戏组成的组合游戏，设起点分别为 s1,s2...sn
     * 当 SG(s1) ^ SG(s2) ^ ... ^ SG(sn) != 0 时先手必胜，否则先手必败
     * 注意 SG(x) = 0 是必败态，叶子节点都 SG 值为 0
     * <p>
     * Nim 游戏可以看做是特殊的有向图游戏，每堆石头都看做一个有向图，起点的 SG(x) = x
     * <p>
     * 一个例子：
     * 有 n 堆石子，还有一个集合 S = {a1,a2,...,ak},
     * 每次可以选择任意一堆石子，再选择集合中的一个数 a[i] ，从那堆石子中取出 a[i] 个石子，
     * 谁不能操作就输了，问是否先手必胜
     * 思路：
     * 将每堆石子看成一个有向图，求起点的 SG 值，最后将每堆石子的 SG 值做 XOR，如果不为 0 先手必胜
     */
    static List<Integer>[] ed;
    static int[] memo;//记忆化搜索，保存 SG 值

    static int sg(int u) {
        if (memo[u] != -1) return memo[u];
        Set<Integer> set = new HashSet<>();
        for (int v : ed[u]) set.add(sg(v));
        for (int i = 0; ; i++) {
            if (!set.contains(i)) return memo[u] = i;
        }
    }


}
