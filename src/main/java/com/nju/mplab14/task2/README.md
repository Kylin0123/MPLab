# 任务2 特征抽取：人物同现统计

本任务的重要完成基于单词同现算法的人物同现统计。在人物同现分析中，如果两个人在原文的同一段落中出现，则认为两个人发生了一次同现关系。我们需要对人物之间的同现关系次数进行统计，同现关系次数越多，则说明两人的关系越密切。

## 输入输出

输入：任务1的输出；

输出：在金庸的所有武侠小说中，人物之间的同现次数。

## 示例

输入：

```
狄云 戚芳 戚芳 戚长发 卜垣
戚芳 卜垣 卜垣
```

输出：
 
 ```
<狄云,戚芳> 1
<狄云,戚长发> 1
<狄云,卜垣> 1
<戚芳,狄云> 1
<戚芳,戚长发> 1
<戚芳,卜垣> 2
<戚长发,狄云> 1
<戚长发,戚芳> 1
<戚长发,卜垣> 1
<卜垣,狄云> 1
<卜垣,戚芳> 2
<卜垣,戚长发> 1
```
