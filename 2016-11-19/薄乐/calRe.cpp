#include<iostream>
#include<fstream>
#include<string>
#include<set>
#include<map>
#include<vector>
#include"basicmethod.h"
using namespace std;

set<string> relation;
map<string, pair<vector<string>, vector<string>>> rela;
void addRe(string line);
int oneToone = 0, oneTomany = 0, manyToone = 0, manyTomany = 0;
void readfile()
{
	ifstream input("train_FB15k.txt");
	string line;
	while (getline(input, line))
	{
		addRe(line);
	}
	input.close();
}
void addRe(string line)
{
	vector<string> vec;
	split(line, '\t', vec);

	vector<string> v0;
	auto ret = relation.insert(vec[2]);
	if (ret.second)
	{
		rela.insert(make_pair(vec[2], make_pair(v0,v0)));
	}
	rela[vec[2]].first.push_back(vec[0]);
	rela[vec[2]].second.push_back(vec[1]);

}
void calRe()
{
	map<string, pair<vector<string>, vector<string>>>::iterator iter_rela;
	for (iter_rela = rela.begin(); iter_rela != rela.end(); iter_rela++)
	{
		int unihead = 0, unitail = 0;
		int head = iter_rela->second.first.size();
		int tail = iter_rela->second.second.size();
		set<string> s0,s1;
		for (int i = 0; i < head; i++)
		{
			s0.insert(iter_rela->second.first[i]);
		}
		for (int i = 0; i < tail; i++)
		{
			s1.insert(iter_rela->second.second[i]);
		}
		unihead = s0.size();
		unitail = s1.size();
		double tphr = (1.0)*tail / unihead;
		double hptr = (1.0)*head / unitail;
		if (tphr < 1.5)
		{
			if (hptr < 1.5)
				oneToone++;
			else
				manyToone++;
		}
		else
		{
			if (hptr < 1.5)
				oneTomany++;
			else
				manyTomany++;
		}
	}
}
int main()
{
	readfile();
	calRe();
	ofstream out("CorrRela.txt");
	out << "1-1\t" << oneToone << "\n" << "1-n\t" << oneTomany << "\n"
		<< "n-1\t" << manyToone << "\n" << "n-n\t" << manyTomany << "\n";

	return 0;
}