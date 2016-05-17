#!/usr/bin/python3

import tkinter
import tkinter.messagebox
import sqlite3

data = []
statesdic = {
        'AK': 'Alaska',
        'AL': 'Alabama',
        'AR': 'Arkansas',
        'AS': 'American Samoa',
        'AZ': 'Arizona',
        'CA': 'California',
        'CO': 'Colorado',
        'CT': 'Connecticut',
        'DC': 'District of Columbia',
        'DE': 'Delaware',
        'FL': 'Florida',
        'GA': 'Georgia',
        'GU': 'Guam',
        'HI': 'Hawaii',
        'IA': 'Iowa',
        'ID': 'Idaho',
        'IL': 'Illinois',
        'IN': 'Indiana',
        'KS': 'Kansas',
        'KY': 'Kentucky',
        'LA': 'Louisiana',
        'MA': 'Massachusetts',
        'MD': 'Maryland',
        'ME': 'Maine',
        'MI': 'Michigan',
        'MN': 'Minnesota',
        'MO': 'Missouri',
        'MP': 'Northern Mariana Islands',
        'MS': 'Mississippi',
        'MT': 'Montana',
        'NA': 'National',
        'NC': 'North Carolina',
        'ND': 'North Dakota',
        'NE': 'Nebraska',
        'NH': 'New Hampshire',
        'NJ': 'New Jersey',
        'NM': 'New Mexico',
        'NV': 'Nevada',
        'NY': 'New York',
        'OH': 'Ohio',
        'OK': 'Oklahoma',
        'OR': 'Oregon',
        'PA': 'Pennsylvania',
        'PR': 'Puerto Rico',
        'RI': 'Rhode Island',
        'SC': 'South Carolina',
        'SD': 'South Dakota',
        'TN': 'Tennessee',
        'TX': 'Texas',
        'UT': 'Utah',
        'VA': 'Virginia',
        'VI': 'Virgin Islands',
        'VT': 'Vermont',
        'WA': 'Washington',
        'WI': 'Wisconsin',
        'WV': 'West Virginia',
        'WY': 'Wyoming'
}


def readdata() :
    global data
    dbconn = sqlite3.connect("info.db")
    dbcurs = dbconn.cursor()
    records = dbcurs.execute("SELECT * FROM statecands")
    for record in records :
        data.append(record)
    dbconn.close()

def getstatedata() :
    state = statetext.get().strip()
    # if input is abbreations
    if state.upper() in statesdic.keys() :
        state = statesdic[state.upper()]
    #if input has correct spellings
    elif state.upper() in (fullname.upper() for fullname in statesdic.values()) :
        pass
        
    else :
        outputtext.set("Invalid state name, please check the spellings.")
        return

    for statedata in data :
        if statedata[0].lower() == state.lower() :
            outputstring="State : "+ statedata[0] + "\n"
            outputstring+="Number of votes for candidate 1 : "+ str(statedata[1])+"\n"
            outputstring+="Number of votes for candiaate 2 : "+ str(statedata[2])
            outputtext.set(outputstring)
            break


#get the sum for votes

def getoveralldata() :
    cnt1 = 0
    cnt2 = 0
    for statedata in data :
        cnt1 += statedata[1]
        cnt2 += statedata[2]

    outputstring="51 States summary : \n"
    outputstring+="Total number of votes for candidate 1 : "+ str(cnt1)+"\n"
    outputstring+="Total number of votes for candidate 2 : "+ str(cnt2)

    outputtext.set(outputstring)

#get the electoral sum 

def getfinaldata() :
    cnt1 = 0
    cnt2 = 0
    for statedata in data :
        if statedata[1] > statedata[2] :
            cnt1 += statedata[1] + statedata[2]
        else :
            cnt2 += statedata[1] + statedata[2]
    outputstring="51 States elector summary : \n"
    outputstring+="Total number of electoral votes for candidate 1 : "+ str(cnt1)+"\n"
    outputstring+="Total number of electoral votes for candidate 2 : "+ str(cnt2)

    tkinter.messagebox.showinfo("Elector report", outputstring)

    

#read in database
readdata()

#initate gui layout
rootwindow = tkinter.Tk()
rootwindow.title("Elector Votes")
rootwindow.config(width=400, height=400, bg="light grey")


prompt = tkinter.Label(rootwindow, height = 2, text="Please enter the query state : ", background="light grey",width = 30)
prompt.grid(row=0,column=0,columnspan=2)



statetext = tkinter.StringVar()
stateentry = tkinter.Entry(rootwindow, width=15, background="light grey", textvariable=statetext)
stateentry.focus_set()
stateentry.grid(row=0, column=2)


button1 = tkinter.Button(rootwindow, height=1, width = 8, foreground="blue", background="light grey", text="State stats", command=getstatedata)
button1.grid(row=1, column=0)

button2 = tkinter.Button(rootwindow, height=1, width = 8, foreground="blue", background="light grey", text="Overall stats", command=getoveralldata)
button2.grid(row=1, column=1)

button3 = tkinter.Button(rootwindow, height=1, width = 8, foreground="blue", background="light grey", text="Final stats", command=getfinaldata)
button3.grid(row=1, column=2)


outputtext = tkinter.StringVar()
outputmessage = tkinter.Message(rootwindow, textvariable=outputtext, foreground="black", background="white", anchor="w", justify="left", width = 300)
outputmessage.grid(row=2,column=0,columnspan = 3, sticky="wens")

tkinter.mainloop()

