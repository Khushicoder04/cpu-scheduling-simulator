# CPU Scheduling Simulator

A comprehensive, professional-grade Java application that simulates and compares various CPU scheduling algorithms with an intuitive console interface, visual Gantt charts, and detailed performance analysis.

## 🚀 Features

### Core Scheduling Algorithms
- **First Come First Serve (FCFS)** - Non-preemptive scheduling based on arrival order
- **Shortest Job First (SJF)** - Non-preemptive scheduling based on burst time
- **Round Robin** - Preemptive scheduling with configurable time quantum
- **Priority Scheduling** - Non-preemptive scheduling based on priority levels

### Advanced Features
- **Interactive Menu-Driven Interface** - User-friendly console interface with colored output
- **Dynamic Gantt Charts** - Visual representation of process execution timeline
- **Performance Analysis** - Comprehensive metrics including waiting time and turnaround time
- **Algorithm Comparison** - Side-by-side comparison of all algorithms with best performer highlighting
- **Data Structure Implementation** - Uses Queue, ArrayList, and proper OOP design patterns
- **Input Validation** - Robust error handling and user input validation
- **Professional Visualization** - Colored output, formatted tables, and Unicode graphics

## 📊 Performance Metrics

The simulator calculates and displays:
- **Waiting Time** - Time processes spend waiting in the ready queue
- **Turnaround Time** - Total time from arrival to completion
- **Completion Time** - When each process finishes execution
- **Average Metrics** - System-wide performance averages
- **Algorithm Rankings** - Automatic identification of best performing algorithm

## 🏗️ Project Structure

```
cpu-scheduling-simulator/
├── Main.java              # Main application with menu interface
├── Process.java           # Process entity with attributes and methods
├── Scheduler.java         # Abstract base class for scheduling algorithms
├── FCFS.java             # First Come First Serve implementation
├── SJF.java              # Shortest Job First implementation
├── RoundRobin.java       # Round Robin implementation
├── PriorityScheduling.java # Priority Scheduling implementation
└── README.md             # This documentation file
```

## 🛠️ Technical Implementation

### Object-Oriented Design
- **Encapsulation**: Private fields with controlled access through methods
- **Inheritance**: Abstract Scheduler class with concrete algorithm implementations
- **Polymorphism**: Dynamic method dispatch for different scheduling algorithms
- **Composition**: GanttChartEntry as inner class for visualization data

### Data Structures Used
- **ArrayList**: Dynamic process collection management
- **Queue**: FIFO process management for FCFS and Round Robin
- **PriorityQueue**: Efficient selection based on priority or burst time (conceptual)

### Design Patterns
- **Template Method**: Abstract schedule() method with algorithm-specific implementations
- **Strategy Pattern**: Different scheduling algorithms as interchangeable strategies
- **Factory Pattern**: Algorithm selection and instantiation

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt with ANSI color support (for best experience)

### Installation
1. Clone or download the project files
2. Navigate to the project directory
3. Compile all Java files:
   ```bash
   javac *.java
   ```

### Running the Application
```bash
java Main
```

## 📖 Usage Guide

### Main Menu Options
1. **First Come First Serve (FCFS)** - Run FCFS algorithm
2. **Shortest Job First (SJF)** - Run SJF algorithm  
3. **Round Robin** - Run Round Robin with custom time quantum
4. **Priority Scheduling** - Run Priority-based scheduling
5. **Run All Algorithms & Compare** - Comprehensive comparison of all algorithms

### Input Process Information
- **Number of Processes**: Enter how many processes to simulate
- **Arrival Time**: When each process arrives (≥ 0)
- **Burst Time**: CPU time required (> 0)
- **Priority**: Priority level (lower number = higher priority)

### Understanding the Output

#### Process Summary Table
```
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1      |    0    |   6   |    3     | 
| P2      |    2    |   8   |    1     |
```

#### Results Table
```
| Process | Arrival | Burst | Waiting | Turnaround |
|---------|---------|-------|---------|------------|
| P1      |    0    |   6   |    0    |     6      |
| P2      |    2    |   8   |    6    |    14      |
```

#### Gantt Chart
```
|P1     |P2     |P3     |
0    6    14    17    20
```

