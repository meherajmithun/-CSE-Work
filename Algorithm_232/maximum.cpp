///Find the Maximum of Two Numbers

#include<bits/stdc++.h>
using namespace std;

int main(){
    int a,b;
    printf("Input a : ");
    scanf("%d",&a);
    printf("Input b : ");
    scanf("%d",&b);
    if(a>b){
        printf("%d is maximum \n",a);
    }
    else if (a<b){
        printf("%d is maximum \n",b);
    }
    else{
        printf("%d and %d is equal \n",a,b);
    }
}
