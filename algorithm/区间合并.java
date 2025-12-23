package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2024/10/26 19:57
 **/
public class 区间合并 {

    static int[][] merge(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> list = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if (intervals[i][0] <= right) {
                right = Math.max(right, intervals[i][1]);
            } else {
                list.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        list.add(new int[]{left, right});
        return list.toArray(new int[list.size()][]);
    }

    static List<int[]> merge1(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> list = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if (intervals[i][0] <= right) {
                right = Math.max(right, intervals[i][1]);
            } else {
                list.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        list.add(new int[]{left, right});
        return list;
    }

    static List<int[]> merge2(List<int[]> intervals) {
        int n = intervals.size();
        intervals.sort((a, b) -> a[0] - b[0]);
        List<int[]> list = new ArrayList<>();

        int left = intervals.get(0)[0];
        int right = intervals.get(0)[1];
        for (int i = 1; i < n; i++) {
            int[] z = intervals.get(i);
            if (z[0] <= right) {
                right = Math.max(right, z[1]);
            } else {
                list.add(new int[]{left, right});
                left = z[0];
                right = z[1];
            }
        }
        list.add(new int[]{left, right});
        return list;
    }

    static int[][] merge3(List<int[]> intervals) {
        int n = intervals.size();
        intervals.sort((a, b) -> a[0] - b[0]);
        List<int[]> list = new ArrayList<>();

        int left = intervals.get(0)[0];
        int right = intervals.get(0)[1];
        for (int i = 1; i < n; i++) {
            int[] z = intervals.get(i);
            if (z[0] <= right) {
                right = Math.max(right, z[1]);
            } else {
                list.add(new int[]{left, right});
                left = z[0];
                right = z[1];
            }
        }
        list.add(new int[]{left, right});
        return list.toArray(new int[list.size()][]);
    }
}
