# DeadLock Avoidance

### 1) Banker's Algorithm Simulator

## Overview
The Banker's Algorithm is a renowned resource allocation and deadlock avoidance algorithm used in operating system design to prevent deadlock by evaluating resource requests against available resources. This simulator aims to demonstrate the Banker's Algorithm with support for `N` processes and `M` resource types (with a constraint of `N < 10` and `M < 10`).

## File Structure

The input to this simulator is a text file that represents the initial system state. It is structured as:

```
<number_of_processes>
<number_of_resources>
| max | |alloc.| |ttl res
<max values, alloc values, total resources>
...

```

Example:

```
5  # processes
4  # resources
3,2,2,2,2,1,1,0,5,6,8,4
2,1,1,2,0,1,1,0,5,6,8,4
... 
```

Where:
- `number_of_processes` specifies the number of processes.
- `number_of_resources` specifies the number of resource types.
- Each subsequent line represents the data for a process. The data includes:
  - The maximum demand of resources for the process.
  - The currently allocated resources to the process.
  - The total resources available in the system.

## How the Code Works

1. **SetupFile Method**: 
   - Reads the input file to initialize various arrays and matrices such as `max`, `allocate`, `need`, `allocatedResources`, and `ttlResources`.
   - Calculates the initial available resources by subtracting the allocated resources from the total resources.

2. **CheckSafeSeq Method**: 
   - Checks for the existence of a safe sequence by verifying if `need[i] <= initial[i]` for any process.

3. **PrintMatrix Method**: 
   - Displays the system matrices (`max`, `allocate`, `need`) and the available resources.

4. **Check Method**: 
   - Checks if the needs of a particular process can be satisfied by the currently available resources. It also keeps track of multiple solutions.

5. **Algorithm Method**: 
   - Executes the Banker's Algorithm:
     - It releases resources held by a process if the process's needs can be satisfied.
     - Updates the available resources.
     - Tracks the processes in the safe sequence.

6. **Main Method**: 
   - Sets up the file, checks if a safe sequence exists, and prints the system matrix. The Banker's Algorithm is then executed to find potential safe sequences.

## Execution Steps

1. Run the program.
2. When prompted, input the filename (without `.txt` extension) containing the system state.
3. The program will read the file, display the system matrices, and find potential safe sequences.
4. If a safe sequence exists, it will be displayed. If not, the program will output "There is no safe Sequence!"

## Testing

Three sample text files have been provided for testing purposes. These can be used to check various scenarios of the Banker's Algorithm. Make sure to specify the correct path when prompted for the filename.

## Conclusion

This Banker's Algorithm simulator effectively visualizes the algorithm's process of determining safe sequences, giving the user insights into how resource allocation and deadlock avoidance operate in an OS environment.


---

### 2) Deadlock Simulator in Python

This simulator demonstrates a typical deadlock scenario in a multithreaded environment. A deadlock arises when two or more threads are permanently blocked, each waiting indefinitely for the other to release a lock.

In this simulation, two threads, `thread_1` and `thread_2`, are created. The `thread_1` attempts to acquire `lock1` and then `lock2`, while `thread_2` attempts to acquire `lock2` and then `lock1`. This leads to a situation where each thread is waiting for the other to release a lock, resulting in a deadlock.

## How to Run

1. Make sure you have Python installed on your system.
2. Get into the directory by `cd Deadlock-Avoidance`.
3. Run the code by `simulation.py`.

## Expected Output

You might observe the following output, although the exact sequence might vary:

```
Thread 1 acquired lock1
Thread 2 acquired lock2
```

After this, both threads will be in a deadlock situation, waiting for each other to release their respective locks.

## Caution

This is a simple simulator to demonstrate a deadlock. It's important to understand the implications and the challenges of multithreaded environments in real-world applications. Deadlocks can lead to unresponsive systems and are often hard to diagnose and resolve.

---

### 3) Race Condition Simulator

This Python-based simulator is designed to demonstrate race conditions, a type of concurrency bug that can occur when multiple threads attempt to access and modify shared resources without proper synchronization. In the provided `simulation1.py`, an array of numbers is divided into ten parts, and each part is summed up by a separate thread. Due to a lack of synchronization mechanisms, there's a chance that race conditions will occur, leading to an incorrect total sum.

### How it Works

- **Array of Numbers**: The program initializes a large array of numbers named `numbers`.

- **Global Total Sum**: There's a shared global variable `total_sum` which holds the sum of the numbers and will be updated by all threads.

- **`sum_numbers` Function**: Each thread will execute this function, which is responsible for summing a slice of the array. Due to the deliberate introduction of a small delay using `time.sleep()`, race conditions are more likely to occur.

- **Thread Creation and Execution**: The array is divided into ten slices, and ten threads are created to process these slices. Once started, each thread will attempt to update the `total_sum` without synchronization.

