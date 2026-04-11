#include<bits/stdc++.h>
using namespace std;
const int mx = 100;
int adj[mx][mx];
int q[mx];
int front=-1,rear=-1;

void enqueue(int x){
    if( (rear+1)%mx==front ){
        cout<<"Queue overflow\n"; return;
    }
    if(front==-1) front=0;
    rear=(rear+1)%mx;
    q[rear]=x;
}

bool isempty(){
    return front==-1;
}

int dequeue(){
    int x = q[front];
    if(front==rear) front=rear=-1;
    else front=(front+1)%mx;
    return x;
}

int main(){
    cout<<"Enter number of nodes : ";
    int n; cin>>n;
    cout<<"Enter edges : ";
    int e; cin>>e;
    cout<<"Enter "<<e<<" edges : ";
    for(int i=0; i<e; i++){
        int u,v; cin>>u>>v;
        adj[u][v]=1;
        adj[v][u]=1;
    }

    cout<<"Enter source node : ";
    int src; cin>>src;

    int visited[mx]={0};
    enqueue(src);
    visited[src]=1;
    int dist[mx]={0};
    cout<<"BFS traversal : ";
    while(!isempty()){
        int u = dequeue();
        cout<<u<<" ";
        for(int i=1; i<=n; i++){
            if(adj[u][i]==1 and !visited[i]){
                enqueue(i);
                visited[i]=1;
                dist[i] = dist[u]+1;
            }
        }
    }
    cout<<endl;
    for(int i=1; i<=n; i++) cout<<dist[i]<<" "; cout<<endl;

}