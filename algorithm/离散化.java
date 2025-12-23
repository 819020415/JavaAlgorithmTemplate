package algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2024/12/22 8:07
 */
public class 离散化 {
    static void discretize(int[] a) {
        discretize = a.clone();
        len = 0;
        map = new HashMap<>();
        int n = a.length;
        Arrays.sort(discretize);
        int prev = -1 << 30;//取一个比 min(a[i]) 小的数字
        for (int i = 0; i < n; i++) {
            if (prev < discretize[i]) {
                prev = discretize[i];
                map.put(prev, len++);
            }
        }
    } 

    static int len = 0;//len = 去重后的元素个数
    static int[] discretize;
    static Map<Integer, Integer> map = new HashMap<>();//key 原来的数字 val 离散化后的数字

}


