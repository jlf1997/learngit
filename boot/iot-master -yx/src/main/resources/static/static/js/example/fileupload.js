var fileUpload = {};

fileUpload.action = {
    initBody: function () {
        fileUpload.action.bindEvent();
        fileUpload.interface.singleFileHtml();
    },
    bindEvent: function () {
    }
};

fileUpload.interface = {

    singleFileHtml: function () {
        var singleFile = new CommonFileUpload("testUpload", null, null);
        singleFile.singleFileInit(null);
    }
};

fileUpload.event = {
    'isReaded': false,
    onReady: function () {
        fileUpload.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!fileUpload.event.isReaded) {
        fileUpload.event.isReaded = true;
        fileUpload.event.onReady();
    }
});