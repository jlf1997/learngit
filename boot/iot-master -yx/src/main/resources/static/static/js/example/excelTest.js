var excel = {};

excel.action = {
    initBody: function () {
        excel.interface.initHtml();
        $("#showdata").click(function () {
            $("#div_data").html("");
            for(var i=0;i<excel.data.showData.getData().length;i++){
                $("#div_data").append(JSON.stringify(excel.data.showData.getData()[i]));
            }
        })
    },
};
excel.interface = {
    initHtml: function () {
        var excelIn0 = new excelIn("test3");
        excelIn0.excelInit();
        excel.data.showData = excelIn0;
    },

};
excel.data={
    showData:null
}
excel.event = {
    'isReaded': false,
    onReady: function () {
        excel.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!excel.event.isReaded) {
        excel.event.isReaded = true;
        excel.event.onReady();
    }
});