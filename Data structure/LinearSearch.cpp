///Linear_Search
/// '0' based index

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n; cin >> n;
    int key; cin >> key;
    int arr[n];
    bool f = true;
    for(int i=0; i<n; i++) cin >> arr[i];
    for(int i=0; i<n; i++){
        if(arr[i] == key){
            cout<<"Key found in index : "<<i<<'\n';
            f = false;
            break;
        }
    }
    if(f){
        cout<<"Key Doesn't found"<<'\n';
    }

}

