import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.run();
    }

    private Queue<Job> pendingQueue;
    private int numCustomers;
    private long totalWaitingTime;
    private long time = 0;

    private class Job implements Comparable<Job> {

        final long arrivalTime;
        final long processingTime;
        long entryTime;

        public long getArrivalTime() {
            return arrivalTime;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public Job(final long arrivalTime, final long processingTime) {
            this.arrivalTime = arrivalTime;
            this.processingTime = processingTime;
        }

        @Override
        public String toString() {
            return "Job{" +
                    "arrivalTime=" + arrivalTime +
                    ", processingTime=" + processingTime +
                    '}';
        }

        @Override
        public int compareTo(Job o) {
            return this.arrivalTime > o.arrivalTime ? +1 : this.arrivalTime < o.arrivalTime ? -1 : 0;
        }


    }

    private void run() {
        load();
        process();
        report();
    }

    private void report() {
        long averageWaitTime = (long) totalWaitingTime / numCustomers;

        System.out.print(averageWaitTime);
    }

    private void process() {
        final Queue<Job> processingQueue = new PriorityQueue<>(10, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return o1.processingTime > o2.processingTime ? 1 : o1.processingTime < o2.processingTime ? -1 : 0;
            }
        });
        long count = 1;
        while (!pendingQueue.isEmpty() || !processingQueue.isEmpty()) {
            if (!pendingQueue.isEmpty()) {
                if (!processingQueue.isEmpty()) {
                    time = time < processingQueue.peek().arrivalTime ? processingQueue.peek().arrivalTime : time;
                }
                else {
                    time = time < pendingQueue.peek().arrivalTime ? pendingQueue.peek().arrivalTime : time;
                }
            }

            while (!pendingQueue.isEmpty() && pendingQueue.peek().arrivalTime <= time) {

                Job jobToQueue = pendingQueue.poll();
                jobToQueue.entryTime = time;
                processingQueue.add(jobToQueue);
            }
            Job job = processingQueue.poll();
            long waitingTime = calculateWaitingTime(job);

            count++;
            totalWaitingTime = totalWaitingTime + waitingTime;

            time = time + job.processingTime;
        }
    }

    private long calculateWaitingTime(Job job) {
        return (time - job.arrivalTime) + job.processingTime;
    }

    private void load() {
        Scanner s = new Scanner(System.in);
        numCustomers = s.nextInt();
        pendingQueue = new PriorityQueue<>(numCustomers);
        int i = numCustomers;
        while (i-- > 0) {
            long arrivalTime = s.nextLong();
            long processingTime = s.nextLong();
            pendingQueue.add(new Job(arrivalTime, processingTime));
        }
    }
}
