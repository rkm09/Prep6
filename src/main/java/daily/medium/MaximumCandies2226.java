package daily.medium;


public class MaximumCandies2226 {
    public static void main(String[] args) {
        int[] candies = {5,8,6};
        System.out.println(maximumCandies(candies, 3));
    }


//    binary search; time: O(mlogn), space: O(n) [m - greatest number of candies]
    public static int maximumCandies(int[] candies, long k) {
        int maxCandies = 0;
//        find max possible candies in any bag
        for(int bag : candies)
            maxCandies = Math.max(bag, maxCandies);
        int left = 0, right = maxCandies;
//        binary search on possible allocation range
        while(left < right) {
            int mid = (left + right + 1) / 2;
            if(canAllocateCandies(candies, k, mid))
                left = mid;
            else
                right = mid - 1;
        }
        return left;
    }

    private static boolean canAllocateCandies(int[] candies, long k, int numOfCandies) {
        long maxNumOfChildren = 0;
//        max number of children that each bag can serve
        for(int candyBag : candies) {
            maxNumOfChildren += candyBag / numOfCandies;
        }
        return maxNumOfChildren >= k;
    }
}

/*
You are given a 0-indexed integer array candies. Each element in the array denotes a pile of candies of size candies[i]. You can divide each pile into any number of sub piles, but you cannot merge two piles together.
You are also given an integer k. You should allocate piles of candies to k children such that each child gets the same number of candies. Each child can be allocated candies from only one pile of candies and some piles of candies may go unused.
Return the maximum number of candies each child can get.
Example 1:
Input: candies = [5,8,6], k = 3
Output: 5
Explanation: We can divide candies[1] into 2 piles of size 5 and 3, and candies[2] into 2 piles of size 5 and 1. We now have five piles of candies of sizes 5, 5, 3, 5, and 1. We can allocate the 3 piles of size 5 to 3 children. It can be proven that each child cannot receive more than 5 candies.
Example 2:
Input: candies = [2,5], k = 11
Output: 0
Explanation: There are 11 children but only 7 candies in total, so it is impossible to ensure each child receives at least one candy. Thus, each child gets no candy and the answer is 0.

Constraints:
1 <= candies.length <= 105
1 <= candies[i] <= 107
1 <= k <= 1012
 */