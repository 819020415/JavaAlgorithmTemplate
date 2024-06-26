package algorithm.图论.强连通分量;

public class two_SAT {
    /**
     * SAT 是适定性（Satisfiability）问题的简称。一般形式为 k - 适定性问题，简称 k-SAT。
     * 而当 k>2 时该问题为 NP 完全的。所以我们只研究 k=2 的情况。
     *
     * 2-SAT，简单的说就是给出 n 个集合，每个集合有两个元素，已知若干个 <a,b>，
     * 表示 a 与 b 矛盾（其中 a 与 b 属于不同的集合）。然后从每个集合选择一个元素，
     * 判断能否一共选 n 个两两不矛盾的元素。显然可能有多种选择方案，
     * 一般题中只需要求出一种即可。
     *
     * a ∨ b => a 或 b 为 true => ¬a 向 b 连一条边，¬b 向 a 连一条边，
     * 表示当 a = false 时 b = true ; 当 b = false 时 a = true
     * 将所有的矛盾关系转化为有向边，用 tarjan 求 SCC，每个 SCC 中的条件要么全为
     * true,要么全为 false，如果某个 SCC 中包含了同一个集合里的两个元素，则不可能完成，
     * 否则令 x 所在的强连通分量的拓扑序在 ¬x 所在的强连通分量之后 ⇔ x 为真
     */


}
