///Factorial of a Number

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Input n : ");
    scanf("%d",&n);
    int factorial = 1;
    for(int i=1; i<=n; i++){
        factorial = factorial*i;
    }
    printf("Factorial of %d is : %d \n",n,factorial);
}
