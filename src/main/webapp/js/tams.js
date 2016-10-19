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

function doEditorPreSubmit(editorID,transferID) {

    // alert('asdf');
    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}


