package daily.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DistinctNumbers1852 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,2,2,1,3};
        System.out.println(Arrays.toString(distinctNumbers(nums, 3)));
    }

//    hashmap and fixed size sliding window; time: O(n), space: O(n)
    public static int[] distinctNumbers(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Map<Integer, Integer> freqMap = new HashMap<>();
//        process first window
        for(int pos = 0 ; pos < k ; pos++)
            freqMap.put(nums[pos], freqMap.getOrDefault(nums[pos], 0 ) + 1);
        res[0] = freqMap.size();
//        slide window and update counts
        for(int pos = k ; pos < n ; pos++) {
            int leftNum = nums[pos - k];
//            remove leftmost element of previous window; be careful of .get(index) - 1
            freqMap.put(leftNum, freqMap.get(leftNum) - 1);
            freqMap.remove(leftNum, 0);
//            add rightmost element of current window
            int rightNum = nums[pos];
            freqMap.put(rightNum, freqMap.getOrDefault(rightNum, 0) + 1);
//            store distinct count for current window
            res[pos - k + 1] = freqMap.size();
        }
        return res;
    }
}

/*
Given an integer array nums and an integer k, you are asked to construct the array ans of size n-k+1 where ans[i] is the number of distinct numbers in the subarray nums[i:i+k-1] = [nums[i], nums[i+1], ..., nums[i+k-1]].
Return the array ans.
Example 1:
Input: nums = [1,2,3,2,2,1,3], k = 3
Output: [3,2,2,2,3]
Explanation: The number of distinct elements in each subarray goes as follows:
- nums[0:2] = [1,2,3] so ans[0] = 3
- nums[1:3] = [2,3,2] so ans[1] = 2
- nums[2:4] = [3,2,2] so ans[2] = 2
- nums[3:5] = [2,2,1] so ans[3] = 2
- nums[4:6] = [2,1,3] so ans[4] = 3
Example 2:
Input: nums = [1,1,1,1,2,3,4], k = 4
Output: [1,2,3,4]
Explanation: The number of distinct elements in each subarray goes as follows:
- nums[0:3] = [1,1,1,1] so ans[0] = 1
- nums[1:4] = [1,1,1,2] so ans[1] = 2
- nums[2:5] = [1,1,2,3] so ans[2] = 3
- nums[3:6] = [1,2,3,4] so ans[3] = 4

Constraints:
1 <= k <= nums.length <= 105
1 <= nums[i] <= 105

 */

/*
Hashmap + Fixed sliding window:
Notice that each subarray is actually a fixed-size window in the array, which keeps moving.
For example, the first subarray contains elements from index 0 to k - 1. When we slide this window one position right, we get the second subarray, which spans from index 1 to k. This technique is called the fixed-size sliding window approach. It's efficient because getting the next subarray only requires removing the leftmost element and adding one new element, instead of creating entirely new subarrays.
 */