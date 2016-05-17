#!/usr/local/bin/python3

L = [ ["tom", "smith", 32], ["fred", "smith", 35], ["tom", "smithers", 42], ["john", "smith", 1] ]

def namemerge (item) :
    return item[1] + "," + item[0]

print(L)
L.sort(key=namemerge)
print(L)

