#include<bits/stdc++.h>
using namespace std;

int main(){
    int n; cout<<"Enter number of process : ";
    cin>>n;
    int bt[n],rt[n],wt[n]={0},tat[n];

    cout<<"Enter burst time : \n";

    for(int i=0; i<n; i++){
        cout<<"p "<<i<<" : ";
        cin>>bt[i];
        rt[i]=bt[i];
    }
    cout<<"Enter time quantum : ";
    int quantum; cin>>quantum;

    int time = 0;
    while(1){
        bool done = 1;
        for(int i=0; i<n; i++){
            if(rt[i]>0){
                done=0;
                if(rt[i]>quantum){
                    // cout<<i<<" ";// jdi gannt chart bole
                    time += quantum;
                    rt[i] -= quantum;
                }
                else{
                    // cout<<i<<" ";// jdi gannt chart bole
                    time += rt[i];
                    wt[i] = time-bt[i];
                    rt[i] = 0;
                }
            }
        }
        if(done) break;
    }
    for (int i = 0; i < n; i++) {
        tat[i] = bt[i] + wt[i];
    }
    cout << "\n\nProcess\tBT\tWT\tTAT\n";
    for (int i = 0; i < n; i++) {
        cout << "P" << i+1 << "\t" << bt[i]
             << "\t" << wt[i]
             << "\t" << tat[i] << "\n";
    }
}