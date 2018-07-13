# 任务4 数据分析：基于人物关系图的PageRank计算

在给出人物关系图之后，我们就可以对人物关系图进行一个数据分析。其中一个典型的分析任务是：PageRank值计算。通过计算PageRank，我们就可以定量地金庸武侠江湖中的“主角”们是哪些。

## 输入输出

输入：任务3的输出
```
狄云 [戚芳,0.33333|戚长发,0.333333|卜垣,0.333333]
戚芳 [狄云,0.25|戚长发,0.25|卜垣,0.5]
戚长发 [狄云,0.33333|戚芳,0.333333|卜垣,0.333333]
卜垣 [狄云,0.25|戚芳,0.5|戚长发,0.25]
```
或者任务4的输出 

输出：
```
人物\t人物的PageRank值
```

## 伪代码

>Main:
>
>​	Read output3 into DC
>
>​	count the number in output3 into the **N**
>
>
>
>Mapper:
>
>​	Input: 	name \t curRank **or**  NodeList
>
>​	Setup:	from DC read  \< name, NodeList > into map
>
>​	Process:	if NodeList
>
>​				curRank = initRank
>
>​			for each <u, probability> in NodeList
>
>​				emit \< u , curRank * probability \>
>
>​			emit \< name , NodeList>

> Reducer:
>
> ​	Setup: from context read **N**
>
> ​	Input:  name \t val **or** NodeList
>
> ​	Process: 
>
> ​			newRank = (1-d)/N + d*sum( **all** val  )
>
> ​			emit \< name , newRank  >

