import java.util.ArrayList;
import java.util.List;

public class SJF extends Scheduler {
    
    public SJF() {
        super("Shortest Job First (SJF)");
    }

    @Override
    public void schedule() {
        resetProcesses();
        
        List<Process> completedProcesses = new ArrayList<>();
        int currentTime = 0;
        int completedCount = 0;
        
        System.out.println("\n" + algorithmName + " Execution Order:");
        
        while (completedCount < processes.size()) {
            Process shortestProcess = findShortestJob(currentTime, completedProcesses);
            
            if (shortestProcess == null) {
                currentTime++;
                continue;
            }
            
            int startTime = currentTime;
            int endTime = currentTime + shortestProcess.getBurstTime();
            
            System.out.printf("Time %d-%d: Executing P%d\n", 
                startTime, endTime, shortestProcess.getProcessId());
            
            addGanttChartEntry(shortestProcess.getProcessId(), startTime, endTime);
            currentTime = endTime;
            shortestProcess.setCompletionTime(currentTime);
            completedProcesses.add(shortestProcess);
            completedCount++;
        }
        
        processes = completedProcesses;
        calculateMetrics();
    }
    
    private Process findShortestJob(int currentTime, List<Process> completedProcesses) {
        Process shortestProcess = null;
        int shortestBurstTime = Integer.MAX_VALUE;
        
        for (Process process : processes) {
            if (!completedProcesses.contains(process) && 
                process.getArrivalTime() <= currentTime &&
                process.getBurstTime() < shortestBurstTime) {
                shortestProcess = process;
                shortestBurstTime = process.getBurstTime();
            }
        }
        
        return shortestProcess;
    }
}
