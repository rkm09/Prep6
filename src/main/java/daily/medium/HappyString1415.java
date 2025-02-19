package daily.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HappyString1415 {
    public static void main(String[] args) {
        System.out.println(getHappyString(1,3));
    }

//    backtracking ; time: O(n.2^n), space: O(2^n)
    public static String getHappyString(int n, int k) {
        List<String> happyStringsList = new ArrayList<>();
//        generate all the happy strings of length n
        generateHappyStrings(n, happyStringsList, "");
//        sort in lexicographical order
        Collections.sort(happyStringsList);
//        check if there are at least k happy strings
        if(happyStringsList.size() < k) return "";
        return happyStringsList.get(k - 1);
    }

    private static void generateHappyStrings(int n, List<String> happyStringsList, String currentString) {
//        if the current string has reached the desired length, add it the list
        if(currentString.length() == n) {
            happyStringsList.add(currentString);
            return;
        }
//        try adding each character('a','b','c') to the current string
        for(char currentChar = 'a' ; currentChar <= 'c' ; currentChar++) {
//            skip if the current character is the same as the last one
            if(currentString.length() > 0 && currentString.charAt(currentString.length() - 1) == currentChar)
                continue;
//            recursively generate the next character
            generateHappyStrings(n, happyStringsList, currentString + currentChar);
        }
    }
}

/*
A happy string is a string that:
consists only of letters of the set ['a', 'b', 'c'].
s[i] != s[i + 1] for all values of i from 1 to s.length - 1 (string is 1-indexed).
For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are not happy strings.
Given two integers n and k, consider a list of all happy strings of length n sorted in lexicographical order.
Return the kth string of this list or return an empty string if there are less than k happy strings of length n.
Example 1:
Input: n = 1, k = 3
Output: "c"
Explanation: The list ["a", "b", "c"] contains all happy strings of length 1. The third string is "c".
Example 2:
Input: n = 1, k = 4
Output: ""
Explanation: There are only 3 happy strings of length 1.
Example 3:
Input: n = 3, k = 9
Output: "cab"
Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb", "cab", "cac", "cba", "cbc"]. You will find the 9th string = "cab"
Constraints:
1 <= n <= 10
1 <= k <= 100
 */

/*
backtracking:
Let n be the desired length of the happy strings.
Time Complexity:O(n⋅2^n).
In the backtracking, we explore 3 options for the first character of the string and 2 options for the next character at each of the following steps. This is similar to exploring all nodes in a binary tree withnlevels, resulting in a time complexity ofO(2
n)for generating the strings. Then, we sort3⋅2^n−1=O(2^n)strings, which requires2^nlog2^n=O(n⋅2^n)time.
Thus, the overall complexity is determined by the sorting of all happy strings and is equal toO(n⋅2^n).
Space Complexity:O(2^n).
We create an array to store all happy strings of lengthn, which will eventually hold 3⋅2^n−1=O(2^n)elements. Additionally, the recursion depth can grow up ton, adding anotherO(n)factor to the total space complexity. However, the amount of extra space used is dominated by the happyStrings array and remains equal toO(2^n).
 */