#### Algorithm Comparison
```
+----------------------+-------------+--------------+
| Algorithm           | Avg Waiting | Avg Turnaround |
+----------------------+-------------+--------------+
| FCFS                |      7.20   |        12.40 |
| SJF                 |      2.80   |         8.00 |
| Priority            |      3.60   |         8.80 |
| Round Robin         |      5.60   |        10.80 |
+----------------------+-------------+--------------+
🏆 Best for Waiting Time: SJF
🏆 Best for Turnaround Time: SJF
```

## 🎯 Learning Objectives

This project demonstrates proficiency in:
- **Object-Oriented Programming** - Classes, inheritance, polymorphism
- **Data Structures** - ArrayList, Queue, and algorithmic thinking
- **Algorithm Design** - Implementation of classic scheduling algorithms
- **Software Engineering** - Clean code, documentation, and maintainable design
- **User Interface Design** - Console applications with enhanced UX
- **Performance Analysis** - Metrics calculation and comparison
- **Problem Solving** - Real-world OS scheduling concepts

## 🔧 Customization and Extension

### Adding New Algorithms
1. Extend the `Scheduler` abstract class
2. Implement the `schedule()` method
3. Add the new algorithm to the main menu
4. Update comparison logic

### Example Algorithm Structure
```java
public class CustomAlgorithm extends Scheduler {
    public CustomAlgorithm() {
        super("Custom Algorithm");
    }
    
    @Override
    public void schedule() {
        // Reset and sort processes
        resetProcesses();
        sortProcessesByArrivalTime();
        
        // Implement custom scheduling logic
        // Add Gantt chart entries
        // Calculate completion times
        
        // Calculate final metrics
        calculateMetrics();
    }
}
```

## 📈 Performance Characteristics

### Algorithm Trade-offs
- **FCFS**: Simple, fair, but can have high waiting times (convoy effect)
- **SJF**: Optimal for minimum waiting time, but can cause starvation
- **Round Robin**: Fair response time, but higher context switching overhead
- **Priority**: Good for priority-based systems, but can starve low-priority processes

### Complexity Analysis
- **FCFS**: O(n log n) for sorting, O(n) for scheduling
- **SJF**: O(n²) worst case for job selection
- **Round Robin**: O(n × quantum) for complete execution
- **Priority**: O(n log n) with priority queue implementation

## 🐛 Troubleshooting

### Common Issues
1. **Colors not displaying**: Ensure terminal supports ANSI escape codes
2. **Compilation errors**: Check Java version (JDK 8+ required)
3. **Input validation**: Follow on-screen prompts for valid input ranges

### Platform-Specific Notes
- **Windows**: Use Command Prompt or PowerShell with ANSI support
- **Linux/macOS**: Native ANSI support in most terminals
- **IDE Consoles**: May have limited color support

## 📚 Educational Value

This simulator is ideal for learning:
- Operating Systems concepts and CPU scheduling
- Algorithm analysis and comparison
- Object-oriented design principles
- Data structure applications
- Software development best practices

## 🤝 Contributing

Feel free to:
- Report bugs or issues
- Suggest improvements
- Add new scheduling algorithms
- Enhance the user interface
- Improve documentation

## 📄 License

This project is provided for educational purposes. Feel free to use, modify, and distribute for learning and teaching.

## 🎓 Resume Highlights

**Key Technical Skills Demonstrated:**
- **Java Programming**: Advanced OOP, exception handling, collections framework
- **Algorithm Implementation**: Classic OS scheduling algorithms
- **Data Structures**: ArrayList, Queue, and efficient data management
- **Software Design**: Abstract classes, inheritance, polymorphism
- **User Experience**: Console applications with enhanced interfaces
- **Performance Analysis**: Metrics calculation and algorithm comparison
- **Problem Solving**: Real-world system simulation and optimization

**Project Features:**
- Complete, production-ready codebase
- Comprehensive documentation and comments
- Robust error handling and validation
- Professional user interface design
- Extensible and maintainable architecture
- Performance analysis and comparison tools

---

**Author**: CPU Scheduling Simulator Team  
**Version**: 1.0  
**Last Updated**: 2024
