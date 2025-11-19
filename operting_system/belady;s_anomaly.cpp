#include <bits/stdc++.h>
using namespace std;

int main()
{
    cout << "Enter reference string size : ";
    int n;
    cin >> n;

    vector<int> reference(n), check(1000, 0);
    cout << "Enter reference string : ";
    for (auto &a : reference)
        cin >> a;
    int last = n;
    vector<int> belady;
    for (int sz = 1; sz <= n; sz++)
    {

        int cnt = 0;
        queue<int> q;

        // cout << "Reference string : ";
        // for (auto a : reference) cout << a << " "; cout <<endl;

        for (auto a : reference)
        {
            if (check[a] == 0)
            {
                cnt++;

                if (q.size() == sz)
                {
                    int k = q.front();
                    q.pop();
                    check[k] = 0;
                }

                q.push(a);
                check[a] = 1;
            }
        }
        if (cnt > last)
        {
            belady.push_back(sz - 1);
            belady.push_back(sz);
        }
        last = cnt;

        while (q.size())
            q.pop();
        for (int i = 0; i < check.size(); i++)
            check[i] = 0;
    }
    if (belady.size() == 0)
        cout << "Belady's Anamoly doesn't exists\n"
             << endl;
    else
    {
        cout << "Belady's Anamoly exists for : ";
        for (auto a : belady)
            cout << a << " ";
    }
}