#!/usr/bin/python3

import wsgiref.simple_server as wg
import urllib.request as urlr
import urllib.parse as urlp
import json
from os import curdir, sep
import sys
from urllib.parse import quote


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

        i = 1 
        data = ""
        data += "<p>Driving direction from Mapquest:</p>"
        data +="<table id='directions'>"
        
        for instruction in parsed_data['route']['legs'][0]['maneuvers'] :
            data +=  "<tr><td>"+ str(i) + ".  "+instruction['narrative'] + " (" + str(instruction['distance']) + " miles) <td/><tr/>"
            i += 1
    return data
            
        
def googleMap(orig,dest):
    googleKey = "AIzaSyDn-5xdwkXhZfVDeIk_1kjZnKfVt_H83wc"
    urlTemplate = "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s"
    url = (urlTemplate %(quote(orig), quote(dest),googleKey))

    response = connectLink(url)

    if response == "failed" : 
        print("Could not connect to google map service")
    else :
        

        parsed_data = json.loads(response)

        i = 1 
        data = ""
        data += "<p>Driving direction from Google Map:</p>"
        data +="<table id='directions'>"

        for instruction in parsed_data['routes'][0]['legs'][0]['steps'] :
            #remove the bold html tag 
            dirtext = instruction['html_instructions'].replace("<b>","").replace("</b>","")
            if '<div style="font-size:0.9em">' in dirtext :
                dirtext = dirtext.replace('<div style="font-size:0.9em">', "\n").replace("</div>","")
            data +=  "<tr><td>"+ str(i) + ".  "+ dirtext + " (" + instruction['distance']['text'] + " ) <td/><tr/>"
            i += 1
    return data


def MyWebApp(environ, start_response):

    path_info = environ["PATH_INFO"]
    
    if environ['REQUEST_METHOD'] == "GET" :
         
         if path_info == "/" :
            filename = curdir + sep + "index.html"
            type = "text/html"
            

         elif path_info.endswith("favicon.ico") :
            filename = curdir + sep + path_info 
            type = "image/x-icon"
            response = open(filename, "rb").read()
          
         elif path_info.endswith(".css") :
            filename = curdir + sep + path_info
            type = "text/css"

         response = open(filename,"rb").read()

         resplen = len(response)
         
         status = "200 ok"
         headers = [('Content-type', type),('Content-length', str(resplen))]
         start_response(status, headers)
         return [response]
    elif environ['REQUEST_METHOD'] == "POST" :
        
    
         print("received a post")
         
         datalen = environ['CONTENT_LENGTH']
         data = environ['wsgi.input'].read(int(datalen))
         data = data.decode('utf-8')
         datadict = urlp.parse_qs(data)
         print (datadict)
         start = datadict['from'][0]
         stop = datadict['to'][0]
         service = datadict['service'][0]

         if service == "mapquest" :
            response = maprequestMap(start, stop)
         if service == "google" :
            response = googleMap(start, stop)
         
         status = "200 ok"
         headers = [('Content-type', 'text/html')]
         start_response(status, headers)
         return [bytes(response, "utf-8")]

    else :
        print("received a ", environ['REQUEST_METHOD'])


srv = wg.make_server("", 8081, MyWebApp)
srv.serve_forever()

