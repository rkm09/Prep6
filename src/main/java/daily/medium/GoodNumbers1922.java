package daily.medium;

public class GoodNumbers1922 {
    private static final long MOD = 1_000_000_007;
    public static void main(String[] args) {
        System.out.println(countGoodNumbers(4));
    }

//    fast exponentiation; time: O(logn), space: O(logn)
    public static int countGoodNumbers(long n) {
        return (int) ((fastExponent(5, (n + 1) / 2) * fastExponent(4, n / 2)) % MOD);
    }

//    use fast exponentiation to calculate x^y % mod
    private static long fastExponent(int x, long y) {
        long res = 1;
        long mul = x;
        while(y > 0) {
            if(y % 2 == 1)
                res = (res * mul) % MOD;
            mul = (mul * mul) % MOD;
            y /= 2;
        }
        return res;
    }
}

/*
A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2, 3, 5, or 7).
For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd positions are prime. However, "3245" is not good because 3 is at an even index but is not even.
Given an integer n, return the total number of good digit strings of length n. Since the answer may be large, return it modulo 109 + 7.
A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.
Example 1:
Input: n = 1
Output: 5
Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
Example 2:
Input: n = 4
Output: 400
Example 3:
Input: n = 50
Output: 564908303

Constraints:
1 <= n <= 1015
 */

/*
For the numbers at even indices, they can be 0,2,4,6,8, a total of 5 types. A digit string of length n has (n + 1) / 2 [floor] even indices, where ⌊x⌋ denotes the floor function of x.
For the numbers at odd indices, they can be 2,3,5,7, a total of 4 types. A digit string of length n has (n) / 2 [floor] odd indices
Therefore, the total number of good numbers in a digit string of length n is: 5 ^ (n + 1 / 2) * 4 ^ (n / 2)
 */