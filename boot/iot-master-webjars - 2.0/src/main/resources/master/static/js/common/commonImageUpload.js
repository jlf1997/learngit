
;
/**
 * layUI图片上传
 * @author:suhuanzhao
 * @date: 2017/12/21 11:46
 **/
var CommonImageUpload = function (ele) {
    //默认的参数,私有变量
    var defaultOpts = {
        'url': AppCommon.url.getBaseURL()+"/files/upload",//上传路径
        'accept': 'images',//接受的文件类型
        'exts': 'gif|jpg|jpeg|png|bmp|png',//接受的文件格式
        'multiple': true,//多选
        'auto': false,//是否自动上传
        'size': 5 * 1024,//单张文件大小
        'bindAction': '#' + ele + "BindAction",
        'defaultPreview': '#' + ele + "Preview",//单图片上传的时候图片预览的dom
        'defaultFileName' : '#'+ele+"FileName",//单图片上传的时候图片下方的文件名dom
        'number':5,//一次上传的文件数量,
        'repeat':false//是否去重判断
    };
    //缓存已经上传的文件对象（layerUI默认的文件对象）layerUi文件上传队列
    var images = {};
    var thisEle = this;
    //合并用户的自定义参数
    var multiOptions = {};
    var singleOptions = {};
    //
    //已经上传的文件对象，上传成功后，后台返回的文件对象(FileDto对象数组)
    var uploadFiles = [];
    //单文件上传的时候，只存一张图片
    var singleUploadFile = [];
    //外部获取已经上传的文件对象
    this.getFiles = function () {
        return uploadFiles;
    }

    this.getSingleUploadFile = function(){
        return singleUploadFile;
    }

    //文件上传成功后，把获取到的文件对象缓存到uploadFiles
    var addFiles = function (data) {
        for(var i= 0;i<data.length;i++){
            //处理value为null的key
            for(var key in data[i]){
                if(data[i][key] == null){
                    data[i][key] = "";
                }
            }
            uploadFiles.push(data[i]);
        }
    }

    //默认的多选预览函数
    var defaultMultiPrview = function defaultMultiPrview(index, file, result,files){
        var viewId = multiOptions.defaultPreview;
        if (file.size > 0 && $(viewId).find('img').length == 0) {
            $(viewId).empty();
        }
        images = files;
        //文件去重
        if(!multiOptions.repeat){
            //还未上传文件检查重复
            var count = 0;
            for (var i in files) {
                if(file.name == files[i].name ){
                    count++;
                }
            }
            if(count > 1){
                layer.alert("不允许上传相同的文件!");
                delete files[index];
                images = files;
                return false;
            }
            //已上传文件检查重复
            for(var i=0;i<uploadFiles.length;i++){
                if(file.name == uploadFiles[i].originalName){
                    layer.alert("不允许上传相同的文件!");
                    delete files[index];
                    images = files;
                    return false;
                }
            }
        }
        //按钮可编辑
        $(multiOptions.bindAction).removeClass("layui-btn-disabled");
        $(multiOptions.bindAction).removeAttr('disabled');
        var imgId = "upload_img_" + index;
        $(viewId).append('<li id="'+imgId+'"><a href="#" ><img title="'+file.name+'" src="'+result+'" /><span title="'+file.name+'" style="width:160px;overflow : hidden;white-space : nowrap;text-overflow: ellipsis;">'+file.name+'</span><div  id="delete_img_btn_'+index+'">删除</div></a></li>');
        //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
        //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
        $("#delete_img_btn_" + index).bind('click', function () {
            console.log(index+"被删除");
            deleteFilesByIndex(index);
            delete files[index];
            images = files;
            // console.log("After delete uploadFiles:");
            // console.log(uploadFiles);
            $("#upload_img_"+index).remove();
        });
        return true;
    }

    //默认的单选预览函数
    var defaultSinglePrview = function(index,file, result){
        var viewId = singleOptions.defaultPreview;
        var txtId = singleOptions.defaultFileName;
        // console.log(singleUploadFile);
        if(singleUploadFile.length > 0){
            if (file.name == singleUploadFile[0].originalName) {
                layer.alert("该文件已上传");
                console.log(index);
                delete images[index];
                console.log(images);
                return;
            }
        }
        // $(singleOptions.bindAction).removeClass("layui-btn-disabled");
        // $(singleOptions.bindAction).removeAttr('disabled');
        $(viewId).attr("src",result);
        $(viewId).attr("title",file.name);
        $(txtId).attr("title",file.name);
        $(txtId).text(file.name);
    }

    //多选图片初始化函数
    this.multiImageUploadInit = function (previewfunc,multiParams) {
        multiOptions = $.extend({}, defaultOpts,multiParams);
        //合并用户自定义的预览函数
        console.log('初始化多图片上传插件');
        layui.use('upload', function () {
            var upload = layui.upload;
            //执行实例
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#' + ele
                , url: multiOptions.url
                , multiple: multiOptions.multiple
                , auto: multiOptions.auto    //关闭自动提交
                , exts: multiOptions.exts //只允许上传文件
                , size: multiOptions.size //限制文件大小，单位 KB
                , number:multiOptions.number
                , bindAction: multiOptions.bindAction //指定提交按钮
                , before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    console.log("执行m-before动作");
                    // multiChoose(obj,thisEle,previewfunc);
                    var length = getObjKeyLength(images);
                    if (length == null || length <= 0){
                        // layer.alert("请先选择图片");
                        return;
                    }
                    layer.load(); //上传loading
                }
                ,done : function(res, index, upload){
                    console.log("执行m-done动作");
                    res.data[0].index = index;
                    console.log(res.data);
                    console.log(index+"已经上传成功");
                    if (res.success == true) {
                        addFiles(res.data);
                    }
                    delete images[index];
                }
                , choose: function (obj) {
                    console.log("执行m-choose动作");
                    // console.log(obj);
                    multiChoose(obj,thisEle,previewfunc);


                }
                , allDone: function (obj) { //当文件全部被提交后，才触发
                    console.log("执行m-allDone动作");
                    $(multiOptions.bindAction).addClass("layui-btn-disabled");
                    $(multiOptions.bindAction).attr('disabled',"true");
                    // console.log(obj);
                    // console.log("得到的files：");
                    // console.log(uploadFiles);
                    layer.closeAll('loading'); //关闭loading
                    // console.log(obj.total); //得到总文件数
                    // console.log(obj.successful); //请求成功的文件数
                    // console.log(obj.aborted); //请求失败的文件数
                }
                ,error: function(index, upload){
                    layer.closeAll('loading'); //关闭loading
                }
            });
        });
    };

    //单选图片初始化函数
    this.singleImageUploadInit = function(previewfunc,singleParams){
        singleOptions = $.extend({}, defaultOpts,singleParams);
        singleOptions.auto = true;
        console.log('初始化单图片上传插件');
        layui.use('upload', function () {
            var upload = layui.upload;
            //执行实例
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#' + ele
                , url: singleOptions.url
                , auto: singleOptions.auto     //关闭自动提交
                , exts: singleOptions.exts //只允许上传的文件类型
                , size: singleOptions.size //限制文件大小，单位 KB
                // , bindAction: singleOptions.bindAction //指定提交按钮
                , before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    console.log("执行before动作");
                    singleChoose(obj,previewfunc);
                    var length = getObjKeyLength(images);
                    if (length == null || length <= 0){
                        // layer.alert("请先选择图片");
                        return;
                    }
                    layer.load(); //上传loading
                }
                , choose: function (obj) {
                    console.log("执行choose动作");
                    // singleChoose(obj,previewfunc);
                }
                , done: function (res, index, upload) { //当文件全部被提交后，才触发
                    console.log("执行done动作");
                    if(res.success == true){
                        // addFiles(res.data);
                        var data = res.data;
                        for(var i= 0;i<data.length;i++) {
                            for (var key in data[i]) {
                                if (data[i][key] == null) {
                                    data[i][key] = "";
                                }
                            }
                        }
                        singleUploadFile = data;
                        console.log(singleUploadFile);
                        // $(singleOptions.bindAction).addClass("layui-btn-disabled");
                        // $(singleOptions.bindAction).attr('disabled',"true");
                    }
                    layer.closeAll('loading'); //关闭loading
                    delete images[index];
                }
                ,error: function(index, upload){
                    console.log("执行error动作");
                    layer.closeAll('loading'); //关闭loading
                }
            });
        });
    }

    //多选图片的默认choose回调函数
    var multiChoose = function (obj,thisEle,callBack) {
        //将每次选择的文件追加到文件队列
        var files = obj.pushFile();
        images = files;
        //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
        obj.preview(function (index, file, result) {
            if(callBack != null && typeof callBack == "function"){
                console.log("=====执行用户自定义的回调函数=====");
                var params = {};
                params.index = index;
                params.file = file;
                params.result = result;
                params.files = files;
                params.multiOptions = multiOptions;
                params.uploadFiles = uploadFiles;
                callBack.call(this,params);
            }else{
                defaultMultiPrview(index, file, result,files);
            }
        });
    };

    //单选图片默认的choose函数
    var singleChoose = function (obj,callBack) {
        //将每次选择的文件追加到文件队列
        images = obj.pushFile();
        obj.preview(function (index, file, result) {

            if(callBack != null && typeof callBack == "function"){
                console.log("=====执行用户自定义的回调函数=====");
                var params = {};
                params.index = index;
                params.file = file;
                params.result = result;
                params.singleOptions = singleOptions;
                params.singleUploadFile = singleUploadFile;
                // console.log(params);
                callBack.call(this,params);
            }else{
                defaultSinglePrview(index, file, result);
            }
        });
    };
    //获取对象的key数量
    var getObjKeyLength = function(obj){
        var count = 0;
        for(var i in obj){

            if(obj.hasOwnProperty(i)){
                count++;
            }
        }
        return count;
    }

    //点击删除按钮删除文件的同时，删除已经上传文件集合里面的文件对象
    var deleteFilesByIndex = function(index){
        for(var i=0;i<uploadFiles.length; i++){
            var f = uploadFiles[i];
            console.log("需要删除的文件:"+index);
            if(f.index == index){
                console.log("删除文件");
                uploadFiles.splice(i,1);
                return;
            }
        }
    }

    //清空图片文件队列，和页面上的图片
    this.clear = function(){
        singleUploadFile = [];
        uploadFiles = [];
        // $(singleOptions.defaultPreview).hide();
        // $(singleOptions.defaultFileName).hide();
    }

};