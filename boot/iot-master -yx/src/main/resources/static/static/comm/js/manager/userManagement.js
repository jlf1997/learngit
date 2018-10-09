var userManagement_page = {};

userManagement_page.action = {
    initBody: function () {
        userManagement_page.interface.initTablePage();
        userManagement_page.action.bindEvent();
    },
    bindEvent: function () {
        $("#addBtn").click(function () {
            userManagement_page.interface.initPlugins();
            userManagement_page.interface.initFormPage();
            userManagement_page.action.bindEvent();
            return false;
        });
        $("#saveBtn").click(function () {
        	userManagement_page.action.saveForm();
        });
        $("#cancel").click(function () {
            userManagement_page.action.initBody();
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
    
    saveForm: function () {
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg(resultData.error);
                userManagement_page.action.initBody();
            } else {
                layer.msg(resultData.error);
            }
        };

        layui.use('form', function () {
            var form = layui.form;
            form.render();
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                data.field.fileJson = JSON.stringify(userManagement_page.data.imgUpload.getSingleUploadFile());
                layer.load();
                data.field.groupId=$("#parentId_add").attr("data-id");
                data.field.groupName=$("#parentId_add").val();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/userManager/ajax/saveUser',
                    'data': data.field,
                    'success': onSuccess
                });
                return false;
            });
        });
    },

    deleteUser: function (userId) {
        var params = {
            id: userId
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                userManagement_page.data.tableIns.reload();
            }else{
                layer.msg(resultData.error);
                userManagement_page.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/userManager/ajax/deleteUser',
            'data': params,
            'success': onSuccess
        });
    }
};
userManagement_page.interface = {

    initSelect:function (data) {
    	console.log('initselect');
    	var groupIdSelect = new CommonSelectedSearch("groupIdDiv", "groupId", "/userManager/ajax/getGroupIdData");
        if(data == undefined || data == null){
            //获取全部组织
            groupIdSelect.allDataInit({groupId:null});
        } else {
        	console.log(data.groupId);
        	groupIdSelect.allDataInit({groupId:data.groupId});
        }
    },
    // 加载表格页
    initTablePage: function () {
        var html = template("userManagementList_template");
        $(".userManagement_box").html(html);

        layui.use(['table', 'form'], function () {
            var table = layui.table;
            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/userManager/ajax/getUserManagerList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'fullname',
                        title: '姓名',
                        width:200,
                        align: 'left'
                    },
                    {
                        field: 'username',
                        title: '账号',
                        width: 200,
                        align: 'left'
                    }, {
                        field: 'phone',
                        title: '电话',
                        width: 200,
                        align: 'left'
                    },
                    {
                        field: 'userTypeStr',
                        title: '用户类型',
                        width: 150,
                        align: 'left'
                    },{
                        field: 'operation',
                        title: '操作',
                        align: 'center',
                        width: 250,
                        toolbar: '#toolbar',
                    }
                ]],done: function (res, curr, count) {
                        console.log(isAdmin);
                        if(isAdmin=="true"){
                        $(".reset").show();
                        $("td[data-field='operation']").find("div.layui-table-cell").addClass("laytable-cell-operation");
                     }
                }
            });
            userManagement_page.data.tableIns = tableIns;
            table.on('tool(tb_list)', function (obj) {

                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    layer.load();
                    userManagement_page.interface.initFormPage(rowData.id);
                    userManagement_page.action.bindEvent();
                    // layer.alert(rowData.id+'ddsdfsdfsdfsdds',{icon: 3, title:'提示'});
                }
                if (layEvent === 'perBtn') {
                    layer.load();
                    userManagement_page.interface.initPermissionPage(rowData.id);
                    userManagement_page.action.bindEvent();
                }
                if (layEvent === 'deleteBtn') {
                    if(rowData.username=="admin"){
                        layer.msg("管理员账号不能删除！！",{icon: 2});
                    }else{
                        layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                            userManagement_page.action.deleteUser(rowData.id);
                            layer.close(index);
                        }, function (index) {
                            layer.close(index);
                        });
                    }
                }
                if(layEvent === 'resetBtn'){
                        layer.confirm('确定要重置当前用户密码吗？', {icon: 3, title: '提示'}, function (index) {
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    layer.msg("恭喜重置成功，您重置后的密码是：123456！");
                                    userManagement_page.data.tableIns.reload();
                                    layer.close(index);
                                }else{
                                    layer.msg(resultData.error);
                                    userManagement_page.data.tableIns.reload();
                                    layer.close(index);
                                }
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/userManager/ajax/pwdReset',
                                'data': {username:rowData.username},
                                'success': onSuccess
                            });
                        }, function (index) {
                            layer.close(index);
                        });
                }
                if (layEvent === 'operateBtn') {
                    layer.load();
                    userManagement_page.interface.initOperatePage(rowData.id);
                }
                if (layEvent === 'appPerBtn') {
                    layer.load();
                    userManagement_page.interface.initAppMenuPage(rowData.id);
                }

                if (layEvent === 'showPer') {

                    var param ={
                        id:rowData.id,
                    };
                    var onSuccess = function (resultData) {
                        if (resultData.success) {
                            layer.closeAll('loading');
                            var html = template("userManagerList_per", {});
                            var perLayer = layer.open({
                                type: 1,
                                skin: 'layui-layer-rim', //加上边框
                                area: ['800px', '420px'], //宽高
                                offset: '100px',
                                content: html
                            });
                            var myChart = echarts.init(document.getElementById('treePer'));
                            myChart.showLoading();
                            var data1 = {
                                    "name": "菜单",
                                    "children": resultData.data.menu
                                };
                            // var data2 = {
                            //     "name": "操作",
                            //     "children": resultData.data.permission
                            // };
                            myChart.hideLoading();

                            myChart.setOption(option = {
                                tooltip: {
                                    trigger: 'item',
                                    triggerOn: 'mousemove'
                                },
                                legend: {
                                    top: '2%',
                                    left: '3%',
                                    orient: 'vertical',
                                    data: [{
                                        name: '菜单',
                                        icon: 'rectangle'
                                    } ,
                                        // {
                                        //     name: '操作',
                                        //     icon: 'rectangle'
                                        // }
                                        ],
                                    borderColor: '#c23531'
                                },
                                series:[
                                    {
                                        type: 'tree',

                                        name: '菜单',

                                        data: [data1],

                                        top: '5%',
                                        left: '7%',
                                        bottom: '2%',
                                        right: '60%',

                                        symbolSize: 7,

                                        label: {
                                            normal: {
                                                position: 'left',
                                                verticalAlign: 'middle',
                                                align: 'right'
                                            }
                                        },

                                        leaves: {
                                            label: {
                                                normal: {
                                                    position: 'right',
                                                    verticalAlign: 'middle',
                                                    align: 'left'
                                                }
                                            }
                                        },

                                        expandAndCollapse: true,

                                        animationDuration: 550,
                                        animationDurationUpdate: 750

                                    },
                                    // {
                                    //     type: 'tree',
                                    //     name: '操作',
                                    //     data: [data2],
                                    //
                                    //     top: '20%',
                                    //     left: '60%',
                                    //     bottom: '22%',
                                    //     right: '18%',
                                    //
                                    //     symbolSize: 7,
                                    //
                                    //     label: {
                                    //         normal: {
                                    //             position: 'left',
                                    //             verticalAlign: 'middle',
                                    //             align: 'right'
                                    //         }
                                    //     },
                                    //
                                    //     leaves: {
                                    //         label: {
                                    //             normal: {
                                    //                 position: 'right',
                                    //                 verticalAlign: 'middle',
                                    //                 align: 'left'
                                    //             }
                                    //         }
                                    //     },
                                    //
                                    //     expandAndCollapse: true,
                                    //
                                    //     animationDuration: 550,
                                    //     animationDurationUpdate: 750
                                    // }
                                ]
                            });
                        }
                    }
                    layer.load();
                    AppCommon.ajax.execute({
                        'url': AppCommon.url.getBaseURL() + '/userManager/ajax/getPermissionListByUserId',
                        'data': param,
                        'success': onSuccess
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
                        layer.msg("排序已修改！");
                        userManagement_page.action.initBody();
                    } else {
                        lay.msg("修改失败！");
                    }
                };
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/userManager/ajax/saveUser',
                    'data': param,
                    'success': onSuccess
                });
            });
            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    fullname: data.field.fullnameInput
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
        if (id == undefined || '' == id.trim()) {
            var onSuccess1 =function (resultData) {
                layer.closeAll('loading');

                // 添加
                var html = template("userManagementForm_template");
                $(".userManagement_box").html(html);
                userManagement_page.interface.initSelect();
                userManagement_page.action.bindEvent();
                userManagement_page.interface.initPlugins();
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
                        username: function (value) {
                            if (value == "" || value == null) {
                                return "账号不能为空";
                            }
                            var flag = 0;
                            var params = {
                                userName: value,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if (resultData.data != null) {
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url': AppCommon.url.getBaseURL() + '/userManager/ajax/getUserByUserName',
                                'data': params,
                                'async': false,
                                'success': onSuccess
                            });
                            if (flag > 0) {
                                return "用户名已存在";
                            }
                        },
                    });
                });
                //添加页面显示默认的图片
                $("#test_imgUpload2Preview").attr("src", AppCommon.url.getBaseURL() + "/static/comm/img/defaultHead.jpg");
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
                id: id
            };
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                //获取上传头像的url
                var imgUrl = null;
                if(resultData.data.unitFile != null){
                    imgUrl = resultData.data.unitFile.sourceUrl;
                }
                var html = template("userManagementForm_template", resultData.data);
                $(".userManagement_box").html(html);
                $("#parentId_add").val(resultData.data.group.groupName);
                $("#parentId_add").attr("data-id",resultData.data.group.id);
                userManagement_page.interface.initSelect(resultData.data.user);
                userManagement_page.interface.initPlugins(resultData.data.user.userType);
                //判断头像是否已经上传
                if(imgUrl == null || imgUrl == ""){
                    $("#test_imgUpload2Preview").attr("src",AppCommon.url.getBaseURL()+"/static/comm/img/defaultHead.jpg");
                }
                userManagement_page.action.bindEvent();
                // $("#username_div").remove();
                $("#username").attr("readonly", "true");
                $("#username").attr("disabled", "true");
                $("#username").addClass("layui-disabled");
                $("#username").removeAttr("lay-verify");
                $("#pswd_div").remove();
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
                        username:function(value){
                            var flag = 0;
                            var params={
                                id:resultData.data.id,
                                userName:value,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if(resultData.data!=null){
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url':AppCommon.url.getBaseURL()+'/userManager/ajax/getUserByUserNameOutCurrent',
                                'data':params,
                                'async':false,
                                'success':onSuccess
                            });
                            if(flag>0){
                                return "用户名已存在";
                            }
                        },
                    });
                });
            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/userManager/ajax/getUserById',
                'data': params,
                'success': onSuccess
            });
        }
    },
    //角色授权界面初始化
    initPermissionPage: function (dataId) {
        var param={
            userId:dataId
        }
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            var data = {
                dList:resultData.data,
            }
            //alert(JSON.stringify(data));
            if (resultData.success) {
                var html = template("userManagerList_role", data);
                var roleLayer = layer.open({
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['700px', '420px'], //宽高
                    offset: '100px',
                    content: html
                });
                layui.use('form', function() {
                    var form = layui.form;
                    form.render();
                    form.on('submit(pushRole)', function (data) {
                        var param={
                            id:dataId,
                            ids:JSON.stringify(data.field)
                        }
                        var onSuccess1 = function (resultData) {
                            if (resultData.success) {
                                layer.msg("保存成功！");
                                userManagement_page.action.initBody();
                            } else {
                                layer.msg("保存失败！");
                            }
                        };
                        AppCommon.ajax.execute({
                            'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/saveRolePer',
                            'data': param,
                            'success': onSuccess1
                        });
                    });
                });
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/roleManager/ajax/getRoleListByLoginUser',
            'data': param,
            'success': onSuccess
        });

    },
    //设备授权页面
    initOperatePage: function (dataId) {
        var existParams={
            userId:dataId
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
            layer.closeAll('loading');
            if (resultData.success) {
                for(var i=0;i<resultData.data.length;i++){
                    var opLog = "<button class='close' style='margin:3px;display: inline-block;height: 38px;line-height: 38px;padding: 0 18px;border: 1px solid #C9C9C9;background-color: #fff;color: #555;white-space: nowrap;text-align: center;font-size: 14px;border-radius: 2px;cursor: pointer; -moz-user-select: none; -webkit-user-select: none;'"+"data_id="+resultData.data[i].id+">"+resultData.data[i].name+"<span class='layui-badge' >x</span></button>"
                    $("#opLog").append(opLog);
                    userManagement_page.action.bindEventClose();
                }
            }
        }
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/device/ajax/getUserDevices',
            'data':existParams,
            'success':onSuccessExist
        });
        layui.use(['table', 'form'], function () {
            var table = layui.table;

            //执行表格渲染
            var operationTable = table.render({
                elem: "#op_list",
                url:AppCommon.url.getBaseURL()+'/device/ajax/getList',
                page: {
                    layout: ['count', 'prev', 'page', 'next', 'skip']
                },
                limit: 10,
                loading: true,
                id: 'op_list',
                cols: [[
                    // {
                    //     field: 'id',
                    //     title: 'id',
                    //     width: 200,
                    //     align: 'left',
                    //     event: 'setSign',
                    // },
                    {
                        field: 'name',
                        title: '设备名称',
                        width: 400,
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
                        var opLog = "<button class='close' style='margin:3px;display: inline-block;height: 38px;line-height: 38px;padding: 0 18px;border: 1px solid #C9C9C9;background-color: #fff;color: #555;white-space: nowrap;text-align: center;font-size: 14px;border-radius: 2px;cursor: pointer; -moz-user-select: none; -webkit-user-select: none;'"+"data_id="+rowData.id+">"+rowData.name+"<span class='layui-badge' >x</span></button>"
                        $("#opLog").append(opLog);
                        userManagement_page.action.bindEventClose();
                    }
                    if(flag>0){
                        layer.msg('不能重复添加', {
                            time: 2000, //2s后自动关闭
                        });
                    }
                }

            });
            userManagement_page.data.operationTable = operationTable;
            // 查询
            var form = layui.form;
            form.render(null, 'searchOpForm');
            form.on('submit(searchOp)', function (data) {
                var postData = {
                    deviceName: data.field.name
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
                    userId:dataId,
                    deviceIds:ids
                }
                var onSuccess = function (resultData) {
                    if (resultData.success) {
                        layer.close(opationLayer);
                        layer.msg("配置成功！");
                    }
                };
                AppCommon.ajax.execute({
                    'url':AppCommon.url.getBaseURL()+'/device/ajax/saveUserDevices',
                    'data':params,
                    'success':onSuccess
                });
                return false;
            });
        });
    },
