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

/**
 * 要求传入id，数据list
 * @param chartId
 */
function getPieChart(chartId) {
    jQuery('#' + chartId).highcharts({
        title: {
            text: '学院经费分布'
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
            data: [
                {
                    name: '高等数学',
                    y: 1200,
                    sliced: true,
                    selected: true
                },
                // ['高等数学',      1200],
                ['概率论', 1000],
                ['应用数学', 600],
                ['离散数学', 900],
                ['统计学', 800],
                ['计算数学', 500],
                ['运筹学与控制论', 500],
                ['数学分析', 900]
            ]
        }]
    });

}


function getBarChart(chartId) {
    jQuery('#' + chartId).highcharts({
        chart: {
            type: 'column' // 竖柱图
        },
        title: {
            text: '学院经费比例'
        },
        xAxis: {
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
        tooltip: {shared: true},
    });

}

