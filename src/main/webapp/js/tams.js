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

function showElement(id) {
    var element=jQuery('#'+id);
    element.css('display','block');
}


/**
 * 添加测边栏自动伸缩功能
 */
function setupAutoSideBar() {
    var nav = jQuery('#Uif-Navigation');
    winWidth = document.body.clientWidth;
    nav.css("position", "fixed");
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

/*
 Uif-TabGroup部分的切换列表加上icon图片
 */
function tabGroupIcon(){
    jQuery("#RequestTaPage .nav-tabs").children("li:eq(0)").find("a").html('<h5><i class="icon-book"></i><big>课程</big></h5>');
    jQuery("#RequestTaPage .nav-tabs").children("li:eq(1)").find("a").html('<h5><i class="icon-arrow-up"></i><big>反馈</big></h5>');
    jQuery("#tabGroupOne .uif-sectionHeader").html('<h4><i class="icon-book"></i><big>课程信息</big></h4>');
    jQuery("#tabGroupTwo .uif-sectionHeader").html('<h4><i class="icon-arrow-up"></i><big>反馈信息</big></h4>');
}


/**
 *该方法生成动态变化的面包线
 * @param breadcrumbId 面包线控件id
 * @param value 要修改的值
 * @param key   待修改option的key
 */
function setupDynamicBreadcrumb(breadcrumbId,value,key){
    jQuery("#"+breadcrumbId+" li *"+"[data-key='"+key+"']").html(value)
}


/*
  时间控件
 */
!function ($) {

    var DateRangePicker = function (element, options, cb) {
        var hasOptions = typeof options == 'object';
        var localeObject;

        // option defaults

        this.startDate = moment().startOf('day');
        this.endDate = moment().startOf('day');
        this.minDate = false;
        this.maxDate = false;
        this.dateLimit = false;

        this.showDropdowns = false;
        this.showWeekNumbers = false;
        this.timePicker = false;
        this.timePickerIncrement = 30;
        this.timePicker12Hour = true;
        this.ranges = {};
        this.opens = 'right';

        this.buttonClasses = ['btn', 'btn-small'];
        this.applyClass = 'btn-success';
        this.cancelClass = 'btn-default';

        this.format = 'MM/DD/YYYY';
        this.separator = ' - ';

        this.locale = {
            applyLabel: 'Apply',
            cancelLabel: 'Cancel',
            fromLabel: 'From',
            toLabel: 'To',
            weekLabel: 'W',
            customRangeLabel: 'Custom Range',
            daysOfWeek: moment()._lang._weekdaysMin.slice(),
            monthNames: moment()._lang._monthsShort.slice(),
            firstDay: 0
        };

        this.cb = function () { };

        // by default, the daterangepicker element is placed at the bottom of
        // HTML body
        this.parentEl = 'body';

        // element that triggered the date range picker
        this.element = $(element);

        if (this.element.hasClass('pull-right'))
            this.opens = 'left';

        if (this.element.is('input')) {
            this.element.on({
                click: $.proxy(this.show, this),
                focus: $.proxy(this.show, this)
            });
        } else {
            this.element.on('click', $.proxy(this.show, this));
        }

        localeObject = this.locale;

        if (hasOptions) {
            if (typeof options.locale == 'object') {
                $.each(localeObject, function (property, value) {
                    localeObject[property] = options.locale[property] || value;
                });
            }

            if (options.applyClass) {
                this.applyClass = options.applyClass;
            }

            if (options.cancelClass) {
                this.cancelClass = options.cancelClass;
            }
        }

        var DRPTemplate = '<div class="daterangepicker dropdown-menu">' +
            '<div class="calendar left"></div>' +
            '<div class="calendar right"></div>' +
            '<div class="ranges">' +
            '<div class="range_inputs">' +
            '<div class="daterangepicker_start_input" style="float: left">' +
            '<label for="daterangepicker_start">' + this.locale.fromLabel + '</label>' +
            '<input class="input-mini" type="text" name="daterangepicker_start" value="" disabled="disabled" />' +
            '</div>' +
            '<div class="daterangepicker_end_input" style="float: left; padding-left: 11px">' +
            '<label for="daterangepicker_end">' + this.locale.toLabel + '</label>' +
            '<input class="input-mini" type="text" name="daterangepicker_end" value="" disabled="disabled" />' +
            '</div>' +
            '<button class="' + this.applyClass + ' applyBtn" disabled="disabled">' + this.locale.applyLabel + '</button>&nbsp;' +
            '<button class="' + this.cancelClass + ' cancelBtn">' + this.locale.cancelLabel + '</button>' +
            '</div>' +
            '</div>' +
            '</div>';

        this.parentEl = (hasOptions && options.parentEl && $(options.parentEl)) || $(this.parentEl);
        // the date range picker
        this.container = $(DRPTemplate).appendTo(this.parentEl);

        if (hasOptions) {

            if (typeof options.format == 'string')
                this.format = options.format;

            if (typeof options.separator == 'string')
                this.separator = options.separator;

            if (typeof options.startDate == 'string')
                this.startDate = moment(options.startDate, this.format);

            if (typeof options.endDate == 'string')
                this.endDate = moment(options.endDate, this.format);

            if (typeof options.minDate == 'string')
                this.minDate = moment(options.minDate, this.format);

            if (typeof options.maxDate == 'string')
                this.maxDate = moment(options.maxDate, this.format);

            if (typeof options.startDate == 'object')
                this.startDate = moment(options.startDate);

            if (typeof options.endDate == 'object')
                this.endDate = moment(options.endDate);

            if (typeof options.minDate == 'object')
                this.minDate = moment(options.minDate);

            if (typeof options.maxDate == 'object')
                this.maxDate = moment(options.maxDate);

            if (typeof options.ranges == 'object') {
                for (var range in options.ranges) {

                    var start = moment(options.ranges[range][0]);
                    var end = moment(options.ranges[range][1]);

                    // If we have a min/max date set, bound this range
                    // to it, but only if it would otherwise fall
                    // outside of the min/max.
                    if (this.minDate && start.isBefore(this.minDate))
                        start = moment(this.minDate);

                    if (this.maxDate && end.isAfter(this.maxDate))
                        end = moment(this.maxDate);

                    // If the end of the range is before the minimum (if min is
                    // set) OR
                    // the start of the range is after the max (also if set)
                    // don't display this
                    // range option.
                    if ((this.minDate && end.isBefore(this.minDate)) || (this.maxDate && start.isAfter(this.maxDate))) {
                        continue;
                    }

                    this.ranges[range] = [start, end];
                }

                var list = '<ul>';
                for (var range in this.ranges) {
                    list += '<li>' + range + '</li>';
                }
                list += '<li>' + this.locale.customRangeLabel + '</li>';
                list += '</ul>';
                this.container.find('.ranges').prepend(list);
            }

            if (typeof options.dateLimit == 'object')
                this.dateLimit = options.dateLimit;

            // update day names order to firstDay
            if (typeof options.locale == 'object') {

                if (typeof options.locale.daysOfWeek == 'object') {

                    // Create a copy of daysOfWeek to avoid modification of
                    // original
                    // options object for reusability in multiple
                    // daterangepicker instances
                    this.locale.daysOfWeek = options.locale.daysOfWeek.slice();
                }

                if (typeof options.locale.firstDay == 'number') {
                    this.locale.firstDay = options.locale.firstDay;
                    var iterator = options.locale.firstDay;
                    while (iterator > 0) {
                        this.locale.daysOfWeek.push(this.locale.daysOfWeek.shift());
                        iterator--;
                    }
                }
            }

            if (typeof options.opens == 'string')
                this.opens = options.opens;

            if (typeof options.showWeekNumbers == 'boolean') {
                this.showWeekNumbers = options.showWeekNumbers;
            }

            if (typeof options.buttonClasses == 'string') {
                this.buttonClasses = [options.buttonClasses];
            }

            if (typeof options.buttonClasses == 'object') {
                this.buttonClasses = options.buttonClasses;
            }

            if (typeof options.showDropdowns == 'boolean') {
                this.showDropdowns = options.showDropdowns;
            }

            if (typeof options.timePicker == 'boolean') {
                this.timePicker = options.timePicker;
            }

            if (typeof options.timePickerIncrement == 'number') {
                this.timePickerIncrement = options.timePickerIncrement;
            }

            if (typeof options.timePicker12Hour == 'boolean') {
                this.timePicker12Hour = options.timePicker12Hour;
            }

        }

        if (!this.timePicker) {
            this.startDate = this.startDate.startOf('day');
            this.endDate = this.endDate.startOf('day');
        }

        // apply CSS classes to buttons
        var c = this.container;
        $.each(this.buttonClasses, function (idx, val) {
            c.find('button').addClass(val);
        });

        if (this.opens == 'right') {
            // swap calendar positions
            var left = this.container.find('.calendar.left');
            var right = this.container.find('.calendar.right');
            left.removeClass('left').addClass('right');
            right.removeClass('right').addClass('left');
        }

        if (typeof options == 'undefined' || typeof options.ranges == 'undefined') {
            this.container.find('.calendar').show();
            this.move();
        }

        if (typeof cb == 'function')
            this.cb = cb;

        this.container.addClass('opens' + this.opens);

        // try parse date if in text input
        if (!hasOptions || (typeof options.startDate == 'undefined' && typeof options.endDate == 'undefined')) {
            if ($(this.element).is('input[type=text]')) {
                var val = $(this.element).val();
                var split = val.split(this.separator);
                var start, end;
                if (split.length == 2) {
                    start = moment(split[0], this.format);
                    end = moment(split[1], this.format);
                }
                if (start != null && end != null) {
                    this.startDate = start;
                    this.endDate = end;
                }
            }
        }

        // state
        this.oldStartDate = this.startDate.clone();
        this.oldEndDate = this.endDate.clone();

        this.leftCalendar = {
            month: moment([this.startDate.year(), this.startDate.month(), 1, this.startDate.hour(), this.startDate.minute()]),
            calendar: []
        };

        this.rightCalendar = {
            month: moment([this.endDate.year(), this.endDate.month(), 1, this.endDate.hour(), this.endDate.minute()]),
            calendar: []
        };

        // event listeners
        this.container.on('mousedown', $.proxy(this.mousedown, this));

        this.container.find('.calendar')
            .on('click', '.prev', $.proxy(this.clickPrev, this))
            .on('click', '.next', $.proxy(this.clickNext, this))
            .on('click', 'td.available', $.proxy(this.clickDate, this))
            .on('mouseenter', 'td.available', $.proxy(this.enterDate, this))
            .on('mouseleave', 'td.available', $.proxy(this.updateFormInputs, this))
            .on('change', 'select.yearselect', $.proxy(this.updateMonthYear, this))
            .on('change', 'select.monthselect', $.proxy(this.updateMonthYear, this))
            .on('change', 'select.hourselect,select.minuteselect,select.ampmselect', $.proxy(this.updateTime, this));

        this.container.find('.ranges')
            .on('click', 'button.applyBtn', $.proxy(this.clickApply, this))
            .on('click', 'button.cancelBtn', $.proxy(this.clickCancel, this))
            .on('click', '.daterangepicker_start_input,.daterangepicker_end_input', $.proxy(this.showCalendars, this))
            .on('click', 'li', $.proxy(this.clickRange, this))
            .on('mouseenter', 'li', $.proxy(this.enterRange, this))
            .on('mouseleave', 'li', $.proxy(this.updateFormInputs, this));

        this.element.on('keyup', $.proxy(this.updateFromControl, this));

        this.updateView();
        this.updateCalendars();

    };

    DateRangePicker.prototype = {

        constructor: DateRangePicker,

        mousedown: function (e) {
            e.stopPropagation();
        },

        updateView: function () {
            this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
            this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
            this.updateFormInputs();
        },

        updateFormInputs: function () {
            this.container.find('input[name=daterangepicker_start]').val(this.startDate.format(this.format));
            this.container.find('input[name=daterangepicker_end]').val(this.endDate.format(this.format));

            if (this.startDate.isBefore(this.endDate)) {
                this.container.find('button.applyBtn').removeAttr('disabled');
            } else {
                this.container.find('button.applyBtn').attr('disabled', 'disabled');
            }
        },

        updateFromControl: function () {
            if (!this.element.is('input')) return;
            if (!this.element.val().length) return;

            var dateString = this.element.val().split(this.separator);
            var start = moment(dateString[0], this.format);
            var end = moment(dateString[1], this.format);

            if (start == null || end == null) return;
            if (end.isBefore(start)) return;

            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();

            this.startDate = start;
            this.endDate = end;

            if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate))
                this.notify();

            this.updateCalendars();
        },

        notify: function () {
            this.updateView();
            this.cb(this.startDate, this.endDate);
        },

        move: function () {
            var parentOffset = {
                top: this.parentEl.offset().top - (this.parentEl.is('body') ? 0 : this.parentEl.scrollTop()),
                left: this.parentEl.offset().left - (this.parentEl.is('body') ? 0 : this.parentEl.scrollLeft())
            };
            if (this.opens == 'left') {
                this.container.css({
                    top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                    right: $(window).width() - this.element.offset().left - this.element.outerWidth() - parentOffset.left,
                    left: 'auto'
                });
                if (this.container.offset().left < 0) {
                    this.container.css({
                        right: 'auto',
                        left: 9
                    });
                }
            } else {
                this.container.css({
                    top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                    left: this.element.offset().left - parentOffset.left,
                    right: 'auto'
                });
                if (this.container.offset().left + this.container.outerWidth() > $(window).width()) {
                    this.container.css({
                        left: 'auto',
                        right: 0
                    });
                }
            }
        },

        show: function (e) {
            this.container.show();
            this.move();

            if (e) {
                e.stopPropagation();
                e.preventDefault();
            }

            $(document).on('mousedown', $.proxy(this.hide, this));
            this.element.trigger('shown', {target: e.target, picker: this});
        },

        hide: function (e) {
            this.container.hide();

            if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate))
                this.notify();

            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();

            $(document).off('mousedown', this.hide);
            this.element.trigger('hidden', { picker: this });
        },

        enterRange: function (e) {
            var label = e.target.innerHTML;
            if (label == this.locale.customRangeLabel) {
                this.updateView();
            } else {
                var dates = this.ranges[label];
                this.container.find('input[name=daterangepicker_start]').val(dates[0].format(this.format));
                this.container.find('input[name=daterangepicker_end]').val(dates[1].format(this.format));
            }
        },

        showCalendars: function() {
            this.container.find('.calendar').show();
            this.move();
        },

        updateInputText: function() {
            if (this.element.is('input'))
                this.element.val(this.startDate.format(this.format) + this.separator + this.endDate.format(this.format));
        },

        clickRange: function (e) {
            var label = e.target.innerHTML;
            if (label == this.locale.customRangeLabel) {
                this.showCalendars();
            } else {
                var dates = this.ranges[label];

                this.startDate = dates[0];
                this.endDate = dates[1];

                if (!this.timePicker) {
                    this.startDate.startOf('day');
                    this.endDate.startOf('day');
                }

                this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year()).hour(this.startDate.hour()).minute(this.startDate.minute());
                this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year()).hour(this.endDate.hour()).minute(this.endDate.minute());
                this.updateCalendars();

                this.updateInputText();

                this.container.find('.calendar').hide();
                this.hide();
            }
        },

        clickPrev: function (e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.subtract('month', 1);
            } else {
                this.rightCalendar.month.subtract('month', 1);
            }
            this.updateCalendars();
        },

        clickNext: function (e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.add('month', 1);
            } else {
                this.rightCalendar.month.add('month', 1);
            }
            this.updateCalendars();
        },

        enterDate: function (e) {

            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');

            if (cal.hasClass('left')) {
                this.container.find('input[name=daterangepicker_start]').val(this.leftCalendar.calendar[row][col].format(this.format));
            } else {
                this.container.find('input[name=daterangepicker_end]').val(this.rightCalendar.calendar[row][col].format(this.format));
            }

        },

        clickDate: function (e) {
            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');

            if (cal.hasClass('left')) {
                var startDate = this.leftCalendar.calendar[row][col];
                var endDate = this.endDate;
                if (typeof this.dateLimit == 'object') {
                    var maxDate = moment(startDate).add(this.dateLimit).startOf('day');
                    if (endDate.isAfter(maxDate)) {
                        endDate = maxDate;
                    }
                }
            } else {
                var startDate = this.startDate;
                var endDate = this.rightCalendar.calendar[row][col];
                if (typeof this.dateLimit == 'object') {
                    var minDate = moment(endDate).subtract(this.dateLimit).startOf('day');
                    if (startDate.isBefore(minDate)) {
                        startDate = minDate;
                    }
                }
            }

            cal.find('td').removeClass('active');

            if (startDate.isSame(endDate) || startDate.isBefore(endDate)) {
                $(e.target).addClass('active');
                this.startDate = startDate;
                this.endDate = endDate;
            } else if (startDate.isAfter(endDate)) {
                $(e.target).addClass('active');
                this.startDate = startDate;
                this.endDate = moment(startDate).add('day', 1).startOf('day');
            }

            this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
            this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
            this.updateCalendars();
        },

        clickApply: function (e) {
            this.updateInputText();
            this.hide();
        },

        clickCancel: function (e) {
            this.startDate = this.oldStartDate;
            this.endDate = this.oldEndDate;
            this.updateView();
            this.updateCalendars();
            this.hide();
        },

        updateMonthYear: function (e) {

            var isLeft = $(e.target).closest('.calendar').hasClass('left');
            var cal = this.container.find('.calendar.left');
            if (!isLeft)
                cal = this.container.find('.calendar.right');

            // Month must be Number for new moment versions
            var month = parseInt(cal.find('.monthselect').val(), 10);
            var year = cal.find('.yearselect').val();

            if (isLeft) {
                this.leftCalendar.month.month(month).year(year);
            } else {
                this.rightCalendar.month.month(month).year(year);
            }

            this.updateCalendars();

        },

        updateTime: function(e) {

            var isLeft = $(e.target).closest('.calendar').hasClass('left');
            var cal = this.container.find('.calendar.left');
            if (!isLeft)
                cal = this.container.find('.calendar.right');

            var hour = parseInt(cal.find('.hourselect').val());
            var minute = parseInt(cal.find('.minuteselect').val());

            if (this.timePicker12Hour) {
                var ampm = cal.find('.ampmselect').val();
                if (ampm == 'PM' && hour < 12)
                    hour += 12;
                if (ampm == 'AM' && hour == 12)
                    hour = 0;
            }

            if (isLeft) {
                var start = this.startDate.clone();
                start.hour(hour);
                start.minute(minute);
                this.startDate = start;
                this.leftCalendar.month.hour(hour).minute(minute);
            } else {
                var end = this.endDate.clone();
                end.hour(hour);
                end.minute(minute);
                this.endDate = end;
                this.rightCalendar.month.hour(hour).minute(minute);
            }

            this.updateCalendars();
            this.updateFormInputs();

        },

        updateCalendars: function () {
            this.leftCalendar.calendar = this.buildCalendar(this.leftCalendar.month.month(), this.leftCalendar.month.year(), this.leftCalendar.month.hour(), this.leftCalendar.month.minute(), 'left');
            this.rightCalendar.calendar = this.buildCalendar(this.rightCalendar.month.month(), this.rightCalendar.month.year(), this.rightCalendar.month.hour(), this.rightCalendar.month.minute(), 'right');
            this.container.find('.calendar.left').html(this.renderCalendar(this.leftCalendar.calendar, this.startDate, this.minDate, this.maxDate));
            this.container.find('.calendar.right').html(this.renderCalendar(this.rightCalendar.calendar, this.endDate, this.startDate, this.maxDate));

            this.container.find('.ranges li').removeClass('active');
            var customRange = true;
            var i = 0;
            for (var range in this.ranges) {
                if (this.timePicker) {
                    if (this.startDate.isSame(this.ranges[range][0]) && this.endDate.isSame(this.ranges[range][1])) {
                        customRange = false;
                        this.container.find('.ranges li:eq(' + i + ')').addClass('active');
                    }
                } else {
                    // ignore times when comparing dates if time picker is not
                    // enabled
                    if (this.startDate.format('YYYY-MM-DD') == this.ranges[range][0].format('YYYY-MM-DD') && this.endDate.format('YYYY-MM-DD') == this.ranges[range][1].format('YYYY-MM-DD')) {
                        customRange = false;
                        this.container.find('.ranges li:eq(' + i + ')').addClass('active');
                    }
                }
                i++;
            }
            if (customRange)
                this.container.find('.ranges li:last').addClass('active');
        },

        buildCalendar: function (month, year, hour, minute, side) {

            var firstDay = moment([year, month, 1]);
            var lastMonth = moment(firstDay).subtract('month', 1).month();
            var lastYear = moment(firstDay).subtract('month', 1).year();

            var daysInLastMonth = moment([lastYear, lastMonth]).daysInMonth();

            var dayOfWeek = firstDay.day();

            // initialize a 6 rows x 7 columns array for the calendar
            var calendar = [];
            for (var i = 0; i < 6; i++) {
                calendar[i] = [];
            }

            // populate the calendar with date objects
            var startDay = daysInLastMonth - dayOfWeek + this.locale.firstDay + 1;
            if (startDay > daysInLastMonth)
                startDay -= 7;

            if (dayOfWeek == this.locale.firstDay)
                startDay = daysInLastMonth - 6;

            var curDate = moment([lastYear, lastMonth, startDay, 12, minute]);
            for (var i = 0, col = 0, row = 0; i < 42; i++, col++, curDate = moment(curDate).add('hour', 24)) {
                if (i > 0 && col % 7 == 0) {
                    col = 0;
                    row++;
                }
                calendar[row][col] = curDate.clone().hour(hour);
                curDate.hour(12);
            }

            return calendar;

        },

        renderDropdowns: function (selected, minDate, maxDate) {
            var currentMonth = selected.month();
            var monthHtml = '<select class="monthselect">';
            var inMinYear = false;
            var inMaxYear = false;

            for (var m = 0; m < 12; m++) {
                if ((!inMinYear || m >= minDate.month()) && (!inMaxYear || m <= maxDate.month())) {
                    monthHtml += "<option value='" + m + "'" +
                        (m === currentMonth ? " selected='selected'" : "") +
                        ">" + this.locale.monthNames[m] + "</option>";
                }
            }
            monthHtml += "</select>";

            var currentYear = selected.year();
            var maxYear = (maxDate && maxDate.year()) || (currentYear + 5);
            var minYear = (minDate && minDate.year()) || (currentYear - 50);
            var yearHtml = '<select class="yearselect">';

            for (var y = minYear; y <= maxYear; y++) {
                yearHtml += '<option value="' + y + '"' +
                    (y === currentYear ? ' selected="selected"' : '') +
                    '>' + y + '</option>';
            }

            yearHtml += '</select>';

            return monthHtml + yearHtml;
        },

        renderCalendar: function (calendar, selected, minDate, maxDate) {

            var html = '<div class="calendar-date">';
            html += '<table class="table-condensed">';
            html += '<thead>';
            html += '<tr>';

            // add empty cell for week number
            if (this.showWeekNumbers)
                html += '<th></th>';

            if (!minDate || minDate.isBefore(calendar[1][1])) {
                html += '<th class="prev available"><i class="icon-arrow-left glyphicon glyphicon-arrow-left"></i></th>';
            } else {
                html += '<th></th>';
            }

            var dateHtml = this.locale.monthNames[calendar[1][1].month()] + calendar[1][1].format(" YYYY");

            if (this.showDropdowns) {
                dateHtml = this.renderDropdowns(calendar[1][1], minDate, maxDate);
            }

            html += '<th colspan="5" style="width: auto">' + dateHtml + '</th>';
            if (!maxDate || maxDate.isAfter(calendar[1][1])) {
                html += '<th class="next available"><i class="icon-arrow-right glyphicon glyphicon-arrow-right"></i></th>';
            } else {
                html += '<th></th>';
            }

            html += '</tr>';
            html += '<tr>';

            // add week number label
            if (this.showWeekNumbers)
                html += '<th class="week">' + this.locale.weekLabel + '</th>';

            $.each(this.locale.daysOfWeek, function (index, dayOfWeek) {
                html += '<th>' + dayOfWeek + '</th>';
            });

            html += '</tr>';
            html += '</thead>';
            html += '<tbody>';

            for (var row = 0; row < 6; row++) {
                html += '<tr>';

                // add week number
                if (this.showWeekNumbers)
                    html += '<td class="week">' + calendar[row][0].week() + '</td>';

                for (var col = 0; col < 7; col++) {
                    var cname = 'available ';
                    cname += (calendar[row][col].month() == calendar[1][1].month()) ? '' : 'off';

                    if ((minDate && calendar[row][col].isBefore(minDate)) || (maxDate && calendar[row][col].isAfter(maxDate))) {
                        cname = ' off disabled ';
                    } else if (calendar[row][col].format('YYYY-MM-DD') == selected.format('YYYY-MM-DD')) {
                        cname += ' active ';
                        if (calendar[row][col].format('YYYY-MM-DD') == this.startDate.format('YYYY-MM-DD')) {
                            cname += ' start-date ';
                        }
                        if (calendar[row][col].format('YYYY-MM-DD') == this.endDate.format('YYYY-MM-DD')) {
                            cname += ' end-date ';
                        }
                    } else if (calendar[row][col] >= this.startDate && calendar[row][col] <= this.endDate) {
                        cname += ' in-range ';
                        if (calendar[row][col].isSame(this.startDate)) { cname += ' start-date '; }
                        if (calendar[row][col].isSame(this.endDate)) { cname += ' end-date '; }
                    }

                    var title = 'r' + row + 'c' + col;
                    html += '<td class="' + cname.replace(/\s+/g, ' ').replace(/^\s?(.*?)\s?$/, '$1') + '" data-title="' + title + '">' + calendar[row][col].date() + '</td>';
                }
                html += '</tr>';
            }

            html += '</tbody>';
            html += '</table>';
            html += '</div>';

            if (this.timePicker) {

                html += '<div class="calendar-time">';
                html += '<select class="hourselect">';
                var start = 0;
                var end = 23;
                var selected_hour = selected.hour();
                if (this.timePicker12Hour) {
                    start = 1;
                    end = 12;
                    if (selected_hour >= 12)
                        selected_hour -= 12;
                    if (selected_hour == 0)
                        selected_hour = 12;
                }

                for (var i = start; i <= end; i++) {
                    if (i == selected_hour) {
                        html += '<option value="' + i + '" selected="selected">' + i + '</option>';
                    } else {
                        html += '<option value="' + i + '">' + i + '</option>';
                    }
                }

                html += '</select> : ';

                html += '<select class="minuteselect">';

                for (var i = 0; i < 60; i += this.timePickerIncrement) {
                    var num = i;
                    if (num < 10)
                        num = '0' + num;
                    if (i == selected.minute()) {
                        html += '<option value="' + i + '" selected="selected">' + num + '</option>';
                    } else {
                        html += '<option value="' + i + '">' + num + '</option>';
                    }
                }

                html += '</select> ';

                if (this.timePicker12Hour) {
                    html += '<select class="ampmselect">';
                    if (selected.hour() >= 12) {
                        html += '<option value="AM">AM</option><option value="PM" selected="selected">PM</option>';
                    } else {
                        html += '<option value="AM" selected="selected">AM</option><option value="PM">PM</option>';
                    }
                    html += '</select>';
                }

                html += '</div>';

            }

            return html;

        }

    };

    $.fn.daterangepicker = function (options, cb) {
        this.each(function () {
            var el = $(this);
            if (!el.data('daterangepicker'))
                el.data('daterangepicker', new DateRangePicker(el, options, cb));
        });
        return this;
    };

}(window.jQuery);

jQuery(function($){
    $.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '&#x3C;上月',
        nextText: '下月&#x3E;',
        currentText: '今天',
        monthNames: ['一月','二月','三月','四月','五月','六月',
            '七月','八月','九月','十月','十一月','十二月'],
        monthNamesShort: ['一月','二月','三月','四月','五月','六月',
            '七月','八月','九月','十月','十一月','十二月'],
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
        dayNamesMin: ['日','一','二','三','四','五','六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '年'};
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});