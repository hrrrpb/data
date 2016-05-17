#!/usr/local/bin/python3

filename = input("What is the password file name? ")
oldaccount = input("What is the old account name? ")
newaccount = input("What is the new account name? ")

textfile = open(filename, "r")
text = textfile.readlines()
textfile.close()

textfile = open(filename, "w")

for line in text :
   accountdata = line.split(":")
   if oldaccount == accountdata[0] :
       accountdata[0] = newaccount
       if accountdata[5].endswith(oldaccount) :
            accountdata[5] = accountdata[5].replace(oldaccount, newaccount)
   newdata = ":".join(accountdata)
   textfile.write(newdata)

textfile.close()


