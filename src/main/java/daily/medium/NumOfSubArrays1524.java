package daily.medium;


public class NumOfSubArrays1524 {
    private static final int MOD = (int) 1e9 + 7;
    public static void main(String[] args) {
        int[] arr = {1,3,5};
        System.out.println(numOfSubarrays(arr));
    }

//    dp; time: O(n), space: O(n)
    public static int numOfSubarrays(int[] arr) {
        int n = arr.length;
        int[] dpEven = new int[n];
        int[] dpOdd = new int[n];
//        convert elements to 0 (even) or 1 (odd)
        for(int num = 0 ; num < n ; num++)
            arr[num] %= 2;
//        initialization based on the last element
        if(arr[n - 1] == 1) dpOdd[n - 1] = 1;
        else dpEven[n - 1] = 1;
        for(int num = n - 2 ; num >= 0 ; num--) {
            if(arr[num] == 1) {
//                odd element contributes to odd sub arrays
                dpOdd[num] = (1 + dpEven[num + 1]) % MOD;
//                even element continues the pattern
                dpEven[num] = dpOdd[num + 1];
            } else {
//                even element contributes to even sub arrays
                dpEven[num] = (1 + dpEven[num + 1]) % MOD;
//                odd element continues the pattern
                dpOdd[num] = dpOdd[num + 1];
            }
        }
//        sum all the odd sub arrays
        int count = 0;
        for(int oddCount : dpOdd) {
            count += oddCount;
            count %= MOD;
        }

        return count;
    }

//    brute force; TLE !! time : O(n^2), space: O(1)
    public static int numOfSubarraysX(int[] arr) {
        int n = arr.length;
        int count = 0;
        for(int startIndex = 0 ; startIndex < n ; startIndex++) {
            int currentSum = 0;
            for(int endIndex = startIndex ; endIndex < n ; endIndex++) {
                currentSum += arr[endIndex];
                if(currentSum % 2 != 0)
                    count++;
            }
        }
        return count % MOD;
    }
}

/*
Given an array of integers arr, return the number of subarrays with an odd sum.
Since the answer can be very large, return it modulo 109 + 7.
Example 1:
Input: arr = [1,3,5]
Output: 4
Explanation: All subarrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
All sub-arrays sum are [1,4,9,3,8,5].
Odd sums are [1,9,3,5] so the answer is 4.
Example 2:
Input: arr = [2,4,6]
Output: 0
Explanation: All subarrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
All sub-arrays sum are [2,6,12,4,10,6].
All sub-arrays have even sum and the answer is 0.
Example 3:
Input: arr = [1,2,3,4,5,6,7]
Output: 16

Constraints:
1 <= arr.length <= 105
1 <= arr[i] <= 100
 */

/*
DP:
The key idea here is that we don’t need to compute the sum of every subarray explicitly. Instead, we only need to find whether the sum is even or odd. This significantly simplifies the problem because, rather than recalculating subarray sums, we only need to keep track of their parity.
To see why this works, let's consider how adding a number to a subarray affects its sum. If a subarray currently has an odd sum, then adding an odd number will make it even, while adding an even number will keep it odd. Similarly, if a subarray has an even sum, then adding an odd number will make it odd, and adding an even number will keep it even. This means that instead of keeping track of all possible subarray sums, we only need to count how many subarrays up to a given index have even sums and how many have odd sums.
With this in mind, we can now formulate our dynamic programming approach. For each index, we maintain two arrays:
dpEven[i] — the number of subarrays ending at index i with an even sum.
dpOdd[i] — the number of subarrays ending at index i with an odd sum.
We initialize these based on the last element of the array. If the last element is even, it forms a single even subarray by itself, so dpEven is initialized accordingly. If it's odd, it forms a single odd subarray, so dpOdd is initialized instead.
Now, we process the array in reverse. For each element, we update dpEven and dpOdd by looking at the next index. The key observation is that if the current element is odd, it flips the parity of all subarrays ending at the next index: even subarrays become odd, and odd subarrays become even. If the element is even, it preserves the parity, meaning even subarrays remain even, and odd subarrays remain odd.
By processing each element once and updating these two counts in constant time, we efficiently keep track of the number of subarrays with even and odd sums. At the end, we sum up all values in dpOdd, since each of these represents a subarray that ends at some index and has an odd sum.
 */