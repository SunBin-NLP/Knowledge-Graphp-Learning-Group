import java.io.BufferedWriter;
	import java.io.FileWriter;
	import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class count_relation_FB15k {
	public static void main(String[] args) throws IOException{
		RandomAccessFile in=new  RandomAccessFile("train_FB15k.txt","r");
		FileWriter f=new FileWriter("test_tongji_relation.txt",true);
		BufferedWriter out=new BufferedWriter(f);
	    Map<String,Integer> map=new HashMap<String,Integer>();
	    Map<String,Integer> map1=new HashMap<String,Integer>();
	    Map<String,Integer> map6=new HashMap<String,Integer>();
	    Map<String,String> map2=new HashMap<String,String>();
	    Map<String,String> map4=new HashMap<String,String>();
	    List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    List<Map<String,String>> list2=new ArrayList<Map<String,String>>(); 
		String line=in.readLine();
		while(line!=null){
			String[] a=line.split("\t");
			map2=new HashMap<String,String>();
			map2.put(a[2],a[0]);
			list.add(map2);
			map4=new HashMap<String,String>();
			map4.put(a[2], a[1]);
			list2.add(map4);
			if(!map.containsKey(a[2])){
				map.put(a[2],1);
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
			map1=new HashMap<String,Integer>();
		    map6=new HashMap<String,Integer>();
			for(int p=0;p<l;p++){
				String s=list.get(p).get(key);
				if(list2.get(p).containsKey(key)){
				String t=list2.get(p).get(key);//关系的头实体对应的尾实体
				if(s!=null&&t!=null){
				if(!map1.containsKey(s) )
				{
					map1.put(s, 1);
					}else {
						map1.put(s, map1.get(s)+1);
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
			float l1=map1.size();
			float l2=map6.size();
			float m=0;
			float n=0;
			for(String k:map1.keySet()){			
				float p=map1.get(k);
				m+=p;
			}
			float tph=m/l1;
			for(String k1:map6.keySet()){
				float p=map6.get(k1);
				n+=p;
			}
			float hpt=n/l2;			
			if(tph<1.5 && hpt<1.5) I++;
			if(tph>=1.5 && hpt<1.5 ) J++;
			if(tph<1.5 && hpt>=1.5) W++;
			if(tph>=1.5 && hpt>=1.5) Q++;		
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