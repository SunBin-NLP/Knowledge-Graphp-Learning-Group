package task8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/*
 * 对每一个关系，初始化其对应的“头实体稀疏矩阵”和“尾实体稀疏矩阵”（自己思考一下：两种矩阵的大小m*n中，m、n各应设置为多少？），具体要求如下：
 *
 *m表示的是关系的维度，n表示的是实体的维度。在这里是20*20
 *
 * 1）、对于每一个关系，分别计算其“头实体稀疏矩阵稀疏度”和“尾实体稀疏矩阵稀疏度”，根据各自的稀疏度，决定对应矩阵中非零元素的个数；
 * 2）、根据上一步得出的矩阵中非0元素的个数（这里假设有a个），利用随机数生成函数，随机产生相应个数(这里为a）的随机数，每一个随机数对应一个矩阵的位置；
 * 3）、根据上一步得到的非0元素在矩阵中的位置信息，对该位置随机分配一个实始值。
 */
public class Task {
	public static void main(String[] args) throws IOException {
		long t1 = System.currentTimeMillis();
		double min = 0.7;
		int n = 20;
		double headsDegree;
		double tailsDegree;
		// 读取关系统计文件
		File srcFile = new File("statistics.txt");
		File destFile = new File("data/sparse.txt");
		// 定义输入输出流
		BufferedReader br = new BufferedReader(new FileReader(srcFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
		// 定义存储各关系稀疏度的map
		HashMap<String, Integer> head = new HashMap<>();
		HashMap<String, Integer> tail = new HashMap<>();
		TreeSet<Integer> headNum = new TreeSet<>();
		TreeSet<Integer> tailNum = new TreeSet<>();

		HashMap<String, HashMap<Integer, HashSet<Integer>>> rel_loc = new HashMap<>();
		HashMap<Integer, HashSet<Integer>> loc;
		HashSet<Integer> coll;
		// 读取文件
		String line;
		String[] tmp; // 关系 三元组个数 头实体 尾实体
		while ((line = br.readLine()) != null) {
			tmp = line.split("\t\t\t");
			head.put(tmp[0], new Integer(tmp[2]));
			tail.put(tmp[0], new Integer(tmp[3]));
			headNum.add(new Integer(tmp[2]));
			tailNum.add(new Integer(tmp[3]));
		}
		int hmax = headNum.last();
		int tmax = tailNum.last();
		int max = hmax > tmax ? hmax : tmax;
		int h_nz;
		int t_nz;
		bw.write("最小稀疏度设置为" + min + "\n");
		Set<String> relation = head.keySet();
		for (String rel : relation) {
			headsDegree = head.get(rel) * 1.0 / max;
			tailsDegree = tail.get(rel) * 1.0 / max;
			// 根据稀疏度确定非零个数
			h_nz = (int) ((1 - min) * headsDegree * n * n); // 头实体矩阵非零元素个数
			t_nz = (int) ((1 - min) * tailsDegree * n * n);
			bw.write(rel + "\t头实体稀疏矩阵非零个数：" + h_nz + "\t尾实体稀疏矩阵非零个数：" + t_nz);
			bw.newLine();
			bw.flush();
			// 根据个数随机产生随机数以及随机数的位置
			loc = new HashMap<>();
			int size = 0;
			while (size < h_nz) {
				int row = (int) (Math.random() * 20);
				int col = (int) (Math.random() * 20);
				if (loc.get(row) == null) {
					coll = new HashSet<>();
				} else {
					coll = loc.get(row);
				}
				if (coll.add(col) == true) {
					size++;
					loc.put(row, coll);
					rel_loc.put(rel, loc);
				}
			}
			bw.write("头实体稀疏矩阵非零元素及位置" + "\n");
			Set<Integer> tmprow = loc.keySet();
			for (Integer ro : tmprow) {
				HashSet<Integer> tmpcol = loc.get(ro);
				for (Integer c : tmpcol) {
					double value = Math.random();
					bw.write(ro + "\t" + c + "\t" + value);
					bw.newLine();
					bw.flush();
				}
			}

			loc = new HashMap<>();
			size = 0;
			while (size < t_nz) {
				int row = (int) (Math.random() * 20);
				int col = (int) (Math.random() * 20);
				if (loc.get(row) == null) {
					coll = new HashSet<>();
				} else {
					coll = loc.get(row);
				}
				if (coll.add(col) == true) {
					size++;
					loc.put(row, coll);
					rel_loc.put(rel, loc);
				}
			}
			bw.write("尾实体稀疏矩阵非零元素及位置" + "\n");
			tmprow = loc.keySet();
			for (Integer ro : tmprow) {
				HashSet<Integer> tmpcol = loc.get(ro);
				for (Integer c : tmpcol) {
					double value = Math.random();
					bw.write(ro + "\t" + c + "\t" + value);
					bw.newLine();
					bw.flush();
				}
			}
		}
		// 关闭输入输出流
		br.close();
		bw.close();
		long t2 = System.currentTimeMillis();
		System.out.println("耗时：" + (t2 - t1) + "毫秒");
	}
}
