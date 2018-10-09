/**
 *
 * @param domId   初始化id
 * @param accepts 允许上传的文件类型 默认所有类型都可以
 * @param exts 允许上传的后缀名 默认值jpg|png|gif|bmp|jpeg
 */
var CommonFileUpload = function (domId, accepts, exts) {

    var uploadObj = new Object();

    uploadObj.domId = domId;
    uploadObj.url = null;
    uploadObj.accepts = accepts;
    uploadObj.fileNumber = null;
    //文件大小 单位为kb  0不限制大小
    uploadObj.fileSize = 0;
    uploadObj.exts = exts;

    /**
     * 是否允许多文件上传  默认不允许
     * @type {boolean}
     */
    uploadObj.multiple = false;

    /**
     * 是否选完文件后自动上传，默认不允许
     * @type {boolean}
     */
    uploadObj.auto = false;

    /**
     * 默认的上传url
     * @type {string}
     */
    uploadObj.defaultUrl = AppCommon.url.getBaseURL()+"/files/upload";

    uploadObj.defaultAccepts = "file";

    uploadObj.defaultFileNumber = 1;

    uploadObj.defaultExts = "";

    uploadObj.layer = {};

    var delFileInfo = [];


    var callback = new Object();
    //上传成功回调
    callback.doneCallback = null;
    //选则文件后回调
    callback.chooseCallback = null;
    //上传错误回调
    callback.errorCallback = null;

    uploadObj.files = null;


    var uploadFileRes = null;

    /**
     * 获取上传成功后，服务器返回的结果
     * @returns {*}
     */
    this.getUploadFileRes = function(){
        return uploadFileRes;
    }

    /***
     * 上传回调设置
     * @param doneCallback
     * @param chooseCallback
     * @param errorCallback
     */
    this.setCallBack = function (doneCallback, chooseCallback, errorCallback) {
        if (typeof doneCallback === "function") {
            callback.doneCallback = doneCallback;
        }
        if (typeof chooseCallback === "function") {
            callback.chooseCallback = chooseCallback;
        }
        if (typeof errorCallback === "function") {
            callback.errorCallback = errorCallback;
        }

    };

    this.setUrl = function (url) {
        uploadObj.url = url;
    };

    /**
     * 文件上传插件初始化
     */
    this.multiFilesInit = function (param, fileNumber) {
        uploadObj.fileNumber = fileNumber;
        layui.use(['upload'], function () {
            var upload = layui.upload;
            var uploadRender = upload.render({
                elem: "#" + uploadObj.domId
                , url: uploadObj.url != null ? uploadObj.url : uploadObj.defaultUrl
                , auto: uploadObj.auto
                , multiple: true
                , accept: uploadObj.accepts != null ? uploadObj.accepts : uploadObj.defaultAccepts
                , bindAction: "#" + uploadObj.domId + "BindAction"
                , data: param
                , number: uploadObj.fileNumber != null ? uploadObj.fileNumber : uploadObj.defaultFileNumber
                , exts: uploadObj.exts != null ? uploadObj.exts : uploadObj.defaultExts
                , before: function () {
                    loadMsg();
                }
                , done: function (res, index, upload) {
                    if (res.success) { //上传成功
                        var tr = $("#upload-" + index + uploadObj.domId),
                            tds = tr.children();
                        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                        return delete uploadObj.files[index]; //删除文件队列已经上传成功的文件
                    }
                    this.error(index, upload);
                }
                , allDone: function () {
                    layer.close(uploadObj.layer);
                    layer.alert('全部上传成功');
                    $("#" + uploadObj.domId + "BindAction").prop("disabled", true).addClass('layui-disabled');
                }
                , choose: function (obj) {
                    if (typeof callback.chooseCallback === "function") {
                        callback.chooseCallback;
                    } else {
                        multiFilesChoose(obj, uploadRender);
                    }
                }
                , error: function (index, upload) {
                    //请求异常回调
                    var tr = $("#upload-" + index + uploadObj.domId),
                        tds = tr.children();
                    tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                    tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
                }
            });
            uploadObj.uploadRender = uploadRender;
            deleteFile();
            getExistFiles();
        });
    };

    /**
     * 文件上传插件初始化
     */
    this.singleFileInit = function (param) {
        layui.use(['upload'], function () {
            var upload = layui.upload;
            var uploadRender = upload.render({
                elem: "#" + uploadObj.domId
                , url: uploadObj.url != null ? uploadObj.url : uploadObj.defaultUrl
                , auto: uploadObj.auto
                , multiple: false
                , accept: uploadObj.accepts != null ? uploadObj.accepts : uploadObj.defaultAccepts
                , bindAction: "#" + uploadObj.domId + "BindAction"
                , data: param
                , exts: uploadObj.exts != null ? uploadObj.exts : uploadObj.defaultExts
                , done: function (res, index, upload) {
                    var resStr = res.data;
                    uploadFileRes = resStr;
                    if (typeof callback.doneCallback === "function") {
                        console.log("调用用户自定义函数");
                        callback.doneCallback(res,index);
                    } else {
                        console.log(resStr);
                        // layer.open({
                        //     type: 0
                        //     , title: '返回结果'
                        //     , content: resStr
                        // });
                    }
                }
                , choose: function (obj) {
                    if (typeof callback.chooseCallback === "function") {
                        callback.chooseCallback(obj);
                    } else {
                        console.log("commonUpload--choose");
                    }
                }
                , error: function (index, upload) {
                    //请求异常回调
                    if (typeof callback.errorCallback === "function") {
                        callback.errorCallback(index);
                    }
                }
            });
            uploadObj.uploadRender = uploadRender;
        });
    };


    /**
     * 选择文件后加入队列及其他操作
     * @param obj
     * @param uploadRender
     */
    var multiFilesChoose = function (obj, uploadRender) {
        var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
        uploadObj.files = this.files;
        //读取本地文件
        obj.preview(function (index, file, result) {
            var tr = $(['<tr id="upload-' + index + uploadObj.domId + '">'
                , '<td>' + file.name + '</td>'
                , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                , '<td class="progress upload" index="' + index + '">等待上传</td>'
                , '<td>'
                , '<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                , '<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                , '</td>'
                , '</tr>'].join(''));

            //单个重传
            tr.find('.demo-reload').on('click', function () {
                obj.upload(index, file);
            });

            //删除
            tr.find('.demo-delete').on('click', function () {
                delete files[index]; //删除对应的文件
                tr.remove();
                uploadRender.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                getExistFiles();
            });
            $("#" + uploadObj.domId + "List").append(tr);
            getExistFiles();
        });
    };

    function done(result, index, upload) {
        if (!result.success) {

        }
    }


    function loadMsg() {
        uploadObj.layer = layer.msg('正在上传', {
            icon: 16
            , shade: [0.8, '#393D49']
            , time: 0
        });
    }


    /**
     * 获取删除的文件信息，并更新上传文件的个数
     */
    function deleteFile() {
        $("." + uploadObj.domId + "DeleteFile").click(function () {
            var fileInfo = $(this).parent().parent().attr("fileInfo");
            delFileInfo.push(fileInfo);
            $(this).parent().parent().remove();
            getExistFiles();
        });
    }

    /**
     * 设置文件的个数
     * @param flag true:第一次初始化；
     */
    function getExistFiles() {
        var $fileNumber = getFileNumber();
        if (uploadObj.fileNumber != 0 && $fileNumber <= uploadObj.fileNumber) {
            if ($fileNumber === uploadObj.fileNumber) {
                $("#" + uploadObj.domId).addClass('layui-disabled').prop("disabled", true);
            } else {
                $("#" + uploadObj.domId).removeClass('layui-disabled').prop("disabled", false);
            }

            if (!isEmpty(uploadObj.files)) {
                $("#" + uploadObj.domId + "BindAction").removeClass('layui-disabled').prop("disabled", false);
            } else {
                $("#" + uploadObj.domId + "BindAction").prop("disabled", true).addClass('layui-disabled');
            }
        } else {
            $("#" + uploadObj.domId).prop("disabled", true).addClass('layui-disabled');
            $("#" + uploadObj.domId + "BindAction").prop("disabled", true).addClass('layui-disabled');
        }
    }

    2

    function getFileNumber() {
        var $tbodyId = uploadObj.domId + "List";
        return $("#" + $tbodyId).find("tr").length;
    }


    function isEmpty(data) {
        if (data != null && JSON.stringify(data) != '{}') {
            return false
        } else {
            return true
        }
    };
};





