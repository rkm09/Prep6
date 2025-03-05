package daily.medium;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MinSwaps1150 {
    public static void main(String[] args) {
        int[] data = {1,0,1,0,1};
        System.out.println(minSwaps(data));
    }


//    sliding window with two pointer; time: O(n), space: O(1) [fastest]
    public static int minSwaps(int[] data) {
        int ones = Arrays.stream(data).sum();
        int maxOnes = 0, cntOnes = 0;
        int left = 0, right = 0, n = data.length;
        while(right < n) {
//            update the number of ones by adding the new element
            cntOnes += data[right++];
//            maintain the length of window to 'ones'
            if(right - left > ones) {
//                update by removing the oldest element
                cntOnes -= data[left++];
            }
//            record the maximum number of ones in the window
            maxOnes = Math.max(maxOnes, cntOnes);
        }
        return ones - maxOnes;
    }

//    deque, sliding window; time: O(n), space: O(n)
    public static int minSwaps1(int[] data) {
        int ones = Arrays.stream(data).sum();
        int maxOnes = 0, cntOnes = 0, n = data.length;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int num : data) {
            queue.offer(num);
            cntOnes += num;
            if (queue.size() > ones) {
                cntOnes -= queue.poll();
            }
            maxOnes = Math.max(maxOnes, cntOnes);
        }
        return ones - maxOnes;
    }
}

/*
Given a binary array data, return the minimum number of swaps required to group all 1’s present in the array together in any place in the array.
Example 1:
Input: data = [1,0,1,0,1]
Output: 1
Explanation: There are 3 ways to group all 1's together:
[1,1,1,0,0] using 1 swap.
[0,1,1,1,0] using 2 swaps.
[0,0,1,1,1] using 1 swap.
The minimum is 1.
Example 2:
Input: data = [0,0,0,1,0]
Output: 0
Explanation: Since there is only one 1 in the array, no swaps are needed.
Example 3
Input: data = [1,0,1,0,1,0,0,1,1,0,1]
Output: 3
Explanation: One possible solution that uses 3 swaps is [0,0,0,0,0,1,1,1,1,1,1].

Constraints:
1 <= data.length <= 105
data[i] is either 0 or 1.
 */

/*
The fewer 0s present in this subarray, the fewer swaps we will need to achieve the desired arrangement.
To determine the minimum number of swaps required, we should look for a subarray of length ones that already contains the maximum possible number of 1s. If we can find such a subarray, then the number of swaps needed will simply be the count of 0s within it.
 */