#include<bits/stdc++.h>
using namespace std;

int ackerman(int m, int n){
    if(m == 0){
        return n+1;
    }
    else if(m != 0 and n == 0){
        return ackerman(m-1 , 1);
    }
    else if (m != 0 and n != 0){
        return ackerman(m - 1, ackerman(m, n - 1));
    }
}

int main(){
    cout<<"Enter value of m : ";
    int m; cin>>m;
    cout<<"Enter value of n : ";
    int n; cin>>n;
    
    int result = ackerman(m,n);
    cout<<"Ackerman of ("<<m<<","<<n<<") = "<<result<<endl;

}