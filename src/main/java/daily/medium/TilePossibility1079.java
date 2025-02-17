package daily.medium;

import java.util.HashSet;
import java.util.Set;

public class TilePossibility1079 {
    public static void main(String[] args) {
        String tiles = "AAB";
        System.out.println(numTilePossibilities(tiles));
    }

//    backtracking; time: O(n.n!), space: O(n.n!)
    public static int numTilePossibilities(String tiles) {
//        use set to avoid adding valid duplicate string twice that are caused when the source itself has duplicates example ab & ab formed from either using first or second 'a'
        Set<String> sequences = new HashSet<>();
        boolean[] used = new boolean[tiles.length()];
//        generate all possible strings including the empty string
        generateSequences(sequences, used, "", tiles);
//        subtract 1 to exclude empty string from the final count
        return sequences.size() - 1;
    }

    private static void generateSequences(Set<String> sequences, boolean[] used, String currentStr, String tiles) {
//        add current sequence to the set
        sequences.add(currentStr);
//        try adding each unused character to current string
        for(int pos = 0 ; pos < tiles.length() ; pos++) {
            if(!used[pos]) {
                used[pos] = true;
                generateSequences(sequences, used, currentStr + tiles.charAt(pos), tiles);
                used[pos] = false;
            }
        }
    }
}

/*
You have n  tiles, where each tile has one letter tiles[i] printed on it.
Return the number of possible non-empty sequences of letters you can make using the letters printed on those tiles.
Example 1:
Input: tiles = "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
Example 2:
Input: tiles = "AAABBC"
Output: 188
Example 3:
Input: tiles = "V"
Output: 1
Constraints:
1 <= tiles.length <= 7
tiles consists of uppercase English letters.
 */

/*
backtracking:
Time complexity: O(n⋅n!)
The time complexity is determined by two main factors. First, for each position, we have at most n choices (in the first level of recursion). At each subsequent level, we have one less choice as characters get used. This creates a pattern similar to n⋅(n−1)⋅(n−2)⋅...⋅1, which is n!. Additionally, at each step, we perform string concatenation which takes O(n) time. Therefore, the total time complexity is O(n⋅n!).
Space complexity: O(n⋅n!)
The space complexity has multiple components. First, the recursion stack can go up to depth n, using O(n) space. The set sequences will store all possible unique sequences. For a string of length n, we can have sequences of length 1 to n, and each sequence can be made from n possible characters (with repetition allowed). This means the hash set can store up to O(n!) sequences, and each sequence can be of length O(n). Therefore, the total space complexity is O(n⋅n!).
 */