// 初始化相关插件
    initPlugins: function(userType){
        //默认回显普通用户
        var u = 1;
        if(!$.isEmpty(userType)){
            u = userType;
        }
        //初始化图片上传插件
        var imgUpload = new CommonImageUpload("test_imgUpload2");
        userManagement_page.data.imgUpload = imgUpload;
        imgUpload.singleImageUploadInit(null);
        var typeSelect = new CommonSelectedSearch("typeDiv", "userType", '/dict/ajax/getDictData');
        //请求下拉连接传的参数type:user_type
        typeSelect.allDataInit({type:"user_type",value:u});
    },
    //初始化AA菜单授权页
    initAppMenuPage: function (dataId) {
        var ztreeLoad = layer.load();
        var params = {
            userId: dataId,
        }

        var html = template("roleManagerForm_ztree", {});
        var roleAuthLayer = layer.open({
            type: 1,
            area: ['700px', '420px'], //宽高
            offset: '100px',
            btn: ['提交', '取消'], //可以无限个按钮
            yes: function(index, layero){
                var nodesArray = userManagement_page.data.ztreeChecked.getCheckedNodes();
                var menuArray ="";
                for(var i=0;i<nodesArray.length;i++){
                    menuArray += nodesArray[i].id+",";
                }
                var datas ={
                    "id":dataId,
                    "menuIds":menuArray
                }

                var onSuccess = function (resultData) {
                    if(resultData.success){
                        layer.msg("保存成功")
                        layer.close(roleAuthLayer);
                    }
                };

                AppCommon.ajax.execute({
                    'url':AppCommon.url.getBaseURL()+'/appMenu/ajax/saveAppMenu',
                    'data':datas,
                    'success':onSuccess
                });

            },
            btnAlign: 'c',
            content: html
        });

        $("#dataId").val(dataId);
        var ztree = new CommonZtree('treeDemo', '/appMenu/ajax/getMenuZtree', params);
        ztree.setZtreeCheck(true);
        ztree.ztreeInit();
        setting = {
            callback: {
                onAsyncSuccess: zTreeOnAsyncSuccess
            }
        };
        var firstAsyncSuccessFlag = 0;
        function zTreeOnAsyncSuccess(event, treeId, msg) {
            layer.closeAll('loading');
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
        userManagement_page.data.ztreeChecked = ztree;
        layer.close(ztreeLoad);
    },
}
userManagement_page.data = {
    'tableIns': undefined,
    roleTable:null,
    imgUpload:null,
};

userManagement_page.event = {
    'isReaded': false,
    onReady: function () {
        userManagement_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!userManagement_page.event.isReaded) {
        userManagement_page.event.isReaded = true;
        userManagement_page.event.onReady();
    }
});