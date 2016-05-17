#!/usr/bin/python3

import math
import time



#the function will find the divisors, assume n is the product of two prime numbers
# the function itself does not validate n is the product of 2 prime numbers
def findPrimerDivisors(n) :

	#get the largest int of squre root of n
	sqrt = math.floor(math.sqrt(n))

	if n % 2 == 0 :
		return (2, n//2)

	for i in range(3, sqrt+1, 2) :
		if n % i == 0 : 
			return (i, n//i)

	return (0, 0)



#using euclidean algorithm , return the coeffients tuple
#in tuple, first element is the remaining r, 
# second the multiplicative coeffients q x -1 
# a = q1 b + r1  =>  a + (-q1) b = r1
# b = q2 r1 + r2 =>  b + (-q2) r1 = r2
# ....
# the final coeff[1] is the inverse modulo of deKey

def inverseMod(a, b) :

	if b == 1 :
		return (1, 0)

	#basis case 1:  coprime
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

#the output from findInverseModulo could be negative
#create the postive inverse modulo
def findDeKey(p, q, enKey) :
	totient = (p-1) * (q-1)
	deKey = inverseMod(totient, enKey)[1]
	if deKey < 0 :
		deKey += totient

	return deKey


def moduloPower(base, power, modulus) :
	if base == 0 :
		return 0

	answer = 1
	for i in range (0, power) :
		answer = answer * base % modulus
	return answer


def factorAttack(e, n, c) :
	start = time.clock()
	(p,q) = findPrimerDivisors(n)
	deKey = findDeKey(p, q ,e)
	m = moduloPower(c, deKey, n)
	stop = time.clock()
	print("Factor attack output: ")
	if deKey == 0 : 
		print ("Attack failed")
	else:
		print("p =", p)
		print("q =", q)
		print("d =", deKey)
		print("M =", m)
		print("Used time in miliseconds : ", (stop - start) * 1000, "\n")


def discreteLogAttack(e, n, c) :
	start = time.clock()
    # m is intialized to -1, in case not found, -1 is indicative
	m = -1 
	if c == 0:
		m = 0
	elif c == 1:
		m = 1
	else:
		for i in range(2, n) :
			if moduloPower(i, e, n) == c :
				m = i
				break
	stop = time.clock()
	print("Discrete log attack : ")
	if m == -1 :
		print ("Attack failed")
	else :
		print ("M = ", m)
		print("Used time in miliseconds : ", (stop - start) * 1000, "\n")


print("RSA attack demo:")

print(moduloPower(23,17,103))

while True:

	userInput = input("Please input public key, n, and ciphertext, separeted by space : ")
	e = int(userInput.split(" ") [0])
	n = int(userInput.split(" ") [1])
	c = int(userInput.split(" ") [2])

	factorAttack(e, n, c)
	discreteLogAttack(e, n, c)

	quit = input("Do you want to quit ? y for quit, all other keys for continue :  ")
	if (quit == "Y" or quit == "y") : 
		break



	
