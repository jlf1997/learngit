var selectSearch = {};

selectSearch.action={
    initBody:function () {
        var demoSelect = new CommonSelectedSearch("demoDiv", "demoSelect", "/selectSeacrch/ajax/getSelectData");
        demoSelect.asyncLoadInit();

        var allInSelect =new CommonSelectedSearch("allInDiv","allInSelect","/selectSeacrch/ajax/getSelectData");
        allInSelect.allDataInit({param:22});
    },
};

selectSearch.event = {
    'isReaded': false,
    onReady: function () {
        selectSearch.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!selectSearch.event.isReaded) {
        selectSearch.event.isReaded = true;
        selectSearch.event.onReady();
    }
});