///Sum of First N Natural Numbers

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Enter n : ");
    scanf("%d",&n);
    int sum = 0;
    for(int i=1; i<=n; i++){
        sum += i;
    }
    printf("Sum of first %d natural number is %d",n,sum);
}
