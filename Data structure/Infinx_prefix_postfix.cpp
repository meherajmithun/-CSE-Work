#include<bits/stdc++.h>
using namespace std;

int precedence(char op) {
    if (op == '^') return 3;
    else if (op == '*' || op == '/') return 2;
    else if (op == '+' || op == '-') return 1;
    else return -1;
}


int main(){
    cout<<"Enter Infinix string : ";
    string s; cin>>s;
    int n = (int)s.size();
    stack<char>st;
    string postfix;

    for(int i=0; i<n; i++){
        if(isalnum(s[i])){
            postfix += s[i];
        }
        else if(s[i] == '('){
            st.push(s[i]);
        }
        else if(s[i] == ')'){
            while(!st.empty() and st.top() != '('){
                postfix += st.top();
                st.pop();
            }
            if(!st.empty()) st.pop();
        }
        else if(s[i] ==  '+' or s[i] == '-' or s[i] == '*' or s[i] == '/' or s[i] == '^'){
            
            if (s[i] == '^' and st.top() == '^') break;
            
            while(!st.empty() and precedence(s[i]) <= precedence(st.top())){
                postfix += st.top();
                st.pop();
            }
            st.push(s[i]);
        }
    }

    while (!st.empty()) {
        postfix += st.top();
        st.pop();
    }

    cout<<"Postfix string is : "<<postfix<<endl;

}