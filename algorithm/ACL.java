package algorithm;

import java.util.Arrays;

class ACL {

    public static final class DisjointSetUnion {

        private final int[] parent;

        private DisjointSetUnion(final int n) {
            parent = new int[n];
            Arrays.fill(parent, -1);
        }

        public static DisjointSetUnion create(final int n) {
            return new DisjointSetUnion(n);
        }

        public int getLeader(int a) {
            int p1, p2;
            while ((p1 = parent[a]) >= 0) {
                if ((p2 = parent[p1]) >= 0) a = parent[a] = p2;
                else return p1;
            }
            return a;
        }

        public int merge(int a, int b) {
            a = getLeader(a);
            b = getLeader(b);
            if (a == b) return a;
            if (parent[a] < parent[b]) {
                parent[b] += parent[a];
                parent[a] = b;
                return b;
            }
            parent[a] += parent[b];
            parent[b] = a;
            return a;
        }

        public boolean isSame(final int a, final int b) {
            return getLeader(a) == getLeader(b);
        }

        public int getSize(final int a) {
            return -parent[getLeader(a)];
        }

        public java.util.ArrayList<java.util.ArrayList<Integer>> getGroups() {
            final Object[] group = new Object[parent.length];
            final java.util.ArrayList<java.util.ArrayList<Integer>> ret = new java.util.ArrayList<>();
            for (int i = 0; i < parent.length; ++i) {
                final int leader = getLeader(i);
                final Object put = group[leader];
                if (put == null) {
                    final java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
                    list.add(i);
                    ret.add(list);
                    group[leader] = list;
                } else {
                    @SuppressWarnings("unchecked")
                    final java.util.ArrayList<Integer> list = (java.util.ArrayList<Integer>) put;
                    list.add(i);
                }
            }
            return ret;
        }

        @Override
        public String toString() {
            return getGroups().toString();
        }
    }

    public static final class IntFenwickTree {

        private final int[] array;

        private IntFenwickTree(final int n) {
            array = new int[n + 1];
        }

        private IntFenwickTree(final int[] array) {
            this(array.length);
            System.arraycopy(array, 0, this.array, 1, array.length);
            for (int i = 1; i < this.array.length; ++i)
                if (i + (i & -i) < this.array.length) this.array[i + (i & -i)] += this.array[i];
        }

        public static IntFenwickTree create(final int n) {
            return new IntFenwickTree(n);
        }

        public static IntFenwickTree create(final int[] array) {
            return new IntFenwickTree(array);
        }

        public void add(int index, final int add) {
            ++index;
            while (index < array.length) {
                array[index] += add;
                index += index & -index;
            }
        }

