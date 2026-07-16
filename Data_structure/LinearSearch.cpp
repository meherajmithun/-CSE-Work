///Linear_Search

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    cout<<"Enter array size : "; cin >> n;
    int key;
    cout<<"Enter key : "; cin >> key;
    int arr[n];
    bool f = true;
    cout<<"Enter element of the array : ";
    for(int i=0; i<n; i++) cin >> arr[i];
    for(int i=0; i<n; i++){
        if(arr[i] == key){
            cout<<"Key found in index : "<<(i+1)<<'\n';
            f = false;
            break;
        }
    }
    if(f){
        cout<<"Key Doesn't found"<<'\n';
    }
}
