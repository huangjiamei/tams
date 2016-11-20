/**
 * 汉化datatable
 */
(function(){

    var oLanguage={
        "oAria": {
            "sSortAscending": ": 升序排列",
            "sSortDescending": ": 降序排列"
        },
        "oPaginate": {
            "sFirst": "&laquo;",
            "sLast": "&raquo;",
            "sNext": "&rsaquo;",
            "sPrevious": "&lsaquo;"
        },
        "sEmptyTable": "没有相关记录",
        "sInfo": " _START_ - _END_/共_TOTAL_条数据",
        "sInfoEmpty": "0-0/0 ",
        "sInfoFiltered": "",
        "sInfoPostFix": "",
        "sDecimal": "",
        "sInfoThousands": "",
        "sLengthMenu": "每页显示_MENU_",
        "sLoadingRecords": "正在载入...",
        "sProcessing": "正在载入...",
        "sSearch": "",
        "sSearchPlaceholder": "",
        "sUrl": "",
        "sZeroRecords": "没有相关记录"
    }

    jQuery.fn.dataTable.defaults.oLanguage=oLanguage;
    jQuery.extend( jQuery.fn.dataTable.defaults, {
        "bSort": false,
        "sPaginationType":"full_numbers",
        //"sDom":"&lt;'top't&gt;&lt;'bottom'ilp&gt;" //自定义布局sdom，暂时没有生效
    } );
})();

/**
 * 每次点击btn都会提交两次，
 * 所有首先判断transfer和editor中内容是否有区别，内容不同时才会提交
 * @param editorID
 * @param transferID
 * @returns {boolean}
 */
function doEditorPreSubmit(editorID,transferID) {
    var content=UE.getEditor(editorID).getPlainTxt();
    alert(content)
    if (document.getElementById(transferID).value==content){
        return false;
    }else{
        jQuery('#' + transferID).val(content);
        return true;
    }
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



//var jsonObj = {"header":["编辑","待负责人审核","待学院审核","待学校审核","工作"],"data":[[{"checked":false,"disabled":false},{"checked":true,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false}],[{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":true,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false}],[{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":true,"disabled":false},{"checked":false,"disabled":false}],[{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":true,"disabled":false}],[{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false},{"checked":false,"disabled":false}]]}

function drawStatusTransTable(boxid,tableJson){

    var jsonObj = eval('('+tableJson+')');
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

function save(id) {
    var tableTransObj = {
        "header":[],
        "data":[]
    };
    var tbl = document.getElementById('status').getElementsByTagName('tbody')[0];
    var rows = tbl.childNodes;
    var head = rows[0].childNodes;
    for(var i=1;i<head.length;i++)
    {
        tableTransObj.header[i-1] = head[i].innerHTML;
    }

    for(var i=1;i<head.length;i++)
    {
        var row_data =[];
        var row = rows[i].childNodes;
        for(var j=1;j<head.length;j++)
        {
            row_data[j-1]={
                "checked":null,
                "disabled":null
            }
            row_data[j-1].checked = row[j].childNodes[0].checked;
            row_data[j-1].disabled = row[j].childNodes[0].disabled;
        }
        tableTransObj.data[i-1]=row_data;
    }
    document.getElementById('hidden-statusTrans').getElementsByTagName('textarea')[0].innerHTML = JSON.stringify(tableTransObj);
    jQuery("#hidden-save").click();

}


/**
 * 要求传入chart容器id，表title，中转站id
 * 指定隐藏input中转站的dataTransferid，从该中转站提取val作为data输入
 * data格式为：[['高数',1000],['线代',5000]]
 * 或格式2：[{"name":"高数","y":10000},{"name":"线代","y":5000}]
 * @param chartId
 */
function getPieChart(chartId,title,data) {
    data = JSON.parse(data);

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
            // 除了此项占比，还需要加一个此项具体数值
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
}

/**
 * barChart输入的data格式为(待定)
 * @param chartId
 * @param title
 * @param dataTransferId
 */
function getBarChart(chartId,title,data) {
    data = eval(data);

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
                    format: '{y} 元'
                }
            },
            {
                title: {text: '助教优秀率'},
                opposite: true,
                labels: {
                    format: '{y} %'
                }
            }
        ],
        series: [{
            name: '经费',
            color: '#ff4d4d',
            // objlist.y
            data: [1200, 1000, 600, 900, 800, 500, 500, 900],
            tooltip: {ySuffix: '元'},
            pointPadding: 0.15,
            pointPlacement: -0.03
        }, {
            name: '助教优秀率',
            color: '#66a3ff',
            data: [60, 90, 85, 70, 80, 85, 80, 75],
            tooltip: {ySuffix: '%'},
            yAxis: 1, // 双y轴的关键
            pointPadding: 0.15,
            pointPlacement: 0.03
        }],
        tooltip: {shared: true}
    });

}


