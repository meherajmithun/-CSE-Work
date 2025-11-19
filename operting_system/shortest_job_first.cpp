#include<bits/stdc++.h>
using namespace std;

int main(){
    int n;
    int wt[20]  , tat[20];
    float awt =0 , atat = 0;
    cout<< "Enter the number of process: ";
    cin>>n;
    vector<pair<int,int>>vp;
    for(int i =0 ; i<n ; i++){
        cout<< "Enter burst time for process " << i << "-->";
        int a; cin>>a;
        vp.push_back({a,i});
    }
    sort(vp.begin() , vp.end());
    //for(auto u : vp) cout<<"bt -> "<<u.first<<" "<<"p_id ->" << u.second<<endl;
    wt[0] =0;
    tat[0] =vp[0].first;
    for( int i =1 ; i<n; i++){
       wt[i] = wt[i-1]+ vp[i-1].first;
       tat[i] = wt[i] + vp[i].first;
    }
    float total_wt =0, total_tat=0;
    for(int i =0; i<n;i++){
        total_wt= total_wt+wt[i];
        total_tat=total_tat+tat[i];
    }
    awt = total_wt/n;
    atat= total_tat/n;


    cout<<"Pid"<<"\tTAT"<< "\tWT"<<"\tBT"<<endl;
    for(int i=0;i<n;i++){
      cout<<"P"<<vp[i].second <<"\t"<<tat[i]<<"\t"<<wt[i]<<"\t"<<vp[i].first<<endl;
    }
    cout<<"Average waiting time: "<<awt<<endl;
    cout<<"Average turnaround time:"<<atat<<endl;
}
