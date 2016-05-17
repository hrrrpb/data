#!/usr/bin/python3

#bouncing balls, threads, animations

import tkinter
import PIL.Image
import PIL.ImageTk
import random
import math
import threading

def moveball() :
    global dx1
    global dy1
    global dx2
    global dy2
    location = screencanvas.coords(canvasballid1)
    centerx1 = (location[0] + location[2])/2
    centery1 = (location[1] + location[3])/2
    if centerx1 >= 485 or centerx1 <= 15 :
        dx1 = -dx1
    if centery1 >= 485 or centery1 <= 15:
        dy1 = -dy1

    location = screencanvas.coords(canvasballid2)
   
    centerx2 = (location[0] + location[2])/2
    centery2 = (location[1] + location[3])/2
    if centerx2 >= 485 or centerx2 <= 15 :
        dx2 = -dx2
    if centery2 >= 485 or centery2 <= 15:
        dy2 = -dy2
    
    if math.sqrt((centerx1-centerx2)**2 + (centery1 - centery2)**2) <=30 :
        dx1,dx2 = dx2, dx1
        dy1,dy2 = dy2, dy1

    screencanvas.move(canvasballid1, dx1,dy1)
    screencanvas.move(canvasballid2, dx2,dy2)
    rootwindow.after(20, moveball)
    

rootwindow = tkinter.Tk()
rootwindow.title("Bouncing balls screen saver")
rootwindow.config(width=500, height=500, bg="purple")

x1 = random.randint(15,485)
y1 = random.randint(15,485)
screencanvas = tkinter.Canvas(rootwindow, width=500, height = 500, bg="light grey")
canvasballid1 = screencanvas.create_oval(x1-15,y1-15,x1+15,y1+15,fill="red")

x2 = random.randint(15, 485)
y2 = random.randint(15, 485)
while math.sqrt((x1-x2)**2 + (y1-y2)**2) < 30 :
    x2 = random.randint(0,470)
    y2 = random.randint(0,470)
canvasballid2 = screencanvas.create_oval(x2-15,y2-15,x2+15, y2+15, fill="blue")


screencanvas.grid(row=0, column=0)
dx1 = 0
dy1 = 0
while dx1==0 or dy1==0 :
    dx1 = random.randint(-1, 1)
    dy1 = random.randint(-1, 1)
 
dx2 = 0
dy2 = 0
while dx2==0 or dy2==0 :
    dx2 = random.randint(-1, 1)
    dy2 = random.randint(-1, 1)
 

rootwindow.after(2000, moveball)
tkinter.mainloop()

T = threading.Thread(target = function, args=[arg1,arg2, ...])

