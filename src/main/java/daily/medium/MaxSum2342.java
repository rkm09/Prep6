package daily.medium;

import common.Pair;

import java.util.*;

public class MaxSum2342 {
    public static void main(String[] args) {
        int[] nums = {18,43,36,13,7};
        int[] nums1 = {368,369,307,304,384,138,90,279,35,396,114,328,251,364,300,191,438,467,183};
        System.out.println(maximumSum(nums));
    }

//    priority queue (minheap); time: O(nlogm), space: O(n) [m - maximum number in nums]  [fastest]
    public static int maximumSum(int[] nums) {
        PriorityQueue<Integer>[] digitSumGroups = new PriorityQueue[82];
//        note: don't by mistake use Arrays.fill here, as it will just assign a reference to one PQ which has been specified as the chosen value
        for(int i = 0 ; i < 82 ; i++)
            digitSumGroups[i] = new PriorityQueue<>();
        for(int num : nums) {
            int digitSum = calculateDigitSum(num);
            digitSumGroups[digitSum].offer(num);
            if(digitSumGroups[digitSum].size() > 2) {
                digitSumGroups[digitSum].poll();
            }
        }
        int maxSum = -1;
        for(PriorityQueue<Integer> minHeap : digitSumGroups) {
            if(minHeap.size() == 2) {
                int first = minHeap.poll();
                int second = minHeap.poll();
                maxSum = Math.max(maxSum, first + second);
            }
        }
        return maxSum;
    }

    //    def; map; [fast]
    public static int maximumSum2(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int num : nums) {
            int curr = num, sum = 0;
            while(curr != 0) {
                int rdr = curr % 10;
                sum += rdr;
                curr /= 10;
            }
            map.computeIfAbsent(sum, k -> new ArrayList<>()).add(num);
        }
        int maxSum = -1;
        for(int key : map.keySet()) {
            List<Integer> numList = map.get(key);
            if(numList.size() > 1) {
                numList.sort((a,b) -> b - a);
                maxSum = Math.max(maxSum, numList.get(0) + numList.get(1));
            }
        }
        return maxSum;
    }

//    sorting; time: O(nlogn), space: O(n)
    public static int maximumSum1(int[] nums) {
        int n = nums.length;
        Pair<Integer, Integer>[] digitSumPairs = new Pair[n];
        for(int i = 0 ; i < n ; i++) {
            digitSumPairs[i] = new Pair<>(calculateDigitSum(nums[i]), nums[i]);
        }
        Arrays.sort(digitSumPairs, Comparator.comparing(Pair<Integer,Integer>::getKey)
                .thenComparing(Pair::getValue));
        int maxSum = -1;
        for(int i = 1 ; i < n ; i++) {
            int prevSum = digitSumPairs[i].getKey();
            int currSum = digitSumPairs[i - 1].getKey();
            if(prevSum == currSum) {
                maxSum = Math.max(maxSum, digitSumPairs[i].getValue() + digitSumPairs[i - 1].getValue());
            }
        }
        return maxSum;
    }

    private static int calculateDigitSum(int num) {
        int digitSum = 0;
        while(num != 0) {
            digitSum += num % 10;
            num /= 10;
        }
        return digitSum;
    }

}

/*
You are given a 0-indexed array nums consisting of positive integers. You can choose two indices i and j, such that i != j, and the sum of digits of the number nums[i] is equal to that of nums[j].
Return the maximum value of nums[i] + nums[j] that you can obtain over all possible indices i and j that satisfy the conditions.
Example 1:
Input: nums = [18,43,36,13,7]
Output: 54
Explanation: The pairs (i, j) that satisfy the conditions are:
- (0, 2), both numbers have a sum of digits equal to 9, and their sum is 18 + 36 = 54.
- (1, 4), both numbers have a sum of digits equal to 7, and their sum is 43 + 7 = 50.
So the maximum sum that we can obtain is 54.
Example 2:
Input: nums = [10,12,19,14]
Output: -1
Explanation: There are no two numbers that satisfy the conditions, so we return -1.

Constraints:
1 <= nums.length <= 105
1 <= nums[i] <= 109
 */