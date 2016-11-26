package kg_task_ycli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Count {
	public static final float theta_min = 0.0f;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//test getRandomSet
//		HashSet<Integer> s_random = new HashSet<Integer>();
//		Utils.getRandomSet(34, 39, 5, s_random);
//		Iterator<Integer> iterator = s_random.iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}	
	
		long start = System.currentTimeMillis();
		
		Map<String, Integer> hm_entity = new HashMap<String, Integer>();
		Map<String, Relation> hm_relation = new HashMap<String, Relation>();
		
		Map<String, ArrayList<Relation>> hm_relation_head = new HashMap<String, ArrayList<Relation>>();
		Map<String, ArrayList<Relation>> hm_relation_tail = new HashMap<String, ArrayList<Relation>>();
		       
		//String filename = "D:\\Documents\\EclipseWorkspace\\kg_task_ycli\\train.txt";
		String filename = "D:\\Documents\\EclipseWorkspace\\kg_task_ycli\\train_FB15k.txt";
		
		FileReader fr;
		try {
			fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			ArrayList<Relation> list;
			String s;
	        while((s = br.readLine()) != null) {
	            String[] words = s.split("\\s+");
	            if(!hm_entity.containsKey(words[0])){
	            	hm_entity.put(words[0], new Integer(words[0].hashCode()));
	            }
	            
	            if(!hm_entity.containsKey(words[1])){
	            	hm_entity.put(words[1], new Integer(words[1].hashCode()));
	            }
	            
	            Relation r;
	            if(hm_relation.containsKey(words[2])){
	                r = hm_relation.get(words[2]);
	            	r.count++;
	            }
	            else{
	                r = new Relation();
	            	r.id = words[2].hashCode();
	            	r.count = 1;
	            	r.s_head = new HashSet<String>();
	            	r.s_tail = new HashSet<String>();
	            	//r.r_type = Rel_Type.one2one;
	            	hm_relation.put(words[2], r);
	            }
	            r.s_head.add(words[0]);
	            r.s_tail.add(words[1]);
	            
	            //处理头实体部分       
	            if(hm_relation_head.containsKey(words[2])){
	            	list = hm_relation_head.get(words[2]);
	            }
	            else{
	            	list = new ArrayList<Relation>();
	            	hm_relation_head.put(words[2], list );
	            }
	            r = null;
	            for(Relation rel:list){
	            	if(rel.s_head.iterator().next().equals(words[0])){
	            		r = rel;
	            		break;
	            	}
	            }
	            if(r == null){
	            	r = new Relation();
	            	r.s_head = new HashSet<String>();
	            	r.s_tail = new HashSet<String>();
	            	r.s_head.add(words[0]);
	            	list.add(r);
	            }
	            r.s_tail.add(words[1]);
	            
	            //处理尾实体部分
	            if(hm_relation_tail.containsKey(words[2])){
	            	list = hm_relation_tail.get(words[2]);
	            }
	            else{
	            	list = new ArrayList<Relation>();
	            	hm_relation_tail.put(words[2], list );
	            }
	            r = null;
	            for(Relation rel:list){
	            	if(rel.s_tail.iterator().next().equals(words[1])){
	            		r = rel;
	            		break;
	            	}
	            }
	            if(r == null){
	            	r = new Relation();
	            	r.s_head = new HashSet<String>();
	            	r.s_tail = new HashSet<String>();
	            	r.s_tail.add(words[1]);
	            	list.add(r);
	            }
	            r.s_head.add(words[0]);	            	                     
	        }  
	        fr.close();
	        br.close();
	        
	        Utils.getSparseDegree(hm_relation, theta_min);
	        
	        int[] r_types = new int[4];
	        for ( String key : hm_relation.keySet()) {
	        	list = hm_relation_head.get(key);
	        	Rel_Type type;
	        	float tphr,hptr;
	        	int total_size_tail = 0;
	        	int total_size_head = 0;
//	        	for(Relation rel:list){
//	        		type = rel.s_head.size() < rel.s_tail.size() ? Rel_Type.one2more : Rel_Type.one2one;
//	        		if(type == Rel_Type.one2more){
//	        			hm_relation.get(key).r_type = type;
//	        			break;
//	        		}
//	            }
	        	for(Relation rel:list){
	        		total_size_tail += rel.s_tail.size();
	        	}
	        	tphr = total_size_tail/(float)list.size();
	        	
	        	list = hm_relation_tail.get(key);
//	        	for(Relation rel:list){
//	        		type = rel.s_tail.size() < rel.s_head.size() ? Rel_Type.more2one : Rel_Type.one2one;
//	        		if(hm_relation.get(key).r_type == Rel_Type.one2one){
//	        			if(type == Rel_Type.more2one){
//		        			hm_relation.get(key).r_type = type;
//		        		}
//	        		}
//	        		else{
//	        			if(type == Rel_Type.more2one){
//		        			hm_relation.get(key).r_type = Rel_Type.more2more;
//		        			break;
//		        		}
//	        		}
//	            }
	        	for(Relation rel:list){
	        		total_size_head += rel.s_head.size();
	        	}
	        	hptr = total_size_head/(float)list.size();
	        	if(tphr < 1.5 && hptr < 1.5){
	        		hm_relation.get(key).r_type = Rel_Type.one2one;
	        		r_types[0]++;
	        	}
	        	else if(tphr >= 1.5 && hptr < 1.5){
	        		hm_relation.get(key).r_type = Rel_Type.one2more;
	        		r_types[1]++;
	        	}
				else if(tphr < 1.5 && hptr >= 1.5){
					hm_relation.get(key).r_type = Rel_Type.more2one;
					r_types[2]++;
				}
				else if(tphr >= 1.5 && hptr >= 1.5){
					hm_relation.get(key).r_type = Rel_Type.more2more;
					r_types[3]++;
				}
	        	
//	        	type = hm_relation.get(key).r_type;
//	        	if(type == c.one2one)
//	        		r_types[0]++;
//	        	else if(type == Rel_Type.one2more)
//	        		r_types[1]++;
//	        	else if(type == Rel_Type.more2one)
//	        		r_types[2]++;
//	        	else
//	        		r_types[3]++;
	        }
	        
	        FileWriter result_entity = new FileWriter("result_entity.txt", true);
	        FileWriter result_relation = new FileWriter("result_relation.txt", true);
	        FileWriter result_count = new FileWriter("result_count.txt", true);
	        FileWriter result_relation_type = new FileWriter("result_relation_type.txt", true);
	        
			for ( String key : hm_entity.keySet()) {
				result_entity.write(key + "\t" + hm_entity.get(key).intValue() + "\r\n");
			}
			
			for ( String key : hm_relation.keySet()) {
				Relation r = hm_relation.get(key);
				result_relation.write(key + "\t" + r.id + "\r\n");
				result_count.write(key + "\t" + r.count + "\t" + r.s_head.size() + "\t" + r.s_tail.size() + "\t" + r.theta_head + "\t" + r.theta_tail + "\t" + r.r_type.toString() + "\r\n");
			}
			
			result_relation_type.write("1-to-1" + "\t" + r_types[0] + "\r\n" + 
					"1-to-N" + "\t" + r_types[1] + "\r\n" + 
					"N-to-1" + "\t" + r_types[2] + "\r\n" +
					"N-to-N" + "\t" + r_types[3]);

			result_entity.close();
			result_relation.close();
			result_count.close();
			result_relation_type.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		long end = System.currentTimeMillis();
		System.out.println("time:"+(end-start)+"ms");
	}

}

