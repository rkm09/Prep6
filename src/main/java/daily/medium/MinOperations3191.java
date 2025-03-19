package daily.medium;

public class MinOperations3191 {
    public static void main(String[] args) {
        int[] nums = {0,1,1,1,0,0};
        System.out.println(minOperations(nums));
    }

//    sliding window; time: O(n), space: O(1)
    public static int minOperations(int[] nums) {
        int count = 0, n = nums.length;
        for(int i = 2 ; i < n ; i++) {
            if(nums[i - 2] == 0) {
                count++;
                nums[i - 2] ^= 1;
                nums[i - 1] ^= 1;
                nums[i] ^= 1;
            }
        }
        int sum = 0;
        for(int num : nums)
            sum += num;
        return sum == n ? count : -1;
    }
}

/*
You are given a binary array nums.
You can do the following operation on the array any number of times (possibly zero):
Choose any 3 consecutive elements from the array and flip all of them.
Flipping an element means changing its value from 0 to 1, and from 1 to 0.
Return the minimum number of operations required to make all elements in nums equal to 1. If it is impossible, return -1.
Example 1:
Input: nums = [0,1,1,1,0,0]
Output: 3
Explanation:
We can do the following operations:
Choose the elements at indices 0, 1 and 2. The resulting array is nums = [1,0,0,1,0,0].
Choose the elements at indices 1, 2 and 3. The resulting array is nums = [1,1,1,0,0,0].
Choose the elements at indices 3, 4 and 5. The resulting array is nums = [1,1,1,1,1,1].
Example 2:
Input: nums = [0,1,1,1]
Output: -1
Explanation:
It is impossible to make all elements equal to 1.

Constraints:
3 <= nums.length <= 105
0 <= nums[i] <= 1
 */

/*
Sliding Window:
In the previous approach, we used a deque to track active flips and determine how many times each index had been flipped. Now, we take a different approach by modifying the array directly as we iterate. The core idea remains the same: flipping three consecutive elements at a time while ensuring that every 0 gets converted to 1 in the most efficient way possible.
Instead of maintaining a separate structure to track flips, we will scan the array from left to right and only focus on the last element of each triplet to determine if a flip is needed. This means that for each index i, we check whether nums[i - 2] is still 0. If it is, then we must flip the triplet ending at i (nums[i - 2], nums[i - 1], nums[i]).
By flipping in this way, we ensure that every 0 gets handled at the earliest possible opportunity, preventing any unflippable 0s from being left behind. This also ensures that we are using the minimum number of operations because each flip is only applied when absolutely necessary.
 */