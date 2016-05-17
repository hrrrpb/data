#!/usr/local/bin/python3

# include math library to do sqrt
import math

# read data into one big string
numberstring = input("Please enter a set of numbers separated by spaces: ")

# split string at spaces to make a list
numberlist = numberstring.split()

# create a corresponding list of integers. Start with empty list and append items
intlist = []

for item in numberlist :
    intlist.append(int(item))  # append string converted to integer onto end of list

# find min and max using builtin functions
print("The maximum value of the set of numbers is ", max(intlist))
print("The minimum value of the set of numbers is ", min(intlist))

# determine how many numbers are in list
N = len(intlist)

# compute total by summing numbers
total = 0

for value in intlist :
    total += value

# mean is simply the average value
mean = total / N
print("The mean of the set of numbers is ", mean)

# sort list for finding median and mode
intlist.sort()

# median is middle value at index N / 2
medianindex = N // 2
print("The median of the set of numbers is ", intlist[medianindex])

# initialize list to find all values that occur max number of times
mode = []
modecount = 0
index = 0

# loop through sorted list, skip over repeated value and only search in remaining part
while index < N :
    value = intlist[index]
    count = intlist[index:].count(intlist[index]) # count next value in rest of list

    if count == modecount :       # if matches biggest count so far, add to mode list
            mode.append(value)
    elif count > modecount :      # if biggest count yet, reset to be only mode in list
        mode = [ value ]
        modecount = count
    index += count	          # skip over other repeated items

# print mode, if all occur once there technically is no mode
if modecount == 1 :
    print("There is no mode in this set of numbers.")
else :
    print("The mode of the set of numbers is ", mode)

# compute standard deviation using standard formula
total = 0

for value in intlist :
    total += (mean - value) ** 2

standarddeviation = math.sqrt(total / N)   # call sqrt function in math module
print("The standard deviation of the set of numbers is ", standarddeviation)

