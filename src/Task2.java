import java.io.*;

public class Task2 {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();

        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) grid[i] = fs.next().toCharArray();

        if (grid[0][0] == '#' || grid[n - 1][m - 1] == '#') {
            System.out.println(0);
            return;
        }

        int[] dp = new int[m];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '#') {
                    dp[j] = 0;
                } else if (j > 0) {
                    dp[j] = (int) (((long) dp[j] + dp[j - 1]) % MOD);
                }
            }
        }

        System.out.println(dp[m - 1]);
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
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do { c = read(); } while (c <= ' ' && c != -1);
            while (c > ' ') { sb.append((char)c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
