import java.io.*;
import java.util.*;

public class Task3 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();

        long[] value = new long[n];
        for (int i = 0; i < n; i++) value[i] = fs.nextLong();

        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n];
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

        long[] take = new long[n];
        long[] skip = new long[n];

        for (int i = orderSize - 1; i >= 0; i--) {
            int u = order[i];
            take[u] = value[u];
            skip[u] = 0;

            for (int v : graph[u]) {
                if (parent[v] == u) {
                    take[u] += skip[v];
                    skip[u] += Math.max(take[v], skip[v]);
                }
            }
        }

        System.out.println(Math.max(take[0], skip[0]));
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
