/**
 * Created by DELL on 2016-10-16.
 */

// $(window).resize(function(){
//
//     $('.col-in-center-block').css({
//         display:'inline-block',
//
//         width: ($(window).width()
//         - $('.col-in-center-block').outerWidth())/2,
//
//     });
//
// });

/**
 * 每次点击btn都会提交两次，
 * 所有首先判断transfer和editor中内容是否有区别，内容不同时才会提交
 * @param editorID
 * @param transferID
 * @returns {boolean}
 */
function doEditorPreSubmit(editorID,transferID) {
    var content=UE.getEditor(editorID).getPlainTxt();
    if (document.getElementById(transferID).value==content){
        return false;
    }else{
        jQuery('#' + transferID).val(content);
        return true;
    }
}


