package daily.medium;

public class Pow50 {
    public static void main(String[] args) {
        System.out.println(myPow(2.00000, 10));
    }

//    binary exponentiation; time: O(logn), space: O(logn)
    public static double myPow(double x, int n) {
        return binaryExp(x, n);
    }

    private static double binaryExp(double x, long n) {
//        base case to stop recursion
        if(n == 0) return 1;
//        handle case where n < 0
        if(n < 0)
            return 1.0 / binaryExp(x, -1 * n);
//        perform binary exponentiation
        if(n % 2 == 1)
            return x * binaryExp(x * x, (n - 1) / 2);
        else
            return binaryExp(x * x, n / 2);
    }
}

/*
Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
Example 1:
Input: x = 2.00000, n = 10
Output: 1024.00000
Example 2:
Input: x = 2.10000, n = 3
Output: 9.26100
Example 3:
Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
Constraints:
-100.0 < x < 100.0
-231 <= n <= 231-1
n is an integer.
Either x is not zero or n > 0.
-104 <= xn <= 104
 */

/*
Binary exponentiation, also known as exponentiation by squaring, is a technique for efficiently computing the power of a number. By repeatedly squaring x and halving n, we can quickly compute x
n using a logarithmic number of multiplications.
The basic idea here is to use the fact that x^n can be expressed as:(x^2)^n/2 if n is even
x∗(x^2)^(n−1)/2 if n is odd (we separate out one x, then n−1 will become even)

eg. 2^100 -> (2 * 2)^50 -> (4 * 4)^25 -> 16 * (16)^24 -> 16 * (16 * 16)^12 -> 16 * (256 * 256)^6 -> 16 * (65536 * 65536)^3
-> 16 * 4294967296 * (4294967296)^2 -> 16 * 4294967296 * (4294967296 * 4294967296)^1 -> 1267650600228229401496703205376
 */