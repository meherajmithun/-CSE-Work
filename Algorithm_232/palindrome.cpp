///Check if a Number is Palindrome

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
    if(res == tmp){
        printf("The number %d is palindorme \n",tmp);
    }
    else{
        printf("The number %d is not palindrome \n",tmp);
    }
}

