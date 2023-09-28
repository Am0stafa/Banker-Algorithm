# A deadlock typically occurs in a multithreaded environment when two or more threads are blocked forever, each waiting for the other to release a lock.

# wo threads, thread_1 and thread_2, are created. thread_1 attempts to acquire lock1 and then lock2, while thread_2 attempts to acquire lock2 and then lock1.

import threading

# Define the locks
lock1 = threading.Lock()
lock2 = threading.Lock()

def thread_1():
  with lock1:
    print("Thread 1 acquired lock1")
    with lock2:
        print("Thread 1 acquired lock2")

def thread_2():
  with lock2:
    print("Thread 2 acquired lock2")
    with lock1:
        print("Thread 2 acquired lock1")

# Create the threads
t1 = threading.Thread(target=thread_1)
t2 = threading.Thread(target=thread_2)

# Start the threads
t1.start()
t2.start()

# Wait for both threads to finish
t1.join()
t2.join()
