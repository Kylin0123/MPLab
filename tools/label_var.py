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
    label_dict = {}
    with open(filename, encoding="utf-8") as f:
        for line in f:
            splt = line[:-1].split("\t")
            name = splt[0]
            label = splt[1]
            if label in label_dict:
                label_dict[label].append(name)
            else:
                label_dict[label] = [name]
    return label_dict


def gen_name_dict(filename):
    name_dict = {}
    with open(filename, encoding="utf-8") as f:
        for line in f:
            splt = line[:-1].split("\t")
            name = splt[0]
            label = splt[1]
            name_dict[name] = label
    return name_dict


def analyze_labels_var(name):
    for i in range(0, len(os.listdir("output5-itr"))):
        name_dict = gen_name_dict("output5-itr/itr"+str(i)+"/part-r-00000")
        label_dict = analyze_labels("output5-itr/itr"+str(i)+"/part-r-00000")
        print("itr"+str(i)+":"+str(len(label_dict[name_dict[name]])))
    name_dict = gen_name_dict("output5/part-r-00000")
    label_dict = analyze_labels("output5/part-r-00000")
    print("final:"+str(len(label_dict[name_dict[name]])))


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("请输入参数：num/var/alz/alzname")
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
            labels = analyze_labels(sys.argv[2])
            label_list = []
            for label in labels:
                label_list.append((label, labels[label]))
            label_list.sort(key=lambda x: len(x[1]), reverse=True)
            for label, names in label_list:
                print("label "+str(label)+" ("+str(len(names))+") "+str(names))
    elif sys.argv[1] == 'alzname':
        if len(sys.argv) < 3:
            print("请输入人物名")
        else:
            analyze_labels_var(sys.argv[2])
