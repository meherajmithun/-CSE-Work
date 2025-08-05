///Reverse a Number

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Input n : ");
    scanf("%d",&n);
    int tmp = n;
    int res = 0;
    while(n>0){
        int rem = n%10;
        res = res*10+rem;
        n = n/10;
    }
    printf("Reverse of %d is -> %d",tmp,res);
}
