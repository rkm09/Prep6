package daily.medium;

import java.util.*;

public class ZeroArray3356 {
    public static void main(String[] args) {
        int[][] queries = {{0,2,1},{0,2,1},{1,1,3}};
        int[] nums = {2,0,2};
        System.out.println(minZeroArray(nums, queries));
    }


//    binary search; time: O(logM(M+N)), space: O(N)   [M size of queries]
    public static int minZeroArray(int[] nums, int[][] queries) {
        int left = 0, right = queries.length;
//        zero array isn't formed after all queries are processed
        if(!checkCurrentIndexForZero(nums, queries, right)) return -1;
//        binary search
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(checkCurrentIndexForZero(nums, queries, mid))
                right = mid - 1;
            else
                left = mid + 1;
        }
//        return the earliest query that zero array can be formed with
        return left;
    }

    private static boolean checkCurrentIndexForZero(int[] nums, int[][] queries, int k) {
        int n = nums.length, sum = 0;
        int[] differenceArray = new int[n + 1];
//        process query
        for(int i = 0 ; i < k ; i++) {
            int left = queries[i][0], right = queries[i][1];
            int val = queries[i][2];
//            process start and end of the range
            differenceArray[left] += val;
            differenceArray[right + 1] -= val;
        }
//        check if zero can be formed
        for(int index = 0 ; index < n ; index++) {
            sum += differenceArray[index];
            if(sum < nums[index]) return false;
        }
        return true;
    }

//    def; brute force; TLE !!
    public static int minZeroArrayX(int[] nums, int[][] queries) {
        int count = 0, diff, totalDiff;
        int sum = Arrays.stream(nums).sum();
        if(sum == 0) return 0;
        for(int[] query : queries) {
            int left = query[0];
            int right = query[1];
            int val = query[2];
            totalDiff = 0;
            while(left <= right) {
                if(nums[left] != 0) {
                    diff = Math.min(val, nums[left]);
                    nums[left] -= diff;
                    totalDiff += diff;
                }
                left++;
            }
            sum -= totalDiff;
            count++;
            if(sum == 0) return count;
        }
        return -1;
    }
}

/*
You are given an integer array nums of length n and a 2D array queries where queries[i] = [li, ri, vali].
Each queries[i] represents the following action on nums:
Decrement the value at each index in the range [li, ri] in nums by at most vali.
The amount by which each value is decremented can be chosen independently for each index.
A Zero Array is an array with all its elements equal to 0.
Return the minimum possible non-negative value of k, such that after processing the first k queries in sequence, nums becomes a Zero Array. If no such k exists, return -1.
Example 1:
Input: nums = [2,0,2], queries = [[0,2,1],[0,2,1],[1,1,3]]
Output: 2
Explanation:
For i = 0 (l = 0, r = 2, val = 1):
Decrement values at indices [0, 1, 2] by [1, 0, 1] respectively.
The array will become [1, 0, 1].
For i = 1 (l = 0, r = 2, val = 1):
Decrement values at indices [0, 1, 2] by [1, 0, 1] respectively.
The array will become [0, 0, 0], which is a Zero Array. Therefore, the minimum value of k is 2.
Example 2:
Input: nums = [4,3,2,1], queries = [[1,3,2],[0,2,1]]
Output: -1
Explanation:
For i = 0 (l = 1, r = 3, val = 2):
Decrement values at indices [1, 2, 3] by [2, 2, 1] respectively.
The array will become [4, 1, 0, 0].
For i = 1 (l = 0, r = 2, val = 1):
Decrement values at indices [0, 1, 2] by [1, 1, 0] respectively.
The array will become [3, 0, 0, 0], which is not a Zero Array.

Constraints:
1 <= nums.length <= 105
0 <= nums[i] <= 5 * 105
1 <= queries.length <= 105
queries[i].length == 3
0 <= li <= ri < nums.length
1 <= vali <= 5
 */