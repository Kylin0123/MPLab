# -*- coding:utf-8 -*-
"""读取Task5的迭代数据，绘制标签数变化图"""
import matplotlib.pyplot as plt
import os
import sys


def get_label_num(filename):
    labels = set([])
    with open(filename, "r", encoding="utf-8") as f:
        for line in f:
            labels.add(int(line.split('\t')[-1]))
    return len(labels)


def get_label_var():
    label_var = []
    for i in range(0, len(os.listdir("output5-itr"))):
        label_var.append(get_label_num("output5-itr/itr"+str(i)+"/part-r-00000"))
    label_var.append(get_label_num("output5/part-r-00000"))
    print(len(label_var))
    print(label_var)
    plt.xlabel("Iteration times")
    plt.ylabel("Number of labels")
    plt.grid()
    plt.plot(label_var, '.-')
    plt.show()


def analyze_labels(filename):
    labels = {}
    with open(filename, encoding="utf-8") as f:
        for line in f:
            splt = line[:-1].split("\t")
            name = splt[0]
            label = splt[1]
            if label in labels:
                labels[label].append(name)
            else:
                labels[label] = [name]
    for label in labels:
        print(label+":("+str(len(labels[label]))+")\t"+str(labels[label]))


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("请输入参数：num/var/alz")
    elif sys.argv[1] == 'var':
        get_label_var()
    elif sys.argv[1] == 'num':
        if len(sys.argv) < 3:
            print("请输入文件名")
        else:
            print(get_label_num(sys.argv[2]))
    elif sys.argv[1] == 'alz':
        if len(sys.argv) < 3:
            print("请输入文件名")
        else:
            analyze_labels(sys.argv[2])
