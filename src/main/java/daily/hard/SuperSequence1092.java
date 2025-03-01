package daily.hard;

import java.util.HashMap;
import java.util.Map;

public class SuperSequence1092 {
    public static void main(String[] args) {
        String str1 = "abac";
        String str2 = "cab";
        String str11 = "bbabacaa", str22 = "cccababab"; // res: bbcccabacabab
        System.out.println(shortestCommonSupersequence(str1, str2));
    }

//    dp; time: O(n.m), space: O(n.m)
    public static String shortestCommonSupersequence(String str1, String str2) {
        int str1Length = str1.length(), str2Length = str2.length();
        int[][] dp = new int[str1Length + 1][str2Length + 1];
//        initialize the base cases
//        when str2 is empty, the super sequence is str1 itself (length = rowIndex)
        for(int row = 0 ; row <= str1Length ; row++)
            dp[row][0] = row;
//        when str1 is empty, the super sequence is str2 itself (length = colIndex)
        for(int col = 0 ; col <= str2Length ; col++)
            dp[0][col] = col;
//        fill the dp table
        for(int row = 1 ; row <= str1Length ; row++) {
            for(int col = 1 ; col <= str2Length ; col++) {
//                when characters match, inherit the length from diagonal + 1
                if(str1.charAt(row - 1) == str2.charAt(col - 1))
                    dp[row][col] = dp[row - 1][col - 1] + 1;
                else  // if the characters don't match, take the minimum length + 1
                    dp[row][col] = Math.min(dp[row - 1][col], dp[row][col - 1]) + 1;
            }
        }
        StringBuilder superSequence = new StringBuilder();
        int row = str1Length, col = str2Length;
        while(row > 0 && col > 0) {
//            if characters match, take it from diagonal
            if(str1.charAt(row - 1) == str2.charAt(col - 1)) {
                superSequence.append(str1.charAt(row - 1));
                row--;
                col--;
            } else if(dp[row - 1][col] < dp[row][col - 1]) {
//                if str1's character is part of the super sequence, move up
                superSequence.append(str1.charAt(row - 1));
                row--;
            } else {
//                if str2's character is part of the super sequence, move left
                superSequence.append(str2.charAt(col - 1));
                col--;
            }
        }
//        append any remaining characters
//        if there are leftover characters in str1
        while(row > 0) {
            superSequence.append(str1.charAt(row - 1));
            row--;
        }
//        if there are leftover characters in str2
        while(col > 0) {
            superSequence.append(str2.charAt(col - 1));
            col--;
        }

//        reverse the built sequence to get the correct order
        return superSequence.reverse().toString();
    }


//    Backtracking; TLE!! ; time: O(2^(m+n).(m+n)), space: O((m+n)^2)
    public static String shortestCommonSupersequence1(String str1, String str2) {
//        base case: both strings are empty
        if(str1.isEmpty() && str2.isEmpty())
            return "";
//        base case: one string is empty, append the other one
        if(str1.isEmpty())
            return str2;
        if(str2.isEmpty())
            return str1;
//        if the first characters match, include it in the super sequence
        if(str1.charAt(0) == str2.charAt(0)) {
            return str1.charAt(0) + shortestCommonSupersequence(str1.substring(1), str2.substring(1));
        } else {
//            try both options and pick the smaller one
            String pickS1 = str1.charAt(0) + shortestCommonSupersequence(str1.substring(1), str2);
            String pickS2 = str2.charAt(0) + shortestCommonSupersequence(str1, str2.substring(1));
            return (pickS1.length() < pickS2.length()) ? pickS1 : pickS2;
        }
    }

//    Memoization; MLE!! ; time: O(m.n.(m+n)), space: O(m.n.(m+n))
    public static String shortestCommonSupersequence2(String str1, String str2) {
        Map<String, String> memo = new HashMap<>();
        return helper(str1, str2, memo);
    }

    private static String helper(String str1, String str2, Map<String, String> memo) {
//        note the space in the memo key (will give wrong result for case 2)
        String memoKey = str1 + " " + str2;
//        check if the result is already precomputed
        if(memo.containsKey(memoKey)) return memo.get(memoKey);
        if(str1.isEmpty() && str2.isEmpty()) return "";
        if(str1.isEmpty()) return str2;
        if(str2.isEmpty()) return str1;
        if(str1.charAt(0) == str2.charAt(0)) {
            String result = str1.charAt(0) + helper(str1.substring(1), str2.substring(1), memo);
            memo.put(memoKey, result);
            return result;
        }
        String pickS1 = str1.charAt(0) + helper(str1.substring(1), str2, memo);
        String pickS2 = str2.charAt(0) + helper(str1, str2.substring(1), memo);
        String result = (pickS1.length() < pickS2.length()) ? pickS1 : pickS2;
        memo.put(memoKey, result);
        return result;
    }
}


/*
Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences. If there are multiple valid strings, return any of them.
A string s is a subsequence of string t if deleting some number of characters from t (possibly 0) results in the string s.
Example 1:
Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation:
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.
Example 2:
Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
Output: "aaaaaaaa"

Constraints:
1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.
 */

