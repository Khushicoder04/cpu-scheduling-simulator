import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for CPU scheduling algorithms.
 * 
 * This class provides the foundation for implementing various CPU scheduling
 * algorithms including FCFS, SJF, Round Robin, and Priority Scheduling.
 * It manages process collections, calculates performance metrics, and tracks
 * Gantt chart entries for visualization.
 * 
 * @author CPU Scheduling Simulator
 * @version 1.0
 * @since 2024
 */
public abstract class Scheduler {
    /** List of processes to be scheduled */
    protected List<Process> processes;
    
    /** Name of the scheduling algorithm */
    protected final String algorithmName;
    
    /** Gantt chart entries for visualization */
    protected List<GanttChartEntry> ganttChart;

    /**
     * Constructs a new Scheduler with the specified algorithm name.
     * 
     * @param algorithmName Name of the scheduling algorithm
     * @throws IllegalArgumentException if algorithmName is null or empty
     */
    public Scheduler(String algorithmName) {
        if (algorithmName == null || algorithmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Algorithm name cannot be null or empty");
        }
        this.algorithmName = algorithmName;
        this.processes = new ArrayList<>();
        this.ganttChart = new ArrayList<>();
    }

    /**
     * Adds a single process to the scheduler.
     * 
     * @param process Process to be added
     * @throws IllegalArgumentException if process is null
     */
    public void addProcess(Process process) {
        if (process == null) {
            throw new IllegalArgumentException("Process cannot be null");
        }
        processes.add(process);
    }

    /**
     * Adds multiple processes to the scheduler.
     * 
     * @param processes List of processes to be added
     * @throws IllegalArgumentException if processes list is null
     */
    public void addProcesses(List<Process> processes) {
        if (processes == null) {
            throw new IllegalArgumentException("Processes list cannot be null");
        }
        this.processes.addAll(processes);
    }

    /**
     * Executes the scheduling algorithm.
     * This method must be implemented by concrete scheduling algorithms.
     * It should:
     * 1. Order processes according to the algorithm's rules
     * 2. Calculate completion times
     * 3. Track Gantt chart entries
     * 4. Calculate performance metrics
     */
    public abstract void schedule();

    /**
     * Calculates waiting time and turnaround time for all processes.
     * This method should be called after scheduling is complete.
     */
    protected void calculateMetrics() {
        for (Process process : processes) {
            process.calculateTurnaroundTime();
            process.calculateWaitingTime();
        }
    }

