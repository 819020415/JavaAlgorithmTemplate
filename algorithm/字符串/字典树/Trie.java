package algorithm.字符串.字典树;

//数组实现 01 trie
//注意：创建出字典树后要调用一次 newNode() 方法创建出根节点，
//如果是多测试用例，每次都要先将 tot 赋值为 0，再调用一次 newNode() 方法
//PS 已经在构造函数中将 tot 赋值为 0,并且调用了 newNode() 函数，每次只需要创建一个 Trie 对象即可
public class Trie {
    int N;
    int[][] trie;
    int[] cnt;
    int tot;
    int MAX;

    //数组长度，数组最大值二进制的长度
    public Trie(int n, int max) {
        N = n * 30;//一共 n 个数字，每个数字 <= 1e9，每个数字最多占 30 个点
        trie = new int[2][N];
        this.MAX = max;
        cnt = new int[n * MAX];
        tot = 0;
        newNode();
    }

    int newNode() {
        int x = ++tot;
        trie[0][x] = trie[1][x] = 0;
        cnt[x] = 0;
        return x;
    }

    //插入或删除一个数，v = 1 插入 v = -1 删除
    void add(int d, int v) {
        int p = 1;
        cnt[p] += v;
        for (int i = MAX - 1; i >= 0; i--) {
            int u = d >> i & 1;
            if (trie[u][p] == 0) {
                trie[u][p] = newNode();
            }
            p = trie[u][p];
            cnt[p] += v;
        }
    }

    //求字典树中与 d 异或的最大值
    int maxXOR(int d) {
        int p = 1;
        if (cnt[p] == 0) return 0;
        int ans = 0;
        for (int i = MAX - 1; i >= 0; i--) {
            int u = d >> i & 1;
            if (cnt[trie[u ^ 1][p]] > 0) {
                ans |= 1 << i;
                p = trie[u ^ 1][p];
            } else {
                p = trie[u][p];
            }
        }
        return ans;
    }

    //求字典树中与 d 异或的最小值
    int minXOR(int d) {
        int p = 1;
        if (cnt[p] == 0) return 0;
        int ans = 0;
        for (int i = MAX - 1; i >= 0; i--) {
            int u = d >> i & 1;
            if (cnt[trie[u][p]] > 0) {
                p = trie[u][p];
            } else {
                ans |= 1 << i;
                p = trie[u ^ 1][p];
            }
        }
        return ans;
    }

    //求字典树中与 x 异或后值 >= k 的数字个数
    int countGEK(int x, int k) {
        int p = 1, ans = 0;
        for (int i = MAX - 1; i >= 0; --i) {
            int bit = x >> i & 1;
            if ((k >> i & 1) == 0) {
                ans += cnt[trie[bit ^ 1][p]];
                p = trie[bit][p];
            } else {
                p = trie[bit ^ 1][p];
            }
            if (p == 0) break;
        }
        return ans + cnt[p];
    }


    //求字典树中与 x 异或后值 <= k 的数字个数
    int countLEK(int x, int k) {
        int p = 1, ans = 0;
        for (int i = MAX - 1; i >= 0; --i) {
            int bit = x >> i & 1;
            if ((k >> i & 1) == 0) {
                p = trie[bit][p];
            } else {
                ans += cnt[trie[bit ^ 1][p]];
                p = trie[bit ^ 1][p];
            }
            if (p == 0) break;
        }
        return ans + cnt[p];
    }
}
