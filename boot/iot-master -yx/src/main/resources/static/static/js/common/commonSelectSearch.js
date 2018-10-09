var CommonSelectedSearch = function (divId, selectId, url) {

    var searchObj = new Object();

    searchObj.url = url;
    searchObj.selectId = selectId;
    searchObj.divId = divId;
    searchObj.inputElement = null;
    searchObj.layForm = null;
    searchObj.initElemnt = "#" + divId + " input";


    /**
     * 一次性加载所有信息 数据量少时使用
     * @param params 查询参数
     * @param selectData select的数据
     */
    this.allDataInit = function (params, selectData) {
        if (selectData == null && url != null) {
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + searchObj.url,
                'data': params,
                'success': function (data) {
                    if (data.success) {
                        layui.use('form', function () {
                            var form = layui.form;
                            $("#" + searchObj.selectId).html(optionHtml(data.data));
                            form.render();
                        });
                    }
                }
            });
        } else {
            layui.use('form', function () {
                var form = layui.form;
                $("#" + searchObj.selectId).html('');
                $("#" + searchObj.selectId).append(optionHtml(selectData));
                form.render();
            });
        }
    };

    /**
     * 异步加载初始化
     * @param domId
     * @param url
     */
    this.asyncLoadInit = function () {
        layui.use('form', function () {
            var form = layui.form;
            form.render('select');
            searchObj.layForm = form;
            $(document).on('input propertychange', "#" + divId + " input", function () {
                var inputVal = $('#' + searchObj.divId).find("input[type='text']").val();
                asyncLoad(inputVal);
            });
        });
    };

    /**
     * 异步获取select数据
     * @param selectId
     * @param url
     * @param inputVal
     * @param form
     */
    var asyncLoad = function (inputVal) {
        AppCommon.ajax.execute({
            'url': AppCommon.url.getBaseURL() + searchObj.url,
            'data': {param: inputVal},
            'success': function (data) {
                if (data.success) {
                    $("#" + searchObj.selectId).html('');
                    $("#" + searchObj.selectId).append(optionHtml(data.data));
                    searchObj.layForm.render();
                    //获取当前下拉菜单的位置并将光标移动到最末尾的位置
                    $('#' + searchObj.divId).find("input[type='text']").val('').focus().val(inputVal);
                    $('#' + searchObj.divId + '>div').addClass("layui-form-selected");
                }
            }
        });
    };


    /**
     * 生成html代码
     * @param options
     * @returns {string}
     */
    function optionHtml(options) {
        var optionHtml = "";
        var option = "";
        $(options).each(function (index, item) {
            if (item.selected) {
                option = "<option selected value='" + item.value + "' attribute ='" + item.attribute + "'>";
            } else {
                option = "<option  value='" + item.value + "' attribute ='" + item.attribute + "'>";
            }

            option += item.key + "</option>";
            optionHtml += option;
        });
        return optionHtml;
    };
};
