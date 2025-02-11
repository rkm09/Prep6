package daily.medium;

public class RemoveOccurrences1910 {
    public static void main(String[] args) {
        String s = "daabcbaabcbc";
        String part = "abc";
        System.out.println(removeOccurrences(s,part));
    }

//    using inbuilt; time: O(n^2/m), space: O(n)
    public  static String removeOccurrences(String s, String part) {
        while(s.contains(part)) {
            int partIndex = s.indexOf(part);
            s = s.substring(0, partIndex) + s.substring(partIndex + part.length());
        }
        return s;
    }
}

/*
Given two strings s and part, perform the following operation on s until all occurrences of the substring part are removed:
Find the leftmost occurrence of the substring part and remove it from s.
Return s after removing all occurrences of part.
A substring is a contiguous sequence of characters in a string.
Example 1:
Input: s = "daabcbaabcbc", part = "abc"
Output: "dab"
Explanation: The following operations are done:
- s = "daabcbaabcbc", remove "abc" starting at index 2, so s = "dabaabcbc".
- s = "dabaabcbc", remove "abc" starting at index 4, so s = "dababc".
- s = "dababc", remove "abc" starting at index 3, so s = "dab".
Now s has no occurrences of "abc".
Example 2:
Input: s = "axxxxyyyyb", part = "xy"
Output: "ab"
Explanation: The following operations are done:
- s = "axxxxyyyyb", remove "xy" starting at index 4 so s = "axxxyyyb".
- s = "axxxyyyb", remove "xy" starting at index 3 so s = "axxyyb".
- s = "axxyyb", remove "xy" starting at index 2 so s = "axyb".
- s = "axyb", remove "xy" starting at index 1 so s = "ab".
Now s has no occurrences of "xy".
Constraints:
1 <= s.length <= 1000
1 <= part.length <= 1000
s and part consists of lowercase English letters.
 */

/*
Time: (inbuilt)
The algorithm uses a while loop to repeatedly remove the leftmost occurrence of part from s. Each iteration of the loop involves finding the index of part, which takes O(n) time, and then creating a new string by concatenating the segments before and after part, which also takes O(n) time. In the worst case, there are O(n/m) such iterations (e.g., when part is non-overlapping and removed sequentially). The total time across all iterations is O(n⋅(n/m))=O(n^2/m).

 */
