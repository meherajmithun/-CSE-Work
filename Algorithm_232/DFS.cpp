#include<bits/stdc++.h>
using namespace std;
const int N = 200;int adj[N][N];
int visited[N]={0};

void dfs(int u,int n){
    if(visited[u]) return;
    visited[u]=1;
    cout<<u<<" ";
    for(int i=1; i<=n; i++){
        if(adj[u][i]==1 and !visited[i]){
            dfs(i,n);
        }
    }

}

int main(){
    int n,m; cin>>n>>m;
    for(int i=0; i<m; i++){
        int u,v; cin>>u>>v;
        adj[u][v]=1; adj[v][u]=1;
    }
    cout<<"Enter source node :";
    int src; cin>>src;
    dfs(src,n);
}