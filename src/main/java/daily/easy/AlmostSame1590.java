package daily.easy;

import java.util.Arrays;

public class AlmostSame1590 {
    public static void main(String[] args) {
        String s1 = "bank";
        String s2 = "kanb";
        System.out.println(areAlmostEqual(s1, s2));
    }

//    only check differences; time: O(n), space: O(1) [fastest]
    public static boolean areAlmostEqual(String s1, String s2) {
        int firstDiffIndex = 0, secondDiffIndex = 0;
        int numDiff = 0;
        for(int i = 0 ; i < s1.length() ; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                numDiff++;
//                if numDiff count is more than 2, one swap will not make them equal
                if(numDiff > 2) return false;
                if(numDiff == 1) {
//                  store the index of first difference
                    firstDiffIndex = i;
                } else {
//                  store the index of second difference
                    secondDiffIndex = i;
                }
            }
        }
//        check if swap is possible
        return (s1.charAt(firstDiffIndex) == s2.charAt(secondDiffIndex)) &&
                (s1.charAt(secondDiffIndex) == s2.charAt(firstDiffIndex));
    }

//    freq map; time: O(n), space: O(1)
    public static boolean areAlmostEqual2(String s1, String s2) {
        char[] freq = new char[26];
        int numDiffs = 0;
        for(int i = 0 ; i < s1.length() ; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(c1 != c2) numDiffs++;
            if(numDiffs > 2) return false;
            freq[c1 - 'a']++;
            freq[c2 - 'a']--;
        }
        for(int i = 0 ; i < 26 ; i++) {
            if(freq[i] != 0) return false;
        }
        return true;
    }

//    two freq maps; time: O(n), space: O(1)
    public static boolean areAlmostEqual1(String s1, String s2) {
        char[] freqMap1 = new char[26];
        char[] freqMap2 = new char[26];
        int numDiffs = 0;
        for(int i = 0 ; i < s1.length() ; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(c1 != c2) numDiffs++;
            if(numDiffs > 2) return false;
            freqMap1[c1 - 'a']++;
            freqMap2[c2 - 'a']++;
        }
        return Arrays.equals(freqMap1, freqMap2);
    }
}

/*
You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices in a string (not necessarily different) and swap the characters at these indices.
Return true if it is possible to make both strings equal by performing at most one string swap on exactly one of the strings. Otherwise, return false.
Example 1:
Input: s1 = "bank", s2 = "kanb"
Output: true
Explanation: For example, swap the first character with the last character of s2 to make "bank".
Example 2:
Input: s1 = "attack", s2 = "defend"
Output: false
Explanation: It is impossible to make them equal with one string swap.
Example 3:
Input: s1 = "kelb", s2 = "kelb"
Output: true
Explanation: The two strings are already equal, so no string swap operation is required.
Constraints:
1 <= s1.length, s2.length <= 100
s1.length == s2.length
s1 and s2 consist of only lowercase English letters.
 */