package com.biz.oops;

public class MyQueue<T> {

    MyLinkedList<T> myLinkedList;


    public MyQueue(){
        myLinkedList = new MyLinkedList<T>();
    }//Constructor initializes linked list


    //element to be enqueued adds element end of the list
    public void enqueue(T data){
        myLinkedList.add(data);
    }


    //removes top element in the list returns removed element
    public T dequeue(){
        return myLinkedList.pop(0);
    }

    //returns true if list is empty else returns false
    public boolean isEmpty(){
        return myLinkedList.isEmpty();
    }


    //returns size of the list
    public int size(){
        return myLinkedList.size();
    }
}






