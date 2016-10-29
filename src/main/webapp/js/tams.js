/**
 * Created by DELL on 2016-10-16.
 */

function doEditorPreSubmit(editorID,transferID) {

    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}


//初始化导航栏对话框
function initNavDialog() {
    //alert(jQuery(".nav-pills a").size());
    //设置nav-pills中各个选项的点击事件为切换导航栏目
    jQuery(".nav-pills a").click(function(e){
        //将当前导航栏目隐藏
        jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
        //显示选择的导航栏目
        jQuery(this).tab('show');
    });
}




