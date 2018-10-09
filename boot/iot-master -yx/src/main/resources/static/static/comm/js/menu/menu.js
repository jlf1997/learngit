var sys_menu_page={};

sys_menu_page.action = {
    initBody: function () {
        var onSuccess = function (resultData) {
            layui.use(['element'],function () {
               var element = layui.element;
                if (resultData.success) {
                    var html = template("menuTree_template", resultData);
                    $(".menuTree_box").html(html);
                    element.render();
                    $('.layui-nav-tree .layui-nav-item').click(function() {
                        //    $(this).addClass('layui-nav-itemed');
                        $(this).siblings().removeClass('layui-nav-itemed');
                    });
                   /*  $('.layui-nav-tree .layui-nav-item').click(function (event) {
                         if ($(this).hasClass('layui-nav-itemed')) {
                             $(this).removeClass('layui-nav-itemed');
                         } else {
                             $(this).addClass('layui-nav-itemed');
                             $(this).siblings().removeClass('layui-nav-itemed');
                        }
                     });*/

                    /* $("#" + $("#layoutMenu").val()).addClass("layui-this");
                     $("#" + $("#layoutMenu").val()).parent().parent().addClass("layui-nav-itemed");
                     $("#" + $("#layoutMenu").val()).parent().parent().siblings().removeClass("layui-nav-itemed");

                     if ($("#" + $("#layoutMenu").val()).offset()!=undefined && $("#" + $("#layoutMenu").val()).offset().top>700) {
                     location.href = "#" + $("#layoutMenu").val();
                     }*/
                }
            });

        };
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/getMenuTreeByCurrentUser',
            'success':onSuccess,
            'data':null
        });
    }
};

sys_menu_page.data={
    'layoutMenu':''
};

sys_menu_page.event={
    'isReaded':false,
    onReady: function () {
        sys_menu_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!sys_menu_page.event.isReaded) {
        sys_menu_page.event.isReaded = true;
        sys_menu_page.event.onReady();
    }
});