#include<bits/stdc++.h>
using namespace std;
#define n 5
int front = -1,back=-1;
int arr_queue[n];

void endqueue(int x){
    if(back == n-1){
        cout<<"Overflow"<<endl;
    }
    else if(front == -1 and back == -1){
        front = 0,
        back = 0;
        arr_queue[back] = x;
    }
    else{
        back++;
        arr_queue[back] = x;
    }
}

void dequeue(){
    if(front == -1 and back == -1){
        cout<<"Overflow"<<endl;
    }
    else if(front == back){
        //cout<<queue[front]<<endl;
        back = -1;
        front = -1;
    }
    else{
        front++;
    }
}

void display(){
    for(int i=front; i<=back; i++){
        cout<<arr_queue[i]<<" ";
    }
    cout<<endl;
}

int main(){
    //It mean's push
    endqueue(5);
    endqueue(7);
    endqueue(12);
    endqueue(25);
    endqueue(25);
    
    dequeue(); // means pop
    endqueue(14);
    
    display();
}