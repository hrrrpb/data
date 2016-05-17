/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

public class StackADT {
    StackNode[] arr=new StackNode[10];
        int top = -1;
        StackNode node;

        StackADT(){

        }
        
        public void push(StackNode n)
        {
                  top=top+1;
                  node=new StackNode(n.node,n.character);
                  arr[top] = node;
        }

        public StackNode pop()
        {
                  StackNode popped = arr[top];
                  top--;
                
                  return popped;
        }

        public StackNode peek()
        {
                 return arr[top];
        }

        public boolean isEmpty()
        {
                  if(top==-1)
                    return true;
                  return false;
    }

}