var filterHintCache = {

    "searchCourseNm": [
        "画法几何",
        "空气污染控制工程",
        "环境水资源学",
        "建筑项目环境管理",
        "建筑给排水工程（含高层）",
        "有机化学II",
        "建筑节能技术-可再生资源",
        "环境认识实习",
        "化工原理实验（Ⅱ）",
        "水工程施工与项目管理",
        "制冷压缩机",
        "物理化学（Ⅰ-2）",
        "化工制图",
        "高聚物合成工艺学",
        "医学图像处理实验",
        "环境工程CAD",
        "复合材料力学",
        "健美操第一专项训练（6）",
        "单片机原理及应用"],
    "searchCourseNmb": [
        "OE12002220",
        "MSE13019220",
        "EE15001025",
        "LA19017740",
        "PESS10456",
        "ENVR21000240++",
        "EnAd4033630",
        "EnAd4033630",
        "ColdFusion",
        "EE11000",
        "ME11027820",
        "OE12005320",
        "CEME21030640",
        "ENVR21001230",
        "BEE21023920",
        "ENVR21031760",
        "CHEM22036930",
        "CHEN22037635",
        "ME11025235",
        "EP14004520",],
    "searchCourseManager": [
        "140388-006",
        "140388-007",
        "229337-003",
        "229184-001",
        "220040-005",
        "239118-001",
        "189333-002",
        "210044-002",
        "239118-002"],
    "searchCourseInsCode": [
        "test"],


    "condCourseName": [
        "画法几何",
        "空气污染控制工程",
        "环境水资源学",
        "建筑项目环境管理",
        "建筑给排水工程（含高层）",
        "有机化学II",
        "建筑节能技术-可再生资源",
        "环境认识实习",
        "化工原理实验（Ⅱ）",
        "水工程施工与项目管理",
        "制冷压缩机",
        "物理化学（Ⅰ-2）",
        "化工制图",
        "高聚物合成工艺学",
        "医学图像处理实验",
        "环境工程CAD",
        "复合材料力学",
        "健美操第一专项训练（6）",
        "单片机原理及应用"],
    "condCourseCode": [
        "OE12002220",
        "MSE13019220",
        "EE15001025",
        "LA19017740",
        "PESS10456",
        "ENVR21000240++",
        "EnAd4033630",
        "EnAd4033630",
        "ColdFusion",
        "EE11000",
        "ME11027820",
        "OE12005320",
        "CEME21030640",
        "ENVR21001230",
        "BEE21023920",
        "ENVR21031760",
        "CHEM22036930",
        "CHEN22037635",
        "ME11025235",
        "EP14004520",],
    "condClassNumber": [
        "140388-006",
        "140388-007",
        "229337-003",
        "229184-001",
        "220040-005",
        "239118-001",
        "189333-002",
        "210044-002",
        "239118-002"],
    "condInstructorName": [
        "test"],

}



/**
 * 该函数可统一地将表格外的过滤器移动到表格内，隐藏搜索按钮，
 * 为所有过滤器添加按下搜索按钮事件（select添加onchange，input添加keydown）
 *
 * @param searchbox ----xml中放置所有过滤器的外层框，其中包括搜索按钮，过滤器顺序与表格中一致
 * 若有的列不需要过滤器，则在该位置加入一个messageField或其他控件，
 * 并添加hidden-field的样式类来占位
 *
 * @param tablebox  ----xml中表格bean的id
 */
function refreshTableFilter(searchbox,tablebox) {

    //得到所有过滤器
    var searchFields = jQuery(searchbox).children("div");

    //得到table元素
    var table = jQuery(jQuery(tablebox).find("table")[0]);

    //表头
    var thead = jQuery(table.find('thead')[0]);
    //过滤器所在的表头
    var filter = jQuery("<thead class='search-filter'></thead>");

    var tr = jQuery("<tr></tr>");
    //得到searchbox中的搜索按钮，以便添加事件
    var searchButton =jQuery(searchbox).children("button")[0];

    //遍历过滤器，移入表格中
    for (var i = 0;i < searchFields.size();i++)
    {
        var th = jQuery("<th></th>");
        th.append(searchFields[i]);
        tr.append(th);

        var field = jQuery(searchFields[i]);
        //alert(field.children()[0].tagName);
        //为输入框添加事件
        if (field.children()[0].tagName=='INPUT'){

            //普通的搜索框
            if (!jQuery(field.children()[0]).hasClass("hasDatepicker")){
                jQuery(field.children()[0]).autocomplete({
                    source: eval("filterHintCache."+field.find("input")[0].name)
                }).attr("class", "form-control input-sm uif-textControl column-filter");
            }
            jQuery(field.children()[0]).on(
                {keydown: function(e){
                    var key = e.which;
                    if(key == 13){
                        e.preventDefault();
                        jQuery(searchButton).click();
                    }
                }
                });

        }
        //为下拉框添加事件
        if (field.children()[0].tagName=='SELECT'){
            jQuery(field.children()[0]).comboSelect();
            jQuery(field.find("input")[0]).attr("class", "form-control input-sm uif-textControl column-filter");
            jQuery(field.find("select")[0]).on('change', function () {
                jQuery(searchButton).click();
            } );
        }
    }
    filter.append(tr);
    thead.after(filter)
}

