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
    for(int i=0; i<n; i++){
        if(arr[i] == key){
            flag = true;
            indx = i;
            break;
        }
    }
    if(flag){
        printf("key found at index : %d\n",indx);
    }
    else{
        printf("Key not found \n");
    }
}
