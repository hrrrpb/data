#!/usr/bin/python3

import tkinter
import PIL.Image
import PIL.ImageTk
import random
import math, string
import threading
import time

CANVAS_XSIZE = 600
CANVAS_YSIZE = 400
GUNCNT = 3
BULLETSPD = 3
TARGETSPD = 3
FIRESPD = 3
GUNTHREADS = []
GUNS = []
HIT = 0
TOTAL = 0
TARGETS = []



LOCK = threading.Lock()   #GLOBAL LOCK to check if bullet hits target

# it is a animation for shooting, guns and target both maintain a list of objects, object creation and moving are done in the class 
# internal threads using their individual internal lock (run function)
# all the guns and objects share another global lock to check if bullet hit target. The hit is implemented in bullet moving function. 
# To improve the efficiency, only bullet reaching the hitting region, the global lock will be applied. 
# after stop, all the guns and target, including their bullets and objects, will be deleted. The statistics will be printed out.

class flyingtargets() :
    def __init__(self):
        global screencanvas
        
        self.objects = []
        self.state = True

        self.y = 10
        self.x = 5
        self.lock = threading.Lock()


    def createobject(self) :
        global TOTAL
        while self.state :
            #get the internal lock to add new object
            self.lock.acquire()
            
            self.objects.append(screencanvas.create_rectangle(self.x, self.y, self.x + 40, self.y + 10, fill ="green"))
            self.lock.release()
            TOTAL = TOTAL + 1
            time.sleep(2)


    def __del__(self):
        global screencanvas

        #delete gun and its current bullets
        
        for object in self.objects :
            screencanvas.delete(object)
        
        print ("target objects deleted ")


    def moveobjects(self) :
        global LOCK
        while self.state :
            # get the internal lock to update the objects
            self.lock.acquire()
            LOCK.acquire()
            deletelist = []
            for flyobject in self.objects :
                coords = screencanvas.coords(flyobject)
                if coords[0] > CANVAS_XSIZE - 5 :
                    screencanvas.delete(flyobject)
                    deletelist.append(flyobject)

                else :
                    screencanvas.move(flyobject, 2, 0)

            for flyobject in deletelist :
                self.objects.remove(flyobject)
            LOCK.release()
            self.lock.release()
            time.sleep(40/1000/TARGETSPD)

    def run(self) :

        time.sleep(2)
        
        t1 = threading.Thread(target=self.createobject)
        t2 = threading.Thread(target=self.moveobjects)
        t1.start()
        t2.start()
        
        # if both thread exit, run will exit
        t1.join()
        t2.join()

 


class gun():

    def __init__(self, id):
        global screencanvas
        global guns
        global maxdist
        self.lock = threading.Lock()

        self.id = id
        self.bullets = []

        self.state = True

        #create the gun
        distance = CANVAS_XSIZE / (GUNCNT + 1)
        self.x = self.id * distance - 20
        self.y = CANVAS_YSIZE - 15
        self.canvasid = screencanvas.create_rectangle(self.x, self.y, self.x + 40, self.y + 15, fill ="#4B5320")


    def __del__(self):
        global screencanvas

        #delete gun and its current bullets
        
        for bullet in self.bullets :
            screencanvas.delete(bullet)
        screencanvas.delete(self.canvasid)
        print ("gun", self.id, "deleted ,bullets deleted")

    def fire(self) :
        
        while self.state :
            #get internal lock to create new bullets
            self.lock.acquire()
            
            self.bullets.append(screencanvas.create_rectangle(self.x+20, self.y-10, self.x +25, self.y -20, fill ="silver"))
            self.lock.release()
            time.sleep(9/FIRESPD)



    def movebullets(self) :
        global LOCK
        global HIT

        while self.state :
            #get internal lock to update bullets
            self.lock.acquire()
            #create the delete list to keep all bullets should be removed this round
            deletelist = []
            for bullet in self.bullets :
                coords = screencanvas.coords(bullet)
                #if bullets reaching the hit range, aquire the GLOBAL LOCK to check if hit
                if coords[1] <= 20 :
                    LOCK.acquire()
                    bulletcenterx = (coords[0] + coords[2])/2
                    bulletcentery = (coords[1] + coords[3])/2
                    #keep the targets which are hit this round
                    objdeletelist = []
                    for flyobject in TARGETS[0].objects :
                        objcoords = screencanvas.coords(flyobject)
                        objcenterx = (objcoords[0] + objcoords[2])/2
                        objcentery = (objcoords[1] + objcoords[3])/2
                        dist = math.sqrt((bulletcenterx - objcenterx) ** 2 + (bulletcentery - objcentery) ** 2)
                        if dist <= maxdist :
                            screencanvas.delete(bullet)
                            screencanvas.delete(flyobject)
                            objdeletelist.append(flyobject)
                            deletelist.append(bullet)
                            HIT = HIT + 1
                    # remove the targets
                    for flyobject in objdeletelist :
                        TARGETS[0].objects.remove(flyobject)


                    LOCK.release()

                # if bullets fly out of screen, remove 

                if coords[3] < 0 :
                    screencanvas.delete(bullet)
                    deletelist.append(bullet)
                # if still in screen and not hitting anything, move
                else :
                    screencanvas.move(bullet, 0, -2)
            #remove bullets from the gun
            for bullet in deletelist :
                self.bullets.remove(bullet)
            self.lock.release()

            time.sleep(40/1000/BULLETSPD)


    def run(self) :

        time.sleep(2)
        randomInterval = random.randint(0, 100000)

        time.sleep(randomInterval/100000)
        t1 = threading.Thread(target=self.fire)
        t2 = threading.Thread(target=self.movebullets)
        t1.start()
        t2.start()

        t1.join()
        t2.join()

        


