/**
 * Created by DELL on 2016-10-16.
 */

function doEditorPreSubmit(editorID,transferID) {

    // alert('asdf');
    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}
/*
    ClassListPage�еı�����ǰ����
    �����ػ����е������ؼ�(��rice����)������Ϊ����������ؼ������¼�����
 */
function getClassTableReady() {

    //�õ���������
    var table =jQuery('#ClassListPageTable table').DataTable();

    //���ص�list�е�input
    var searchHiddenList = jQuery('#SearchCondContainer > div > input');

    //�ڱ��м�һ��
    jQuery('#ClassListPageTable table thead').append( '<tr><tr>' );


    //�洢���п�ѡ��option
    var selectGradeHtml=document.getElementsByName("AIPsearchGradeId")[0].innerHTML;//�꼶
    var selectBelongDeptHtml=document.getElementsByName("AIPsearchDepartmentId")[0].innerHTML;//ѧԺ
    var selectCampusHtml=document.getElementsByName("AIPsearchCampusId")[0].innerHTML;//У��
    var selectLtypeHtml=document.getElementsByName("applicantLanguage")[0].innerHTML;//����
    var selectLevelHtml=document.getElementsByName("applicantLevelId")[0].innerHTML;//����


    //ȷ���ɼ�ѧԺ
    var selectDeptHtml;
    //�鿴��ǰ�û���ѧԺID
    var userDeptId = document.querySelector("#userDeptId > p").innerHTML;
    //��Ϊ������ɼ�����ѧԺ
    if(userDeptId.trim() == "114819126"){
        selectDeptHtml = selectBelongDeptHtml;
        document.getElementsByName("AIPsearchDepartmentId")[0].value = document.getElementsByName("applicantdDept")[0].value;
    }else{//����ǰ�û������ڽ���
        //����ѡ��ѧԺ����Ϊ��ǰ�û���ѧԺ
        selectDeptHtml = '<option selected="selected" value="'+userDeptId+'">'+document.querySelector("#userDeptName > p").innerHTML+'</option>';
        //��ѡ�в鿴��ѧԺ����Ϊ��ǰ�û�ѧԺ
        document.getElementsByName("applicantdDept")[0].value = userDeptId;
    }

    var htmlArr = new Array();
    htmlArr[6] = selectGradeHtml;
    htmlArr[7] = selectDeptHtml;
    htmlArr[8] = selectCampusHtml;
    htmlArr[11] = selectLtypeHtml;
    htmlArr[12] = selectLevelHtml;



    jQuery('#ClassListPageTable table thead tr:eq(0) th').each( function ( i ) {//���ڱ���ÿһ��
        var thwidth = this.style.width;
        jQuery('#ClassListPageTable table thead tr:eq(1)').append( '<th></th>' );
        var column = table.column( i );
        if(i==1 || i==5){ //������ѧ��
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
        else if(i==10||(i>14 && i<17)){ //֤����||�������||׼��֤��
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
        else if(i==3 || i==4 || i==9 || i==13){//ѧ��||ѧ��||֤������||Ӧ�����
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
        else if(i==2){//�Ա�
            var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
                +'<option value selected ="selected"></option>'
                +'<option value="F">Ů</option>'
                +'<option value="M">��</option>'
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
        else if(i==6||i==8||i==12){//�꼶||У��||���Լ���
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
        else if(i==11){//��������
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
        else if(i==7){//ѧԺ
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
        else if(i==14){//�Ƿ�ɷ�
            var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
                +'<option value selected ="selected"></option>'
                +'<option value="0">��</option>'
                +'<option value="1">��</option>'
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


