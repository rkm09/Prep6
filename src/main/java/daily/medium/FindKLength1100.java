package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class FindKLength1100 {
    public static void main(String[] args) {
        String s = "havefunonleetcode";
        System.out.println(numKLenSubstrNoRepeats(s, 5));
    }

//    sliding window; time: O(n), space: O(1)
    public static int numKLenSubstrNoRepeats(String s, int k) {
//        if k > 26, there can be no strings with unique characters
        if(k > 26) return 0;
        int n = s.length(), substrings = 0;
        int left = 0, right = 0;
        int[] freq = new int[26];
        while(right < n) {
            char newCharacter = s.charAt(right);
            freq[newCharacter - 'a']++;
//            keep contracting the window till count becomes 1, move left pointer accordingly
            while(freq[newCharacter - 'a'] > 1) {
                char startCharacter = s.charAt(left);
                freq[startCharacter - 'a']--;
                left++;
            }
//            check if the length of the current unique substring is equal to k
            if(right - left + 1 == k) {
                substrings++;
                int startCharacter = s.charAt(left);
//                contract the window and remove the leftmost, and get it ready for the next iteration
                freq[startCharacter - 'a']--;
                left++;
            }
//            expand the window
            right++;
        }
        return substrings;
    }

//    def; sliding window + map; time: O(n), space: O(n)
    public static int numKLenSubstrNoRepeats1(String s, int k) {
        int n = s.length(), substrings = 0;
        if(k > 26) return 0; // rather than k > n (a more generalized case this way)
        int start = 0, end = 0;
        Map<Character, Integer> letterMap = new HashMap<>();
        while(end < n) {
            char newLetter = s.charAt(end);
            while(letterMap.containsKey(newLetter)) {
                char startLetter = s.charAt(start);
                letterMap.remove(startLetter);
                start++;
            }
            letterMap.put(newLetter, 1);
            if(letterMap.size() == k) {
                substrings++;
                char startLetter = s.charAt(start);
                letterMap.remove(startLetter);
                start++;
            }
            end++;
        }
        return substrings;
    }
}

/*
Given a string s and an integer k, return the number of substrings in s of length k with no repeated characters.
Example 1:
Input: s = "havefunonleetcode", k = 5
Output: 6
Explanation: There are 6 substrings they are: 'havef','avefu','vefun','efuno','etcod','tcode'.
Example 2:
Input: s = "home", k = 5
Output: 0
Explanation: Notice k can be larger than the length of s. In this case, it is not possible to find any substring.

Constraints:
1 <= s.length <= 104
s consists of lowercase English letters.
1 <= k <= 104
 */