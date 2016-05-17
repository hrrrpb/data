#!/usr/local/bin/python3

import sqlite3
import sys


filename = sys.argv[1]
adultage = sys.argv[2]

dbconn = sqlite3.connect(filename)
dbcurs = dbconn.cursor()

#dbcurs.execute("CREATE TABLE peopletable (firstname text, lastname text, age integer)")

#people = [["Fred", "Flinstone", 42], ["Wilma", "Flintstone", 39], ["Barney", "Rubble", 35], ["Betty", "Rubble", 36], ["BamBam", "Rubble", 5], ["Pebbles", "Flintstone", 4], ["Dino", "Flintstone", 3]]

#dbcurs.executemany("INSERT INTO peopletable (firstname, lastname, age) VALUES (?, ?, ?)", people)

people = dbcurs.execute("SELECT * FROM peopletable WHERE age > ? ORDER BY lastname, firstname", (adultage,))

for person in people:
    print(person[1], ", ", person[0], " is ", person[2], " years old")

dbconn.commit()
dbconn.close()

