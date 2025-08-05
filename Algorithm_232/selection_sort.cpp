///Selection Sort

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
    for(int i=0; i<n; i++){
        int mn = i;
        for(int j=i+1; j<n; j++){
            if(arr[j] < arr[mn]){
                mn = j;
            }
        }
        if(mn != i){
            swap(arr[i],arr[mn]);
        }
    }
    printf("Sorted array is : ");
    for(int i=0; i<n; i++){
        printf("%d ",arr[i]);
    }
    printf("\n");
}
