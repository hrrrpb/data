#!/usr/local/bin/python3

import tkinter
import tkinter.filedialog
import PIL.Image
import PIL.ImageTk

def loadimage1():
    filevar = tkinter.filedialog.askopenfilename()
    image1 = PIL.Image.open(filevar)
    image1 = image1.resize((256,256), PIL.Image.ANTIALIAS)
    image1tk = PIL.ImageTk.PhotoImage(image1)
    image1label.configure(image=image1tk)
    image1label.image = image1
    image1label.imagetk = image1tk
    
def loadimage2():
    filevar = tkinter.filedialog.askopenfilename()
    image2 = PIL.Image.open(filevar)
    image2 = image2.resize((256,256), PIL.Image.ANTIALIAS)
    image2tk = PIL.ImageTk.PhotoImage(image2)
    image2label.configure(image=image2tk)
    image2label.image = image2
    image2label.imagetk = image2tk
    
def updateimage(slidervalue):
    print(slidervalue)
    morphimage = PIL.Image.blend(image1label.image,image2label.image,float(slidervalue))
    morphcanvas.image=morphimage
    morphimagetk = PIL.ImageTk.PhotoImage(morphimage)
    morphcanvas.imagetk = morphimagetk
    morphcanvas.itemconfig(canvasimageid, image=morphimagetk)

rootwindow = tkinter.Tk()
rootwindow.title("Image Morpher")
rootwindow.config(width=500, height=400, bg="purple")
rootwindow.lift()
rootwindow.call('wm', 'attributes', '.', '-topmost', True)
rootwindow.after_idle(rootwindow.call, 'wm', 'attributes', '.', '-topmost', False)

image1 = PIL.Image.new("RGB", (256, 256), color="pink")
image1tk = PIL.ImageTk.PhotoImage(image1)

image1label = tkinter.Label(rootwindow, background="blue", image=image1tk)
image1label.image = image1
image1label.imagetk = image1tk
image1label.grid(row=0,column=0)

image1button = tkinter.Button(rootwindow, bg="green", text="Load Image 1", command=loadimage1)
image1button.grid(row=1,column=0,sticky="wens")

image2label = tkinter.Label(rootwindow, background="blue", image=image1tk)
image2label.image = image1
image2label.imagetk = image1tk
image2label.grid(row=0,column=2)

image2button = tkinter.Button(rootwindow, bg="green", text="Load Image 2", command=loadimage2)
image2button.grid(row=1,column=2,sticky="wens")

slider = tkinter.Scale(rootwindow, from_=0, to=1, foreground="blue", background="light grey", orient="horizontal", resolution=0.01, command=updateimage, showvalue=1, length=256)
slider.grid(row=1,column=1)

morphcanvas = tkinter.Canvas(rootwindow, width=256, height = 256, bg="blue")
canvasimageid = morphcanvas.create_image(2,2, image=image1tk, anchor="nw")
morphcanvas.grid(row=0,column=1)
 
#scalevar = tkinter.DoubleVar()

#scalelabel = tkinter.Label(rootwindow, textvariable=scalevar, foreground="blue", background="grey")
#scalelabel.grid(row=0,column=1)

#rootwindow.bind("f", fileopen)
#rootwindow.focus_set()

tkinter.mainloop()

