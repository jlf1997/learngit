var operationManagement_page={};

operationManagement_page.action = {
    initBody:function () {
        operationManagement_page.interface.initTablePage();
        operationManagement_page.action.bindEvent();
    },
    bindEvent:function () {
        $("#addBtn").click(function () {
            operationManagement_page.interface.initFormPage();
            return false;
        });
        $("#cancelBtn").click(function () {
            operationManagement_page.action.initBody();
            return false;
        });
    },
    deleteOperation:function (id) {
        var params = {
            id:id
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                operationManagement_page.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/deleteOperation',
            'data':params,
            'success':onSuccess
        });
    }
};
operationManagement_page.interface = {
    // 加载表格页
    initTablePage:function () {
        var html = template("operationManagementList_template");
        $(".operationManagement_box").html(html);
        layui.use(['table','form'], function(){
            var table = layui.table;
            //执行表格渲染
            var tableIns = table.render({
                //height:476,
                url:AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationList',
                elem:"#tb_list",
                page: true,
                // limit:10,
                loading:true,
                id:'id',
                cols: [[
                    {
                        field:'operationKey',
                        title:'操作关键字',
                        align:'left',
                    },
                    {
                        field:'operationName',
                        title:'操作名称',
                        align:'left',
                    },{
                        field:'orderId',
                        title:'排序号',
                        edit: 'text',
                        align:'left',
                    },{
                        field:'comment',
                        title:'说明',
                        align:'left',
                    }, {
                        title:'操作',
                        fixed:'right',
                        width:180,
                        align:'center',
                        toolbar:'#toolbar',
                        fixed: 'right'
                    }
                ]]
            });

            operationManagement_page.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent==='editBtn') {
                    operationManagement_page.interface.initFormPage(rowData.id);
                    operationManagement_page.action.bindEvent();
                }
                if (layEvent==='deleteBtn') {
                    layer.confirm('确定要进行删除吗？',{icon: 3, title:'提示'},function(index){
                        operationManagement_page.action.deleteOperation(rowData.id);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                    });
                }
            });
            table.on('edit(tb_list)', function(obj){
                var param ={
                    id:obj.data.id,
                    orderId:obj.value,
                }
                var onSuccess = function (resultData) {
                    if (resultData.success) {
                        layer.msg("排序号已修改！");
                        operationManagement_page.action.initBody();
                    } else {
                        layer.msg("修改失败！");
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
                    operationName: data.field.operationName
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
    initFormPage:function (dataId) {
        if (dataId==undefined || ''==dataId.trim()) {
            // 添加
            var html = template("operationManagementForm_template");
            $(".operationManagement_box").html(html);
            operationManagement_page.action.bindEvent();
            layui.use('form', function(){
                var form = layui.form;
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
                            return "角色关键字已存在";
                        }

                    },
                });

                form.on('submit(saveBtn)', function(data){
                    var onSuccess = function (resultData) {
                        if (resultData.success) {
                            layer.msg("保存成功！");
                            roleManagement_page.action.initBody();
                        } else {
                            layer.msg(resultData.error);
                        }
                    };
                    AppCommon.ajax.execute({
                        'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/saveOperation',
                        'data':data.field,
                        'success':onSuccess
                    });
                });
            });

        } else {
            // 编辑
            layer.load();
            var params = {
                id:dataId
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                var html = template("operationManagementForm_template", resultData.data);
                $(".operationManagement_box").html(html);
                operationManagement_page.action.bindEvent();
                layui.use('form', function(){
                    var form = layui.form;
                    form.verify({
                        operationKey:function(value){
                            var flag = 0;
                            var params={
                                id:dataId,
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
                                return "角色关键字已存在";
                            }

                        },
                    });

                    form.on('submit(saveBtn)', function(data){
                        data.field.id = dataId;
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                            	layer.closeAll('loading');
                                layer.msg("保存成功！");
                                roleManagement_page.action.initBody();
                            } else {
                                layer.msg("保存失败！");
                            }
                        };
                        var onError = function (resultData) {
                        	layer.closeAll('loading');
                        	layer.msg("发生错误！");
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/saveOperation',
                            'data':data.field,
                            'success':onSuccess,
                            'error':onError
                        });
                    });
                });
            };
            AppCommon.ajax.execute({
                'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationById',
                'data':params,
                'success':onSuccess
            });
        }
    },



};

operationManagement_page.data = {
    //'tableIns':undefined,
};

operationManagement_page.event={
    'isReaded':false,
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