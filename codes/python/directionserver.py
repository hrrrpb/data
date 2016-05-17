#!/usr/local/bin/python3

import wsgiref.simple_server as wg
import urllib.request as url
import urllib.parse as urlp
import json

apicode = "b0d45b8cc18faf92d77e9825fd5aae74"

def MyWebApp(environ, start_response):
    print("In Web App")
    if environ['REQUEST_METHOD'] == "GET" :
         
         response = """
                    <html>
                    <HEAD>
                    <TITLE>My Weather Server</TITLE>
                    </HEAD>
                    <BODY>
                    <h1>Welcome to the weather server</h1><br>
                    Please enter the city and state to get current conditions.<br>
                    <form method="POST">
                    <br><b>City: </b><input type=text name='city'>
                    <br><b>State: </b><input type=text name='state'>
                    <br><button type=submit>Submit
                    </form>
                    </BODY>
                    </html>"""
         response = bytes(response, 'utf-8')
         resplen = len(response)
         
         status = "200 ok"
         headers = [('Content-type', 'text/html, charset=utf-8'),('Content-length', str(resplen))]
         start_response(status, headers)
         return [response]
    elif environ['REQUEST_METHOD'] == "POST" :
         print("received a post")
#         print(environ)
         datalen = environ['CONTENT_LENGTH']
         data = environ['wsgi.input'].read(int(datalen))
         data = data.decode('utf-8')
         datadict = urlp.parse_qs(data)
#         print(datadict)

         urlquery = "http://api.openweathermap.org/data/2.5/weather?q="+datadict['city'][0]+","+datadict['state'][0]+"&mode=json&units=imperial&APPID="+apicode

         urlquery = urlquery.replace(" ", "%20")

         page = url.urlopen(urlquery)

         if page.getcode() == 200 :
             weatherstring = page.read()
             weatherstring = weatherstring.decode("utf-8")

             weatherdata = json.loads(weatherstring)
             print(weatherdata)

             temperature = weatherdata['main']['temp']
     
             humidity = weatherdata['main']['humidity']

             windspeed = weatherdata['wind']['speed']

             if 'deg' in weatherdata['wind'] :
                 winddir = weatherdata['wind']['deg']
                 if winddir < 23 or winddir > 337:
                     winddir = "north"
                 elif winddir > 22 and winddir < 68 :
                     winddir = "northeast"
                 elif winddir > 67 and winddir < 113 :
                     winddir = "east"
                 elif winddir > 112 and winddir < 158 :
                     winddir = "southeast"
                 elif winddir > 157 and winddir < 203 :
                     winddir = "south"
                 elif winddir > 202 and winddir < 248 :
                     winddir = "southwest"
                 elif winddir > 247 and winddir < 293 :
                     winddir = "west"
                 elif winddir > 292 and winddir < 338 :
                     winddir = "northwest"
             else :
                 winddir = "the center of the universe"
   
             response = """
                    <html>
                    <HEAD>
                    <TITLE>Current Weather Conditions</TITLE>
                    </HEAD>
                    <BODY>
                    <h1>The current weather conditions for your city are:</h1><br>
                    <form method="GET">
                    """
             response += "The temperature is " + str(temperature) + "degrees F<br>"
             response += "The humidity is " + str(humidity) + "%<br>"
             response += "The wind is from the " + winddir + " at " + str(windspeed) + " mph<br>"
             response += "<br><button type=submit>Back</form></BODY></html>"

             print("The tempeature is ", temperature, " degrees F")
             print("The humidity is ", humidity, "%")
             print("The wind is from the ", winddir, " at ", windspeed, " mph")
         else :
             print("No valid response from weather server")

         response = bytes(response, 'utf-8')
         resplen = len(response)
         
         status = "200 ok"
         headers = [('Content-type', 'text/html, charset=utf-8'),('Content-length', str(resplen))]
         start_response(status, headers)
         return [response]
    else :
         print("received a ", environ['REQUEST_METHOD'])

srv = wg.make_server("", 1234, MyWebApp)
srv.serve_forever()

