var fileManager_page={};

fileManager_page.action = {
    initBody:function () {
        fileManager_page.interface.initTablePage();
        fileManager_page.action.bindEvent();
    },
    bindEvent:function () {

        $("#cancelBtn").click(function () {
            fileManager_page.action.initBody();
            return false;
        });
    },

    deleteFile:function (fileId) {
        var params = {
            'sysFileIds':fileId
        };
        var onSuccess = function (resultData) {
            if (resultData.success) {
                layer.msg("删除成功！");
                fileManager_page.data.tableIns.reload();
            }
        };
        AppCommon.ajax.execute({
            'url':AppCommon.url.getBaseURL()+'/unitFile/ajax/delete',
            'data':params,
            'success':onSuccess
        });
    }
};
fileManager_page.interface = {
    // 加载表格页
    initTablePage:function () {
        var html = template("fileManagerList_template");
        $(".fileManager_box").html(html);

        layui.use(['table','form'], function(){
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                //height:550,
                url:AppCommon.url.getBaseURL()+'/unitFile/ajax/getList',
                elem:"#tb_list",
                page:true,
                limit:10,
                loading:true,
                id:'id',
                cols: [[
                    {
                        field:'title',
                        title:'文件名',
                        width:200,
                        align:'left'
                    },
                    {
                        field:'sourceUrl',
                        title:'源文件地址',
                        width:200,
                        align:'left'
                    },
                    {
                        field:'ext',
                        title:'文件格式',
                         width:200,
                        align:'left'
                    },
                    {
                        field:'createTimeStr',
                        title:'创建时间',
                        width:200,
                        align:'left'
                    },
                    {
                        field:'opration',
                        title:'操作',
                        fixed:'right',
                        width:120,
                        align:'center',
                        toolbar:'#toolbar'
                    }
                ]]
            });

            fileManager_page.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent==='editBtn') {

                } else if (layEvent==='deleteBtn') {
                    layer.confirm('确定要进行删除吗？',{icon: 3, title:'提示'},function(index){
                        fileManager_page.action.deleteFile(rowData.id);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                    });
                }
            });

            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    title: $.trim(data.field.fullnameInput)
                };
                tableIns.reload({
                	page:{curr:1},
                    where: postData
                });
                return false;
            });
        });

    },
};

fileManager_page.data = {
    'tableIns':undefined
};

fileManager_page.event={
    'isReaded':false,
    onReady: function () {
        fileManager_page.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!fileManager_page.event.isReaded) {
        fileManager_page.event.isReaded = true;
        fileManager_page.event.onReady();
    }
});
