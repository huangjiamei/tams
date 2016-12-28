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
        "sThousands": "",
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
        "sPaginationType":"full_numbers"
        //"sDom":"&lt;'top't&gt;&lt;'bottom'ilp&gt;" //自定义布局sdom，暂时没有生效
    } );
})();

//取消离开页面时的询问弹框
(function(){
    window.onbeforeunload = function(){
        return;
    }
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
    //jQuery("#navDialog").show();
    jQuery(".navigationDialog .nav>ul>:first-child>a").tab('show');
    //去掉dialog本身的header
    jQuery(".navigationDialog>.modal-content>.modal-header").remove();

    jQuery("#navDialog").removeClass("fade");
    //添加导航栏点击切换事件
    jQuery(".navigationDialog .nav a").click(function(e){
        //e.preventDefault();
        jQuery(this).tab('show');
    });

    jQuery(".navigationDialog .close").click(function(a) {
        a.stopPropagation();
        jQuery(".navigationDialog .nav .active").removeClass("active");
        jQuery(".navigationDialog .tab-content .active").removeClass("active");
    });
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
    topleft.innerHTML = "<div class=\"out\"> <b>新状态</b>  <em>当前状态</em> </div>";

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
// function getPieChart(chartId,title,data) {
//     data = JSON.parse(data);
//
//     // 尝试过将下面这段setOptions代码提取为initHigicharts()但是没有效果
//     Highcharts.setOptions({
//         lang: {
//             printChart:"打印图表",
//             downloadJPEG: "下载JPEG 图片" ,
//             downloadPDF: "下载PDF文档"  ,
//             downloadPNG: "下载PNG 图片"  ,
//             downloadSVG: "下载SVG 矢量图" ,
//             exportButtonTitle: "导出图片"
//         }
//     });
//
//     jQuery('#' + chartId).highcharts({
//         credits:{
//           enabled:false     // 去除highcharts的水印
//         },
//         title: {
//             text: title
//         },
//         tooltip: {
//             // 除了此项占比，还需要加一个此项具体数值
//             pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b> '
//         },
//         plotOptions: {
//             pie: {
//                 allowPointSelect: true, // 一块饼的提取
//                 cursor: 'pointer', // 鼠标指针能否点击
//                 dataLabels: {
//                     enabled: false
//                 },
//                 showInLegend: true
//             }
//         },
//         series: [{
//             type: 'pie',
//             name: ' ',
//             data: data
//         }]
//     });
// }

/**
 * barChart输入的data格式为(待定)
 * @param chartId
 * @param title
 * @param dataTransferId
 */
// function getBarChart(chartId,title,data) {
//     //data = eval(data);
//     data = JSON.parse(data);
//
//     // data = {
//     //
//     //     x: [
//     //
//     //         '高等数学',
//     //
//     //         '概率论',
//     //
//     //         '应用数学',
//     //
//     //         '离散数学',
//     //
//     //         '统计学',
//     //
//     //         '计算数学',
//     //
//     //         '运筹学与控制论',
//     //
//     //         '数学分析'],
//     //
//     //     y: [1200, 1000, 600, 900, 800, 500, 500, 900]
//     //
//     // }
//     // 尝试过将下面这段setOptions代码提取为initHigicharts()但是没有效果
//     Highcharts.setOptions({
//         lang: {
//             printChart:"打印图表",
//             downloadJPEG: "下载JPEG 图片" ,
//             downloadPDF: "下载PDF文档"  ,
//             downloadPNG: "下载PNG 图片"  ,
//             downloadSVG: "下载SVG 矢量图" ,
//             exportButtonTitle: "导出图片"
//         }
//     });
//
//     jQuery('#' + chartId).highcharts({
//         legend:{
//             enabled:false
//         },
//         credits:{
//             enabled:false   // 去除highcharts的水印
//         },
//         chart: {
//             type: 'column' // 竖柱图
//         },
//         title: {
//             text: title
//         },
//         xAxis: {
//             // objlist.name
//             /*
//             categories: [
//                 '高等数学',
//                 '概率论',
//                 '应用数学',
//                 '离散数学',
//                 '统计学',
//                 '计算数学',
//                 '运筹学与控制论',
//                 '数学分析']
//              */
//             title:{
//                 text:"课程"
//             },
//             categories : data.x
//         },
//         yAxis: [
//             {
//                 title:{
//                   text:"经费"
//                 },
//                 allowDecimals: false, // 坐标轴刻度不为小数
//
//                 labels: {
//                     format: '{value} 元',
//                     // format : '{series.y}'
//                 }
//             }
//
//         ],
//         series: [{
//             name: '经费',
//             //color: '#ff4d4d',
//             color: '#66a3ff',
//             // objlist.y
//             //data: [1200, 1000, 600, 900, 800, 500, 500, 900],
//             data: data.y,
//             tooltip: {
//                 ySuffix: '元',
//                 pointFormat:'<b>{point.y}</b> ',
//                 headerFormat:'<span style="font-size: 10px">{point.key}: </span>',
//             },
//             pointPadding: 0.15,
//             pointPlacement: -0.03
//         }/*, {
//             name: '助教优秀率',
//             color: '#66a3ff',
//             data: [60, 90, 85, 70, 80, 85, 80, 75],
//             tooltip: {ySuffix: '%'},
//             yAxis: 1, // 双y轴的关键
//             pointPadding: 0.15,
//             pointPlacement: 0.03
//         }*/],
//         tooltip: {shared: true}
//     });
//
// }


var filterHintCache = {

    "searchCourseNm": [
        ],
    "searchCourseNmb": [
       ],
    "searchCourseManager": [
       ],
    "searchCourseInsCode": [
       ],

   //课程页面搜索框
    "condCourseName": [
       ],
    "condCourseCode": [
        ],
    "condClassNumber": [
       ],
    "condInstructorName": [
       ],
    "courseWorkTime": [
       ],
    "courseAppFunds":[],
    "taNumber":[],

    //助教课表搜索框
    "taAssitantName": [
    ],
    "taAssitantIDNumber":[
    ],
    "taCourseName":[],
    "taCourseCode":[
      ],
    "taClassNumber": [
       ],
    "taTeacherName": [
       ],
    "taScore":[ ],

    //用户过滤框
    "userRoleName":[
    ],
    "userRoleNumber":[
    ],
    "userRoleIDNumber":[
    ],

    //批次经费——学校历史经费
    "sPreFunds":[
    ],
    "sApplyFunds":[
    ],
    "sApprovalFunds":[
    ],
    "sAddingFunds":[
    ],
    "sRewardFunds":[
    ],
    "sTrafficFunds":[
    ],
    "sTotalFunds":[
    ],

    //学院经费——学院经费
    "departmentPreFunds":[
    ],
    "departmentApplyFunds":[
    ],
    "departmentApprovalFunds":[
    ],
    "departmentAddingFunds":[
    ],
    "departmentRewardFunds":[
    ],
    "departmentTrafficFunds":[
    ],
    "departmentTotalFunds":[
    ],

    //学院经费——学院历史经费
    "dPreFunds":[
    ],
    "dApplyFunds":[
    ],
    "dApprovalFunds":[
    ],
    "dAddingFunds":[
    ],
    "dRewardFunds":[
    ],
    "dTrafficFunds":[
    ],
    "dTotalFunds":[
    ],

    //课程经费
    "cName": [
       ],
    "cCode": [
       ],
    "cNbr": [
        ],
    "cTeacher": [
        ],
    "cApplyFunds":[
    ],
    "cActualFunds":[
    ],
    "cPhdFunds":[
    ],
    "cBonus":[
    ],
    "cTrafficFunds":[
    ],
    "cTotalFunds":[
    ],

    //助教经费
    "tName": [
    ],
    "tNumber":[
    ],
    "tType": [
    ],
    "tCourseName": [
       ],
    "tCourseCode": [
        ],
    "tAssignedFunds":[
    ],
    "tPhdFunds":[
    ],
    "tTrafficFunds":[
    ],
    "tBonus":[
    ],
    "tTotalFunds":[
    ],

    //明细经费
    "detailsName": [
    ],
    "detailsNumber":[
    ],
    "detailsBank": [
    ],
    "detailsBankNumber":[
    ],
    "detailsIDCard": [
    ],
    "detailsCourseName": [
    ],
    "detailsCourseCode": [
        ],
    "month1":[
    ],
    "month2":[
    ],
    "month3":[
    ],
    "month4":[
    ],
    "month5":[
    ],
    "month6":[
    ],
    "month7":[
    ],
    "month8":[
    ],
    "month9":[
    ],
    "month10":[
    ],
    "month11":[
    ],
    "month12":[
    ],
    "detailsTotalFunds":[
    ]
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
    jQuery('#'+id).html('<h3> <i class="'+icon+'"></i> '+bigTitle+' |<small> '+smallTitle+'</small></h3>');
}


function initRightBtnMenu(targetid) {
    //alert(jQuery(".table tbody tr").size())
    jQuery("#"+targetid+" tbody").contextPopup({
        title: '',
        items: [
            // {
            //     label:'添加',
            //     icon:'icon-plus',
            //     action:function(e) {
            //         var id = e.target.id;
            //         var patt = new RegExp(".*line[0-9]+.*");
            //         if (typeof(id)=="undefined"||id==""||!patt.test(id))
            //             id = jQuery(e.target).children("*[id*='line']")[0].id;
            //         var index=id.match("line[0-9]+")[0].match('[0-9]+');
            //         alert("添加"+id+', index='+index);
            //
            //     }
            // },
            // {label:'查看',     icon:'icon-search',              action:function() { alert('clicked 3') } },
            //null, // divider
            // {
            //     label:'选中',
            //     icon:'icon-signup',
            //     action:function(e) {
            //         var id = e.target.id;
            //         var patt = new RegExp(".*line[0-9]+.*");
            //         if (typeof(id)=="undefined"||id==""||!patt.test(id))
            //             id = jQuery(e.target).children("*[id*='line']")[0].id;
            //         var index=id.match("line[0-9]+")[0].match('[0-9]+');
            //         jQuery('#ClassListPageTable tbody>tr:eq('+index+')').find(":checkbox").attr("checked",true);
            //     }
            // },
            // null, // divider
            {
                label:'查看',
                icon:'icon-eye-open',
                action:function(e) {
                    var id = e.target.id;
                    var patt = new RegExp(".*line[0-9]+.*");
                    if (typeof(id)=="undefined"||id==""||!patt.test(id))
                        id = jQuery(e.target).children("*[id*='line']")[0].id;
                    var index=id.match("line[0-9]+")[0].match('[0-9]+');
                    document.getElementById('indexClassList_control').value=index;
                    jQuery("#classDetailPageId").click();
                }
            },
            // null,
            //  {
            //      label:'审批',
            //      icon:'icon-zoom-in',
            //      action:function(e) {
            //          var id = e.target.id;
            //          var patt = new RegExp(".*line[0-9]+.*");
            //          if (typeof(id)=="undefined"||id==""||!patt.test(id))
            //              id = jQuery(e.target).children("*[id*='line']")[0].id;
            //          var index=parseInt(id.match("line[0-9]+")[0].match('[0-9]+'));
            //          alert("查看"+id+', index='+index);
            //           jQuery("#ClassListPageTable").on('click','tbody>tr:eq('+index+')',function () {
            //              jQuery(this).find(":checkbox").attr("checked",true);
            //          });
            //            //jQuery('#ClassListPageTable').on('click','tbody>tr:eq('+index+')>td:eq(1)').find(":checkbox").attr("checked",true);
            //          jQuery("#approveButtonId").click();
            //      }
            //  },
            null,
            {
                label:'全选',
                icon:'icon-check',
                action:function(e) {
                    jQuery("#checkedClassListAllId").find(":checkbox").attr("checked",true);
                    jQuery("#checkedClassListAllId").click();
                }
            },
            // {
            //     label:'编辑',
            //     icon:'icon-pencil2',
            //     action:function(e) {
            //         var id = e.target.id;
            //         var patt = new RegExp(".*line[0-9]+.*");
            //         if (typeof(id)=="undefined"||id==""||!patt.test(id))
            //             id = jQuery(e.target).children("*[id*='line']")[0].id;
            //         var index=id.match("line[0-9]+")[0].match('[0-9]+');
            //         alert("编辑"+id+', index='+index);
            //     }
            // },
            // null, // divider
            // {
            //     label:'删除',
            //     icon:'icon-remove2',
            //     action:function(e) {
            //         var id = e.target.id;
            //         var patt = new RegExp(".*line[0-9]+.*");
            //         if (typeof(id)=="undefined"||id==""||!patt.test(id))
            //             id = jQuery(e.target).children("*[id*='line']")[0].id;
            //         var index=id.match("line[0-9]+")[0].match('[0-9]+');
            //         alert("删除"+id+', index='+index);
            //     }
            // },
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
 * 让dialoay:none的元素显示出来
 * 应用于taManagementPage的‘添加助教’功能
 * @param id
 */
function showElement(id) {
    var element=jQuery('#'+id);
    element.css('display','block');
}

/**
 * 经费管理页面，
 * 实现保存草稿的目的onChange时调用这个方法去点击隐藏的updateAction
 * 同时通过input中转站将一个用于区分当前修改的到底是哪个tab的flag传到后台。
 * department：学院tab
 * school：学校tab
 * class：课程(教学班？)tab
 * (前台可以通过@{#index}获取index，但是不知道怎么传给后台，如果能够将index直接传给后台就不需要这个点击action来中转的方式)
 * @param element
 */
// function inputFieldOnChange(element,transferId,transferData) {
//     var updateAction=jQuery(element).parent().parent().find("input[data-ajaxreturntype='update-component']")[0];
//     jQuery('#' + transferId).val(transferData); //
//     updateAction.click();
// }

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
    else{
        nav.css("margin-top", 0);
        if (!jQuery('.sidebar-collapse').parent().hasClass('sidebar-collapsed')) {
            jQuery('.sidebar-collapse').click();
        }
    }

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
    // jQuery("#RequestTaPage .nav-tabs").children("li:eq(0)").find("a").html('<h5><i class="icon-book"></i><big>课程</big></h5>');
    // jQuery("#RequestTaPage .nav-tabs").children("li:eq(1)").find("a").html('<h5><i class="icon-arrow-up"></i><big>反馈</big></h5>');
    // jQuery("#tabGroupOne .uif-sectionHeader").html('<h4><i class="icon-book"></i><big>课程信息</big></h4>');
    // jQuery("#tabGroupTwo .uif-sectionHeader").html('<h4><i class="icon-arrow-up"></i><big>反馈信息</big></h4>');
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
  日历
 */
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

/*
添加联系人时，回车查询
 */
function clickButton(id,buttonId){
    jQuery("#"+id).on(
        {keydown: function(e){
            var key = e.which;
            if(key == 13){
                e.preventDefault();
                jQuery("#"+buttonId).click();
            }
        }
        });
}

/*
table默认每页显示5条记录
 */
function searchTaApplicantListALengthMenu(){
    // jQuery("#searchTaApplicantList table").dataTable( {
    //     "bRetrieve": true,
    //     "bPaginate":true,
    //     //"bJQueryUI":true,
    //     "iDisplayLength":5,//默认每页显示几条数据
    //     "aLengthMenu": [[5,10,20, 50], ["5","10", "20", "50"]]  //设置每页显示记录的下拉菜单
    // });
}


//清除datatable的页脚页数缓存
function deleteCookie(){
    localStorage.clear();
}


//显示菜单dialog
function showDialogMenu(){
    showDialog('navDialog');
    if(jQuery("#navDialog").css("display")=="none"){
        jQuery("#navDialog").show();
    }else{
        jQuery("#dismissNavDialog").click();
    }

    //header部分切换时进行隐藏
    jQuery(".dropdown-toggle").click(function() {
        jQuery("#dismissNavDialog").click();
    });
    jQuery("#sessionTermInfo").click(function() {
        jQuery("#dismissNavDialog").click();
    });
}

//显示学期更改dialog
function showDialogTerm(){
    showDialog('sessionTermFinderDialog');
    if(jQuery("#sessionTermFinderDialog").css("display")=="none"){
        jQuery("#sessionTermFinderDialog").show();
    }else{
        jQuery("#dissmissSessionTermDialog").click();
    }

    //header部分切换时进行隐藏
    jQuery(".dropdown-toggle").click(function() {
        jQuery("#sessionTermFinderDialog").hide();
    });
    jQuery("#menuHeaderItem").click(function() {
        jQuery("#sessionTermFinderDialog").hide();
    });
}

//为当table中前学期设置背景色，该行字体大一点
function setCurrentColor(){

    var currenTr=jQuery("#TermManageTable table.uif-tableCollectionLayout>tbody > tr");
    var currenSession=jQuery("#sessionTermMessage").text();

    for (var i = 0; i < currenTr.length; i++) {
        var currenSpan=jQuery("#TermManageTable table.uif-tableCollectionLayout>tbody").children("tr:eq("+i+")").children("td:eq(0)").children("div:eq(0)").children("span:eq(0)");
        if(currenSpan.text()==currenSession){//此处是用列表的批次名称和当前学期相同，显示背景色，
            currenTr[i].style.background = "#d0e9c6";
            currenTr[i].style.fontSize="16px";
        }
    }

}

//table的隔行变色
jQuery(document).ready(function(){
    jQuery("table.uif-tableCollectionLayout tbody tr:odd").css("background-color","#f5f5f5");
    jQuery("table.uif-tableCollectionLayout tbody tr:even").css("background-color","white");
});

//刷新整个页面
function refreshPage(){
    location.reload();
}

//Header部分Logo跳至重庆大学页面
 function onClickHerf(){
     window.location="http://www.cqu.edu.cn";
}

//checkbox全选
function checkedAll(id) {
        jQuery("#"+id).click();
}

//助教页面进入详细信息页面，因为课程页面有详细按钮，所以两个页面单独写
function getTaDetailPage(){

    var listLength=jQuery('#taListTable tbody>tr').length;
    for(var i=0;i<listLength;i++){
        jQuery('#taListTable').on('click','tbody>tr:eq('+i+')>td:eq(0)>div>input',function (e) { //checkbox为true时添加样式，为false时，去除样式
            if(jQuery(this).parent().parent().parent().find(":checkbox").attr("checked")!="checked"){
                jQuery(this).parent().parent().parent().find("td").removeClass("selected")
                    .end().find(":checkbox").attr("checked",false);

            }else{
                jQuery(this).parent().parent().parent().find("td").addClass("selected")
                    .end().find(":checkbox").attr("checked",true);

            }
        });
        if(jQuery('#checkedTaListAllId').css('display')!="none"){
            jQuery('#taListTable').on('click','tbody>tr:eq('+i+')>td:not(:first)',function (e) {
                var targetid=taListTable;
                var id = e.target.id;
                var patt = new RegExp(".*line[0-9]+.*");
                if (typeof(id)=="undefined"||id==""||!patt.test(id))
                    id = jQuery(e.target).children("*[id*='line']")[0].id;
                var index=id.match("line[0-9]+")[0].match('[0-9]+');
                document.getElementById('indexTaList_control').value=index;
                jQuery("#taDetailPageId").click();
            });
        }
    }
}
//课程页面点击整行进入详细页面
function getClassDetailPage(){
    var listLength=jQuery('#ClassListPageTable tbody>tr').length;

    for(var i=0;i<listLength;i++){

        jQuery('#ClassListPageTable').on('click','tbody>tr:eq('+i+')>td:eq(1)>div>input',function (e) { //checkbox为true时添加样式，为false时，去除样式
            if(jQuery(this).parent().parent().parent().find(":checkbox").attr("checked")!="checked"){
                jQuery(this).parent().parent().parent().find("td").removeClass("selected")
                    .end().find(":checkbox").attr("checked",false);

            }else{
                jQuery(this).parent().parent().parent().find("td").addClass("selected")
                    .end().find(":checkbox").attr("checked",true);

            }
        });
        if(jQuery('#checkedClassListAllId').css('display')!="none") {
            jQuery('#ClassListPageTable').on('click', 'tbody>tr:eq(' + i + ')>td:not(:eq(0),:eq(1))', function (e) {
                var targetid = ClassListPageTable;
                var id = e.target.id;
                var patt = new RegExp(".*line[0-9]+.*");
                if (typeof(id) == "undefined" || id == "" || !patt.test(id))
                    id = jQuery(e.target).children("*[id*='line']")[0].id;
                var index = id.match("line[0-9]+")[0].match('[0-9]+');
                document.getElementById('indexClassList_control').value = index;
                jQuery("#classDetailPageId").click();
            });
        }
    }
}
//统一的点击整行变色
function setBgColor(id){
    var tableLength=jQuery("#"+id).find('tbody').find('tr').length;
    for(var i=0;i<tableLength;i++) {
        jQuery("#" + id).on('click', 'tbody>tr:eq(' + i + ')>td:eq(0)>div>input', function (e) { //checkbox为true时添加样式，为false时，去除样式
            if (jQuery(this).parent().parent().parent().find(":checkbox").attr("checked") != "checked") {
                jQuery(this).parent().parent().parent().find("td").removeClass("selected")
                    .end().find(":checkbox").attr("checked", false);
            } else {
                jQuery(this).parent().parent().parent().find("td").addClass("selected")
                    .end().find(":checkbox").attr("checked", true);
            }
        });
    }
}

//table上的鼠标滑过显示其他样式
function addPointer(id){
    jQuery("#"+id).on('mouseover','td', function () {
        jQuery(this).addClass("addPointer");
    });
}

//checkbox全选后table全部背景添加颜色(暂时未生效)
// function allTableBgColor(checkId,tableId) {
//
//     if(jQuery("#"+checkId).find(":checkbox").attr("checked")){
//         jQuery("#"+tableId).find("td").addClass("selected");
//     }else{
//         jQuery("#"+tableId).find("td").removeClass("selected");
//     }
// }

//给table设置高度值
function tableHeightSet(id){
    var tableListLength=jQuery("#"+id).find('tbody').find('tr').length;
    if(tableListLength<6){
        jQuery("#"+id).css("height",400);
    }
}

//交通补贴弹框
function travelSubsidyDialog(){
    var listLength=jQuery('#FundsManagerAssistantTable tbody>tr').length;
        for(var i=0;i<listLength;i++){
            jQuery('#FundsManagerAssistantTable').on('click', 'tbody>tr:eq('+i+')>td:eq(8)', function (e) {
                var targetid = FundsManagerAssistantTable;
                var id = e.target.id;
                var patt = new RegExp(".*line[0-9]+.*");
                if (typeof(id) == "undefined" || id == "" || !patt.test(id))
                    id = jQuery(e.target).children("*[id*='line']")[0].id;
                var index = id.match("line[0-9]+")[0].match('[0-9]+');

                document.getElementById('trafficIndex_control').value = index;

                jQuery("#trafficButton").click();//通过按钮弹框，index得到行下标值

            });
    }
}

// function numberStyle(str){
//     var len = str.length, str2 = '', max = Math.floor(len / 3);
//     for(var i = 0 ; i < max ; i++){
//         var s = str.slice(len - 3, len);
//         str = str.substr(0, len - 3);
//         str2 = (',' + s) + str2;
//         len = str.length;
//     }
//     str += str2;
//     return str
// }

