#!/usr/bin/python3

import urllib.request as urlr

url ="http://www.mapquestapi.com/directions/v2/route?key=yzA9By0zz0RjyNfG6uGwbejpqBj8ABAs&from=branford%20ct&to=hartford%20ct&callback=renderNarrative"


print(urlr.urlopen(url).read())