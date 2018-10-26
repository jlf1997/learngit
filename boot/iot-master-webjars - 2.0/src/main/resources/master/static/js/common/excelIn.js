
// 包名
var excelIn = function (ele,url) {
    var data;
    var doneFunction = function(res,callBack){
        if(callBack != null && typeof callBack == "function"){
            console.log("=====执行用户自定义的回调函数=====");
            callBack.call(this,res);
        }else{
            layer.closeAll('loading');
            data = res;
        }
    }

    this.excelInit = function (callBack) {
        layui.use('upload', function () {
            var $ = layui.jquery, upload = layui.upload;
            var uploadInst = upload.render({
                elem: '#' + ele
                , url: AppCommon.url.getBaseURL()+"/excel/ajax/excelIn"
                , accept: 'file' //普通文件
                ,before:function(){
                    layer.load();
                }
                , done: function (res) {
                    doneFunction(res,callBack)
                }
            })
        })
    }
    this.getData = function(){
        return data;
    }
}

