var groupManagement_page = {};

groupManagement_page.action = {
    initBody: function () {
        groupManagement_page.interface.initTablePage();
        groupManagement_page.action.bindEvent();
    },
    bindEvent: function () {

        /*$("#addBtn").click(function () {
            groupManagement_page.interface.initFormPage();
            groupManagement_page.action.bindEvent();
            return false;
        });*/
        $("#saveBtn").click(function () {
            groupManagement_page.action.saveForm();
           /* return;*/
        });
        $("#cancelBtn").click(function () {
            //清空缓存的数据
            //groupManagement_page.data.groupData = {};
            groupManagement_page.action.initBody();
         /*   return false;*/
        });
        $("#selectMenu_add").click(function (){
            if($("#menuTree_add").is(":hidden")){
                $("#menuTree_add").show();
            }else{
                $("#menuTree_add").hide();
            }
            return false;
        })
    },
    saveForm: function () {
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                groupManagement_page.action.initBody();
            } else {
                layer.msg(resultData.error);
            }
        };

        layui.use('form', function () {
            var form = layui.form;
            form.render();
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                layer.load();
                data.field.parentId=$("#parentId_add").attr("data-id");
                data.field.parentName=$("#parentId_add").val();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/group/ajax/save',
                    'data': data.field,
                    'success': onSuccess
                });
            });
        });
    },
    deleteGroup: function (groupId) {
        var params = {
            'id': groupId
        };
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                if ("删除成功" == resultData.error) {
                    groupManagement_page.interface.initTablePage();
                }
            }else{
                layer.msg(resultData.error);
            }
        };
        layer.load();
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/group/ajax/delete',
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

