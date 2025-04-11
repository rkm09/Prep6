package daily.easy;

public class CountSymmetric2843 {
    public static void main(String[] args) {
        System.out.println(countSymmetricIntegers(1200,1230));
    }

//    enumeration; time: O(high - low), space: O(1)
    public static int countSymmetricIntegers(int low, int high) {
        int res = 0;
        for(int num = low ; num <= high ; num++) {
            if(num < 100 && num % 11 == 0)
                res++;
            else if(num >= 1000 && num < 10000) {
                int left = (num / 1000) + (num % 1000) / 100;
                int right = (num % 100) / 10 + (num % 10);
                if(left == right)
                    res++;
            }
        }
        return res;
    }

//    def(brute force); time: O(n^2), space: O(1)
    public static int countSymmetricIntegers1(int low, int high) {
        int count = 0;
        for(int num = low ; num <= high ; num++) {
            if(String.valueOf(num).length() % 2 != 0) continue;
            if(checkSymmetric(num))
                count++;
        }
        return count;
    }

    private static boolean checkSymmetric(int num) {
        String numStr = String.valueOf(num);
        String firstHalf = numStr.substring(0, numStr.length() / 2);
        String secondHalf = numStr.substring(numStr.length() / 2);
        int fSum = 0, bSum = 0;
        for(int i = 0 ; i < firstHalf.length() ; i++) {
            fSum += firstHalf.charAt(i) - '0';
        }
        for(int i = 0 ; i < secondHalf.length() ; i++) {
            bSum += secondHalf.charAt(i) - '0';
        }
        return fSum == bSum;
    }
}

/*
You are given two positive integers low and high.
An integer x consisting of 2 * n digits is symmetric if the sum of the first n digits of x is equal to the sum of the last n digits of x. Numbers with an odd number of digits are never symmetric.
Return the number of symmetric integers in the range [low, high].
Example 1:
Input: low = 1, high = 100
Output: 9
Explanation: There are 9 symmetric integers between 1 and 100: 11, 22, 33, 44, 55, 66, 77, 88, and 99.
Example 2:
Input: low = 1200, high = 1230
Output: 4
Explanation: There are 4 symmetric integers between 1200 and 1230: 1203, 1212, 1221, and 1230.
Constraints:
1 <= low <= high <= 104
 */