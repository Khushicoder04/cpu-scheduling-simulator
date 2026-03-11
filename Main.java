import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";
    
    public static void main(String[] args) {
        displayWelcomeScreen();
        
        boolean running = true;
        
        while (running) {
            List<Process> processes = getProcessesFromUser();
            
            if (processes.isEmpty()) {
                System.out.println(RED + "No processes entered. Exiting..." + RESET);
                break;
            }
            
            displayProcessList(processes);
            
            int choice = getAlgorithmChoice();
            executeAlgorithm(choice, processes);
            
            running = askToContinue();
        }
        
        displayGoodbyeScreen();
        scanner.close();
    }
    
    private static void displayWelcomeScreen() {
        clearScreen();
        System.out.println(BOLD + CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BOLD + WHITE + "                    CPU SCHEDULING SIMULATOR                 " + RESET + CYAN + "║" + RESET);
        System.out.println(BOLD + CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  Explore and compare different CPU scheduling algorithms   " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + WHITE + "  with interactive Gantt charts and performance metrics " + CYAN + "║" + RESET);
        System.out.println(BOLD + CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println(YELLOW + "✨ Features:" + RESET);
        System.out.println("   • First Come First Serve (FCFS)");
        System.out.println("   • Shortest Job First (SJF)");
        System.out.println("   • Round Robin Scheduling");
        System.out.println("   • Priority Scheduling");
        System.out.println("   • Interactive Gantt Charts");
        System.out.println("   • Performance Analysis");
        System.out.println();
    }
    
    private static void displayGoodbyeScreen() {
        System.out.println();
        System.out.println(BOLD + GREEN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + GREEN + "║" + RESET + WHITE + "                    THANK YOU FOR USING!                      " + RESET + GREEN + "║" + RESET);
        System.out.println(BOLD + GREEN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println(CYAN + "🎯 Hope this helped you understand CPU scheduling algorithms!" + RESET);
    }
    
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print new lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private static List<Process> getProcessesFromUser() {
        List<Process> processes = new ArrayList<>();
        
        System.out.println(BOLD + BLUE + "\n📊 PROCESS INPUT" + RESET);
        System.out.println(BLUE + "─".repeat(50) + RESET);
        System.out.print(YELLOW + "Enter the number of processes: " + RESET);
        int numProcesses;
        
        try {
            numProcesses = Integer.parseInt(scanner.nextLine());
            if (numProcesses <= 0) {
                System.out.println(RED + "⚠ Number of processes must be positive. Using sample data instead." + RESET);
                return createSampleProcesses();
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "⚠ Invalid input. Using sample data instead." + RESET);
            return createSampleProcesses();
        }
        
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println(BOLD + CYAN + "\n--- Process P" + i + " ---" + RESET);
            
            int arrivalTime = getValidInput("🕐 Enter Arrival Time: ", 0);
            int burstTime = getValidInput("⏱️  Enter Burst Time: ", 1);
            int priority = getValidInput("🎯 Enter Priority (lower number = higher priority): ", 1);
            
            processes.add(new Process(i, arrivalTime, burstTime, priority));
        }
        
        return processes;
    }
    
    private static int getValidInput(String prompt, int minValue) {
        int value;
        while (true) {
            try {
                System.out.print(YELLOW + prompt + RESET);
                value = Integer.parseInt(scanner.nextLine());
                if (value >= minValue) {
                    return value;
                } else {
                    System.out.println(RED + "⚠ Value must be >= " + minValue + ". Please try again." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "⚠ Invalid input. Please enter a valid number." + RESET);
            }
        }
    }
    
    private static int getAlgorithmChoice() {
        System.out.println(BOLD + BLUE + "\n🎛️  SELECT SCHEDULING ALGORITHM" + RESET);
        System.out.println(BLUE + "─".repeat(50) + RESET);
        System.out.println(GREEN + "1." + RESET + " First Come First Serve (FCFS)");
        System.out.println(GREEN + "2." + RESET + " Shortest Job First (SJF)");
        System.out.println(GREEN + "3." + RESET + " Round Robin");
        System.out.println(GREEN + "4." + RESET + " Priority Scheduling");
        System.out.println(GREEN + "5." + RESET + " 🔄 Run All Algorithms & Compare");
        
        while (true) {
            try {
                System.out.print(YELLOW + "Enter your choice (1-5): " + RESET);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 5) {
                    return choice;
                } else {
                    System.out.println(RED + "⚠ Invalid choice. Please enter a number between 1 and 5." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "⚠ Invalid input. Please enter a valid number." + RESET);
            }
        }
    }
    
    private static void executeAlgorithm(int choice, List<Process> originalProcesses) {
        System.out.println("\n" + "=".repeat(60));
        
        switch (choice) {
            case 1:
                runFCFS(originalProcesses);
                break;
            case 2:
                runSJF(originalProcesses);
                break;
            case 3:
                runRoundRobin(originalProcesses);
                break;
            case 4:
                runPriorityScheduling(originalProcesses);
                break;
            case 5:
                runAllAlgorithms(originalProcesses);
                break;
        }
        
        System.out.println("=".repeat(60));
    }
    
    private static void runFCFS(List<Process> originalProcesses) {
        FCFS fcfs = new FCFS();
        fcfs.addProcesses(new ArrayList<>(originalProcesses));
        fcfs.schedule();
        displayGanttChart(fcfs.getGanttChart(), "First Come First Serve (FCFS)");
        displayResultsTable(fcfs.getProcesses(), "First Come First Serve (FCFS)");
    }
    
    private static void runSJF(List<Process> originalProcesses) {
        SJF sjf = new SJF();
        sjf.addProcesses(new ArrayList<>(originalProcesses));
        sjf.schedule();
        displayGanttChart(sjf.getGanttChart(), "Shortest Job First (SJF)");
        displayResultsTable(sjf.getProcesses(), "Shortest Job First (SJF)");
    }
    
    private static void runRoundRobin(List<Process> originalProcesses) {
        int timeQuantum = getValidInput("Enter Time Quantum for Round Robin: ", 1);
        RoundRobin roundRobin = new RoundRobin(timeQuantum);
        roundRobin.addProcesses(new ArrayList<>(originalProcesses));
        roundRobin.schedule();
        displayGanttChart(roundRobin.getGanttChart(), "Round Robin (Quantum=" + timeQuantum + ")");
        displayResultsTable(roundRobin.getProcesses(), "Round Robin (Quantum=" + timeQuantum + ")");
    }
    
    private static void runPriorityScheduling(List<Process> originalProcesses) {
        PriorityScheduling priority = new PriorityScheduling();
        priority.addProcesses(new ArrayList<>(originalProcesses));
        priority.schedule();
        displayGanttChart(priority.getGanttChart(), "Priority Scheduling");
        displayResultsTable(priority.getProcesses(), "Priority Scheduling");
    }
    
    private static void runAllAlgorithms(List<Process> originalProcesses) {
        System.out.println(BOLD + YELLOW + "\n🔄 COMPARING ALL ALGORITHMS" + RESET);
        System.out.println(YELLOW + "─".repeat(60) + RESET);
        
        runFCFS(originalProcesses);
        System.out.println("\n");
        runSJF(originalProcesses);
        System.out.println("\n");
        runPriorityScheduling(originalProcesses);
        System.out.println("\n");
        runRoundRobin(originalProcesses);
        
        displayComparisonSummary(originalProcesses);
    }
    
    private static void displayComparisonSummary(List<Process> originalProcesses) {
        System.out.println(BOLD + CYAN + "\n📊 ALGORITHM COMPARISON SUMMARY" + RESET);
        System.out.println(CYAN + "─".repeat(60) + RESET);
        
        // Run all algorithms and collect metrics
        FCFS fcfs = new FCFS();
        fcfs.addProcesses(new ArrayList<>(originalProcesses));
        fcfs.schedule();
        
        SJF sjf = new SJF();
        sjf.addProcesses(new ArrayList<>(originalProcesses));
        sjf.schedule();
        
        PriorityScheduling priority = new PriorityScheduling();
        priority.addProcesses(new ArrayList<>(originalProcesses));
        priority.schedule();
        
        RoundRobin roundRobin = new RoundRobin(2);
        roundRobin.addProcesses(new ArrayList<>(originalProcesses));
        roundRobin.schedule();
        
        // Calculate averages
        double fcfsAvgWait = fcfs.getProcesses().stream().mapToDouble(Process::getWaitingTime).average().orElse(0);
        double sjfAvgWait = sjf.getProcesses().stream().mapToDouble(Process::getWaitingTime).average().orElse(0);
        double priorityAvgWait = priority.getProcesses().stream().mapToDouble(Process::getWaitingTime).average().orElse(0);
        double rrAvgWait = roundRobin.getProcesses().stream().mapToDouble(Process::getWaitingTime).average().orElse(0);
        
        double fcfsAvgTurn = fcfs.getProcesses().stream().mapToDouble(Process::getTurnaroundTime).average().orElse(0);
        double sjfAvgTurn = sjf.getProcesses().stream().mapToDouble(Process::getTurnaroundTime).average().orElse(0);
        double priorityAvgTurn = priority.getProcesses().stream().mapToDouble(Process::getTurnaroundTime).average().orElse(0);
        double rrAvgTurn = roundRobin.getProcesses().stream().mapToDouble(Process::getTurnaroundTime).average().orElse(0);
        
        // Display comparison table
        String header = String.format("| %-20s | %-12s | %-12s |", "Algorithm", "Avg Waiting", "Avg Turnaround");
        String separator = "+-" + "-".repeat(20) + "-+-" + "-".repeat(12) + "-+-" + "-".repeat(12) + "-+";
        
        System.out.println(BOLD + CYAN + separator + RESET);
        System.out.println(BOLD + CYAN + header + RESET);
        System.out.println(BOLD + CYAN + separator + RESET);
        
        System.out.printf("| %-20s | %-12.2f | %-12.2f |\n", "FCFS", fcfsAvgWait, fcfsAvgTurn);
        System.out.printf("| %-20s | %-12.2f | %-12.2f |\n", "SJF", sjfAvgWait, sjfAvgTurn);
        System.out.printf("| %-20s | %-12.2f | %-12.2f |\n", "Priority", priorityAvgWait, priorityAvgTurn);
        System.out.printf("| %-20s | %-12.2f | %-12.2f |\n", "Round Robin", rrAvgWait, rrAvgTurn);
        
        System.out.println(BOLD + CYAN + separator + RESET);
        
        // Find best algorithms
        String bestWait = getBestAlgorithm(fcfsAvgWait, sjfAvgWait, priorityAvgWait, rrAvgWait, 
            new String[]{"FCFS", "SJF", "Priority", "Round Robin"});
        String bestTurn = getBestAlgorithm(fcfsAvgTurn, sjfAvgTurn, priorityAvgTurn, rrAvgTurn, 
            new String[]{"FCFS", "SJF", "Priority", "Round Robin"});
        
        System.out.println(GREEN + "🏆 Best for Waiting Time: " + bestWait + RESET);
        System.out.println(GREEN + "🏆 Best for Turnaround Time: " + bestTurn + RESET);
        System.out.println();
    }
    
    private static String getBestAlgorithm(double fcfs, double sjf, double priority, double rr, String[] names) {
        double[] values = {fcfs, sjf, priority, rr};
        int bestIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[bestIndex]) {
                bestIndex = i;
            }
        }
        return names[bestIndex];
    }
    
    private static void displayProcessList(List<Process> processes) {
        System.out.println(BOLD + BLUE + "\n📋 PROCESS SUMMARY" + RESET);
        System.out.println(BLUE + "─".repeat(50) + RESET);
        
        String header = String.format("| %-8s | %-10s | %-8s | %-8s |", 
            "Process", "Arrival", "Burst", "Priority");
        String separator = "+-" + "-".repeat(8) + "-+-" + "-".repeat(10) + "-+-" + "-".repeat(8) + "-+-" + "-".repeat(8) + "-+";
        
        System.out.println(BOLD + CYAN + separator + RESET);
        System.out.println(BOLD + CYAN + header + RESET);
        System.out.println(BOLD + CYAN + separator + RESET);
        
        for (Process process : processes) {
            String row = String.format("| %-8s | %-10d | %-8d | %-8d |",
                "P" + process.getProcessId(),
                process.getArrivalTime(),
                process.getBurstTime(),
                process.getPriority());
            System.out.println(CYAN + row + RESET);
        }
        
        System.out.println(BOLD + CYAN + separator + RESET);
    }
    
    private static boolean askToContinue() {
        while (true) {
            System.out.print(YELLOW + "\n🔄 Do you want to run another simulation? (y/n): " + RESET);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println(RED + "⚠ Invalid input. Please enter 'y' or 'n'." + RESET);
            }
        }
    }
    
    private static void displayResultsTable(List<Process> processes, String algorithmName) {
        System.out.println(BOLD + GREEN + "\n📊 " + algorithmName.toUpperCase() + " RESULTS" + RESET);
        System.out.println(GREEN + "─".repeat(60) + RESET);
        
        String header = String.format("| %-8s | %-8s | %-8s | %-8s | %-10s |", 
            "Process", "Arrival", "Burst", "Waiting", "Turnaround");
        String separator = "+-" + "-".repeat(8) + "-+-" + "-".repeat(8) + "-+-" + "-".repeat(8) + "-+-" + "-".repeat(8) + "-+-" + "-".repeat(10) + "-+";
        
        System.out.println(BOLD + CYAN + separator + RESET);
        System.out.println(BOLD + CYAN + header + RESET);
        System.out.println(BOLD + CYAN + separator + RESET);
        
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        
        for (Process process : processes) {
            String row = String.format("| %-8s | %-8d | %-8d | %-8d | %-10d |",
                "P" + process.getProcessId(),
                process.getArrivalTime(),
                process.getBurstTime(),
                process.getWaitingTime(),
                process.getTurnaroundTime());
            
            // Color code based on waiting time
            if (process.getWaitingTime() == 0) {
                System.out.println(GREEN + row + RESET);
            } else if (process.getWaitingTime() <= 5) {
                System.out.println(YELLOW + row + RESET);
            } else {
                System.out.println(RED + row + RESET);
            }
            
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        
        System.out.println(BOLD + CYAN + separator + RESET);
        
        double avgWaiting = totalWaitingTime / processes.size();
        double avgTurnaround = totalTurnaroundTime / processes.size();
        
        System.out.println(BOLD + WHITE + "📈 Average Waiting Time: " + 
            String.format("%.2f", avgWaiting) + RESET);
        System.out.println(BOLD + WHITE + "📈 Average Turnaround Time: " + 
            String.format("%.2f", avgTurnaround) + RESET);
        System.out.println();
    }
    
    private static void displayGanttChart(List<Scheduler.GanttChartEntry> ganttChart, String algorithmName) {
        if (ganttChart.isEmpty()) {
            return;
        }
        
        System.out.println(BOLD + PURPLE + "\n📈 " + algorithmName.toUpperCase() + " GANTT CHART" + RESET);
        System.out.println(PURPLE + "─".repeat(60) + RESET);
        System.out.println();
        
        // Build the process bars
        StringBuilder processBars = new StringBuilder();
        StringBuilder timeBars = new StringBuilder();
        
        // Add first time point
        timeBars.append(String.format("%-4d", ganttChart.get(0).getStartTime()));
        
        for (Scheduler.GanttChartEntry entry : ganttChart) {
            String processLabel = "P" + entry.getProcessId();
            int duration = entry.getDuration();
            
            // Add process bar with appropriate width and color
            processBars.append("|");
            int spaces = Math.max(1, duration - 1);
            int leftPadding = spaces / 2;
            int rightPadding = spaces - leftPadding;
            
            // Choose color based on process ID
            String color = getColorForProcess(entry.getProcessId());
            
            for (int i = 0; i < leftPadding; i++) {
                processBars.append(" ");
            }
            processBars.append(color + processLabel + RESET);
            for (int i = 0; i < rightPadding; i++) {
                processBars.append(" ");
            }
            
            // Add time point
            timeBars.append(String.format("%-4d", entry.getEndTime()));
        }
        
        // Print the Gantt chart
        System.out.println(BOLD + processBars.toString() + RESET);
        System.out.println(BOLD + timeBars.toString() + RESET);
        System.out.println();
    }
    
    private static String getColorForProcess(int processId) {
        switch (processId % 6) {
            case 1: return RED;
            case 2: return GREEN;
            case 3: return YELLOW;
            case 4: return BLUE;
            case 5: return PURPLE;
            default: return CYAN;
        }
    }
    
    private static List<Process> createSampleProcesses() {
        List<Process> processes = new ArrayList<>();
        
        processes.add(new Process(1, 0, 6, 3));
        processes.add(new Process(2, 2, 8, 1));
        processes.add(new Process(3, 4, 3, 4));
        processes.add(new Process(4, 6, 4, 2));
        processes.add(new Process(5, 8, 2, 5));
        
        return processes;
    }
}
