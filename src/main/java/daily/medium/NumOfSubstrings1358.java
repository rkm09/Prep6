package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class NumOfSubstrings1358 {
    public static void main(String[] args) {
        String s = "abcabc";
        System.out.println(numberOfSubstrings(s));
    }

//    sliding window with frequency counter; time: O(n), space: O(1)
    public static int numberOfSubstrings(String s) {
        int n = s.length(), numOfSubstrings = 0;
        int left = 0, right = 0;
        int[] freq = new int[3];
        while(right < n) {
            char newCharacter = s.charAt(right);
            freq[newCharacter - 'a']++;
            while(hasAllCharacters(freq)) {
                numOfSubstrings += n - right;
                char startCharacter = s.charAt(left);
                freq[startCharacter - 'a']--;
                left++;
            }
            right++;
        }
        return numOfSubstrings;
    }

    private static boolean hasAllCharacters(int[] freq) {
        return freq[0] > 0 && freq[1] > 0 && freq[2] > 0;
    }

//   def; sliding window; time: O(n), space: O(n)
    public static int numberOfSubstrings1(String s) {
        int n = s.length(), numOfSubstrings = 0;
        int start = 0, end = 0;
        Map<Character, Integer> abcMap = new HashMap<>();
        while(end < n) {
            char newCharacter = s.charAt(end);
            abcMap.put(newCharacter, abcMap.getOrDefault(newCharacter, 0) + 1);
            while(abcMap.size() >= 3) {
                numOfSubstrings += n - end;
                char startCharacter = s.charAt(start);
                abcMap.put(startCharacter, abcMap.get(startCharacter) - 1);
                if(abcMap.get(startCharacter) == 0)
                    abcMap.remove(startCharacter);
                start++;
            }
            end++;
        }
        return numOfSubstrings;
    }
}

/*
Given a string s consisting only of characters a, b and c.
Return the number of substrings containing at least one occurrence of all these characters a, b and c.
Example 1:
Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
Example 2:
Input: s = "aaacb"
Output: 3
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
Example 3:
Input: s = "abc"
Output: 1

Constraints:
3 <= s.length <= 5 x 10^4
s only consists of a, b or c characters.
 */