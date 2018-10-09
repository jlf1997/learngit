var area = {};

area.action = {
    initBody: function () {
        area.interface.initTablePage();
        area.action.bindEvent();
    },
    bindEvent: function () {

        $("#addBtn").click(function () {
            console.log("click");
            area.interface.initAreaData();
            area.interface.initFormPage();
            area.action.bindEvent();
            return false;
        });
        $("#saveBtn").click(function () {
            console.log("saveBtn");
            area.action.saveForm();
        });
        $("#cancelBtn").click(function () {
            //清空缓存的数据
            //area.data.areaData ={};
            area.action.initBody();
            return false;
        });

    },
    saveForm: function () {
        console.log("save");
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg("保存成功！");
                area.action.initBody();
            } else {
                layer.msg("保存失败！");
            }
        };

        layui.use('form', function () {
            console.log("use form");
            var form = layui.form;
            form.render();
            //监听提交
            form.on('submit(saveBtn1)', function (data) {
                console.log("save form sub");
                // if(){
                //
                // }
                layer.load();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/area/ajax/save',
                    'data': data.field,
                    'success': onSuccess
                });
                return false;
            });
        });


    },
    deleteArea: function (areaId) {
        var params = {
            'areaId': areaId
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                area.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/area/ajax/delete',
            'data': params,
            'success': onSuccess
        });
    },
    bindEventClose: function () {
        $(".close").click(function () {
            this.remove();
        })
    }
}
area.interface = {
    // 加载表格页
    initTablePage: function () {
        var html = template("areaManagerList_template");
        $(".areaManager_box").html(html);

        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/area/ajax/getList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'title',
                        title: '地区标题',
                        //width:200,
                        align: 'left'
                    },
                    {
                        field: 'areaId',
                        title: '地区编码',
                        align: 'left'
                    }, {
                        field: 'code',
                        title: '地区拼音',
                        align: 'left'
                    }, {
                        field: 'pTitle',
                        title: '父级地区',
                        align: 'left'
                    },
                    {
                        field: 'orderId',
                        title: '排序号',
                        edit: 'text',
                        align: 'left'
                    },
                    {
                        field: 'opration',
                        //width:140,
                        title: '操作',
                        align: 'center',
                        toolbar: '#toolbar'
                    }
                ]]
            });

            area.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    console.log("编辑");
                    area.interface.initAreaData();
                    area.interface.initFormPage(rowData.id);
                    area.action.bindEvent();
                } else if (layEvent === 'deleteBtn') {
                    layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                        area.action.deleteArea(rowData.id);
                        layer.close(index);
                    }, function (index) {
                        layer.close(index);
                    });
                }
            });

            //修改排序字段
            table.on('edit(tb_list)', function(obj){
                var param ={
                    id:obj.data.id,
                    orderId:obj.value,
                }
                var onSuccess = function (resultData) {
                    if (resultData.success) {
                        layer.msg("排序已修改！");
                        area.action.initBody();
                    } else {
                        lay.msg("修改失败！");
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/area/ajax/save',
                    'data': param,
                    'success': onSuccess
                });
            });

            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    title: $.trim(data.field.areaTitle)
                };
                tableIns.reload({
                	page:{curr:1},
                    where: postData
                });
                return false;
            });
        });

    },
    // 初始化表单页
    initFormPage: function (areaId) {
        console.log("dataId:" + areaId);
        if (areaId == undefined || '' == areaId.trim()) {
            // 添加
            console.log("add");
            var html = template("areaManagerForm_template");
            $(".areaManager_box").html(html);
            layui.use('form', function () {
                var form = layui.form;
                form.render();
                //规则校验
                form.verify({});
            });
            return;
        } else {
            // 编辑
            console.log("edit");
            layer.load();
            var params = {
                areaId: areaId
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                area.data.areaData = resultData.data;
                var html = template("areaManagerForm_template", resultData.data);
                $(".areaManager_box").html(html);
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                    //规则校验
                    form.verify({});
                });
                area.interface.initAreaData();
                area.action.bindEvent();
            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/area/ajax/getAreaInfo',
                'data': params,
                'success': onSuccess
            });
        }

    },
    // 初始化相关插件
    initAreaData: function () {

        //初始化省市区搜索下拉框

        var data = {
            "pid": null
        }

        var pidSelect = new CommonSelectedSearch("pidDiv", "pid", "/area/ajax/getAreaData");
        var onSuccess = function (resultData) {
            //缓存数据
            area.data.allAreaData = resultData.data;
            if (resultData.success) {
                var opt = {
                    "key":"请选择",
                    "value":""
                };
                resultData.data.unshift(opt);
                pidSelect.allDataInit(null, resultData.data);
                if (area.data.areaData.pid != null) {
                    $("#pidDiv").find("dd[lay-value=" + area.data.areaData.pid + "]").click();
                }
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/area/ajax/getAreaData',
            'data': data,
            'success': onSuccess
        });


    },
};

area.data = {
    "areaData": {},
    //所有地区数据缓存
    "allAreaData":null
};

area.event = {
    'isReaded': false,
    onReady: function () {
        area.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!area.event.isReaded) {
        area.event.isReaded = true;
        area.event.onReady();
    }
});
