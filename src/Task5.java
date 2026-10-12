import java.io.*;
import java.util.*;

public class Task5 {
    static final long NEG_INF = Long.MIN_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();

        long[][] previous = new long[m][k + 1];
        for (int j = 0; j < m; j++) Arrays.fill(previous[j], NEG_INF);

        for (int i = 0; i < n; i++) {
            long[][] current = new long[m][k + 1];
            for (int j = 0; j < m; j++) Arrays.fill(current[j], NEG_INF);

            for (int j = 0; j < m; j++) {
                long value = fs.nextLong();

                if (i == 0 && j == 0) {
                    current[j][0] = value;
                    if (value < 0 && k > 0) current[j][1] = 0;
                    continue;
                }

                for (int used = 0; used <= k; used++) {
                    long bestPrevious = NEG_INF;
                    if (i > 0) bestPrevious = Math.max(bestPrevious, previous[j][used]);
                    if (j > 0) bestPrevious = Math.max(bestPrevious, current[j - 1][used]);

                    if (bestPrevious != NEG_INF) {
                        current[j][used] = Math.max(current[j][used], bestPrevious + value);
                    }

                    if (value < 0 && used > 0) {
                        long beforeShield = NEG_INF;
                        if (i > 0) beforeShield = Math.max(beforeShield, previous[j][used - 1]);
                        if (j > 0) beforeShield = Math.max(beforeShield, current[j - 1][used - 1]);
                        if (beforeShield != NEG_INF) {
                            current[j][used] = Math.max(current[j][used], beforeShield);
                        }
                    }
                }
            }
            previous = current;
        }

        long answer = NEG_INF;
        for (int used = 0; used <= k; used++) {
            answer = Math.max(answer, previous[m - 1][used]);
        }
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
