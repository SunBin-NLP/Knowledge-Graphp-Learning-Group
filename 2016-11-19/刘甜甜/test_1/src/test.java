import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class test {
	public static void main(String[] args) throws IOException{
		RandomAccessFile in=new  RandomAccessFile("train.txt","r");
		FileWriter f1=new FileWriter("relation.txt",true);
		FileWriter f2=new FileWriter("entity.txt",true);
		FileWriter f3=new FileWriter("tongji.txt",true);
		BufferedWriter out1=new BufferedWriter(f1);
		BufferedWriter out2=new BufferedWriter(f2);
		BufferedWriter out3=new BufferedWriter(f3);
		Map<String,Integer> map=new HashMap<String,Integer>();
		Map<String,Integer> map1=new HashMap<String,Integer>();
		Map<String, String> map3=new HashMap<String,String>();
		List<Map<String,String>> listMap1 = new ArrayList<Map<String,String>>();  
		List<Map<String,String>> listMap2 = new ArrayList<Map<String,String>>();  
		List<Map<String,String>> listMap3 = new ArrayList<Map<String,String>>();  
		List<Map<String,String>> listMap4 = new ArrayList<Map<String,String>>();
        Set<Map> setMap = new HashSet<Map>(); 
        Set<Map> setMap1 = new HashSet<Map>();
	    Map<String,String> map5=new HashMap<String,String>();
		int i=0;
		int j=0;
		
		try {
			String line=in.readLine();
			//把[关系，头实体]，[关系，尾实体]分别存储在listMap1，listMap3
			while(line!=null){	
		     String[] a=line.split("\t");
		     map3=new HashMap<String,String>();
		     map3.put(a[1], a[0]);
		    // map3.put(a[2], a[0]);
		     listMap1.add(map3);
		     map5=new HashMap<String,String>();
		     map5.put(a[1], a[2]);
		    // map5.put(a[2], a[1]);
		     listMap3.add(map5);
		     //给关系编写ID，顺便记录每个关系有多少行，即三元组个数。
		     if(!map.containsKey(a[1]))
		     {
		     out1.write((i++)+"     "+a[1]); 
		     out1.newLine();
		     out1.flush();
		     map.put(a[1],1);
		    
		 	}else{
		    	map.put(a[1], map.get(a[1])+1);
		    	}
	    //给实体编写ID
		      if(!map1.containsKey(a[0]) )
		     {
		     out2.write((j++)+"     "+a[0]);
		     out2.newLine();
		     out2.flush();
		    map1.put(a[0],1);
		     }
		      if( !map1.containsKey(a[2])){
		      out2.write((j++)+"     "+a[2]);
		      out2.newLine();
		      out2.flush();
		      map1.put(a[2],1);
		    }
		    line=in.readLine();
			}  
		 out1.close();
		out2.close();
		//统计关系连接的头实体和尾实体数目
		//去掉重复的,如果集合中不包含要添加的则添加对象
		for(Map<String,String> map9 : listMap1){  
            if(setMap.add(map9)){  
                listMap2.add(map9);  
            } 
		}
            for(Map<String,String> map8 : listMap3){  
                if(setMap1.add(map8)){  
                    listMap4.add(map8);  
                }  
        }  
           //统计每个关系连接的头实体和尾实体
		int l1=listMap2.size();
		int l2=listMap4.size();
		
		for(String key:map.keySet()){
			int t=0;
			int h=0;
			if(l1<=l2){
		for( int p=0;p<l1;p++){
			if(listMap2.get(p).containsKey(key))
			t++;
			if(listMap4.get(p).containsKey(key))
				h++;
		}
		
		for( int p=l1;p<l2;p++){
			if(listMap4.get(p).containsKey(key))
			h++;
		}		
		}
		else{
			for(int p=0;p<l2;p++){
				if(listMap2.get(p).containsKey(key))
					t++;
					if(listMap4.get(p).containsKey(key))
						h++;
			}
			for(int p=l2;p<l1;p++){
				if(listMap2.get(p).containsKey(key))
					t++;
			}
		}
		out3.write(key+"\t"+map.get(key)+"\t"+t+"\t"+h);
		out3.newLine();
		out3.flush();
		}} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
