package algorithm.字符串.字典树;

public class Trie01 {
    Trie01[] children;
    boolean isEnd;
    int size;//当前节点的孩子数量
    int n;//最高位，1e9 就是 30

    public Trie01(int n) {
        this.n = n;
        this.children = new Trie01[2];
    }

    public Trie01() {
        this.children = new Trie01[2];
    }

    //插入一个数
    public void insert(int d) {
        Trie01 t = this;
        for (int i = n - 1; i >= 0; i--) {
            int index = (d >> i) & 1;
            if (t.children[index] == null) {
                t.children[index] = new Trie01();
            }
            t.size++;
            t = t.children[index];

        }
        t.size++;
        t.isEnd = true;
    }

    //删除一个数
    public void delete(int d) {
        Trie01 t = this;
        for (int i = n - 1; i >= 0; i--) {
            int index = (d >> i) & 1;
            if (t.children[index] == null) {
                t.children[index] = new Trie01();
            }
            t.size--;
            t = t.children[index];
        }
        t.size--;
        t.isEnd = true;
    }

    //搜索 x 和当前字典树中的数异或所能组成的最大的数
    public int maxXOR(int x) {
        Trie01 t = this;
        int ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            int d = (x >> i & 1);
            int e = d ^ 1;
            if (t.children[e] != null && t.children[e].size > 0) {
                ret |= 1 << i;
                t = t.children[e];
            } else {
                t = t.children[d];
            }
        }
        return ret;
    }

    //搜索 x 和当前字典树中的数异或所能组成的最小的数
    public int minXOR(int x) {
        Trie01 t = this;
        int ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            int d = (x >> i & 1);
            int e = d ^ 1;
            if (t.children[d] != null && t.children[d].size > 0) {
                t = t.children[d];
            } else {
                ret |= 1 << i;
                t = t.children[e];
            }
        }
        return ret;
    }

    //搜索当前字典树中与 x 做异或后值 >= k 的数字个数
    public int search(int x, int k) {
        Trie01 t = this;
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            int i1 = (x >> i) & 1, i2 = (k >> i) & 1;
            if (i2 == 0) {//k 当前位是 0
                if (t.children[i1 ^ 1] != null) ans += t.children[i1 ^ 1].size;
                t = t.children[i1];
            } else {//k 当前位是 1
                t = t.children[i1 ^ 1];
            }
            if (t == null || t.size == 0) break;
        }
        if (t != null) ans += t.size;
        return ans;
    }

    //搜索当前字典树中与 x 做异或后值 < k 的数字个数
    public int searchLess(int x, int k) {
        Trie01 t = this;
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            int i1 = (x >> i) & 1, i2 = (k >> i) & 1;
            if (i2 == 0) {//k 当前位是 0
                t = t.children[i1];
            } else {//k 当前位是 1
                if (t.children[i1] != null) ans += t.children[i1].size;
                t = t.children[i1 ^ 1];
            }
            if (t == null || t.size == 0) break;
        }
        //因为要严格 < k ，所以这行注释掉，如果求 <= k 的个数，这行就不要注释掉
        // if (t != null) ans += t.size;
        return ans;
    }


}
