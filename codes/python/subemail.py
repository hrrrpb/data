#!/usr/local/bin/python3

import sys
import re
import os

filename = sys.argv[1]

if os.path.exists(filename) and os.path.isfile(filename) :

    textfile = open(filename, "r")
    textlines = textfile.readlines()
    textfile.close()

    reobject = re.compile(r">([a-zA-Z][-_.a-zA-Z0-9]*@([a-zA-Z0-9][-_a-zA-Z0-9]*\.)+[a-zA-Z]{2,})<")
    
    for line in textlines :
#       matches = reobject.search(line)   # only finds one as match object
#       matches = reobject.findall(line)  # find all in string form
#       if matches :
#           for match in matches :
#               print(match[0])

        newline = reobject.sub("emailaddress",line) #fixed text replacement
        print(line)
        print(newline)
else :
    print(filename, " is not valid file name.")

