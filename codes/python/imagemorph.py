#!/usr/bin/python3

import tkinter
import tkinter.messagebox
import tkinter.filedialog

def updatescale(slidervalue):
    scalevar.set(slidervalue)

def fileopen(keyhit):
    print("In file open: hit key "+keyhit.char)
    filevar = tkinter.filedialog.askopenfilename()
    print(filevar)
    #print(filevar.read())

rootwindow = tkinter.Tk()
rootwindow.title("Image Morpher")
rootwindow.config(width=500, height=400, bg="purple")
rootwindow.lift()
rootwindow.call('wm', 'attributes', '.', '-topmost', True)
rootwindow.after_idle(rootwindow.call, 'wm', 'attributes', '.', '-topmost', False)

slider = tkinter.Scale(rootwindow, from_=0, to=1, foreground="blue", background="light grey", orient="vertical", resolution=0.01, command=updatescale, showvalue=0, length=200)
slider.grid(row=0,column=0)

scalevar = tkinter.DoubleVar()

scalelabel = tkinter.Label(rootwindow, textvariable=scalevar, foreground="blue", background="grey")
scalelabel.grid(row=0,column=1)

rootwindow.bind("f", fileopen)
rootwindow.focus_set()

tkinter.mainloop()

