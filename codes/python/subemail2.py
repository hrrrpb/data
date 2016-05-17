#!/usr/local/bin/python3

import sys
import re
import os
import urllib.request as urlr

def emailsub (matchobject) :

    oldemail = matchobject.group(0)
    oldemail = oldemail.replace("@", " AT ")
    oldemail = oldemail.replace(".", " PERIOD ")
    oldemail = oldemail.replace("-", " HYPHEN ")
    oldemail = oldemail.replace("_", " UNDERSCORE ")

    return oldemail

url = sys.argv[1]

urlobject = urlr.urlopen(url)

if urlobject.getcode() == 200 :

    textlines = urlobject.read()
    textlines = textlines.decode("utf-8")

    reobject = re.compile(r">([a-zA-Z][-_.a-zA-Z0-9]*@([a-zA-Z0-9][-_a-zA-Z0-9]*\.)+[a-zA-Z]{2,})<")
    
    textfile = open("emailpage.html", "wb")

    newtext = reobject.sub(emailsub,textlines) #fixed text replacement
    newtext = newtext.encode("utf-8")
    textfile.write(newtext)
    textfile.close()
else :
    print(url, " is not a valid web page.")

