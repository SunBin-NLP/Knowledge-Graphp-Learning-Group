package kg_task_ycli;

import java.util.HashSet;
import java.util.Map;

enum Rel_Type{one2one,one2more,more2one,more2more}

public class Utils {

	    public static void getRandomSet(int min, int max, int n, HashSet<Integer> set) {
	        if (n > (max - min + 1) || max < min || set == null) {
	            return;
	        }
	        for (int i = 0; i < n - set.size(); i++) {
	            int num = (int) (Math.random() * (max - min)) + min;
	            set.add(num);
	        }
	        if (set.size() < n) {
	        	getRandomSet(min, max, n , set);
	        }
	    }
	    
	    public static void getSparseDegree(Map<String, Relation> hm_relation, float theta_min){
	    	int head_max = 0;
	    	int tail_max = 0;
	    	for ( String key : hm_relation.keySet()) {
	    		Relation r = hm_relation.get(key);
	    		if(r.s_head.size() > head_max)
	    			head_max = r.s_head.size();
	    		if(r.s_tail.size() > tail_max)
	    			tail_max = r.s_tail.size();
	    	}
	    	System.out.println(head_max+","+tail_max);
	    	for ( String key : hm_relation.keySet()) {
	    		Relation r = hm_relation.get(key);
	    		r.theta_head = 1-(1-theta_min)*r.s_head.size()/head_max;
	    		r.theta_tail = 1-(1-theta_min)*r.s_tail.size()/tail_max;
	    	}
	    }
}