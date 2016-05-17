#!/usr/local/bin/python3

import os
import sys
import pwd

searchdir = '/'
searchsize = 10000000

if len(sys.argv) > 1 :
   if os.path.exists(sys.argv[1]) and os.path.isdir(sys.argv[1]) :
       searchdir = sys.argv[1]
   else :
       print("Bad directory name: ", sys.argv[1], ", using default /")

   if len(sys.argv) == 3 :
       if int(sys.argv[2]) > 0 :
            searchsize = int(sys.argv[2])
       else :
            print("Bad size: ", int(sys.argv[2]), ", using default 10,000,000")

   if len(sys.argv) > 3 :
       print("Extra arguments being ignored.")


for (pathnow, dirs, files) in os.walk(searchdir, followlinks=True) :
   for file in files :
       try :
           filestats = os.stat(os.path.join(pathnow,file))
       except FileNotFoundError :
           continue

       if filestats.st_size > searchsize :
           pwdentry = pwd.getpwuid(filestats.st_uid)
           owner = pwdentry.pw_name
           print("Filename: ", os.path.join(pathnow,file), " Size: ", filestats.st_size, " Owner: ", owner)

