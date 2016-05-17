#!/usr/local/bin/python3

filename = input("Please enter file name to process: ")

textfile = open(filename, "r")
lines = textfile.readlines()
textfile.close()

worddict = {}
linenum = 0

for line in lines :
    wordslist = line.split()
    for word in wordslist :
        word = word.strip(",.!'\"")
        word = word.upper()

        if word in worddict :
           worddict[word].add(linenum)
        else :
           worddict[word] = { linenum } 

    linenum += 1

# print(worddict)

superlineset = {num for num in range(0,linenum)}

done = False

while not done :
   query = input("Please enter your query: ")

   querywords = query.split()    
   if len(querywords) == 1 :
      queryword = querywords[0].upper()
      if worddict.get(queryword) == None :
          print("The word ", queryword, " is not in the file.")
      else :
          print("The word ", queryword, " appears on lines ", sorted(list(worddict[queryword])))
   elif len(querywords) == 2 :
      queryword = querywords[1].upper()
      if worddict.get(queryword) == None :
          print("The word ", queryword, " is not on every line.")
      else :
          print("The word ", queryword, " does not appear on lines ", sorted(list(superlineset - worddict[queryword])))
   elif len(querywords) == 3 and querywords[1] == "and" :
      queryword1 = querywords[0].upper()
      queryword2 = querywords[2].upper()
      if worddict.get(queryword1) == None or worddict.get(queryword2) == None :
          print("These words do not occur together on any line")
      else :
          print("These words occur together on lines ", sorted(list(worddict[queryword1] & worddict[queryword2])))



   answer = input("Do you want to do it again? ")
   if answer != "yes" :
       done = True
