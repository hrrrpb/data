#!/usr/local/bin/python3

import sys
import urllib.request as url
import json

apicode = "b0d45b8cc18faf92d77e9825fd5aae74"

zipcode = sys.argv[1]

if len(zipcode) == 5 and zipcode.isdigit() :

    urlquery = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",us&mode=json&units=imperial&APPID="+apicode

    page = url.urlopen(urlquery)

    if page.getcode() == 200 :
        weatherstring = page.read()
        weatherstring = weatherstring.decode("utf-8")

        weatherdata = json.loads(weatherstring)
        #print(weatherdata)

        temperature = weatherdata['main']['temp']

        humidity = weatherdata['main']['humidity']

        windspeed = weatherdata['wind']['speed']

        winddir = weatherdata['wind']['deg']
        if winddir < 23 or winddir > 337:
            winddir = "north"
        elif winddir > 22 and winddir < 68 :
            winddir = "northeast"
        elif winddir > 67 and winddir < 113 :
            winddir = "east"
        elif winddir > 112 and winddir < 158 :
            winddir = "southeast"
        elif winddir > 157 and winddir < 203 :
            winddir = "south"
        elif winddir > 202 and winddir < 248 :
            winddir = "southwest"
        elif winddir > 247 and winddir < 293 :
            winddir = "west"
        elif winddir > 292 and winddir < 338 :
            winddir = "northwest"
   
        print("The tempeature is ", temperature, " degrees F")
        print("The humidity is ", humidity, "%")
        print("The wind is from the ", winddir, " at ", windspeed, " mph")
    else :
        print("No valid response from weather server")


else :
    print(zipcode, " is not a proper zipcode. Try again.")

