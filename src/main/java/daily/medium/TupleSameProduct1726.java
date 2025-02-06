package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class TupleSameProduct1726 {
    public static void main(String[] args) {
        int[] nums = {2,3,4,6};
        System.out.println(tupleSameProduct(nums));
    }

//    product freq map; time: O(n^2), space: O(n^2)
    public static int tupleSameProduct(int[] nums) {
        int n = nums.length;
//        initialize a map to store the frequency of each product of pairs
        Map<Integer, Integer> pairProductFrequency = new HashMap<>();
//        iterate through each number of pairs in `nums`
        for(int firstIndex = 0 ; firstIndex < n ; firstIndex++) {
            for(int secondIndex = firstIndex + 1 ; secondIndex < n ; secondIndex++) {
                int product = nums[firstIndex] * nums[secondIndex];
                pairProductFrequency.put((product),
                        pairProductFrequency.getOrDefault(product, 0) + 1);
            }
        }
        int totalNumberOfTuples = 0;
        for(int productValue : pairProductFrequency.keySet()) {
            int productFrequency = pairProductFrequency.get(productValue);
//            calculate the number of ways to choose a product, taken 2 at a time
            int pairsOfEqualProduct = ((productFrequency - 1) * productFrequency) / 2;
//            add the number of pairs for this product to the total
//            each pair combination can form 8 tuples
            totalNumberOfTuples += 8 * pairsOfEqualProduct;
        }
        return totalNumberOfTuples;
    }
}

/*
Given an array nums of distinct positive integers, return the number of tuples (a, b, c, d) such that a * b = c * d where a, b, c, and d are elements of nums, and a != b != c != d.
Example 1:
Input: nums = [2,3,4,6]
Output: 8
Explanation: There are 8 valid tuples:
(2,6,3,4) , (2,6,4,3) , (6,2,3,4) , (6,2,4,3)
(3,4,2,6) , (4,3,2,6) , (3,4,6,2) , (4,3,6,2)
Example 2:
Input: nums = [1,2,4,5,10]
Output: 16
Explanation: There are 16 valid tuples:
(1,10,2,5) , (1,10,5,2) , (10,1,2,5) , (10,1,5,2)
(2,5,1,10) , (2,5,10,1) , (5,2,1,10) , (5,2,10,1)
(2,10,4,5) , (2,10,5,4) , (10,2,4,5) , (10,2,5,4)
(4,5,2,10) , (4,5,10,2) , (5,4,2,10) , (5,4,10,2)

Constraints:
1 <= nums.length <= 1000
1 <= nums[i] <= 104
All elements in nums are distinct.
 */