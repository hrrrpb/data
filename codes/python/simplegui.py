#!/usr/local/bin/python3

import tkinter
import tkinter.messagebox

import urllib.request as url
import urllib.parse as urlp
import json

apicode = "b0d45b8cc18faf92d77e9825fd5aae74"

def getweatherreport():

    urlquery = "http://api.openweathermap.org/data/2.5/weather?q="+citytext.get()+","+statetext.get()+"&mode=json&units=imperial&APPID="+apicode

    urlquery = urlquery.replace(" ", "%20")

    page = url.urlopen(urlquery)

    if page.getcode() == 200 :
        weatherstring = page.read()
        weatherstring = weatherstring.decode("utf-8")

        weatherdata = json.loads(weatherstring)

        temperature = weatherdata['main']['temp']
     
        humidity = weatherdata['main']['humidity']

        windspeed = weatherdata['wind']['speed']

        if 'deg' in weatherdata['wind'] :
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
        else :
            winddir = "the center of the universe"
   
        outputstring="The temperature is "+str(temperature)+"degrees F\n"
        outputstring+="The humidity is "+str(humidity)+"%\n"
        outputstring+="The wind is from the "+winddir+" at "+str(windspeed)+" mph"
#        tkinter.messagebox.showinfo("Weather Report", outputstring)
        outputtext.set(outputstring)

        print("The temperature is ", temperature, " degrees F")
        print("The humidity is ", humidity, "%")
        print("The wind is from the ", winddir, " at ", windspeed, " mph")
    else :
        print("No valid response from weather server")


rootwindow = tkinter.Tk()
rootwindow.title("My Title")
rootwindow.config(width=500, height=400, bg="purple")
rootwindow.lift()
rootwindow.call('wm', 'attributes', '.', '-topmost', True)
rootwindow.after_idle(rootwindow.call, 'wm', 'attributes', '.', '-topmost', False)

firstlabel = tkinter.Label(rootwindow, text="Please enter the city and state \nyou wish to get a weather report about.", font="Ariel 24", foreground="blue", background="light grey")
firstlabel.grid(row=0,column=0)
firstlabel = tkinter.Label(rootwindow, text="City: ", font="Ariel 24", foreground="blue", background="light grey")
firstlabel.grid(row=1,column=0)
firstlabel = tkinter.Label(rootwindow, text="State: ", font="Ariel 24", foreground="blue", background="light grey")
firstlabel.grid(row=2,column=0)

citytext = tkinter.StringVar()
cityentry = tkinter.Entry(rootwindow, width=20, foreground="red", background="yellow", textvariable=citytext)
cityentry.focus_set()
cityentry.grid(row=1, column=1)

statetext = tkinter.StringVar()
stateentry = tkinter.Entry(rootwindow, width=20, foreground="red", background="yellow", textvariable=statetext)
stateentry.grid(row=2, column=1)

submitbutton = tkinter.Button(rootwindow, height=1, text="Submit", font="Times 54", foreground="orange", background="pink", command=getweatherreport)
submitbutton.grid(row=3, column=1)

outputtext = tkinter.StringVar()
outputmessage = tkinter.Message(rootwindow, textvariable=outputtext, foreground="blue", background="white")
outputmessage.grid(row=4,column=0)

tkinter.mainloop()

