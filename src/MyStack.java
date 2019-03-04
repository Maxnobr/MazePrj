/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maxno
 */
class MyStack<T> {

    private Node<T> top;
    int length = 0;

    void push(T data){
        top = new Node<>(top,data);
        length++;
    }
    
    T pop(){
        if(top != null){
            Node<T> t = top;
            top = top.previousNode;
            length--;
            return t.data;
        }
        return null;
    }
    
    T peek(){
        if(top != null) {
            return top.data;
        }
        return null;
    }
}

class Node<T>{
    T data;
    Node previousNode;
    
    Node(Node previousNode, T data){
        this.previousNode = previousNode;
        this.data = data;
    }
}
