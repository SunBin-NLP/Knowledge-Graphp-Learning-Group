import time
start = time.clock()

train_data = open('./train.txt')
train_list = []
num = 1
for line in train_data:
    L = line.strip('\n')
    L = L +'\t'+str(num)
    num += 1
    train_list.append(L)
train_list.sort()
train_data.close()
length = len(train_list)

repeat_triplet_file = open('./repeat_triplet_revision.txt','w')
L1_list = []
L2_list = []
row_idx_list = []
repeat_list = []
line_num = 0
flag = 0
for i in xrange(length-1):
    L1 = train_list[i]
    L1_list = L1.split('\t')
    if i == xrange(length):
        break
    L2 = train_list[i+1]
    L2_list = L2.split('\t')
    if line_num != L1_list[3]:
        line_num = 0
    if L1_list[0] == L2_list[0] and L1_list[1] == L2_list[1] and L1_list[2] == L2_list[2] and line_num == 0:
        line_num = L2_list[3]
        if flag == 1:
             repeat_triplet_file.write('\n')
        flag = 1
        repeat_triplet_file.write(L1_list[0] + '\t' + L1_list[1] + '\t' + L1_list[2] + '\t' + L1_list[3] + '\t' + L2_list[3])
    elif line_num == L2_list[3]:
        repeat_triplet_file.write('\t'+L2_list[3])

repeat_triplet_file.close()

end = time.clock()
print "RunningTime: %f s" % (end - start)