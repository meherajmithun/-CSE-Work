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
    cout<<"Enter source node and destination : ";
    int src,dest; cin>>src>>dest;
    int dis[mx]={0},parent[mx];
    memset(parent , -1, sizeof(parent));

    for(int i=1;i<=n; i++) dis[i]=INF;
    dis[src]=0;

    for(int i=1; i<=n; i++){
        for(int u=1; u<=n; u++){
            if(dis[u]==INF) continue;
            for(int v=1; v<=n; v++){
                if(adj[u][v] != INF and dis[u]+adj[u][v]<dis[v]){
                    dis[v] = dis[u]+adj[u][v];
                    parent[v] = u;
                }
            }
        }
    }

    for(int i=1; i<=n; i++){
        if(dis[i]==INF) cout<<"INF ";
        else cout<<dis[i]<<" ";
    }
    if(dis[dest]==INF){
        cout<<"NO Path exists\n"; return 0;
    }
    cout<<endl;
    int node=dest;
    vector<int>path;
    while(node!=-1){
        path.push_back(node);
        node = parent[node];
    }
    reverse(path.begin() , path.end());
    cout<<"Shorest path from "<<src<<" to "<<dest<<" : ";
    for(auto a : path) cout<<a<<" "; cout<<endl;
    
}