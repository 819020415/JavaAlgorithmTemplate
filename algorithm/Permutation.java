package algorithm;


/**
 * @date 2024/7/21 12:56
 *
 **/
public class Permutation {
    int[] p;

    public Permutation(int[] p) {
        this.p = p;
    }

    //注意，这个方法只会生成字典序 > 当前排列的所有情况，要想输出全排列，需要先对数组排序
    boolean hasNext() {
        int n = p.length;
        for (int i = n - 2; i >= 0; i--) {
            if (p[i] < p[i + 1]) {
                int j = n - 1;
                while (!(p[i] < p[j])) j--;
                swap(i, j);
                int l = i + 1, r = n - 1;
                while (l < r) swap(l++, r--);
                return true;
            }
        }
        return false;
    }

    //注意，这个方法只会生成字典序 < 当前排列的所有情况，要想输出全排列，需要先对数组排序
    boolean hasPrev() {
        int n = p.length;
        for (int i = n - 2; i >= 0; i--) {
            if (p[i] > p[i + 1]) {
                int j = n - 1;
                while (!(p[i] > p[j])) j--;
                swap(i, j);
                int l = i + 1, r = n - 1;
                while (l < r) swap(l++, r--);
                return true;
            }
        }
        return false;
    }

    boolean nextDouble() {
        int n = p.length;
        for (int i = n - 3; i >= 1; i -= 2) {
            if (p[i] < p[i + 2]) {
                int j = i + 1;
                while (!(p[i] < p[j])) {
                    if (j == n - 2) j++;
                    else if ((j & 1) == 0) j += 2;
                    else j -= 2;
                }
                swap(i, j);
                int s = i + 1;
                int[] tmp = new int[n - s];
                for (int i_ = 0; i_ < tmp.length; i_++) {
                    tmp[i_] = p[s];
                    if (s == n - 2) s++;
                    else if ((s & 1) == 0) s += 2;
                    else s -= 2;
                }
                for (int i_ = 0; i_ < tmp.length; i_++) p[i_ + i + 1] = tmp[i_];
                return true;
            }
        }
        return false;
    }

    boolean prevDouble() {
        int n = p.length;
        for (int i = n - 3; i >= 1; i -= 2) {
            if (p[i] > p[i + 1]) {
                int j = n - 1;
                while (!(p[i] > p[j])) j--;
                swap(i, j);
                int s = i + 1;
                int[] tmp = new int[n - s];
                for (int i_ = 0; i_ < tmp.length; i_++) tmp[i_] = p[i_ + i + 1];
                for (int i_ = 0; i_ < tmp.length; i_++) {
                    p[s] = tmp[i_];
                    if (s == n - 2) s++;
                    else if ((s & 1) == 0) s += 2;
                    else s -= 2;
                }
                return true;
            }
        }
        return false;
    }

    private void swap(int i, int j) {
        int tmp = p[i];
        p[i] = p[j];
        p[j] = tmp;
    }
}
