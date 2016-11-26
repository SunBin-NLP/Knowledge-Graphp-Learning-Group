import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;


public class count {
	public static void main(String[] args) throws IOException{
		float min=Float.parseFloat(args[0]);
		RandomAccessFile in=new  RandomAccessFile("tongji.txt","r");
		FileWriter f=new FileWriter("share.txt",true);
		FileWriter f1=new FileWriter("separate.txt",true);
		BufferedWriter out=new BufferedWriter(f);
		BufferedWriter out1=new BufferedWriter(f1);
		Map<String,Float> map=new LinkedHashMap<String,Float>();
		Map<String,Float> map1=new LinkedHashMap<String,Float>();
		Map<String,Float> map2=new LinkedHashMap<String,Float>();
		String line=in.readLine();
		int[] b=new int[3];
		while(line!=null){
			String[] a=line.split("\t");
			int a1=Integer.parseInt(a[1]);
			int a2=Integer.parseInt(a[2]);
			int a3=Integer.parseInt(a[3]);
			map.put(a[0], (1-min)*a1);//存储share的稀疏度
			map1.put(a[0], (1-min)*a2);//存储separate的头实体的稀疏度	
			map2.put(a[0], (1-min)*a3);//存储separate的尾实体的稀疏度
			if(a1>b[0]) b[0]=a1;//最大三元组个数
			if(a2>b[1]) b[1]=a2;//最大头实体个数
			if(a3>b[2]) b[2]=a3;//最大尾实体个数
			line=in.readLine();
			}
		System.out.println(b[0]+"\t"+b[1]+"\t"+b[2]);
		for(String key:map.keySet()){
			map.put(key, 1-map.get(key)/b[0]);
			map1.put(key, 1-map.get(key)/b[1]);
			map2.put(key, 1-map.get(key)/b[2]);
			out.write(key+"\t"+map.get(key));
			out.newLine();
			out.flush();
			out1.write(key+"\t"+"head:"+map1.get(key)+"   tail:"+map2.get(key));
			out1.newLine();
			out1.flush();
		}
		
	}

}
