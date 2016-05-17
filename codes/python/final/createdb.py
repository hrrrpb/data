#!/usr/bin/python3

import sqlite3
import sys
import random

def parsedata(info) :
	data = []

	#placeholders for temp data
	statestring = ""
	temp = []

	for item in info.split() :
		
		item = item.strip()

		# if is digit, a state record is done, save to data
		if item.isdigit() :
			temp.append(statestring.strip())
			temp.append(int(item))
			data.append(temp)
			temp = []
			statestring = ""
		#if not, add to statestring
		else :
			statestring += item +" "

	return data


def generatedata(data) :
	newdata = []
	temp = []
	for item in data :
		temp.append(item[0])

		#make sure the candidates do not have same number of votes
		while True:
			vote1 = int(random.random() * item[1])
			vote2 = item[1] - vote1
			if vote1 != vote2 :
				break
		temp.append(vote1)
		temp.append(vote2)
		newdata.append(temp)
		temp = []

	return newdata




datastring = '''Alabama	9 Alaska	3 Arizona	11 Arkansas	6 California	55 Colorado	9 Connecticut	7 
Delaware	3 District of Columbia	3 Florida	29 Georgia	16 Hawaii	4 Idaho	4 Illinois	20 Indiana	11 Iowa	6 
Kansas	6 Kentucky	8 Louisiana	8 Maine	4 Maryland	10 Massachusetts	11 Michigan	16 Minnesota	10 
Mississippi	6 Missouri	10 Montana	3 Nebraska	5 Nevada	6 New Hampshire	4 New Jersey	14 New Mexico	5 
New York	29 North Carolina	15 North Dakota	3 Ohio	18 Oklahoma	7 Oregon	7 Pennsylvania	20 Rhode Island	4 
South Carolina	9 South Dakota	3 Tennessee	11 Texas	38 Utah	6 Vermont	3 Virginia	13 Washington	12 
West Virginia	5 Wisconsin	10 Wyoming	3'''

data = parsedata(datastring)

data2 = generatedata(data)


dbconn = sqlite3.connect("info.db")
dbcurs = dbconn.cursor()

#create the first table
dbcurs.execute("CREATE TABLE statevotes (state text, votes integer)")
dbcurs.executemany("INSERT INTO statevotes (state, votes) VALUES (?, ?)", data)
dbconn.commit()
print("Table 1 of states and numbers of votes has been created.")

#create the second table
dbcurs.execute("CREATE TABLE statecands (state text, cand1 integer, cand2 integer)")
dbcurs.executemany("INSERT INTO statecands (state, cand1, cand2) VALUES (?, ?, ?)", data2)
dbconn.commit()
print("Table 2 of states and numbers of votes for two candidates has been created.")



dbconn.close()

