package daily.hard;

public class SuperSequence1092 {
    public static void main(String[] args) {
        String str1 = "abac";
        String str2 = "cab";
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