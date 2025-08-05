///Binary search
///Linear Search

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Input array size : ");
    scanf("%d",&n);
    int arr[n];
    printf("Input the array : ");
    for(int i=0; i<n; i++){
        scanf("%d",&arr[i]);
    }
    int key;
    printf("Input key : ");
    scanf("%d",&key);
    bool flag = false;
    int indx;
    int l=0,r=n-1;
    while(l<=r){
        int mid = (l+r)/2;
        if(arr[mid] == key){
            flag = true;
            indx = mid;
            break;
        }
        else if(arr[mid] < key){
            l = mid+1;
        }
        else{
            r = mid-1;
        }
    }

    if(flag){
        printf("key found at index : %d\n",indx);
    }
    else{
        printf("Key not found \n");
    }
}

