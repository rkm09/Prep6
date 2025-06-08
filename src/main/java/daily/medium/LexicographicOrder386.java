package daily.medium;

import java.util.ArrayList;
import java.util.List;

public class LexicographicOrder386 {
    public static void main(String[] args) {
        System.out.println(lexicalOrder(13));
//        1,10,11,12,13,2,3,4,5,6,7,8,9
    }

//    iterative; time: O(n), space: O(1)
    public static List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        int currNum = 1;
//        generate numbers from 1 to n
        for(int i = 0 ; i < n ; i++) {
            res.add(currNum);
//            if multiplying by 10, is within limits then do it
            if(currNum * 10 <= n) {
                currNum *= 10;
            } else {
//                adjusts the current number by moving up one digit
                while(currNum % 10 == 9 || currNum >= n)
                    currNum /= 10; // remove the last digit
                currNum += 1;  // increment the current number
            }
        }
        return res;
    }
}

/*
Given an integer n, return all the numbers in the range [1, n] sorted in lexicographical order.
You must write an algorithm that runs in O(n) time and uses O(1) extra space.
Example 1:
Input: n = 13
Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
Example 2:
Input: n = 2
Output: [1,2]
Constraints:
1 <= n <= 5 * 104
 */

/*
We can do the same thing iterative, the overall concept remains the same as DFS approach. The difference will be how we organize and implement it.
We initialize the current number as 1, which is the first number in lexicographical order, and set up a loop that runs n times because we want to generate exactly n numbers.
In each iteration, we add the current number to the result list. After that, we check if we can go deeper by multiplying the current number by 10, appending a zero to the current number, giving us the lexicographically smallest possible next number. If the result is still less than or equal to n, we update the current number to this new value and continue.
If multiplying by 10 would exceed n, we increment the current number. However, this increment can’t always happen directly. If the current number ends in 9 or goes beyond the next "root" (like moving from 19 to 2), we divide by 10 to move up a level and strip off the last digit. This way we make sure we don’t skip any numbers.
After incrementing, if the new current number ends in a zero (like 20), we continue removing zeroes, dividing by 10, until we get a valid number. This ensures we stay in lexicographical order as we move forward.
This way we mimic the way we would manually write numbers in lexicographical order. We move from one number to the next by considering when to go deeper (appending digits) and when to backtrack (moving to the next root). Unlike the recursive method, which builds numbers by diving into each tree branch, this way it keeps track of the current number and adjusts it directly, making it more space efficient to be specific no space overhead and in O(n) time.
 */