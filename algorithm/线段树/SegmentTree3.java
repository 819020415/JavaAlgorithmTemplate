package algorithm.线段树;

class SegmentTree3 {
    //离散化线段树，动态开点
    //注意要先离散化，把点映射到[0,2e5]范围
    static class Tree {
        Tree l, r;
        int sum, add;
    }

    static int query(Tree tr, int lc, int rc, int l, int r) {
        if (lc >= l && rc <= r) {
            return tr.sum;
        }
        pushDown(tr);
        int ret = 0;
        int mid = lc + rc >> 1;
        if (l <= mid) ret = Math.max(ret, query(tr.l, lc, mid, l, r));
        if (r > mid) ret = Math.max(ret, query(tr.r, mid + 1, rc, l, r));
        return ret;
    }

    static void update(Tree tr, int lc, int rc, int l, int r) {
        if (lc >= l && rc <= r) {
            tr.sum += 1;
            tr.add += 1;
            return;
        }
        pushDown(tr);
        int mid = lc + rc >> 1;
        if (l <= mid) update(tr.l, lc, mid, l, r);
        if (r > mid) update(tr.r, mid + 1, rc, l, r);
        pushUp(tr);
    }

    static void pushUp(Tree tr) {
        tr.sum = Math.max(tr.l.sum, tr.r.sum);
    }

    static void pushDown(Tree tr) {
        if (tr.l == null) tr.l = new Tree();
        if (tr.r == null) tr.r = new Tree();
        if (tr.add == 0) return;
        tr.l.sum += tr.add;
        tr.r.sum += tr.add;
        tr.l.add += tr.add;
        tr.r.add += tr.add;
        tr.add = 0;
    }

    static  Tree root = new Tree();
    static  int N = (int) (1e9);

}