# divided the array into four equal parts, each to be summed by a separate thread.
import threading
import time
import random

# Assume this is your large array of numbers
numbers = [25, 26, 27, 28, 29, 30, 31, 32, 33, 34] * 2_000_000

total_sum = 0

# A function that sums up a portion of the array
def sum_numbers(start, end):
    global total_sum
    for i in range(start, end):
        local_sum = numbers[i]
        temp_sum = total_sum + local_sum
        time.sleep(0.000001)
        total_sum = temp_sum

# Splitting the work among four threads
length = len(numbers)
tenth = length // 10
threads = [
    threading.Thread(target=sum_numbers, args=(i * tenth, (i + 1) * tenth))
    for i in range(10)
]

for thread in threads:
  thread.start()

for thread in threads:
  thread.join()


actual_sum = sum(numbers)
if total_sum != actual_sum:
    print(f"Race condition detected: total_sum = {total_sum}, actual_sum = {actual_sum}")
else:
    print(f"No race condition detected: total_sum = {total_sum}")
