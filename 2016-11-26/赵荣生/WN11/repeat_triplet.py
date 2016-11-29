import time
start = time.clock()
train_data = open('./train.txt')
train_list = []
for line in train_data:
    L = line.strip('\n')
    train_list.append(L)
train_data.close()
length = len(train_list)

repeat_triplet_file = open('./repeat_triplet.txt','w')
L1 = {}
L2 = {}
row_idx_list = []
repeat_list = []
for i in xrange(length):
    L1 = train_list[i]
    if i not in row_idx_list:
        k = 0
        for j in range(i+1,length):
            L2 = train_list[j]
            if L1 == L2:
                row_idx_list.append(j)
                if k == 0:
                    repeat_triplet_file.write(L1+'\t'+str(i+1)+'\t'+str(j+1))
                    #print L1,i+1,j+1
                    k = 1
                else:
                    repeat_triplet_file.write('\t' + str(j + 1))
                    #print  j + 1
            if k == 1 and j == length-1:
                 repeat_triplet_file.write('\n')
repeat_triplet_file.close()

end = time.clock()
print "RunningTime: %f s" % (end - start)