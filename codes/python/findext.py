#!/usr/local/bin/python3

import sys
import os

searchdir = '/'
searchext = ".txt"

if len(sys.argv) > 1 :
   if os.path.exists(sys.argv[1]) and os.path.isdir(sys.argv[1]) :
       searchdir = sys.argv[1]
   else :
       print("Bad directory name: ", sys.argv[1], ", using default /")

   if len(sys.argv) == 3 :
       if sys.argv[2][0] == "." and len(sys.argv[2]) > 1 :
            searchext = sys.argv[2]
       else :
            print("Bad extension: ", sys.argv[2], ", using default .txt")

   if len(sys.argv) > 3 :
       print("Extra arguments being ignored.")

def recsearch(directoryname) :

   contents = os.listdir(directoryname)

   for item in contents :
       if os.path.isfile(item) :
            if item.endswith(searchext) :
                 print(item)
       elif os.path.isdir(item) :
            recsearch(item)

recsearch(searchdir)


