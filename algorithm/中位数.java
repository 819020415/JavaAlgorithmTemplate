package algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//求数组所有长度为 k 的子数组的中位数 mid 以及 ∑ abs(a[i]-mid)
//时间复杂度 nlog(k)
public class 中位数 {

    static void medianSlidingWindow(int[] nums, int k) {
        DualHeap dh = new DualHeap();
        for (int i = 0; i < k; ++i) {
            dh.insert(nums[i]);
        }
        op = new long[nums.length - k + 1];
        med = new int[nums.length - k + 1];
        med[0] = dh.getMedian();
        op[0] = dh.getDiff();
        for (int i = k; i < nums.length; ++i) {
            dh.insert(nums[i]);
            dh.erase(nums[i - k]);
            med[i - k + 1] = dh.getMedian();
            op[i - k + 1] = dh.getDiff();
        }
    }

    static class DualHeap {
        // 大根堆，维护较小的一半元素
        private PriorityQueue<Integer> small;
        // 小根堆，维护较大的一半元素
        private PriorityQueue<Integer> large;
        // 哈希表，记录「延迟删除」的元素，key 为元素，value 为需要删除的次数
        private Map<Integer, Integer> delayed;

        long smallSum, largeSum;//大根堆 小根堆的元素和

        // small 和 large 当前包含的元素个数，需要扣除被「延迟删除」的元素
        private int smallSize, largeSize;

        public DualHeap() {
            this.small = new PriorityQueue<Integer>(new Comparator<Integer>() {
                public int compare(Integer num1, Integer num2) {
                    return num2.compareTo(num1);
                }
            });
            this.large = new PriorityQueue<Integer>(new Comparator<Integer>() {
                public int compare(Integer num1, Integer num2) {
                    return num1.compareTo(num2);
                }
            });
            this.delayed = new HashMap<Integer, Integer>();
            this.smallSize = 0;
            this.largeSize = 0;
            this.smallSum = 0;
            this.largeSum = 0;
        }

        //如果当前个数是偶数个，并且要取较大的，比如 [1,2,3,4] 取 3 ，
        //就返回 large.peek() ?
        public int getMedian() {
            // assert !small.isEmpty();
            return small.peek();
            //return (k & 1) == 1 ? small.peek() : ((double) small.peek() + large.peek()) / 2;
        }

        public void insert(int num) {
            if (small.isEmpty() || num <= small.peek()) {
                small.offer(num);
                smallSum += num;
                ++smallSize;
            } else {
                large.offer(num);
                largeSum += num;
                ++largeSize;
            }
            makeBalance();
        }

        public void erase(int num) {
            delayed.put(num, delayed.getOrDefault(num, 0) + 1);

            if (num <= small.peek()) {
                --smallSize;
                smallSum -= num;
                if (num == small.peek()) {
                    prune(small, 0);
                }
            } else {
                --largeSize;
                largeSum -= num;
                if (num == large.peek()) {
                    prune(large, 1);
                }
            }
            makeBalance();
        }

        // 不断地弹出 heap 的堆顶元素，并且更新哈希表
        private void prune(PriorityQueue<Integer> heap, int t) {
            while (!heap.isEmpty()) {
                int num = heap.peek();
                if (delayed.containsKey(num)) {
                    delayed.put(num, delayed.get(num) - 1);
                    if (delayed.get(num) == 0) {
                        delayed.remove(num);
                    }
                    int d = heap.poll();
                    // if (t == 0) ls -= d;
                    //  else rs -= d;
                } else {
                    break;
                }
            }
        }

        // 调整 small 和 large 中的元素个数，使得二者的元素个数满足要求
        private void makeBalance() {
            if (smallSize > largeSize + 1) {
                // small 比 large 元素多 2 个
                int d = small.poll();
                smallSum -= d;
                largeSum += d;
                large.offer(d);
                --smallSize;
                ++largeSize;
                // small 堆顶元素被移除，需要进行 prune
                prune(small, 0);
            } else if (smallSize < largeSize) {
                // large 比 small 元素多 1 个
                int d = large.poll();
                largeSum -= d;
                smallSum += d;
                small.offer(d);
                ++smallSize;
                --largeSize;
                // large 堆顶元素被移除，需要进行 prune
                prune(large, 1);
            }
        }

        //获取 ∑ abs(a[i]-mid)
        public long getDiff() {
            int mid = getMedian();
            return (long) mid * smallSize - smallSum + largeSum - (long) mid * largeSize;
        }

    }

    static int[] med;//med[i] = 子数组 [i,i+k-1] 的中位数
    static long[] op;//op[i] = ∑ abs(a[i]-mid) op[i] = 将子数组调整成中位数的操作次数
}


