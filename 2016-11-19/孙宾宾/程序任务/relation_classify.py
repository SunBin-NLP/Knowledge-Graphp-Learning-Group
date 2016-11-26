#!/usr/bin/python
# -*- coding: utf-8 -*-
import time
import numpy as np
from math import *
import matplotlib
from pylab import *
from matplotlib.ticker import MultipleLocator
from matplotlib.patches import Ellipse,Circle
import matplotlib.pyplot as plt
start = time.clock()


def classify(rec):
	tphr=rec[3]*1.0/len(rec[0])  # averaged number of tails per head.
	hptr=rec[2]*1.0/len(rec[1])  # averaged number of head per tails.
	if (tphr<1.5) and (hptr<1.5):
		return "1_to_1"
	if (tphr>=1.5) and (hptr<1.5):
		return "1_to_n"
	if (tphr<1.5) and (hptr>=1.5):
		return "n_to_1"
	return "n_to_n"

entity_set=set()
relation_id={}
relation_type={}
relation_rec={}
li=[]
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
		relation_rec.setdefault(triple[2], [set(), set(), 0, 0])
		relation_rec[triple[2]][0].add(triple[0])
		relation_rec[triple[2]][1].add(triple[1])
		relation_rec[triple[2]][2] += 1
		relation_rec[triple[2]][3] += 1
		entity_set.add(triple[0])
		entity_set.add(triple[1])
	fin.close()

	fout = open("relation_type", "w")
	for relation, rec in relation_rec.items():
		relation_id[relation] = len(relation_id.keys()) + 1  # Nnumber the relation
		type = classify(rec)
		print>> fout, relation + '\ttype:' + type + '\tentity_pairs:' + str(rec[2]) + '\thead_entity:' + str(
			len(rec[0])) + '\ttail_entity:' + str(len(rec[1]))
		relation_type[type] = relation_type.get(type, 0) + 1  # Count the relation_type
		li.append(rec[2])
	fout.close()

	fout = open("relation2id", "w")
	for relation, re_id in relation_id.items():
		print>> fout, relation + '\t' + str(re_id)
	fout.close()

	fout = open("entity2id", "w")
	num = 0
	for entity in entity_set:
		num += 1
		print>> fout, entity + '\t' + str(num)
	fout.close()

	fout = open("relation_type_count", "w")
	for type, num in relation_type.items():
		print >> fout, type + '\t' + str(num)
		print type + '\t' + str(num)+'\t%.2f%%' % (num*100.0/1345)
	fout.close()

def draw_mapping():
	plt.figure(figsize=(8, 7))
	ax = subplot(111)
	ymajorLocator = MultipleLocator(500)
	plt.xlim(0,5000)
	plt.ylim(0,5000)
	ax.yaxis.set_major_locator(ymajorLocator)

	plt.title("Imbalance in FB15k", fontsize=18)
	plt.xlabel('number of head entities', fontsize=18)
	plt.ylabel('number of tail entities', fontsize=18)

	ell1 = Ellipse(xy=(260, 2250), width=600, height=3300, angle=0.0, facecolor='white', linestyle='--',
	               edgecolor='#EE2A29', lw=2, alpha=1.0)
	ell2 = Ellipse(xy=(2550, 200), width=3500, height=600, angle=0.0, facecolor='white', linestyle='--',
	               edgecolor='#EE2A29', lw=2, alpha=1.0)
	ax.add_patch(ell1)
	ax.add_patch(ell2)

	for relation, rec in relation_rec.items():
		type = classify(rec)
		if type == "n_to_n":
			num = (log(rec[2] * 1.0) )**4/450
			plt.plot(len(rec[0]), len(rec[1]), markerfacecolor='#6EBE44', markeredgecolor='#6EBE44', alpha=1,
			         marker='o', markersize=num)
	for relation, rec in relation_rec.items():
		type = classify(rec)
		if type == "1_to_1":
			num = (log(rec[2] * 1.0)) ** 4 / 450
			plt.plot(len(rec[0]), len(rec[1]), markerfacecolor='#A55BA4', markeredgecolor='#A55BA4',marker='o',markersize=num)
	for relation, rec in relation_rec.items():
		type = classify(rec)
		if type == "1_to_n":
			num = (log(rec[2] * 1.0)) ** 4 / 450
			plt.plot(len(rec[0]), len(rec[1]), markerfacecolor='#2256A6', markeredgecolor='#2256A6',marker='o',markersize=num)
	for relation, rec in relation_rec.items():
		type = classify(rec)
		if type == "n_to_1":
			num = (log(rec[2] * 1.0)) ** 4 / 450
			plt.plot(len(rec[0]), len(rec[1]), markerfacecolor='#231F20', markeredgecolor='#231F20',marker='o',markersize=num)

	plt.plot(0, 'go',markersize=7, markeredgecolor='white',label='N-to-N 274 (20.37%)')
	plt.plot(0, 'mo', markersize=7, markeredgecolor='white', label='1-to-1 368 (27.36%)')
	plt.plot(0, 'ko', markersize=7, markeredgecolor='white', label='N-to-1 394 (29.29%)', lw=3)
	plt.plot(0, 'bo', markersize=7, markeredgecolor='white', label='1-to-N 309 (22.97%)', lw=3)
	plt.legend(numpoints=1, fontsize=15)

	plt.annotate('',xy=(550, 2200), xytext=(2750, 3000), arrowprops=dict(arrowstyle="->",connectionstyle="arc3",lw=2,color='#EE2A29'),)
	plt.annotate('',xy=(2700, 500), xytext=(2730, 3000), arrowprops=dict(arrowstyle="->",connectionstyle="arc3",lw=2,color='#EE2A29'),)
	plt.text(2200, 3000, 'imbalance', fontsize = 20,color='#EE2A29')

	plt.savefig("Imbalance in FB15K.png")
	plt.show()

def draw_graph():
	plt.figure()
	ax = subplot(111)
	xmajorLocator = MultipleLocator(500)
	ymajorLocator = MultipleLocator(2000)
	plt.xlim(0, 1500)
	plt.ylim(0, 16000)
	ax.yaxis.set_major_locator(ymajorLocator)
	ax.xaxis.set_major_locator(xmajorLocator)
	plt.title('Heterogeneity in FB15k',fontsize=18)
	plt.xlabel('relation index',fontsize=18)
	plt.ylabel('number of entity pairs',fontsize=18)
	x = np.arange(len(relation_rec.keys()))
	plt.bar(x,li,color='k')
	plt.savefig('Heterogeneity in FB15k.png')
	# plt.show()

if __name__ == '__main__':
	text_statistics()
	draw_graph()
	draw_mapping()

	end = time.clock()
	print "read: %.2fs" % (end - start)