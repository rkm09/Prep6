package daily.easy;

import java.util.ArrayList;
import java.util.List;

public class LongestSub2900 {
    public static void main(String[] args) {
        String[] words = {"e","a","b"};
        int[] groups = {0,0,1};
        System.out.println(getLongestSubsequence(words, groups));
    }

//    greedy; time: O(n), space: O(1)
    public static List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> res = new ArrayList<>();
        int n = words.length;
        for(int i = 0 ; i < n ; i++) {
            if(i == 0 || groups[i] != groups[i - 1])
                res.add(words[i]);
        }
        return res;
    }
}

/*
You are given a string array words and a binary array groups both of length n, where words[i] is associated with groups[i].
Your task is to select the longest alternating subsequence from words. A subsequence of words is alternating if for any two consecutive strings in the sequence, their corresponding elements in the binary array groups differ. Essentially, you are to choose strings such that adjacent elements have non-matching corresponding bits in the groups array.
Formally, you need to find the longest subsequence of an array of indices [0, 1, ..., n - 1] denoted as [i0, i1, ..., ik-1], such that groups[ij] != groups[ij+1] for each 0 <= j < k - 1 and then find the words corresponding to these indices.
Return the selected subsequence. If there are multiple answers, return any of them.
Note: The elements in words are distinct.
Example 1:
Input: words = ["e","a","b"], groups = [0,0,1]
Output: ["e","b"]
Explanation: A subsequence that can be selected is ["e","b"] because groups[0] != groups[2]. Another subsequence that can be selected is ["a","b"] because groups[1] != groups[2]. It can be demonstrated that the length of the longest subsequence of indices that satisfies the condition is 2.
Example 2:
Input: words = ["a","b","c","d"], groups = [1,0,1,1]
Output: ["a","b","c"]
Explanation: A subsequence that can be selected is ["a","b","c"] because groups[0] != groups[1] and groups[1] != groups[2]. Another subsequence that can be selected is ["a","b","d"] because groups[0] != groups[1] and groups[1] != groups[3]. It can be shown that the length of the longest subsequence of indices that satisfies the condition is 3.

Constraints:
1 <= n == words.length == groups.length <= 100
1 <= words[i].length <= 10
groups[i] is either 0 or 1.
words consists of distinct strings.
words[i] consists of lowercase English letters.
 */

/*
Greedy:
The task is to find the longest subsequence in groups where adjacent elements are different. Since the array groups contains only two possible values, 0 and 1, the problem simplifies to removing consecutive duplicates. In other words, we can construct the longest valid subsequence by selecting just one representative element from each group of consecutive identical values. For example, given the input:
[0,0,0,1,1,1,0,1,0,1,1,1]
we can break it into segments of consecutive identical elements:
[[0,0,0],[1,1,1],[0],[1],[0],[1,1,1]]
To ensure adjacent elements in the resulting subsequence are different, we select a single index from each segment. In order to maximize the subsequence length, we must select exactly one index from every segment of identical elements. At the same time, we append the corresponding string from words to the result.
For ease of implementation, we can simply select either the leftmost or the rightmost index from each segment. For the array above, the index groups of identical values are:
[[0,1,2],[3,4,5],[6],[7],[8],[9,10,11]]
From these, we can construct two valid sets of indices by picking either:
The leftmost index of each segment:
[0,3,6,7,8,9]
Or the rightmost index of each segment:
[2,5,6,7,8,11]
Here we choose the leftmost index from each segment and add the corresponding string from words to the final answer.
 */