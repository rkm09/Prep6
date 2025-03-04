package daily.medium;

public class PowersOfThree1780 {
    public static void main(String[] args) {
        PowersOfThree1780 p = new PowersOfThree1780();
        System.out.println(p.checkPowersOfThree(12));
    }


//    backtracking; time: O(2^log3n) ~ O(n), space: O(log3n)
    public boolean checkPowersOfThree(int n) {
        return helper(0, n);
    }

    private boolean helper(int power, int n) {
        if(n == 0) return true;
        if(Math.pow(3, power) > n) return false;
        boolean addPower = helper(power + 1, n - (int) Math.pow(3, power));
        boolean skipPower = helper(power + 1, n);
        return addPower || skipPower;
    }
}

/*
Given an integer n, return true if it is possible to represent n as the sum of distinct powers of three. Otherwise, return false.
An integer y is a power of three if there exists an integer x such that y == 3x.
Example 1:
Input: n = 12
Output: true
Explanation: 12 = 31 + 32
Example 2:
Input: n = 91
Output: true
Explanation: 91 = 30 + 32 + 34
Example 3:
Input: n = 21
Output: false

Constraints:
1 <= n <= 107
 */

/*
backtracking:
Time complexity: O(2 log 3n) or O(n)
Since we only consider the powers of 3 that are at most equal to n, there are O(log
3n) candidate powers. For each candidate power, we explore two possibilities: including it in the sum or excluding it. This leads to a binary recursion tree, where each node corresponds to one of the choices (include or exclude) for a given power of 3. The depth of this tree is O(log 3n), and each recursive call performs a constant amount of work: checking the base cases and returning the logical OR of two boolean values.
Thus, the overall time complexity is O(2 log 3n), which simplifies to O(n) (as 2 log 3n is equivalent to n^log(3)2).
Space complexity: O(log 3n)
The space complexity is primarily dominated by the recursion stack. Since the recursion may need to explore all possible powers before returning, the depth of the recursion stack can grow up to O(log
3n). Apart from a few variables that only require constant space, no additional data structures are created. Therefore, the auxiliary space complexity of the algorithm is O(log3n).
 */