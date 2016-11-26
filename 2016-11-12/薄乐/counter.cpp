#include<iostream>
#include<fstream>
#include<string>
#include<set>
#include<map>
#include<vector>
#include"basicmethod.h"
using namespace std;

set<string> entity;
set<string> relation;
map < string, pair<int, pair<set<string>, set<string>>>> triple;

void counter(string line);

void readfile()
{
	ifstream input("train_FB15k.txt");
	string line;
	while (getline(input, line))
	{
		counter(line);
	}
	input.close();
}
void counter(string line)
{
	set<string> s0;
	vector<string> vec;
	split(line, '\t', vec);
	entity.insert(vec[0]);
	entity.insert(vec[2]);
	auto ret = relation.insert(vec[1]);
	if (ret.second)
	{
		triple.insert(make_pair(vec[1], make_pair(1, make_pair(s0, s0))));
	}
	else
	{
		triple[vec[1]].first++;
	}
	triple[vec[1]].second.first.insert(vec[0]);
	triple[vec[1]].second.second.insert(vec[2]);
}
int getsparse(int Nr,int MaxNr,double min)
{//计算稀疏度
	double sp;
	sp = 1 - (1 - min)*Nr / MaxNr;
}

void outputfile()
{
	set<string>::iterator iter_en;
	set<string>::iterator iter_re;
	map < string, pair<int, pair<set<string>, set<string>>>>::iterator iter_tr;
	ofstream out1("entity.txt");
	ofstream out2("relation.txt");
	ofstream out3("triple.txt");
	int i = 0;
	for (iter_en = entity.begin(); iter_en != entity.end(); iter_en++, i++)
	{
		out1 << i << "\t" << *iter_en << "\n";
	}

	for (i = 0, iter_re = relation.begin(); iter_re != relation.end(); iter_re++, i++)
	{
		out2 << i << "\t" << *iter_re << "\n";
	}
	for (i = 0, iter_tr = triple.begin(); iter_tr != triple.end(); iter_tr++, i++)
	{
		out3 << iter_tr->first << "\t" << iter_tr->second.first << "\t" << iter_tr->second.second.first.size() <<
			"\t" << iter_tr->second.second.second.size() << "\n";
	}
}
int main()
{
	readfile();
	outputfile();
	return 0;
}