/*
DP(most optimal):
We can further optimize this problem by defining dp[row][col] as the length of the shortest common supersequence (SCS) for the first row characters of str1 and the first col characters of str2 and not the entire sequence like in the previous approach. To build this table, we begin by handling base cases: if one string is empty, the only way to form the supersequence is to take all characters from the other string. This means that dp[row][0] = row and dp[0][col] = col, since the SCS of any string with an empty string is just the string itself.
Next, we iterate through both strings and update dp[row][col], based on whether the current characters of str1 and str2 match. We have two branches:
Matching Characters:
If str1[row - 1] == str2[col - 1], then this character is part of the SCS, so we extend the solution from dp[row - 1][col - 1] by 1: dp[row][col] = dp[row - 1][col - 1] + 1
Different Characters:
If str1[row - 1] != str2[col - 1], we must include one of the characters. We choose the option that results in the shorter supersequence: dp[row][col] = min(dp[row - 1][col], dp[row][col - 1]) + 1
Here, dp[row - 1][col] represents including a character from str1 and dp[row][col - 1] represents including a character from str2.
Once the dp table is filled, we backtrack from dp[m][n] to reconstruct the SCS. The idea is to start at the last cell (m, n) and trace back how we reached that value. If characters match, they are added to the result, and both pointers move diagonally. If they differ, we move in the direction that resulted in the smaller value, ensuring that we include necessary characters while keeping the sequence as short as possible. Finally, any remaining characters from str1 or str2 are appended to complete the supersequence. Since we build the sequence in reverse, we finally reverse it to obtain the correct order.
Let n be the size of str1 and m be the size of str2.
Time complexity: O(n⋅m)
The main time complexity comes from constructing the DP table which requires iterating through each cell, taking O(n⋅m) time. After building the table, we perform backtracking to construct the supersequence which takes O(n+m) time since we move either up, left, or diagonally starting from the bottom-right corner. The append operations take amortized O(1) time, while reversing the supersequence string takes O(n+m) time. Since DP table construction dominates other operations, the overall time complexity remains O(n⋅m).
Space complexity: O(n⋅m)
The primary space usage comes from the DP table which requires a 2D array of size (n+1)⋅(m+1), taking O(n⋅m) space. Additionally, we use a string to store the final supersequence which takes O(n+m) space. Other variables like row and col use constant space. The DP table dominates the space requirements, making the overall space complexity O(n⋅m).

Backtracking: Time Limit Exceeded
The most direct way to solve this problem is to try all possible ways to form the shortest common supersequence by exploring different combinations of characters from the two given strings. At each step, we add one character to the supersequence until we reach the end of both strings.
Time:
Let n be the size of str1 and m be the size of str2.
Time complexity: O(2^(n+m)⋅(n+m))
The time complexity of this approach is exponential due to the recursive nature of the function getSuperseq. For each pair of characters in str1 and str2, the function may branch into two recursive calls when the characters do not match. This results in a binary tree of recursive calls, where the height of the tree is at most n+m (the total number of characters in both strings). Since each level of the tree doubles the number of calls, the total number of recursive calls is proportional to 2 n+m.
Additionally, the substring operation, which advances the strings by 1 character, has a time complexity of O(n) or O(m) depending on the string being processed. Since this operation occurs in every recursive call, the total cost includes an additional O(n+m) factor. Thus the total time complexity of the algorithm is O(2 (n+m)⋅(n+m)).
Space complexity: O((n+m)^2)
The space complexity is determined by the depth of the recursion stack. In the worst case, the recursion depth can reach n+m because the function may need to process all characters of both strings before reaching the base case. Each recursive call consumes additional space on the call stack, leading to a stack space complexity of O(n+m).
However, the substring operation creates new copies of suffixes at each recursive call. This leads to the creation of substrings of decreasing lengths, contributing to an additional O((n+m) 2) space complexity due to repeated string allocations.

Memoization: Memory Limit Exceeded
The issue with the backtracking approach is that it repeatedly computes results for the same sub problems. To optimize this, we use memoization, a technique that stores previously computed results and reuses them when needed. Instead of recalculating the shortest supersequence for the same inputs multiple times, we store results in a hash map, where the key is a combination of the remaining portions of str1 and str2. If we encounter the same state again, we can retrieve the stored result instantly, avoiding redundant calculations.
Time complexity: O(n⋅m⋅(n+m))
In this memoized recursive approach, we have O(n⋅m) unique subproblems, as each subproblem is defined by a unique combination of remaining suffixes of s1 and s2. For each subproblem, we perform string operations, including concatenation (+) and substring, which take O(n+m) time in the worst case, as the strings can grow up to length n+m.
The memoKey creation using string concatenation also takes O(n+m) time. Hash map operations (put and get) take amortized O(1) time.
Therefore, the total time complexity is O(n⋅m⋅(n+m)) considering all subproblems and string operations within each subproblem.
Space complexity: O(n⋅m⋅(n+m))
The memoization Hash map stores results for O(n⋅m) subproblems. Each stored result can be a string of length up to O(n+m) in the worst case.
Additionally, the recursion stack can grow up to O(n) or O(m) in the worst case when we keep taking characters from one string while keeping the other string intact. The memoKey strings also consume space but are bounded by the same complexity.
Therefore, the total space complexity is O(n⋅m⋅(n+m)), dominated by the memoized results storage.

 */