package daily.easy;

public class NumEquiv1128 {
    public static void main(String[] args) {
        int[][] dominoes = {{1,2},{2,1},{3,4},{5,6}};
        System.out.println(numEquivDominoPairs(dominoes));
    }

//    tuple representation + counting; time: O(n), space: O(1)
    public static int numEquivDominoPairs(int[][] dominoes) {
        int[] num = new int[100];
        int count = 0;
        for(int[] domino : dominoes) {
            int val = domino[0] < domino[1] ? 10 * domino[0] + domino[1] :
                    domino[1] * 10 + domino[0];
            count += num[val];
            num[val]++;
        }
        return count;
    }

    
//    TLE; brute force
    public static int numEquivDominoPairsX(int[][] dominoes) {
        int count = 0, n = dominoes.length;
        for(int i = 0 ; i < n ; i++) {
            for(int j = i + 1 ; j < n ; j++) {
                if((dominoes[i][0] == dominoes[j][0] && dominoes[i][1] == dominoes[j][1]) ||
                        (dominoes[i][0] == dominoes[j][1] && dominoes[i][1] == dominoes[j][0]))
                    count++;
            }
        }
        return count;
    }
}

/*
Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a == c and b == d), or (a == d and b == c) - that is, one domino can be rotated to be equal to another domino.
Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j].
Example 1:
Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
Output: 1
Example 2:
Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
Output: 3
Constraints:
1 <= dominoes.length <= 4 * 104
dominoes[i].length == 2
1 <= dominoes[i][j] <= 9
 */

/*
So we might as well directly convert each binary pair into the specified format, that is, the first dimension must not be greater than the second dimension. Two pairs are equivalent if they contain the same two numbers, regardless of order.
Noticing that the elements in the pairs are all not greater than 9, we can concatenate each binary pair into a two-digit positive integer, i.e., (x,y)→10x+y. In this way, there is no need to use a hash table to count the number of elements, but we can directly use an array of length 100.
 */