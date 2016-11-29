#coding:utf-8
import numpy as np
import random
import time

start = time.clock()

#处理训练数据
train_data = open('./train.txt')
train_list = []
entity_list = []
relation_list = []
for line in train_data:
    L = line.strip('\n')      #把\n后边的都给去掉
    train_list.append(L)
    L_list = L.split('\t')    #按\t将字符串分成list
    entity_list.append(L_list[0])
    entity_list.append(L_list[2])
    relation_list.append(L_list[1])
train_data.close()

entity_list = list(set(entity_list))      #去除重复的字符串
relation_list = list(set(relation_list))      #去除重复的字符串
train_list = list(set(train_list))
n_relation = len(relation_list)

#为实体分配ID
entity_id_file = open('./entity_id.txt','w')
entity_id = {}
id = 0
for entity in entity_list:
    entity_id_file.write(entity+' '+str(id)+'\n')
    entity_id[entity] = id
    id += 1
entity_id_file.close()

#为关系分配ID
relation_id_file = open('./relation_id.txt','w')
relation_id = {}
id = 0
for relation in relation_list:
    relation_id_file.write(relation+' '+str(id)+'\n')
    relation_id[relation] = id
    id += 1
relation_id_file.close()

#每一个关系链接的元组个数，头实体个数，尾实体个数
triplet_file = open('./triplet.txt','w')
for relation in relation_list:
    head_entity_list = []
    tail_entity_list = []
    #triplet_list = []
    num_triplet = 0
    for triplet in train_list:
        if relation in triplet:
            t_list = triplet.split('\t')
            head_entity_list.append(t_list[0])
            tail_entity_list.append(t_list[2])
            num_triplet +=1
    head_entity_list = list(set(head_entity_list))
    tail_entity_list = list(set(tail_entity_list))
    num_head = len(head_entity_list)
    num_tail = len(tail_entity_list)
    triplet_file.write(str(relation_id[relation])+'\t'+str(num_triplet)+'\t'+str(num_head)+'\t'+str(num_tail)+'\n')
triplet_file.close()

#生成不重复的num个随机数
def random_para(min,max,num):
    i = 0
    random_list = []
    for i in xrange(num):
        random_num = np.random.uniform(min,max)
        random_list.append(random_num)
        random_list = list(set(random_list))
    return random_list


h_set = [set() for i in xrange(n_relation)]
t_set = [set() for i in xrange(n_relation)]
relation_data = open('./triplet.txt')
l = 0
for line in relation_data:
    L = line.strip('\n')
    L_list = L.split('\t')[0:4]
    h_set[l] = L_list[2]
    t_set[l] = L_list[3]
    l +=1
relation_data.close()
h_sparse = np.zeros(n_relation)
t_sparse = np.zeros(n_relation)
for i in xrange(n_relation):
    h_sparse[i] = h_set[i]
    t_sparse[i] = t_set[i]
max_value = np.max([np.max(h_sparse),np.max(t_sparse)])
h_sparse = 1-0.3*h_sparse/max_value
t_sparse = 1-0.3*t_sparse/max_value
sparse_degree = open('./sparse_degree.txt','w')
for i in xrange(n_relation):
    sparse_degree.write(str(i)+'\t'+str(h_sparse[i])+'\t'+str(t_sparse[i])+'\n')
    #print str(i) + '\t' + str(h_sparse[i]) + '\t' + str(t_sparse[i]) + '\n'
sparse_degree.close()

n = 20
length = n*n
h_para_num = (1.-h_sparse)*length
t_para_num = (1.-t_sparse)*length
#print h_para_num,t_para_num
#头实体转移矩阵
h_matrix_file = open('./h_matrix.txt','w')
relation_num = 0
for relation_num in xrange(n_relation):
    h_random_list = random_para(-1,1,int(h_para_num[relation_num]))
    for i in xrange(length-int(h_para_num[relation_num])) :
        h_random_list.append(0)
    random.shuffle(h_random_list)
    for j in xrange(length):
        if h_random_list[j] != 0:
            row_idx = j/n_relation
            col_idx = j%n_relation
            h_matrix_file.write(str(relation_num)+'\t'+str(row_idx)+'\t'+str(col_idx)+'\t'+str(h_random_list[j])+'\n') #关系ID,非零元的行标，列标，随机数
            j += 1
    print '关系' + str(relation_num) + '的头稀疏矩阵非零元素个数为：' + str(int(h_para_num[relation_num]))
    relation_num += 1

h_matrix_file.close()

#尾实体转移矩阵
t_matrix_file = open('./t_matrix.txt','w')
relation_num = 0
for relation_num in xrange(n_relation):
    t_random_list = random_para(-1,1,int(t_para_num[relation_num]))
    for i in xrange(length-int(t_para_num[relation_num])) :
        t_random_list.append(0)
    random.shuffle(t_random_list)
    for j in xrange(length):
        if t_random_list[j] != 0:
            row_idx = j/n_relation
            col_idx = j%n_relation
            t_matrix_file.write(str(relation_num)+'\t'+str(row_idx)+'\t'+str(col_idx)+'\t'+str(t_random_list[j])+'\n')#关系ID,非零元的行标，列标，随机数
            j += 1
    print '关系' + str(relation_num) + '的尾稀疏矩阵非零元素个数为：' + str(int(t_para_num[relation_num]))
    relation_num += 1
t_matrix_file.close()

end = time.clock()
print "RunningTime: %f s" % (end - start)

