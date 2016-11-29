package sixth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
   1）为train.txt中的每一个实体，分配一个唯一的Id值；  ==》结果保存为一个文件
   2）为train.txt中的每一个关系，分配一个唯一的Id值；  ==》结果保存为一个文件
   3）为每一个关系，统计其连接的三元组个数、头实体个数、尾实体个数  ==》结果保存为一个文件
 */
public class Task1 {
	public static void main(String[] args) throws IOException {
		long t1 = System.currentTimeMillis();
		// 定义文件和输入输出流
		File srcFile = new File("train.txt");
		File[] destFiles = new File[3];
		destFiles[0] = new File("entity.txt");
		destFiles[1] = new File("relation.txt");
		destFiles[2] = new File("statistics.txt");
		BufferedReader br = new BufferedReader(new FileReader(srcFile));
		BufferedWriter bw = null;
		// 定义存放实体和关系的集合
		ArrayList<HashSet<String>> tmpSet = new ArrayList<>();
		HashSet<String> tmpEnt = new HashSet<>();
		HashSet<String> tmpRel = new HashSet<>();
		tmpSet.add(tmpEnt);
		tmpSet.add(tmpRel);
		ArrayList<HashMap<Integer, String>> maps = new ArrayList<>();
		HashMap<Integer, String> entity = new HashMap<>();
		HashMap<Integer, String> relation = new HashMap<>();
		maps.add(entity);
		maps.add(relation);
		// 定义存放关系对应统计量的集合
		HashMap<String, HashSet<String>> numOfTrip = new HashMap<>();
		HashMap<String, HashSet<String>> numOfHead = new HashMap<>();
		HashMap<String, HashSet<String>> numOfTail = new HashMap<>();
		// 读文件，每次读一行
		String line;
		HashSet<String> tmpHead, tmpTail, trip;
		while ((line = br.readLine()) != null) {
			String[] tmp = line.split("\t");
			tmpEnt.add(tmp[0]);
			tmpEnt.add(tmp[2]);
			tmpRel.add(tmp[1]);
			// 存放三元组信息
			if (numOfTrip.get(tmp[1]) == null) {
				trip = new HashSet<>();
			} else {
				trip = numOfTrip.get(tmp[1]);
			}
			trip.add(tmp[0]);
			numOfTrip.put(tmp[1], trip);
			// 存放关系的头实体
			if (numOfHead.get(tmp[1]) == null) {
				tmpHead = new HashSet<>();
			} else {
				tmpHead = numOfHead.get(tmp[1]);
			}
			tmpHead.add(tmp[0]);
			numOfHead.put(tmp[1], tmpHead);
			// 存放关系的尾实体
			if (numOfTail.get(tmp[1]) == null) {
				tmpTail = new HashSet<>();
			} else {
				tmpTail = numOfTail.get(tmp[1]);
			}
			tmpTail.add(tmp[2]);
			numOfTail.put(tmp[1], tmpTail);
		}
		// 存放入map,为每个实体分配唯一的ID值，并写文件
		int index, ntrip, nhead, ntail;
		for (int i = 0; i < 3; i++) {
			index = 0;
			bw = new BufferedWriter(new FileWriter(destFiles[i]));
			if (i < 2) {
				for (String str : tmpSet.get(i)) {
					maps.get(i).put(++index, str);
					bw.write(index + "\t" + str);
					bw.newLine();
					bw.flush();
				}
			} else {
				Set<String> hs = numOfTrip.keySet();
				for (String str : hs) {
					ntrip = numOfTrip.get(str).size();
					nhead = numOfHead.get(str).size();
					ntail = numOfTail.get(str).size();
					bw.write(str + "\t\t\t" + ntrip + "\t\t\t" + nhead + "\t\t\t" + ntail);
					bw.newLine();
					bw.flush();
				}
			}
		}
		// 关闭输入输出流
		br.close();
		bw.close();
		long t2 = System.currentTimeMillis();
		System.out.println("用时：" + (t2 - t1) + "毫秒");
	}
}
