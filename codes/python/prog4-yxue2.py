#!/usr/bin/python3
import os
import sys


#extract the files in the directory
def extractfiles(directory) :
	content = os.listdir(directory)
	files =[]
	for item in content:
		if os.path.isfile(directory+"/"+item):
			files.append(item)
	return files

#extract the subdirectories in the directory
def extractdirs(directory) :
	content = os.listdir(directory)
	dirs = []
	for item in content:
		if os.path.isdir(directory+"/"+item) :
			dirs.append(item)
	return dirs


#list the contents in the directory, spaces control the indentation
def listdir(directory, spaces):
	for file in extractfiles(directory):
		print(spaces,"-",file, "(", directory, ")")
	for direc in extractdirs(directory) :
		print(spaces,"+",direc, "(", directory, ")")
		listdir(os.path.join(directory, direc), spaces+" ")


#check difference in two dirs, spaces control indentation
def checkdirdiff(dir1, dir2, spaces=""):
    
	files1 = extractfiles(dir1)
	dirs1 = extractdirs(dir1)
	files2 = extractfiles(dir2)
	dirs2 = extractdirs(dir2)



    #check files difference



	for file in files1 : 
		if file not in files2 :
			print(spaces,"-", file, "(", dir1, ")")
		else:
			stat1 = os.stat(dir1+"/"+file)
			stat2 = os.stat(dir2+"/"+file)
			if stat1.st_size == stat2.st_size and stat1.st_mtime == stat2.st_mtime :
				print (spaces,"-", file, "( same )")
			else:
				if stat1.st_mtime < stat2.st_mtime :
					print (spaces, "-",file, "( newer in :", dir2, ")")
				else :
					print (spaces, "-",file, "( newer in :", dir1, ")")


	for file in files2 : 
		if file not in files1 :
			print(spaces, "-",file, " (", dir2, ")")

	#checking dir differences

	for directory in dirs1 : 
		
		if directory not in dirs2 :
			print(spaces, "+",directory,"(", dir1, ")")
			#list content in sub dir, notice adding one space for indentation
			listdir(os.path.join(dir1,directory), " ")
		else:
			print(spaces, "+", directory, "( same )")
			#recursively check sub dir difference, adding one space for indentation control
			checkdirdiff(os.path.join(dir1,directory),os.path.join(dir2,directory), spaces+" ")


	for directory in dirs2 :

		if directory not in dirs1 :
			
			print(spaces, "+", directory, "(", dir2,")")
			listdir(os.path.join(dir2,directory), " ")



#validating input containing two valid directories
if len(sys.argv)!=3 :
	print("Command operands error : please input exactly two directories")
	sys.exit(0)
else :
	if (not os.path.isdir(sys.argv[1]) or not os.path.exists(sys.argv[1])) :
		print("Error: ", sys.argv[1], " is not a directory or does not exist")
		sys.exit(0)
	if (not os.path.isdir(sys.argv[2]) or not os.path.exists(sys.argv[2])) :
		print("Error: ", sys.argv[2], " is not a directory or does not exist")
		sys.exit(0)

dir1 = sys.argv[1]
dir2 = sys.argv[2]



checkdirdiff(dir1,dir2)