- **Checking for Race Condition**: After all threads have completed their execution, the program will compute the actual sum of the numbers in the array and compare it to the `total_sum`. If they don't match, a race condition has been detected.

### Note

This simulator is a simplified representation of how race conditions can occur in multi-threaded environments. It's essential to recognize that in real-world scenarios, race conditions might be less predictable and could result in various issues, including data corruption and application crashes.

---

### 4) Resource Allocation Graph (RAG)

This project aims to create a Resource Allocation Graph (RAG) to keep track of resources and processes in a system, and to visualize this graph along with detecting cycles which could indicate potential deadlocks.

## Overview

Resource Allocation Graph (RAG) is a data structure that represents processes, resources, and the relationships between them to prevent and avoid deadlock situations. This implementation allows you to:

- Define processes with unique names and priorities.
- Define resources with unique names and available instances.
- Allocate specific instances of resources to processes.
- Release instances of resources from processes.
- Visualize the Resource Allocation Graph.

## Features

1. **Easy-to-Use Command-Line Interface:** Define processes, resources, allocations, and releases via command-line arguments.
2. **Visualization Support:** Plot the RAG with process and resource nodes using `networkx` and `matplotlib`.
3. **Deadlock Detection:** Upon visualizing the graph, the system will detect and notify if a cycle (potential deadlock) exists.

## Usage

The script can be executed from the command line using the following format:

```bash
python rag.py --processes [ProcessName:Priority ...] --resources [ResourceName:Instances ...] --allocate [ProcessName:ResourceName:Instances ...] --release [ProcessName:ResourceName:Instances ...]
```

## How the Code Works

1. **Data Classes**:
    - `Process` Class:
        - Holds information about a process including its name, priority, allocated resources, and requested resources.
    - `Resource` Class:
        - Holds information about a resource including its name, the number of available instances, and allocations to processes.

2. **Resource Allocation Graph (RAG) Class**:
    - `ResourceAllocationGraph` Class:
        - Manages the collection of `Process` and `Resource` objects.
        - Provides methods to add processes and resources, allocate and release resources, and visualize the graph.

3. **Resource and Process Management**:
    - `add_process` Method:
        - Adds a new process to the RAG with a specified name and priority.
    - `add_resource` Method:
        - Adds a new resource to the RAG with a specified name and number of instances.
    - `allocate` Method:
        - Allocates a specified number of instances of a resource to a process.
    - `release` Method:
        - Releases a specified number of instances of a resource from a process back to the resource pool.

4. **Graph Visualization**:
    - `visualize` Method:
        - Uses NetworkX and Matplotlib to create a visual representation of the RAG.
        - Processes are represented as red nodes, resources as blue nodes, and allocations as edges with labels indicating the number of allocated instances.
        - Checks for cycles in the graph to detect potential deadlocks and prints the result to the console.

5. **User Input Handling**:
    - The `argparse` library is used to define command-line arguments for specifying processes, resources, allocation requests, and release requests.
    - Users can input data through command-line arguments when launching the script.

6. **Main Function**:
    - Parses command-line arguments using `argparse`.
    - Initializes a `ResourceAllocationGraph` object.
    - Processes user input to add processes and resources, and handle allocation and release requests.
    - Calls the `visualize` method to display the graph and indicate whether a cycle is detected.

7. **Cycle Detection**:
    - Within the `visualize` method, the `nx.is_directed_acyclic_graph()` function from NetworkX is used to check for cycles in the graph, which would indicate potential deadlocks.

8. **Command-Line Interface**:
    - Users can interact with the script through command-line arguments to specify processes, resources, allocation requests, and release requests.

9. **Error Handling**:
    - Comprehensive error handling is provided to manage scenarios like invalid input, insufficient resource instances, non-existing processes or resources, etc.

10. **Execution**:
    - When the script is run, it processes the command-line arguments, manages the resources and processes as per user input, visualizes the RAG, and checks for cycles to detect potential deadlocks.


### Parameters

- `--processes`: List of processes and their priorities. Format: `ProcessName:Priority`.
- `--resources`: List of resources and their available instances. Format: `ResourceName:Instances`.
- `--allocate`: Allocation requests specifying which process requests which resource and how many instances. Format: `ProcessName:ResourceName:Instances`.
- `--release`: Resource release requests specifying which process releases which resource and how many instances. Format: `ProcessName:ResourceName:Instances`.

### Example

```bash
python rag.py --processes P1:1 P2:2 --resources R1:3 R2:4 --allocate P1:R1:2 P2:R2:3 --release P1:R1:1
```

## Implementation Details

- `Process`: Represents a process with a name, priority, allocated resources, and requested resources.
- `Resource`: Represents a resource with a name, total instances, and allocated instances to processes.
- `ResourceAllocationGraph`: Main class to manage processes, resources, allocations, and visualizations.


---