        private int sum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += array[index];
                index -= index & -index;
            }
            return sum;
        }

        public int sum(final int l, final int r) {
            return sum(r) - sum(l);
        }

        @Override
        public String toString() {
            return java.util.stream.IntStream.range(0, array.length - 1)
                    .mapToObj(i -> String.valueOf(sum(i + 1) - sum(i)))
                    .collect(java.util.stream.Collectors.joining(", ", "[", "]"));
        }
    }

    public static final class LongFenwickTree {

        private final long[] array;

        private LongFenwickTree(final int n) {
            array = new long[n + 1];
        }

        private LongFenwickTree(final long[] array) {
            this(array.length);
            System.arraycopy(array, 0, this.array, 1, array.length);
            for (int i = 1; i < this.array.length; ++i)
                if (i + (i & -i) < this.array.length) this.array[i + (i & -i)] += this.array[i];
        }

        public static LongFenwickTree create(final int n) {
            return new LongFenwickTree(n);
        }

        public static LongFenwickTree create(final long[] array) {
            return new LongFenwickTree(array);
        }

        public void add(int index, final long add) {
            ++index;
            while (index < array.length) {
                array[index] += add;
                index += index & -index;
            }
        }

        private long sum(int index) {
            long sum = 0;
            while (index > 0) {
                sum += array[index];
                index -= index & -index;
            }
            return sum;
        }

        public long sum(final int l, final int r) {
            return sum(r) - sum(l);
        }

        @Override
        public String toString() {
            return java.util.stream.IntStream.range(0, array.length - 1)
                    .mapToObj(i -> String.valueOf(sum(i + 1) - sum(i)))
                    .collect(java.util.stream.Collectors.joining(", ", "[", "]"));
        }
    }

    public static final class MathLib {

        public static class Barrett {
            private final int mod;
            private final long h, l;
            private final long MAX = 1L << 62;
            private final int MASK = (1 << 31) - 1;

            Barrett(final int mod) {
                this.mod = mod;
                final long t = MAX / mod;
                h = t >>> 31;
                l = t & MASK;
            }

            int reduce(final long x) {
                final long xh = x >>> 31, xl = x & MASK;
                long z = xl * l;
                z = xl * h + xh * l + (z >>> 31);
                z = xh * h + (z >>> 31);
                final int ret = (int) (x - z * mod);
                return ret >= mod ? ret - mod : ret;
            }
        }

        public static class BarrettSmall {
            private final int mod;
            final long t;

            BarrettSmall(final int mod) {
                this.mod = mod;
                t = (1L << 42) / mod;
            }

            int reduce(long x) {
                long q = x * t >> 42;
                x -= q * mod;
                return (int) (x >= mod ? x - mod : x);
            }
        }

        private static long safe_mod(long x, final long m) {
            x %= m;
            if (x < 0) x += m;
            return x;
        }

        private static long[] inv_gcd(long a, final long b) {
            a = safe_mod(a, b);
            if (a == 0) return new long[] { b, 0 };

            long s = b, t = a;
            long m0 = 0, m1 = 1;
            while (t > 0) {
                final long u = s / t;
                s -= t * u;
                m0 -= m1 * u;
                long tmp = s;
                s = t;
                t = tmp;
                tmp = m0;
                m0 = m1;
                m1 = tmp;
            }
            if (m0 < 0) m0 += b / s;
            return new long[] { s, m0 };
        }

        public static int pow(long n, long m, final int mod) {
            assert m >= 0 && mod >= 1;
            if (mod == 1) return 0;
            return pow(n, m, new Barrett(mod));
        }

        public static int pow(long n, long m, Barrett mod) {
            assert m >= 0;
            long ans = 1, num = n % mod.mod;
            while (m != 0) {
                if ((m & 1) != 0) ans = mod.reduce(ans * num);
                m >>>= 1;
                num = mod.reduce(num * num);
            }
            return (int) ans;
        }

        public static int pow998_244_353(long n, long m) {
            assert m >= 0;
            long ans = 1, num = n % 998_244_353;
            while (m != 0) {
                if ((m & 1) != 0) ans = ans * num % 998_244_353;
                m >>>= 1;
                num = num * num % 998_244_353;
            }
            return (int) ans;
        }

        public static int pow167_772_161(long n, long m) {
            assert m >= 0;
            long ans = 1, num = n % 167_772_161;
            while (m != 0) {
                if ((m & 1) != 0) ans = ans * num % 167_772_161;
                m >>>= 1;
                num = num * num % 167_772_161;
            }
            return (int) ans;
        }

        public static int pow469_762_049(long n, long m) {
            assert m >= 0;
            long ans = 1, num = n % 469_762_049;
            while (m != 0) {
                if ((m & 1) != 0) ans = ans * num % 469_762_049;
                m >>>= 1;
                num = num * num % 469_762_049;
            }
            return (int) ans;
        }

        public static int pow1_000_000_007(long n, long m) {
            assert m >= 0;
            long ans = 1, num = n % 1_000_000_007;
            while (m != 0) {
                if ((m & 1) != 0) ans = ans * num % 1_000_000_007;
                m >>>= 1;
                num = num * num % 1_000_000_007;
            }
            return (int) ans;
        }

        public static int pow(long n, long m, BarrettSmall mod) {
            assert m >= 0;
            long ans = 1, num = n % mod.mod;
            while (m != 0) {
                if ((m & 1) != 0) ans = mod.reduce(ans * num);
                m >>>= 1;
                num = mod.reduce(num * num);
            }
            return (int) ans;
        }

        public static long[] crt(final long[] r, final long[] m) {
            assert r.length == m.length;
            final int n = r.length;

            long r0 = 0, m0 = 1;
            for (int i = 0; i < n; i++) {
                assert 1 <= m[i];
                long r1 = safe_mod(r[i], m[i]), m1 = m[i];
                if (m0 < m1) {
                    long tmp = r0;
                    r0 = r1;
                    r1 = tmp;
                    tmp = m0;
                    m0 = m1;
                    m1 = tmp;
                }
                if (m0 % m1 == 0) {
                    if (r0 % m1 != r1) return new long[] { 0, 0 };
                    continue;
                }

                final long[] ig = inv_gcd(m0, m1);
                final long g = ig[0], im = ig[1];

                final long u1 = m1 / g;
                if ((r1 - r0) % g != 0) return new long[] { 0, 0 };

                final long x = (r1 - r0) / g % u1 * im % u1;

                r0 += x * m0;
                m0 *= u1;
                if (r0 < 0) r0 += m0;
                // System.err.printf("%d %d\n", r0, m0);
            }
            return new long[] { r0, m0 };
        }

        public static long floor_sum(final long n, final long m, long a, long b) {
            long ans = 0;
            if (a >= m) {
                ans += (n - 1) * n * (a / m) / 2;
                a %= m;
            }
            if (b >= m) {
                ans += n * (b / m);
                b %= m;
            }

            final long y_max = (a * n + b) / m;
            final long x_max = y_max * m - b;
            if (y_max == 0) return ans;
            ans += (n - (x_max + a - 1) / a) * y_max;
            ans += floor_sum(y_max, a, m, (a - x_max % a) % a);
            return ans;
        }

        /**
         * aとbの最大公約数を返します。
         * @param a 整数
         * @param b 整数
         * @return 最大公約数
         */
        public static int gcd(int a, int b) {
            while (a != 0) if ((b %= a) != 0) a %= b;
            else return a;
            return b;
        }

        /**
         * 配列全ての値の最大公約数を返します。
         * @param array 配列
         * @return 最大公約数
         */
        public static int gcd(int... array) {
            int ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = gcd(ret, array[i]);
            return ret;
        }

        /**
         * aとbの最大公約数を返します。
         * @param a 整数
         * @param b 整数
         * @return 最大公約数
         */
        public static long gcd(long a, long b) {
            while (a != 0) if ((b %= a) != 0) a %= b;
            else return a;
            return b;
        }

        /**
         * 配列全ての値の最大公約数を返します。
         * @param array 配列
         * @return 最大公約数
         */
        public static long gcd(long... array) {
            long ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = gcd(ret, array[i]);
            return ret;
        }

        /**
         * 配列全ての値の最小公倍数を返します。
         * @param a 整数
         * @param b 整数
         * @return 最小公倍数
         */
        public static long lcm(int a, int b) {
            return a / gcd(a, b) * (long) b;
        }

        /**
         * 配列全ての値の最小公倍数を返します。
         * @param a 整数
         * @param b 整数
         * @return 最小公倍数
         */
        public static long lcm(long a, long b) {
            return a / gcd(a, b) * b;
        }

        /**
         * 配列全ての値の最小公倍数を返します。
         * @param array 配列
         * @return 最小公倍数
         */
        public static long lcm(int... array) {
            long ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = lcm(ret, array[i]);
            return ret;
        }

        /**
         * aとbのうち、小さい方を返します。
         * @param a 整数
         * @param b 整数
         * @return aとbのうち小さい方の値
         */
        public static int min(int a, int b) {
            return a < b ? a : b;
        }

        /**
         * 配列の中で最小の値を返します。
         * @param array 配列
         * @return 配列の中で最小の値
         */
        public static int min(int... array) {
            int ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = min(ret, array[i]);
            return ret;
        }

        /**
         * aとbのうち、小さい方を返します。
         * @param a 整数
         * @param b 整数
         * @return aとbのうち小さい方の値
         */
        public static long min(long a, long b) {
            return a < b ? a : b;
        }

        /**
         * 配列の中で最小の値を返します。
         * @param array 配列
         * @return 配列の中で最小の値
         */
        public static long min(long... array) {
            long ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = min(ret, array[i]);
            return ret;
        }

        /**
         * aとbのうち、大きい方を返します。
         * @param a 整数
         * @param b 整数
         * @return aとbのうち大きい方の値
         */
        public static int max(int a, int b) {
            return a > b ? a : b;
        }

        /**
         * 配列の中で最大の値を返します。
         * @param array 配列
         * @return 配列の中で最大の値
         */
        public static int max(int... array) {
            int ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = max(ret, array[i]);
            return ret;
        }

        /**
         * aとbのうち、大きい方を返します。
         * @param a 整数
         * @param b 整数
         * @return aとbのうち大きい方の値
         */
        public static long max(long a, long b) {
            return a > b ? a : b;
        }

        /**
         * 配列の中で最大の値を返します。
         * @param array 配列
         * @return 配列の中で最大の値
         */
        public static long max(long... array) {
            long ret = array[0];
            for (int i = 1; i < array.length; ++i) ret = max(ret, array[i]);
            return ret;
        }

        /**
         * 配列の値の合計を返します。
         * @param array 配列
         * @return 配列の値の総和
         */
        public static long sum(int... array) {
            long ret = 0;
            for (int i : array) ret += i;
            return ret;
        }

        /**
         * 配列の値の合計を返します。
         * @param array 配列
         * @return 配列の値の総和
         */
        public static long sum(long... array) {
            long ret = 0;
            for (long i : array) ret += i;
            return ret;
        }

        /**
         * 二項係数を列挙した配列を返します。
         * @param l 左辺
         * @param r 右辺
         * @return 0≦i≦l及び0≦j≦rを満たす全てのi, jに対してi choose jを求めた配列
         */
        public static long[][] combination(int l, int r) {
            long[][] pascal = new long[l + 1][r + 1];
            pascal[0][0] = 1;
            for (int i = 1; i <= l; ++i) {
                pascal[i][0] = 1;
                for (int j = 1; j <= r; ++j) {
                    pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];
                }
            }
            return pascal;
        }

        /**
         * 二分探索を行い、func(x) != func(x+1)となるような数xを発見します。
         * funcが単調な関数であるとき、発見されるxは一意に定まります。
         * @param isTrue func(isTrue)=trueとなるような値
         * @param isFalse func(isFalse)=falseとなるような値
         * @param func 関数
         * @complexity O(log(max(isTrue, isFalse) - min(isTrue, isFalse)))
         * @return func(x) != func(x+1)となるような数x
         */
        public static int binarySearch(int isTrue, int isFalse, java.util.function.IntPredicate func) {
            if (isTrue <= isFalse) {
                int halfDiff = isFalse - isTrue >> 1, mid = isTrue + halfDiff;
                while(halfDiff != 0) {
                    if (func.test(mid)) isTrue = mid;
                    else isFalse = mid;
                    halfDiff = isFalse - isTrue >> 1;
                    mid = isTrue + halfDiff;
                }
                return isTrue;
            } else {
                int halfDiff = isTrue - isFalse >> 1, mid = isFalse + halfDiff;
                while(halfDiff != 0) {
                    if (func.test(mid)) isTrue = mid;
                    else isFalse = mid;
                    halfDiff = isTrue - isFalse >> 1;
                    mid = isFalse + halfDiff;
                }
                return isFalse;
            }
        }

        /**
         * 二分探索を行い、func(x) != func(x+1)となるような数xを発見します。
         * funcが単調な関数であるとき、発見されるxは一意に定まります。
         * @param isTrue func(isTrue)=trueとなるような値
         * @param isFalse func(isFalse)=falseとなるような値
         * @param func 関数
         * @complexity O(log(max(isTrue, isFalse) - min(isTrue, isFalse)))
         * @return func(x) != func(x+1)となるような数x
         */
        public static long binarySearch(long isTrue, long isFalse, java.util.function.LongPredicate func) {
            if (isTrue <= isFalse) {
                long halfDiff = isFalse - isTrue >> 1, mid = isTrue + halfDiff;
                while(halfDiff != 0) {
                    if (func.test(mid)) isTrue = mid;
                    else isFalse = mid;
                    halfDiff = isFalse - isTrue >> 1;
                    mid = isTrue + halfDiff;
                }
                return isTrue;
            } else {
                long halfDiff = isTrue - isFalse >> 1, mid = isFalse + halfDiff;
                while(halfDiff != 0) {
                    if (func.test(mid)) isTrue = mid;
                    else isFalse = mid;
                    halfDiff = isTrue - isFalse >> 1;
                    mid = isFalse + halfDiff;
                }
                return isFalse;
            }
        }

        /**
         * 二分探索を行い、func(x) != func(x+Math.nextUp(x))となるような数xを発見します。
         * funcが単調な関数であるとき、発見されるxは一意に定まります。
         * @param isTrue func(isTrue)=trueとなるような値
         * @param isFalse func(isFalse)=falseとなるような値
         * @param func 関数
         * @complexity O(log(max(isTrue, isFalse) - min(isTrue, isFalse)))
         * @return func(x) != func(x+Math.nextUp(x))となるような数x
         */
        public static double binarySearch(double isTrue, double isFalse, java.util.function.DoublePredicate func) {
            return Double.longBitsToDouble(binarySearch(Double.doubleToRawLongBits(isTrue), Double.doubleToRawLongBits(isFalse), (long i) -> func.test(Double.longBitsToDouble(i))));
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param loop 探索回数
         * @param func 関数
         * @return 極小値
         */
        public static <T extends Comparable<T>> double find_minimal(double min, double max, int loop, java.util.function.DoubleFunction<T> func) {
            return find_minimal(min, max, loop, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param loop 探索回数
         * @param func 関数
         * @param comparator 比較関数
         * @return 極小値
         */
        public static <T> double find_minimal(double min, double max, int loop, java.util.function.DoubleFunction<T> func, java.util.Comparator<T> comparator) {
            double phi = (1 + Math.sqrt(5)) / 2;
            for (int i = 0;i < loop;++ i) {
                double mid_min = (min * phi + max) / (1 + phi), mid_max = (min + max * phi) / (1 + phi);
                T mid_min_calc = func.apply(mid_min), mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) <= 0) max = mid_max;
                else min = mid_min;
            }
            return min;
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param loop 探索回数
         * @param func 関数
         * @return 極大値
         */
        public static <T extends Comparable<T>> double find_maximal(double min, double max, int loop, java.util.function.DoubleFunction<T> func) {
            return find_maximal(min, max, loop, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param loop 探索回数
         * @param func 関数
         * @param comparator 比較関数
         * @return 極大値
         */
        public static <T> double find_maximal(double min, double max, int loop, java.util.function.DoubleFunction<T> func, java.util.Comparator<T> comparator) {
            if (max <= min) throw new IllegalArgumentException("empty range");
            double phi = (1 + Math.sqrt(5)) / 2;
            for (int i = 0;i < loop;++ i) {
                double mid_min = (min * phi + max) / (1 + phi), mid_max = (min + max * phi) / (1 + phi);
                T mid_min_calc = func.apply(mid_min), mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) >= 0) max = mid_max;
                else min = mid_min;
            }
            return min;
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @return 極小値
         */
        public static <T extends Comparable<T>> int find_minimal(int min, int max, java.util.function.IntFunction<T> func) {
            return find_minimal(min, max, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @param comparator 比較関数
         * @return 極小値
         */
        public static <T> int find_minimal(int min, int max, java.util.function.IntFunction<T> func, java.util.Comparator<T> comparator) {
            -- min;
            int range = max - min;
            if (range <= 1) throw new IllegalArgumentException("empty range");
            int fib_small = 1, fib_large = 1;
            while(fib_large < range) {
                fib_large += fib_small;
                fib_small = fib_large - fib_small;
            }
            T mid_min_calc = null, mid_max_calc = null;
            int last_calc = -1;
            final int LAST_CALC_IS_MIN = 0, LAST_CALC_IS_MAX = 1;
            while(max - min > 2) {
                fib_small = fib_large - fib_small;
                fib_large -= fib_small;
                int mid_min = min + fib_small, mid_max = min + fib_large;
                if (mid_max >= max) {
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                    continue;
                }
                if (last_calc != LAST_CALC_IS_MIN) mid_min_calc = func.apply(mid_min);
                if (last_calc != LAST_CALC_IS_MAX) mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) <= 0) {
                    max = mid_max;
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                } else {
                    min = mid_min;
                    mid_min_calc = mid_max_calc;
                    last_calc = LAST_CALC_IS_MIN;
                }
            }
            return min + 1;
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @return 極大値
         */
        public static <T extends Comparable<T>> int find_maximal(int min, int max, java.util.function.IntFunction<T> func) {
            return find_maximal(min, max, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @param comparator 比較関数
         * @return 極大値
         */
        public static <T> int find_maximal(int min, int max, java.util.function.IntFunction<T> func, java.util.Comparator<T> comparator) {
            -- min;
            int range = max - min;
            if (range <= 1) throw new IllegalArgumentException("empty range");
            int fib_small = 1, fib_large = 1;
            while(fib_large < range) {
                fib_large += fib_small;
                fib_small = fib_large - fib_small;
            }
            T mid_min_calc = null, mid_max_calc = null;
            int last_calc = -1;
            final int LAST_CALC_IS_MIN = 0, LAST_CALC_IS_MAX = 1;
            while(max - min > 2) {
                fib_small = fib_large - fib_small;
                fib_large -= fib_small;
                int mid_min = min + fib_small, mid_max = min + fib_large;
                if (mid_max >= max) {
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                    continue;
                }
                if (last_calc != LAST_CALC_IS_MIN) mid_min_calc = func.apply(mid_min);
                if (last_calc != LAST_CALC_IS_MAX) mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) >= 0) {
                    max = mid_max;
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                } else {
                    min = mid_min;
                    mid_min_calc = mid_max_calc;
                    last_calc = LAST_CALC_IS_MIN;
                }
            }
            return min + 1;
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @return 極小値
         */
        public static <T extends Comparable<T>> long find_minimal(long min, long max, java.util.function.LongFunction<T> func) {
            return find_minimal(min, max, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 下に凸な関数の極小値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @param comparator 比較関数
         * @return 極小値
         */
        public static <T> long find_minimal(long min, long max, java.util.function.LongFunction<T> func, java.util.Comparator<T> comparator) {
            -- min;
            long range = max - min;
            if (range <= 1) throw new IllegalArgumentException("empty range");
            long fib_small = 1, fib_large = 1;
            while(fib_large < range) {
                fib_large += fib_small;
                fib_small = fib_large - fib_small;
            }
            T mid_min_calc = null, mid_max_calc = null;
            int last_calc = -1;
            final int LAST_CALC_IS_MIN = 0, LAST_CALC_IS_MAX = 1;
            while(max - min > 2) {
                fib_small = fib_large - fib_small;
                fib_large -= fib_small;
                long mid_min = min + fib_small, mid_max = min + fib_large;
                if (mid_max >= max) {
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                    continue;
                }
                if (last_calc != LAST_CALC_IS_MIN) mid_min_calc = func.apply(mid_min);
                if (last_calc != LAST_CALC_IS_MAX) mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) <= 0) {
                    max = mid_max;
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                } else {
                    min = mid_min;
                    mid_min_calc = mid_max_calc;
                    last_calc = LAST_CALC_IS_MIN;
                }
            }
            return min + 1;
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @return 極大値
         */
        public static <T extends Comparable<T>> long find_maximal(long min, long max, java.util.function.LongFunction<T> func) {
            return find_maximal(min, max, func, java.util.Comparator.naturalOrder());
        }

        /**
         * 上に凸な関数の極大値を発見します。
         * @param <T> 関数の終域
         * @param min 関数の定義域の下界
         * @param max 関数の定義域の上界
         * @param func 関数
         * @param comparator 比較関数
         * @return 極大値
         */
        public static <T> long find_maximal(long min, long max, java.util.function.LongFunction<T> func, java.util.Comparator<T> comparator) {
            -- min;
            long range = max - min;
            if (range <= 1) throw new IllegalArgumentException("empty range");
            long fib_small = 1, fib_large = 1;
            while(fib_large < range) {
                fib_large += fib_small;
                fib_small = fib_large - fib_small;
            }
            T mid_min_calc = null, mid_max_calc = null;
            int last_calc = -1;
            final int LAST_CALC_IS_MIN = 0, LAST_CALC_IS_MAX = 1;
            while(max - min > 2) {
                fib_small = fib_large - fib_small;
                fib_large -= fib_small;
                long mid_min = min + fib_small, mid_max = min + fib_large;
                if (mid_max >= max) {
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                    continue;
                }
                if (last_calc != LAST_CALC_IS_MIN) mid_min_calc = func.apply(mid_min);
                if (last_calc != LAST_CALC_IS_MAX) mid_max_calc = func.apply(mid_max);
                if (comparator.compare(mid_min_calc, mid_max_calc) >= 0) {
                    max = mid_max;
                    mid_max_calc = mid_min_calc;
                    last_calc = LAST_CALC_IS_MAX;
                } else {
                    min = mid_min;
                    mid_min_calc = mid_max_calc;
                    last_calc = LAST_CALC_IS_MIN;
                }
            }
            return min + 1;
        }

        public static class BezoutCoefficients {
            public final long a, b;
            public final long x, y;
            public final long gcd;
            private BezoutCoefficients(long a, long b, long x, long y, long gcd) {
                this.a = a;
                this.b = b;
                this.x = x;
                this.y = y;
                this.gcd = gcd;
            }
            /**
             * lx≦i<rxかつly≦j<ryを満たす整数i, jであって、ai+bj=ax+byとなる解の個数を求めます。
             * @param lx iの下限(これを含む)
             * @param rx iの上限(これを含まない)
             * @param ly jの下限(これを含む)
             * @param ry jの上限(これを含まない)
             * @return 解の個数
             * @complexity O(1)
             */
            public long countSatisfySolution(long lx, long rx, long ly, long ry) {
                long ag = a / gcd, bg = b / gcd;
                long la = Math.floorDiv(lx - x + bg - 1, bg), ra = Math.floorDiv(rx - x - 1, bg) + 1;
                long lb = Math.floorDiv(y - ry, ag) + 1, rb = Math.floorDiv(y - ly, ag) + 1;
                return Math.max(0, Math.min(ra, rb) - Math.max(la, lb));
            }
            @Override
            public String toString() {
                return "(" + x + ", " + y + "), gcd=" + gcd;
            }
            /**
             * ax+by=gcd(a, b)となるような解を一つ求めます。
             * この時、|x|≦|b/gcd(a,b)|、|y|≦|a/gcd(a,b)|であることが保証されます。
             * @param a 整数
             * @param b 整数
             * @return 与えられた一次不定方程式の解
             * @complexity O(log(min(a, b)))
             */
            public static BezoutCoefficients solve(long a, long b) {
                int as = Long.signum(a);
                int bs = Long.signum(b);
                long aa = Math.abs(a);
                long ba = Math.abs(b);
                long p = 1, q = 0, r = 0, s = 1;
                while(ba != 0){
                    long c = aa / ba;
                    long e;
                    e = aa; aa = ba; ba = e % ba;
                    e = p; p = q; q = e - c * q;
                    e = r; r = s; s = e - c * s;
                }
                return new BezoutCoefficients(a, b, p * as, r * bs, aa);
            }
            /**
             * ax+by=dとなるような解を一つ求めます。
             * @param a 整数
             * @param b 整数
             * @param d 不定方程式の解
             * @return 与えられた一次不定方程式の解(存在しなければnull)
             * @complexity O(log(min(a, b)))
             */
            public static BezoutCoefficients solve(long a, long b, long d) {
                int as = Long.signum(a);
                int bs = Long.signum(b);
                long aa = Math.abs(a);
                long ba = Math.abs(b);
                long p = 1, q = 0, r = 0, s = 1;
                while(ba != 0){
                    long c = aa / ba;
                    long e;
                    e = aa; aa = ba; ba = e % ba;
                    e = p; p = q; q = e - c * q;
                    e = r; r = s; s = e - c * s;
                }
                if (d % aa != 0) return null;
                long divd = d / a, modd = d % a / aa;
                return new BezoutCoefficients(a, b, p * as * modd + divd, r * bs * modd, aa);
            }
        }
    }

    /**
     * @verified https://atcoder.jp/contests/practice2/tasks/practice2_d
     */
    public static final class MaxFlow {
        private static final class InternalCapEdge {
            final int to;
            final int rev;
            long cap;

            InternalCapEdge(int to, int rev, long cap) {
                this.to = to;
                this.rev = rev;
                this.cap = cap;
            }
        }

        public static final class CapEdge {
            public final int from, to;
            public final long cap, flow;

            CapEdge(int from, int to, long cap, long flow) {
                this.from = from;
                this.to = to;
                this.cap = cap;
                this.flow = flow;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof CapEdge) {
                    CapEdge e = (CapEdge) o;
                    return from == e.from && to == e.to && cap == e.cap && flow == e.flow;
                }
                return false;
            }
        }

        private static final class IntPair {
            final int first, second;

            IntPair(int first, int second) {
                this.first = first;
                this.second = second;
            }
        }

        static final long INF = Long.MAX_VALUE;

        private final int n;
        private final java.util.ArrayList<IntPair> pos;
        private final java.util.ArrayList<InternalCapEdge>[] g;

        @SuppressWarnings("unchecked")
        public MaxFlow(int n) {
            this.n = n;
            pos = new java.util.ArrayList<>();
            g = new java.util.ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new java.util.ArrayList<>();
            }
        }

        public int addEdge(int from, int to, long cap) {
            rangeCheck(from, 0, n);
            rangeCheck(to, 0, n);
            nonNegativeCheck(cap, "Capacity");
            int m = pos.size();
            pos.add(new IntPair(from, g[from].size()));
            int fromId = g[from].size();
            int toId = g[to].size();
            if (from == to) toId++;
            g[from].add(new InternalCapEdge(to, toId, cap));
            g[to].add(new InternalCapEdge(from, fromId, 0L));
            return m;
        }

        private InternalCapEdge getInternalEdge(int i) {
            return g[pos.get(i).first].get(pos.get(i).second);
        }

        private InternalCapEdge getInternalEdgeReversed(InternalCapEdge e) {
            return g[e.to].get(e.rev);
        }

        public CapEdge getEdge(int i) {
            int m = pos.size();
            rangeCheck(i, 0, m);
            InternalCapEdge e = getInternalEdge(i);
            InternalCapEdge re = getInternalEdgeReversed(e);
            return new CapEdge(re.to, e.to, e.cap + re.cap, re.cap);
        }

        //求所有边的流量，容量情况
        public CapEdge[] getEdges() {
            CapEdge[] res = new CapEdge[pos.size()];
            Arrays.setAll(res, this::getEdge);
            return res;
        }

        public void changeEdge(int i, long newCap, long newFlow) {
            int m = pos.size();
            rangeCheck(i, 0, m);
            nonNegativeCheck(newCap, "Capacity");
            if (newFlow > newCap) {
                throw new IllegalArgumentException(
                        String.format("Flow %d is greater than the capacity %d.", newCap, newFlow));
            }
            InternalCapEdge e = getInternalEdge(i);
            InternalCapEdge re = getInternalEdgeReversed(e);
            e.cap = newCap - newFlow;
            re.cap = newFlow;
        }

        public long maxFlow(int s, int t) {
            return flow(s, t, INF);
        }

        public long flow(int s, int t, long flowLimit) {
            rangeCheck(s, 0, n);
            rangeCheck(t, 0, n);
            long flow = 0L;
            int[] level = new int[n];
            int[] que = new int[n];
            int[] iter = new int[n];
            while (flow < flowLimit) {
                bfs(s, t, level, que);
                if (level[t] < 0) break;
                Arrays.fill(iter, 0);
                while (flow < flowLimit) {
                    long d = dfs(t, s, flowLimit - flow, iter, level);
                    if (d == 0) break;
                    flow += d;
                }
            }
            return flow;
        }

        private void bfs(int s, int t, int[] level, int[] que) {
            Arrays.fill(level, -1);
            int hd = 0, tl = 0;
            que[tl++] = s;
            level[s] = 0;
            while (hd < tl) {
                int u = que[hd++];
                for (InternalCapEdge e : g[u]) {
                    int v = e.to;
                    if (e.cap == 0 || level[v] >= 0) continue;
                    level[v] = level[u] + 1;
                    if (v == t) return;
                    que[tl++] = v;
                }
            }
        }

        private long dfs(int cur, int s, long flowLimit, int[] iter, int[] level) {
            if (cur == s) return flowLimit;
            long res = 0;
            int curLevel = level[cur];
            for (int itMax = g[cur].size(); iter[cur] < itMax; iter[cur]++) {
                int i = iter[cur];
                InternalCapEdge e = g[cur].get(i);
                InternalCapEdge re = getInternalEdgeReversed(e);
                if (curLevel <= level[e.to] || re.cap == 0) continue;
                long d = dfs(e.to, s, Math.min(flowLimit - res, re.cap), iter, level);
                if (d <= 0) continue;
                e.cap += d;
                re.cap -= d;
                res += d;
                if (res == flowLimit) break;
            }
            return res;
        }

        //求最小割
        public boolean[] minCut(int s) {
            rangeCheck(s, 0, n);
            boolean[] visited = new boolean[n];
            int[] stack = new int[n];
            int ptr = 0;
            stack[ptr++] = s;
            visited[s] = true;
            while (ptr > 0) {
                int u = stack[--ptr];
                for (InternalCapEdge e : g[u]) {
                    int v = e.to;
                    if (e.cap > 0 && !visited[v]) {
                        visited[v] = true;
                        stack[ptr++] = v;
                    }
                }
            }
            return visited;
        }

        private void rangeCheck(int i, int minInclusive, int maxExclusive) {
            if (i < 0 || i >= maxExclusive) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for length %d", i, maxExclusive));
            }
        }

        private void nonNegativeCheck(long cap, String attribute) {
            if (cap < 0) { throw new IllegalArgumentException(String.format("%s %d is negative.", attribute, cap)); }
        }
    }

    /**
     * @verified
     * - https://atcoder.jp/contests/practice2/tasks/practice2_e
     * - http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_6_B
     */
    public static final class MinCostFlow {
        private static final class InternalWeightedCapEdge {
            final int to, rev;
            long cap;
            final long cost;

            InternalWeightedCapEdge(int to, int rev, long cap, long cost) {
                this.to = to;
                this.rev = rev;
                this.cap = cap;
                this.cost = cost;
            }
        }

        public static final class WeightedCapEdge {
            public final int from, to;
            public final long cap, flow, cost;

            WeightedCapEdge(int from, int to, long cap, long flow, long cost) {
                this.from = from;
                this.to = to;
                this.cap = cap;
                this.flow = flow;
                this.cost = cost;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof WeightedCapEdge) {
                    WeightedCapEdge e = (WeightedCapEdge) o;
                    return from == e.from && to == e.to && cap == e.cap && flow == e.flow && cost == e.cost;
                }
                return false;
            }
        }

        private static final class IntPair {
            final int first, second;

            IntPair(int first, int second) {
                this.first = first;
                this.second = second;
            }
        }

        public static final class FlowAndCost {
            public final long flow, cost;

            FlowAndCost(long flow, long cost) {
                this.flow = flow;
                this.cost = cost;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof FlowAndCost) {
                    FlowAndCost c = (FlowAndCost) o;
                    return flow == c.flow && cost == c.cost;
                }
                return false;
            }
        }

        static final long INF = Long.MAX_VALUE;

        private final int n;
        private final java.util.ArrayList<IntPair> pos;
        private final java.util.ArrayList<InternalWeightedCapEdge>[] g;

        @SuppressWarnings("unchecked")
        public MinCostFlow(int n) {
            this.n = n;
            pos = new java.util.ArrayList<>();
            g = new java.util.ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new java.util.ArrayList<>();
            }
        }

        public int addEdge(int from, int to, long cap, long cost) {
            rangeCheck(from, 0, n);
            rangeCheck(to, 0, n);
            nonNegativeCheck(cap, "Capacity");
            nonNegativeCheck(cost, "Cost");
            int m = pos.size();
            pos.add(new IntPair(from, g[from].size()));
            int fromId = g[from].size();
            int toId = g[to].size();
            if (from == to) toId++;
            g[from].add(new InternalWeightedCapEdge(to, toId, cap, cost));
            g[to].add(new InternalWeightedCapEdge(from, fromId, 0L, -cost));
            return m;
        }

        private InternalWeightedCapEdge getInternalEdge(int i) {
            return g[pos.get(i).first].get(pos.get(i).second);
        }

        private InternalWeightedCapEdge getInternalEdgeReversed(InternalWeightedCapEdge e) {
            return g[e.to].get(e.rev);
        }

        public WeightedCapEdge getEdge(int i) {
            int m = pos.size();
            rangeCheck(i, 0, m);
            InternalWeightedCapEdge e = getInternalEdge(i);
            InternalWeightedCapEdge re = getInternalEdgeReversed(e);
            return new WeightedCapEdge(re.to, e.to, e.cap + re.cap, re.cap, e.cost);
        }

        public WeightedCapEdge[] getEdges() {
            WeightedCapEdge[] res = new WeightedCapEdge[pos.size()];
            Arrays.setAll(res, this::getEdge);
            return res;
        }

        public FlowAndCost minCostMaxFlow(int s, int t) {
            return minCostFlow(s, t, INF);
        }

        public FlowAndCost minCostFlow(int s, int t, long flowLimit) {
            return minCostSlope(s, t, flowLimit).getLast();
        }

        public java.util.ArrayList<Long> minCostList(int s, int t) {
            return minCostList(s, t, INF);
        }

        public java.util.ArrayList<Long> minCostList(int s, int t, long flowLimit) {
            java.util.LinkedList<FlowAndCost> list = minCostSlope(s, t, flowLimit);
            FlowAndCost last = list.pollFirst();
            java.util.ArrayList<Long> ret = new java.util.ArrayList<>();
            ret.add(0L);
            while(!list.isEmpty()) {
                FlowAndCost now = list.pollFirst();
                for (long i = last.flow + 1;i <= now.flow;++ i) {
                    ret.add(last.cost + (i - last.flow) * (now.cost - last.cost) / (now.flow - last.flow));
                }
                last = now;
            }
            return ret;
        }

        java.util.LinkedList<FlowAndCost> minCostSlope(int s, int t) {
            return minCostSlope(s, t, INF);
        }

        public java.util.LinkedList<FlowAndCost> minCostSlope(int s, int t, long flowLimit) {
            rangeCheck(s, 0, n);
            rangeCheck(t, 0, n);
            if (s == t) { throw new IllegalArgumentException(String.format("%d and %d is the same vertex.", s, t)); }
            long[] dual = new long[n];
            long[] dist = new long[n];
            int[] pv = new int[n];
            int[] pe = new int[n];
            boolean[] vis = new boolean[n];
            long flow = 0;
            long cost = 0, prev_cost = -1;
            java.util.LinkedList<FlowAndCost> result = new java.util.LinkedList<>();
            result.addLast(new FlowAndCost(flow, cost));
            while (flow < flowLimit) {
                if (!dualRef(s, t, dual, dist, pv, pe, vis)) break;
                long c = flowLimit - flow;
                for (int v = t; v != s; v = pv[v]) {
                    c = Math.min(c, g[pv[v]].get(pe[v]).cap);
                }
                for (int v = t; v != s; v = pv[v]) {
                    InternalWeightedCapEdge e = g[pv[v]].get(pe[v]);
                    e.cap -= c;
                    g[v].get(e.rev).cap += c;
                }
                long d = -dual[s];
                flow += c;
                cost += c * d;
                if (prev_cost == d) {
                    result.removeLast();
                }
                result.addLast(new FlowAndCost(flow, cost));
                prev_cost = cost;
            }
            return result;
        }

        private boolean dualRef(int s, int t, long[] dual, long[] dist, int[] pv, int[] pe, boolean[] vis) {
            Arrays.fill(dist, INF);
            Arrays.fill(pv, -1);
            Arrays.fill(pe, -1);
            Arrays.fill(vis, false);
            class State implements Comparable<State> {
                final long key;
                final int to;

                State(long key, int to) {
                    this.key = key;
                    this.to = to;
                }

                @Override
                public int compareTo(State q) {
                    return key > q.key ? 1 : -1;
                }
            };
            java.util.PriorityQueue<State> pq = new java.util.PriorityQueue<>();
            dist[s] = 0;
            pq.add(new State(0L, s));
            while (pq.size() > 0) {
                int v = pq.poll().to;
                if (vis[v]) continue;
                vis[v] = true;
                if (v == t) break;
                for (int i = 0, deg = g[v].size(); i < deg; i++) {
                    InternalWeightedCapEdge e = g[v].get(i);
                    if (vis[e.to] || e.cap == 0) continue;
                    long cost = e.cost - dual[e.to] + dual[v];
                    if (dist[e.to] - dist[v] > cost) {
                        dist[e.to] = dist[v] + cost;
                        pv[e.to] = v;
                        pe[e.to] = i;
                        pq.add(new State(dist[e.to], e.to));
                    }
                }
            }
            if (!vis[t]) { return false; }

            for (int v = 0; v < n; v++) {
                if (!vis[v]) continue;
                dual[v] -= dist[t] - dist[v];
            }
            return true;
        }

        private void rangeCheck(int i, int minInlusive, int maxExclusive) {
            if (i < 0 || i >= maxExclusive) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for length %d", i, maxExclusive));
            }
        }

        private void nonNegativeCheck(long cap, String attribute) {
            if (cap < 0) { throw new IllegalArgumentException(String.format("%s %d is negative.", attribute, cap)); }
        }
    }

    /**
     * @verified
     *           <ul>
     *           <li>https://atcoder.jp/contests/arc050/tasks/arc050_c
     *           <li>https://atcoder.jp/contests/abc129/tasks/abc129_f
     *           </ul>
     */
    public static final class ModIntFactory {

        private final ModArithmetic ma;
        private final int mod;

        public ModIntFactory(final int mod) {
            ma = ModArithmetic.of(mod);
            this.mod = mod;
        }

        public ModInt create(long value) {
            if ((value %= mod) < 0) value += mod;
            if (ma instanceof ModArithmetic.ModArithmeticMontgomery) {
                return new ModInt(((ModArithmetic.ModArithmeticMontgomery) ma).generate(value));
            }
            return new ModInt((int) value);
        }

        class ModInt {

            private int value;

            private ModInt(final int value) {
                this.value = value;
            }

            public int mod() {
                return mod;
            }

            public int value() {
                if (ma instanceof ModArithmetic.ModArithmeticMontgomery) {
                    return ((ModArithmetic.ModArithmeticMontgomery) ma).reduce(value);
                }
                return value;
            }

            public ModInt add(final ModInt mi) {
                return new ModInt(ma.add(value, mi.value));
            }

            public ModInt add(final ModInt mi1, final ModInt mi2) {
                return new ModInt(ma.add(value, mi1.value)).addAsg(mi2);
            }

            public ModInt add(final ModInt mi1, final ModInt mi2, final ModInt mi3) {
                return new ModInt(ma.add(value, mi1.value)).addAsg(mi2).addAsg(mi3);
            }

            public ModInt add(final ModInt mi1, final ModInt mi2, final ModInt mi3, final ModInt mi4) {
                return new ModInt(ma.add(value, mi1.value)).addAsg(mi2).addAsg(mi3).addAsg(mi4);
            }

            public ModInt add(final ModInt mi1, final ModInt... mis) {
                final ModInt mi = add(mi1);
                for (final ModInt m : mis) mi.addAsg(m);
                return mi;
            }

            public ModInt add(final long mi) {
                return new ModInt(ma.add(value, ma.remainder(mi)));
            }

            public ModInt sub(final ModInt mi) {
                return new ModInt(ma.sub(value, mi.value));
            }

            public ModInt sub(final long mi) {
                return new ModInt(ma.sub(value, ma.remainder(mi)));
            }

            public ModInt mul(final ModInt mi) {
                return new ModInt(ma.mul(value, mi.value));
            }

            public ModInt mul(final ModInt mi1, final ModInt mi2) {
                return new ModInt(ma.mul(value, mi1.value)).mulAsg(mi2);
            }

            public ModInt mul(final ModInt mi1, final ModInt mi2, final ModInt mi3) {
                return new ModInt(ma.mul(value, mi1.value)).mulAsg(mi2).mulAsg(mi3);
            }

            public ModInt mul(final ModInt mi1, final ModInt mi2, final ModInt mi3, final ModInt mi4) {
                return new ModInt(ma.mul(value, mi1.value)).mulAsg(mi2).mulAsg(mi3).mulAsg(mi4);
            }

            public ModInt mul(final ModInt mi1, final ModInt... mis) {
                final ModInt mi = mul(mi1);
                for (final ModInt m : mis) mi.mulAsg(m);
                return mi;
            }

            public ModInt mul(final long mi) {
                return new ModInt(ma.mul(value, ma.remainder(mi)));
            }

            public ModInt div(final ModInt mi) {
                return new ModInt(ma.div(value, mi.value));
            }

            public ModInt div(final long mi) {
                return new ModInt(ma.div(value, ma.remainder(mi)));
            }

            public ModInt inv() {
                return new ModInt(ma.inv(value));
            }

            public ModInt pow(final long b) {
                return new ModInt(ma.pow(value, b));
            }

            public ModInt addAsg(final ModInt mi) {
                value = ma.add(value, mi.value);
                return this;
            }

            public ModInt addAsg(final ModInt mi1, final ModInt mi2) {
                return addAsg(mi1).addAsg(mi2);
            }

            public ModInt addAsg(final ModInt mi1, final ModInt mi2, final ModInt mi3) {
                return addAsg(mi1).addAsg(mi2).addAsg(mi3);
            }

            public ModInt addAsg(final ModInt mi1, final ModInt mi2, final ModInt mi3, final ModInt mi4) {
                return addAsg(mi1).addAsg(mi2).addAsg(mi3).addAsg(mi4);
            }

            public ModInt addAsg(final ModInt... mis) {
                for (final ModInt m : mis) addAsg(m);
                return this;
            }

            public ModInt addAsg(final long mi) {
                value = ma.add(value, ma.remainder(mi));
                return this;
            }

            public ModInt subAsg(final ModInt mi) {
                value = ma.sub(value, mi.value);
                return this;
            }

            public ModInt subAsg(final long mi) {
                value = ma.sub(value, ma.remainder(mi));
                return this;
            }

            public ModInt mulAsg(final ModInt mi) {
                value = ma.mul(value, mi.value);
                return this;
            }

            public ModInt mulAsg(final ModInt mi1, final ModInt mi2) {
                return mulAsg(mi1).mulAsg(mi2);
            }

            public ModInt mulAsg(final ModInt mi1, final ModInt mi2, final ModInt mi3) {
                return mulAsg(mi1).mulAsg(mi2).mulAsg(mi3);
            }

            public ModInt mulAsg(final ModInt mi1, final ModInt mi2, final ModInt mi3, final ModInt mi4) {
                return mulAsg(mi1).mulAsg(mi2).mulAsg(mi3).mulAsg(mi4);
            }

            public ModInt mulAsg(final ModInt... mis) {
                for (final ModInt m : mis) mulAsg(m);
                return this;
            }

            public ModInt mulAsg(final long mi) {
                value = ma.mul(value, ma.remainder(mi));
                return this;
            }

            public ModInt divAsg(final ModInt mi) {
                value = ma.div(value, mi.value);
                return this;
            }

            public ModInt divAsg(final long mi) {
                value = ma.div(value, ma.remainder(mi));
                return this;
            }

            @Override
            public String toString() {
                return String.valueOf(value());
            }

            @Override
            public boolean equals(final Object o) {
                if (o instanceof ModInt) {
                    final ModInt mi = (ModInt) o;
                    return mod() == mi.mod() && value() == mi.value();
                }
                return false;
            }

            @Override
            public int hashCode() {
                return (1 * 37 + mod()) * 37 + value();
            }
        }

        private interface ModArithmetic {

            public int mod();

            public int remainder(long value);

            public int add(int a, int b);

            public int sub(int a, int b);

            public int mul(int a, int b);

            public default int div(final int a, final int b) {
                return mul(a, inv(b));
            }

            public int inv(int a);

            public int pow(int a, long b);

            public static ModArithmetic of(final int mod) {
                if (mod <= 0) {
                    throw new IllegalArgumentException();
                } else if (mod == 1) {
                    return new ModArithmetic1();
                } else if (mod == 2) {
                    return new ModArithmetic2();
                } else if (mod == 998244353) {
                    return new ModArithmetic998244353();
                } else if (mod == 1000000007) {
                    return new ModArithmetic1000000007();
                } else if ((mod & 1) == 1) {
                    return new ModArithmeticMontgomery(mod);
                } else {
                    return new ModArithmeticBarrett(mod);
                }
            }

            static final class ModArithmetic1 implements ModArithmetic {

                @Override
                public int mod() {
                    return 1;
                }

                @Override
                public int remainder(final long value) {
                    return 0;
                }

                @Override
                public int add(final int a, final int b) {
                    return 0;
                }

                @Override
                public int sub(final int a, final int b) {
                    return 0;
                }

                @Override
                public int mul(final int a, final int b) {
                    return 0;
                }

                @Override
                public int inv(final int a) {
                    throw new ArithmeticException("divide by zero");
                }

                @Override
                public int pow(final int a, final long b) {
                    return 0;
                }
            }

            static final class ModArithmetic2 implements ModArithmetic {

                @Override
                public int mod() {
                    return 2;
                }

                @Override
                public int remainder(final long value) {
                    return (int) (value & 1);
                }

                @Override
                public int add(final int a, final int b) {
                    return a ^ b;
                }

                @Override
                public int sub(final int a, final int b) {
                    return a ^ b;
                }

                @Override
                public int mul(final int a, final int b) {
                    return a & b;
                }

                @Override
                public int inv(final int a) {
                    if (a == 0) throw new ArithmeticException("divide by zero");
                    return a;
                }

                @Override
                public int pow(final int a, final long b) {
                    if (b == 0) return 1;
                    return a;
                }
            }

            static final class ModArithmetic998244353 implements ModArithmetic {

                private final int mod = 998244353;

                @Override
                public int mod() {
                    return mod;
                }

                @Override
                public int remainder(long value) {
                    return (int) ((value %= mod) < 0 ? value + mod : value);
                }

                @Override
                public int add(final int a, final int b) {
                    final int res = a + b;
                    return res >= mod ? res - mod : res;
                }

                @Override
                public int sub(final int a, final int b) {
                    final int res = a - b;
                    return res < 0 ? res + mod : res;
                }

                @Override
                public int mul(final int a, final int b) {
                    return (int) ((long) a * b % mod);
                }

                @Override
                public int inv(int a) {
                    int b = mod;
                    long u = 1, v = 0;
                    while (b >= 1) {
                        final long t = a / b;
                        a -= t * b;
                        final int tmp1 = a;
                        a = b;
                        b = tmp1;
                        u -= t * v;
                        final long tmp2 = u;
                        u = v;
                        v = tmp2;
                    }
                    u %= mod;
                    if (a != 1) { throw new ArithmeticException("divide by zero"); }
                    return (int) (u < 0 ? u + mod : u);
                }

                @Override
                public int pow(final int a, long b) {
                    if (b < 0) throw new ArithmeticException("negative power");
                    long res = 1;
                    long pow2 = a;
                    long idx = 1;
                    while (b > 0) {
                        final long lsb = b & -b;
                        for (; lsb != idx; idx <<= 1) {
                            pow2 = pow2 * pow2 % mod;
                        }
                        res = res * pow2 % mod;
                        b ^= lsb;
                    }
                    return (int) res;
                }
            }

            static final class ModArithmetic1000000007 implements ModArithmetic {

                private final int mod = 1000000007;

                @Override
                public int mod() {
                    return mod;
                }

                @Override
                public int remainder(long value) {
                    return (int) ((value %= mod) < 0 ? value + mod : value);
                }

                @Override
                public int add(final int a, final int b) {
                    final int res = a + b;
                    return res >= mod ? res - mod : res;
                }

                @Override
                public int sub(final int a, final int b) {
                    final int res = a - b;
                    return res < 0 ? res + mod : res;
                }

                @Override
                public int mul(final int a, final int b) {
                    return (int) ((long) a * b % mod);
                }

                @Override
                public int div(final int a, final int b) {
                    return mul(a, inv(b));
                }

                @Override
                public int inv(int a) {
                    int b = mod;
                    long u = 1, v = 0;
                    while (b >= 1) {
                        final long t = a / b;
                        a -= t * b;
                        final int tmp1 = a;
                        a = b;
                        b = tmp1;
                        u -= t * v;
                        final long tmp2 = u;
                        u = v;
                        v = tmp2;
                    }
                    u %= mod;
                    if (a != 1) { throw new ArithmeticException("divide by zero"); }
                    return (int) (u < 0 ? u + mod : u);
                }

                @Override
                public int pow(final int a, long b) {
                    if (b < 0) throw new ArithmeticException("negative power");
                    long res = 1;
                    long pow2 = a;
                    long idx = 1;
                    while (b > 0) {
                        final long lsb = b & -b;
                        for (; lsb != idx; idx <<= 1) {
                            pow2 = pow2 * pow2 % mod;
                        }
                        res = res * pow2 % mod;
                        b ^= lsb;
                    }
                    return (int) res;
                }
            }

            static final class ModArithmeticMontgomery extends ModArithmeticDynamic {

                private final long negInv;
                private final long r2, r3;

                private ModArithmeticMontgomery(final int mod) {
                    super(mod);
                    long inv = 0;
                    long s = 1, t = 0;
                    for (int i = 0; i < 32; i++) {
                        if ((t & 1) == 0) {
                            t += mod;
                            inv += s;
                        }
                        t >>= 1;
                        s <<= 1;
                    }
                    final long r = (1l << 32) % mod;
                    negInv = inv;
                    r2 = r * r % mod;
                    r3 = r2 * r % mod;
                }

                private int generate(final long x) {
                    return reduce(x * r2);
                }

                private int reduce(long x) {
                    x = x + (x * negInv & 0xffff_ffffl) * mod >>> 32;
                    return (int) (x < mod ? x : x - mod);
                }

                @Override
                public int remainder(long value) {
                    return generate((value %= mod) < 0 ? value + mod : value);
                }

                @Override
                public int mul(final int a, final int b) {
                    return reduce((long) a * b);
                }

                @Override
                public int inv(int a) {
                    a = super.inv(a);
                    return reduce(a * r3);
                }

                @Override
                public int pow(final int a, final long b) {
                    return generate(super.pow(a, b));
                }
            }

            static final class ModArithmeticBarrett extends ModArithmeticDynamic {

                private static final long mask = 0xffff_ffffl;
                private final long mh;
                private final long ml;

                private ModArithmeticBarrett(final int mod) {
                    super(mod);
                    /**
                     * m = floor(2^64/mod) 2^64 = p*mod + q, 2^32 = a*mod + b => (a*mod + b)^2 =
                     * p*mod + q => p = mod*a^2 + 2ab + floor(b^2/mod)
                     */
                    final long a = (1l << 32) / mod;
                    final long b = (1l << 32) % mod;
                    final long m = a * a * mod + 2 * a * b + b * b / mod;
                    mh = m >>> 32;
                    ml = m & mask;
                }

                private int reduce(long x) {
                    long z = (x & mask) * ml;
                    z = (x & mask) * mh + (x >>> 32) * ml + (z >>> 32);
                    z = (x >>> 32) * mh + (z >>> 32);
                    x -= z * mod;
                    return (int) (x < mod ? x : x - mod);
                }

                @Override
                public int remainder(long value) {
                    return (int) ((value %= mod) < 0 ? value + mod : value);
                }

                @Override
                public int mul(final int a, final int b) {
                    return reduce((long) a * b);
                }
            }

            static class ModArithmeticDynamic implements ModArithmetic {

                final int mod;

                public ModArithmeticDynamic(final int mod) {
                    this.mod = mod;
                }

                @Override
                public int mod() {
                    return mod;
                }

                @Override
                public int remainder(long value) {
                    return (int) ((value %= mod) < 0 ? value + mod : value);
                }

                @Override
                public int add(final int a, final int b) {
                    final int sum = a + b;
                    return sum >= mod ? sum - mod : sum;
                }

                @Override
                public int sub(final int a, final int b) {
                    final int sum = a - b;
                    return sum < 0 ? sum + mod : sum;
                }

                @Override
                public int mul(final int a, final int b) {
                    return (int) ((long) a * b % mod);
                }

                @Override
                public int inv(int a) {
                    int b = mod;
                    long u = 1, v = 0;
                    while (b >= 1) {
                        final long t = a / b;
                        a -= t * b;
                        final int tmp1 = a;
                        a = b;
                        b = tmp1;
                        u -= t * v;
                        final long tmp2 = u;
                        u = v;
                        v = tmp2;
                    }
                    u %= mod;
                    if (a != 1) { throw new ArithmeticException("divide by zero"); }
                    return (int) (u < 0 ? u + mod : u);
                }

                @Override
                public int pow(final int a, long b) {
                    if (b < 0) throw new ArithmeticException("negative power");
                    int res = 1;
                    int pow2 = a;
                    long idx = 1;
                    while (b > 0) {
                        final long lsb = b & -b;
                        for (; lsb != idx; idx <<= 1) {
                            pow2 = mul(pow2, pow2);
                        }
                        res = mul(res, pow2);
                        b ^= lsb;
                    }
                    return res;
                }
            }
        }
    }

    /**
     * Convolution.
     *
     * @verified https://atcoder.jp/contests/practice2/tasks/practice2_f
     * @verified https://judge.yosupo.jp/problem/convolution_mod_1000000007
     */
    public static final class Convolution {
        /**
         * writer: amotama 勝手に借りてます、問題あったらごめんね
         */
        private static void fft(double[] a, double[] b, boolean invert) {
            int count = a.length;
            for (int i = 1, j = 0; i < count; i++) {
                int bit = count >> 1;
                for (; j >= bit; bit >>= 1) {
                    j -= bit;
                }
                j += bit;
                if (i < j) {
                    double temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    temp = b[i];
                    b[i] = b[j];
                    b[j] = temp;
                }
            }
            for (int len = 2; len <= count; len <<= 1) {
                int halfLen = len >> 1;
                double angle = 2 * Math.PI / len;
                if (invert) {
                    angle = -angle;
                }
                double wLenA = Math.cos(angle);
                double wLenB = Math.sin(angle);
                for (int i = 0; i < count; i += len) {
                    double wA = 1;
                    double wB = 0;
                    for (int j = 0; j < halfLen; j++) {
                        double uA = a[i + j];
                        double uB = b[i + j];
                        double vA = a[i + j + halfLen] * wA - b[i + j + halfLen] * wB;
                        double vB = a[i + j + halfLen] * wB + b[i + j + halfLen] * wA;
                        a[i + j] = uA + vA;
                        b[i + j] = uB + vB;
                        a[i + j + halfLen] = uA - vA;
                        b[i + j + halfLen] = uB - vB;
                        double nextWA = wA * wLenA - wB * wLenB;
                        wB = wA * wLenB + wB * wLenA;
                        wA = nextWA;
                    }
                }
            }
            if (invert) {
                for (int i = 0; i < count; i++) {
                    a[i] /= count;
                    b[i] /= count;
                }
            }
        }

        /**
         * writer: amotama 勝手に借りてます、問題あったらごめんね
         */
        public static long[] convolution(long[] a, long[] b) {
            int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
            resultSize = Math.max(resultSize, 1);
            double[] aReal = new double[resultSize];
            double[] aImaginary = new double[resultSize];
            double[] bReal = new double[resultSize];
            double[] bImaginary = new double[resultSize];
            for (int i = 0; i < a.length; i++) aReal[i] = a[i];
            for (int i = 0; i < b.length; i++) bReal[i] = b[i];

            fft(aReal, aImaginary, false);
            if (a == b) {
                System.arraycopy(aReal, 0, bReal, 0, aReal.length);
                System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
            } else {
                fft(bReal, bImaginary, false);
            }
            for (int i = 0; i < resultSize; i++) {
                double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
                aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
                aReal[i] = real;
            }
            fft(aReal, aImaginary, true);
            long[] result = new long[a.length + b.length - 1];
            for (int i = 0; i < result.length; i++) result[i] = Math.round(aReal[i]);
            return result;
        }

        /**
         * writer: amotama 勝手に借りてます、問題あったらごめんね
         */
        public static int[] convolution(int[] a, int[] b) {
            int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
            resultSize = Math.max(resultSize, 1);
            double[] aReal = new double[resultSize];
            double[] aImaginary = new double[resultSize];
            double[] bReal = new double[resultSize];
            double[] bImaginary = new double[resultSize];
            for (int i = 0; i < a.length; i++) aReal[i] = a[i];
            for (int i = 0; i < b.length; i++) bReal[i] = b[i];
            fft(aReal, aImaginary, false);
            if (a == b) {
                System.arraycopy(aReal, 0, bReal, 0, aReal.length);
                System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
            } else {
                fft(bReal, bImaginary, false);
            }
            for (int i = 0; i < resultSize; i++) {
                double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
                aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
                aReal[i] = real;
            }
            fft(aReal, aImaginary, true);
            int[] result = new int[a.length + b.length - 1];
            for (int i = 0; i < result.length; i++) result[i] = (int) Math.round(aReal[i]);
            return result;
        }

        public static double[] convolution(double[] a, double[] b) {
            int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
            resultSize = Math.max(resultSize, 1);
            double[] aReal = Arrays.copyOf(a, resultSize);
            double[] aImaginary = new double[resultSize];
            double[] bReal = Arrays.copyOf(b, resultSize);
            double[] bImaginary = new double[resultSize];
            fft(aReal, aImaginary, false);
            if (a == b) {
                System.arraycopy(aReal, 0, bReal, 0, aReal.length);
                System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
            } else {
                fft(bReal, bImaginary, false);
            }
            for (int i = 0; i < resultSize; i++) {
                double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
                aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
                aReal[i] = real;
            }
            fft(aReal, aImaginary, true);
            return Arrays.copyOf(aReal, a.length + b.length - 1);
        }

        /**
         * Find a primitive root.
         *
         * @param m A prime number.
         * @return Primitive root.
         */
        private static int primitiveRoot(final int m) {
            if (m == 2) return 1;
            if (m == 167772161) return 3;
            if (m == 469762049) return 3;
            if (m == 754974721) return 11;
            if (m == 998244353) return 3;

            final int[] divs = new int[20];
            divs[0] = 2;
            int cnt = 1;
            int x = (m - 1) / 2;
            while (x % 2 == 0) x /= 2;
            for (int i = 3; (long) i * i <= x; i += 2) {
                if (x % i == 0) {
                    divs[cnt++] = i;
                    while (x % i == 0) {
                        x /= i;
                    }
                }
            }
            if (x > 1) {
                divs[cnt++] = x;
            }
            for (int g = 2;; g++) {
                boolean ok = true;
                for (int i = 0; i < cnt; i++) {
                    if (MathLib.pow(g, (m - 1) / divs[i], m) == 1) {
                        ok = false;
                        break;
                    }
                }
                if (ok) return g;
            }
        }

        /**
         * Ceil of power 2.
         *
         * @param n Value.
         * @return Ceil of power 2.
         */
        private static int ceilPow2(final int n) {
            int x = 0;
            while (1L << x < n) x++;
            return x;
        }

        /**
         * Garner's algorithm.
         *
         * @param c    Mod convolution results.
         * @param mods Mods.
         * @return Result.
         */
        private static long garner(final long[] c, final int[] mods) {
            final int n = c.length + 1;
            final long[] cnst = new long[n];
            final long[] coef = new long[n];
            Arrays.fill(coef, 1);
            for (int i = 0; i < n - 1; i++) {
                final int m1 = mods[i];
                long v = (c[i] - cnst[i] + m1) % m1;
                v = v * MathLib.pow(coef[i], m1 - 2, m1) % m1;

                for (int j = i + 1; j < n; j++) {
                    final long m2 = mods[j];
                    cnst[j] = (cnst[j] + coef[j] * v) % m2;
                    coef[j] = coef[j] * m1 % m2;
                }
            }
            return cnst[n - 1];
        }

        /**
         * Garner's algorithm.
         *
         * @param mods Mods.
         * @return Result.
         */
        private static int garner(int c0, int c1, int c2, final MathLib.Barrett[] mods) {
            final long[] cnst = new long[4];
            final long[] coef = new long[4];
            Arrays.fill(coef, 1);
            MathLib.Barrett m1 = mods[0];
            long v = m1.reduce(c0 - cnst[0] + m1.mod);
            v = m1.reduce(v * MathLib.pow(coef[0], m1.mod - 2, m1));
            {
                MathLib.Barrett m2 = mods[1];
                cnst[1] = m2.reduce(cnst[1] + coef[1] * v);
                coef[1] = m2.reduce(coef[1] * m1.mod);
                m2 = mods[2];
                cnst[2] = m2.reduce(cnst[2] + coef[2] * v);
                coef[2] = m2.reduce(coef[2] * m1.mod);
                m2 = mods[3];
                cnst[3] = m2.reduce(cnst[3] + coef[3] * v);
                coef[3] = m2.reduce(coef[3] * m1.mod);
            }
            m1 = mods[1];
            v = m1.reduce(c1 - cnst[1] + m1.mod);
            v = m1.reduce(v * MathLib.pow(coef[1], m1.mod - 2, m1));
            {
                MathLib.Barrett m2 = mods[2];
                cnst[2] = m2.reduce(cnst[2] + coef[2] * v);
                coef[2] = m2.reduce(coef[2] * m1.mod);
                m2 = mods[3];
                cnst[3] = m2.reduce(cnst[3] + coef[3] * v);
                coef[3] = m2.reduce(coef[3] * m1.mod);
            }
            m1 = mods[2];
            v = m1.reduce(c2 - cnst[2] + m1.mod);
            v = m1.reduce(v * MathLib.pow(coef[2], m1.mod - 2, m1));
            {
                MathLib.Barrett m2 = mods[3];
                cnst[3] = m2.reduce(cnst[3] + coef[3] * v);
                coef[3] = m2.reduce(coef[3] * m1.mod);
            }
            return (int) cnst[3];
        }

        /**
         * Garner's algorithm.
         *
         * @return Result.
         */
        private static int garner1_000_000_007(int c0, int c1, int c2) {
            final long[] cnst = new long[4];
            final long[] coef = new long[4];
            Arrays.fill(coef, 1);
            long v = (c0 - cnst[0] + 998_244_353) % 998_244_353;
            v = v * MathLib.pow998_244_353(coef[0], 998_244_353 - 2) % 998_244_353;
            {
                cnst[1] = (cnst[1] + coef[1] * v) % 167_772_161;
                coef[1] = coef[1] * 998_244_353 % 167_772_161;
                cnst[2] = (cnst[2] + coef[2] * v) % 469_762_049;
                coef[2] = coef[2] * 998_244_353 % 469_762_049;
                cnst[3] = (cnst[3] + coef[3] * v) % 1_000_000_007;
                coef[3] = coef[3] * 998_244_353 % 1_000_000_007;
            }
            v = (c1 - cnst[1] + 167_772_161) % 167_772_161;
            v = v * MathLib.pow167_772_161(coef[1], 167_772_161 - 2) % 167_772_161;
            {
                cnst[2] = (cnst[2] + coef[2] * v) % 469_762_049;
                coef[2] = coef[2] * 167_772_161 % 469_762_049;
                cnst[3] = (cnst[3] + coef[3] * v) % 1_000_000_007;
                coef[3] = coef[3] * 167_772_161 % 1_000_000_007;
            }
            v = (c2 - cnst[2] + 469_762_049) % 469_762_049;
            v = v * MathLib.pow469_762_049(coef[2], 469_762_049 - 2) % 469_762_049;
            {
                cnst[3] = (cnst[3] + coef[3] * v) % 1_000_000_007;
                coef[3] = coef[3] * 469_762_049 % 1_000_000_007;
            }
            return (int) cnst[3];
        }

        /**
         * Pre-calculation for NTT.
         *
         * @param mod NTT Prime.
         * @param g   Primitive root of mod.
         * @return Pre-calculation table.
         */
        private static long[] sumE(final int mod, final int g) {
            final long[] sum_e = new long[30];
            final long[] es = new long[30];
            final long[] ies = new long[30];
            final int cnt2 = Integer.numberOfTrailingZeros(mod - 1);
            long e = MathLib.pow(g, mod - 1 >> cnt2, mod);
            long ie = MathLib.pow(e, mod - 2, mod);
            for (int i = cnt2; i >= 2; i--) {
                es[i - 2] = e;
                ies[i - 2] = ie;
                e = e * e % mod;
                ie = ie * ie % mod;
            }
            long now = 1;
            for (int i = 0; i < cnt2 - 2; i++) {
                sum_e[i] = es[i] * now % mod;
                now = now * ies[i] % mod;
            }
            return sum_e;
        }

        /**
         * Pre-calculation for inverse NTT.
         *
         * @param mod Mod.
         * @param g   Primitive root of mod.
         * @return Pre-calculation table.
         */
        private static long[] sumIE(final int mod, final int g) {
            final long[] sum_ie = new long[30];
            final long[] es = new long[30];
            final long[] ies = new long[30];

            final int cnt2 = Integer.numberOfTrailingZeros(mod - 1);
            long e = MathLib.pow(g, mod - 1 >> cnt2, mod);
            long ie = MathLib.pow(e, mod - 2, mod);
            for (int i = cnt2; i >= 2; i--) {
                es[i - 2] = e;
                ies[i - 2] = ie;
                e = e * e % mod;
                ie = ie * ie % mod;
            }
            long now = 1;
            for (int i = 0; i < cnt2 - 2; i++) {
                sum_ie[i] = ies[i] * now % mod;
                now = now * es[i] % mod;
            }
            return sum_ie;
        }

        /**
         * Inverse NTT.
         *
         * @param a     Target array.
         * @param sumIE Pre-calculation table.
         * @param mod   NTT Prime.
         */
        private static void butterflyInv(final long[] a, final long[] sumIE, final int mod) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = h; ph >= 1; ph--) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long inow = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p];
                        a[i + offset] = (l + r) % mod;
                        a[i + offset + p] = (mod + l - r) * inow % mod;
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    inow = inow * sumIE[x] % mod;
                }
            }
        }

        /**
         * Inverse NTT.
         *
         * @param a    Target array.
         * @param sumE Pre-calculation table.
         * @param mod  NTT Prime.
         */
        private static void butterfly(final long[] a, final long[] sumE, final int mod) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = 1; ph <= h; ph++) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long now = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p] * now % mod;
                        a[i + offset] = (l + r) % mod;
                        a[i + offset + p] = (l - r + mod) % mod;
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    now = now * sumE[x] % mod;
                }
            }
        }

        /**
         * Inverse NTT used mod 998_244_353.
         *
         * @param a     Target array.
         * @param sumIE Pre-calculation table.
         */
        private static void butterflyInv998_244_353(final int[] a, final int[] sumIE) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = h; ph >= 1; ph--) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long inow = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p];
                        a[i + offset] = (int) ((l + r) % 998_244_353);
                        a[i + offset + p] = (int) ((998_244_353 + l - r) * inow % 998_244_353);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    inow = inow * sumIE[x] % 998_244_353;
                }
            }
        }

        /**
         * Inverse NTT used mod 167_772_161.
         *
         * @param a     Target array.
         * @param sumIE Pre-calculation table.
         */
        private static void butterflyInv167_772_161(final int[] a, final int[] sumIE) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = h; ph >= 1; ph--) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long inow = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p];
                        a[i + offset] = (int) ((l + r) % 167_772_161);
                        a[i + offset + p] = (int) ((167_772_161 + l - r) * inow % 167_772_161);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    inow = inow * sumIE[x] % 167_772_161;
                }
            }
        }

        /**
         * Inverse NTT used mod 469_762_049.
         *
         * @param a     Target array.
         * @param sumIE Pre-calculation table.
         */
        private static void butterflyInv469_762_049(final int[] a, final int[] sumIE) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = h; ph >= 1; ph--) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long inow = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p];
                        a[i + offset] = (int) ((l + r) % 469_762_049);
                        a[i + offset + p] = (int) ((469_762_049 + l - r) * inow % 469_762_049);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    inow = inow * sumIE[x] % 469_762_049;
                }
            }
        }

        /**
         * Inverse NTT.
         *
         * @param a     Target array.
         * @param sumIE Pre-calculation table.
         * @param mod   NTT Prime.
         */
        private static void butterflyInv(final int[] a, final int[] sumIE, final MathLib.Barrett mod) {
            final int n = a.length;
            final int h = ceilPow2(n);

            for (int ph = h; ph >= 1; ph--) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long inow = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p];
                        long sum = l + r;
                        if (sum >= mod.mod) sum -= mod.mod;
                        a[i + offset] = (int) sum;
                        a[i + offset + p] = mod.reduce((mod.mod + l - r) * inow);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    inow = mod.reduce(inow * sumIE[x]);
                }
            }
        }

        /**
         * Inverse NTT used mod 998_244_353.
         *
         * @param a    Target array.
         * @param sumE Pre-calculation table.
         * @param mod  NTT Prime.
         */
        private static void butterfly998_244_353(final int[] a, final int[] sumE) {
            final int n = a.length;
            final int h = ceilPow2(n);
            final long ADD = (long) (998_244_353 - 2) * 998_244_353;

            for (int ph = 1; ph <= h; ph++) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long now = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p] * now;
                        a[i + offset] = (int) ((l + r) % 998_244_353);
                        a[i + offset + p] = (int) ((l - r + ADD) % 998_244_353);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    now = now * sumE[x] % 998_244_353;
                }
            }
        }

        /**
         * Inverse NTT used mod 167_772_161.
         *
         * @param a    Target array.
         * @param sumE Pre-calculation table.
         * @param mod  NTT Prime.
         */
        private static void butterfly167_772_161(final int[] a, final int[] sumE) {
            final int n = a.length;
            final int h = ceilPow2(n);
            final long ADD = (long) (167_772_161 - 2) * 167_772_161;

            for (int ph = 1; ph <= h; ph++) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long now = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p] * now;
                        a[i + offset] = (int) ((l + r) % 167_772_161);
                        a[i + offset + p] = (int) ((l - r + ADD) % 167_772_161);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    now = now * sumE[x] % 167_772_161;
                }
            }
        }

        /**
         * Inverse NTT used mod 469_762_049.
         *
         * @param a    Target array.
         * @param sumE Pre-calculation table.
         * @param mod  NTT Prime.
         */
        private static void butterfly469_762_049(final int[] a, final int[] sumE) {
            final int n = a.length;
            final int h = ceilPow2(n);
            final long ADD = (long) (469_762_049 - 2) * 469_762_049;

            for (int ph = 1; ph <= h; ph++) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long now = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p] * now;
                        a[i + offset] = (int) ((l + r) % 469_762_049);
                        a[i + offset + p] = (int) ((l - r + ADD) % 469_762_049);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    now = now * sumE[x] % 469_762_049;
                }
            }
        }

        /**
         * Inverse NTT.
         *
         * @param a    Target array.
         * @param sumE Pre-calculation table.
         * @param mod  NTT Prime.
         */
        private static void butterfly(final int[] a, final int[] sumE, final MathLib.Barrett mod) {
            final int n = a.length;
            final int h = ceilPow2(n);
            final long ADD = (long) (mod.mod - 2) * mod.mod;

            for (int ph = 1; ph <= h; ph++) {
                final int w = 1 << ph - 1, p = 1 << h - ph;
                long now = 1;
                for (int s = 0; s < w; s++) {
                    final int offset = s << h - ph + 1;
                    for (int i = 0; i < p; i++) {
                        final long l = a[i + offset];
                        final long r = a[i + offset + p] * now;
                        a[i + offset] = mod.reduce(l + r);
                        a[i + offset + p] = mod.reduce(l - r + ADD);
                    }
                    final int x = Integer.numberOfTrailingZeros(~s);
                    now = mod.reduce(now * sumE[x]);
                }
            }
        }

        /**
         * Convolution used mod 998_244_353.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @return Answer.
         */
        private static int[] convolution998_244_353(int[] a, int[] b) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new int[0];

            final int z = 1 << ceilPow2(n + m - 1);
            {
                final int[] na = new int[z];
                final int[] nb = new int[z];
                System.arraycopy(a, 0, na, 0, n);
                System.arraycopy(b, 0, nb, 0, m);
                a = na;
                b = nb;
            }

            final int g = primitiveRoot(998_244_353);
            final int[] sume;
            {
                long[] s = sumE(998_244_353, g);
                sume = new int[s.length];
                for (int i = 0; i < s.length; ++i) sume[i] = (int) s[i];
            }
            final int[] sumie;
            {
                long[] s = sumIE(998_244_353, g);
                sumie = new int[s.length];
                for (int i = 0; i < s.length; ++i) sumie[i] = (int) s[i];
            }

            butterfly998_244_353(a, sume);
            butterfly998_244_353(b, sume);
            for (int i = 0; i < z; i++) a[i] = (int) ((long) a[i] * b[i] % 998_244_353);
            butterflyInv998_244_353(a, sumie);
            a = Arrays.copyOf(a, n + m - 1);

            final long iz = MathLib.pow998_244_353(z, 998_244_353 - 2);
            for (int i = 0; i < n + m - 1; i++) a[i] = (int) (a[i] * iz % 998_244_353);
            return a;
        }

        /**
         * Convolution used mod 167_772_161.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @return Answer.
         */
        private static int[] convolution167_772_161(int[] a, int[] b) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new int[0];

            final int z = 1 << ceilPow2(n + m - 1);
            {
                final int[] na = new int[z];
                final int[] nb = new int[z];
                System.arraycopy(a, 0, na, 0, n);
                System.arraycopy(b, 0, nb, 0, m);
                a = na;
                b = nb;
            }

            final int g = primitiveRoot(167_772_161);
            final int[] sume;
            {
                long[] s = sumE(167_772_161, g);
                sume = new int[s.length];
                for (int i = 0; i < s.length; ++i) sume[i] = (int) s[i];
            }
            final int[] sumie;
            {
                long[] s = sumIE(167_772_161, g);
                sumie = new int[s.length];
                for (int i = 0; i < s.length; ++i) sumie[i] = (int) s[i];
            }

            butterfly167_772_161(a, sume);
            butterfly167_772_161(b, sume);
            for (int i = 0; i < z; i++) a[i] = (int) ((long) a[i] * b[i] % 167_772_161);
            butterflyInv167_772_161(a, sumie);
            a = Arrays.copyOf(a, n + m - 1);

            final long iz = MathLib.pow167_772_161(z, 167_772_161 - 2);
            for (int i = 0; i < n + m - 1; i++) a[i] = (int) (a[i] * iz % 167_772_161);
            return a;
        }

        /**
         * Convolution used mod 469_762_049.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @return Answer.
         */
        private static int[] convolution469_762_049(int[] a, int[] b) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new int[0];

            final int z = 1 << ceilPow2(n + m - 1);
            {
                final int[] na = new int[z];
                final int[] nb = new int[z];
                System.arraycopy(a, 0, na, 0, n);
                System.arraycopy(b, 0, nb, 0, m);
                a = na;
                b = nb;
            }

            final int g = primitiveRoot(469_762_049);
            final int[] sume;
            {
                long[] s = sumE(469_762_049, g);
                sume = new int[s.length];
                for (int i = 0; i < s.length; ++i) sume[i] = (int) s[i];
            }
            final int[] sumie;
            {
                long[] s = sumIE(469_762_049, g);
                sumie = new int[s.length];
                for (int i = 0; i < s.length; ++i) sumie[i] = (int) s[i];
            }

            butterfly469_762_049(a, sume);
            butterfly469_762_049(b, sume);
            for (int i = 0; i < z; i++) a[i] = (int) ((long) a[i] * b[i] % 469_762_049);
            butterflyInv469_762_049(a, sumie);
            a = Arrays.copyOf(a, n + m - 1);

            final long iz = MathLib.pow469_762_049(z, 469_762_049 - 2);
            for (int i = 0; i < n + m - 1; i++) a[i] = (int) (a[i] * iz % 469_762_049);
            return a;
        }

        /**
         * Convolution.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod NTT Prime.
         * @return Answer.
         */
        private static int[] convolutionNTT(int[] a, int[] b, final int mod) {
            MathLib.Barrett barrett = new MathLib.Barrett(mod);
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new int[0];

            final int z = 1 << ceilPow2(n + m - 1);
            {
                final int[] na = new int[z];
                final int[] nb = new int[z];
                System.arraycopy(a, 0, na, 0, n);
                System.arraycopy(b, 0, nb, 0, m);
                a = na;
                b = nb;
            }

            final int g = primitiveRoot(mod);
            final int[] sume;
            {
                long[] s = sumE(mod, g);
                sume = new int[s.length];
                for (int i = 0; i < s.length; ++i) sume[i] = (int) s[i];
            }
            final int[] sumie;
            {
                long[] s = sumIE(mod, g);
                sumie = new int[s.length];
                for (int i = 0; i < s.length; ++i) sumie[i] = (int) s[i];
            }

            butterfly(a, sume, barrett);
            butterfly(b, sume, barrett);
            for (int i = 0; i < z; i++) a[i] = barrett.reduce((long) a[i] * b[i]);
            butterflyInv(a, sumie, barrett);
            a = Arrays.copyOf(a, n + m - 1);

            final long iz = MathLib.pow(z, mod - 2, mod);
            for (int i = 0; i < n + m - 1; i++) a[i] = barrett.reduce(a[i] * iz);
            return a;
        }

        /**
         * Convolution.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod NTT Prime.
         * @return Answer.
         */
        private static long[] convolutionNTT(long[] a, long[] b, final int mod) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new long[0];

            final int z = 1 << ceilPow2(n + m - 1);
            {
                final long[] na = new long[z];
                final long[] nb = new long[z];
                System.arraycopy(a, 0, na, 0, n);
                System.arraycopy(b, 0, nb, 0, m);
                a = na;
                b = nb;
            }

            final int g = primitiveRoot(mod);
            final long[] sume = sumE(mod, g);
            final long[] sumie = sumIE(mod, g);

            butterfly(a, sume, mod);
            butterfly(b, sume, mod);
            for (int i = 0; i < z; i++) {
                a[i] = a[i] * b[i] % mod;
            }
            butterflyInv(a, sumie, mod);
            a = Arrays.copyOf(a, n + m - 1);

            final long iz = MathLib.pow(z, mod - 2, mod);
            for (int i = 0; i < n + m - 1; i++) a[i] = a[i] * iz % mod;
            return a;
        }

        /**
         * Convolution.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod Any mod.
         * @return Answer.
         */
        public static long[] convolution(final long[] a, final long[] b, final int mod) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new long[0];

            final int mod1 = 998_244_353;
            final int mod2 = 167_772_161;
            final int mod3 = 469_762_049;

            final long[] c1 = convolutionNTT(a, b, mod1);
            final long[] c2 = convolutionNTT(a, b, mod2);
            final long[] c3 = convolutionNTT(a, b, mod3);

            final int retSize = c1.length;
            final long[] ret = new long[retSize];
            final int[] mods = { mod1, mod2, mod3, mod };
            for (int i = 0; i < retSize; ++i) {
                ret[i] = garner(new long[] { c1[i], c2[i], c3[i] }, mods);
            }
            return ret;
        }

        /**
         * Convolution.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod Any mod.
         * @return Answer.
         */
        public static int[] convolution(final int[] a, final int[] b, final int mod) {
            final int n = a.length;
            final int m = b.length;
            if (n == 0 || m == 0) return new int[0];
            if (mod == 1_000_000_007) return convolution1_000_000_007(a, b);
            if (mod == 998_244_353) return convolution998_244_353(a, b);
            int ntt = Integer.lowestOneBit(mod - 1) >> 1;
            if (n + m <= ntt) return convolutionNTT(a, b, mod);

            final int[] c1 = convolution998_244_353(a, b);
            final int[] c2 = convolution167_772_161(a, b);
            final int[] c3 = convolution469_762_049(a, b);

            final int retSize = c1.length;
            final int[] ret = new int[retSize];
            final MathLib.Barrett[] mods = { new MathLib.Barrett(998_244_353), new MathLib.Barrett(167_772_161),
                    new MathLib.Barrett(469_762_049), new MathLib.Barrett(mod) };
            for (int i = 0; i < retSize; ++i) ret[i] = garner(c1[i], c2[i], c3[i], mods);
            return ret;
        }

        /**
         * Convolution used mod 1_000_000_007.
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @return Answer.
         */
        private static int[] convolution1_000_000_007(final int[] a, final int[] b) {

            final int[] c1 = convolution998_244_353(a, b);
            final int[] c2 = convolution167_772_161(a, b);
            final int[] c3 = convolution469_762_049(a, b);

            final int retSize = c1.length;
            final int[] ret = new int[retSize];
            for (int i = 0; i < retSize; ++i) ret[i] = garner1_000_000_007(c1[i], c2[i], c3[i]);
            return ret;
        }

        /**
         * Convolution. need: length < 2000
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod Any mod.
         * @return Answer.
         */
        public static int[] convolution2(final int[] a, final int[] b, final int mod) {
            if (Math.max(a.length, b.length) < 4000) {
                long[] la = new long[a.length], ha = new long[a.length], ma = new long[a.length],
                        lb = new long[b.length], hb = new long[b.length], mb = new long[b.length];
                MathLib.Barrett barrett = new MathLib.Barrett(mod);
                for (int i = 0; i < a.length; ++i) {
                    ha[i] = a[i] >> 15;
                    la[i] = a[i] & 0x7FFF;
                    ma[i] = la[i] + ha[i];
                }
                for (int i = 0; i < b.length; ++i) {
                    hb[i] = b[i] >> 15;
                    lb[i] = b[i] & 0x7FFF;
                    mb[i] = lb[i] + hb[i];
                }
                long[] l = convolution(la, lb), h = convolution(ha, hb), m = convolution(ma, mb);
                int[] ret = new int[m.length];
                for (int i = 0; i < m.length; ++i) {
                    h[i] = barrett.reduce(h[i]);
                    m[i] = barrett.reduce(m[i] - l[i] - h[i] + (long) m.length * mod);
                    ret[i] = barrett.reduce((h[i] << 30) + (m[i] << 15) + l[i]);
                }
                return ret;
            }
            return convolution(a, b, mod);
        }

        /**
         * Naive convolution. (Complexity is O(N^2)!!)
         *
         * @param a   Target array 1.
         * @param b   Target array 2.
         * @param mod Mod.
         * @return Answer.
         */
        public static long[] convolutionNaive(final long[] a, final long[] b, final int mod) {
            final int n = a.length;
            final int m = b.length;
            final int k = n + m - 1;
            final long[] ret = new long[k];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i + j] += a[i] * b[j] % mod;
                    ret[i + j] %= mod;
                }
            }
            return ret;
        }
    }

    /**
     * @verified https://atcoder.jp/contests/practice2/tasks/practice2_g
     */
    public static final class SCC {

        static class Edge {

            int from, to;

            public Edge(final int from, final int to) {
                this.from = from;
                this.to = to;
            }
        }

        final int n;
        int m;
        final java.util.ArrayList<Edge> unorderedEdges;
        final int[] start;
        final int[] ids;
        boolean hasBuilt = false;

        public SCC(final int n) {
            this.n = n;
            unorderedEdges = new java.util.ArrayList<>();
            start = new int[n + 1];
            ids = new int[n];
        }

        public void addEdge(final int from, final int to) {
            rangeCheck(from);
            rangeCheck(to);
            unorderedEdges.add(new Edge(from, to));
            start[from + 1]++;
            m++;
        }

        public int id(final int i) {
            if (!hasBuilt) { throw new UnsupportedOperationException("Graph hasn't been built."); }
            rangeCheck(i);
            return ids[i];
        }

        public int[][] build() {
            for (int i = 1; i <= n; i++) {
                start[i] += start[i - 1];
            }
            final Edge[] orderedEdges = new Edge[m];
            final int[] count = new int[n + 1];
            System.arraycopy(start, 0, count, 0, n + 1);
            for (final Edge e : unorderedEdges) {
                orderedEdges[count[e.from]++] = e;
            }
            int nowOrd = 0;
            int groupNum = 0;
            int k = 0;
            // parent
            final int[] par = new int[n];
            final int[] vis = new int[n];
            final int[] low = new int[n];
            final int[] ord = new int[n];
            Arrays.fill(ord, -1);
            // u = lower32(stack[i]) : visiting vertex
            // j = upper32(stack[i]) : jth child
            final long[] stack = new long[n];
            // size of stack
            int ptr = 0;
            // non-recursional DFS
            for (int i = 0; i < n; i++) {
                if (ord[i] >= 0) continue;
                par[i] = -1;
                // vertex i, 0th child.
                stack[ptr++] = 0l << 32 | i;
                // stack is not empty
                while (ptr > 0) {
                    // last element
                    final long p = stack[--ptr];
                    // vertex
                    final int u = (int) (p & 0xffff_ffffl);
                    // jth child
                    int j = (int) (p >>> 32);
                    if (j == 0) { // first visit
                        low[u] = ord[u] = nowOrd++;
                        vis[k++] = u;
                    }
                    if (start[u] + j < count[u]) { // there are more children
                        // jth child
                        final int to = orderedEdges[start[u] + j].to;
                        // incr children counter
                        stack[ptr++] += 1l << 32;
                        if (ord[to] == -1) { // new vertex
                            stack[ptr++] = 0l << 32 | to;
                            par[to] = u;
                        } else { // backward edge
                            low[u] = Math.min(low[u], ord[to]);
                        }
                    } else { // no more children (leaving)
                        while (j-- > 0) {
                            final int to = orderedEdges[start[u] + j].to;
                            // update lowlink
                            if (par[to] == u) low[u] = Math.min(low[u], low[to]);
                        }
                        if (low[u] == ord[u]) { // root of a component
                            while (true) { // gathering verticies
                                final int v = vis[--k];
                                ord[v] = n;
                                ids[v] = groupNum;
                                if (v == u) break;
                            }
                            groupNum++; // incr the number of components
                        }
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                ids[i] = groupNum - 1 - ids[i];
            }

            final int[] counts = new int[groupNum];
            for (final int x : ids) counts[x]++;
            final int[][] groups = new int[groupNum][];
            for (int i = 0; i < groupNum; i++) {
                groups[i] = new int[counts[i]];
            }
            for (int i = 0; i < n; i++) {
                final int cmp = ids[i];
                groups[cmp][--counts[cmp]] = i;
            }
            hasBuilt = true;
            return groups;
        }

        private void rangeCheck(final int i) {
            if (i < 0 || i >= n) {
                throw new IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", i, n));
            }
        }
    }

    /**
     * @verified https://atcoder.jp/contests/practice2/submissions/16647102
     */
    public static final class TwoSAT {
        private final int n;
        private final InternalSCC scc;
        private final boolean[] answer;

        private boolean hasCalledSatisfiable = false;
        private boolean existsAnswer = false;

        public TwoSAT(int n) {
            this.n = n;
            scc = new InternalSCC(2 * n);
            answer = new boolean[n];
        }

        public void addClause(int x, boolean f, int y, boolean g) {
            rangeCheck(x);
            rangeCheck(y);
            scc.addEdge(x << 1 | (f ? 0 : 1), y << 1 | (g ? 1 : 0));
            scc.addEdge(y << 1 | (g ? 0 : 1), x << 1 | (f ? 1 : 0));
        }

        public void addImplication(int x, boolean f, int y, boolean g) {
            addClause(x, !f, y, g);
        }

        public void addNand(int x, boolean f, int y, boolean g) {
            addClause(x, !f, y, !g);
        }

        public void set(int x, boolean f) {
            addClause(x, f, x, f);
        }

        public boolean satisfiable() {
            hasCalledSatisfiable = true;
            int[] ids = scc.ids();
            for (int i = 0; i < n; i++) {
                if (ids[i << 1 | 0] == ids[i << 1 | 1]) return existsAnswer = false;
                answer[i] = ids[i << 1 | 0] < ids[i << 1 | 1];
            }
            return existsAnswer = true;
        }

        public boolean[] answer() {
            if (!hasCalledSatisfiable) {
                throw new UnsupportedOperationException("Call TwoSAT#satisfiable at least once before TwoSAT#answer.");
            }
            if (existsAnswer) return answer;
            return null;
        }

        private void rangeCheck(int x) {
            if (x < 0 || x >= n) {
                throw new IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", x, n));
            }
        }

        private static final class EdgeList {
            long[] a;
            int ptr = 0;

            EdgeList(int cap) {
                a = new long[cap];
            }

            void add(int upper, int lower) {
                if (ptr == a.length) grow();
                a[ptr++] = (long) upper << 32 | lower;
            }

            void grow() {
                long[] b = new long[a.length << 1];
                System.arraycopy(a, 0, b, 0, a.length);
                a = b;
            }
        }

        private static final class InternalSCC {
            final int n;
            int m;
            final EdgeList unorderedEdges;
            final int[] start;

            InternalSCC(int n) {
                this.n = n;
                unorderedEdges = new EdgeList(n);
                start = new int[n + 1];
            }

            void addEdge(int from, int to) {
                unorderedEdges.add(from, to);
                start[from + 1]++;
                m++;
            }

            static final long mask = 0xffff_ffffl;

            int[] ids() {
                for (int i = 1; i <= n; i++) {
                    start[i] += start[i - 1];
                }
                int[] orderedEdges = new int[m];
                int[] count = new int[n + 1];
                System.arraycopy(start, 0, count, 0, n + 1);
                for (int i = 0; i < m; i++) {
                    long e = unorderedEdges.a[i];
                    orderedEdges[count[(int) (e >>> 32)]++] = (int) (e & mask);
                }
                int nowOrd = 0;
                int groupNum = 0;
                int k = 0;
                int[] par = new int[n];
                int[] vis = new int[n];
                int[] low = new int[n];
                int[] ord = new int[n];
                Arrays.fill(ord, -1);
                int[] ids = new int[n];
                long[] stack = new long[n];
                int ptr = 0;

                for (int i = 0; i < n; i++) {
                    if (ord[i] >= 0) continue;
                    par[i] = -1;
                    stack[ptr++] = i;
                    while (ptr > 0) {
                        long p = stack[--ptr];
                        int u = (int) (p & mask);
                        int j = (int) (p >>> 32);
                        if (j == 0) {
                            low[u] = ord[u] = nowOrd++;
                            vis[k++] = u;
                        }
                        if (start[u] + j < count[u]) {
                            int to = orderedEdges[start[u] + j];
                            stack[ptr++] += 1l << 32;
                            if (ord[to] == -1) {
                                stack[ptr++] = to;
                                par[to] = u;
                            } else {
                                low[u] = Math.min(low[u], ord[to]);
                            }
                        } else {
                            while (j-- > 0) {
                                int to = orderedEdges[start[u] + j];
                                if (par[to] == u) low[u] = Math.min(low[u], low[to]);
                            }
                            if (low[u] == ord[u]) {
                                while (true) {
                                    int v = vis[--k];
                                    ord[v] = n;
                                    ids[v] = groupNum;
                                    if (v == u) break;
                                }
                                groupNum++;
                            }
                        }
                    }
                }
                for (int i = 0; i < n; i++) {
                    ids[i] = groupNum - 1 - ids[i];
                }
                return ids;
            }
        }
    }

    public static final class StringAlgorithm {

        private static int[] saNaive(final int[] s) {
            final int n = s.length;
            final Integer[] _sa = new Integer[n];
            for (int i = 0; i < n; i++) {
                _sa[i] = i;
            }
            Arrays.sort(_sa, (l, r) -> {
                while (l < n && r < n) {
                    if (s[l] != s[r]) return s[l] - s[r];
                    l++;
                    r++;
                }
                return -(l - r);
            });
            final int[] sa = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = _sa[i];
            }
            return sa;
        }

        private static int[] saDoubling(final int[] s) {
            final int n = s.length;
            final Integer[] _sa = new Integer[n];
            for (int i = 0; i < n; i++) {
                _sa[i] = i;
            }
            int[] rnk = s;
            int[] tmp = new int[n];

            for (int k = 1; k < n; k *= 2) {
                final int _k = k;
                final int[] _rnk = rnk;
                final java.util.Comparator<Integer> cmp = (x, y) -> {
                    if (_rnk[x] != _rnk[y]) return _rnk[x] - _rnk[y];
                    final int rx = x + _k < n ? _rnk[x + _k] : -1;
                    final int ry = y + _k < n ? _rnk[y + _k] : -1;
                    return rx - ry;
                };
                Arrays.sort(_sa, cmp);
                tmp[_sa[0]] = 0;
                for (int i = 1; i < n; i++) {
                    tmp[_sa[i]] = tmp[_sa[i - 1]] + (cmp.compare(_sa[i - 1], _sa[i]) < 0 ? 1 : 0);
                }
                final int[] buf = tmp;
                tmp = rnk;
                rnk = buf;
            }

            final int[] sa = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = _sa[i];
            }
            return sa;
        }

        private static final int THRESHOLD_NAIVE = 10;
        private static final int THRESHOLD_DOUBLING = 40;

        private static int[] sais(final int[] s, final int upper) {
            final int n = s.length;
            if (n == 0) return new int[0];
            if (n == 1) return new int[] { 0 };
            if (n == 2) { return s[0] < s[1] ? new int[] { 0, 1 } : new int[] { 1, 0 }; }
            if (n < THRESHOLD_NAIVE) { return saNaive(s); }
            if (n < THRESHOLD_DOUBLING) { return saDoubling(s); }

            final int[] sa = new int[n];
            final boolean[] ls = new boolean[n];
            for (int i = n - 2; i >= 0; i--) {
                ls[i] = s[i] == s[i + 1] ? ls[i + 1] : s[i] < s[i + 1];
            }

            final int[] sumL = new int[upper + 1];
            final int[] sumS = new int[upper + 1];

            for (int i = 0; i < n; i++) {
                if (ls[i]) {
                    sumL[s[i] + 1]++;
                } else {
                    sumS[s[i]]++;
                }
            }

            for (int i = 0; i <= upper; i++) {
                sumS[i] += sumL[i];
                if (i < upper) sumL[i + 1] += sumS[i];
            }

            final java.util.function.Consumer<int[]> induce = lms -> {
                Arrays.fill(sa, -1);
                final int[] buf = new int[upper + 1];
                System.arraycopy(sumS, 0, buf, 0, upper + 1);
                for (final int d : lms) {
                    if (d == n) continue;
                    sa[buf[s[d]]++] = d;
                }
                System.arraycopy(sumL, 0, buf, 0, upper + 1);
                sa[buf[s[n - 1]]++] = n - 1;
                for (int i = 0; i < n; i++) {
                    final int v = sa[i];
                    if (v >= 1 && !ls[v - 1]) {
                        sa[buf[s[v - 1]]++] = v - 1;
                    }
                }
                System.arraycopy(sumL, 0, buf, 0, upper + 1);
                for (int i = n - 1; i >= 0; i--) {
                    final int v = sa[i];
                    if (v >= 1 && ls[v - 1]) {
                        sa[--buf[s[v - 1] + 1]] = v - 1;
                    }
                }
            };

            final int[] lmsMap = new int[n + 1];
            Arrays.fill(lmsMap, -1);
            int m = 0;
            for (int i = 1; i < n; i++) {
                if (!ls[i - 1] && ls[i]) {
                    lmsMap[i] = m++;
                }
            }

            final int[] lms = new int[m];
            {
                int p = 0;
                for (int i = 1; i < n; i++) {
                    if (!ls[i - 1] && ls[i]) {
                        lms[p++] = i;
                    }
                }
            }

            induce.accept(lms);

            if (m > 0) {
                final int[] sortedLms = new int[m];
                {
                    int p = 0;
                    for (final int v : sa) {
                        if (lmsMap[v] != -1) {
                            sortedLms[p++] = v;
                        }
                    }
                }
                final int[] recS = new int[m];
                int recUpper = 0;
                recS[lmsMap[sortedLms[0]]] = 0;
                for (int i = 1; i < m; i++) {
                    int l = sortedLms[i - 1], r = sortedLms[i];
                    final int endL = lmsMap[l] + 1 < m ? lms[lmsMap[l] + 1] : n;
                    final int endR = lmsMap[r] + 1 < m ? lms[lmsMap[r] + 1] : n;
                    boolean same = true;
                    if (endL - l != endR - r) {
                        same = false;
                    } else {
                        while (l < endL && s[l] == s[r]) {
                            l++;
                            r++;
                        }
                        if (l == n || s[l] != s[r]) same = false;
                    }
                    if (!same) {
                        recUpper++;
                    }
                    recS[lmsMap[sortedLms[i]]] = recUpper;
                }

                final int[] recSA = sais(recS, recUpper);

                for (int i = 0; i < m; i++) {
                    sortedLms[i] = lms[recSA[i]];
                }
                induce.accept(sortedLms);
            }
            return sa;
        }

        public static int[] suffixArray(final int[] s, final int upper) {
            assert 0 <= upper;
            for (final int d : s) {
                assert 0 <= d && d <= upper;
            }
            return sais(s, upper);
        }

        public static int[] suffixArray(final int[] s) {
            final int n = s.length;
            final Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) {
                idx[i] = i;
            }
            Arrays.sort(idx, (l, r) -> s[l] - s[r]);
            final int[] s2 = new int[n];
            int now = 0;
            for (int i = 0; i < n; i++) {
                if (i > 0 && s[idx[i - 1]] != s[idx[i]]) {
                    now++;
                }
                s2[idx[i]] = now;
            }
            return sais(s2, now);
        }

        public static int[] suffixArray(final char[] s) {
            final int n = s.length;
            final int[] s2 = new int[n];
            for (int i = 0; i < n; i++) {
                s2[i] = s[i];
            }
            return sais(s2, 255);
        }

        public static int[] suffixArray(final String s) {
            return suffixArray(s.toCharArray());
        }

        public static int[] lcpArray(final int[] s, final int[] sa) {
            final int n = s.length;
            assert n >= 1;
            final int[] rnk = new int[n];
            for (int i = 0; i < n; i++) {
                rnk[sa[i]] = i;
            }
            final int[] lcp = new int[n - 1];
            int h = 0;
            for (int i = 0; i < n; i++) {
                if (h > 0) h--;
                if (rnk[i] == 0) {
                    continue;
                }
                final int j = sa[rnk[i] - 1];
                for (; j + h < n && i + h < n; h++) {
                    if (s[j + h] != s[i + h]) break;
                }
                lcp[rnk[i] - 1] = h;
            }
            return lcp;
        }

        public static int[] lcpArray(final char[] s, final int[] sa) {
            final int n = s.length;
            final int[] s2 = new int[n];
            for (int i = 0; i < n; i++) {
                s2[i] = s[i];
            }
            return lcpArray(s2, sa);
        }

        public static int[] lcpArray(final String s, final int[] sa) {
            return lcpArray(s.toCharArray(), sa);
        }

        public static int[] zAlgorithm(final int[] s) {
            final int n = s.length;
            if (n == 0) return new int[0];
            final int[] z = new int[n];
            for (int i = 1, j = 0; i < n; i++) {
                int k = j + z[j] <= i ? 0 : Math.min(j + z[j] - i, z[i - j]);
                while (i + k < n && s[k] == s[i + k]) k++;
                z[i] = k;
                if (j + z[j] < i + z[i]) j = i;
            }
            z[0] = n;
            return z;
        }

        public static int[] zAlgorithm(final char[] s) {
            final int n = s.length;
            if (n == 0) return new int[0];
            final int[] z = new int[n];
            for (int i = 1, j = 0; i < n; i++) {
                int k = j + z[j] <= i ? 0 : Math.min(j + z[j] - i, z[i - j]);
                while (i + k < n && s[k] == s[i + k]) k++;
                z[i] = k;
                if (j + z[j] < i + z[i]) j = i;
            }
            z[0] = n;
            return z;
        }

        public static int[] zAlgorithm(final String s) {
            return zAlgorithm(s.toCharArray());
        }
    }

    /**
     * @verified https://atcoder.jp/contests/practice2/tasks/practice2_j
     */
    public static final class SegTree<S> {

        final int MAX;

        final int N;
        final java.util.function.BinaryOperator<S> op;
        final S E;

        final S[] data;

        @SuppressWarnings("unchecked")
        public SegTree(final int n, final java.util.function.BinaryOperator<S> op, final S e) {
            MAX = n;
            int k = 1;
            while (k < n) k <<= 1;
            N = k;
            E = e;
            this.op = op;
            data = (S[]) new Object[N << 1];
            Arrays.fill(data, E);
        }

        public SegTree(final S[] dat, final java.util.function.BinaryOperator<S> op, final S e) {
            this(dat.length, op, e);
            build(dat);
        }

        private void build(final S[] dat) {
            final int l = dat.length;
            System.arraycopy(dat, 0, data, N, l);
            for (int i = N - 1; i > 0; i--) {
                data[i] = op.apply(data[i << 1 | 0], data[i << 1 | 1]);
            }
        }

        public void set(int p, final S x) {
            exclusiveRangeCheck(p);
            data[p += N] = x;
            p >>= 1;
            while (p > 0) {
                data[p] = op.apply(data[p << 1 | 0], data[p << 1 | 1]);
                p >>= 1;
            }
        }

        public void set(int p, java.util.function.UnaryOperator<S> f) {
            exclusiveRangeCheck(p);
            data[p += N] = f.apply(data[p]);
            p >>= 1;
            while (p > 0) {
                data[p] = op.apply(data[p << 1 | 0], data[p << 1 | 1]);
                p >>= 1;
            }
        }

        public S get(final int p) {
            exclusiveRangeCheck(p);
            return data[p + N];
        }

        public S prod(int l, int r) {
            if (l > r) { throw new IllegalArgumentException(String.format("Invalid range: [%d, %d)", l, r)); }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            S sumLeft = E;
            S sumRight = E;
            l += N;
            r += N;
            while (l < r) {
                if ((l & 1) == 1) sumLeft = op.apply(sumLeft, data[l++]);
                if ((r & 1) == 1) sumRight = op.apply(data[--r], sumRight);
                l >>= 1;
                r >>= 1;
            }
            return op.apply(sumLeft, sumRight);
        }

        public S allProd() {
            return data[1];
        }

        public int maxRight(int l, final java.util.function.Predicate<S> f) {
            inclusiveRangeCheck(l);
            if (!f.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
            if (l == MAX) return MAX;
            l += N;
            S sum = E;
            do {
                l >>= Integer.numberOfTrailingZeros(l);
                if (!f.test(op.apply(sum, data[l]))) {
                    while (l < N) {
                        l = l << 1;
                        if (f.test(op.apply(sum, data[l]))) {
                            sum = op.apply(sum, data[l]);
                            l++;
                        }
                    }
                    return l - N;
                }
                sum = op.apply(sum, data[l]);
                l++;
            } while ((l & -l) != l);
            return MAX;
        }

        public int minLeft(int r, final java.util.function.Predicate<S> f) {
            inclusiveRangeCheck(r);
            if (!f.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
            if (r == 0) return 0;
            r += N;
            S sum = E;
            do {
                r--;
                while (r > 1 && (r & 1) == 1) r >>= 1;
                if (!f.test(op.apply(data[r], sum))) {
                    while (r < N) {
                        r = r << 1 | 1;
                        if (f.test(op.apply(data[r], sum))) {
                            sum = op.apply(data[r], sum);
                            r--;
                        }
                    }
                    return r + 1 - N;
                }
                sum = op.apply(data[r], sum);
            } while ((r & -r) != r);
            return 0;
        }

        private void exclusiveRangeCheck(final int p) {
            if (p < 0 || p >= MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for the range [%d, %d).", p, 0, MAX));
            }
        }

        private void inclusiveRangeCheck(final int p) {
            if (p < 0 || p > MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for the range [%d, %d].", p, 0, MAX));
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0;i < N;++ i) {
                if (i != 0) sb.append(", ");
                sb.append(data[i + N]);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    /**
     *
     * @verified https://atcoder.jp/contests/practice2/tasks/practice2_k
     */

    public static final class LazySegTree<S, F> {

        final int MAX;

        final int N;
        final int Log;
        final java.util.function.BinaryOperator<S> Op;
        final S E;
        final java.util.function.BiFunction<F, S, S> Mapping;
        final java.util.function.BinaryOperator<F> Composition;
        final F Id;

        final S[] Dat;
        final F[] Laz;

        @SuppressWarnings("unchecked")
        public LazySegTree(final int n, final java.util.function.BinaryOperator<S> op, final S e,
                           final java.util.function.BiFunction<F, S, S> mapping,
                           final java.util.function.BinaryOperator<F> composition, final F id) {
            MAX = n;
            int k = 1;
            while (k < n) k <<= 1;
            N = k;
            Log = Integer.numberOfTrailingZeros(N);
            Op = op;
            E = e;
            Mapping = mapping;
            Composition = composition;
            Id = id;
            Dat = (S[]) new Object[N << 1];
            Laz = (F[]) new Object[N];
            Arrays.fill(Dat, E);
            Arrays.fill(Laz, Id);
        }

        public LazySegTree(final S[] dat, final java.util.function.BinaryOperator<S> op, final S e,
                           final java.util.function.BiFunction<F, S, S> mapping,
                           final java.util.function.BinaryOperator<F> composition, final F id) {
            this(dat.length, op, e, mapping, composition, id);
            build(dat);
        }

        private void build(final S[] dat) {
            final int l = dat.length;
            System.arraycopy(dat, 0, Dat, N, l);
            for (int i = N - 1; i > 0; i--) {
                Dat[i] = Op.apply(Dat[i << 1 | 0], Dat[i << 1 | 1]);
            }
        }

        private void push(final int k) {
            if (Laz[k] == Id) return;
            final int lk = k << 1 | 0, rk = k << 1 | 1;
            Dat[lk] = Mapping.apply(Laz[k], Dat[lk]);
            Dat[rk] = Mapping.apply(Laz[k], Dat[rk]);
            if (lk < N) Laz[lk] = Composition.apply(Laz[k], Laz[lk]);
            if (rk < N) Laz[rk] = Composition.apply(Laz[k], Laz[rk]);
            Laz[k] = Id;
        }

        private void pushTo(final int k) {
            for (int i = Log; i > 0; i--) push(k >> i);
        }

        private void pushTo(final int lk, final int rk) {
            for (int i = Log; i > 0; i--) {
                if (lk >> i << i != lk) push(lk >> i);
                if (rk >> i << i != rk) push(rk >> i);
            }
        }

        private void updateFrom(int k) {
            k >>= 1;
            while (k > 0) {
                Dat[k] = Op.apply(Dat[k << 1 | 0], Dat[k << 1 | 1]);
                k >>= 1;
            }
        }

        private void updateFrom(final int lk, final int rk) {
            for (int i = 1; i <= Log; i++) {
                if (lk >> i << i != lk) {
                    final int lki = lk >> i;
                    Dat[lki] = Op.apply(Dat[lki << 1 | 0], Dat[lki << 1 | 1]);
                }
                if (rk >> i << i != rk) {
                    final int rki = rk - 1 >> i;
                    Dat[rki] = Op.apply(Dat[rki << 1 | 0], Dat[rki << 1 | 1]);
                }
            }
        }

        public void set(int p, final S x) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            Dat[p] = x;
            updateFrom(p);
        }

        public S get(int p) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            return Dat[p];
        }

        public S prod(int l, int r) {
            if (l > r) { throw new IllegalArgumentException(String.format("Invalid range: [%d, %d)", l, r)); }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            if (l == r) return E;
            l += N;
            r += N;
            pushTo(l, r);
            S sumLeft = E, sumRight = E;
            while (l < r) {
                if ((l & 1) == 1) sumLeft = Op.apply(sumLeft, Dat[l++]);
                if ((r & 1) == 1) sumRight = Op.apply(Dat[--r], sumRight);
                l >>= 1;
                r >>= 1;
            }
            return Op.apply(sumLeft, sumRight);
        }

        public S allProd() {
            return Dat[1];
        }

        public void apply(int p, final F f) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            Dat[p] = Mapping.apply(f, Dat[p]);
            updateFrom(p);
        }

        public void apply(int l, int r, final F f) {
            if (l > r) { throw new IllegalArgumentException(String.format("Invalid range: [%d, %d)", l, r)); }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            if (l == r) return;
            l += N;
            r += N;
            pushTo(l, r);
            for (int l2 = l, r2 = r; l2 < r2;) {
                if ((l2 & 1) == 1) {
                    Dat[l2] = Mapping.apply(f, Dat[l2]);
                    if (l2 < N) Laz[l2] = Composition.apply(f, Laz[l2]);
                    l2++;
                }
                if ((r2 & 1) == 1) {
                    r2--;
                    Dat[r2] = Mapping.apply(f, Dat[r2]);
                    if (r2 < N) Laz[r2] = Composition.apply(f, Laz[r2]);
                }
                l2 >>= 1;
                r2 >>= 1;
            }
            updateFrom(l, r);
        }

        public int maxRight(int l, final java.util.function.Predicate<S> g) {
            inclusiveRangeCheck(l);
            if (!g.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
            if (l == MAX) return MAX;
            l += N;
            pushTo(l);
            S sum = E;
            do {
                l >>= Integer.numberOfTrailingZeros(l);
                if (!g.test(Op.apply(sum, Dat[l]))) {
                    while (l < N) {
                        push(l);
                        l = l << 1;
                        if (g.test(Op.apply(sum, Dat[l]))) {
                            sum = Op.apply(sum, Dat[l]);
                            l++;
                        }
                    }
                    return l - N;
                }
                sum = Op.apply(sum, Dat[l]);
                l++;
            } while ((l & -l) != l);
            return MAX;
        }

        public int minLeft(int r, final java.util.function.Predicate<S> g) {
            inclusiveRangeCheck(r);
            if (!g.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
            if (r == 0) return 0;
            r += N;
            pushTo(r - 1);
            S sum = E;
            do {
                r--;
                while (r > 1 && (r & 1) == 1) r >>= 1;
                if (!g.test(Op.apply(Dat[r], sum))) {
                    while (r < N) {
                        push(r);
                        r = r << 1 | 1;
                        if (g.test(Op.apply(Dat[r], sum))) {
                            sum = Op.apply(Dat[r], sum);
                            r--;
                        }
                    }
                    return r + 1 - N;
                }
                sum = Op.apply(Dat[r], sum);
            } while ((r & -r) != r);
            return 0;
        }

        private void exclusiveRangeCheck(final int p) {
            if (p < 0 || p >= MAX) {
                throw new IndexOutOfBoundsException(String.format("Index %d is not in [%d, %d).", p, 0, MAX));
            }
        }

        private void inclusiveRangeCheck(final int p) {
            if (p < 0 || p > MAX) {
                throw new IndexOutOfBoundsException(String.format("Index %d is not in [%d, %d].", p, 0, MAX));
            }
        }

        // **************** DEBUG **************** //

        private int indent = 6;

        public void setIndent(final int newIndent) { indent = newIndent; }

        @Override
        public String toString() {
            return toString(1, 0);
        }

        private String toString(final int k, final int sp) {
            if (k >= N) return indent(sp) + Dat[k];
            String s = "";
            s += toString(k << 1 | 1, sp + indent);
            s += "\n";
            s += indent(sp) + Dat[k] + "/" + Laz[k];
            s += "\n";
            s += toString(k << 1 | 0, sp + indent);
            return s;
        }

        private static String indent(int n) {
            final StringBuilder sb = new StringBuilder();
            while (n-- > 0) sb.append(' ');
            return sb.toString();
        }
    }

    public static final class MultiSet<T> extends java.util.TreeMap<T, Long> {

        private static final long serialVersionUID = 1L;

        public MultiSet() {
            super();
        }

        public MultiSet(final java.util.List<T> list) {
            super();
            for (final T e : list) this.addOne(e);
        }

        public long count(final Object elm) {
            return getOrDefault(elm, 0L);
        }

        public void add(final T elm, final long amount) {
            if (!containsKey(elm)) put(elm, amount);
            else replace(elm, get(elm) + amount);
            if (this.count(elm) == 0) this.remove(elm);
        }

        public void addOne(final T elm) {
            this.add(elm, 1);
        }

        public void removeOne(final T elm) {
            this.add(elm, -1);
        }

        public void removeAll(final T elm) {
            this.add(elm, -this.count(elm));
        }

        public static <T> MultiSet<T> merge(final MultiSet<T> a, final MultiSet<T> b) {
            final MultiSet<T> c = new MultiSet<>();
            for (final T x : a.keySet()) c.add(x, a.count(x));
            for (final T y : b.keySet()) c.add(y, b.count(y));
            return c;
        }
    }
}

