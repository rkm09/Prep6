package daily.medium;

import java.util.Arrays;

public class ClosestPrimes2523 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(closestPrimes(10,19)));
    }

//    analyse distance between twin primes; time: O(min(1452, R-L), sqrt(R))
    public static int[] closestPrimes(int left, int right) {
        int prevPrime = -1, closestA = -1, closestB = -1;
        int minDifference = Integer.MAX_VALUE;
//        find all prime numbers in the given range
        for(int candidate = left ; candidate <= right ; candidate++) {
            if(isPrime(candidate)) {
                if(prevPrime != -1) {
                    int difference = candidate - prevPrime;
                    if(difference < minDifference) {
                        minDifference = difference;
                        closestA = prevPrime;
                        closestB = candidate;
                    }
//                    twin prime optimisation
                    if(difference == 2 || difference == 1)
                        return new int[] {closestA, closestB};
                }
                prevPrime = candidate;
            }
        }
        return (closestA == -1) ? new int[] {-1,-1} : new int[] {closestA, closestB};
    }

    private static boolean isPrime(int number) {
        if(number < 2) return false;
        if(number == 2 || number == 3) return true;
        if(number % 2 == 0) return false;
        for(int divisor = 3 ; divisor * divisor <= number ; divisor++) {
            if(number % divisor == 0) return false;
        }
        return true;
    }
}

/*
Given two positive integers left and right, find the two integers num1 and num2 such that:
left <= num1 < num2 <= right .
Both num1 and num2 are prime numbers.
num2 - num1 is the minimum amongst all other pairs satisfying the above conditions.
Return the positive integer array ans = [num1, num2]. If there are multiple pairs satisfying these conditions, return the one with the smallest num1 value. If no such numbers exist, return [-1, -1].
Example 1:
Input: left = 10, right = 19
Output: [11,13]
Explanation: The prime numbers between 10 and 19 are 11, 13, 17, and 19.
The closest gap between any pair is 2, which can be achieved by [11,13] or [17,19].
Since 11 is smaller than 17, we return the first pair.
Example 2:
Input: left = 4, right = 6
Output: [-1,-1]
Explanation: There exists only one prime number in the given range, so the conditions cannot be satisfied.

Constraints:
1 <= left <= right <= 106
 */

/*

In this approach, we take advantage of a special property of prime numbers known as twin primes, which are pairs of prime numbers that differ by exactly 2, such as (3,5), (11,13), and (17,19). Instead of searching through all prime numbers, we can optimize our search by focusing on this pattern.
A key mathematical observation under the given constraints (1 ≤ L,R ≤ 10^6) is that for any range [L, R] where R - L ≥ 1452, there is always at least one twin prime pair. This means that if the given range is wide enough (at least 1452 numbers long), we can be certain that a twin prime pair exists. Since no two prime numbers can be closer than a twin prime pair (which has a difference of exactly 2), we can immediately return this result without further searching.
You can use the following code snippet to verify this behavior by checking the maximum gap between consecutive prime numbers in the range [1, 10^6] (Click to expand):
However, if the range [L, R] is smaller than 1452 numbers, we cannot rely on this property and must manually find the closest prime pair. To do this, we iterate through the numbers in the range, check which ones are prime, and compute the smallest difference between consecutive primes.
Therefore, we leverage the concept of twin primes to optimize our search for the closest prime pair. Instead of storing all prime numbers and comparing them later, we track only the last encountered prime (prevPrime). As we iterate through the range [left, right], if we find a new prime, we calculate the difference between it and prevPrime. If the difference is 2, we instantly return the pair, since no closer pair can exist. This early exit significantly reduces unnecessary iterations, especially in large ranges where twin primes are guaranteed to exist.
To summarize, if no twin prime pair is found initially, we continue searching for the closest prime pair by tracking the smallest difference encountered. However, if the range is greater than 1452, it is mathematically guaranteed that at least one twin prime pair will exist within it.

Let R be right and L be left, representing the range within which we search for prime numbers.
Time Complexity: O(min(1452,R−L)⋅sqrt(R))
The algorithm iterates through numbers in the range [L, R] to identify prime numbers. For each number, it performs a primality check, which takes O(
R) time in the worst case.
If R - L ≥ 1452, we know that a twin prime pair must exist in the range, allowing us to stop early. In this case, the algorithm processes at most 1452 numbers, leading to a complexity of O(1452⋅
R).
If R - L < 1452, the algorithm checks up to R - L numbers, resulting in a worst-case complexity of O((R−L)⋅
R).Therefore, the overall time complexity is bounded by O(min(1452,R−L)⋅ R).

 */