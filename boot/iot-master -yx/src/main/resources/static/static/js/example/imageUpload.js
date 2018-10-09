var imageUpload = {};

imageUpload.action = {
    initBody: function () {
        imageUpload.interface.initHtml();
        imageUpload.action.bindEvent();
    },

    bindEvent: function () {
        $("#test_imgUpload2BindAction").click(function () {
            console.log(JSON.stringify(imageUpload.data.imgUpload2.getSingleUploadFile()));
            var files =imageUpload.data.imgUpload2.getSingleUploadFile();
            if(files.length <= 0){
                layer.alert("请先选择图片");
                return;
            }
            var param = {
                'fileJson':JSON.stringify(files)
            }

            layer.load();
            var onSuccess = function (resultData) {
                layer.closeAll('loading');
                if (resultData.success) {
                    imageUpload.data.imgUpload2.clear();
                    $("#test_imgUpload2Preview").attr("src","");
                    $("#test_imgUpload2FileName").text("");
                    layer.msg("保存成功！");
                } else {
                    layer.msg("保存失败！");
                }
            };
            AppCommon.ajax.execute({
                'url':AppCommon.url.getBaseURL()+'/unitFile/ajax/save',
                'data':param,
                'success':onSuccess
            });
        });
    }
};

imageUpload.interface = {
    initHtml: function () {
        //单图和多图最简单的初始化形式，都采用默认的配置
        var imgUpload = new CommonImageUpload("test_imgUpload");
        // var myOptions = {
        //     'size':100
        // }
        // var myMultiPrview = function(obj){
        //     var viewId = "#test_imgUploadPreview";
        //     if (obj.file.size > 0 && $(viewId).find('img').length == 0) {
        //         $(viewId).empty();
        //     }
        //     var imgId = "upload_img_" + obj.index;
        //     $(viewId).append('<li id="'+imgId+'"><a href="#" ><img src="'+obj.result+'" /><span>'+obj.file.name+'</span><div id="delete_img_btn_'+obj.index+'">重写删除11</div></a></li>');
        //     //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
        //     //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
        //     $("#delete_img_btn_" + obj.index).bind('click', function () {
        //         delete obj.files[obj.index];
        //         $("#upload_img_"+obj.index).remove();
        //     });
        // };
        imgUpload.multiImageUploadInit(null,null);
        imageUpload.data.imgUpload = imgUpload;

        // var mySinglePrview = function(obj){
        //     var viewId = "#test_imgUpload2Preview";
        //     var txtId = "#test_imgUpload2FileName";
        //     $(viewId).attr("src",obj.result);
        //     $(txtId).text("我是自定义回调函数,"+obj.file.name);
        // }

        // var myOptions = {
        //     'size':100
        // }
        var imgUpload2 = new CommonImageUpload("test_imgUpload2");
        imageUpload.data.imgUpload2 = imgUpload2;
        imgUpload2.singleImageUploadInit(null,null);
    },

};

imageUpload.event = {
    'isReaded': false,
    onReady: function () {
        imageUpload.action.initBody();
    }
};

imageUpload.data = {
    "imgUpload" : {},
    "imgUpload2":{}
}

AppCommon.action.ready(function () {
    if (!imageUpload.event.isReaded) {
        imageUpload.event.isReaded = true;
        imageUpload.event.onReady();
    }
});