# MPLab
A repository for MPLabs in NJU, 2018.

## how to build

```
mvn package
```

## how to run InvertedIndexer

```
hadoop jar MPLab-1.0.jar com.nju.mplab14.InvertedIndexer {folder-in} {folder-out}
```

## how to run HBaseScanner

```
mvn exec:java -Dexec.mainClass="com.nju.mplab14.HBaseScanner"
```
