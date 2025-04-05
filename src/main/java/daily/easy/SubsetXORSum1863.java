package daily.easy;

import java.util.ArrayList;
import java.util.List;

public class SubsetXORSum1863 {
    public static void main(String[] args) {
        SubsetXORSum1863 s = new SubsetXORSum1863();
        int[] nums = {5,1,6};
        System.out.println(s.subsetXORSum(nums));
    }

//    optimized backtracking; time: O(2^n), space: O(n)
    public int subsetXORSum(int[] nums) {
        return xorSum(nums, 0, 0);
    }

    private int xorSum(int[] nums, int index, int currentXOR) {
        if(index == nums.length)
            return currentXOR;
        int withElement = xorSum(nums, index + 1, currentXOR ^ nums[index]);
        int withoutElement = xorSum(nums, index + 1, currentXOR);
        return withElement + withoutElement;
    }

//    backtracking(generate all possible subsets); time: O(N.2^N), space: O(N.2^N)
    public int subsetXORSum1(int[] nums) {
        int result = 0;
        List<List<Integer>> subsetList = new ArrayList<>();
//        generate all possible subsets
        generateSubsets(nums, 0, new ArrayList<>(), subsetList);
        for(List<Integer> subset : subsetList) {
//            compute the xor total for each of the subset and add to the result
            int subsetXORTotal = 0;
            for(int num : subset)
                subsetXORTotal ^= num;
            result += subsetXORTotal;
        }
        return result;
    }

    private void generateSubsets(int[] nums, int index, List<Integer> subset, List<List<Integer>> subsetList) {
//        base case: index reached end of nums
//        add the current subset to subsets
        if(index == nums.length) {
//            note: you have to add a new arraylist and pass subset to it, can't do it directly (will give incorrect result)
            subsetList.add(new ArrayList<>(subset));
//            don't forget this step
            return;
        }
//        generate subsets with nums[i]
        subset.add(nums[index]);
        generateSubsets(nums, index + 1, subset, subsetList);
//        backtrack, remove last added element
        subset.removeLast();
//        generate sunsets without nums[i]
        generateSubsets(nums, index + 1, subset, subsetList);
    }
}

/*
The XOR total of an array is defined as the bitwise XOR of all its elements, or 0 if the array is empty.
For example, the XOR total of the array [2,5,6] is 2 XOR 5 XOR 6 = 1.
Given an array nums, return the sum of all XOR totals for every subset of nums.
Note: Subsets with the same elements should be counted multiple times.
An array a is a subset of an array b if a can be obtained from b by deleting some (possibly zero) elements of b.
Example 1:
Input: nums = [1,3]
Output: 6
Explanation: The 4 subsets of [1,3] are:
- The empty subset has an XOR total of 0.
- [1] has an XOR total of 1.
- [3] has an XOR total of 3.
- [1,3] has an XOR total of 1 XOR 3 = 2.
0 + 1 + 3 + 2 = 6
Example 2:
Input: nums = [5,1,6]
Output: 28
Explanation: The 8 subsets of [5,1,6] are:
- The empty subset has an XOR total of 0.
- [5] has an XOR total of 5.
- [1] has an XOR total of 1.
- [6] has an XOR total of 6.
- [5,1] has an XOR total of 5 XOR 1 = 4.
- [5,6] has an XOR total of 5 XOR 6 = 3.
- [1,6] has an XOR total of 1 XOR 6 = 7.
- [5,1,6] has an XOR total of 5 XOR 1 XOR 6 = 2.
0 + 5 + 1 + 6 + 4 + 3 + 7 + 2 = 28
Example 3:
Input: nums = [3,4,5,6,7,8]
Output: 480
Explanation: The sum of all XOR totals for every subset is 480.

Constraints:
1 <= nums.length <= 12
1 <= nums[i] <= 20
 */

/*
Backtracking: generate all possible subsets
Let N be the size of nums.

Time complexity: O(N⋅2^N)

Each element can be included or excluded from any given subset, meaning there are 2^N
possible subsets. Generating them takes O(2^N).
We iterate through each of the 2^N subsets to calculate the result. The average size of each subset is approximately N/2
, so it takes O(N/2.2^N).
Therefore, the overall time complexity is O(2^N +N/2.2^N), which we can represent as O(N⋅2^N).

Space complexity: O(N⋅2^N)
The subsets list will contain 2^N subsets with an average size of N/2, so it requires O(N/2⋅2^N) space.
The recursion depth can reach size N because we generate subsets with and without each index in nums. The recursive call stack may use up to O(N) space.

Optimized Backtracking:
Complexity:
Let N be the size of nums.
Time complexity: O(2^N)
We traverse through each of the 2^N subsets to calculate the result.
Space complexity: O(N)
The recursion depth can reach N because we calculate the XOR totals for each of the N indices in nums. The recursive call stack may require up to O(N) space.
 */