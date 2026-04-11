#include<bits/stdc++.h>
using namespace std;
const int mx=100,INF=1000;

int main(){
    int n,m; cin>>n>>m;
    int adj[mx][mx];

    for(int i=1;i<=n; i++){
        for(int j=1; j<=n; j++){
            if(i==j) adj[i][j]=0;
            else adj[i][j]=INF;
        }
    }

    for(int i=0; i<m; i++){
        int u,v,w; cin>>u>>v>>w;
        adj[u][v]=w;
    }
    for (int k = 1; k <= n; k++) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (adj[i][k] < INF && adj[k][j] < INF) {
                    adj[i][j] = min(adj[i][j], adj[i][k] + adj[k][j]);
                }
            }
        }
    }
    cout << "All-pairs shortest distances:\n";
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (adj[i][j] == INF) cout << "INF ";
            else cout << adj[i][j] << " ";
        }
        cout << endl;
    }
    
}