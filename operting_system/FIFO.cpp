#include<bits/stdc++.h>
using namespace std;

int main() {
    cout << "Enter reference string size : ";
    int n; cin >> n;

    cout << "Enter frame size : ";
    int sz; cin >> sz;

    vector<int> reference(n), check(1000, 0);
    cout << "Enter reference string : ";
    for (auto &a : reference) cin >> a;

    int cnt = 0;
    queue<int> q;

    cout << "Reference string : ";
    for (auto a : reference) cout << a << " "; cout <<endl;

    for (auto a : reference) {
        if (check[a] == 0) {
            cnt++;

            if (q.size() == sz) {
                int k = q.front();
                q.pop();
                check[k] = 0;
            }

            q.push(a);
            check[a] = 1;
        }
    }
    cout << "Total page fault : " << cnt << endl;
}