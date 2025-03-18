package daily.easy;

public class CanPermute266 {
    public static void main(String[] args) {
        String s = "code";
        System.out.println(canPermutePalindrome(s));
    }

    public static boolean canPermutePalindrome(String s) {
        int[] freq = new int[26];
        for(char c : s.toCharArray())
            freq[c - 'a']++;

        int odd = 0;
        for(int i = 0 ; i < 26 ; i++) {
            if(freq[i] % 2 != 0) {
                odd++;
                if(odd > 1) return false;
            }
        }
        return true;
    }
}

/*
Given a string s, return true if a permutation of the string could form a palindrome and false otherwise.
Example 1:
Input: s = "code"
Output: false
Example 2:
Input: s = "aab"
Output: true
Example 3:
Input: s = "carerac"
Output: true
Constraints:
1 <= s.length <= 5000
s consists of only lowercase English letters.
 */