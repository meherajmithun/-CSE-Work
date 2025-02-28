///First_Patter Matching algorithm

#include<bits/stdc++.h>
using namespace std;

int main(){
    string s1; cin >> s1;
    string s2; cin >> s2;
    int a = s1.size();
    int b = s2.size();
    int sz = a-b+1;
    bool flag = true;
    for(int i=0; i<sz; i++){
        bool f = true;
        for(int j=0; j<b && f==true; j++){
            if(s1[i+j] != s2[j]){
                f = false;
            }
        }
        if(f==true){
            cout<<"Pattern Matching in index : "<<i<<endl;
            flag = false;
            break;
        }
    }
    if(flag){
        cout<<"Pattern doesn't matching in the string"<<endl;
    }
}
