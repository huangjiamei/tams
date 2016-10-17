/**
 * Created by DELL on 2016-10-16.
 */

//保持col-in-center-block的居中
$(window).resize(function(){

    $('.col-in-center-block').css({
        display:'inline-block',

        width: ($(window).width()
        - $('.col-in-center-block').outerWidth())/2,

    });

});
