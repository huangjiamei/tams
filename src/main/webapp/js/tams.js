/**
 * Created by DELL on 2016-10-16.
 */

function doEditorPreSubmit(editorID,transferID) {

    // alert(UE.getEditor(editorID).getPlainTxt());
    jQuery('#' + transferID).val(UE.getEditor(editorID).getPlainTxt());
    return true;
}


//��ʼ���������Ի���
function initNavDialog() {
    //ȥ��uif-dialog�Դ���header
    jQuery(".navigationDialog>.modal-content>.modal-header").remove();

    jQuery("#navDialog").removeClass("fade");
    //����nav-pills�и���ѡ��ĵ���¼�Ϊ�л�������Ŀ
    jQuery(".navigationDialog .nav a").click(function(e){
        //����ǰ������Ŀ����
        e.preventDefault();
        //��ʾѡ��ĵ�����Ŀ
        jQuery(this).tab('show');
    });

    //�رյ���¼�
    jQuery(".navigationDialog .close").click(function(a) {
        a.stopPropagation();//�õ���¼�ֹͣ��������ص�����dialog
        //ȥ��ѡ��Ч��
        jQuery(".navigationDialog .nav .active").removeClass("active"), jQuery(".navigationDialog .tab-content .active").removeClass("active")
    })
}




