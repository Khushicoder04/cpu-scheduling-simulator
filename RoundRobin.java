import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends Scheduler {
    private int timeQuantum;

    public RoundRobin(int timeQuantum) {
        super("Round Robin (Quantum=" + timeQuantum + ")");
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void schedule() {
        resetProcesses();
        sortProcessesByArrivalTime();
        
        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completedCount = 0;
        int processIndex = 0;
        
        System.out.println("\n" + algorithmName + " Execution Order:");
        
        while (completedCount < processes.size()) {
            while (processIndex < processes.size() && 
                   processes.get(processIndex).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.get(processIndex));
                processIndex++;
            }
            
            if (readyQueue.isEmpty()) {
                if (processIndex < processes.size()) {
                    currentTime = processes.get(processIndex).getArrivalTime();
                    continue;
                } else {
                    break;
                }
            }
            
            Process currentProcess = readyQueue.poll();
            int executeTime = Math.min(timeQuantum, currentProcess.getRemainingTime());
            
            int startTime = currentTime;
            int endTime = currentTime + executeTime;
            
            System.out.printf("Time %d-%d: Executing P%d", 
                startTime, endTime, currentProcess.getProcessId());
            
            if (currentProcess.getRemainingTime() == currentProcess.getBurstTime()) {
                System.out.print(" [Start]");
            }
            
            addGanttChartEntry(currentProcess.getProcessId(), startTime, endTime);
            currentTime = endTime;
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executeTime);
            
            if (currentProcess.getRemainingTime() == 0) {
                currentProcess.setCompletionTime(currentTime);
                completedCount++;
                System.out.println(" [Complete]");
            } else {
                System.out.println(" [Continue]");
                while (processIndex < processes.size() && 
                       processes.get(processIndex).getArrivalTime() <= currentTime) {
                    readyQueue.add(processes.get(processIndex));
                    processIndex++;
                }
                readyQueue.add(currentProcess);
            }
        }
        
        calculateMetrics();
    }
}
