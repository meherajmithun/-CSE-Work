#include<bits/stdc++.h>
using namespace std;

void TOH(int n,int A, int B, int C){
    if(n>0){
        TOH(n-1,A,C,B);
        cout<<"Move a Disc from "<< A << " to "<<C<<endl;
        TOH(n-1,B,A,C);
    }
}

int main(){
    cout<<"Enter n : ";
    int tower; cin>>tower;

    TOH(tower,1,2,3);

}