#!/usr/bin/python3

import tkinter
import tkinter.filedialog
import PIL.Image
import PIL.ImageTk
import random
import math
import threading
import time

CANVAS_XSIZE = 500
CANVAS_YSIZE = 500
TIMESTEP = 40
NUMBALLS = 6




class bouncingball():

    def __init__(self, id):
        global screencanvas
        global balls
        global ballslock

        ballslock.acquire()
        self.threadid = id
        self.collided = False
        self.color = "#%06x" % random.randint(0,0xFFFFFF)
        self.radius = random.randint(5,25)

        self.centerx = random.randint(self.radius+2,CANVAS_XSIZE-self.radius-2)
        self.centery = random.randint(self.radius+2,CANVAS_YSIZE-self.radius-2)

        overlap = True

        while overlap:
            overlap = False

            for otherball in balls:
                if math.sqrt((self.centerx-otherball.centerx)**2 + (self.centery-otherball.centery)**2) <= (self.radius + otherball.radius + 1):
                    overlap = True 

            if overlap:
                self.centerx = random.randint(self.radius+2,CANVAS_XSIZE-self.radius-2)
                self.centery = random.randint(self.radius+2,CANVAS_YSIZE-self.radius-2)

        self.speed = random.randint(1,3)
        self.dx = 0
        self.dy = 0

        while self.dx == 0 and self.dy == 0:
            self.dx = random.randint(-1, 1)
            self.dy = random.randint(-1, 1)

        balls.append(self)
        ballslock.release()

        self.canvasid = screencanvas.create_oval(self.centerx-self.radius,self.centery-self.radius,self.centerx+self.radius,self.centery+self.radius, fill=self.color)
 
    def __del__(self):
        global screencanvas
        screencanvas.delete(self.canvasid)
        print ("deleted")

    def updateposition(self):
        global screencanvas
        global ballslock
        global balls

        ballslock.acquire()
        olddx = self.dx
        olddy = self.dy
        self.centerx += self.dx
        self.centery += self.dy
        collided = False

        if self.centerx >= CANVAS_XSIZE-self.radius or self.centerx <= self.radius:
            collided = True
            self.dx = -self.dx
    
        if self.centery >= CANVAS_YSIZE-self.radius or self.centery <= self.radius:
            collided = True
            self.dy = -self.dy

        for otherball in balls:
            if self.threadid != otherball.threadid:
                if math.sqrt((self.centerx-otherball.centerx)**2 + (self.centery-otherball.centery)**2) <= (self.radius + otherball.radius + 1):
                    collided = True
                    self.dx,otherball.dx = otherball.dx,self.dx
                    self.dy,otherball.dy = otherball.dy,self.dy
         
        if collided:
            self.centerx -= olddx
            self.centery -= olddy
        ballslock.release()

        if not collided:
            screencanvas.move(self.canvasid, self.dx, self.dy)

def moveball(id):
    global balls
    global screencanvas
    global ballslock
    global NUMBALLS

    ball = bouncingball(id)

    while threadflags[id]:
        ball.updateposition()

        time.sleep((TIMESTEP-ball.speed*10)/1000.0)

    ballslock.acquire()
    for ballid in range(NUMBALLS):
        if balls[ballid].threadid == id:
            break

    del balls[ballid]
    NUMBALLS -= 1
    ballslock.release()

    ball = None

def startthreads():
    global CANVAS_XSIZE
    global CANVAS_YSIZE
    global NUMBALLS

    if widthtext.get() != "":
        CANVAS_XSIZE = int(widthtext.get())

    if heighttext.get() != "":
        CANVAS_YSIZE = int(heighttext.get())

    if numtext.get() != "":
        NUMBALLS = int(numtext.get())

    screencanvas.config(width=CANVAS_XSIZE, height=CANVAS_YSIZE)

    for id in range(1,NUMBALLS+1):
        T = threading.Thread(target=moveball, args=[id])
        threadflags.append(True)
        T.start()

def stopthreads():
    global rootwindow
    global threadflags

    for id in range(1,NUMBALLS+1):
        threadflags[id] = False

    #rootwindow.after(100, rootwindow.destroy)

rootwindow = tkinter.Tk()
rootwindow.title("Bouncing Balls Screen Saver")
rootwindow.config(width=CANVAS_XSIZE, height=CANVAS_YSIZE, bg="purple")
rootwindow.lift()
rootwindow.call('wm', 'attributes', '.', '-topmost', True)
rootwindow.after_idle(rootwindow.call, 'wm', 'attributes', '.', '-topmost', False)

widthlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Width")
widthlabel.grid(row=0, column=0, sticky="nsew")

widthtext = tkinter.StringVar()
widthentry = tkinter.Entry(rootwindow, width=6, foreground="blue", background="light grey", textvariable=widthtext)
widthentry.grid(row=0, column=1, sticky="nsew")

heightlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Height")
heightlabel.grid(row=0, column=2, sticky="nsew")

heighttext = tkinter.StringVar()
heightentry = tkinter.Entry(rootwindow, width=6, foreground="blue", background="light grey", textvariable=heighttext)
heightentry.grid(row=0, column=3, sticky="nsew")

numlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Balls")
numlabel.grid(row=0, column=4, sticky="nsew")

numtext = tkinter.StringVar()
numentry = tkinter.Entry(rootwindow, width=6, foreground="blue", background="light grey", textvariable=numtext)
numentry.grid(row=0, column=5, sticky="nsew")

startbutton = tkinter.Button(rootwindow, height=1, foreground="blue", background="light grey", text="Start", command=startthreads)
startbutton.grid(row=0, column=6, sticky="nsew")

stopbutton = tkinter.Button(rootwindow, height=1, foreground="blue", background="light grey", text="Stop", command=stopthreads)
stopbutton.grid(row=0, column=7, sticky="nsew")

screencanvas = tkinter.Canvas(rootwindow, width=CANVAS_XSIZE, height = CANVAS_YSIZE, bg="light grey")
screencanvas.grid(row=1, column=0, columnspan=8)

threadflags = [False]
ballslock = threading.Lock()
balls = []

tkinter.mainloop()

