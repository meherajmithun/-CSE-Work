#include<bits/stdc++.h>
using namespace std;
int top = -1;
const int mx = 5;
int arr[mx];

void push(int data){
    if(top < mx-1){
        top = top + 1;
        arr[top] = data;
    }
    else{
        cout<<"size exceeded"<<endl;
    }
}

void pop(){
    top = top-1;
}

int main(){
    push(4);
    push(7);
    push(9);
    push(12);
    push(21);
    push(21);

    cout<<"Array before pop : ";
    for(int i=0; i<=top; i++){
        cout<<arr[i]<<" ";
    }
    cout<<endl;

    pop();
    pop();

    cout<<"Array after pop : ";
    for(int i=0; i<=top; i++){
        cout<<arr[i]<<" ";
    }

}