#include <bits/stdc++.h>
using namespace std;

bool cmp(pair<int, int> &p1, pair<int, int> &p2)
{
    double a = 1.0 * (p1.first / p1.second);
    double b = 1.0 * (p1.first / p1.second);
    return a > b;
}

int main()
{
    cout << "Enter total number of items : ";
    int n;
    cin >> n;
    vector<pair<int, int>> vp;
    cout << "Enter " << n << " items profit and weight : ";
    for (int i = 0; i < n; i++)
    {
        int profit, weight;
        cin >> profit >> weight;
        vp.push_back({profit, weight});
    }
    cout << "Enter bag size : ";
    int bag;
    cin >> bag;
    sort(vp.begin(), vp.end(), cmp);
    double total_profit = 0.0;

    for (int i = 0; i < n; i++)
    {
        double profit = vp[i].first;
        double weight = vp[i].second;
        if (weight <= bag)
        {
            total_profit += profit;
            bag -= weight;
        }
        else
        {
            total_profit += (1.0 * (profit / weight) * bag);
            break;
        }
    }
    cout << "Total profit is -> " << total_profit << endl;
}