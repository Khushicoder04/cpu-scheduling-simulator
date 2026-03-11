/**
 * Represents a process in the CPU scheduling simulator.
 * 
 * This class encapsulates all the essential attributes of a process including
 * arrival time, burst time, priority, and calculated metrics like waiting time
 * and turnaround time. It provides methods to update and retrieve process information
 * throughout the scheduling simulation.
 * 
 * @author CPU Scheduling Simulator
 * @version 1.0
 * @since 2024
 */
public class Process {
    /** Unique identifier for the process */
    private final int processId;
    
    /** Time when the process arrives in the ready queue */
    private final int arrivalTime;
    
    /** Total CPU time required by the process */
    private final int burstTime;
    
    /** Priority level (lower number = higher priority) */
    private final int priority;
    
    /** Time when the process completes execution */
    private int completionTime;
    
    /** Total time from arrival to completion */
    private int turnaroundTime;
    
    /** Total time spent waiting in ready queue */
    private int waitingTime;
    
    /** Remaining CPU time for pre-emptive algorithms */
    private int remainingTime;

    /**
     * Constructs a new Process with specified parameters.
     * 
     * @param processId Unique identifier for the process
     * @param arrivalTime Time when process arrives (must be >= 0)
     * @param burstTime CPU time required (must be > 0)
     * @param priority Priority level (lower number = higher priority)
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Process(int processId, int arrivalTime, int burstTime, int priority) {
        if (processId <= 0) {
            throw new IllegalArgumentException("Process ID must be positive");
        }
        if (arrivalTime < 0) {
            throw new IllegalArgumentException("Arrival time cannot be negative");
        }
        if (burstTime <= 0) {
            throw new IllegalArgumentException("Burst time must be positive");
        }
        if (priority < 0) {
            throw new IllegalArgumentException("Priority cannot be negative");
        }
        
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }

    /** @return Unique process identifier */
    public int getProcessId() {
        return processId;
    }

    /** @return Time when process arrives in ready queue */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /** @return Total CPU time required by process */
    public int getBurstTime() {
        return burstTime;
    }

    /** @return Priority level (lower = higher priority) */
    public int getPriority() {
        return priority;
    }

    /** @return Time when process completes execution */
    public int getCompletionTime() {
        return completionTime;
    }

    /** @return Total time from arrival to completion */
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    /** @return Total time spent waiting in ready queue */
    public int getWaitingTime() {
        return waitingTime;
    }

    /** @return Remaining CPU time for pre-emptive algorithms */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets the completion time for the process.
     * 
     * @param completionTime Time when process completes execution
     */
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * Sets the turnaround time for the process.
     * 
     * @param turnaroundTime Total time from arrival to completion
     */
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    /**
     * Sets the waiting time for the process.
     * 
     * @param waitingTime Total time spent waiting in ready queue
     */
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Sets the remaining time for pre-emptive algorithms.
     * 
     * @param remainingTime Remaining CPU time
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Calculates turnaround time based on completion and arrival times.
     * Turnaround Time = Completion Time - Arrival Time
     */
    public void calculateTurnaroundTime() {
        this.turnaroundTime = this.completionTime - this.arrivalTime;
    }

    /**
     * Calculates waiting time based on turnaround and burst times.
     * Waiting Time = Turnaround Time - Burst Time
     */
    public void calculateWaitingTime() {
        this.waitingTime = this.turnaroundTime - this.burstTime;
    }

    /**
     * Resets the process state for new simulation runs.
     * Resets completion time, turnaround time, waiting time, and remaining time.
     */
    public void reset() {
        this.remainingTime = this.burstTime;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }

    /**
     * Returns a string representation of the process with all key metrics.
     * 
     * @return Formatted string containing process information
     */
    @Override
    public String toString() {
        return String.format("P%d (AT=%d, BT=%d, PR=%d, CT=%d, TAT=%d, WT=%d)", 
            processId, arrivalTime, burstTime, priority, completionTime, turnaroundTime, waitingTime);
    }

    /**
     * Compares this process to another based on process ID.
     * 
     * @param obj Object to compare with
     * @return true if processes have same ID, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Process process = (Process) obj;
        return processId == process.processId;
    }

    /**
     * Returns hash code based on process ID.
     * 
     * @return Hash code for this process
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(processId);
    }
}
