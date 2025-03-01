package daily.easy;

import java.util.Arrays;

public class ApplyOperations2460 {
    public static void main(String[] args) {
        int[] nums = {1,2,2,1,1,0};
        System.out.println(Arrays.toString(applyOperations(nums)));
    }

//    one pass; time: O(n), space: O(1) [in-place]
    public static int[] applyOperations(int[] nums) {
        int n = nums.length, writeIdx = 0;
//        merge adjacent non zero elements
        for(int idx = 0 ; idx < n ; idx++) {
           if(idx < n - 1 && nums[idx] == nums[idx + 1]
                   && nums[idx] != 0) {
               nums[idx] *= 2;
               nums[idx + 1] = 0;
           }
//           step 2: shift the non-zero elements to the front
           if(nums[idx] != 0) {
               if(writeIdx != idx) {
                   int temp = nums[idx];
                   nums[idx] = nums[writeIdx];
                   nums[writeIdx] = temp;
               }
               writeIdx++;
           }
        }
        return nums;
    }

//    def; time: O(n), space: O(1)  [in-place, 2 pass]
    public static int[] applyOperations1(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for(int i = 1 ; i < n ; i++) {
            if(nums[i - 1] == nums[i]) {
                nums[i - 1] *= 2;
                nums[i] = 0;
            }
        }
        int idx = 0;
        for(int num : nums) {
            if(num != 0) {
                res[idx++] = num;
            }
        }
        return res;
    }
}

/*
You are given a 0-indexed array nums of size n consisting of non-negative integers.
You need to apply n - 1 operations to this array where, in the ith operation (0-indexed), you will apply the following on the ith element of nums:
If nums[i] == nums[i + 1], then multiply nums[i] by 2 and set nums[i + 1] to 0. Otherwise, you skip this operation.
After performing all the operations, shift all the 0's to the end of the array.
For example, the array [1,0,2,0,0,1] after shifting all its 0's to the end, is [1,2,1,0,0,0].
Return the resulting array.
Note that the operations are applied sequentially, not all at once.
Example 1:
Input: nums = [1,2,2,1,1,0]
Output: [1,4,2,0,0,0]
Explanation: We do the following operations:
- i = 0: nums[0] and nums[1] are not equal, so we skip this operation.
- i = 1: nums[1] and nums[2] are equal, we multiply nums[1] by 2 and change nums[2] to 0. The array becomes [1,4,0,1,1,0].
- i = 2: nums[2] and nums[3] are not equal, so we skip this operation.
- i = 3: nums[3] and nums[4] are equal, we multiply nums[3] by 2 and change nums[4] to 0. The array becomes [1,4,0,2,0,0].
- i = 4: nums[4] and nums[5] are equal, we multiply nums[4] by 2 and change nums[5] to 0. The array becomes [1,4,0,2,0,0].
After that, we shift the 0's to the end, which gives the array [1,4,2,0,0,0].
Example 2:
Input: nums = [0,1]
Output: [1,0]
Explanation: No operation can be applied, we just shift the 0 to the end.

Constraints:
2 <= nums.length <= 2000
0 <= nums[i] <= 1000
 */