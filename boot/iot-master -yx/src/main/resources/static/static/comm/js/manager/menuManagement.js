var menuManagement_page = {};

menuManagement_page.action = {
    initBody: function () {
        menuManagement_page.interface.initTablePage();
        menuManagement_page.action.bindEvent();
    },
    bindEvent: function () {
        $("#cancelBtn").click(function () {
            menuManagement_page.action.initBody();
            return false;
        });
        $("#cancelBtn_add").click(function () {
            menuManagement_page.action.initBody();
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
        $("#selectMenu").click(function (){
            if($("#menuTree").is(":hidden")){
                $("#menuTree").show();
            }else{
                $("#menuTree").hide();
            }
            return false;
        })
        $("#selectMenu_sub").click(function (){
            if($("#menuTree_sub").is(":hidden")){
                $("#menuTree_sub").show();
            }else{
                $("#menuTree_sub").hide();
            }
            return false;
        })
        $("#selectIcon").click(function (){
            var menuIconLsit = layer.open({
                type: 2,
                title: '菜单图标',
                shadeClose: true,
                shade: 0.2,
                area: ['80%', '90%'],
                btn: ['确定', '取消']
                ,yes: function(index, layero){
                    var iframeWin = window[layero.find('iframe')[0]['name']];//获取此iframe的window对象
                    var body = layer.getChildFrame('body', index);//获取此iframe的文档结构的body
                    var parent = iframeWin.parent.document;////获取此iframe的父级页面的文档对象
                    var menuinfo = body.find('.bigicon .fa-hover a.on i').attr("class");
                    $(parent).find("#menuIcon").removeClass();
                    $(parent).find("#menuIcon").addClass(menuinfo);
                    $(parent).find("#menuIcon").siblings("span").text(menuinfo);
                    layer.close(menuIconLsit);
                }
                ,btn2: function(index, layero){
                    layer.close(menuIconLsit);
                },
                content: AppCommon.url.getBaseURL()+"/"+"manager/nav/menuIcon"
            });
            return false;
        })
        $("#selectIcon_add").click(function (){
            var menuIconLsit = layer.open({
                type: 2,
                title: '菜单图标',
                shadeClose: true,
                shade: 0.2,
                area: ['80%', '90%'],
                btn: ['确定', '取消']
                ,yes: function(index, layero){
                    var iframeWin = window[layero.find('iframe')[0]['name']];//获取此iframe的window对象
                    var body = layer.getChildFrame('body', index);//获取此iframe的文档结构的body
                    var parent = iframeWin.parent.document;////获取此iframe的父级页面的文档对象
                    var menuinfo = body.find('.bigicon .fa-hover a.on i').attr("class");
                    $(parent).find("#menuIcon_add").removeClass();
                    $(parent).find("#menuIcon_add").addClass(menuinfo);
                    $(parent).find("#menuIcon_add").siblings("span").text(menuinfo);
                    layer.close(menuIconLsit);
                }
                ,btn2: function(index, layero){
                    layer.close(menuIconLsit);
                },
                content: AppCommon.url.getBaseURL()+"/"+"manager/nav/menuIcon"
            });
            return false;
        })
        $("#selectIcon_sub").click(function (){
            var menuIconLsit = layer.open({
                type: 2,
                title: '菜单图标',
                shadeClose: true,
                shade: 0.2,
                area: ['80%', '90%'],
                btn: ['确定', '取消']
                ,yes: function(index, layero){
                    var iframeWin = window[layero.find('iframe')[0]['name']];//获取此iframe的window对象
                    var body = layer.getChildFrame('body', index);//获取此iframe的文档结构的body
                    var parent = iframeWin.parent.document;////获取此iframe的父级页面的文档对象
                    var menuinfo = body.find('.bigicon .fa-hover a.on i').attr("class");
                    $(parent).find("#menuIcon_sub").removeClass();
                    $(parent).find("#menuIcon_sub").addClass(menuinfo);
                    $(parent).find("#menuIcon_sub").siblings("span").text(menuinfo);
                    layer.close(menuIconLsit);
                }
                ,btn2: function(index, layero){
                    layer.close(menuIconLsit);
                },
                content: AppCommon.url.getBaseURL()+"/"+"manager/nav/menuIcon"
            });
            return false;
        })
    },
    deleteMenu: function (id,level) {
        var params = {
            id: id,
            level:level
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                menuManagement_page.interface.initTablePage();
            }else{
                layer.msg(resultData.error);
            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/menuManager/ajax/deleteMenu',
            'data': params,
            'success': onSuccess
        });
    }
};
menuManagement_page.interface = {
    // 加载表格页
    initTablePage: function () {

        var onSuccess = function (resultData) {
            if (resultData.success) {
                var html = template("menuManagementList_template");
                $(".menuManagement_box").html(html);
                var layout = [//https://gitee.com/shaojiepeng/layui-treetable
                    {name: '菜单名称', field: 'name',treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '菜单关键字', field: 'menuKey',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '等级', field: 'level',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '链接', field: 'url',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '打开方式', field: 'target',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '排序号', field: 'orderId',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
                    {name: '说明', field: 'comment',treeNodes: false, headerClass: 'value_col', colClass: 'value_col', style: 'width: 18%'},
                    {
                        name: '操作',
                        headerClass: 'value_col',
                        colClass: 'value_col',
                        style: 'width: 18%',
                        render: function (row) {
                            return '<div class="layui-table-cell laytable-cell-1-operation">'+'<a class="layui-btn layui-btn-xs addSubMenu" data-id='+row.id+'>添加下级菜单</a><a class="layui-btn layui-btn-xs editBtn" data-id='+row.id+'>编辑</a><a class="layui-btn layui-btn-danger layui-btn-xs deleteBtn" data-id='+row.id+' data-level='+row.level+'>删除</a>'+'</div>'; //列渲染
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
                        menuManagement_page.interface.initFormPage($(this).attr("data-id"));
                    });
                    $(".addSubMenu").click(function (){
                        menuManagement_page.interface.initFormPageSub($(this).attr("data-id"));
                        menuManagement_page.action.bindEvent();
                        return false;
                    });
                    $(".deleteBtn").click(function () {
                        var id = $(this).attr("data-id");
                        var level = $(this).attr("data-level");
                        layer.confirm('确定要进行删除吗？',{icon: 3, title:'提示'},function(index){
                            menuManagement_page.action.deleteMenu(id,level);
                            layer.close(index);
                        }, function(index){
                            layer.close(index);
                        });
                    });
                    $("#addBtn").click(function () {
                        menuManagement_page.interface.initFormPage();
                        menuManagement_page.action.bindEvent();
                        return false;
                    });
                });

            }
        };
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/menuManager/ajax/getMenuTableTreeByParentId',
            'data':null,
            'success': onSuccess
        });



    },
    // 初始化表单页
    initFormPage: function (dataId) {


        if (dataId == undefined || '' == dataId.trim()) {
            // 添加
            var onSuccess1 = function (resultData) {
                layer.closeAll('loading');
                var html = template("menuManagementForm_template_add",{});
                $(".menuManagement_box").html(html);
                menuManagement_page.action.bindEvent();
                layui.use('tree', function(){
                    layui.tree({
                        elem: '#menuTree_add' //传入元素选择器
                        ,nodes: [{
                                "id": "0",
                                spread:true,
                                "name": "主菜单",
                                "menuKey": null,
                                "parentId": null,
                                "level": 0,
                                "comment": null,
                                "orderId": null,
                                "target": null,
                                "url": null,
                                "menuIcon": null,
                                "children":resultData.data}]
                        ,click: function(node){
                            $("#parentId_add").val(node.name);
                            $("#parentId_add").attr("data-id",node.id);
                            $("#parentId_add").attr("data-level",node.level);
                            $("#menuTree_add").hide();
                        }
                    });
                });
                layui.use('form', function(){
                    var form = layui.form;
                    form.render();
                    form.verify({
                        menuKey_add:function(value){
                            if((value==""||value==null)){
                                return "菜单关键字不能为空";
                            }
                            var flag = 0;
                            var params={
                                menuKey:value,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if(resultData.data != null && resultData.data.length>0){
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/getMenuListByMenuKey',
                                'data':params,
                                'async':false,
                                'success':onSuccess
                            });
                            if(flag>0){
                                return "菜单关键字不能重复";
                            }
                        },
                    });

                    form.on('submit(saveBtn_add)', function(data){
                        var level = parseInt($("#parentId_add").attr("data-level"))+1;
                        var param={
                            comment:data.field.comment_add,
                            href:data.field.href_add,
                            menuKey:data.field.menuKey_add,
                            menuName:data.field.menuName_add,
                            orderId:data.field.orderId_add,
                            parentId:$("#parentId_add").attr("data-id"),
                            target: data.field.target_add,
                            level:level,
                            menuIcon:$("#menuIcon_add").siblings("span").text()
                        }
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                layer.msg("保存成功！");
                                menuManagement_page.action.initBody();
                            } else {
                                layer.msg("保存失败！");
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/saveMenu',
                            'data':param,
                            'success':onSuccess
                        });
                    });
                });
            };

            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/menuManager/ajax/getMenuTableTreeByParentId',
                'data': null,
                'success': onSuccess1
            });

        } else {
            // 编辑
            layer.load();
            var params={
                id:dataId,
            }
            var onSuccess = function (resultData) {

                layer.closeAll('loading');
                var html = template("menuManagementForm_template", resultData.data);
                $(".menuManagement_box").html(html);
                menuManagement_page.action.bindEvent();
                layui.use('tree', function(){
                    layui.tree({
                        elem: '#menuTree' //传入元素选择器
                        ,spread:true
                        ,nodes: [{
                                "id": "0",
                                spread:true,
                                "name": "主菜单",
                                "menuKey": null,
                                "parentId": null,
                                "level": 0,
                                "comment": null,
                                "orderId": null,
                                "target": null,
                                "url": null,
                                "menuIcon": null,
                                "children":resultData.data.menuTree}]
                        ,click: function(node){
                            $("#parentId").val(node.name);
                            $("#parentId").attr("data-id",node.id);
                            $("#parentId").attr("data-level",node.level);
                            $("#menuTree").hide();
                        }
                    });
                });
                layui.use('form', function(){
                    var form = layui.form;
                    form.render();
                    form.verify({
                        menuKey:function(value){
                            var flag = 0;
                            var params={
                                menuKey:value,
                                id:dataId,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if(resultData.data!=null){
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/getMenuListByMenuKeyOutCurrent',
                                'data':params,
                                'async':false,
                                'success':onSuccess
                            });
                            if(flag>0){
                                return "菜单关键字不能重复";
                            }
                        },
                    });
                    form.on('submit(saveBtn)', function(data){
                        data.field.id=dataId;
                        data.field.level =parseInt($("#parentId").attr("data-level"))+1;
                        data.field.parentId=$("#parentId").attr("data-id");
                        data.field.menuIcon=$("#menuIcon").siblings("span").text()
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                layer.msg("保存成功！");
                                menuManagement_page.action.initBody();
                            } else {
                                layer.msg("保存失败！");
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/saveMenu',
                            'data':data.field,
                            'success':onSuccess
                        });
                    });
                });

            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/menuManager/ajax/getMenuInfoById',
                'data': params,
                'success': onSuccess
            });
        }
    },
    initFormPageSub: function (dataId) {
            layer.load();
            var params = {
                id: dataId
            };
            var onSuccess = function (resultData) {

                layer.closeAll('loading');
                var html = template("menuManagementForm_template_sub", resultData.data);
                $(".menuManagement_box").html(html);
                menuManagement_page.action.bindEvent();
                layui.use('tree', function(){
                    layui.tree({
                        elem: '#menuTree_sub' //传入元素选择器
                        ,spread:true
                        ,nodes: [{
                                "id": "0",
                                spread:true,
                                "name": "主菜单",
                                "menuKey": null,
                                "parentId": null,
                                "level": 0,
                                "comment": null,
                                "orderId": null,
                                "target": null,
                                "url": null,
                                "menuIcon": null,
                                "children":resultData.data.menuTree}]
                        ,click: function(node){
                            $("#parentId_sub").val(node.name);
                            $("#parentId_sub").attr("data-id",node.id);
                            $("#parentId_sub").attr("data-level",node.level);
                            $("#menuTree_sub").hide();
                        }
                    });
                });
                layui.use('form', function(){
                    var form = layui.form;
                    form.render();
                    form.verify({
                        menuKey:function(value){
                            var flag = 0;
                            var params={
                                menuKey:value,
                                id:dataId,
                            };
                            var onSuccess = function (resultData) {
                                if (resultData.success) {
                                    if(resultData.data!=null){
                                        flag++;
                                    }
                                }
                            };
                            AppCommon.ajax.execute({
                                'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/getMenuListByMenuKeyOutCurrent',
                                'data':params,
                                'async':false,
                                'success':onSuccess
                            });
                            if(flag>0){
                                return "菜单关键字不能重复";
                            }
                        },
                    });
                    form.on('submit(saveBtn_sub)', function(data){
                        var param={
                            comment:data.field.comment_sub,
                            href:data.field.href_sub,
                            menuKey:data.field.menuKey_sub,
                            menuName:data.field.menuName_sub,
                            orderId:data.field.orderId_sub,
                            parentId:$("#parentId_sub").attr("data-id"),
                            target: data.field.target_add,
                            level:parseInt($("#parentId_sub").attr("data-level"))+1,
                            menuIcon:$("#menuIcon_sub").siblings("span").text()
                        }
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                layer.msg("保存成功！");
                                menuManagement_page.action.initBody();
                            } else {
                                layer.msg("保存失败！");
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/menuManager/ajax/saveMenu',
                            'data':param,
                            'success':onSuccess
                        });
                    });
                });

            };
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/menuManager/ajax/getMenuInfoById',
                'data': params,
                'success': onSuccess
            });
    },
}

menuManagement_page.data = {
    'tableIns': undefined,
};

menuManagement_page.event = {
    'isReaded': false,
    onReady: function () {
        menuManagement_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!menuManagement_page.event.isReaded) {
        menuManagement_page.event.isReaded = true;
        menuManagement_page.event.onReady();
    }
});