jQuery(document).ready(function(){

	/*
		这部分代码实现导航栏的切换功能
	 */
	//让导航栏的tab部分点击事件停止传播，以免切换导航之后下拉框消失。
	jQuery("#megamenu-content").click(function(a) {
		a.stopPropagation()
	});
	//设置nav-pills中各个选项的点击事件为切换导航栏目
	jQuery(".nav-pills a").click(function(e){
		//将当前导航栏目隐藏
		jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
		//显示选择的导航栏目
		jQuery(this).tab('show');

	});
});


//暂时不用
function getNavReady() {
	showDialog('guideMapDialog');
	alert(jQuery(".nav-pills a").size());
	jQuery(".nav-pills a").click(function(e){
		//alert("aaa");
		jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
		jQuery(this).tab('show');

	});
}
