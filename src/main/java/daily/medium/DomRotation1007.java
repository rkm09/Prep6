package daily.medium;

public class DomRotation1007 {
    public static void main(String[] args) {
        int[] tops = {5,1,2,4,2,2};
        int[] bottoms = {2,2,6,2,3,2};
        System.out.println(minDominoRotations(tops, bottoms));
    }

//    greedy; time: O(n), space: O(1)
    public static int minDominoRotations(int[] tops, int[] bottoms) {
        int n = tops.length;
        int rotations = check(tops[0], tops, bottoms, n);
//        if it is possible to make all elements in top or bottom equal to tops[0]
        if(rotations != -1) return rotations;
//        if it is possible to make all elements in top or bottom equal to bottoms[0]
        else return check(bottoms[0], tops, bottoms, n);
    }

    private static int check(int x, int[] tops, int[] bottoms, int n) {
        int rotationsX = 0, rotationsY = 0;
        for(int i = 0 ; i < n ; i++) {
            if(tops[i] != x && bottoms[i] != x)
                return -1;
            else if(tops[i] == x && bottoms[i] != x)
                rotationsY++;
            else if(tops[i] != x && bottoms[i] == x)
                rotationsX++;

        }
        return Math.min(rotationsX, rotationsY);
    }
}

/*
In a row of dominoes, tops[i] and bottoms[i] represent the top and bottom halves of the ith domino. (A domino is a tile with two numbers from 1 to 6 - one on each half of the tile.)
We may rotate the ith domino, so that tops[i] and bottoms[i] swap values.
Return the minimum number of rotations so that all the values in tops are the same, or all the values in bottoms are the same.
If it cannot be done, return -1.
Example 1:
Input: tops = [2,1,2,4,2,2], bottoms = [5,2,6,2,3,2]
Output: 2
Explanation:
The first figure represents the dominoes as given by tops and bottoms: before we do any rotations.
If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.
Example 2:
Input: tops = [3,5,1,2,3], bottoms = [3,6,3,3,4]
Output: -1
Explanation:
In this case, it is not possible to rotate the dominoes to make one row of values equal.

Constraints:
2 <= tops.length <= 2 * 104
bottoms.length == tops.length
1 <= tops[i], bottoms[i] <= 6
 */
