var ztreeJs = {};

ztreeJs.action = {

    initBody: function () {
        var ztreeChecked = new CommonZtree("ztreeChecked", "/ztree/ajax/getZtreeData", null);
        ztreeChecked.setZtreeCheck(true);
        ztreeChecked.setZtreeCallback(null, null, ztreeChecked.beforeExpand);
        ztreeChecked.ztreeInit();
        ztreeJs.data.ztreeChecked = ztreeChecked;

        var ztreeNoChecked = new CommonZtree("ztreeNoChecked", "/ztree/ajax/getZtreeData", null);
        ztreeNoChecked.ztreeInit();

        ztreeJs.action.bindEvent();
    },

    bindEvent: function () {
        $("#checkNodes").click(function () {
            var treeCheckedNodes = ztreeJs.data.ztreeChecked.getCheckedNodes();
            layer.alert(JSON.stringify(treeCheckedNodes));
        });
        $("#checkedAll").click(function () {
             ztreeJs.data.ztreeChecked.checkAllNodes(true);
        });

        $("#cancelCheckedAll").click(function () {
            ztreeJs.data.ztreeChecked.checkAllNodes(false);
        });
    }
};

ztreeJs.data = {
    ztreeChecked: null
};

ztreeJs.event = {
    'isReaded': false,
    onReady: function () {
        ztreeJs.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!ztreeJs.event.isReaded) {
        ztreeJs.event.isReaded = true;
        ztreeJs.event.onReady();
    }
});