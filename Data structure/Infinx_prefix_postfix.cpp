#include<bits/stdc++.h>
using namespace std;

int main(){
    string s; cin>>s;
    int n = (int)s.size();
    stack<int>st;

    for(int i=0; i<n; i++){
        if(s[i]>='A' and s[i] <= 'Z'){
            cout<<s[i];
        }
        else{
            if(s[i] == '('){
                stack<int>st2;
                i++;
                while(s[i] != ')'){
                    st2.push(s[i]);
                    i++;
                }
            }
        }
    }
}