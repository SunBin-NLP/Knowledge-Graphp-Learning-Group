train_file=open("D:/train.txt",'r') #打开三元组文档

relation_of_triple=dict()   #统计各关系三元组的字典
relation_of_head=dict()     #统计链接各关系头实体的字典
relation_of_tail=dict()     #统计链接各关系尾实体的字典
entity_set = set()          #实体集合
relation_set = set()        #关系集合

for line in train_file:
    line = line.strip()     #去掉换行符
    line_word = line.split() #以空格分割，返回一个列表
    
    head = line_word[0]
    tail = line_word[2]
    relation = line_word[1]

    if relation not in relation_of_triple.keys():   #当前字典中是否存在此关系
        relation_of_triple[relation] = 1         #不存在的话，添加这个关系
        relation_of_head[relation] = list()     #此字典中的关系为键值，对应一个列表，列表中存头实体
        relation_of_tail[relation] = list()
    else:
        relation_of_triple[relation] += 1       #存在此关系，关系数加一
    relation_of_head[relation].append(head)     #在关系对应的列表中追加头实体
    relation_of_tail[relation].append(tail)
    
    entity_set.add(head)            #实体集合添加头实体
    entity_set.add(tail)            #实体集合添加尾实体
    relation_set.add(relation)      #关系集合添加关系

entity_count=1 #实体计数器
for entity in entity_set:
    open("D:/entity_file.txt",'a').write(str(entity_count) + "\t" + entity + "\n")
    #写入文件
    #print str(entity_count) + "\t" + entity 
    entity_count += 1

relation_count=1  #关系计数器
for relation in relation_set:
    open("D:/relation_file.txt",'a').write(str(relation_count) + "\t" + relation + "\n")
    #写入文件
    print str(relation_count)+ "\t" + relation
    relation_count += 1
print '----------------------------------'
    
for relation in relation_of_triple:
    print "关系:"+ relation +"的三元组个数：" + str(relation_of_triple[relation])+"\t头实体个数：" + str(len(set(relation_of_head[relation]))) +"\t尾实体个数：" + str(len(set(relation_of_tail[relation])))
    open("D:/statistic_file.txt",'a').write("关系:"+ relation +"的三元组个数：" + str(relation_of_triple[relation])+"\t头实体个数：" + str(len(set(relation_of_head[relation]))) +"\t尾实体个数：" + str(len(set(relation_of_tail[relation])))+"\n")
                                          
