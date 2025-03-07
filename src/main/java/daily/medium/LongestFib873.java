package daily.medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestFib873 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8};
        int[] arr1 = {2,3,4,7,11};
        System.out.println(lenLongestFibSubseq1(arr1));
    }

//    brute force; time: O(N^2logM), space: O(N)
    public static int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
//        store all elements in a set for O(1) lookup
        Set<Integer> numSet = new HashSet<>();
        for(int num : arr)
            numSet.add(num);
        int maxLength = 0;
//        try all possible first two numbers of the sequence
        for(int start = 0 ; start < n ; start++) {
            for(int next = start + 1 ; next < n ; next++) {
//                start with the first two numbers
                int prev = arr[next];
                int curr = arr[next] + arr[start];
                int len = 2;
//                keep finding the next fibonacci number
                while(numSet.contains(curr)) {
//                    update values for next iteration
                    int temp = curr;
                    curr += prev;
                    prev = temp;
                    maxLength = Math.max(maxLength, ++len);
                }
            }
        }
        return maxLength;
    }

//    dp; time: O(n^2), space: O(n^2)
    public static int lenLongestFibSubseq1(int[] arr) {
        int n = arr.length;
//        dp[prev][curr] stores length of fibonacci sequence ending at indices prev, curr
        int[][] dp = new int[n][n];
//        map value to index for O(1) lookup
        Map<Integer, Integer> valToIdx = new HashMap<>();
        int maxLength = 0;
//        fill the dp array
        for(int curr = 0 ; curr < n ; curr++) {
            valToIdx.put(arr[curr], curr);
            for(int prev = 0 ; prev < curr ; prev++) {
//                find if there exists a previous number to form a fibonacci sequence
                int diff = arr[curr] - arr[prev];
                int prevIdx = valToIdx.getOrDefault(diff, -1);
//                update dp if valid fibonacci sequence is possible
//                diff is less than arr[prev] ensures strictly increasing sequence
                if(diff < arr[prev] && prevIdx != -1) {
                    dp[prev][curr] = dp[prevIdx][prev] + 1;
                } else{
                    dp[prev][curr] = 2;
                }
                maxLength = Math.max(maxLength, dp[prev][curr]);
            }
        }
//        return 0 if no sequence of length greater than 2 is found
        return maxLength > 2 ? maxLength : 0;
    }

//    Optimised DP with two pointer; time: O(n^2), space: O(n^2)
    public static int lenLongestFibSubseq2(int[] arr) {
        int n = arr.length, maxLength = 0;
//        dp[prev][curr] stores length of fibonacci sequences that end at prev, curr
        int[][] dp = new int[n][n];
//        find all possible pairs that sum to arr[curr]
        for(int curr = 0 ; curr < n ; curr++) {
            int start = 0;
            int end = curr - 1;
            while(start < end) {
//                use two pointers to find pairs that sum to arr[curr]
                int pairSum = arr[start] + arr[end];
                if(pairSum < arr[curr])
                    start++;
                else if(pairSum > arr[curr])
                    end--;
                else {
//                    found a valid pair, update dp
                    dp[end][curr] = dp[start][end] + 1;
                    maxLength = Math.max(maxLength, dp[end][curr]);
                    start++; end--;
                }
            }
        }
//        add 2 to include first two numbers, or return 0 if no sequence is found
        return maxLength == 0 ? 0 : maxLength + 2;
    }
}

/*
A sequence x1, x2, ..., xn is Fibonacci-like if: n >= 3
xi + xi+1 == xi+2 for all i + 2 <= n
Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest Fibonacci-like subsequence of arr. If one does not exist, return 0.
A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].
Example 1:
Input: arr = [1,2,3,4,5,6,7,8]
Output: 5
Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].
Example 2:
Input: arr = [1,3,7,11,12,14,18]
Output: 3
Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].

Constraints:
3 <= arr.length <= 1000
1 <= arr[i] < arr[i + 1] <= 109
 */

/*
Brute force:
Our first insight: if we know the first two numbers of our subsequence, we can calculate all possible next numbers in the sequence.
So, our core strategy becomes: we'll try every possible pair of numbers from our array as starting points. For each pair, we'll attempt to build the longest possible Fibonacci-like sequence.
However, repeatedly searching through an array to check whether a number exists is inefficient. A simple optimization is to store all numbers in a hash set, allowing us to check for existence in constant time instead of scanning through the array repeatedly.
Now, let's walk through how we build sequences. We pick two numbers from the array — let's call them start and next—and consider them as the first two numbers of our Fibonacci-like sequence.
Since each new number in the sequence must be the sum of the previous two, we compute this sum and check whether it exists in our set. If it does, we have successfully extended the sequence, and we shift our window forward — our new pair now consists of the previous second number and the sum we just found. We repeat this process until we can no longer extend the sequence.
Throughout this process, we keep track of the longest sequence found using a variable maxLen. Once all loops are complete, maxLen holds the length of the longest Fibonacci-like sequence found, which we return as our answer.

Let n be the length of the input array arr.
Time complexity: O(n^2logM)
The time complexity of this algorithm is determined by how many times the loops run. The outer two loops iterate over all pairs of numbers in arr, which results in O(n^2)
iterations. Within these loops, we attempt to build a Fibonacci-like sequence by repeatedly checking if the next number exists in the set.
Since Fibonacci numbers grow exponentially, a sequence that stays within a maximum value of 10^9
can have at most 43 terms. This is because the Fibonacci sequence increases so rapidly that it reaches 10^9
in at most 43 steps. As a result, the inner loop can run at most 43 times, meaning it runs in O(logM) time, where M is the largest number in arr.
Thus, combining the outer O(n^2) loops with the O(logM) inner loop, the final time complexity is O(n^2logM).
Note: Some might consider the complexity to be O(n^3), but that assumption holds only if we consider the worst case where the sequence length is O(n). However, since Fibonacci numbers grow exponentially, the maximum sequence length is actually bounded by O(logM) rather than O(n).
The Fibonacci sequence growth rate: F(k) ≈ φ^k, where φ is the golden ratio ≈1.618.

Optimised DP:
We can further optimize our dynamic programming approach by eliminating the hash map lookup. Since our array is strictly increasing, we can take advantage of this ordering to locate valid number pairs more efficiently.
Think about what happens when we're looking for numbers that could precede our current number in a Fibonacci-like sequence. If we have a current number, say 13, we're looking for two previous numbers that sum to 13. This subproblem is actually a very popular problem by itself, known as the Two-Sum problem.
The core idea remains the same: given a number arr[curr], we need to determine whether there exist two numbers arr[start] and arr[end] such that their sum equals arr[curr]. Instead of relying on a hash map to find arr[curr] - arr[end], we can use a two-pointer approach, which is a well-known technique for solving the Two-Sum problem.
As we iterate through arr, we treat each element as a potential end of a Fibonacci-like sequence. When we find a valid pair (start, end) where arr[start] + arr[end] == arr[curr], we can extend an existing sequence ending at [arr[start], arr[end]] by adding arr[curr]. We track this in a DP table dp[end][curr], setting it to dp[start][end] + 1. This way, we're building longer sequences from shorter ones we've already found.
A subtle but important detail is that we continue searching even after finding a valid pair. This is crucial because there might be multiple pairs that sum to our current number, and we need to consider all of them to find the longest possible sequence.
Similar to the previous approach, we keep track of the maximum value stored in the dp array using a variable maxLen.
 */