def startgun(id) :
    global GUNS
    newgun = gun(id)
    GUNS.append(newgun)
    newgun.run()
    #after gun run finish, delete the gun
    del GUNS[GUNS.index(newgun)]

def starttarget() :
    global TARGETS
    target = flyingtargets()
    TARGETS.append(target)
    target.run()
    #after target stopped, delete the target
    del TARGETS[0]


#check the input value
def checkinput(input) :
    try :
        temp = int(input)
        if temp < 1 :
            temp = 1
        elif temp > 5 :
            temp = 5
        
    except ValueError:
        temp = 0
    
    return temp

def start() :
    global screencanvas
    global GUNCNT
    global FIRESPD
    global BULLETSPD
    global TARGETSPD
    #always clean the screen before start
    screencanvas.delete("all")

    # check if the input value is a number , if yes, check the limit

    if guncounttext.get() != "":
        temp = checkinput(guncounttext.get())
        GUNCNT = 3 if temp == 0 else temp
        

    if firespeedtext.get() != "" :
        temp = checkinput(firespeedtext.get())
        FIRESPD = 3 if temp == 0 else temp

    if bulletspeedtext.get() != "":
        temp = checkinput(bulletspeedtext.get())
        BULLETSPD = 3 if temp == 0 else temp
        

    if targetspeedtext.get() != "":
        temp = checkinput(targetspeedtext.get())
        TARGETSPD = 3 if temp == 0 else temp

    # start threads of guns
    for id in range(1,GUNCNT+1):
        T = threading.Thread(target=startgun, args=[id])
        T.start()

    # start thread of target
    T = threading.Thread(target = starttarget)
    T.start()
    

def stop() :
    global rootwindow
    global threadflags
    global GUNS, TARGET
    global screencanvas
    global TOTAL, HIT
    
    #deactivate both guns and target
    for gun in GUNS:
        gun.state = False
    
    TARGETS[0].state = False

    #put on statistics
    screencanvas.create_text(10, 15 , anchor = "nw", text = " HIT : " + str(HIT), fill = "white" )
    screencanvas.create_text(10, 30 , anchor = "nw", text = " TOTAL : " + str(TOTAL), fill = "white" )
    screencanvas.create_text(10, 45 , anchor = "nw", text = " HIT RATIO :  %.2f" %(HIT/TOTAL), fill = "white")
    
    #reset statistics
    TOTAL = 0
    HIT = 0
    #rootwindow.after(100, rootwindow.destroy)


#initialize layout

#calculate the maximum collision distance between the centers of bullet and flying object
#flying object size : 40, 10
#bullet size : 5, 10
#the max distance is a crude way to judge the hit, given more time, should be able to get a better scheme to do it
deltax = (40 + 5) / 2
deltay = (10 + 10) /2
maxdist = math.sqrt(deltax ** 2 + deltay **2)


#create the layout
rootwindow = tkinter.Tk()
rootwindow.title("Shooting Animation")
rootwindow.config(width=CANVAS_XSIZE, height=CANVAS_YSIZE, bg="light grey")

guncountlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Gun # (1-5)")
guncountlabel.grid(row=0, column=0, sticky="nsew")

guncounttext = tkinter.StringVar()
guncountentry = tkinter.Entry(rootwindow, width=2, foreground="blue", background="light grey", textvariable= guncounttext)
guncountentry.grid(row=0, column=1, sticky="nsew")

firespeedlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Fire spd (1-5)")
firespeedlabel.grid(row=0, column=2, sticky="nsew")

firespeedtext = tkinter.StringVar()
firespeedentry = tkinter.Entry(rootwindow, width=2, foreground="blue", background="light grey", textvariable=firespeedtext)
firespeedentry.grid(row=0, column=3, sticky="nsew")

bulletspeedlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Bullet spd (1-5)")
bulletspeedlabel.grid(row=0, column=4, sticky="nsew")

bulletspeedtext = tkinter.StringVar()
bulletspeedentry = tkinter.Entry(rootwindow, width=2, foreground="blue", background="light grey", textvariable=bulletspeedtext)
bulletspeedentry.grid(row=0, column=5, sticky="nsew")

targetspeedlabel = tkinter.Label(rootwindow, foreground="blue", background="light grey", text="Target spd (1-5)")
targetspeedlabel.grid(row=0, column=6, sticky="nsew")

targetspeedtext = tkinter.StringVar()
targetspeedentry = tkinter.Entry(rootwindow, width=2, foreground="blue", background="light grey", textvariable=targetspeedtext)
targetspeedentry.grid(row=0, column=7, sticky="nsew")

startbutton = tkinter.Button(rootwindow, height=1, foreground="blue", background="light grey", text="Start", command=start)
startbutton.grid(row=0, column=8, sticky="nsew")

stopbutton = tkinter.Button(rootwindow, height=1, foreground="blue", background="light grey", text="Stop", command=stop)
stopbutton.grid(row=0, column=9, sticky="nsew")

screencanvas = tkinter.Canvas(rootwindow, width=CANVAS_XSIZE, height = CANVAS_YSIZE, bg="black")
screencanvas.grid(row=1, column=0,columnspan=10)

#disable resize
rootwindow.resizable(0,0)
tkinter.mainloop()


