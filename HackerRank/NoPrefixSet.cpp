#include <iostream>
#include <cstring>
#include <algorithm>
using namespace std;
class Trie
{
public:
	bool is_element;
	Trie *p[10];
	Trie()
	{
		is_element = false;
		memset(p,0,sizeof(p));
	}
	~Trie();

	bool add(string s);
};
bool Trie::add(string s)
{
	Trie *ch = this;
	for (int i = 0; i < s.length(); ++i)
	{
		int cur = s[i] - 'a';
		if (ch->p[cur] == NULL)
			ch->p[cur] = new Trie;
		else
			if (ch->p[cur]->is_element)
				return false;
		ch = ch->p[cur];
	}
	ch -> is_element = true;
	for (int i = 0; i < 10; ++i)
		if (ch->p[i] != NULL)
			return false;
	return true;
}
int main()
{
	int n;
	Trie *trie = new Trie;
	cin>>n;
	for (int i = 0; i < n; ++i)
	{
		string s;
		cin>>s;
		if (!trie->add(s))
		{
			cout<<"BAD SET"<<endl<<s<<endl;
			return 0;
		}
	}
	cout<<"GOOD SET"<<endl;
	return 0;
}
