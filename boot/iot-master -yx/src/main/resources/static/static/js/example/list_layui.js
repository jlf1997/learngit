// 包名
var list_layui_page = {};
// 动作类
list_layui_page.action={
    // 总加载
    initBody:function () {
        console.log("aaa")
        list_layui_page.interface.initTablePage();
        list_layui_page.action.bindEvent();
        list_layui_page.interface.initUploader();
    },
    // 页面事件绑定
    bindEvent:function () {
        // 添加按钮事件
        $("#addBtn").click(function () {
            list_layui_page.interface.initFormPage();
            list_layui_page.action.bindEvent();
        });
        // 取消按钮事件
        $("#cancelBtn").click(function () {
            list_layui_page.action.initBody();
        });

    },
};
// 界面类
list_layui_page.interface = {
    //图片上传
    initUploader: function () {
        //需要使用哪一部分的组件就这样写。
        //使用layui的图片上传组件
        layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#chooseBtn'
                ,url: '/frame/files/upload'
                ,multiple: true
                ,auto: false    //关闭自动提交
                ,exts: 'gif|jpg|jpeg|png|bmp|png' //只允许上传压缩文件
                ,size: 2*1024 //限制文件大小，单位 KB
                ,bindAction : "#uploadBtn" //指定提交按钮
                ,choose: function(obj){
                    //将每次选择的文件追加到文件队列
                    var files = obj.pushFile();
                    //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                    obj.preview(function(index, file, result){
                        if(file.size>0 &&  $('#ImgPreview').find('img').length==0){
                            $('#ImgPreview').empty();
                        }
                        $('#ImgPreview').append('<a class="layui-upload-img" id="upload_img_'+index+'"><span><i class="iconfont">&#xe601;</i></span><img src="'+ result +'" alt="'+ file.name +'"></a>');
                        //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                        //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
                        $("#upload_img_"+index).bind('click',function(){
                            delete files[index];////这里用官网介绍的方法，但是图片无法删除，提交后还是有图片内容
                            $(this).remove();
                        });
                    });
                }
                ,done: function(data){//上传完毕 }
                    alert("upload ok");
                }
            });
        });
    },
    // 加载表格页
    initTablePage:function () {
        var html = template("list_layui-template");
        $(".list_layui-box").html(html);

        layui.use(['table','form'], function(){
            var table = layui.table;

            //执行表格渲染
            var tableIns = table.render({
                height:550,
                url:AppCommon.url.getBaseURL()+'/example/ajax/getList_layui',
                elem:"#tb_list",
                page:true,
                limit:10,
                loading:true,
                id:'id',
                cols: [[
                    {
                        field:'id',
                        title:'数据Id',
                        width:200
                    },{
                        field:'title',
                        title:'标题',
                        width:200
                    }
                ]]
            });


            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    title: data.field.titleInput
                };
                tableIns.reload({
                    where: postData
                });
                return false;
            });
        });

    },
    // 加载表单页
    initFormPage:function () {
        var html = template("form_layui-template");
        $(".list_layui-box").html(html);
    },
};
// 数据类
list_layui_page.data={
    'obj'
    :''
};

// 框架事件部分
list_layui_page.event={
    'isReaded':false,
    onReady: function () {
        list_layui_page.action.initBody();
    }
};
// 框架连接部分
AppCommon.action.ready(function () {
    if (!list_layui_page.event.isReaded) {
        list_layui_page.event.isReaded = true;
        list_layui_page.event.onReady();
    }
});


