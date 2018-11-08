var ${templateInfo.model}Form = {};

//Action类是一些页面上的动作函数
${templateInfo.model}Form.action = {
    //页面初始化函数
    initBody: function () {
        ${templateInfo.model}Form.interface.initFormPage(${templateInfo.model}Form_id);
    },
    /**
     * 事件绑定
     */
    bindEvent: function () {
        $("#saveBtn").click(function () {
            ${templateInfo.model}Form.action.saveForm();
        });
        $("#cancelBtn").click(function () {
            ${templateInfo.model}Form.action.toListPage();
        });
        $(".close").click(function () {
            this.remove();
        })
    },
    /**
     * 表单页插件和事件初始化
     */
    initPluginsAndEvents:function () {
        ${templateInfo.model}Form.action.bindEvent();
    },
    /**
     * 表单提交方法
     */
    saveForm: function () {
        //注意每一个ajax请求的onSuccess处理方法需要处理请求失败的情况,打印出失败信息
        var onSuccess = function (resultData) {
            layer.closeAll('loading');
            if (resultData.success) {
                layer.open({
                    title:"信息",
                    icon: 1,
                    content: resultData.error,
                    yes: function(index, layero){
                        //do something
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                        ${templateInfo.model}Form.action.toListPage();
                    }
                });
            } else {
                layer.msg(resultData.error, {icon: 5});
            }
        };
        layui.use('form', function () {
            var form = layui.form;
            form.render();
            //监听提交
            form.on('submit(saveBtn1)', function (data) {
                layer.load();
                AppCommon.ajax.execute({
                    'url': AppCommon.url.getBaseURL() + '/${templateInfo.model}/ajax/save',
                    'data': ${templateInfo.model}Form.action.beforeSubmit(data.field),
                    'success': onSuccess,
                    'error':function(){},
                });
            });
        });
    },
    /**
     * 跳转到列表页
     */
    toListPage:function () {
        window.location.href = AppCommon.url.getBaseURL() + "/${templateInfo.model}/nav/list";
    },
    /**
     * 提交前对数据进行一些处理
     * @param data
     */
    beforeSubmit:function (data) {
        console.log(data);
        if(!$.isEmpty(data)){
            for(var k in data){
                //字符串类型数据去除首尾空格
                if( typeof(data[k]) == "string"){
                    data[k] = data[k].trim();
                }
            }
        }
        return data;
    }
};

//interface类是一些页面渲染函数
${templateInfo.model}Form.interface ={
    //初始化表单页面
    initFormPage: function (id) {
        if ($.isEmpty(id)) {
            // 添加页面
            var html = template("${templateInfo.model}Form_template");
            $(".${templateInfo.model}Form_box").html(html);
            //插件初始化和事件绑定
            ${templateInfo.model}Form.action.initPluginsAndEvents();
        } else {
            // 编辑页面
            var params = {
                id: id
            };
            //注意每一个ajax请求的onSuccess处理方法需要处理请求失败的情况,打印出失败信息
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                if(resultData.success){
                    ${templateInfo.model}Form.data.${templateInfo.model}Data = resultData.data;
                    var html = template("${templateInfo.model}Form_template", resultData.data);
                    $(".${templateInfo.model}Form_box").html(html);
                    //插件初始化和事件绑定
                    ${templateInfo.model}Form.action.initPluginsAndEvents();
                }else {
                    //出现异常,返回列表页
                    layer.open({
                        title:"错误",
                        content: resultData.error,
                        icon: 5,
                        yes: function(index, layero){
                            //如果设定了yes回调，需进行手工关闭
                            layer.close(index);
                            //返回列表页
                            ${templateInfo.model}Form.action.toListPage();
                        }
                    });
                }
            };
            layer.load();
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + '/${templateInfo.model}/ajax/select${tableModel.domainName}Info',
                'data': params,
                'success': onSuccess,
                'error':function(){},
            });
        }

    },

};

//页面里的一些全局变量
${templateInfo.model}Form.data ={
    ${templateInfo.model}Data: {},
};
//开始渲染页面
${templateInfo.model}Form.event ={
    'isReaded': false,
    onReady: function () {
        ${templateInfo.model}Form.action.initBody();
    }
}
//页面加载完后执行
AppCommon.action.ready(function () {
    if (!${templateInfo.model}Form.event.isReaded) {
        ${templateInfo.model}Form.event.isReaded = true;
        ${templateInfo.model}Form.event.onReady();
    }
});