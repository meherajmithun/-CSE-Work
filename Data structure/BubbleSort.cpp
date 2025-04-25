#include<bits/stdc++.h>
using namespace std;

void BubbleSort(int arr[] , int sz){
    for(int i=0; i<sz-1; i++){
        for(int j=0; j<sz-i-1; j++){
            if(arr[j] > arr[j+1]){
                swap(arr[j] , arr[j+1]);
            }
        }
    }
    cout<<"After sorting : ";
    for(int i=0; i<sz; i++){
        cout<<arr[i]<<" ";
    }
    cout<<endl;
}
int main(){
    int sz;
    cout<<"Enter size of the array : "; cin >> sz;
    int arr[sz];
    cout<<"Enter element of the array : ";
    for(auto &u : arr) cin >> u;
    BubbleSort(arr,sz);
}
