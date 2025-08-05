///Bubble Sort

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
    for(int i=0; i<n-1; i++){
        bool Swap = false;
        for(int j=0; j<n-i-1; j++){
            if(arr[j] > arr[j+1]){
                swap(arr[j],arr[j+1]);
                Swap = true;
            }
        }
        if(!Swap){
            break;
        }
    }
    printf("Sorted array is : ");
    for(int i=0; i<n; i++){
        printf("%d ",arr[i]);
    }
    printf("\n");
}
