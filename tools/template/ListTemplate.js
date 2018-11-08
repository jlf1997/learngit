var ${templateInfo.model}List = {};

//Action类是一些页面上的动作函数
${templateInfo.model}List.action = {
    //页面初始化函数
    initBody: function () {
        ${templateInfo.model}List.interface.initTablePage();
    },
    /**
     * 事件绑定
     */
    bindEvent: function () {
        $("#addBtn").click(function () {
            ${templateInfo.model}List.action.toFormPage();
            return false;
        });
        $(".close").click(function () {
            this.remove();
        })
    },
    /**
     * 列表页插件初始化
     */
    initPluginsAndEvents:function () {
        ${templateInfo.model}List.action.bindEvent();
    },

    /**
     * 跳转到表单页面
     * @param id
     */
    toFormPage:function (id) {
        if ($.isEmpty(id)){
            window.location.href = AppCommon.url.getBaseURL() + "/${templateInfo.model}/nav/form";
        }else {
            window.location.href = AppCommon.url.getBaseURL() + "/${templateInfo.model}/nav/form?id="+id;
        }
    },
    /**
     * 删除
     * @param id
     */
    delete${tableModel.domainName}: function (id) {
        var params = {
            'id': id
        };
        //注意每一个ajax请求的onSuccess处理方法需要处理请求失败的情况,打印出失败信息
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.msg("删除成功！", {icon: 1});
                ${templateInfo.model}List.data.tableIns.reload({
                    page: {curr: 1}
                });
            }else {
                layer.msg(resultData.error, {icon: 5});
            }
        };
        layer.load();
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + '/${templateInfo.model}/ajax/delete',
            'data': params,
            'success': onSuccess,
            'error':function(){},
        });
    },

};

//interface类是一些页面渲染函数
${templateInfo.model}List.interface ={
    //渲染列表页面
    initTablePage: function () {
        var html = template("${templateInfo.model}List_template");
        $(".${templateInfo.model}List_box").html(html);
        //绑定事件和插件
        ${templateInfo.model}List.action.initPluginsAndEvents();
        //layui列表插件
        layer.load();
        layui.use(['table', 'form'], function () {
            var table = layui.table;
            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/${templateInfo.model}/ajax/selectPage',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    //自动生成需要展示的列start
    <#list tableModel.columnList as columnModel>
            <#if columnModel.columnName != "id"
        && columnModel.columnName != "createBy"
        && columnModel.columnName != "createTime"
        && columnModel.columnName != "updateBy"
        && columnModel.columnName != "updateTime"
        && columnModel.columnName != "delFlag">
                    {
                        field: '${columnModel.columnName}',
                        title: '${columnModel.columnChineseName}',
                        align: 'left'
                    },
            </#if>
     </#list>
                //自动生成需要展示的列end
                    //后台已经默认把createTime和updateTime格式化了,只需要获取createTimeStr和updateTimeStr即可
                    {
                        field: 'createTimeStr',
                        title: '创建时间↓',
                        align: 'left'
                    },
                    {
                        field: 'opration',
                        //width: 140,
                        title: '操作',
                        align: 'center',
                        toolbar: '#toolbar'
                    }
                ]],
                //表格加载完后的回调
                done:function(res, curr, count) {
                    layer.closeAll('loading');
                }
            });
            ${templateInfo.model}List.data.tableIns = tableIns;
            //表格里的按钮的事件监听
            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
                if (layEvent === 'editBtn') {
                    ${templateInfo.model}List.action.toFormPage(rowData.id);
                } else if (layEvent === 'deleteBtn') {
                    layer.confirm('确定要进行删除吗？', {icon: 3, title: '提示'}, function (index) {
                        ${templateInfo.model}List.action.delete${tableModel.domainName}(rowData.id);
                        layer.close(index);
                    }, function (index) {
                        layer.close(index);
                    });
                }
            });
            // 列表查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                //查询参数
                var postData = {
      <#list tableModel.columnList as columnModel>
           <#if columnModel.columnName != "id"
                && columnModel.columnName != "createBy"
                && columnModel.columnName != "createTime"
                && columnModel.columnName != "updateBy"
                && columnModel.columnName != "updateTime"
                && columnModel.columnName != "delFlag"
                && columnModel.columnName?index_of("Id")==-1
                && columnModel_index <= 10>
                ${columnModel.columnName}: $.trim(data.field.search${columnModel.columnNameFirstUpper}),
           </#if>
     </#list>
                };
                //查询完成后跳转到第一页
                tableIns.reload({
                    page: {curr: 1},
                    where: postData
                });
                return false;
            });
        });

    },
};
//页面里的一些全局变量
${templateInfo.model}List.data ={

};
//开始渲染页面
${templateInfo.model}List.event ={
    'isReaded': false,
    onReady: function () {
        ${templateInfo.model}List.action.initBody();
    }
}
//页面加载完后执行
AppCommon.action.ready(function () {
    if (!${templateInfo.model}List.event.isReaded) {
        ${templateInfo.model}List.event.isReaded = true;
        ${templateInfo.model}List.event.onReady();
    }
});