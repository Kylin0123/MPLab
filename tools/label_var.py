# -*- coding:utf-8 -*-
"""读取Task5的迭代数据，绘制标签数变化图"""
import matplotlib.pyplot as plt
import os


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


if __name__ == '__main__':
    get_label_var()
