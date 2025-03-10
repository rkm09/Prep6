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

//    frequency array; time: O(n), space: O(n) [fastest]
//    feasible as the maximum element value is 10^5
    public static int[] distinctNumbers1(int[] nums, int k) {
        int maxValue = 0, n = nums.length;
        for(int num : nums)
            maxValue = Math.max(maxValue, num);
        int[] freq = new int[maxValue + 1];
        int[] ans = new int[n - k + 1];
        int distinctCount = 0, ansPos = 0;
        for(int pos = 0 ; pos < n ; pos++) {
            freq[nums[pos]]++;
            if(freq[nums[pos]] == 1)
                distinctCount++;
            if(pos >= k) {
                freq[nums[pos - k]]--;
                if(freq[nums[pos - k]] == 0)
                    distinctCount--;
            }
            if(pos + 1 >= k)
                ans[ansPos++] = distinctCount;
        }
        return ans;
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

Frequency Array:
While we typically think of hash map operations as taking constant time, this isn't always true. In reality, these operations are amortized O(1), meaning they usually take constant time but can sometimes slow down to O(n), especially with large datasets. Hash maps also carry extra overhead due to their hash functions and pointer management.
This is why sometimes it's better to use a frequency array instead of a hash map. A frequency array works similarly to a map, with the index of the array serving as the key and the element stored at that index as the value. In this problem, using such an array is possible, as the largest value in nums is relatively small (nums[i]<=10
5). Overall, this approach is more time-efficient because array operations are guaranteed to take constant time and don't have the extra overhead that hash maps do.
We create a frequency array called freq to track how often each number appears. As we slide our window across the array nums, we update these frequencies just like we did with the hash map approach.
However, since we can't simply check the size of our data structure to count distinct elements anymore, we need a new strategy. We'll use a variable called distinctCount to keep track of how many different numbers we have. When we add a new number and its frequency becomes 1, we increase distinctCount because we've found a new unique number. Similarly, when we remove a number and its frequency drops to 0, we decrease distinctCount because we've lost a unique number.

 */

