#include<bits/stdc++.h>
using namespace std;
const int mp = 5;
int queue_arr[mp];
int stack_arr[mp];
int top = -1;
int rear = -1,fron=-1;

void stak(){
    cout<<"Enter critical patient id : ";
    int id; cin>>id;
    if(top<mp){
        top++;
        stack_arr[top] = id;
    }
    else {
        cout<<"Maximum patient size exceeded \n";
        return;
    }
}

void queu(){
    cout<<"Enter non-critical patient id : ";
    int id; cin>>id;
    if (rear == mp - 1) {
        cout << "Overflow" << endl;
    } else if (fron == -1 && rear == -1) {
        fron = rear = 0;
        queue_arr[rear] = id;
    } else {
        rear++;
        queue_arr[rear] = id;
    }
}

void treat_critical(){
    top = top-1;
}

void treat_non_critical(){
    fron++;
}

void display(){
    cout<<"Current critical patient waiting list with their id : ";
    for(int i=0; i<=top; i++) cout<<stack_arr[i]<<" ";
    cout<<endl;

    cout<<"Current non-critical patient waiting list with their id : ";
    for(int i=fron; i<=rear; i++) cout<<queue_arr[i]<<" ";
    cout<<endl;

}

int main(){
    while(1){
        cout<<"task 1. adding patient "<<endl;
        cout<<"task 2. treating patient "<<endl;
        cout<<"task 3. display patient "<<endl;
        cout<<"task 4. stop treatment and adding . "<<endl;

        cout<<"Enter task : ";
        int task; cin>>task;

        if(task == 1){
            cout<<"type 1 . critical patient "<<endl;
            cout<<"type 2 . non-critical patient "<<endl;

            cout<<"Enter patient type for admit : ";
            int type ; cin>>type;
            if(type == 1){
                stak();
            }
            else {
                queu();
            }
        }

        else if(task == 2){
            cout<<"type 1 . treat critical patient "<<endl;
            cout<<"type 2 . treat non-critical patient "<<endl;

            cout<<"Enter patient type for treatment : ";
            int type ; cin>>type;
            if(type == 1){
                treat_critical();
            }
            else{
                treat_non_critical();
            }
        }
        else if(task == 3) {
            display();
        }
        else {
            break;
        }
    }
}

