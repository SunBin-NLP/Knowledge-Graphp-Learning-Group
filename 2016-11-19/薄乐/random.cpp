#include<iostream>
#include<string>
#include<vector>
#include<time.h>
#include<math.h>
#define N 999
using namespace std;

int a[N];

int getrandom(int start, int end, int k)
{//生成随机数
	int b[N];
	for (int i = start, j = 0; i < end + 1; i++, j++)
	{
		b[j] = i;
	}
	int Max = end - start + 1;
	srand((unsigned)time(NULL));
	for (int i = 0; i < k; i++)
	{
		if (Max == 0) return 0;
		int index = (int)rand() % Max;
		a[i] = b[index]; 
		b[index] = b[Max - 1];
		Max--;
	}
	return 0;
}
