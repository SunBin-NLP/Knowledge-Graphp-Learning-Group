

import java.util.HashSet;

public class Random_test {
public static void main(String[] args){
	 int num=Integer.parseInt(args[0]);
	 int low=Integer.parseInt(args[1]);
	 int high=Integer.parseInt(args[2]);
	 HashSet<Integer> set=new HashSet<Integer>();
	 randSet(num,low,high,set);
	 
}
public static void randSet(int num,int low,int high,HashSet<Integer> set){
	
	int i=0;
	while(i<num){
	int s=(int)(Math.random()*(high-low))+low;
	set.add(s);
	i++;
	}
	int setsize=set.size();
	if(setsize<num)
		randSet(num-setsize,low,high,set);
	for(int j:set){
		 System.out.println(j);
	 }
}
}
