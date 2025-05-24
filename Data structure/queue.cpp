#include <bits/stdc++.h>
using namespace std;

#define SIZE 5
int front = -1, rear = -1;
int arr_queue[SIZE];

void enqueue(int x) {
    if (rear == SIZE - 1) {
        cout << "Overflow" << endl;
    } else if (front == -1 && rear == -1) {
        front = rear = 0;
        arr_queue[rear] = x;
    } else {
        rear++;
        arr_queue[rear] = x;
    }
}

void dequeue() {
    if (front == -1 && rear == -1) {
        cout << "Underflow" << endl;
    } else if (front == rear) {
        front = rear = -1;
    } else {
        front++;
    }
}

void display() {
    if (front == -1 && rear == -1) {
        cout << "Queue is empty" << endl;
    } else {
        for (int i = front; i <= rear; i++) {
            cout << arr_queue[i] << " ";
        }
        cout << endl;
    }
}

int main() {
    enqueue(5);
    enqueue(7);
    enqueue(12);
    enqueue(25);
    enqueue(25); // Should show overflow if more added

    dequeue(); // remove 5
    enqueue(14); // insert 14 at the end

    display(); // expected: 7 12 25 25 14
}
