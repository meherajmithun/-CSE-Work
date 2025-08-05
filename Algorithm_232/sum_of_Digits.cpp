///Sum of Digits of a Number

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Inpu n : ");
    scanf("%d",&n);
    int sum = 0;
    while(n>0){
        int rem = n%10;
        sum += rem;
        n = n/10;
    }
    printf("Sum of digits is : %d",sum);
}
