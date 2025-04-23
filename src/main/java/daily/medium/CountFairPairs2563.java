package daily.medium;

import java.util.Arrays;

public class CountFairPairs2563 {
    public static void main(String[] args) {
        int[] nums = {0,1,7,4,4,5};
        System.out.println(countFairPairs(nums, 3, 6));
    }

//    two pointer; time: O(nlogn), space: O(logn)  [faster]
    public static long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        return lowerBound(nums, upper + 1) - lowerBound(nums, lower);
    }

//    calculate the number of pairs with sum less than 'value'
    private static long lowerBound(int[] nums, int value) {
        int left = 0, right = nums.length - 1;
        long result = 0;
        while(left < right) {
            int sum = nums[left] + nums[right];
//            if sum is less than value, add the range to the result and move on to the next index
            if(sum < value) {
                result += (right - left);
                left++;
            } else  // otherwise shift the right pointer backward till we get a valid window
                right--;
        }
        return result;
    }

//    binary search; time: O(nlogn), space: O(logn)
    public static long countFairPairs1(int[] nums, int lower, int upper) {
        int n = nums.length;
        Arrays.sort(nums);
        long ans = 0;
        for(int i = 0 ; i < n ; i++) {
//            assume we have picked nums[i] as the first pair element
//            'low' indicates number of possible pairs with sum < lower
            long low = lowerBinSearch(nums, i + 1, n - 1, lower - nums[i]);
//            'high' indicates number of possible pairs with sum <= upper
            long high = lowerBinSearch(nums, i + 1, n - 1, upper - nums[i] + 1);
//            their difference gives the number of elements within the given range
            ans += (high - low);
        }
        return ans;
    }

    private static long lowerBinSearch(int[] nums, int low, int high, int element) {
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] >= element)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return low;
    }
}

/*
Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.
A pair (i, j) is fair if:
0 <= i < j < n, and
lower <= nums[i] + nums[j] <= upper
Example 1:
Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
Output: 6
Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
Example 2:
Input: nums = [1,7,9,2,5], lower = 11, upper = 11
Output: 1
Explanation: There is a single fair pair: (2,3).

Constraints:
1 <= nums.length <= 105
nums.length == n
-109 <= nums[i] <= 109
-109 <= lower <= upper <= 109
 */
