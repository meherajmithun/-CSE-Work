#include<bits/stdc++.h>
using namespace std;

int main(){
    cout<<"Enter total number of items -> ";
    int n; cin>>n;
    cout<<"Enter "<<n<<" items profit and weight -> ";
    int profit[n],weight[n];
    for(int i=0; i<n; i++){
        cin>>profit[i]>>weight[i];
    }
    cout<<"Enter bag size -> ";
    int bag; cin>>bag;
    int dp[bag+1] = {0};
    
    for(int i=0; i<n; i++){
        for(int j=bag; j>=weight[i]; j--){
            dp[j] = max(dp[j],profit[i]+dp[j-weight[i]]);
        }
    }
    cout<<dp[bag];
}