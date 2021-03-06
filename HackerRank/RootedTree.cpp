#include <bits/stdc++.h>
#define rep(i, a, b) for(int i = a; i < b; i++)
#define S(x) scanf("%d",&x)
#define P(x) printf("%d\n",x)
using namespace std;
typedef long long int LL;

const int mod = 1000000007;
const int MAXN = 100005;
vector<int> g[MAXN];
int dep[MAXN];
int P[MAXN];
int _tm;
int tin[2 * MAXN];
int tout[2 * MAXN];
int n;
int L[MAXN][25];
LL bit1[2 * MAXN], bit2[2 * MAXN], bit3[2 * MAXN];

LL _pow(LL a, LL b){
    if(!b) return 1;
    if(b == 1) return a;
    if(b == 2) return (a * a) % mod;
    if(b & 1) return (a * _pow(a, b - 1)) % mod;
    return _pow(_pow(a, b / 2), 2);
}

void dfs(int c, int p, int d){
    P[c] = p;
    dep[c] = d;
    _tm++;
    tin[c] = _tm;
    rep(i, 0, g[c].size()){
        int u = g[c][i];
        if(u != p) dfs(u, c, d + 1);
    }
    _tm++;
    tout[c] = _tm;
}

void processLca(){
    int i, j;

    int N = n;
      for(i = 0; i < n; i++)
          for(j = 0; 1 << j < N; j++)
              L[i][j] = -1;

      for(i = 0; i < N; i++)
          L[i][0] = P[i];

      for(j = 1; 1 << j < N; j++)
         for(i = 0; i < N; i++)
             if (L[i][j - 1] != -1)
                 L[i][j] = L[L[i][j - 1]][j - 1];
}

int lca(int p, int q){
      int tmp, log, i;

      if (dep[p] < dep[q])
          tmp = p, p = q, q = tmp;

      for(log = 1; 1 << log <= dep[p]; log++);
      log--;

      for(i = log; i >= 0; i--)
          if (dep[p] - (1 << i) >= dep[q])
              p = L[p][i];
      if (p == q)
          return p;

      for(i = log; i >= 0; i--)
          if (L[p][i] != -1 && L[p][i] != L[q][i])
              p = L[p][i], q = L[q][i];
      return P[p];
}

void update(LL *bit, int idx, LL val){
    for(int i = idx; i <= _tm; i += i & -i) bit[i] += val;
}

LL query(LL *bit, int idx){
    LL res = 0;
    for(int i = idx; i; i -= i & -i){
        res += bit[i];
    }
    return res % mod;
}

LL QQQ(int x){
    LL res;
    LL c = dep[x];
    res = (query(bit1, tin[x]) * c) % mod;
    res += (query(bit2, tin[x]) * (((LL)c * c)%mod));
    res %= mod;
    res += query(bit3, tin[x]);
    return res % mod;
}

int main(){
    int e, r;
    scanf("%d%d%d", &n, &e, &r);
    r--;
    rep(i, 0, n - 1){
        int x, y;
        scanf("%d%d", &x, &y);
        x--; y--;
        g[x].push_back(y);
        g[y].push_back(x);
    }
    dfs(r,-1,0);
    processLca();
    while(e--){
        char s[5];
        scanf("%s", s);
        if(s[0] == 'U'){
            int T, V, K;
            scanf("%d%d%d", &T, &V, &K);
            T--;
            LL k = ((LL)K * _pow(2, mod - 2)) % mod;
            LL p = dep[T];
            LL val;
            val = (V - 2 * p * k + k) % mod;
            val = (val + mod) % mod;
            update(bit1, tin[T], val);
            update(bit1, tout[T] + 1, -val);
            val = k;
            update(bit2, tin[T], val);
            update(bit2, tout[T] + 1, -val);
            val = (p * p) % mod;
            val = (val * k) % mod;
            val -= p * (V + k);
            val %= mod;
            val += mod + V;
            val %= mod;
            update(bit3, tin[T], val);
            update(bit3, tout[T] + 1, -val);
        } else {
            int A, B;
            scanf("%d%d", &A, &B);
            A--; B--;
            LL ans = 0;
            int l = lca(A, B);
            ans = QQQ(A) + QQQ(B) - QQQ(l);
            if(P[l] != -1) ans -= QQQ(P[l]);
            ans %= mod;
            ans += mod;
            ans %= mod;
            printf("%lld\n", ans);
        }
    }
    return 0;
}
