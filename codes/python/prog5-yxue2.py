#!/usr/bin/python3

import sys
import urllib.request as urlr
from urllib.parse import quote
import json

#using json data format for fetching and parsing
#the parsed data were carefully inspected to correctly retreive the info needed
#quote is required to convert the location string into correct url form
#only basic function implemented, advanced options with various parameter choices are not added, though can be added easily

def connectLink(url) :

	urlobject = urlr.urlopen(url)
	if urlobject.getcode() == 200 :
		response = urlobject.read().decode("utf-8")
	else :
		response = "failed"
	return response

def maprequestMap(orig, dest):
	mapquestKey = "yzA9By0zz0RjyNfG6uGwbejpqBj8ABAs"
	urlTemplate = "http://www.mapquestapi.com/directions/v2/route?key=%s&from=%s&to=%s"

	url = (urlTemplate %(mapquestKey, quote(orig), quote(dest)))

	response = connectLink(url)

	if response == "failed" : 
		print("Could not connect to mapquest service")
	else :
       
		parsed_data = json.loads(response)
		print("\nDriving direction from Maprequest :", "\n")
		for instruction in parsed_data['route']['legs'][0]['maneuvers'] :
			print (instruction['narrative'], "(", instruction['distance'] ," miles)")
		
#google map returns multiple routes, I just chose the first one
def googleMap(orig,dest):
	googleKey = "AIzaSyDn-5xdwkXhZfVDeIk_1kjZnKfVt_H83wc"
	urlTemplate = "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s"
	url = (urlTemplate %(quote(orig), quote(dest),googleKey))

	response = connectLink(url)

	if response == "failed" : 
		print("Could not connect to google map service")
	else :
       
		parsed_data = json.loads(response)

		print("\nDriving direction from Google map :", "\n")

		for instruction in parsed_data['routes'][0]['legs'][0]['steps'] :
			#remove the bold html tag 
			dirtext = instruction['html_instructions'].replace("<b>","").replace("</b>","")
			if '<div style="font-size:0.9em">' in dirtext :
				dirtext = dirtext.replace('<div style="font-size:0.9em">', "\n").replace("</div>","")

			print (dirtext, "(",instruction['distance']['text'],")")




origin = input("Please enter your origin : (e.g.  30 Main Street, New Haven CT)\n")
destination = input("Please enter your destination : \n")

while True:

	choice = input("Please enter your choice of map service:   1 for google, 2 for mapquest, 3 for both \n")
	if (choice != "1" and choice !="2" and choice != "3") :
		print ("Invalid choice, you have to choose 1 , 2 or 3. ")
	else:
		break

if choice == "1" :
	googleMap(origin, destination)

if choice == "2" :
	maprequestMap(origin,destination)

if choice == "3" :
	googleMap(origin, destination)
	maprequestMap(origin, destination)



