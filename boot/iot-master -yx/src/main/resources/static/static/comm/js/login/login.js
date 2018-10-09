var login_page = {};
login_page.action = {
    initBody:function () {
        $("#username").on("keypress", function (e) {
            if (e.keyCode==13) {
                $("#loginBtn").trigger("click");
            }
        });
        $("#username").focus();
        $("#pswd").on("keypress", function (e) {
            if (e.keyCode==13) {
                $("#loginBtn").trigger("click");
            }
        });
        login_page.action.bindEventPage();
    },
    bindEventPage :function () {
        $("#loginBtn").click(function () {
            layer.load();
            $("#loginBtn").attr('disabled', true);
            setTimeout(function () {
                $("#loginBtn").attr('disabled', false);
            },2000);
            var username = $("#username").val().trim().toLowerCase();
            var password = $("#pswd").val().trim();
            if (username=='' || password=='') {
                layer.msg('请输入用户名和密码');
                layer.closeAll('loading');
                return;
            }
            var pswd = MD5(username +"#" + password);
            var postData = {
                username: username,
                pswd: pswd
            };

            var onSuccess = function (data) {
                layer.closeAll('loading');
                if (!data.success) {
                    layer.msg(data.error);
                } else {
                    AppCommon.url.replaceWithUrl(AppCommon.url.getBaseURL()+"/index/indexPage");
                }
            };
            var onerror = function (data) {
                layer.closeAll('loading');
                layer.msg("用户名或者密码不正确,请重新输入");
            };
            AppCommon.ajax.execute({
                'url':AppCommon.url.getBaseURL()+"/ajax/login",
                'data':postData,
                'success':onSuccess,
                'error':onerror

            });
        })
    }
};

login_page.event={
    'isReaded':false,
    onReady: function () {
        login_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!login_page.event.isReaded) {
        login_page.event.isReaded = true;
        login_page.event.onReady();
    }
});