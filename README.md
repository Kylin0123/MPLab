# 大数据实验

> Note: 本项目采用Maven构建，所有命令都在工程目录下进行。

## 构建

> 如果pom.xml的java编译选项的source和target为1.8版本，请先改成1.7版本再使用Maven生成。

首先使用Maven在target目录下生成目标jar包

```bash
mvn clean package
```

然后将lib目录下的所有依赖jar包打入目标jar包

```bash
jar uf ./target/MPLab-1.0.jar lib/*.jar
```

这样我们就得到了`./target/MPLab-1.0.jar`

## 运行

将`./target/MPLab-1.0.jar`上传到集群上，然后在集群的同一目录下使用脚本`run.sh`运行。