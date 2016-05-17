#!/usr/bin/python3

import math
import time

#p, q, h, x, k, a “real” value H(M1), and a “fake” value H(M2)

def moduloPower(base, power, modulus) :
	if base == 0 :
		return 0

	answer = 1
	for i in range (0, power) :
		answer = answer * base % modulus
	return answer

def inverseMod(a, b) :


	#base case 1:  coprime
	if a % b == 1 :
		coeff = (1, a // b * (-1))
		return coeff
   
    #basis case 2: not coprime
	if a % b == 0 :
		print("a and b are not coprime, return 0,0")
		return (0, 0)

    #recursive cycles
	r = a % b 
	q = a // b * (-1)

	coeff = inverseMod(b, r)
	newR = coeff[1]
	newQ = coeff[0] + coeff[1] * q
	return (newR, newQ)


#calculate g
def calcg(p,q,h) :
	g = moduloPower(h, (p-1)//q, p)
	print ("g = ", g)
	return g

#calculate y
def calcy(g,x,p) :
	y = moduloPower(g,x,p)
	print("y = ", y)
	return y

#calculate signature
def calcsig(g,k,p,q,x,H) :
	r = moduloPower(g, k, p) % q

	if k == 1 :
		kinverse = 1
	else :
		kinverse = inverseMod(q, k)[1]
		if kinverse < 0 :
			kinverse += q
	s = kinverse * (H + x*r) % q
	print("(r, s) = (", r,",", s, ")" )
	return (r, s)


#perform signing process
def sign(p, q, g, x, k, H) :
	return calcsig(g,k,p,q,x,H)


#perform verifying process
def verify(p,q,g,y,r,s,H) :
	if s == 1 :
		w = 1
	else :
		w = inverseMod(q, s)[1]
		if w < 0:
			w += q
	u1 = H * w % q 
	u2 = r * w % q
	v = (moduloPower(g,u1,p) * moduloPower(y,u2,p))%p % q
	print("w = ", w)
	print("u1 = ", u1)
	print("u2 = ", u2)
	print("v = ", v)
	if v == r :
		print("Signature verfied")
		return True
	else :
		print("Signature not verfied, message faked")
		return False

print("DSA demo:")

while True:

	userInput = input("Please input p, q, h, x ,k, H(M1) and H(M2), separeted by space : ")
	p = int(userInput.split(" ") [0])
	q = int(userInput.split(" ") [1])
	h = int(userInput.split(" ") [2])
	x = int(userInput.split(" ") [3])
	k = int(userInput.split(" ") [4])
	H1 = int(userInput.split(" ") [5])
	H2 = int(userInput.split(" ") [6])

	print("Sign message H1 :")
	g = calcg(p, q, h)

	y = calcy(g, x, p)
	(r, s) = sign(p, q, g, x, k, H1)


    # s cannot be zero, otherwise, verification cause zero division failure
	if s == 0 or r == 0 :
		print("Please choose a different k to make s or r nonzero")
		continue

	print("\nNow verify signature of H1")
	verify(p,q,g,y,r,s,H1)

	print("\nNow verify signature of H2")
	verify(p,q,g,y,r,s,H2)
	
	quit = input("Do you want to quit ? y for quit, all other keys for continue :  ")
	if (quit == "Y" or quit == "y") : 
		break

