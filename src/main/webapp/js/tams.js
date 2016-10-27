/**
 * Created by DELL on 2016-10-16.
 */

function doEditorPreSubmit(editorID,transferID) {

    // alert('asdf');
    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}



    !function(a) {
        a(".esmenu .nav-pills a:first").tab("show");

        a('[data-toggle="tooltip"]').tooltip();

        a(window).click(function(e) {
            1 != e.which && 0 != e.which || a("#megamenu-items").collapse("hide");
        });

        a("#megamenu-items").click(function(a) {
            a.stopPropagation()
        });

        a("#megamenu-items").on("shown.bs.collapse", function() {
            a(".esmenu .menu-icon").removeClass("fa-pulse");
        });

        a("#megamenu-items").on("show.bs.collapse", function() {
            ajQuery(".esmenu .menu-icon").addClass("fa-pulse");
        });

        a(".esmenu .nav-pills a").click(function(e) {
            e.preventDefault(), jQuery(this).tab("show");
        });

        a(".esmenu .close").click(function() {
            a(".esmenu .nav-pills a.active").removeClass("active");
            a(".esmenu .tab-content div.active").removeClass("active");
        });
    }(jQuery)




/*
    ClassListPage中的表格加载前调用
    用于重绘表格中的搜索控件(因rice限制)，并且为各搜索输入控件加入事件监听
 */
function getClassTableReady() {

    //得到表中数据
    var table =jQuery('#ClassListPageTable table').DataTable();

    //隐藏的list中的input
    var searchHiddenList = jQuery('#SearchCondContainer > div > input');

    //在表中加一行
    jQuery('#ClassListPageTable table thead').append( '<tr><tr>' );


    //存储所有可选的option
    var selectGradeHtml=document.getElementsByName("AIPsearchGradeId")[0].innerHTML;//年级
    var selectBelongDeptHtml=document.getElementsByName("AIPsearchDepartmentId")[0].innerHTML;//学院
    var selectCampusHtml=document.getElementsByName("AIPsearchCampusId")[0].innerHTML;//校区
    var selectLtypeHtml=document.getElementsByName("applicantLanguage")[0].innerHTML;//类型
    var selectLevelHtml=document.getElementsByName("applicantLevelId")[0].innerHTML;//级别


    //确定可见学院
    var selectDeptHtml;
    //查看当前用户的学院ID
    var userDeptId = document.querySelector("#userDeptId > p").innerHTML;
    //若为教务处则可见所有学院
    if(userDeptId.trim() == "114819126"){
        selectDeptHtml = selectBelongDeptHtml;
        document.getElementsByName("AIPsearchDepartmentId")[0].value = document.getElementsByName("applicantdDept")[0].value;
    }else{//若当前用户不属于教务处
        //将可选的学院设置为当前用户的学院
        selectDeptHtml = '<option selected="selected" value="'+userDeptId+'">'+document.querySelector("#userDeptName > p").innerHTML+'</option>';
        //将选中查看的学院设置为当前用户学院
        document.getElementsByName("applicantdDept")[0].value = userDeptId;
    }

    var htmlArr = new Array();
    htmlArr[6] = selectGradeHtml;
    htmlArr[7] = selectDeptHtml;
    htmlArr[8] = selectCampusHtml;
    htmlArr[11] = selectLtypeHtml;
    htmlArr[12] = selectLevelHtml;



    jQuery('#ClassListPageTable table thead tr:eq(0) th').each( function ( i ) {//对于表格的每一列
        var thwidth = this.style.width;
        jQuery('#ClassListPageTable table thead tr:eq(1)').append( '<th></th>' );
        var column = table.column( i );
        if(i==1 || i==5){ //姓名和学号
            var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:45px;">')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty())
                .on( {keyup: function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                } ,
                    keydown: function(e){
                        var key = e.which;
                        if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
                            e.preventDefault();
                            table.page(0).draw(false);
                            if(table.page() > 0) {
                                table.page(0).draw(false);
                            }
                            jQuery("#ApplicantPageButton").click();
                        }
                    }
                });
        }
        else if(i==10||(i>14 && i<17)){ //证件号||报名序号||准考证号
            var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( {keyup: function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                } ,
                    keydown: function(e){
                        var key = e.which;
                        if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
                            e.preventDefault();
                            table.page(0).draw(false);
                            if(table.page() > 0) {
                                table.page(0).draw(false);
                            }
                            jQuery("#ApplicantPageButton").click();
                        }
                    }
                });
        }
        else if(i==3 || i==4 || i==9 || i==13){//学历||学制||证件类型||应交金额
            var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:25px;">')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( {keyup: function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                } ,
                    keydown: function(e){
                        var key = e.which;
                        if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
                            e.preventDefault();
                            table.page(0).draw(false);
                            if(table.page() > 0) {
                                table.page(0).draw(false);
                            }
                            jQuery("#ApplicantPageButton").click();
                        }
                    }
                });
        }
        else if(i==2){//性别
            var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
                +'<option value selected ="selected"></option>'
                +'<option value="F">女</option>'
                +'<option value="M">男</option>'
                +'</select>')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( 'change', function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                    table.page(0).draw(false);
                    if(table.page() > 0) {
                        table.page(0).draw(false);
                    }
                    jQuery("#ApplicantPageButton").click();
                } );
            select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
        }
        else if(i==6||i==8||i==12){//年级||校区||语言级别
            var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( 'change', function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                    console.info(table.page());
                    table.page(0).draw(false);
                    if(table.page() > 0) {
                        table.page(0).draw(false);
                    }
                    jQuery("#ApplicantPageButton").click();
                } );
            select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
        }
        else if(i==11){//语言类型
            var select = jQuery('<select style="font-weight:normal;height:24px;width:50px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( 'change', function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                    console.info(table.page());
                    table.page(0).draw(false);
                    if(table.page() > 0) {
                        table.page(0).draw(false);
                    }
                    jQuery("#ApplicantPageButton").click();
                } );
            select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
        }
        else if(i==7){//学院
            var select = jQuery('<select style="font-weight:normal;height:24px;width:80px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( 'change', function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                    console.info(table.page());
                    table.page(0).draw(false);
                    if(table.page() > 0) {
                        table.page(0).draw(false);
                    }
                    jQuery("#ApplicantPageButton").click();
                } );
            select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
        }
        else if(i==14){//是否缴费
            var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
                +'<option value selected ="selected"></option>'
                +'<option value="0">否</option>'
                +'<option value="1">是</option>'
                +'</select>')
                .appendTo( jQuery( '#ClassListPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
                .on( 'change', function () {
                    var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
                    var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
                    obj.value = val;
                    if(table.page() > 0) {
                        table.page(0).draw(false);
                    }
                    jQuery("#ApplicantPageButton").click();
                } );
            select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
        }
    })


}


