#!/usr/bin/python3

print ("Calculator\n")


while (True):
    userInput = raw_input("Pleaes input calculation operation ('q' for quit) :\n")
    if userInput=="q" :
        break
    # make a opList, ** is before *, so ** is always checked before *
    opList=["+","-","**","/","*"]
    for op in opList:
        if op in userInput: 
	    inputList = userInput.split(op)
            #check if the input is integer or float
            if("." in inputList[0]):
                operand1 = float(inputList[0])
            else:
                operand1 = int(inputList[0])
            
            if("." in inputList[1]):
                operand2 = float(inputList[1])
            else:
                operand2 = int(inputList[1])
            
            #perform operatons based on op
            if op=="+":
                print("\nOutput: "+userInput +" = "+ str(operand1+operand2))     
            elif op=="-": 
                print("\nOutput: "+userInput +" = "+ str(operand1-operand2))
            elif op=="*":
                print("\nOutput: "+userInput +" = "+ str(operand1*operand2))
            elif op=="/" :
                if operand2==0 :
                    print("Zero Division Error.")
                else:

                    print("\nOutput: "+userInput +" = "+ str(operand1/operand2))
            else:
                  
                print("\nOutput: "+userInput +" = "+ str(operand1**operand2))
            break

