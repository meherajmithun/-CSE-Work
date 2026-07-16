#include<bits/stdc++.h>
using namespace std;

void SelectionSort(int arr[] , int sz){
    for(int i=0; i<sz-1; i++){
        int Current_Minimum = i;
        for(int j=i+1; j<sz; j++){
            if(arr[j] < arr[Current_Minimum]){
                Current_Minimum = j;
            }
        }
        if(i != Current_Minimum){
            swap(arr[i] , arr[Current_Minimum]);
        }
    }
    for(int i=0; i<sz; i++){
        cout<<arr[i]<<" ";
    }
    cout<<endl;
}

int main(){
    int sz; cin >> sz;
    int arr[sz];
    for(auto &u : arr) cin >> u;

    SelectionSort(arr,sz);
}
