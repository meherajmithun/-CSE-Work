#include<bits/stdc++.h>
using namespace std;

int main(){
    ios_base::sync_with_stdio(0);
    cin.tie(0); cout.tie(0);

    int tc; cin>>tc;
    while(tc--){
        int n; cin>>n;
        string s; cin>>s;
        vector<int>v(n);
        bool bl = 0;
        for(int i=0; i<n; i++){
            v[i] = i+1;
            if(s[i] == 'B') bl = 1;
        }
        if(!bl) cout<<"-1\n";
        else{
            int j=0;
            for(int i=0; i<n; i++){
                if(s[i] == 'B'){
                    reverse(v.begin()+i, v.begin()+j);
                    j = i;
                }
            }
            for(auto u : v) cout<<u<<" "; cout<<endl;
        }
    }
}
