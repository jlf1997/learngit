var ueditor = {};

ueditor.action = {
    initBody: function () {
        var commonUeditor = new CommonUeditor("container", 1000, 400);
        commonUeditor.init();
        ueditor.data.commonUeditor = commonUeditor;
        ueditor.action.bindEvent();
    },

    bindEvent: function () {
        $("#getHtml").click(function () {
            layer.alert(ueditor.data.commonUeditor.getContentHtml());
            console.info(ueditor.data.commonUeditor.getContentHtml());
        });
        $("#getText").click(function () {
            layer.alert(ueditor.data.commonUeditor.getContentText());
        });
    }
};


ueditor.event = {
    'isReaded': false,
    onReady: function () {
        ueditor.action.initBody();
    }
};

ueditor.data = {
    commonUeditor: null
};

AppCommon.action.ready(function () {
    if (!ueditor.event.isReaded) {
        ueditor.event.isReaded = true;
        ueditor.event.onReady();
    }
});