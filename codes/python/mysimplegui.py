#!/usr/bin/python3
import tkinter
from tkinter import messagebox


def getweather():
    print("city : ", citytext.get(), "State:", statetext.get())
    messagebox.showinfo("report")
    outputtext.set("report")


rootwindow = tkinter.Tk()
rootwindow.title("My title")
rootwindow.config(width =500, height = 400, bg = 'purple')
firstlable = tkinter.Label(rootwindow, text = "Please enter the city and state \n you wish to get a weather report", font ="Ariel 24",foreground = "blue", background = "light grey")
firstlable.grid(row = 0,column= 0,columnspan = 2)
secondlable = tkinter.Label(rootwindow, text = "City", font ="Ariel 24",foreground = "blue", background = "light grey")
secondlable.grid(row = 1, column = 0, sticky = "wens")
thirdlable = tkinter.Label(rootwindow, text ="State", font ="Ariel 24",foreground = "blue", background = "light grey")
thirdlable.grid(row = 2, column = 0,sticky ="wens")


citytext = tkinter.StringVar()

cityentry = tkinter.Entry(rootwindow, width = 20, foreground = "Red", background ="yellow", textvariable = citytext)
cityentry.focus_set()
cityentry.grid(row = 1, column =1,sticky="wens")

statetext = tkinter.StringVar()

stateentry = tkinter.Entry(rootwindow, width = 20, foreground = "Red", background ="yellow", textvariable = statetext)
stateentry.grid(row = 2, column =1,sticky = "wens")

submitbutton = tkinter.Button(rootwindow, text = "Submit", font = "Times 24", foreground = "orange", background = "pink", command = getweather)
submitbutton.grid(row=3, column = 1, sticky = "wens")

outputtext = tkinter.StringVar() 
outputmessage = tkinter.Message(rootwindow, textvariable = outputtext, foreground = "blue", background = "white", width = 200, anchor = "w")
outputmessage.grid(row = 3, column = 0, sticky = "wens")


tkinter.mainloop()

#for demonstration
slider = tkinter.Scale(rootwindow, from_ = 0, to = 1, foreground = "blue", background = "light grey")
slider.grid(row = 0, column =0 )




 

