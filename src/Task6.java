import java.io.*;
import java.util.*;

public class Task6 {
    static final long NEG_INF = Long.MIN_VALUE / 4;
    static int n, k;
    static long[] value;
    static List<Integer>[] graph;
    static long[][] dp;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        n = fs.nextInt();
        k = fs.nextInt();

        value = new long[n];
        for (int i = 0; i < n; i++) value[i] = fs.nextLong();

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int u = fs.nextInt() - 1;
            int v = fs.nextInt() - 1;
            graph[u].add(v);
            graph[v].add(u);
        }

        int[] parent = new int[n];
        Arrays.fill(parent, -2);
        int[] order = new int[n];
        int orderSize = 0;
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        parent[0] = -1;

        while (top > 0) {
            int u = stack[--top];
            order[orderSize++] = u;
            for (int v : graph[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                stack[top++] = v;
            }
        }

        dp = new long[n][k + 1];
        for (int u = 0; u < n; u++) Arrays.fill(dp[u], NEG_INF);

        for (int idx = orderSize - 1; idx >= 0; idx--) {
            int u = order[idx];
            dp[u][1] = value[u];

            for (int v : graph[u]) {
                if (parent[v] != u) continue;

                long[] merged = dp[u].clone();
                for (int have = 1; have <= k; have++) {
                    if (dp[u][have] == NEG_INF) continue;
                    for (int childCount = 1; have + childCount <= k; childCount++) {
                        if (dp[v][childCount] == NEG_INF) continue;
                        merged[have + childCount] = Math.max(
                                merged[have + childCount],
                                dp[u][have] + dp[v][childCount]
                        );
                    }
                }
                dp[u] = merged;
            }
        }

        long answer = NEG_INF;
        for (int u = 0; u < n; u++) answer = Math.max(answer, dp[u][k]);
        System.out.println(answer);
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
