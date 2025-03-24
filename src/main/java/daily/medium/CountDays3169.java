package daily.medium;

import java.util.Arrays;
import java.util.Comparator;

public class CountDays3169 {
    public static void main(String[] args) {
        int[][] meetings = {{5,7},{1,3},{9,10}};
        System.out.println(countDays(10, meetings));
    }

//    sorting; time: O(nlogn), space: O(n)
    public static int countDays(int days, int[][] meetings) {
        int freeDays = 0, latestEnd = 0;
//        sort meetings based on starting time
        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));
        for(int[] meeting : meetings) {
            int start = meeting[0], end = meeting[1];
//            add current range of days without a meeting
            if(start > latestEnd + 1)
                freeDays += start - latestEnd - 1;
//            update latest meeting end
            latestEnd = Math.max(latestEnd, end);
        }
//        add all days after the last meeting end time
        freeDays += days - latestEnd;
        return freeDays;
    }
}

/*
You are given a positive integer days representing the total number of days an employee is available for work (starting from day 1). You are also given a 2D array meetings of size n where, meetings[i] = [start_i, end_i] represents the starting and ending days of meeting i (inclusive).
Return the count of days when the employee is available for work but no meetings are scheduled.
Note: The meetings may overlap.
Example 1:
Input: days = 10, meetings = [[5,7],[1,3],[9,10]]
Output: 2
Explanation:
There is no meeting scheduled on the 4th and 8th days.
Example 2:
Input: days = 5, meetings = [[2,4],[1,3]]
Output: 1
Explanation:
There is no meeting scheduled on the 5th day.
Example 3:
Input: days = 6, meetings = [[1,6]]
Output: 0
Explanation:
Meetings are scheduled for all working days.

Constraints:
1 <= days <= 109
1 <= meetings.length <= 105
meetings[i].length == 2
1 <= meetings[i][0] <= meetings[i][1] <= days
 */