    /**
     * Displays the scheduling results in a formatted table.
     * Shows process details, completion times, waiting times, and turnaround times.
     * Also displays average waiting and turnaround times.
     */
    public void displayResults() {
        System.out.println("\n" + algorithmName + " Scheduling Results:");
        System.out.println("PID\tAT\tBT\tPR\tCT\tTAT\tWT");
        System.out.println("----------------------------------------");
        
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        
        for (Process process : processes) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
                process.getProcessId(),
                process.getArrivalTime(),
                process.getBurstTime(),
                process.getPriority(),
                process.getCompletionTime(),
                process.getTurnaroundTime(),
                process.getWaitingTime());
            
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaroundTime / processes.size());
    }

    /**
     * Resets all processes to their initial state.
     * Clears Gantt chart entries and resets process metrics.
     */
    public void resetProcesses() {
        for (Process process : processes) {
            process.reset();
        }
        ganttChart.clear();
    }

    /** @return Immutable list of processes */
    public List<Process> getProcesses() {
        return new ArrayList<>(processes);
    }

    /** @return Immutable list of Gantt chart entries */
    public List<GanttChartEntry> getGanttChart() {
        return new ArrayList<>(ganttChart);
    }

    /** @return Name of the scheduling algorithm */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Adds a Gantt chart entry for tracking process execution.
     * 
     * @param processId ID of the process being executed
     * @param startTime Start time of execution
     * @param endTime End time of execution
     * @throws IllegalArgumentException if parameters are invalid
     */
    protected void addGanttChartEntry(int processId, int startTime, int endTime) {
        if (processId <= 0) {
            throw new IllegalArgumentException("Process ID must be positive");
        }
        if (startTime < 0 || endTime < 0) {
            throw new IllegalArgumentException("Time values cannot be negative");
        }
        if (startTime > endTime) {
            throw new IllegalArgumentException("Start time cannot be greater than end time");
        }
        
        ganttChart.add(new GanttChartEntry(processId, startTime, endTime));
    }

    /**
     * Sorts processes by arrival time in ascending order.
     * This is commonly used as the initial ordering for many algorithms.
     */
    protected void sortProcessesByArrivalTime() {
        processes.sort((p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));
    }

    /**
     * Represents a single entry in the Gantt chart.
     * Each entry tracks when a specific process was executing.
     */
    /**
     * Calculates and returns detailed performance metrics.
     * 
     * @return PerformanceMetrics object containing all calculated metrics
     */
    public PerformanceMetrics getPerformanceMetrics() {
        if (processes.isEmpty()) {
            return new PerformanceMetrics(0, 0, 0, 0, 0, 0);
        }
        
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double totalBurstTime = 0;
        int maxWaitingTime = 0;
        int maxTurnaroundTime = 0;
        
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
            totalResponseTime += (process.getCompletionTime() - process.getArrivalTime() - process.getBurstTime());
            totalBurstTime += process.getBurstTime();
            
            maxWaitingTime = Math.max(maxWaitingTime, process.getWaitingTime());
            maxTurnaroundTime = Math.max(maxTurnaroundTime, process.getTurnaroundTime());
        }
        
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();
        double avgResponseTime = totalResponseTime / processes.size();
        double cpuUtilization = (totalBurstTime / getTotalMakespan()) * 100;
        double throughput = (double) processes.size() / getTotalMakespan();
        
        return new PerformanceMetrics(avgWaitingTime, avgTurnaroundTime, avgResponseTime, 
                                    cpuUtilization, throughput, processes.size());
    }
    
    /**
     * Calculates the total makespan (total execution time).
     * 
     * @return Total time from first arrival to last completion
     */
    private int getTotalMakespan() {
        if (processes.isEmpty()) return 0;
        
        int minArrival = Integer.MAX_VALUE;
        int maxCompletion = 0;
        
        for (Process process : processes) {
            minArrival = Math.min(minArrival, process.getArrivalTime());
            maxCompletion = Math.max(maxCompletion, process.getCompletionTime());
        }
        
        return maxCompletion - minArrival;
    }
    
    /**
     * Represents comprehensive performance metrics for a scheduling algorithm.
     */
    public static class PerformanceMetrics {
        private final double avgWaitingTime;
        private final double avgTurnaroundTime;
        private final double avgResponseTime;
        private final double cpuUtilization;
        private final double throughput;
        private final int totalProcesses;
        
        public PerformanceMetrics(double avgWaitingTime, double avgTurnaroundTime, 
                               double avgResponseTime, double cpuUtilization, 
                               double throughput, int totalProcesses) {
            this.avgWaitingTime = avgWaitingTime;
            this.avgTurnaroundTime = avgTurnaroundTime;
            this.avgResponseTime = avgResponseTime;
            this.cpuUtilization = cpuUtilization;
            this.throughput = throughput;
            this.totalProcesses = totalProcesses;
        }
        
        public double getAvgWaitingTime() { return avgWaitingTime; }
        public double getAvgTurnaroundTime() { return avgTurnaroundTime; }
        public double getAvgResponseTime() { return avgResponseTime; }
        public double getCpuUtilization() { return cpuUtilization; }
        public double getThroughput() { return throughput; }
        public int getTotalProcesses() { return totalProcesses; }
        
        @Override
        public String toString() {
            return String.format("Avg WT: %.2f, Avg TAT: %.2f, CPU Util: %.1f%%, Throughput: %.2f", 
                               avgWaitingTime, avgTurnaroundTime, cpuUtilization, throughput);
        }
    }
    
    /**
     * Represents a single entry in the Gantt chart.
     * Each entry tracks when a specific process was executing.
     */
    public static class GanttChartEntry {
        /** ID of the process */
        private final int processId;
        
        /** Start time of execution */
        private final int startTime;
        
        /** End time of execution */
        private final int endTime;

        /**
         * Constructs a new Gantt chart entry.
         * 
         * @param processId ID of the process
         * @param startTime Start time of execution
         * @param endTime End time of execution
         */
        public GanttChartEntry(int processId, int startTime, int endTime) {
            this.processId = processId;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /** @return Process ID */
        public int getProcessId() {
            return processId;
        }

        /** @return Start time of execution */
        public int getStartTime() {
            return startTime;
        }

        /** @return End time of execution */
        public int getEndTime() {
            return endTime;
        }

        /** @return Duration of execution (end - start) */
        public int getDuration() {
            return endTime - startTime;
        }

        /**
         * Returns a string representation of the Gantt chart entry.
         * 
         * @return Formatted string showing process and time range
         */
        @Override
        public String toString() {
            return String.format("P%d: %d-%d", processId, startTime, endTime);
        }

        /**
         * Compares this entry to another based on start time.
         * 
         * @param obj Object to compare with
         * @return true if entries have same start time, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GanttChartEntry that = (GanttChartEntry) obj;
            return startTime == that.startTime;
        }

        /**
         * Returns hash code based on start time.
         * 
         * @return Hash code for this entry
         */
        @Override
        public int hashCode() {
            return Integer.hashCode(startTime);
        }
    }
}
