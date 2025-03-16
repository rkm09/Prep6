package daily.medium;

public class RepairCars2594 {
    public static void main(String[] args) {
        int[] ranks = {4,2,3,1};
        System.out.println(repairCars(ranks, 10));
    }


//    binary search; time: O(n + max_rank.log(m.max_rank)), space: O(max_rank)
    public static long repairCars(int[] ranks, int cars) {
//        either initialize with any element say rank[0] or like so, but note not 0 as that make it incorrect
        int maxRank = Integer.MIN_VALUE, minRank = Integer.MAX_VALUE;
//        find min and max rank
        for(int rank : ranks) {
            maxRank = Math.max(maxRank, rank);
            minRank = Math.min(minRank, rank);
        }
//        frequency array that keep tracks of the number of mechanics at each rank
        int[] freq = new int[maxRank + 1];
        for(int rank : ranks)
            freq[rank]++;

//        minimum and maximum possible time
        long low = 1, high = (long) minRank * cars * cars;
//        perform binary search to find the minimum time required
        while(low < high) {
            long mid = low + (high - low) / 2;
            long carsRepaired = 0;
//            calculate the total number of cars that can be repaired in 'mid' time
            for(int rank = 1 ; rank <= maxRank ; rank++) {
                carsRepaired += freq[rank] * (long) Math.sqrt((double) mid / rank);
            }
//            adjust the search boundaries based on the number of cars repaired
            if(carsRepaired >= cars)
                high = mid; // try to find a smaller time window
            else
                low = mid + 1;  // need more time
        }
        return low;
    }
}

/*
You are given an integer array ranks representing the ranks of some mechanics. ranksi is the rank of the ith mechanic. A mechanic with a rank r can repair n cars in r * n2 minutes.
You are also given an integer cars representing the total number of cars waiting in the garage to be repaired.
Return the minimum time taken to repair all the cars.
Note: All the mechanics can repair the cars simultaneously.
Example 1:
Input: ranks = [4,2,3,1], cars = 10
Output: 16
Explanation:
- The first mechanic will repair two cars. The time required is 4 * 2 * 2 = 16 minutes.
- The second mechanic will repair two cars. The time required is 2 * 2 * 2 = 8 minutes.
- The third mechanic will repair two cars. The time required is 3 * 2 * 2 = 12 minutes.
- The fourth mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
It can be proved that the cars cannot be repaired in less than 16 minutes.
Example 2:
Input: ranks = [5,1,8], cars = 6
Output: 16
Explanation:
- The first mechanic will repair one car. The time required is 5 * 1 * 1 = 5 minutes.
- The second mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
- The third mechanic will repair one car. The time required is 8 * 1 * 1 = 8 minutes.
It can be proved that the cars cannot be repaired in less than 16 minutes.

Constraints:
1 <= ranks.length <= 105
1 <= ranks[i] <= 100
1 <= cars <= 106
 */

/*
Binary search:
Let n be the size of the ranks array, m be the number of cars (cars).
Time Complexity: O(n+max_ranklog(m⋅max_rank))
The algorithm starts by iterating through the ranks array to compute the minimum rank and build a frequency array. This step takes O(n) time, as it involves a single pass over the array. Next, the algorithm performs a binary search over the possible time range, which spans from 1 to 1L⋅minRank⋅m⋅m. The binary search runs in O(log(m⋅max_rank)) iterations, where max_rank is the maximum rank in the ranks array.
For each iteration of the binary search, the algorithm calculates the total number of cars that can be repaired in mid time. This involves iterating over the frequency array which has a fixed size of max_rank and computing the square root of the ratio of mid to the rank for each entry. This computation takes O(max_rank) time per iteration. Combining these steps, the overall time complexity is O(n+max_ranklog(m⋅max_rank)).
Space Complexity: O(max_rank)
The algorithm uses a frequency array of size max_rank to store the count of mechanics for each rank. This array occupies O(max_rank) space. Additionally, a few variables are used for the binary search (low, high, mid, carsRepaired) and for storing the minimum rank, all of which require constant space, O(1). Thus, the overall space complexity is O(max_rank).
 */