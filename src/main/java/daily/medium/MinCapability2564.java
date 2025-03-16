package daily.medium;

import java.util.Arrays;

public class MinCapability2564 {
    public static void main(String[] args) {
        int[] nums = {2,3,5,9};
        System.out.println(minCapability(nums, 2));
    }

//    binary search; time: O(nlogm), space: O(1)  [m - size of range of elements in nums 1 to m]
    public static int minCapability(int[] nums, int k) {
        int minReward = 1, n = nums.length;
//        store the maximum nums value in maxReward
        int maxReward = Arrays.stream(nums).max().getAsInt();
//        use binary search to find the minimum reward possible
        while(minReward < maxReward) {
            int midReward = minReward + (maxReward - minReward) / 2;
            int possibleThefts = 0;
            for(int index = 0 ; index < n ; index++) {
                if(nums[index] <= midReward) {
                    possibleThefts++;
//                    skip the next position in case of a match to maintain non adjacent condition
                    index++;
                }
            }
            if(possibleThefts >= k) maxReward = midReward;
            else minReward = midReward + 1;
        }
        return minReward;
    }
}

/*
There are several consecutive houses along a street, each of which has some money inside. There is also a robber, who wants to steal money from the homes, but he refuses to steal from adjacent homes.
The capability of the robber is the maximum amount of money he steals from one house of all the houses he robbed.
You are given an integer array nums representing how much money is stashed in each house. More formally, the ith house from the left has nums[i] dollars.
You are also given an integer k, representing the minimum number of houses the robber will steal from. It is always possible to steal at least k houses.
Return the minimum capability of the robber out of all the possible ways to steal at least k houses.
Example 1:
Input: nums = [2,3,5,9], k = 2
Output: 5
Explanation:
There are three ways to rob at least 2 houses:
- Rob the houses at indices 0 and 2. Capability is max(nums[0], nums[2]) = 5.
- Rob the houses at indices 0 and 3. Capability is max(nums[0], nums[3]) = 9.
- Rob the houses at indices 1 and 3. Capability is max(nums[1], nums[3]) = 9.
Therefore, we return min(5, 9, 9) = 5.
Example 2:
Input: nums = [2,7,9,3,1], k = 2
Output: 2
Explanation: There are 7 ways to rob the houses. The way which leads to minimum capability is to rob the house at index 0 and 4. Return max(nums[0], nums[4]) = 2.

Constraints:
1 <= nums.length <= 105
1 <= nums[i] <= 109
1 <= k <= (nums.length + 1)/2
 */

/*
Using dynamic programming, we can store these values and efficiently compute the maximum amount the robber can steal.
In this current problem, the robber still has to follow the restraint that they cannot steal from two consecutive houses. However, this time, instead of maximizing the total reward, they want to maximize the minimum amount stolen amongst all houses.
Similar to the original problem, we can think of a recursive relation to solve this. Again, we have two choices:
Rob the current house (but then we must skip the next house).
Skip the current house and move forward.
However, unlike the original problem, we need an additional condition—ensuring that we rob at least k houses. The dynamic programming solution involves a state dp[houseIndex][numberOfHousesRobbed]. Since we iterate over n houses and track up to k robbed houses, the problem becomes more complex, and solving it with dynamic programming takes O(n⋅k) time.
Problems that require maximizing the minimum or minimizing the maximum often suggest a binary search approach. Instead of searching through indices or subsets directly, we can binary search on the reward value itself. By determining whether a given minimum reward is achievable, we can efficiently narrow down the possible solutions.
A more efficient way to approach this problem is to recognize that we are trying to push the minimum stolen amount as high as possible while still satisfying the condition of robbing at least k houses. This naturally leads to using binary search on the minimum reward that the robber can secure.
 */