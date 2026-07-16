#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

using namespace std;

// Modulo as requested
const int MOD = 10000;

void solve() {
    int n;
    if (!(cin >> n)) return;
    
    map<int, int> counts;
    for (int i = 0; i < n; ++i) {
        int a;
        cin >> a;
        counts[a]++;
    }

    long long total_cost = 0;
    
    // We treat 1s specially as they don't increase product costs.
    // For values > 1, removing them individually minimizes the product sum.
    for (auto const& [val, count] : counts) {
        if (val == 1) {
            // Group all 1s into one operation
            total_cost = (total_cost + 1) % MOD;
        } else {
            // Remove each > 1 value individually
            total_cost = (total_cost + (long long)val * count) % MOD;
        }
    }
    
    cout << total_cost << endl;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    
    int t;
    cin >> t;
    while (t--) {
        solve();
    }
    return 0;
}