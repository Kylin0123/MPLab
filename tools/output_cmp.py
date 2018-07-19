# -*- coding:utf-8 -*-
"""对比两个输出文件是否相同"""
import os
import sys


def cmp_output1(file1, file2):
    """必须每行严格相同"""
    flag = True

    if os.path.getsize(file1) != os.path.getsize(file2):
        flag = False
        print("file size is different")

    with open(file1, encoding="utf-8") as f1:
        with open(file2, encoding="utf-8") as f2:
            lines1 = f1.read().splitlines(keepends=False)
            lines2 = f2.read().splitlines(keepends=False)
            if len(lines1) != len(lines2):
                flag = False
                print("number of lines is different, "+file1+" is "+str(len(lines1))+" but "+file2+" is "+str(len(lines2)))
            for i in range(0, len(lines1)):
                if lines1[i] != lines2[i]:
                    flag = False
                    print("difference in line " + str(i))
                    print(file1+":\t"+lines1[i])
                    print(file2+":\t"+lines2[i])
                    break
    if flag:
        print("all the same")


def gen_dict(filename):
    dct = {}
    with open(filename, encoding="utf-8") as f:
        for line in f:
            splt = line[:-1].split("\t")
            dct[splt[0]] = splt[1]
    return dct


def cmp_output24(file1, file2):
    dct1 = gen_dict(file1)
    dct2 = gen_dict(file2)
    flag = True
    if len(dct1) != len(dct2):
        flag = False
        print("length is not the same")
    for key in dct1:
        if dct1[key] != dct2[key]:
            flag = False
            print("difference in " + key + ", file1 is " + dct1[key] + " but file2 is " + dct2[key])
            break
    if flag:
        print("all the same")


def gen_graph(filename):
    graph = {}
    with open(filename, encoding="utf-8") as f:
        for line in f:
            splt = line[:-1].split("\t")
            name = splt[0]
            neighbors = []
            for seg in splt[1][1:-1].split("|"):
                sg = seg.split(",")
                neighbor = sg[0]
                weight = float(sg[1])
                neighbors.append((neighbor, weight))
            graph[name] = neighbors
    return graph


def cmp_output3(file1, file2):
    flag = True
    graph1 = gen_graph(file1)
    graph2 = gen_graph(file2)
    if len(graph1) != len(graph2):
        flag = False
        print("length is different")
    for key in graph1:
        if graph1[key] != graph2[key]:
            flag = False
            print("difference in " + key)
            print(file1 + ":\t" + str(graph1[key]))
            print(file2 + ":\t" + str(graph2[key]))
            break
    if flag:
        print("all the same")

def get_setList(file):
    curLabel = 0
    setList = []
    with open(file, encoding="utf-8") as f:
        for line in f:
            splt = line.split("\t")
            name = splt[0]
            if (int)(splt[1]) != curLabel:
                newSet = set()
                newSet.add(name)
                setList.append(newSet)
                curLabel = curLabel + 1
            else:
                setList[-1].add(name)
    return setList

def cmp_output6b(file1, file2):
    """文件中的标签值从1递增"""
    flag = True
    setList1 = get_setList(file1)
    setList2 = get_setList(file2)
    if(len(setList1) != len(setList2)):
        print("label numbers are different!")
        flag = False
    else:
        sameNum = 0;
        for i in setList1:
            for j in setList2:
                if(len(i - j) == 0 and len(j - i) == 0):
                    sameNum = sameNum + 1
        if sameNum != len(setList1):
            print("2 files are different")
            flag = False
    if flag == True:
        print("2 files are the same")

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("请输入参数：1/2/3/4/6b")
    elif sys.argv[1] == '1':
        cmp_output1("output1a.txt", "output1b.txt")
    elif sys.argv[1] == '2':
        cmp_output24("output2a.txt", "output2b.txt")
    elif sys.argv[1] == '3':
        cmp_output3("output3a.txt", "output3b.txt")
    elif sys.argv[1] == '4':
        cmp_output24("output4a.txt", "output4b.txt")
    elif sys.argv[1] == '6b':
        cmp_output6b("output6ba.txt", "output6bb.txt")
