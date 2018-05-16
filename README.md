# MPLab
A repository for MPLabs in NJU, 2018.

## 构建

```
mvn package
```

## 运行InvertedIndexer

```
hadoop jar MPLab-1.0.jar com.nju.mplab14.InvertedIndexer {folder-in} {folder-out}
```

## 运行HBaseScanner

```
mvn exec:java -Dexec.mainClass="com.nju.mplab14.HBaseScanner"
```

## 结果文件

* `HBaseScanner`运行结果为`frequency_statistics.txt`，保存了词频统计信息
* Hive读取`frequency_statistics.txt`文件，得到如下两个结果文件
  * 出现次数大于300的词：`frequency_300.txt`
  * 前100个出现次数最多的词：`top_100.txt`