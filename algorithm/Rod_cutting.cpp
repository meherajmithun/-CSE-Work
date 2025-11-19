#include<bits/stdc++.h>
using namespace std;

int main(){
    cout<<"Enter size of the rod -> ";
    int n; cin>>n;
    int price[n];
    cout<<"Enter "<<n<<" items price -> ";
    for(int i=0; i<n; i++) cin>>price[i];
    int dp[n+1]={0};
    
    for(int i=1; i<=n; i++){
        for(int j=1; j<=i; j++){
            dp[i] = max(dp[i],price[j-1]+dp[i-j]);
        }
    }
    cout<<dp[n];
}