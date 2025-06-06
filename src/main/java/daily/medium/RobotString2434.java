package daily.medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class RobotString2434 {
    public static void main(String[] args) {
        String s = "bacac";
        System.out.println(robotWithString(s));
    }

//    greedy + stack; time: O(n), space: O(n)
    public static String robotWithString(String s) {
        int[] cnt = new int[26];
        for(char c : s.toCharArray())
            cnt[c - 'a']++;
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder res = new StringBuilder();
        char minChar = 'a';
        for(char c : s.toCharArray()) {
            stack.push(c);
            cnt[c - 'a']--;
            while(minChar != 'z' && cnt[minChar - 'a'] == 0)
                minChar++;
            while(!stack.isEmpty() && stack.peek() <= minChar)
                res.append(stack.pop());
        }
        return res.toString();
    }
}

/*
You are given a string s and a robot that currently holds an empty string t. Apply one of the following operations until s and t are both empty:
Remove the first character of a string s and give it to the robot. The robot will append this character to the string t.
Remove the last character of a string t and give it to the robot. The robot will write this character on paper.
Return the lexicographically smallest string that can be written on the paper.
Example 1:
Input: s = "zza"
Output: "azz"
Explanation: Let p denote the written string.
Initially p="", s="zza", t="".
Perform first operation three times p="", s="", t="zza".
Perform second operation three times p="azz", s="", t="".
Example 2:
Input: s = "bac"
Output: "abc"
Explanation: Let p denote the written string.
Perform first operation twice p="", s="c", t="ba".
Perform second operation twice p="ab", s="c", t="".
Perform first operation p="ab", s="", t="c".
Perform second operation p="abc", s="", t="".
Example 3:
Input: s = "bdda"
Output: "addb"
Explanation: Let p denote the written string.
Initially p="", s="bdda", t="".
Perform first operation four times p="", s="", t="bdda".
Perform second operation four times p="addb", s="", t="".

Constraints:
1 <= s.length <= 105
s consists of only English lowercase letters.
 */


/*
If c<minCharacter, then the top element of the stack must be popped to ensure the smallest possible pop sequence.
If c>minCharacter, then the top element should be retained, and we should continue pushing characters until we encounter minCharacter, ensuring the minimum lexicographical sequence.
If c=minCharacter, then the top element must also be popped to achieve the smallest sequence. This is because we can pop c now, and later push and pop minCharacter, resulting in two consecutive minimal characters in the output. Otherwise, if we wait and only pop minCharacter later, we’ll end up with just one occurrence, and subsequent characters will be greater than or equal to it.
Following this greedy approach, we push characters onto the stack one by one. After each push, we update minCharacter to be the smallest character remaining in the string and compare it with the stack’s top. If the condition allows, we pop from the stack; otherwise, we continue the loop. Finally, we return the resulting string.
 */