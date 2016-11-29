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
 * ��ÿһ����ϵ����ʼ�����Ӧ�ġ�ͷʵ��ϡ����󡱺͡�βʵ��ϡ����󡱣��Լ�˼��һ�£����־���Ĵ�Сm*n�У�m��n��Ӧ����Ϊ���٣���������Ҫ�����£�
 *
 *m��ʾ���ǹ�ϵ��ά�ȣ�n��ʾ����ʵ���ά�ȡ���������20*20
 *
 * 1��������ÿһ����ϵ���ֱ�����䡰ͷʵ��ϡ�����ϡ��ȡ��͡�βʵ��ϡ�����ϡ��ȡ������ݸ��Ե�ϡ��ȣ�������Ӧ�����з���Ԫ�صĸ�����
 * 2����������һ���ó��ľ����з�0Ԫ�صĸ��������������a������������������ɺ��������������Ӧ����(����Ϊa�����������ÿһ���������Ӧһ�������λ�ã�
 * 3����������һ���õ��ķ�0Ԫ���ھ����е�λ����Ϣ���Ը�λ���������һ��ʵʼֵ��
 */
public class Task {
	public static void main(String[] args) throws IOException {
		long t1 = System.currentTimeMillis();
		double min = 0.7;
		int n = 20;
		double headsDegree;
		double tailsDegree;
		// ��ȡ��ϵͳ���ļ�
		File srcFile = new File("statistics.txt");
		File destFile = new File("data/sparse.txt");
		// �������������
		BufferedReader br = new BufferedReader(new FileReader(srcFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
		// ����洢����ϵϡ��ȵ�map
		HashMap<String, Integer> head = new HashMap<>();
		HashMap<String, Integer> tail = new HashMap<>();
		TreeSet<Integer> headNum = new TreeSet<>();
		TreeSet<Integer> tailNum = new TreeSet<>();

		HashMap<String, HashMap<Integer, HashSet<Integer>>> rel_loc = new HashMap<>();
		HashMap<Integer, HashSet<Integer>> loc;
		HashSet<Integer> coll;
		// ��ȡ�ļ�
		String line;
		String[] tmp; // ��ϵ ��Ԫ����� ͷʵ�� βʵ��
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
		bw.write("��Сϡ�������Ϊ" + min + "\n");
		Set<String> relation = head.keySet();
		for (String rel : relation) {
			headsDegree = head.get(rel) * 1.0 / max;
			tailsDegree = tail.get(rel) * 1.0 / max;
			// ����ϡ���ȷ���������
			h_nz = (int) ((1 - min) * headsDegree * n * n); // ͷʵ��������Ԫ�ظ���
			t_nz = (int) ((1 - min) * tailsDegree * n * n);
			bw.write(rel + "\tͷʵ��ϡ�������������" + h_nz + "\tβʵ��ϡ�������������" + t_nz);
			bw.newLine();
			bw.flush();
			// ���ݸ����������������Լ��������λ��
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
			bw.write("ͷʵ��ϡ��������Ԫ�ؼ�λ��" + "\n");
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
			bw.write("βʵ��ϡ��������Ԫ�ؼ�λ��" + "\n");
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
		// �ر����������
		br.close();
		bw.close();
		long t2 = System.currentTimeMillis();
		System.out.println("��ʱ��" + (t2 - t1) + "����");
	}
}
