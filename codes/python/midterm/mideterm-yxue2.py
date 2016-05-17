#!/usr/bin/python3

import sys

#retreive the info from file in the form of a dictionary
def readPolls(filename) :
	pollfile = open(filename, "r")
	lines = pollfile.readlines()
	
	pollfile.close()
	records = {}

	for line in lines:
		
		firstname = line.split(" ")[0]
		lastname = line.split(" ")[1]
		poll = line.split(" ")[2].strip()
		if poll =="1" or poll == "2":
			name = firstname +" " + lastname
			records[name] = poll

		else:
			print("Wrong poll info, skip the record:", firstname, lastname, poll)

	return records

#retrieve the count info from dictionary
def getCount(totalPolls):
	countOfA = 0
	countOfB = 0
	for key in totalPolls.keys():
		if totalPolls[key] == "1" :
			countOfA += 1
		else:
			countOfB += 1
	return (countOfA, countOfB)
    
#retrive the list for particular poll
def getList(totalPolls, poll):
	names = []
	
	for key in totalPolls.keys():
		if totalPolls[key] == poll :
			names.append(key)
	names.sort(key=sortKey)
	return names

#sortkey
def sortKey(item):
	return item.split(" ")[1]

files = []
if len(sys.argv) <=1 :
	printf("You should provide at least one file to process, abort")
	sys.exit(0)
else:
	for i in range(1,len(sys.argv)) :
		files.append(sys.argv[i])


#the dictionary for the polls
totalPolls = {}

#a set for already deleted names, to prevent further addition from other files
delete = set()

#iterate through all files, and add info to the total polls
for file in files:
	tempPolls = readPolls(file)
	for key in tempPolls.keys() :
		
		if key not in totalPolls and key not in delete :
			
			totalPolls[key] = tempPolls[key]
			
		else :
			if totalPolls[key] != tempPolls[key] :
				print("Inconsistent data found, delete the record")
				del totalPolls[key]
				delete.add(key)



print ("Count of poll for 1 :", getCount(totalPolls)[0])
print ("Voted for 1 list: ", getList(totalPolls, "1"))
print ("Count of poll for 2 :" , getCount(totalPolls)[0])
print ("Voted for 2 list: ", getList(totalPolls, "2"))







