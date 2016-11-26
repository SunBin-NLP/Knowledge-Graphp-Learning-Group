#!/usr/bin/python
# -*- coding: utf-8 -*-
import time
import random
import numpy as np
from numpy import zeros


def classify(rec):
	tphr=rec[3]*1.0/len(rec[0])
	hptr=rec[2]*1.0/len(rec[1])
	if (tphr<1.5) and (hptr<1.5):
		return "1_to_1"
	if (tphr>=1.5) and (hptr<1.5):
		return "1_to_n"
	if (tphr<1.5) and (hptr>=1.5):
		return "n_to_1"
	return "n_to_n"

entity_set = set()
relation_id = {}
relation_type = {}
relation_rec = {}


def text_statistics():
	lines_seen = set()
	infile = open("train.txt", "r")
	pre_file = open("pre_train.txt", "w")
	for line1 in infile:
		lines = ''.join(line1.replace('\t', '').replace('\n', '').replace(' ', ''))
		if lines not in lines_seen:
			pre_file.write(line1)
			lines_seen.add(lines)
	pre_file.close()
	infile.close()

	fin = open("pre_train.txt", "r")
	for line in fin.readlines():
		triple = line.strip().split('\t')
		if len(triple) < 3:
			continue
		relation_rec.setdefault(triple[1], [set(), set(), 0, 0])
		relation_rec[triple[1]][0].add(triple[0])
		relation_rec[triple[1]][1].add(triple[2])
		relation_rec[triple[1]][2] += 1
		relation_rec[triple[1]][3] += 1
		entity_set.add(triple[0])
		entity_set.add(triple[2])
	fin.close()

	fout = open("pre_relation_type", "w")
	for relation, rec in relation_rec.items():
		relation_id[relation] = len(relation_id.keys())  # Nnumber the relation
		type = classify(rec)
		print>> fout, relation + '\ttype:' + type + '\tentity_pairs:' + str(rec[2]) + '\thead_entity:' + str(
			len(rec[0])) + '\ttail_entity:' + str(len(rec[1]))
		relation_type[type] = relation_type.get(type, 0) + 1  # Count the relation_type
	fout.close()

	fout = open("pre_relation2id", "w")
	for relation, re_id in relation_id.items():
		print>> fout, relation + '\t' + str(re_id)
	fout.close()

	fout = open("pre_entity2id", "w")
	num = 0
	for entity in entity_set:
		print>> fout, entity + '\t' + str(num)
		num += 1
	fout.close()

	fout = open("pre_relation_type_count", "w")
	for type, num in relation_type.items():
		print >> fout, type + '\t' + str(num)
	fout.close()


def sparse_matrix():
	min_sparse_degree = 0.7
	max_number_relation_head = max(len(rec[0]) for rec in relation_rec.values())
	max_number_relation_tail = max(len(rec[1]) for rec in relation_rec.values())
	sparse_matrix_file = open("sparse_matrix.txt", "w")
	print >> sparse_matrix_file, '\n所有关系对应最大头实体数：\t' + str(max_number_relation_head) \
	                             + '\t所有关系对应最大尾实体数：' + str(max_number_relation_tail)+'\t最小稀疏度设置为：\t' + str(min_sparse_degree)
	print max_number_relation_head, max_number_relation_tail

	for relation, rec in relation_rec.items():
		head_sparse_degree = 1 - ((1 - min_sparse_degree) * len(rec[0]) / max_number_relation_head)
		tail_sparse_degree = 1 - ((1 - min_sparse_degree) * len(rec[1]) / max_number_relation_tail)
		number_nonzero_head = int(round(400 * (1 - head_sparse_degree)))
		number_nonzero_tail = int(round(400 * (1 - tail_sparse_degree)))
		print>> sparse_matrix_file,"\n\n关系：%s,头实体稀疏度：%f,尾实体稀疏度：%f" % (relation,head_sparse_degree,tail_sparse_degree)
		print>> sparse_matrix_file,"头实体稀疏矩阵非零元素个数：%d,尾实体稀疏矩阵非零元素个数：%d" % (number_nonzero_head, number_nonzero_tail)
		print "关系：%s,头实体稀疏度：%f,尾实体稀疏度：%f" % (relation,head_sparse_degree,tail_sparse_degree)
		print "头实体稀疏矩阵非零元素个数：%d,尾实体稀疏矩阵非零元素个数：%d" % (number_nonzero_head, number_nonzero_tail)
		head_data_nonzero = np.random.uniform(-1, 1, size=number_nonzero_head)
		# head_data_location = np.random.randint(0, 400, number_nonzero_head)
		head_data_location = random.sample(range(0, 400), number_nonzero_head)
		H = zeros([20, 20]).reshape(20, 20)
		print>> sparse_matrix_file, 'Head Sparse Matrix:'
		print '\nHead Sparse Matrix:'
		for i in range(number_nonzero_head):
			m = (head_data_location[i]/20)
			n = head_data_location[i] % 20
			H[m][n] = head_data_nonzero[i]
			print>>sparse_matrix_file, m+1, '\t', n+1, '\t', head_data_nonzero[i]
			print m+1, n+1, head_data_nonzero[i]
		sparse_matrix_file.write('Head Sparse Matrix:\n%s' % str(np.matrix(H)))
		print 'Head Sparse Matrix:\n%s' % str(np.matrix(H))

		tail_data_nonzero = np.random.uniform(-1, 1, size=number_nonzero_tail)
		tail_data_location = random.sample(range(0, 400), number_nonzero_tail)
		T = zeros([20, 20])
		sparse_matrix_file.write('\nTail Sparse Matrix:')
		print>> sparse_matrix_file, 'Tail Sparse Matrix:'
		for i in range(number_nonzero_tail):
			m = (tail_data_location[i] / 20)
			n = tail_data_location[i] % 20
			T[m][n] = tail_data_nonzero[i]
			print>> sparse_matrix_file, m + 1, '\t', n + 1, '\t', tail_data_nonzero[i]
			print m + 1, n + 1, tail_data_nonzero[i]
		sparse_matrix_file.write('Tail Sparse Matrix:\n%s' % str(np.matrix(T)))
		print 'Tail Sparse Matrix:\n%s' % str(np.matrix(T))

if __name__ == '__main__':
	start = time.clock()
	text_statistics()
	sparse_matrix()
	end = time.clock()
	print "read: %.2f s" % (end - start)
