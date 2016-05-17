#!/usr/local/bin/python3

import sqlite3
import sys


filename = sys.argv[1]
querycity = sys.argv[2]

dbconn = sqlite3.connect(filename)
dbcurs = dbconn.cursor()

#people = dbcurs.execute("SELECT * FROM peopletable")
people = dbcurs.execute("SELECT * FROM peopletable WHERE city == ? ORDER BY lastname, firstname", (querycity,))

for person in people:
    print( "ID#", person[4], " - ", person[1], ", ", person[0], " lives at ", person[2], " , ", person[3])

dbconn.commit()
dbconn.close()

