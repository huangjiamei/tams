/**
 * Created by DELL on 2016-10-16.
 */

//����col-in-center-block�ľ���
$(window).resize(function(){

    $('.col-in-center-block').css({
        display:'inline-block',

        width: ($(window).width()
        - $('.col-in-center-block').outerWidth())/2,

    });

});
