package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class CountOfSub3306 {
    public static void main(String[] args) {
        CountOfSub3306 c = new CountOfSub3306();
        String word1 = "eoeaui"; // k = 0, r - 2
        String word = "ieaouqqieaouqq"; // k = 1, r - 3
        System.out.println(c.countOfSubstrings1(word, 1));
    }

//    sliding window; time: O(n), space: O(n)
    public long countOfSubstrings(String word, int k) {
        long numValidSubstrings = 0;
        int n = word.length();
        int start = 0, end = 0;
//        keep track of vowel and consonant count
        Map<Character, Integer> vowelCount = new HashMap<>();
        int consonants = 0;
//        compute index of next consonant for all indices
        int[] nextConsonant = new int[n];
        int nextConsonantIndex = n;
        for(int i = n - 1 ; i >= 0 ; i--) {
            nextConsonant[i] = nextConsonantIndex;
            if(!isVowel(word.charAt(i)))
                nextConsonantIndex = i;
        }
//        start sliding window
        while(end < n) {
//            insert a new letter
            char newLetter = word.charAt(end);
//            update counts
            if(isVowel(newLetter)) {
                vowelCount.put(newLetter, vowelCount.getOrDefault(newLetter , 0) + 1);
            } else {
                consonants++;
            }
//            shrink window if consonant count is greater  than k
            while(consonants > k) {
                char startLetter = word.charAt(start);
                if(isVowel(startLetter)) {
                    vowelCount.put(startLetter, vowelCount.getOrDefault(startLetter , 0) - 1);
                    if(vowelCount.get(startLetter) == 0)
                        vowelCount.remove(startLetter);
                } else {
                    consonants--;
                }
                start++;
            }
//            while we have a valid window try to shrink it
            while(vowelCount.size() >= 5 && consonants == k) {
//                count the current valid substring as well as valid substrings produced by removing more vowels
                numValidSubstrings += nextConsonant[end] - end;
                char startLetter = word.charAt(start);
                if(isVowel(startLetter)) {
                    vowelCount.put(startLetter, vowelCount.getOrDefault(startLetter , 0) - 1);
                    if(vowelCount.get(startLetter) == 0)
                        vowelCount.remove(startLetter);
                } else {
                    consonants--;
                }
                start++;
            }
            end++;
        }

        return numValidSubstrings;
    }


//    sliding window (relaxed constraints); time: O(n), space: O(n)
    public long countOfSubstrings1(String word, int k) {
        return atleastK(word, k) - atleastK(word, k + 1);
    }

    private long atleastK(String word, int k) {
        int start = 0, end = 0, consonants = 0;
        int n = word.length();
        long numOfSubstrings = 0;
        Map<Character, Integer> vowelCount = new HashMap<>();
        while(end < n) {
            char newCharacter = word.charAt(end);
            if(isVowel(newCharacter))
                vowelCount.put(newCharacter, vowelCount.getOrDefault(newCharacter, 0) + 1);
            else
                consonants++;
            while(vowelCount.size() >= 5 && consonants >= k) {
                numOfSubstrings += n - end;
                char startCharacter = word.charAt(start);
                if(isVowel(startCharacter)) {
                    vowelCount.put(startCharacter, vowelCount.get(startCharacter) - 1);
                    if(vowelCount.get(startCharacter) == 0)
                        vowelCount.remove(startCharacter);
                } else
                    consonants--;
                start++;
            }
            end++;
        }
        return numOfSubstrings;
    }

    private boolean isVowel(Character c) {
        return (c == 'a') || (c == 'e') || (c == 'i') || (c == 'o') || (c == 'u');
    }
}

/*
You are given a string word and a non-negative integer k.
Return the total number of substrings of word that contain every vowel ('a', 'e', 'i', 'o', and 'u') at least once and exactly k consonants.
Example 1:
Input: word = "aeioqq", k = 1
Output: 0
Explanation:
There is no substring with every vowel.
Example 2:
Input: word = "aeiou", k = 0
Output: 1
Explanation:
The only substring with every vowel and zero consonants is word[0..4], which is "aeiou".
Example 3:
Input: word = "ieaouqqieaouqq", k = 1
Output: 3
Explanation:
The substrings with every vowel and one consonant are:
word[0..5], which is "ieaouq".
word[6..11], which is "qieaou".
word[7..12], which is "ieaouq".

Constraints:
5 <= word.length <= 2 * 105
word consists only of lowercase English letters.
0 <= k <= word.length - 5
 */

/*
In summary, when we come across a valid window, we can keep shrinking while the window is still valid. For each shrunken window, we have nextConsonant - end more valid windows.
Relaxed constraints:
In the previous approach, we adjusted our sliding window by strictly following the 2 constraints:
A valid window must contain all vowels.
A valid window must contain exactly k consonants.
The second requirement introduces more complexity to our sliding window solution, leading us to precompute a nextConsonant array to keep track of when the next consonant occurs for all indices in the string. To simplify the problem, let's relax this second constraint, so that valid substrings have at least k consonants instead.
Let’s say we find a window (substring) that contains all vowels and exactly k consonants. What happens if we keep expanding the window to the right?
Adding more characters will never remove a vowel from the window.
It may add more consonants, but since we only need at least k, the window remains valid.
This means that once we reach our first valid window (where end is the right boundary of the window), every substring that extends from this point onward is also valid. Instead of checking each one individually, we can instantly count them:
New valid substrings=word.length−end
After counting these substrings, we shrink the window from the left (start index) and repeat the process, making sure our window remains valid.
Now, the question is how we can connect this relaxed version of the problem back to the original problem. Let's denote the solution to this relaxed problem with a given word and k as atLeastK(word, k). The key observation is the number of valid substrings (with exactly k consonants) is equal to atLeastK(word, k) - atLeastK(word, k + 1).
With this problem reduction, we can simplify our sliding window approach and eliminate the need for an auxiliary data structure to keep track of occurrences of consonants.
 */