var roleManagement_page={};

roleManagement_page.action = {
    initBody:function () {
        roleManagement_page.interface.initTablePage();
        roleManagement_page.action.bindEvent();
    },
    bindEvent:function () {
        $("#addBtn").click(function () {
            roleManagement_page.interface.initFormPage();
            roleManagement_page.action.bindEvent();
            return false;
        });
        $("#saveBtnMenu").click(function () {
            roleManagement_page.action.saveMenuForm();
        });
        $("#saveBtn").click(function () {
            roleManagement_page.action.saveForm();
        });
        $("#cancelBtn").click(function () {
            roleManagement_page.action.initBody();
            return false;
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

    bindEventClose:function () {
        $(".close").click(function(){
            this.remove();
        })
    },
    
    //保存角色
    saveForm: function () {
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                roleManagement_page.action.initBody();
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
                data.field.groupId=$("#parentId_add").attr("data-id");
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/saveRole',
                    'data': data.field,
                    'success': onSuccess
                });
                return false;
            });
        });
    },
    
    //保存配置菜单权限
    saveMenuForm:function () {
        var dataId = $("#dataId").val();
        var nodesArray = roleManagement_page.data.ztreeChecked.getCheckedNodes();
        var menuArray = new Array();
        for(var i=0;i<nodesArray.length;i++){
            menuArray.push(nodesArray[i].id);
        }
        var menuIds = JSON.stringify(menuArray);
        var params={
            id:dataId,
            menuIds:menuIds
        }
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("保存成功！");
            } else {
                layer.msg("保存失败！");
            }
        };
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/roleManager/ajax/saveMenuPer',
            'data':params,
            'success':onSuccess
        });
    },

    deleteRole:function (roleId) {
        var params = {
            id:roleId
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                roleManagement_page.data.tableIns.reload();
            }else{
                layer.msg(resultData.error);
                roleManagement_page.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/roleManager/ajax/deleteRole',
            'data':params,
            'success':onSuccess
        });
    }
};
roleManagement_page.interface = {
	//初始化选择框
	initSelect:function (groupId) {
    	//console.log('initselect');
    	var groupIdSelect = new CommonSelectedSearch("groupIdDiv", "groupId", "/userManager/ajax/getGroupIdData");
    	groupIdSelect.allDataInit({groupId:groupId});
    },
    
    // 加载表格页
    initTablePage: function () {
        var html = template("roleManagerList_template");
        $(".roleManager_box").html(html);
        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/roleManager/ajax/getRoleManagerList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'roleKey',
                        title: '角色关键字',
                        align:'left',
                        width: 140,
                    },
                    {
                        field: 'roleName',
                        title: '角色名称',
                        width: 150,
                        align:'left',
                    },
                    {
                    	field: 'groupName',
                    	title: '组织名称',
                    	width: 200,
                    	align:'left',
                    },
                    {
                        field:'orderId',
                        title:'排序号',
                        edit: 'text',
                        width: 80,
                        align:'left',
                    },
                    {
                        field: 'comment',
                        title: '说明',
                        align:'left',
                        width: 200,
                    },
                    {
                        title: '操作',
                        fixed: 'right',
                        width: 250,
                        align: 'center',
                        toolbar: '#toolbar',
                        fixed: 'right'
                    }
                ]]
            });

            roleManagement_page.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    roleManagement_page.interface.initFormPage(rowData.id);
                }
                if (layEvent === 'menuBtn') {
                    roleManagement_page.interface.initMenuPage(rowData.id);
                    roleManagement_page.action.bindEvent();
                }
                if (layEvent === 'operateBtn') {
                    roleManagement_page.interface.initOperatePage(rowData.id);
                }
                if (layEvent === 'deleteBtn') {
                    if(rowData.roleKey=="admin"){
                        layer.msg("系统管理员角色不能删除！！",{icon: 2});
                    }else{
                        layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                            roleManagement_page.action.deleteRole(rowData.id);
                            layer.close(index);
                        }, function (index) {
                            layer.close(index);
                        });
                    }
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
                        roleManagement_page.action.initBody();
                    } else {
                        lay.msg("修改失败！");
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/saveRole',
                    'data': param,
                    'success': onSuccess
                });
            });
            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    roleName: data.field.nameInput
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
    initFormPage: function (dataId) {
        if (dataId == undefined || '' == dataId.trim()) {
            var onSuccess1 =function (resultData) {
                // 添加
                var html = template("roleManagerForm_template");
                $(".roleManager_box").html(html);
                roleManagement_page.interface.initSelect();
                roleManagement_page.action.bindEvent();
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
                    form.verify({
                        roleKey: function (value) {
                            var flag = 0;
                            var params = {
                                roleKey: value,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if (resultData.data != null) {
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/getRoleListByKey',
                                'data': params,
                                'async': false,
                                'success': onSuccess
                            });
                            if (flag > 0) {
                                return "角色关键字已存在";
                            }

                        },
                        orderId: function (value) {
                            if (!$.isNumber(value)) {
                                return '排序号必须为数字';
                            }
                            if ($.isEmpty(value)) {
                                return '排序号不能为空';
                            }
                        }
                    });
                });
            }
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/group/ajax/getById',
                'data': null,
                'success': onSuccess1
            });
        } else {
            // 编辑
            layer.load();
            var params = {
                id: dataId
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                var html = template("roleManagerForm_template", resultData.data.role);
                $(".roleManager_box").html(html);
                roleManagement_page.interface.initSelect(resultData.data.role.groupId);
                roleManagement_page.action.bindEvent();
                //$("#roleKey_div").hide();
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
                layui.use('form', function(){
                    var form = layui.form;
                    form.verify({
                        roleKey: function (value) {
                            var flag = 0;
                            var params = {
                                roleKey: value,
                                id:dataId
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if (resultData.data != null) {
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/getRoleListByKey',
                                'data': params,
                                'async': false,
                                'success': onSuccess
                            });
                            if (flag > 0) {
                                return "角色关键字已存在";
                            }

                        },
                        orderId:function(value){
                            if(!$.isNumber(value)){
                                return '排序号必须为数字';
                            }
                            if($.isEmpty(value)){
                                return '排序号不能为空';
                            }
                        }
                    });
                });
            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/getRoleById',
                'data': params,
                'success': onSuccess
            });
        }
    },
    //初始化菜单授权页
    initMenuPage: function (dataId) {
        var ztreeLoad = layer.load();
        var params = {
            id: dataId,
        }

        var html = template("roleManagerForm_ztree", {});
        var roleAuthLayer = layer.open({
            type: 1,
            area: ['700px', '420px'], //宽高
            btn: ['提交', '取消'], //可以无限个按钮
            yes: function(index, layero){
                layer.close(roleAuthLayer);
                roleManagement_page.action.saveMenuForm();
            },
            btnAlign: 'c',
            content: html
        });

        $("#dataId").val(dataId);
        var ztree = new CommonZtree('treeDemo', '/roleManager/ajax/getMenuZtree', params);
        ztree.setZtreeCheck(true);
        ztree.ztreeInit();
        setting = {
            callback: {
            onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
        var firstAsyncSuccessFlag = 0;
        function zTreeOnAsyncSuccess(event, treeId, msg) {
            if (firstAsyncSuccessFlag == 0) {
                try {
                    //调用默认展开第一个结点
                    var selectedNode = zTree.getSelectedNodes();
                    var nodes = zTree.getNodes();
                    zTree.expandNode(nodes[0], true);

                    var childNodes = zTree.transformToArray(nodes[0]);
                    zTree.expandNode(childNodes[1], true);
                    zTree.selectNode(childNodes[1]);
                    var childNodes1 = zTree.transformToArray(childNodes[1]);
                    zTree.checkNode(childNodes1[1], true, true);
                    firstAsyncSuccessFlag = 1;
                } catch (err) {

                }

            }
        }
        roleManagement_page.data.ztreeChecked = ztree;
        layer.close(ztreeLoad);
    },

    //初始化操作授权页
    initOperatePage: function (dataId) {
        var existParams={
            id:dataId
        }
        var html = template("roleManagerList_operation", {});
        var opationLayer = layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['700px', '420px'], //宽高
            offset: '100px',
            content: html
        });
        var onSuccessExist = function (resultData) {
            if (resultData.success) {
                for(var i=0;i<resultData.data.length;i++){
                    var opLog = "<button class='close' style='margin:3px;display: inline-block;height: 38px;line-height: 38px;padding: 0 18px;border: 1px solid #C9C9C9;background-color: #fff;color: #555;white-space: nowrap;text-align: center;font-size: 14px;border-radius: 2px;cursor: pointer; -moz-user-select: none; -webkit-user-select: none;'"+"data_id="+resultData.data[i].id+">"+resultData.data[i].operationName+"<span class='layui-badge' >x</span></button>"
                    $("#opLog").append(opLog);
                    roleManagement_page.action.bindEventClose();
                }
            }
        }
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationByRoleId',
            'data':existParams,
            'success':onSuccessExist
        });
        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var operationTable = table.render({
                elem: "#op_list",
                url:AppCommon.url.getBaseURL()+'/operationManager/ajax/getOperationList',
                page: {
                    layout: ['count', 'prev', 'page', 'next', 'skip']
                },
                limit: 10,
                loading: true,
                id: 'op_list',
                cols: [[
                    {
                        field: 'operationName',
                        title: '操作名称',
                        width: 200,
                        align: 'left',
                        event: 'setSign',
                    },
                    {
                        field: 'operationKey',
                        title: '操作关键字',
                        width: 200,
                        align: 'left',
                        event: 'setSign',
                    },
                     {
                        field: 'comment',
                        title: '操作说明',
                        width: 200,
                        align: 'left',
                        event: 'setSign',
                    }
                ]]
            });

            table.on('tool(op_list)', function (obj) {
                var rowData = obj.data;
                var layEvent = obj.event;
                if(obj.event === 'setSign'){
                    var flag=0;
                    $("#opLog button").each(function(index,item) {
                        if($(item).attr("data_id")==rowData.id){
                            flag++
                        }
                    })
                    if(flag==0){
                        var opLog = "<button class='close' style='margin:3px;display: inline-block;height: 38px;line-height: 38px;padding: 0 18px;border: 1px solid #C9C9C9;background-color: #fff;color: #555;white-space: nowrap;text-align: center;font-size: 14px;border-radius: 2px;cursor: pointer; -moz-user-select: none; -webkit-user-select: none;'"+"data_id="+rowData.id+">"+rowData.operationName+"<span class='layui-badge' >x</span></button>"
                        $("#opLog").append(opLog);
                        roleManagement_page.action.bindEventClose();
                    }
                    if(flag>0){
                        layer.msg('不能重复添加', {
                            time: 2000, //2s后自动关闭
                        });
                    }
                }

            });
            roleManagement_page.data.operationTable = operationTable;
            // 查询
            var form = layui.form;
            form.render(null, 'searchOpForm');
            form.on('submit(searchOp)', function (data) {
                var postData = {
                    operationName: data.field.operationName
                };
                operationTable.reload({
                    where: postData
                });
                return false;
            });
            $("#getCheckData").click(function () {
                var operD = new Array();
                $("#opLog button").each(function(index,item) {
                    operD.push($(item).attr("data_id"));
                })
                var ids = operD.join(",");
                var params= {
                    id:dataId,
                    ids:ids
                }
                var onSuccess = function (resultData) {
                    if (resultData.success) {
                        layer.close(opationLayer);
                        layer.msg("配置成功！");
                    }
                };
                AppCommon.ajax.execute({
                    'url':AppCommon.url.getBaseURL()+'/roleManager/ajax/saveOpationPer',
                    'data':params,
                    'success':onSuccess
                });
                return false;
            });
        });
    }

}

roleManagement_page.data = {
    'tableIns':undefined,
    ztreeChecked:null,
    operationTable:null,
    tableData:[]
};

roleManagement_page.event={
    'isReaded':false,
    onReady: function () {
        roleManagement_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!roleManagement_page.event.isReaded) {
        roleManagement_page.event.isReaded = true;
        roleManagement_page.event.onReady();
    }
});