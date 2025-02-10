package daily.easy;

import java.util.ArrayDeque;
import java.util.Deque;

public class ClearDigits3174 {
    public static void main(String[] args) {
        String s = "abc";
        System.out.println(clearDigits(s));
    }

//    stack like using sb alone; time: O(n), space: O(n) [string builder space]
    public static String clearDigits(String s) {
        StringBuilder res = new StringBuilder();
        for(char c : s.toCharArray()) {
            if(Character.isDigit(c))
                res.setLength(res.length() - 1);
            else
                res.append(c);
        }
        return res.toString();
    }

//    def; stack + sb; time: O(n), space: O(n)
    public static String clearDigits1(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for(char c : s.toCharArray()) {
            if(Character.isDigit(c))
                stack.pop();
            else
                stack.push(c);
        }
        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.reverse().toString();
    }
}

/*
You are given a string s.
Your task is to remove all digits by doing this operation repeatedly:
Delete the first digit and the closest non-digit character to its left.
Return the resulting string after removing all digits.
Example 1:
Input: s = "abc"
Output: "abc"
Explanation:
There is no digit in the string.
Example 2:
Input: s = "cb34"
Output: ""
Explanation:
First, we apply the operation on s[2], and s becomes "c4".
Then we apply the operation on s[1], and s becomes "".

Constraints:
1 <= s.length <= 100
s consists only of lowercase English letters and digits.
The input is generated such that it is possible to delete all digits.
 */