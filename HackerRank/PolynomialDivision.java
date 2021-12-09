import java.io.*;
import java.util.*;

public class Solution {

    static final int MOD = 1_000_000_007;

    static class SegmentTree {
      int st[];
      int n;

      public SegmentTree(int n) {
          this.n = n;
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

        int maxSize = 2 * (int) Math.pow(2, x) - 1;
        st = new int[maxSize];
      }

      int getMid(int s, int e) {
        return s + (e - s) / 2;
      }

      long getSum(int ss, int se, int qs, int qe, int si) {
        if (qs <= ss && qe >= se) {
          return st[si];
        }

        if (se < qs || ss > qe) {
          return 0;
        }

        int mid = getMid(ss, se);
        return sum(getSum(ss, mid, qs, qe, 2 * si + 1),
            getSum(mid + 1, se, qs, qe, 2 * si + 2));
      }

      void updateValue(int ss, int se, int i, int diff, int si) {
        if (i < ss || i > se) {
          return;
        }

        st[si] = (int) sum(st[si], diff);
        if (se != ss) {
          int mid = getMid(ss, se);
          updateValue(ss, mid, i, diff, 2 * si + 1);
          updateValue(mid + 1, se, i, diff, 2 * si + 2);
        }
      }

      void updateValue(int arr[], int i, int newVal) {
        int diff = newVal - arr[i];

        arr[i] = newVal;

        updateValue(0, n - 1, i, diff, 0);
      }

      long getSum(int qs, int qe) {
        return getSum(0, n - 1, qs, qe, 0);
      }

      void construct(int arr[]) {
        construct(arr, 0, arr.length - 1, 0);
      }

      int construct(int arr[], int ss, int se, int si) {
        if (ss == se) {
          st[si] = arr[ss];
          return arr[ss];
        }

        int mid = getMid(ss, se);
        st[si] = (int) sum(construct(arr, ss, mid, si * 2 + 1),
                construct(arr, mid + 1, se, si * 2 + 2));
        return st[si];
      }

    }


    static long mul(long a, long b) {
        return (a * b) % MOD;
    }

    static long sum(long a, long b) {
        return (a + b) % MOD;
    }

  public static long power(long a, long n) {
    if (n < 0) {
      return power(power(a, MOD - 2), -n);
    }
    if (n == 0) {
      return 1;
    }
    if (n == 1) {
      return a;
    }

    long r = 1;
    for (; n > 0; n >>= 1, a = (a*a) % MOD) {
      if ((n & 1) > 0) {
        r = (r*a) % MOD;
      }
    }
    return r;
  }


  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    StringTokenizer st = new StringTokenizer(br.readLine());

    int n = Integer.parseInt(st.nextToken());
    int a = Integer.parseInt(st.nextToken());
    int b = Integer.parseInt(st.nextToken());
    int q = Integer.parseInt(st.nextToken());

    if (b > 0) {
      int[] pw = new int[n+1];

        long coef = (MOD - mul(b, power(a, MOD - 2))) % MOD;
        pw[0] = 1;
        for (int i = 1; i <= n; i++) {
            pw[i] = (int) mul(pw[i - 1], coef);
        }

      int[] p = new int[n];
      st = new StringTokenizer(br.readLine());

      for (int i = 0; i < n; i++) {
        int cItem = Integer.parseInt(st.nextToken());
        p[i] = (int) mul(pw[i], cItem);
      }

      SegmentTree tree = new SegmentTree(p.length);
      tree.construct(p);
      for (int i = 0; i < q; i++) {
          st = new StringTokenizer(br.readLine());

          int op = Integer.parseInt(st.nextToken());
          int l = Integer.parseInt(st.nextToken());
          int r = Integer.parseInt(st.nextToken());
          if (op == 1) {
              tree.updateValue(p, l, (int) mul(pw[l], r));
          } else {
              long ans = tree.getSum(l, r);
          bw.write( ans == 0 ? "Yes" : "No");
          bw.write("\n");
          }
      }
    } else {
      int[] c = new int[n];
      st = new StringTokenizer(br.readLine());

      for (int i = 0; i < n; i++) {
        int cItem = Integer.parseInt(st.nextToken());
        c[i] = cItem;
      }
      for (int i = 0; i < q; i++) {
          st = new StringTokenizer(br.readLine());
          int op = Integer.parseInt(st.nextToken());
          int l = Integer.parseInt(st.nextToken());
          int r = Integer.parseInt(st.nextToken());
          if (op == 1) {
              c[l] = r;
          } else {
              long ans = c[l];
          bw.write( ans == 0 ? "Yes" : "No");
          bw.write("\n");
          }
      }
    }

    bw.close();
    br.close();
  }
}