groupManagement_page.interface = {
    // 加载表格页
    initTablePage: function () {
        var onSuccess = function (resultData) {
            if (resultData.success) {
                var html = template("groupManagerList_template");
                $(".groupManager_box").html(html);
                var layout = [//https://gitee.com/shaojiepeng/layui-treetable
                    {name: '组织名称', field: 'groupName',treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 30%'},
                    {name: '关键字', field: 'menuKey',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 20%'},
                    {name: '描述', field: 'comment',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 20%'},
                    // {name: '排序号', field: 'orderId',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {
                        name: '操作',
                        headerClass: 'value_col',
                        colClass: 'value_col',
                       /* style: 'width:2%,',*/
                        render: function (row) {
                            if(row.url==1){
                                return "";
                            }
                            return '<div class="layui-table-cell laytable-cell-1-operation">'+'<a class="layui-btn layui-btn-xs editBtn" data-id='+row.id+'>编辑</a><a class="layui-btn layui-btn-danger layui-btn-xs deleteBtn" data-id='+row.id+'>删除</a>'+'</div>'; //列渲染
                        }
                    },
                ];
                layui.use(['tree', 'layer'], function () {
                        var layer = layui.layer;
                        var layuiMenuTree=layui.treeGird({
                            elem: '#tb_list', //传入元素选择器
                            spreadable:true,
                            nodes: resultData.data,
                            layout: layout
                        });
                $(".editBtn").click(function () {
                    groupManagement_page.interface.initFormPage($(this).attr("data-id"));
                });
                $(".deleteBtn").click(function () {
                    var id = $(this).attr("data-id");
                    layer.confirm('确定要进行删除吗？',{icon: 3, title:'提示'},function(index){
                        groupManagement_page.action.deleteGroup(id);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                    });
                });
                    $("#addBtn").click(function () {
                        groupManagement_page.interface.initFormPage();
                        groupManagement_page.action.bindEvent();
                        return false;
                    });
                    $("#searchBtn").click(function () {
                        var groupName=$("#groupNameInput").val();
                        var params = {
                            groupName: groupName
                        };
                        var onSuccess = function (resultData) {

                        };
                        layer.load();
                        AppCommon.ajax.execute({
                            'url': AppCommon.url.getBaseURL() + '/group/ajax/getGroupTreeParent',
                            'data': params,
                            'success': onSuccess
                        });
                    });
                });
  /*      layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/group/ajax/getList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'groupName',
                        title: '组织名称',
                        // width:200,
                        align: 'left'
                    },
                    {
                        field: 'groupKey',
                        title: '关键字',
                        //width:200,
                        align: 'left'
                    },
                    {
                        field: 'comment',
                        title: '描述',
                        //width:200,
                        align: 'left'
                    },
                    {
                        field: 'orderId',
                        title: '排序号',
                        edit: 'text',
                        //width:200,
                        align: 'left'
                    },
                    {
                        field: 'opration',
                        title: '操作',
                        fixed: 'right',
                        align: 'center',
                        toolbar: '#toolbar'
                    }
                ]]
            });

            groupManagement_page.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    //点击编辑按钮需要进行的一些初始化操作
                    groupManagement_page.interface.initFormPage(rowData.id);
                    groupManagement_page.action.bindEvent();
                } else if (layEvent === 'deleteBtn') {
                    layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                        groupManagement_page.action.deleteGroup(rowData.id);
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
                    if (resultData.success) {
                        layer.msg("排序已修改！");
                        groupManagement_page.action.initBody();
                    } else {
                        layer.msg("修改失败！");
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/group/ajax/save',
                    'data': param,
                    'success': onSuccess
                });
            });


            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    groupName: $.trim(data.field.groupNameInput)
                };
                tableIns.reload({
                	page:{curr:1},
                    where: postData
                });
                return false;
            });
        });
*/

            }
        }
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/group/ajax/getGroupTreeParent',
            'data':null,
            'success': onSuccess
        });
    },
    // 初始化表单页
    initFormPage: function (id) {
        console.log("dataId:" + id);
        if (id == undefined || '' == id.trim()) {
            var onSuccess1 =function (resultData) {
                // 添加
                var html = template("groupManagerForm_template");
                $(".groupManager_box").html(html);
                groupManagement_page.action.bindEvent();
                layui.use('tree', function () {
                    layui.tree({
                        elem: '#menuTree_add' //传入元素选择器
                        , nodes: [{
                            "id": resultData.data.peers.id,
                            spread: true,
                            "name": resultData.data.peers.groupName,
                            "menuKey": resultData.data.peers.groupKey,
                            "parentId": resultData.data.peers.parentId,
                            "comment": resultData.data.peers.comment,
                            "orderId": resultData.data.peers.orderId,
                            "target": null,
                            "url": null,
                            "menuIcon": null,
                            "children": resultData.data.menuTree
                        }]
                        , click: function (node) {
                            $("#parentId_add").val(node.name);
                            $("#parentId_add").attr("data-id", node.id);
                            $("#menuTree_add").hide();
                        }
                    });
                });
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                    //规则校验
                    form.verify({
                        groupName:function (value) {
                            var flag;
                            var params = {
                                groupName: value,
                            };
                            var onSuccess = function (resultData) {
                                        flag=resultData.error;
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/group/ajax/isGroupName',
                                'data': params,
                                'async': false,
                                'success': onSuccess
                            });
                            if (flag=="1") {
                                return "组织名称已存在";
                            }
                        }
                    });
                });
            };
                /*return;*/
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/group/ajax/getById',
                    'data': null,
                    'success': onSuccess1
                });
        } else {
            // 编辑
            layer.load();
            var params = {
                id: id
            };
            var onSuccess = function (resultData) {

                layer.closeAll('loading');
                groupManagement_page.data.groupData = resultData.data.group;
                var html = template("groupManagerForm_template", resultData.data.group);
                $(".groupManager_box").html(html);
                layui.use('tree', function () {
                    layui.tree({
                        elem: '#menuTree_add' //传入元素选择器
                        , nodes: [{
                            "id": resultData.data.peers.id,
                            spread: true,
                            "name": resultData.data.peers.groupName,
                            "menuKey": resultData.data.peers.groupKey,
                            "parentId": resultData.data.peers.parentId,
                            "comment": resultData.data.peers.comment,
                            "orderId": resultData.data.peers.orderId,
                            "target": null,
                            "url": null,
                            "menuIcon": null,
                            "children": resultData.data.menuTree
                        }]
                        , click: function (node) {
                            $("#parentId_add").val(node.name);
                            $("#parentId_add").attr("data-id", node.id);
                            $("#menuTree_add").hide();
                        }
                    });
                });
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                    //规则校验
                    form.verify({
                        groupName:function (value) {
                            var flag;
                            var params = {
                                groupName: value,
                                id:id
                            };
                            var onSuccess = function (resultData) {
                                flag=resultData.error;
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/group/ajax/isGroupName',
                                'data': params,
                                'async': false,
                                'success': onSuccess
                            });
                            if (flag=="1") {
                                return "组织名称已存在";
                            }
                        }
                    });
                });
                groupManagement_page.action.bindEvent();
            };
            params.parentName=$("#parentId_add").val();
            params.parentId=$("#parentId_add").attr("data-id");
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/group/ajax/getById',
                'data': params,
                'success': onSuccess
            });
        }

    },
};

groupManagement_page.data = {
    "groupData": {},
};

groupManagement_page.event = {
    'isReaded': false,
    onReady: function () {
        groupManagement_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!groupManagement_page.event.isReaded) {
        groupManagement_page.event.isReaded = true;
        groupManagement_page.event.onReady();
    }
});
