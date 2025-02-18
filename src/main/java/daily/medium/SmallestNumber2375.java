package daily.medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class SmallestNumber2375 {
    public static void main(String[] args) {
        String pattern = "IIIDIDDD";
        System.out.println(smallestNumber(pattern));
    }

//    using stack; time: O(n), space: O(n)
    public static String smallestNumber(String pattern) {
        StringBuilder result = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();
        for(int index = 0 ; index <= pattern.length() ; index++) {
            stack.push(index + 1);
            if(index == pattern.length() || pattern.charAt(index) == 'I') {
                while(!stack.isEmpty())
                    result.append(stack.pop());
            }
        }
        return result.toString();
    }
}

/*
You are given a 0-indexed string pattern of length n consisting of the characters 'I' meaning increasing and 'D' meaning decreasing.
A 0-indexed string num of length n + 1 is created using the following conditions:
num consists of the digits '1' to '9', where each digit is used at most once.
If pattern[i] == 'I', then num[i] < num[i + 1].
If pattern[i] == 'D', then num[i] > num[i + 1].
Return the lexicographically smallest possible string num that meets the conditions.
Example 1:
Input: pattern = "IIIDIDDD"
Output: "123549876"
Explanation:
At indices 0, 1, 2, and 4 we must have that num[i] < num[i+1].
At indices 3, 5, 6, and 7 we must have that num[i] > num[i+1].
Some possible values of num are "245639871", "135749862", and "123849765".
It can be proven that "123549876" is the smallest possible num that meets the conditions.
Note that "123414321" is not possible because the digit '1' is used more than once.
Example 2:
Input: pattern = "DDD"
Output: "4321"
Explanation:
Some possible values of num are "9876", "7321", and "8742".
It can be proven that "4321" is the smallest possible num that meets the conditions.
Constraints:
1 <= pattern.length <= 8
pattern consists of only the letters 'I' and 'D'.
 */

/*
If the pattern consists only of 'I' characters, the solution is simple. For example, with the pattern "III", the answer would be "1234" — we just place the smallest available number at each step in sequential order. This is because each 'I' ensures that the next number must be greater than the previous one, so we can directly append numbers in increasing order.
However, when we introduce 'D' into the pattern, we must be more careful. A 'D' means that the current number must be larger than the next one, and we can’t just keep adding numbers sequentially as we did for 'I'. The challenge is that when we see a 'D', we don't immediately know how many consecutive 'D' characters will follow, which affects how we assign numbers.
To resolve this, when we encounter a 'D', instead of placing a number at that position immediately, we delay the decision. We keep processing the pattern recursively until we reach an 'I' or the end of the pattern. Once we’ve fully processed all future indices, we "unwind" the recursion and start placing numbers in reverse order. This ensures that the numbers corresponding to the 'D' positions are placed in descending order, maintaining the correct decreasing relationship.
 */