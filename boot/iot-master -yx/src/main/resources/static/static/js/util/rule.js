layui.use('form', function () {
    var form = layui.form;
    form.render();
    //规则校验
    form.verify({
        /**
         * 数字校验
         * @param value
         * @param item
         * @returns {string}
         */
         isNumber: function (value, item) {
            if (!$.isEmpty(value)) {
                if (!$.isNumber(value)) {
                    return '请填写数字';
                }
            }
        },
        /**
         * 电话校验
         * @param value
         * @param item
         * @returns {string}
         */
        isPhone:function (value, item) {
            if (!$.isEmpty(value)) {
                if(!$.isPhone(value)){
                    return "请填写正确的电话号码";
                }
            }
        },
        /**
         * 邮箱校验
         * @param value
         * @param item
         * @returns {string}
         */
        isEmail:function (value, item) {
            if (!$.isEmpty(value)) {
                if(!$.isEmail(value)){
                    return "请填写正确的邮箱号码";
                }
            }
        },
        myName:function (value, item) {
            if (!$.isEmpty(value)) {
                if(value.length > 20){
                    return "最多只能输入20个字符";
                }
            }
        },
        myCotent:function (value, item) {
            if (!$.isEmpty(value)) {
                if(value.length > 500){
                    return "内容长度太长了,再短一点吧";
                }
            }
        },
        myAddress:function (value, item) {
            if (!$.isEmpty(value)) {
                if(value.length > 50){
                    return "地址长度太长了,再短一点吧";
                }
            }
        },
        /**
         * 是否是日期
         * @param value
         * @param item
         * @returns {string}
         */
        isDate:function(value, item){
             if(!$.isEmpty(value)){
                 if(!$.isDateStr(value)){
                     return "日期格式有误";
                 }
             }
        },
        /**
         * 是否是正数
         * @param value
         * @param item
         * @returns {string}
         */
        isPositiveNumber :function(value, item){
            if(!$.isEmpty(value)){
                if(!$.isNumber(value)){
                    return "请填写数字";
                }
                var num = parseFloat(value);
                if(num <= 0){
                    return "请填写大于0的数字";
                }
            }
        },
        isPositiveInt:function(value, item){
            if(!$.isEmpty(value)){
                if(!$.isNumberZ(value)){
                    return "请填写正整数";
                }
            }
        },
        /**
         * 身份证号码校验
         * @param value
         * @param item
         * @returns {string}
         */
        isCardNo:function(value, item){
            if(!$.isEmpty(value)){
                if(!$.isCardNo(value)){
                    return "身份证号码格式不对";
                }
            }
        },

    });
});
