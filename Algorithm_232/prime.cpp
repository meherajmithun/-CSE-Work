///Check if a Number is Prime

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Enter n : ");
    scanf("%d",&n);
    bool flag = false;
    for(int i=2; i<n; i++){
        if(n%i == 0){
            flag = true;
            break;
        }
    }
    if(flag){
        printf("%d is not prime \n",n);
    }
    else{
        printf("%d is prime \n", n);
    }
}
