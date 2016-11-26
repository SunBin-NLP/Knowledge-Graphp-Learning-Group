

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class count_relation {
public static void main(String[] args) throws IOException{
	RandomAccessFile in=new  RandomAccessFile("train.txt","r");
	FileWriter f=new FileWriter("test_tongji_relation2.txt",true);
	BufferedWriter out=new BufferedWriter(f);
    Map<String,Integer> map=new HashMap<String,Integer>();
    Map<String,String> map1=new HashMap<String,String>();
    Map<String,Integer> map6=new HashMap<String,Integer>();
    Map<String,String> map2=new HashMap<String,String>();
    Map<String,String> map4=new HashMap<String,String>();
    Map<String,Map<String,String>> map5=new HashMap<String,Map<String,String>>();
    List<Map<String,String>> list=new ArrayList<Map<String,String>>();
    List<Map<String,Map<String,String>>> list2=new ArrayList<Map<String,Map<String,String>>>();
	String line=in.readLine();
	while(line!=null){
		String[] a=line.split("\t");
		map2=new HashMap<String,String>();
		//map2.put(a[2],a[0]);
		map2.put(a[1],a[0]);
		list.add(map2);
		//map4=new HashMap<String,String>();
		//map5=new HashMap<String,Map<String,String>>();
		map4.put(a[0], a[2]);
		map5.put(a[1],map4);
		map4.put(a[0], a[1]);
		map5.put(a[2],map4);
		list2.add(map5);
		if(!map.containsKey(a[1])){
			map.put(a[1],1);
		}
		
		line=in.readLine();
		}
	int l=list.size();
	int I=0;//1 to 1
	int J=0;//1 to N
	int W=0;//N to 1
	int Q=0;//N to N	
	for(String key:map.keySet()){
		System.out.println(key);
		int i=0;
		int j=0;
		map1=new HashMap<String,String>();
	    map6=new HashMap<String,Integer>();
		for(int p=0;p<l;p++){
			String s=list.get(p).get(key);
			if(list2.get(p).containsKey(key)){
			String t=list2.get(p).get(key).get(s);//关系的头实体对应的尾实体
			if(s!=null&&t!=null){
			if(!map1.containsKey(s) )
			{
				map1.put(s, t);
				}else {
					map1.put(s, "false");
					}
			if(!map6.containsKey(t))
				{
				 map6.put(t,1);
				}else{
					map6.put(t, map6.get(t)+1);
					}	
			
			}
		}
			}
		//for(String p:map3.keySet()){System.out.println(p+"\t"+map3.get(p));}
		for(String k:map1.keySet()){
			if(map1.get(k)=="false") {
				i++;
			}
			if(map6.get(map1.get(k))!=null){
			if(map6.get(map1.get(k))!=1){
				j++;
			}
			}
			
		}
		if(i==0 && j==0) I++;
		if(i==0 && j!=0) J++;
		if(i!=0 && j==0) W++;
		if(i!=0 && j!=0) Q++;
		
		}
		
	out.write("1 to 1"+"\t"+I);
	out.newLine();
	out.write("1 to N"+"\t"+J);
	out.newLine();
	out.write("N to 1"+"\t"+W);
	out.newLine();
	out.write("N to N"+"\t"+Q);
	out.newLine();
	out.flush();
	
}
}