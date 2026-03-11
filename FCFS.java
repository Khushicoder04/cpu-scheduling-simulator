import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends Scheduler {
    
    public FCFS() {
        super("First Come First Serve (FCFS)");
    }

    @Override
    public void schedule() {
        resetProcesses();
        sortProcessesByArrivalTime();
        
        Queue<Process> readyQueue = new LinkedList<>(processes);
        int currentTime = 0;
        
        System.out.println("\n" + algorithmName + " Execution Order:");
        
        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll();
            
            if (currentTime < currentProcess.getArrivalTime()) {
                currentTime = currentProcess.getArrivalTime();
            }
            
            int startTime = currentTime;
            int endTime = currentTime + currentProcess.getBurstTime();
            
            System.out.printf("Time %d-%d: Executing P%d\n", 
                startTime, endTime, currentProcess.getProcessId());
            
            addGanttChartEntry(currentProcess.getProcessId(), startTime, endTime);
            currentTime = endTime;
            currentProcess.setCompletionTime(currentTime);
        }
        
        calculateMetrics();
    }
}
