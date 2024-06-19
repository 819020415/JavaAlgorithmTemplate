package algorithm.平衡树;

public class splay {
    /**
     * 普通平衡树 Splay
     * no good
     */
    class Node {
        int[] s;//左右儿子
        int p;//父节点
        int v;//节点权值
        int cnt;//权值出现次数
        int size;//子树大小

        public void init(int p, int v) {
            this.s = new int[2];
            this.p = p;
            this.v = v;
            this.cnt = 1;
            this.size = 1;
        }
    }

    int root;//根节点编号
    Node[] tr;
    int idx;//节点个数，用于给节点编号


    //旋转 x，分为左旋和右旋
    void rotate(int x) {
        int y = tr[x].p, z = tr[y].p;//x 的父节点 和 爷爷节点
        int k = tr[y].s[1] == x ? 1 : 0;//k = 1 表示 x 是 y 的右儿子 ,k = 0 表示 x 是 y 的左儿子
        tr[y].s[k] = tr[x].s[k ^ 1];
        tr[tr[x].s[k ^ 1]].p = y;
        tr[x].s[k ^ 1] = y;
        tr[y].p = x;
        tr[z].s[tr[z].s[1] == y ? 1 : 0] = x;
        tr[x].p = z;
        pushUp(y);
        pushUp(x);
    }

     void pushUp(int x) {
        tr[x].size = tr[tr[x].s[0]].size + tr[tr[x].s[1]].size + tr[x].cnt;
    }

    //伸展，访问一个节点 x，并且把 x 旋转到根节点
     void splay(int x, int k) {//k 是目标，意思是要把 x 旋转到 k 的儿子位置
        while (tr[x].p != k) {
            int y = tr[x].p, z = tr[y].p;
            if (z != k) {
                if ((tr[y].s[0] == x) ^ (tr[z].s[0] == y)) {//折线型，转两次 x
                    rotate(x);
                } else {//直线型，先转 y 再转 x
                    rotate(y);
                }
            }
            rotate(x);
        }
        if (k == 0) root = x;
    }

    //查找 find
    //功能：找到 v 所在节点，并把该节点转到根；如果没有值为 v 的节点，会把和 v 的值最接近的点转到根上
     void find(int v) {
        int x = root;
        while (tr[x].s[v > tr[x].v ? 1 : 0] != 0 && v != tr[x].v) {//当 tr[x] 的儿子不为空 并且 tr[x].v != v
            x = tr[x].s[v > tr[x].v ? 1 : 0];//查询值大于当前节点值， x = tr[x].s[1]，往右子树走,否则往左子树走
        }
        splay(x, 0);
    }

    //前驱：求 v 的前驱，返回其节点编号
     int get_pre(int v) {
        find(v);
        int x = root;
        if (tr[x].v < v) return x;
        x = tr[x].s[0];
        while (tr[x].s[1] != 0) x = tr[x].s[1];
        splay(x, 0);
        return x;
    }

    //后继：求 v 的后继，返回其节点编号
     int get_suc(int v) {
        find(v);
        int x = root;
        if (tr[x].v > v) return x;
        x = tr[x].s[1];
        while (tr[x].s[0] != 0) x = tr[x].s[0];
        splay(x, 0);
        return x;
    }

    //删除：删除 v (若有多个相同的数，只删除一个)
     void del(int v) {
        int pre = get_pre(v);
        int suc = get_suc(v);
        splay(pre, 0);
        splay(suc, pre);
        int del = tr[suc].s[0];
        if (tr[del].cnt > 1) {
            tr[del].cnt--;
            splay(del, 0);
        } else {
            tr[suc].s[0] = 0;
            splay(suc, 0);
        }
    }

    //排名：查询 v 的排名
     int get_rank(int v) {
        insert(v);
        int res = tr[tr[root].s[0]].size;
        del(v);
        return res;
    }

    //查询排名为 k 的数值
     int get_val(int k) {
        int x = root;
        while (true) {
            int y = tr[x].s[0];
            if (tr[y].size + tr[x].cnt < k) {
                k -= tr[y].size + tr[x].cnt;
                x = tr[x].s[1];
            } else {
                if (tr[y].size >= k) x = tr[x].s[0];
                else break;
            }
        }
        splay(x, 0);
        return tr[x].v;
    }

    //插入
     void insert(int v) {
        int x = root, p = 0;
        while (x != 0 && tr[x].v != v) {
            p = x;
            x = tr[x].s[v > tr[x].v ? 1 : 0];
        }
        if (x != 0) tr[x].cnt++;
        else {
            x = ++idx;
            tr[p].s[v > tr[p].v ? 1 : 0] = x;
            tr[x].init(p, v);
        }
        splay(x, 0);
    }


}
