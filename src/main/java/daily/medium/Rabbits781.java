package daily.medium;

import java.util.HashMap;
import java.util.Map;

public class Rabbits781 {
    public static void main(String[] args) {
        int[] answers = {1,1,2};
        System.out.println(numRabbits(answers));
    }

//    counter; time: O(n), space: O(1)
    public static int numRabbits(int[] answers) {
        int[] count = new int[1001];
        for(int ans : answers)
            count[ans]++;
        int res = 0;
        for(int k = 0 ; k < 1000 ; k++) {
            int groupSize =  k + 1;
            int groups = (int) Math.ceil((double) count[k] / groupSize);
            res += groups * groupSize;
        }
        return res;
    }

//    map; time: O(n), space: O(n)
    public static int numRabbits1(int[] answers) {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int ans : answers)
            freq.put(ans, freq.getOrDefault(ans, 0) + 1);
        int res = 0;
        for(int key : freq.keySet()) {
            int groupSize = key + 1;
            int groups = (int) Math.ceil((double) freq.get(key) / groupSize);
            res += groups * groupSize;
        }
        return res;
    }
}

/*
There is a forest with an unknown number of rabbits. We asked n rabbits "How many rabbits have the same color as you?" and collected the answers in an integer array answers where answers[i] is the answer of the ith rabbit.
Given the array answers, return the minimum number of rabbits that could be in the forest.
Example 1:
Input: answers = [1,1,2]
Output: 5
Explanation:
The two rabbits that answered "1" could both be the same color, say red.
The rabbit that answered "2" can't be red or the answers would be inconsistent.
Say the rabbit that answered "2" was blue.
Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.
Example 2:
Input: answers = [10,10,10]
Output: 11
Constraints:
1 <= answers.length <= 1000
0 <= answers[i] < 1000
 */
