package daily.easy;

public class MinimumRecolors2379 {
    public static void main(String[] args) {
        String blocks = "WBBWWBBWBW";
        System.out.println(minimumRecolors(blocks, 7));
    }

//   fixed sliding window; time: O(n), space: O(1)
    public static int minimumRecolors(String blocks, int k) {
        int left = 0, numWhites = 0;
        int n = blocks.length(), minRecolor = Integer.MAX_VALUE;
//        move right pointer
        for(int right = 0 ; right < n ; right++) {
//            increment numWhites if block at right pointer is white
            if(blocks.charAt(right) == 'W')
                numWhites++;
//            k consecutive elements are found
            if(right - left + 1 == k) {
//                udpate minimum
                minRecolor = Math.min(minRecolor, numWhites);
//                decrement left pointer if block at left is white
                if(blocks.charAt(left) == 'W')
                    numWhites--;
//                move left pointer
                left++;
            }
        }
        return minRecolor;
    }
}

/*
You are given a 0-indexed string blocks of length n, where blocks[i] is either 'W' or 'B', representing the color of the ith block. The characters 'W' and 'B' denote the colors white and black, respectively.
You are also given an integer k, which is the desired number of consecutive black blocks.
In one operation, you can recolor a white block such that it becomes a black block.
Return the minimum number of operations needed such that there is at least one occurrence of k consecutive black blocks.
Example 1:
Input: blocks = "WBBWWBBWBW", k = 7
Output: 3
Explanation:
One way to achieve 7 consecutive black blocks is to recolor the 0th, 3rd, and 4th blocks
so that blocks = "BBBBBBBWBW".
It can be shown that there is no way to achieve 7 consecutive black blocks in less than 3 operations.
Therefore, we return 3.
Example 2:
Input: blocks = "WBWBBBW", k = 2
Output: 0
Explanation:
No changes need to be made, since 2 consecutive black blocks already exist.
Therefore, we return 0.

Constraints:
n == blocks.length
1 <= n <= 100
blocks[i] is either 'W' or 'B'.
1 <= k <= n
 */