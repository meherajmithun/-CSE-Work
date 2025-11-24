#include<bits/stdc++.h>
using namespace std;


int main(){
	int wt[20],tat[20],bt[20];	
	cout<<"Enter the number of process : ";
	int n; cin>>n;

	for(int i=0; i<n; i++){
		cout<<"Enter burst time of process  "<<i<<" -> ";
		cin>>bt[i];
	}
	float awt=0,atat=0;

	tat[0]=bt[0];
	wt[0]=0;
	for(int i=1; i<n; i++){
        wt[i] = wt[i-1]+bt[i-1];
        tat[i] = wt[i]+bt[i];
	}
	for(int i=0; i<n; i++){
        awt += wt[i];
        atat += tat[i];
	}
	cout<<"pid"<<"\tTAT"<<"\tWT"<<"\tBT"<<endl;

    for(int i=0; i<n; i++){
        cout<<"p"<<i<<"\t"<<tat[i]<<"\t"<<wt[i]<<"\t"<<bt[i]<<endl;
    }

	cout<<"average waiting time : "<<awt/n<<endl;
	cout<<"average turnaround time : "<<atat/n<<endl;


}
