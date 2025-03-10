package daily.medium;

public class AlternatingGroups3208 {
    public static void main(String[] args) {
        int[] colors = {0,1,0,1,0};
        System.out.println(numberOfAlternatingGroups(colors, 3));
    }

//    extended array and sliding window; time: O(n + k), space: O(n + k)
    public  static int numberOfAlternatingGroups(int[] colors, int k) {
        int n = colors.length;
//        extend the array to handle circular sequence
        int[] extendedArray = new int[n + k - 1];
        for(int i = 0 ; i < n ; i++)
            extendedArray[i] = colors[i];
        for(int i = 0 ; i < k - 1 ; i++)
            extendedArray[n + i] = colors[i];
        int length = extendedArray.length, result = 0;
//        initialize the bounds of the sliding window
        int left = 0, right = 1;
        while(right < length) {
//            check if the current color is the same as the last one
            if(extendedArray[right] == extendedArray[right - 1]) {
//                pattern breaks; reset window from the current position
                left = right;
                right++;
                continue;
            }
//            expand the window to the right
            right++;
//            skip counting the sequence if its length is less than k
            if(right - left < k) continue;
//            record a valid sequence and shrink the window from left to search for more
            result++;
            left++;
        }
        return result;
    }

//  one pass; time: O(n + k), space: O(1)
    public  static int numberOfAlternatingGroups1(int[] colors, int k) {
        int n = colors.length, lastColor = colors[0];
        int result = 0, alternatingCount = 1;
//        loop through array using circular traversal
        for(int i = 1 ; i < n + k - 1 ; i++) {
//            wrap around using modulo
            int index = i % n;
//            check if current color is the same as last color
            if(colors[index] == lastColor) {
                alternatingCount = 1;
                lastColor = colors[index];
                continue;
            }
//            extend alternating sequence
            alternatingCount++;
//            if sequence length reaches at least k, count it
            if(alternatingCount >= k)
                result++;
            lastColor = colors[index];
        }
        return result;
    }
}

/*
There is a circle of red and blue tiles. You are given an array of integers colors and an integer k. The color of tile i is represented by colors[i]:
colors[i] == 0 means that tile i is red.
colors[i] == 1 means that tile i is blue.
An alternating group is every k contiguous tiles in the circle with alternating colors (each tile in the group except the first and last one has a different color from its left and right tiles).
Return the number of alternating groups.
Note that since colors represents a circle, the first and the last tiles are considered to be next to each other.
Example 1:
Input: colors = [0,1,0,1,0], k = 3
Output: 3
Explanation:
Alternating groups:
Example 2:
Input: colors = [0,1,0,0,1,0,1], k = 6
Output: 2
Explanation:
Alternating groups:
Example 3:
Input: colors = [1,1,0,1], k = 4
Output: 0
Explanation:

Constraints:
3 <= colors.length <= 105
0 <= colors[i] <= 1
3 <= k <= colors.length
 */