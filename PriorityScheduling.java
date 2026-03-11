import java.util.ArrayList;
import java.util.List;

public class PriorityScheduling extends Scheduler {
    
    public PriorityScheduling() {
        super("Priority Scheduling (Lower Priority Number = Higher Priority)");
    }

    @Override
    public void schedule() {
        resetProcesses();
        
        List<Process> completedProcesses = new ArrayList<>();
        int currentTime = 0;
        int completedCount = 0;
        
        System.out.println("\n" + algorithmName + " Execution Order:");
        
        while (completedCount < processes.size()) {
            Process highestPriorityProcess = findHighestPriorityProcess(currentTime, completedProcesses);
            
            if (highestPriorityProcess == null) {
                currentTime++;
                continue;
            }
            
            int startTime = currentTime;
            int endTime = currentTime + highestPriorityProcess.getBurstTime();
            
            System.out.printf("Time %d-%d: Executing P%d (Priority=%d)\n", 
                startTime, endTime, highestPriorityProcess.getProcessId(), highestPriorityProcess.getPriority());
            
            addGanttChartEntry(highestPriorityProcess.getProcessId(), startTime, endTime);
            currentTime = endTime;
            highestPriorityProcess.setCompletionTime(currentTime);
            completedProcesses.add(highestPriorityProcess);
            completedCount++;
        }
        
        processes = completedProcesses;
        calculateMetrics();
    }
    
    private Process findHighestPriorityProcess(int currentTime, List<Process> completedProcesses) {
        Process highestPriorityProcess = null;
        int highestPriority = Integer.MAX_VALUE;
        
        for (Process process : processes) {
            if (!completedProcesses.contains(process) && 
                process.getArrivalTime() <= currentTime &&
                process.getPriority() < highestPriority) {
                highestPriorityProcess = process;
                highestPriority = process.getPriority();
            }
        }
        
        return highestPriorityProcess;
    }
}
