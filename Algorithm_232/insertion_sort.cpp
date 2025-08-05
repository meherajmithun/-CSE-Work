///Insertion Sort

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
        int value = arr[i];
        int j = i - 1;

        while (j >= 0 && arr[j] > value) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = value;
    }

    printf("Sorted array is : ");
    for(int i=0; i<n; i++){
        printf("%d ",arr[i]);
    }
    printf("\n");
}
