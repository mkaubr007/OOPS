package com.biz.stock;

class MyNode<T>{
    T data;	//data to be stored
    MyNode<T> next;	//reference to the next node

    MyNode(T data){
        this.data = data;
        next = null;
    }
}
