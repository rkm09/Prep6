package daily.medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class ClearStar3170 {
    public static void main(String[] args) {
        String s = "aaba*";
        System.out.println(clearStars(s));

    }

//    greedy + stack; time: O(n), space: O(n)
    public static String clearStars(String s) {
        char[] arr = s.toCharArray();
        Deque<Integer>[] cnt = new ArrayDeque[26];
        for(int i = 0 ; i < 26 ; i++)
            cnt[i] = new ArrayDeque<>();
        for(int i = 0 ; i < arr.length ; i++) {
            if(arr[i] != '*') {
                cnt[arr[i] - 'a'].push(i);
            } else {
                for(int j = 0 ; j < 26 ; j++) {
                    if(!cnt[j].isEmpty()) {
                        arr[cnt[j].pop()] = '*';
                        break;
                    }
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for(char c : arr) {
            if (c != '*')
                res.append(c);
        }
        return res.toString();
    }
}

/*
You are given a string s. It may contain any number of '*' characters. Your task is to remove all '*' characters.
While there is a '*', do the following operation:
Delete the leftmost '*' and the smallest non-'*' character to its left. If there are several smallest characters, you can delete any of them.
Return the lexicographically smallest resulting string after removing all '*' characters.
Example 1:
Input: s = "aaba*"
Output: "aab"
Explanation:
We should delete one of the 'a' characters with '*'. If we choose s[3], s becomes the lexicographically smallest.
Example 2:
Input: s = "abc"
Output: "abc"
Explanation:
There is no '*' in the string.

Constraints:
1 <= s.length <= 105
s consists only of lowercase English letters and '*'.
The input is generated such that it is possible to delete all '*' characters.
 */


/*
According to the problem statement, whenever a * is encountered, we must remove the smallest character (in lexicographical order) to its left. To ensure that the resulting string is as lexicographically small as possible, and following the greedy principle, it's better to remove characters from the end rather than the beginning. This helps keep the smaller characters closer to the front, which contributes to minimizing the overall lexicographical order of the string.
We traverse the string s from left to right. Since the string contains only lowercase letters, we use 26 stacks to store the indices of each character we've seen so far. The k-th stack stores the indices of the k-th lowercase letter (a corresponds to 0, b to 1, and so on).
When we encounter a *, we find the non-empty stack with the smallest lexicographical character, mark the corresponding character in the string s as *, and remove the index from the top of that stack.
When we encounter a non-* character, we push its index into the corresponding stack.
The final answer is formed by selecting all characters from left to right in the string s that are not *.
 */