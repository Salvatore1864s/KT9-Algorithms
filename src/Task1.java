import java.io.*;

public class Task1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int n = s.length();

        boolean[][] palindrome = new boolean[n][n];
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for (int right = 0; right < n; right++) {
            dp[right + 1] = Integer.MAX_VALUE / 2;

            for (int left = right; left >= 0; left--) {
                if (s.charAt(left) == s.charAt(right)
                        && (right - left <= 2 || palindrome[left + 1][right - 1])) {
                    palindrome[left][right] = true;
                    dp[right + 1] = Math.min(dp[right + 1], dp[left] + 1);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
