var dict = {};

dict.action = {
    initBody: function () {
        dict.interface.initTablePage();
        dict.action.bindEvent();
    },
    bindEvent: function () {

        $("#addBtn").click(function () {
            console.log("click");
            dict.interface.initDictData();
            dict.interface.initFormPage();
            dict.action.bindEvent();
            return false;
        });
        $("#saveBtn").click(function () {
            //console.log("saveBtn");
            dict.action.saveForm();
        });
        $("#cancelBtn").click(function () {
            //清空缓存的数据
            //dict.data.dictData = {};
            dict.action.initBody();
            return false;
        });

    },
    saveForm: function () {
        console.log("save");
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.data.result == "1") {
                alert("请输入同类型不同键值！");
                return false;
            }
            if (resultData.success) {
                layer.msg("保存成功！");
                dict.action.initBody();
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
                //console.log("save form sub");
                layer.load();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/dict/ajax/save',
                    'data': data.field,
                    'success': onSuccess
                });
                return false;
            });
        });


    },
    deleteDict: function (Id) {
        var params = {
            'Id': Id
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                dict.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/dict/ajax/delete',
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
dict.interface = {
    // 加载表格页
    initTablePage: function () {
        var html = template("dictManagerList_template");
        $(".dictManager_box").html(html);
        dict.interface.initDictData();
        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/dict/ajax/getList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'value',
                        title: '键值',
                        width: 100,
                        align: 'left'
                    },
                    {
                        field: 'label',
                        title: '标签',
                        width: 200,
                        align: 'left'
                    }, {
                        field: 'type',
                        title: '类型',
                        width: 300,
                        align: 'left'
                    }, {
                        field: 'description',
                        title: '描述',
                        width: 300,
                        align: 'left'
                    },
                    /*{
                        field: 'orderId',
                        title: '排序',
                        edit: 'text',
                        align: 'left'
                    },*/
                    {
                        field: 'opration',
                        width: 200,
                        title: '操作',
                        align: 'center',
                        toolbar: '#toolbar'
                    }
                ]]
            });

            dict.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    console.log("编辑");
                    dict.interface.initDictData();
                    dict.interface.initFormPage(rowData.id);
                    dict.action.bindEvent();
                } else if (layEvent === 'deleteBtn') {
                    layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                        dict.action.deleteDict(rowData.id);
                        layer.close(index);
                    }, function (index) {
                        layer.close(index);
                    });
                }
            });

            //修改排序字段
            table.on('edit(tb_list)', function (obj) {
                var param = {
                    id: obj.data.id,
                    orderId: obj.value,
                }
                console.log(JSON.stringify(param));
                var onSuccess = function (resultData) {
                    if (resultData.success) {
                        layer.msg("排序已修改！");
                        dict.action.initBody();
                    } else {
                        layer.msg("修改失败！");
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/dict/ajax/save',
                    'data': param,
                    'success': onSuccess
                });
            });

            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    allType: $.trim(data.field.allType),
                    descriptions: $.trim(data.field.descriptions)
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
    initFormPage: function (Id) {
        console.log("dataId:" + Id);
        if (Id == undefined || '' == Id.trim()) {
            // 添加
            console.log("add");
            var html = template("dictManagerForm_template");
            $(".dictManager_box").html(html);
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
                Id: Id
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                dict.data.dictData = resultData.data;
                var html = template("dictManagerForm_template", resultData.data);
                $(".dictManager_box").html(html);
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                    //规则校验
                    form.verify({});
                });
                dict.action.bindEvent();
            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/dict/ajax/getDictInfo',
                'data': params,
                'success': onSuccess
            });
        }

    },
    // 初始化相关插件
    initDictData: function () {

        //初始化搜索下拉框
        var data = {
            "allData": true
        };
        var allTypeSelect = new CommonSelectedSearch("allTypeDiv", "allType", "/dict/ajax/getDictData");
        var onSuccess = function (resultData) {
            if (resultData.success) {
                dict.data.allDictData = resultData.data;
                var opt = {
                    "key": "请选择类型",
                    "value": ""
                };
                var allTypeArr = [];
                var dictSelectData = [];
                dictSelectData.unshift(opt);
                for (var i = 0; i < resultData.data.length; i++) {
                    if (allTypeArr.indexOf(resultData.data[i].type) == -1) {
                        allTypeArr.push(resultData.data[i].type)
                        if (!$.isEmpty(resultData.data[i].type)) {
                            var type = {};
                            type.key = resultData.data[i].type;
                            type.value = resultData.data[i].type;
                            dictSelectData.push(type);
                        }

                    }
                }

                allTypeSelect.allDataInit(null, dictSelectData);
            }

        };

        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/dict/ajax/getDictData',
            'data': data,
            'success': onSuccess
        });


    },
};

dict.data = {
    "dictData": {},

    "allDictData": null
};

dict.event = {
    'isReaded': false,
    onReady: function () {
        dict.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!dict.event.isReaded) {
        dict.event.isReaded = true;
        dict.event.onReady();
    }
});
