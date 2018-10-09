// 兼容jquery3.2.1
$.fn.andSelf = function() {
	return this.addBack.apply(this, arguments);
};

//iframe自适应
/*	$(window).on('resize', function() {
		var $content = $('.admin-nav-card .layui-tab-content');
		$content.height($(this).height() - 110);
		$content.find('iframe').each(function() {
			$(this).height($content.height());
		});
	}).resize();*/
//左侧栏收缩
$('.admin-side-toggle').on('click', function() {
    var sideWidth = $('#admin-side').width();
    if(sideWidth === 200) {
        $('.tooltips').tipso({
            useTitle: false,
        });
        $('.layui-side-scroll .layui-nav-tree').addClass('minibar');
        $('#admin-body').animate({
            left: '60px'
        }); //admin-footer
        $('#admin-footer').animate({
            left: '60px'
        });
        $('#admin-side').animate({
            width: '60px'
        });
        $('.layui-nav-tree').animate({
            width: '60px'
        });
    } else {
        $('.tooltips').tipso('destroy');
        $('.layui-side-scroll .layui-nav-tree').removeClass('minibar');
        $('#admin-body').animate({
            left: '200px'
        });
        $('#admin-footer').animate({
            left: '200px'
        });
        $('#admin-side').animate({
            width: '200px'
        });
        $('.layui-nav-tree').animate({
            width: '200px'
        },0);
    }
});
//手机设备的简单适配
var treeMobile = $('.site-tree-mobile'),
	shadeMobile = $('.site-mobile-shade');
treeMobile.on('click', function() {
	$('body').addClass('site-mobile');
});
shadeMobile.on('click', function() {
	$('body').removeClass('site-mobile');
});

//注销登录
$('#logoutBtn').on('click', function() {
	console.log('logout');
    layer.load();
    var postData = {
    };
    var onSuccess = function (data) {
        layer.closeAll('loading');
        if (!data.success) {
            layer.msg(data.error);
        } else {
            layer.msg("退出成功！",function () {
                window.location.reload();
            });
        }
    };
    AppCommon.ajax.execute({
        'url':AppCommon.url.getBaseURL()+"/ajax/logout",
        'data':postData,
        'success':onSuccess
    });
});