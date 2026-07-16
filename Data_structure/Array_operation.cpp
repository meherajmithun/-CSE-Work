///Array operation

#include <bits/stdc++.h>
using namespace std;
int main(){
    int n;
    cout<<"Enter array size : "; cin>>n;
    int arr[n];
    cout<<"Enter the elements of array: ";
    for(int i=0;i<n;i++) cin>>arr[i];
    int indx;
    cout<<"Enter the index to delete: "; cin>>indx;
    for(int i=indx-1;i<n;i++){
        arr[i]=arr[i+1];
    }
    cout<<"After delete an indx : ";
    for(int i=0;i<n-1;i++){
        cout<<arr[i]<<" ";
    }
    cout<<'\n';
}
