#!/usr/local/bin/python3

import sys
import urllib.request as url
import xml.etree.ElementTree as et

apicode = "b0d45b8cc18faf92d77e9825fd5aae74"

zipcode = sys.argv[1]

if len(zipcode) == 5 and zipcode.isdigit() :

    urlquery = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",us&mode=xml&units=imperial&APPID="+apicode

    page = url.urlopen(urlquery)

    if page.getcode() == 200 :
        weatherstring = page.read()
        weatherstring = weatherstring.decode("utf-8")

        weatherdata = et.fromstring(weatherstring)

    #    print(weatherdata.tag)
    #    print(weatherdata.attrib)

        temperature = weatherdata[1].attrib['value']
        temperature = weatherdata.find('temperature').attrib['value']

        humidity = weatherdata[2].attrib['value']
        humidity = weatherdata.find('humidity').attrib['value']

        windspeed = weatherdata[4][0].attrib['value']
        windspeed = weatherdata.find('wind').find('speed').attrib['value']

        winddir = weatherdata[4][2].attrib['name']
        winddir = weatherdata.find('wind').find('direction').attrib['name']

        print("The tempeature is ", temperature, " degrees F")
        print("The humidity is ", humidity, "%")
        print("The wind is from the ", winddir, " at ", windspeed, " mph")
    else :
        print("No valid response from weather server")


else :
    print(zipcode, " is not a proper zipcode. Try again.")

