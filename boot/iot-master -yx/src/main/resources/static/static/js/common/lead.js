var attachFileIn = function (iframeId) {
    this.attachFileInit = function () {
        attachFile(iframeId,AppCommon.url.getBaseURL(),"css")
        attachFile(iframeId,AppCommon.url.getBaseURL(),"js")
    }
    function attachFile(iframeId,filename, filetype)
    {
        var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
        if (filetype=="css"){ //判断文件类型
            var fileref=window.frames[iframeId].document.createElement("link");
            fileref.setAttribute("rel", "stylesheet") ;
            fileref.setAttribute("type", "text/css");
            fileref.setAttribute("href", filename);
        }
        if (filetype=="js"){ //判断文件类型
            var fileref=window.frames[iframeId].document.createElement('script');//创建标签
            fileref.setAttribute("type","text/javascript");//定义属性type的值为text/javascrip
            fileref.setAttribute("language","JavaScript");
            fileref.setAttribute("src", filename);//文件的地址
        }
        if (typeof fileref!="undefined")
            head.appendChild(fileref);
    }
}
