package daily.medium;

import java.util.*;

public class RepairCars2594 {
    public static void main(String[] args) {
        int[] ranks = {4,2,3,1};
        System.out.println(repairCars(ranks, 10));
    }


//    binary search on time; time: O(n + max_rank.log(m.max_rank)), space: O(max_rank)  [fastest]
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

//    space optimized binary search; time: O(n.log(m.max_rank)), space: O(1)
    public static long repairCars1(int[] ranks, int cars) {
        int minRank = Arrays.stream(ranks).min().getAsInt();
        long low = 1, high = (long) minRank * cars * cars;
        while(low < high) {
            long mid = low + (high - low) / 2;
            long carsRepaired = 0;
            for(int rank : ranks)
                carsRepaired += (long) Math.sqrt(1.0 * mid / rank);
            if(carsRepaired >= cars)
                high = mid;
            else
                low = mid + 1;
        }
        return low;
    }

//    min heap; time: O(n + mlogk), space: O(k)  [m - number of cars, k - max possible rank]
    public static long repairCars2(int[] ranks, int cars) {
        Map<Integer, Integer> count = new HashMap<>();
//        count the frequency of each rank
        for(int rank : ranks)
            count.put(rank, count.getOrDefault(rank, 0) + 1);
//        long[] elements: [time, rank, cars repaired by this mechanic, number of mechanics at this rank]
        PriorityQueue<long[]> minHeap = new PriorityQueue<>(Comparator.comparingLong(a->a[0]));
//        initial time is rank [time(1): rank * 1 * 1]
        for(int rank : count.keySet())
            minHeap.offer(new long[] {rank, rank, 1, count.get(rank)});
        long time = 0;
//        process until all cars are repaired
        while(cars > 0) {
//            pop the mechanic group with the smallest current repair time
            long[] current = minHeap.poll();
            time = current[0];
            int rank = (int) current[1];
            long n = current[2];
            int mechanicCount = (int) current[3];

//            deduct the number of cars repaired by this mechanic group
            cars -= mechanicCount;
//            increment the number of cars repaired by this mechanic
            n += 1;
//            push the updated repair time back into the queue
//            the new repair time is rank * n^2 (time increases quadratically with n)
            minHeap.offer(new long[]{rank * n * n, rank, n, mechanicCount});
        }
        return time;
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
The problem essentially boils down to distributing the cars optimally among the mechanics so that the maximum repair time (the slowest mechanic) is minimized. Another way to say this is that a mechanic with a lower rank (higher skill) can repair cars faster than one with a higher rank. A
A common mistake is misunderstanding how parallel execution works. Instead of focusing on the slowest mechanic, some mistakenly add up all the repair times, as if the tasks were done sequentially. This misinterpretation leads to incorrect conclusions about the total time required.
Another mistake is assuming dynamic programming (DP) is always the right approach for optimization problems. When faced with minimization or maximization, our first instinct might be to reach for DP.
However, before committing to it, we need to check the constraints. If the problem lacks overlapping sub problems or an optimal substructure, DP may not be a suitable choice.
In this problem, we are given: ranks.length can be up to 10^5
cars can be up to 10^6
A typical DP solution would require a state representation like dp[mechanic][car]. The time complexity would then be O(n⋅cars), which in the worst case is 10
5×10^6=10^11 operations. This is far too large to be computationally feasible.
A good approach here can be to use binary search, as it provides a more natural way to minimize the maximum time while still efficiently distributing cars.
Takeaway Tip: When deciding on an approach, always check the constraints first. If n and cars are large (in the range of 10 5to 10 6), DP is usually not feasible. Instead, binary search, greedy, or two pointers are more likely to work in such cases.
Let n be the size of the ranks array, m be the number of cars (cars).
More technically, given a fixed time t, we can determine how many cars can be repaired within that time using a simple formula. But how do we actually find the smallest t that allows all cars to be repaired?
We observe that if a given time t is sufficient to repair all cars, then any time greater than t will also be sufficient. Conversely, if t is not enough, then any time smaller than t will also fail.
This forms a monotonic relationship:
If t is too small, increasing t will eventually make it work.
If t is large enough, decreasing t will still work until we hit the minimum threshold.
This kind of "yes/no" behavior, where a function transitions from failure to success at a specific boundary, is exactly when binary search is useful. Instead of checking every possible value of t from 1 to minRank * cars^2, we can narrow down the search space logarithmically.

Time Complexity: O(n+max_ranklog(m⋅max_rank))
The algorithm starts by iterating through the ranks array to compute the minimum rank and build a frequency array. This step takes O(n) time, as it involves a single pass over the array. Next, the algorithm performs a binary search over the possible time range, which spans from 1 to 1L⋅minRank⋅m⋅m. The binary search runs in O(log(m⋅max_rank)) iterations, where max_rank is the maximum rank in the ranks array.
For each iteration of the binary search, the algorithm calculates the total number of cars that can be repaired in mid time. This involves iterating over the frequency array which has a fixed size of max_rank and computing the square root of the ratio of mid to the rank for each entry. This computation takes O(max_rank) time per iteration. Combining these steps, the overall time complexity is O(n+max_ranklog(m⋅max_rank)).
Space Complexity: O(max_rank)
The algorithm uses a frequency array of size max_rank to store the count of mechanics for each rank. This array occupies O(max_rank) space. Additionally, a few variables are used for the binary search (low, high, mid, carsRepaired) and for storing the minimum rank, all of which require constant space, O(1). Thus, the overall space complexity is O(max_rank).

Min heap:
Instead of using binary search, we can directly simulate the car repair process using a min-heap to always prioritize the mechanic who can complete the next repair in the shortest possible time. Since each mechanic follows the formula time = rank * n^2 to determine how long it takes to repair their k-th car, we can predict the sequence of repair times for each mechanic. The first car takes rank * 1^2 = rank time, the second car takes rank * 4 time, the third car takes rank * 9 time, and so on.
Given this pattern, at any moment, the mechanic who will finish the next repair the fastest should be chosen to repair the next car. The most efficient way to track the next available repair time for all mechanics is to use a min-heap, where each entry stores the next repair time for a mechanic, their rank (to calculate future repair times), the number of cars they have already repaired, and the count of mechanics with that rank (since multiple mechanics can have the same rank).
We begin by initializing the heap with the first repair time for each unique rank. If multiple mechanics share the same rank, we keep track of how many exist. Then, we repeatedly extract the mechanic with the earliest repair time and assign them the next car to repair. Once a mechanic repairs a car, we compute their next available repair time using the formula time = rank * (n + 1)^2, then push this new time back into the heap. This process continues until all cars are repaired.
By always selecting the fastest available repair, we ensure that the total time remains minimal while efficiently distributing the workload among mechanics. Since each mechanic’s repair time follows a monotonically increasing pattern, the heap naturally maintains the correct ordering.
*/