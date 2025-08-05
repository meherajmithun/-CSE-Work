///Print First N Fibonacci Numbers

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Input n : ");
    scanf("%d",&n);
    int a=0,b=1;
    int arr[n];
    for(int i=0; i<n; i++){
        arr[i] = a;
        int tmp = a;
        a = b;
        b = tmp+b;
    }
    printf("First %d Fibonacci numbers is : ",n);
    for(int i=0; i<n; i++){
        printf("%d ",arr[i]);
    }
    printf("\n");
}
