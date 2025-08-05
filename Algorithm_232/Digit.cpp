///Count Digits of a Number

#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    printf("Input n : ");
    scanf("%d",&n);
    int tmp = n;
    int ans = 0;
    while(n>0){
        n = n/10;
        ans++;
    }
    printf("The number %d has %d digits",tmp,ans);
}
