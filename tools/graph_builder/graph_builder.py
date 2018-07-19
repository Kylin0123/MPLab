
graph_template_begin = """
<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8">
   </head>
   <body style="height: 90%">
       <div id="container" style="margin: 0 auto; height: 100%; width: 100%; border: 1px solid black"></div>
       <p>By cbc, cy, czq, zs</p>
       <script type="text/javascript" src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>
       <script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;

    """

graph_template_end = """

    var categories = [];
    for (var i = 0; i < 1400; i++) {
        categories[i] = {
            name: '标签' + i
        };
    }
    graph.nodes.forEach(function (node) {
        node.itemStyle = null;
        node.value = node.symbolSize / 3;
        node.category = node.attributes.modularity_class;
        // Use random x, y
        node.x = node.y = null;
        node.draggable = true;
    });
    option = {
        title: {
            text: '金庸的江湖',
            subtext: '人物关系图',
            top: 'bottom',
            left: 'right'
        },
        tooltip: {},
        animation: false,
        series : [
            {
                name: '金庸的江湖',
                type: 'graph',
                layout: 'force',
                data: graph.nodes,
                links: graph.links,
                categories: categories,
                roam: true,
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                force: {
                    repulsion: 100
                }
            }
        ]
    };

    myChart.setOption(option);
       </script>
   </body>
</html>
"""

if __name__ == '__main__':
    pagerank = {}
    with open("pagerank.txt", "r", encoding='utf-8') as f:
        for line in f.readlines():
            n, pr = line[:-1].split("\t")
            pagerank[n] = pr

    label = {}
    with open("label.txt", "r", encoding='utf-8') as f:
        for line in f.readlines():
            n, lb = line[:-1].split("\t")
            label[n] = lb

    link_table = {}
    with open("link_table.txt", "r", encoding='utf-8') as f:
        for line in f.readlines():
            n, nbl = line[:-1].split("\t")  # type: (str, str)
            link_table[n] = list(map(lambda s: tuple(s.split(",")), nbl[1:-1].split("|")))

    nodes = "[\n"
    nodes += ",\n".join(["{attributes: {modularity_class: " + label[node] + "}, id: \"" + node + "\", name: \"" + node + "\", symbolSize: " + str(float(pagerank[node]) * 3) + "}" for node in link_table.keys() if float(pagerank[node]) > 1.5])
    nodes += "\n]"

    links = "[\n"

    for link_k, link_v in link_table.items():
        len_ = len(link_v)
        link_v.sort(key=lambda x: -float(x[1]))
        link_v = link_v[0:max(1, int(len_ * 0.1))]
        for link_name, link_weight in link_v:
            links += """{id:\"0\", name:null, source:\"""" + link_k + """\", target:\"""" + link_name + """\", lineStyle:{normal:{}}},\n"""
    links += "\n]"

    graph = """var graph = {
        links : """ + links + """,
        nodes : """ + nodes + "\n}"

    graph_template = graph_template_begin + graph + graph_template_end
    print("Build graph success!")
    with open("graph_template.html", "w", encoding="utf-8") as f:
        f.write(graph_template)
    print("Write into graph_template.html!")
