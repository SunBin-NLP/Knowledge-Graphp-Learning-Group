#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
@author: Sun Binbin
@contact: sunbbedu@163.com
@software: PyCharm
@file: test.py
@time: 2016/11/25 0:04
"""
# 列表平均分割
import numpy as np
a=range(10)
N=2

#方法一
b1=[a[i:i+N] for i in range( 0,len(a),N)]
X=np.matrix(b1)

#方法二
b2=[]
for i in range(0,len(a),N):
    b2.append(a[i:i+N])
Y=np.matrix(b2)

print b1
print b2
print X
print Y