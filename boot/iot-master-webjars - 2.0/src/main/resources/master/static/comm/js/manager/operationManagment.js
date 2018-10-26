var operationManagement_page = {};

operationManagement_page.action = {
    initBody: function () {
        operationManagement_page.interface.initTablePage();
        operationManagement_page.action.bindEvent();
    },
    bindEvent: function () {

        $("#addBtn").click(function () {
            operationManagement_page.interface.initFormPage();
            operationManagement_page.action.bindEvent();
            return false;
        });
        $("#saveBtn").click(function () {
            operationManagement_page.action.saveForm();
        });
        $("#cancelBtn").click(function () {
            //清空缓存的数据
            //operationManagement_page.data.operationData = {};
            operationManagement_page.action.initBody();
            return false;
        });
    },
    saveForm: function () {
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                operationManagement_page.action.initBody();
            }
        };

        layui.use('form', function () {
            var form = layui.form;
            form.render();
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                layer.load();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/operationManager/ajax/saveOperation',
                    'data': data.field,
                    'success': onSuccess
                });
                return false;
            });
        });
    },
    deleteOperation: function (operationId) {
        var params = {
            'id': operationId
        };
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                if ("删除成功" == resultData.error) {
                    operationManagement_page.data.tableIns.reload();
                }
            }
        };
        layer.load();
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/operationManager/ajax/deleteOperation',
            'data': params,
            'success': onSuccess
        });
    },
    bindEventClose: function () {
        $(".close").click(function () {
            this.remove();
        })
    },
};

operationManagement_page.interface = {
    // 加载表格页
    initTablePage: function () {
        var html = template("operationManagementList_template");
        $(".operationManagement_box").html(html);

        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/operationManager/ajax/getOperationList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field:'operationKey',
                        title:'操作关键字',
                        align:'left',
                        style: 'width: 10%',
                    },
                    {
                        field:'operationName',
                        title:'操作名称',
                        align:'left',
                        style: 'width: 20%',
                    },{
                        field:'orderId',
                        title:'排序号',
                        edit: 'text',
                        align:'left',
                        style: 'width: 20%',
                    },{
                        field:'comment',
                        title:'说明',
                        align:'left',
                        style: 'width: 30%',
                    }, {
                        title:'操作',
                        fixed:'right',
                        style: 'width: 20%',
                        align:'center',
                        toolbar:'#toolbar',
                        fixed: 'right'
                    }
                ]]
            });

            operationManagement_page.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    //点击编辑按钮需要进行的一些初始化操作
                    operationManagement_page.interface.initFormPage(rowData.id);
                    operationManagement_page.action.bindEvent();
                } else if (layEvent === 'deleteBtn') {
                    layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                        operationManagement_page.action.deleteOperation(rowData.id);
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
                var onSuccess = function (resultData) {
                	//console.log(JSON.stringify(resultData));
                    if (resultData.success) {
                        layer.msg("排序已修改！");
                        operationManagement_page.action.initBody();
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/operationManager/ajax/saveOperation',
                    'data': param,
                    'success': onSuccess
                });
            });


            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                	operationName: $.trim(data.field.operationNameInput)
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
    initFormPage: function (id) {
        console.log("dataId:" + id);
        if (id == undefined || '' == id.trim()) {
            // 添加
            var html = template("operationManagementForm_template");
            $(".operationManagement_box").html(html);
            layui.use('form', function () {
                var form = layui.form;
                form.render();
                //规则校验
                form.verify({
                    operationKey:function(value){
                        if((value==""||value==null)){
                            return "操作关键字不能为空";
                        }
                        var flag = 0;
                        var params={
                            operationKey:value,
                        };
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                if(resultData.data!=null){
                                    flag++;
                                }
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationListByKey',
                            'data':params,
                            'async':false,
                            'success':onSuccess
                        });
                        if(flag>0){
                            return "操作关键字已存在";
                        }
                    },
                });
            });
            return;
        } else {
            // 编辑
            layer.load();
            var params = {
                id: id
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                operationManagement_page.data.operationData = resultData.data;
                var html = template("operationManagementForm_template", resultData.data);
                $(".operationManagement_box").html(html);
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                    //规则校验
                    form.verify({
                        operationKey:function(value){
                            var flag = 0;
                            var params={
                                id:id,
                                operationKey:value,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if(resultData.data!=null){
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationListByKeyOutCurrent',
                                'data':params,
                                'async':false,
                                'success':onSuccess
                            });
                            if(flag>0){
                                return "操作关键字已存在";
                            }
                        },
                    });
                });
                operationManagement_page.action.bindEvent();
            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/operationManager/ajax/getOperationById',
                'data': params,
                'success': onSuccess
            });
        }

    },
};

operationManagement_page.data = {
	'tableIns':undefined,
    "operationData": {},
};

operationManagement_page.event = {
    'isReaded': false,
    onReady: function () {
        operationManagement_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!operationManagement_page.event.isReaded) {
        operationManagement_page.event.isReaded = true;
        operationManagement_page.event.onReady();
    }
});
