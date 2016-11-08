// alert($(".nav-pills a:first").html());
jQuery(document).ready(function(){
 	//alert(jQuery(".nav-pills a").size());
	jQuery(".nav-pills a").click(function(e){
			
			jQuery(".tab-content .active").removeClass("active").addClass("tab-pane");
			jQuery(this).tab('show');

	});
});
	

	function add(){
		var box=document.createElement("div");
		var txt=document.createElement("span");
		txt.innerHTML="aaaaa";
		$(box).append(txt);
		jQuery("#abc").append(box);
	}

 