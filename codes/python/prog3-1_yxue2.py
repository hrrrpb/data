#!/usr/bin/python3

import random

#generator function for the phone records
def recordgenerator(count):
    characters = "abcdefghijklmnopqrstuvwxyz"
    digits ="1234567890"
    for i in range(0, count):
        firstname=""
        lastname=""
        phonenumber =""
        for k in range(0,4):
             firstname += random.choice(characters)
             lastname  += random.choice(characters)
        for k in range(0,7):
             phonenumber +=random.choice(digits)
        record = [firstname, lastname, phonenumber]
        yield record
        


filename = input("Please enter file name to save records : ")
numberofrecords =int(input("Please enter total number of records you want to generate : "))

recordfile = open(filename, "w")

for record in recordgenerator(numberofrecords):
    recordstring=" ".join(record)
    recordfile.write(recordstring+"\n")

recordfile.close()
print("Record file :  " + filename +" is created. ") 








