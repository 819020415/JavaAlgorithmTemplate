package algorithm.字符串.字典树;

class Trie26 {
    Trie26[] children;
    boolean isEnd;

    public Trie26() {
        this.children = new Trie26[26];
    }

    void insert(String word) {
        Trie26 t = this;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            int idx = word.charAt(i) - 'a';
            if (t.children[idx] == null) {
                t.children[idx] = new Trie26();
            }
            t = t.children[idx];
        }
        t.isEnd = true;
    }

    boolean search(String word) {
        Trie26 t = this;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            int idx = word.charAt(i) - 'a';
            if (t.children[idx] == null) return false;
            t = t.children[idx];
        }
        return t.isEnd;
    }


}