#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
@author: Sun Binbin
@contact: sunbbedu@163.com
@software: PyCharm
@file: generateRand.py
@time: 2016/11/14 13:09
"""
import sys
import random
import numpy as np
from scipy.sparse import csr_matrix


def generate_rand():    # Defines a random generation function
	result_list = random.sample(range(-10, 10 + 1), 10)
	print 'Randomly generate lists of numbers:' + str(result_list)


def develop_matrix():    # Develop Sparse Matrix function
	indptr = np.array([0, 1, 3, 5, 6, 8])
	indices = np.array([0, 1, 2, 2, 0, 1, 3, 4])
	# data = np.random.uniform(-1, 1, size=8)
	data = np.random.randint(1,11,8)
	X = csr_matrix((data, indices, indptr), shape=(5, 5)).toarray()
	return np.matrix(X)


def sparse_matrix(X):    # Calculate the density of Sparse Matrix function
	density = (np.size(X) - np.count_nonzero(X)) * 1.0/np.size(X)
	print 'Sparse Matrix:\n%s\nThe density of Sparse Matrix:%.2f' % (str(X), density)

if __name__ == '__main__':
	generate_rand()
	sparse_matrix(develop_matrix())















# def generate_rand():    # Defines a random generation function
# 	result_list = random.sample(range(int(sys.argv[1]), int(sys.argv[2])+1), int(sys.argv[3]))
# 	return result_list
# if __name__ == '__main__':
# 	print generate_rand()


# def develop_matrix():
# 	# randArray = np.random.randint(0, 10, size=(3, 3))
# 	# randArray = np.random.random(size=10)
# 	indptr = np.array([0, 1, 3, 5, 6, 8])
# 	indices = np.array([0, 1, 2, 2, 0, 1, 3, 4])
# 	data = np.random.uniform(-1, 1, size=8)
# 	X = csr_matrix((data, indices, indptr), shape=(5, 5)).toarray()
# 	return np.matrix(X)
# print develop_matrix()
#
#
# def sparse_matrix():    # Calculate the density of Sparse Matrix function
# 	X = np.matrix([[0, 5, 0, 1, 0], [0, 3, 0, 0, 2], [0, 2, 0, 0, 0], [1, 0, 0, 3, 0]])
# 	density = (np.size(X) - np.count_nonzero(X)) * 1.0/np.size(X)
# 	return 'Sparse Matrix:\n%s\nThe density of Sparse Matrix:%.2f' % (str(X), density)
# print sparse_matrix(develop_matrix())


# def sparse_matrix():
# 	X = np.matrix([[0, 0, 0, 1, 0], [0, 3, 0, 0, 0], [0, 2, 0, 0, 0], [0, 0, 0, 3, 0]])
# 	raw, column = X.shape  # get the matrix of a raw and column
# 	print np.count_nonzero(X)
# 	print (np.size(X) - np.count_nonzero(X)) * 1.0/np.size(X)
# 	print raw*column
# 	return 'Sparseness of Sparse Matrix:\n %s' % str(X)
# print sparse_matrix()