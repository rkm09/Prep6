package daily.medium;

public class MaxDiff1432 {
    public static void main(String[] args) {
        int num = 555;
        System.out.println(maxDiff(num));
    }

//    enumeration; time: O(n^2log(num)), space: O(n)
    public static int maxDiff(int num) {
        int minNum = num, maxNum = num;
        for(int i = 0 ; i < 10 ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                String resStr = replaceDigit(num, i, j);
//                check if there are leading zeroes
                if(resStr.charAt(0) != '0') {
                    int res = Integer.parseInt(resStr);
                    maxNum = Math.max(maxNum, res);
                    minNum = Math.min(minNum, res);
                }
            }
        }
        return maxNum - minNum;
    }

    private static String replaceDigit(int num, int x, int y) {
        StringBuilder res = new StringBuilder(String.valueOf(num)); // note: need to put String.valueOf()
        int n = res.length();
        for(int i = 0 ; i < n ; i++) {
            char digit = res.charAt(i);
            if(digit - '0' == x){
                res.setCharAt(i, (char) ('0' +  y));
            }
        }
        return res.toString();
    }
}

/*
You are given an integer num. You will apply the following steps to num two separate times:
Pick a digit x (0 <= x <= 9).
Pick another digit y (0 <= y <= 9). Note y can be equal to x.
Replace all the occurrences of x in the decimal representation of num by y.
Let a and b be the two results from applying the operation to num independently.
Return the max difference between a and b.
Note that neither a nor b may have any leading zeros, and must not be 0.

Example 1:
Input: num = 555
Output: 888
Explanation: The first time pick x = 5 and y = 9 and store the new integer in a.
The second time pick x = 5 and y = 1 and store the new integer in b.
We have now a = 999 and b = 111 and max difference = 888
Example 2:
Input: num = 9
Output: 8
Explanation: The first time pick x = 9 and y = 9 and store the new integer in a.
The second time pick x = 9 and y = 1 and store the new integer in b.
We have now a = 9 and b = 1 and max difference = 8

Constraints:
1 <= num <= 108
 */