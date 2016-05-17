#!/usr/local/bin/python3

decimal = input("Please enter a integer value: ")

quotient = int(decimal)
answer = ""

while quotient != 0 :

    remainder = quotient % 2
    quotient = quotient // 2
    answer = str(remainder) + answer

print(answer)
