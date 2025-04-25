///Binary_Search

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    cout<<"Enter array size : "; cin >> n;
    int key;
    cout<<"Enter Key : "; cin >> key;
    int arr[n];
    cout<<"Enter elements of the array : ";
    for(int i=0; i<n; i++) cin >> arr[i];
    sort(arr,arr+n);
    bool f = true;
    int left=0, right=n-1;
    while(left <= right){
        int mid = (left+right)/2;
        if(arr[mid] == key){
            cout<<"Key found in index : "<<mid+1<<endl;
            f = false;
            break;
        }
        else if(arr[mid] < key){
            cout<<left<<" "<<right<<endl;
            left = mid+1;
        }
        else if(arr[mid] > key){

            cout<<left<<" "<<right<<endl;
            right = mid-1;
        }
    }
    if(f){
        cout<<"Key doesn't found in the array"<<endl;
    }
}
