///Binary_Search

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n; cin >> n;
    int key; cin >> key;
    int arr[n];
    for(int i=0; i<n; i++) cin >> arr[i];
    sort(arr,arr+n);
    bool f = true;
    int left=0, right=n-1;
    while(left <= right){
        int mid = (left+right)/2;
        if(arr[mid] == key){
            cout<<"Key found in index : "<<mid<<endl;
            f = false;
            break;
        }
        else if(arr[mid] < key){
            left = mid+1;
        }
        else if(arr[mid] > key){
            right = mid-1;
        }
    }
    if(f){
        cout<<"Key doesn't found in the array"<<endl;
    }
}
