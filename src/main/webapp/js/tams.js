/**
 * Created by DELL on 2016-10-16.
 */

function doEditorPreSubmit(editorID,transferID) {

    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}



function initNavDialog() {
    jQuery(".navigationDialog .nav>ul>:first-child>a").tab('show');
    //去掉dialog本身的header
    jQuery(".navigationDialog>.modal-content>.modal-header").remove();

    jQuery("#navDialog").removeClass("fade");
    //添加导航栏点击切换事件
    jQuery(".navigationDialog .nav a").click(function(e){
        e.preventDefault();
        jQuery(this).tab('show');
    });


    jQuery(".navigationDialog .close").click(function(a) {
        a.stopPropagation();
        jQuery(".navigationDialog .nav .active").removeClass("active");
        jQuery(".navigationDialog .tab-content .active").removeClass("active");
    })
}



var jsonObj =  {
    header:["编辑","待负责人审核","待学院审核","待学校审核","招聘","工作"],

    data:[
        [{
            "checked":true,
            "disabled":false
        }, {
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":true
        }],[{
            "checked":true,
            "disabled":false
        }, {
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        }],[{
            "checked":true,
            "disabled":false
        }, {
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        }],[{
            "checked":true,
            "disabled":false
        }, {
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":true
        }],[{
            "checked":true,
            "disabled":true
        }, {
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        }],[{
            "checked":true,
            "disabled":false
        }, {
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":true
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":false
        },{
            "checked":true,
            "disabled":true
        }]



    ]
}

function drawStatusTransTable(boxid){

    var tbl = document.createElement("table");
    var tbody = document.createElement("tbody");
    tbl.className+="table table-striped table-bordered table-hover ";

    var thead = document.createElement("tr");

    var topleft = document.createElement("th");
    topleft.style.width = '130px';
    topleft.innerHTML = "<div class=\"out\"> <b>当前状态</b>  <em>新状态</em> </div>";

    thead.appendChild(topleft);

    for(var i=0;i<jsonObj.header.length;i++){

        var header = document.createElement("th");
        header.innerHTML = jsonObj.header[i];
        thead.appendChild(header);

    }
    tbody.appendChild(thead);

    for(var i=0;i<jsonObj.header.length;i++){

        var row = document.createElement("tr");
        var header = document.createElement("th");
        header.innerHTML = jsonObj.header[i];
        row.appendChild(header);

        for(var j=0;j<jsonObj.header.length;j++){

            var td = document.createElement("td");
            var cb = document.createElement("input");
            cb.type="checkbox";

            cb.checked = jsonObj.data[i][j].checked;
            cb.disabled = jsonObj.data[i][j].disabled;
            td.appendChild(cb);

            row.appendChild(td);

        }
        tbody.appendChild(row);
    }
    tbl.appendChild(tbody);
    var box = document.getElementById(boxid);
    box.appendChild(tbl);
}

function save() {
    var tmp_header=[];
    var tmp_data=[];
    var tbl = document.getElementById(status).getElementsByTagName('tbody')[0];
    var rows = tbl.childNodes;
    var head = rows[0].childNodes;
    for(var i=1;i<head.length;i++)
    {
        tmp_header[i-1] = head[i].innerHTML;
    }

    for(var i=1;i<head.length;i++)
    {
        var row_data =[];
        var row = rows[i].childNodes;
        for(var j=1;j<head.length;j++)
        {
            row_data[j-1].checked = row[j].childNodes[0].checked;
            row_data[j-1].disabled = row[j].childNodes[0].disabled;
        }
        tmp_data[i-1]=row_data;
    }
    jsonObj.header = tmp_header;
    jsonObj.data = tmp_data;


}


/**
 * 要求传入chart容器id，表title，中转站id
 * 指定隐藏input中转站的dataTransferid，从该中转站提取val作为data输入
 * data格式为：[['name1',value1],['name2',value2]]
 * @param chartId
 */
function getPieChart(chartId,title,dataTransferId) {
    // var data=eval('[["高等数学", 1200],["概率论", 1000],["应用数学", 600],["离散数学", 900],["统计学", 800],["计算数学", 500],["运筹学与控制论", 500],["数学分析", 900]]');
    var data =eval(jQuery('#'+dataTransferId).val());

    // 尝试过将下面这段setOptions代码提取为initHigicharts()但是没有效果
    Highcharts.setOptions({
        lang: {
            printChart:"打印图表",
            downloadJPEG: "下载JPEG 图片" ,
            downloadPDF: "下载PDF文档"  ,
            downloadPNG: "下载PNG 图片"  ,
            downloadSVG: "下载SVG 矢量图" ,
            exportButtonTitle: "导出图片"
        }
    });

    jQuery('#' + chartId).highcharts({
        credits:{
          enabled:false     // 去除highcharts的水印
        },
        title: {
            text: title
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true, // 一块饼的提取
                cursor: 'pointer', // 鼠标指针能否点击
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: ' ',
            data: data
        }]
    });


    // [
    //     {
    //         name: '高等数学',
    //         y: 1200,
    //         sliced: true,
    //         selected: true
    //     },
    //     // ['高等数学',      1200],
    //     ['概率论', 1000],
    //     ['应用数学', 600],
    //     ['离散数学', 900],
    //     ['统计学', 800],
    //     ['计算数学', 500],
    //     ['运筹学与控制论', 500],
    //     ['数学分析', 900]
    // ]
}

/**
 * barChart输入的data格式为(待定)
 * @param chartId
 * @param title
 * @param dataTransferId
 */
function getBarChart(chartId,title,dataTransferId) {
    var data =eval(jQuery('#'+dataTransferId).val());

    // 尝试过将下面这段setOptions代码提取为initHigicharts()但是没有效果
    Highcharts.setOptions({
        lang: {
            printChart:"打印图表",
            downloadJPEG: "下载JPEG 图片" ,
            downloadPDF: "下载PDF文档"  ,
            downloadPNG: "下载PNG 图片"  ,
            downloadSVG: "下载SVG 矢量图" ,
            exportButtonTitle: "导出图片"
        }
    });

    jQuery('#' + chartId).highcharts({
        credits:{
            enabled:false   // 去除highcharts的水印
        },
        chart: {
            type: 'column' // 竖柱图
        },
        title: {
            text: title
        },
        xAxis: {
            // objlist.name
            categories: [
                '高等数学',
                '概率论',
                '应用数学',
                '离散数学',
                '统计学',
                '计算数学',
                '运筹学与控制论',
                '数学分析']
        },
        yAxis: [
            {
                allowDecimals: false, // 坐标轴刻度不为小数
                title: {text: '经费'},
                labels: {
                    format: '{value} 元'
                }
            },
            {
                title: {text: '助教优秀率'},
                opposite: true,
                labels: {
                    format: '{value} %'
                }
            }
        ],
        series: [{
            name: '经费',
            color: '#ff4d4d',
            // objlist.value
            data: [1200, 1000, 600, 900, 800, 500, 500, 900],
            tooltip: {valueSuffix: '元'},
            pointPadding: 0.15,
            pointPlacement: -0.03
        }, {
            name: '助教优秀率',
            color: '#66a3ff',
            data: [60, 90, 85, 70, 80, 85, 80, 75],
            tooltip: {valueSuffix: '%'},
            yAxis: 1, // 双y轴的关键
            pointPadding: 0.15,
            pointPlacement: 0.03
        }],
        tooltip: {shared: true}
    });

}

