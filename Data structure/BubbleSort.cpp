#include<bits/stdc++.h>
using namespace std;

void BubbleSort(int arr[] , int sz){
    for(int i=0; i<sz-1; i++){
        for(int j=0; j<sz-i-1; j++){
            if(arr[j] > arr[j+1]){
                swap(arr[j] , arr[j+1]);
            }
        }
        //cout<<arr[sz-i]<<" ";
    }
    cout<<endl;
    for(int i=0; i<sz; i++){
        cout<<arr[i]<<" ";
    }
}

int main(){
    int sz; cin >> sz;
    int arr[sz];
    for(auto &u : arr) cin >> u;
    BubbleSort(arr,sz);
}
