import java.io.*;

public class Task4 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextLong();

        long[][] prefix = new long[n + 1][n + 1];
        long[][] dp = new long[n][n];

        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = i; j < n; j++) {
                sum += a[j];
                prefix[i][j] = sum;
            }
            dp[i][i] = 0;
        }

        for (int length = 2; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;
                long best = Long.MAX_VALUE;
                long intervalSum = prefix[left][right];

                for (int mid = left; mid < right; mid++) {
                    long candidate = dp[left][mid] + dp[mid + 1][right] + intervalSum;
                    if (candidate < best) best = candidate;
                }

                dp[left][right] = best;
            }
        }

        System.out.println(dp[0][n - 1]);
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer); ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            do { c = read(); } while (c <= ' ' && c != -1);
            long sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long value = 0;
            while (c > ' ') { value = value * 10 + c - '0'; c = read(); }
            return value * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
