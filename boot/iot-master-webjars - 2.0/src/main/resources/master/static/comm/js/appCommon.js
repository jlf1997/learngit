var AppCommon={};
AppCommon.log = function(msg){
    if (typeof console == "object") {
        console.log(msg);
    }
};
AppCommon.action = {
    ready: function(onReady) {
        !function($) {
            $(function() {
               onReady();
             applicationLoad.interface.show();
            });
        }(window.jQuery);
       

        // 统一处理ajax异常
        $(document).ajaxError(
            //所有ajax请求异常的统一处理函数，处理
            function(event,xhr,options,exc ){
                if(xhr.status == 'undefined'){
                    return;
                }
                switch(xhr.status){
                    case 403:
                        // 未授权异常
                        layer.closeAll("loading");
                        layer.msg("系统拒绝：您没有访问权限。");
                        break;
                    case 404:
                        layer.closeAll("loading");
                        layer.msg("您访问的资源不存在。");
                        break;
                    default:
                        layer.closeAll("loading");
                        break;
                }
            }
        );
    }
};


AppCommon.ajax = {
    'cache':{
        'needLogin':false
    },
    execute: function(options) {

        var mergedOption = $.extend({},{
            'url':undefined,
            'dataType':'json',
            // 'async':true,
            'data':{},
            'beforeSend':function(){},
            'success': function () {},
            'error':function(){},
            'complete':function(XMLHttpRequest, textStatus) {
                if (XMLHttpRequest.responseJSON) {
                    var httpStatus = XMLHttpRequest.status;
                    if (httpStatus == '403') {
                        console.log(XMLHttpRequest.responseJSON.message);
                        return;
                    } else if(!XMLHttpRequest.responseJSON.success){
                        console.log(XMLHttpRequest.responseJSON.error);
                        return;
                    }
                }
            }
        },{
            'type':'POST'
        },options);

        $.ajax(mergedOption);

    }
};

AppCommon.type = {
    isString:function(data){
        var isString = (typeof data=='string');
        isString = isString && (data.constructor==string);
        return isString;
    }
};

AppCommon.include = {
    data:{
        cache:{
            'version':undefined
        },
        getVersion:function(){
            var thisVersion = AppCommon.include.data.cache.version;
            if (!thisVersion) {
                var scriptLoader = $('[data-toggle="jsConfig"]');
                thisVersion = scriptLoader.attr('data-version');
                // 如果明确设置了变量的值（非null/undifined/0/""等值),
                // 结果就会根据变量的实际值来返回，如果没有设置，结果就会返回false
                if (!!thisVersion){
                    AppCommon.include.data.cache.version = thisVersion;
                }
            }
            return thisVersion;
        }
    },
    loadCss: function (url,ver) {
        var urlExtend= !!ver ? '?_ver'+ver : "";
        $("<link>").attr({
            rel:"stylesheet",
            type:"text/css",
            href:(url+urlExtend),
            ctx:"menu"
        }).appendTo("head");
    },
    loadJs: function(url, ver){
        var script = document.createElement("script");
        script.src = !!ver ? (url+'?_ver='+ver) : url;
        document.head.appendChild(script);
    },
    load:function (urlsStr, urlType) {
        urls = urlsStr.split(',');
        var specVersion = AppCommon.include.data.getVersion();
        for (var i=0; i<urls.length; i++) {
            try{
                switch(urlType) {
                    case 'css':
                        AppCommon.include.loadCss(urls[i],specVersion);
                        break;
                    case 'js':
                    {
                        AppCommon.include.loadJs(urls[i], specVersion);
                        break;
                    }
                    default:
                        if (urls[i].indexOf('.css') >= 0){
                            AppCommon.include.loadCss(urls[i], specVersion);
                        } else {
                            AppCommon.include.loadJs(urls[i], specVersion);
                        }
                        break;
                }
            } catch (e) {

            }
        }
    }
};

AppCommon.url = {
    replaceWithUrl:function(url){
        window.location.href=url;
    },
    getBaseURL: function() {
        var fullPath = window.document.location.href;
        var pathName = window.document.location.pathname;
        var hostPath = fullPath.substring(0, fullPath.indexOf(pathName));
        // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        var baseURL = hostPath;
        return baseURL;
    }
};

AppCommon.date={
    formatDate:function (format, timeObject) {
        if(!timeObject)return "";
        try {
            var o =
            {
                "M+" : timeObject.getMonth()+1, //month
                "d+" : timeObject.getDate(),    //day
                "h+" : timeObject.getHours(),   //hour
                "m+" : timeObject.getMinutes(), //minute
                "s+" : timeObject.getSeconds(), //second
                "q+" : Math.floor((timeObject.getMonth()+3)/3),  //quarter
                "S" : timeObject.getMilliseconds() //millisecond
            }
            if(/(y+)/.test(format))
                format=format.replace(RegExp.$1,(timeObject.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(format))
                    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
            return format;
        }catch(e){}
        return "";
    }
};