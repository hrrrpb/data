#!/usr/bin/python3
import time


# retrieve the phone records list from file
def retrieverecords(filename):
    recordfile = open(filename,"r")
    lines = recordfile.readlines()
    phonerecords =[]
    for line in lines :
        phonerecords.append(line.strip().split(" "))
    return phonerecords

def sortkey(item) :
    return item[1]+","+item[0]
    

#binary search for sorted list
def bsearch(sortedlist, searchname) :
    first = 0; 
    last = len(sortedlist)-1
    
    while first<=last: 
        mid = (first+last)//2
        if sortkey(sortedlist[mid]) == searchname :
            return sortedlist[mid][2]
        else:
           if searchname< sortkey(sortedlist[mid]) :
               last=mid-1
           else:
               first = mid +1 
 
    return None

#timing ourput for list
def timinglist(unsortedlist) :
    #timing the sorting
    starttime = time.time()
    sortedlist = sorted(unsortedlist, key=sortkey)
    stoptime = time.time()
    elapsetime = (stoptime-starttime)*1000
    print ("list sorting time in ms :" + str(elapsetime))
    
    #timing the search
    starttime = time.time()
    for item in unsortedlist :
        bsearch(sortedlist, sortkey(item))
    stoptime = time.time()
    elapsetime = (stoptime - starttime) *  1000
    print ("binary search time in ms: " + str(elapsetime))




def createdictionary(unsortedlist):
   listdic = {}
   for item in unsortedlist: 
      listdic[sortkey(item)] = item[2]
   return listdic

def timingdictionary(unsortedlist):
   #timing the dic creation
   starttime = time.time()
   listdic = createdictionary(unsortedlist)
   stoptime = time.time()
   elapsetime = (stoptime - starttime)*1000
   print("dictionary creation time in ms : " + str(elapsetime))

   #timing the dic searching
   starttime = time.time()
   for item in unsortedlist :
       listdic.get(sortkey(item))
   stoptime = time.time()
   elapsetime = (stoptime - starttime) *  1000
   print ("dictionary search time in ms : " + str(elapsetime))

   


# read in all files and output the timing data

filenames =["100.txt","1000.txt","10000.txt","100000.txt","1000000.txt","10000000.txt"]
for filename in filenames:
    print("Timing data for " + filename)
    phonerecords = retrieverecords(filename)
    timinglist(phonerecords)
    print()
    timingdictionary(phonerecords)
    print("-----------------------")







