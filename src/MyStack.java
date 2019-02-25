/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maxno
 */
public class MyStack {
    
    Node top;
    int length = 0;
    
    public void push(Object data){
        top = new Node(top,data);
        length++;
    }
    
    public Object pop(){
        if(top != null){
            Node t = top;
            top = top.previousNode;
            length--;
            return t.data;
        }
        return null;
    }
    
    public Object peek(){
        if(top != null)
            return top.data;
        return null;
    }
}

class Node{
    Object data;
    Node previousNode;
    
    public Node(Node previousNode,Object data){
        this.previousNode = previousNode;
        this.data = data;
    }
}
