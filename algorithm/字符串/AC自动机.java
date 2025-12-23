package algorithm.字符串;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AC自动机 {
    //AC自动机实现
    //
    public static int MAXN = 500001;
    public static int MAXS = 500005;
    public static int[] end = new int[MAXN];

    // tree
    public static int[][] tree = new int[MAXS][26];
    public static int[] fail = new int[MAXS];
    public static int cnt;

    //队列
    public static int[] box = new int[MAXS];

    //times
    public static int[] times = new int[MAXS];

    //
    public static int[] head = new int[MAXS];
    public static int[] next = new int[MAXS];
    public static int[] to = new int[MAXS];
    public static int edge;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        char[] content = br.readLine().toCharArray();
        cnt = 0;
        //建立 trie 树
        int n = Integer.parseInt(br.readLine());
        for (int sss = 0; sss < n; sss++) {
            insert(sss, br.readLine());
        }
        //设置fail 指针和 直通表
        setfail();

        //遍历文字

        int u = 0;
        for (char c : content) {
            int i = c - 'a';
            u = tree[u][i];
            times[u]++;
        }

        //建立反图
        edge = 0;
        for (int i = 1; i <= cnt; i++) {
            addEdge(fail[i], i);
        }
        //
        f(0);
        for (int i = 0; i < n; i++) {
            out.println(times[end[i]]);
        }
        out.close();
    }

    public static void f(int u) {
        int r = 0;
        box[r++] = u;
        boolean[] visited = new boolean[MAXS];
        while (r > 0) {
            int num = box[r - 1];
            if (visited[num]) {
                r--;
                for (int bian = head[num]; bian != 0; bian = next[bian]) {
                    times[num] += times[to[bian]];
                }
            } else {
                visited[num] = true;
                for (int bian = head[num]; bian != 0; bian = next[bian]) {
                    box[r++] = to[bian];
                }
            }
        }
    }

    public static void addEdge(int u, int v) {
        next[++edge] = head[u];
        to[edge] = v;
        head[u] = edge;
    }

    public static void setfail() {
        int l = 0;
        int r = 0;
        for (int i = 0; i < 26; i++) {
            if (tree[0][i] != 0) {
                box[r++] = tree[0][i];
            }
        }

        while (l < r) {
            int f = box[l++];
            for (int i = 0; i < 26; i++) {
                if (tree[f][i] == 0) {
                    tree[f][i] = tree[fail[f]][i];
                } else {
                    fail[tree[f][i]] = tree[fail[f]][i];
                    box[r++] = tree[f][i];
                }
            }
        }
    }

    public static void insert(int index, String s) {
        char[] str = s.toCharArray();
        int u = 0;
        for (char c : str) {
            if (tree[u][c - 'a'] == 0) {
                tree[u][c - 'a'] = ++cnt;
            }
            u = tree[u][c - 'a'];
        }
        end[index] = u;
    }
}