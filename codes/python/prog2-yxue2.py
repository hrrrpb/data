#!/usr/bin/python3

import random, sys

#use system time for seed value
random.seed()

#create a list holding the lists w/ statistics
aList = []

# sample pool size from 100- 10000000
# each list will be initialized with following 12 numbers: pool size, counts of 2 to 12

count = 100
while (count<=10000000) :
    # initialize the temp list with the pool size ,and set the counts to 0    
    temp = [count] + [0]*11
   
    # calculate the total of random dices each round, and add the count to the corresponding count element
    for index in range(0, temp[0]) : 
        randomNumber = random.randint(1,6) + random.randint(1,6)
        temp[randomNumber-1] +=1

    aList.append(temp)
    count *= 10

#calculate statistics and append to each list
for temp in aList :
    for index in range(1,12) :
        observed = temp[index]/temp[0] 
        if (index +1 == 2 or index+1 ==12):
            ideal = 1/36
        elif (index+1==3 or index+1 ==11) :
            ideal = 2/36            
        elif (index+1 ==4 or index+1 ==10):
            ideal = 3/36
        elif (index+1 == 5 or index+1 ==9):
            ideal = 4/36
        elif (index+1== 6 or index+1==8):
            ideal= 5/36
        else:
            ideal = 6/36
        percent = (observed - ideal)/ideal * 100
        temp.append(percent)        

#output statistics row by row
#first row is for the sample pool size
# rest rows are for the statistics, note that real statistics are stored 11 slots away from its counts
print("\nThe final statistics:\n")
for row in range(0, 12) :
    if row==0:
        print("{:<6s}".format(" "), end="")
        for temp in aList:
            print("{:>10.0e}".format(temp[0]),end="")
        print("")  
    else:
        print("{:<6d}".format(row+1),end="")
        for temp in aList:
            print("{:>10.2f}".format(temp[row+11]),end="")
        print("")
            
#Evaluation

# statitics are stored from slot 12 to slot 22
print("\nNow evaluating the random number generator ....")
isGood = True;

for statIndex in range(12,23):
    for index in range(0, len(aList)-1):
        if abs(aList[index][statIndex])< abs(aList[index+1][statIndex]) and isGood :
             isGood = False
            #the following codes can output the outlier details
            #print("\nFinding ourlier:")
            #print("size "+str(aList[index][0])+ " : stats of " +str(statIndex-10) + " is " +"{:<10.2f}".format(aList[index][statIndex]))
            #print("size "+str(aList[index+1][0])+ " : stats of " +str(statIndex-10) + " is " +"{:<10.2f}".format(aList[index+1][statIndex]))
             


if isGood :
    print ("\nThe random number generator is good")
else :
    print ("\nThe random number generator is bad")

