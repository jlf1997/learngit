var fileUpload = {};

fileUpload.action = {
    initBody: function () {
        fileUpload.action.bindEvent();
        fileUpload.interface.multiFileHtml();
    },
    bindEvent: function () {
    }
};

fileUpload.interface = {
    multiFileHtml: function () {
        var multiFile = new CommonFileUpload("multiFileUpload", null, null);
        multiFile.multiFilesInit(null, 5);
    },
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