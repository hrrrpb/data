#!/usr/local/bin/python3

import sqlite3
import sys
import xml.etree.ElementTree as et
import urllib.request as url


filename = sys.argv[1]

dbconn = sqlite3.connect(filename)
dbcurs = dbconn.cursor()

#only do this once or error happens when rerun program
dbcurs.execute("CREATE TABLE peopletable (firstname text, lastname text, street text, city text, id integer)")

urlquery = "http://thomas-bayer.com/sqlrest/CUSTOMER/"

page = url.urlopen(urlquery)

if page.getcode() == 200 :
    customersstring = page.read()
    customersstring = customersstring.decode("utf-8")

    customerdata = et.fromstring(customersstring)

    for customer in customerdata.findall('CUSTOMER') :
        urlquery2 = urlquery + customer.text + "/"

        page = url.urlopen(urlquery2)

        if page.getcode() == 200 :
             customersstring = page.read()
             customersstring = customersstring.decode("utf-8")

             customerdata = et.fromstring(customersstring)

             cid = customerdata.find("ID").text
             firstname = customerdata.find("FIRSTNAME").text
             lastname = customerdata.find("LASTNAME").text
             street = customerdata.find("STREET").text
             city = customerdata.find("CITY").text

             dbcurs.execute("INSERT INTO peopletable (firstname, lastname, street, city, id) VALUES (?, ?, ?, ?, ?)", (firstname, lastname, street, city, cid))
        else :
             print("Data for customer #", customer.text, " is not available")

else :
    print("Database website unavailable")

dbconn.commit()
dbconn.close()

