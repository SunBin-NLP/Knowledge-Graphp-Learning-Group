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
   1��Ϊtrain.txt�е�ÿһ��ʵ�壬����һ��Ψһ��Idֵ��  ==���������Ϊһ���ļ�
   2��Ϊtrain.txt�е�ÿһ����ϵ������һ��Ψһ��Idֵ��  ==���������Ϊһ���ļ�
   3��Ϊÿһ����ϵ��ͳ�������ӵ���Ԫ�������ͷʵ�������βʵ�����  ==���������Ϊһ���ļ�
 */
public class Task1 {
	public static void main(String[] args) throws IOException {
		long t1 = System.currentTimeMillis();
		// �����ļ������������
		File srcFile = new File("train.txt");
		File[] destFiles = new File[3];
		destFiles[0] = new File("entity.txt");
		destFiles[1] = new File("relation.txt");
		destFiles[2] = new File("statistics.txt");
		BufferedReader br = new BufferedReader(new FileReader(srcFile));
		BufferedWriter bw = null;
		// ������ʵ��͹�ϵ�ļ���
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
		// �����Ź�ϵ��Ӧͳ�����ļ���
		HashMap<String, HashSet<String>> numOfTrip = new HashMap<>();
		HashMap<String, HashSet<String>> numOfHead = new HashMap<>();
		HashMap<String, HashSet<String>> numOfTail = new HashMap<>();
		// ���ļ���ÿ�ζ�һ��
		String line;
		HashSet<String> tmpHead, tmpTail, trip;
		while ((line = br.readLine()) != null) {
			String[] tmp = line.split("\t");
			tmpEnt.add(tmp[0]);
			tmpEnt.add(tmp[2]);
			tmpRel.add(tmp[1]);
			// �����Ԫ����Ϣ
			if (numOfTrip.get(tmp[1]) == null) {
				trip = new HashSet<>();
			} else {
				trip = numOfTrip.get(tmp[1]);
			}
			trip.add(tmp[0]);
			numOfTrip.put(tmp[1], trip);
			// ��Ź�ϵ��ͷʵ��
			if (numOfHead.get(tmp[1]) == null) {
				tmpHead = new HashSet<>();
			} else {
				tmpHead = numOfHead.get(tmp[1]);
			}
			tmpHead.add(tmp[0]);
			numOfHead.put(tmp[1], tmpHead);
			// ��Ź�ϵ��βʵ��
			if (numOfTail.get(tmp[1]) == null) {
				tmpTail = new HashSet<>();
			} else {
				tmpTail = numOfTail.get(tmp[1]);
			}
			tmpTail.add(tmp[2]);
			numOfTail.put(tmp[1], tmpTail);
		}
		// �����map,Ϊÿ��ʵ�����Ψһ��IDֵ����д�ļ�
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
		// �ر����������
		br.close();
		bw.close();
		long t2 = System.currentTimeMillis();
		System.out.println("��ʱ��" + (t2 - t1) + "����");
	}
}
