jQuery(document).ready(function(){

	/*
		�ⲿ�ִ���ʵ�ֵ��������л�����
	 */
	//�õ�������tab���ֵ���¼�ֹͣ�����������л�����֮����������ʧ��
	jQuery("#megamenu-content").click(function(a) {
		a.stopPropagation()
	});
	//����nav-pills�и���ѡ��ĵ���¼�Ϊ�л�������Ŀ
	jQuery(".nav-pills a").click(function(e){
		//����ǰ������Ŀ����
		jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
		//��ʾѡ��ĵ�����Ŀ
		jQuery(this).tab('show');

	});
});


//��ʱ����
function getNavReady() {
	showDialog('guideMapDialog');
	alert(jQuery(".nav-pills a").size());
	jQuery(".nav-pills a").click(function(e){
		//alert("aaa");
		jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
		jQuery(this).tab('show');

	});
}