/**
 * 为每个page生成edusec中的header样式
 * 简易版方法
 * @param id header-content中额外设置的一个标题容器对应的id
 * @param icon 图标样式
 * @param bigTitle 正标题
 * @param smallTitle 副标题
 */
function initContentHeader(id,icon,bigTitle,smallTitle) {
    jQuery('#'+id).html('<h2> <i class="'+icon+'"></i> '+bigTitle+' |<small> '+smallTitle+'</small></h2>');
}


function initRightBtnMenu(targetid) {
    //alert(jQuery(".table tbody tr").size())
    jQuery("#"+targetid+" tbody").contextPopup({
        title: '',
        items: [
            {
                label:'添加',
                icon:'icon-plus',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    alert("添加"+id+', index='+index);

                }
            },
            // {label:'查看',     icon:'icon-search',              action:function() { alert('clicked 3') } },
            null, // divider
            {
                label:'选中',
                icon:'icon-signup',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    alert("选中"+id+', index='+index);
                }
            },
            null, // divider
            {
                label:'查看',
                icon:'icon-search',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    alert("查看"+id+', index='+index);
                }
            },
            {
                label:'编辑',
                icon:'icon-pencil2',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    alert("编辑"+id+', index='+index);
                }
            },
            null, // divider
            {
                label:'删除',
                icon:'icon-remove2',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    alert("删除"+id+', index='+index);
                }
            },
        ]
    });
    // jQuery(document).click(function(e){
    //     /**
    //      * 由于rice的特性，table中每个元素的id都为xxx_line15的形式，可以通过id结合正则表达式来确定现在操作的到底是第几行
    //      * 如果当前容器没找到id，那么就去子容器找。
    //      *
    //      * 非table的情况暂时未考虑
    //      */
    //     var id= jQuery(this.target).attr('id');
    //     if (typeof(id) == "undefined")
    //         id=jQuery(this.target.childNodes[0]).attr('id');
    //     var index=id.match("line[0-9]+")[0].match('[0-9]+');
    //     alert(id+', index='+index);
    // });
}

/**
 * 全选测试，还不能使用(功能完成后删除此行)
 */
function checkAll() {
    // jQuery(".tams-activity-group-title").each(function () {
    //     jQuery(this.childNodes[0]).attr("checked", true);
    // });
    // jQuery(".tams-activity-item-title").each(function () {
    //     jQuery(this).attr("checked", "checked");
    // });
}

/**
 * 添加测边栏自动伸缩功能
 */
function setupAutoSideBar() {

    var nav = jQuery('#Uif-Navigation');
    winWidth = document.body.clientWidth;
    if (winWidth>768)
        // 让导航栏下来一点，避免缩放按键被遮挡
        nav.css("margin-top", 50);

    window.onresize = function(){
        var nav = jQuery('#Uif-Navigation');
        winWidth = document.body.clientWidth;
        if (winWidth>768){
            // 让导航栏下来一点，避免缩放按键被遮挡
            nav.css("margin-top", 50);
            if (jQuery('.sidebar-collapse').parent().hasClass('sidebar-collapsed')) {
                jQuery('.sidebar-collapse').click();
            }
        }else{
            //移动端应该将margin还原
            nav.css("margin-top", 0);
            if (!jQuery('.sidebar-collapse').parent().hasClass('sidebar-collapsed')) {
                jQuery('.sidebar-collapse').click();
            }
        }
    }
}
/**
 * 部分页面去掉侧边栏
 */
function removeSideBar()
{
    var nav = jQuery('#Uif-Navigation');
    if (typeof (nav)=="undefined")
        return
    nav.remove();
    jQuery(".uif-hasLeftNav").css("margin-left",0).removeClass("uif-hasLeftNav");
}
