var form_layui_page={};
form_layui_page.action={
    initBody:function () {
        $("#saveBtn").click(function () {
            var params = {

            };
            var onSuccess = function () {

            };
            AppCommon.ajax.execute({
                'url':AppCommon.url.getBaseURL()+"/example/ajax/saveExample",
                'data':params,
                'success':onSuccess
            });
        });
    }
};

list_layui_page.event={
    'isReaded':false,
    onReady: function () {
        form_layui_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!form_layui_page.event.isReaded) {
        form_layui_page.event.isReaded = true;
        form_layui_page.event.onReady();
    }
});