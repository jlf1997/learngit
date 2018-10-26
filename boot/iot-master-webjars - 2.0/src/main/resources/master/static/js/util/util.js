/**
 * @api {js} jQuery.isNumber(str) isNumber(验证数字格式)
 * @apiName isNumber 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是有效数字格式
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是有效的数字;false 不是有效的数字
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("12345")){
 *  	alert("是有效的数字");
 *  }else{
 *  	alert("不是有效的数字");
 *  }
 *  
 */
jQuery.isNumber = function(str){
	var reg = /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/;
	return reg.test(str+"");
};



/**
 * @api {js} jQuery.isNumber(str) isNumber(验证正整数字格式)
 * @apiName isNumberZ 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是有效数字格式
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是有效的数字;false 不是有效的数字
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("12345")){
 *  	alert("是有效的数字");
 *  }else{
 *  	alert("不是有效的数字");
 *  }
 *  
 */
jQuery.isNumberZ = function(str){
	var reg = /^[1-9]\d*$/;
	return reg.test(str+"");
};

/**
 * @api {js} jQuery.isDateStr(str) isDateStr(验证日期格式)
 * @apiName isDateStr 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是有效日期字符串格式
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是有效的日期字符串;false 不是有效的日期字符串
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("2016-01-01")){
 *  	alert("是有效的日期字符串");
 *  }else{
 *  	alert("不是有效的日期字符串");
 *  }
 *  
 */
jQuery.isDateStr=function(str){
	var reg = /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/;
	return reg.test(str);
};

/**
 * @api {js} jQuery.isUrl(str) isUrl(验证url格式)
 * @apiName isUrl 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是一个url
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串
 * @apiSuccess {boolean} return true 是一个url;false 不是一个url
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("是一个url");
 *  }else{
 *  	alert("不是一个url");
 *  }
 *  
 */
jQuery.isUrl = function(str){
	var reg = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
    return reg.test(str+"");
};

/**
 * @api {js} jQuery.isCardNo(str) isCardNo(验证身份证格式)
 * @apiName isCardNo 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是符合身份证格式
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串
 * @apiSuccess {boolean} return true 符合身份证格式;false 不符合身份证格式
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("符合身份证格式");
 *  }else{
 *  	alert("不符合身份证格式");
 *  }
 *  
 */
jQuery.isCardNo = function(v) {
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	return reg.test(v);
};

/**
 * @api {js} jQuery.isMoney(num) isMoney(验证价格格式)
 * @apiName isMoney 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否是符合价格证格式
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} num 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 符合价格格式;false 不符合价格证格式
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber(100)){
 *  	alert("符合价格格式");
 *  }else{
 *  	alert("不符合价格格式");
 *  }
 *  
 */
jQuery.isMoney=function(v){
	var reg = /^(([1-9])\d*.[0-9]+|([1-9])\d*)$/;
	return reg.test(v);
};


/**
 * @api {js} jQuery.isSpecialChar(str) isSpecialChar(验证是否含特殊字符)
 * @apiName isSpecialChar 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否含特殊字符
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 含有特殊字符;false 不含有特殊字符
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("含有特殊字符");
 *  }else{
 *  	alert("不含有特殊字符");
 *  }
 *  
 */
jQuery.isSpecialChar = function(v){
	var reg = /[~#^$@%&!*:\,._]/gi;
	return reg.test(v);
};

/**
 * @api {js} jQuery.isZZNum(num) isZZNum(非负整数判断)
 * @apiName isZZNum 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否非负整数
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} num 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是非负整数;false 不是非负整数
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("是非负整数");
 *  }else{
 *  	alert("不是非负整数");
 *  }
 *  
 */
jQuery.isZZNum = function(v){
	var reg = /^(([1-9])\d*|0{1})$/;
	return reg.test(v);
};

/**
 * @api {js} jQuery.isLetterNumber(num) isLetterNumber(是否只有字母和数字组成)
 * @apiName isLetterNumber 
 * @apiGroup jQuery-is
 * @apiDescription 验证入参是否只有字母和数字组成
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} num 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是只有字母和数字组成;false 不是只有字母和数字组成
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("是只有字母和数字组成");
 *  }else{
 *  	alert("不是只有字母和数字组成");
 *  }
 *  
 */
jQuery.isLetterNumber = function(v) {
	var reg = /^[0-9a-zA-Z]+$/;
	return reg.test(v);
};

/**
 * @api {js} jQuery.isEmpty(str) isEmpty(字符串是否为空)
 * @apiName isLetterNumber 
 * @apiGroup jQuery-is
 * @apiDescription 验证字符串v是否为空（null 或者 空字符串——""）
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 空字符串;false 非空字符串
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("http://www.baidu.com")){
 *  	alert("是空字符串");
 *  }else{
 *  	alert("不空字符串");
 *  }
 *  
 */
jQuery.isEmpty = function(v) {
	if (v == null || $.trim(v) == "") {
		return true;
	} else {
		return false;
	}
};

/**
 * @api {js} jQuery.isTelphone(str) isTelphone(验证固定电话)
 * @apiName isTelphone 
 * @apiGroup jQuery-is
 * @apiDescription 验证固定电话
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是固定电话;false 不是固定电话
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber(15200005056)){
 *  	alert("是固定电话");
 *  }else{
 *  	alert("不是固定电话");
 *  }
 *  
 */
jQuery.isTelphone = function(v){
	//var reg = /^0\d{2,3}-?\d{7,8}$/;
	var reg = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
	return reg.test(v);
};


jQuery.isFax = function(v){
	var reg = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	return reg.test(v);
};


/**
 * @api {js} jQuery.isPhone(str) isPhone(验证移动电话)
 * @apiName isPhone 
 * @apiGroup jQuery-is
 * @apiDescription 验证移动电话
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是移动电话;false 不是移动电话
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber(15200005056)){
 *  	alert("是移动电话");
 *  }else{
 *  	alert("不是移动电话");
 *  }
 *  
 */
jQuery.isPhone = function(v) {
	var reg = /^(12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/;
	return reg.test(v);
};

/**
 * @api {js} jQuery.isEmail(str) isEmail(验证邮箱地址)
 * @apiName isEmail 
 * @apiGroup jQuery-is
 * @apiDescription 验证邮箱地址
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要被验证的字符串或者数字
 * @apiSuccess {boolean} return true 是邮箱地址;false 不是邮箱地址
 * @apiExample {js} 示例:
 * 
 *  if($.isNumber("163@163.com")){
 *  	alert("是邮箱地址");
 *  }else{
 *  	alert("不是邮箱地址");
 *  }
 *  
 */
jQuery.isEmail = function(v) {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return reg.test(v);
};


/**
 * @api {js} jQuery.getLength(str) getLength(获取字符串长度)
 * @apiName getLength 
 * @apiGroup jQuery-get
 * @apiDescription 获取字符串长度
 * @apiVersion 1.0.0
 * 
 * @apiParam {String} str 需要获取长度的字符串
 * @apiSuccess {number} return 字符串长度（1个中文字符=2个字节）
 * @apiExample {js} 示例:
 * 
 *  var nlen = $.getLength("需要获取长度字符串");
 *  
 */
jQuery.getLength = function(v) {
	if ($.isEmpty(v)) {
		return 0;
	} else {
		return v.replace(/[^\x00-\xff]/ig, "**").length;
	}
};




/**
 * @api {js} jQuery.iFormatDate(s) iFormatDate(日期格式化)
 * @apiName iFormatDate 
 * @apiGroup jQuery-format
 * @apiDescription 日期格式化
 * @apiVersion 1.0.0
 * 
 * @apiParam {number} time 时间戳
 * @apiParam {string} pattern 日期格式 缺省值"yyyy-MM-dd hh:mm:ss"
 * 
 * @apiSuccess {string} return 格式化后的日期字符串
 * @apiExample {js} 示例:
 * 
 *  var str = $.iFormatDate({
 *  				time:1457600527725,
 *  				pattern: "yyyy-MM-dd hh:mm:ss"
 *  				});
 *  
 */
jQuery.iFormatDate = function(s) {
	var d;
	var pattern;
	if (typeof(s) == "number") {
		d = new Date(parseInt(s));
		s = {};
	} else if (typeof(s.time) == "number" || typeof(s.time) == "string") {
		d = new Date(parseInt(s.time));
	} else if (typeof(s.time) == "object") {
		d = new Date(parseInt(s.time.time));
	} else {
		d = new Date();
	}

	pattern = s.pattern || "yyyy-MM-dd hh:mm:ss";

	var y = d.getFullYear();
	var MM = d.getMonth() + 1;
	var dd = d.getDate();
	var hh = d.getHours();
	var mm = d.getMinutes();
	var ss = d.getSeconds();

	return format(y, MM, dd, hh, mm, ss, pattern);

	function format(y, M, d, h, m, s, pattern) {

		var yy = (y + "").substring(2);
		var MM = M;
		var dd = d;
		var hh = h;
		var mm = m;
		var ss = s;

		if (MM < 10) {
			MM = "0" + MM;
		}
		if (dd < 10) {
			dd = "0" + dd;
		}
		if (hh < 10) {
			hh = "0" + hh;
		}
		if (mm < 10) {
			mm = "0" + mm;
		}
		if (ss < 10) {
			ss = "0" + ss;
		}

		pattern = pattern.replace(/[y]{4}/g, y);
		pattern = pattern.replace(/[y]{2}/g, yy);
		pattern = pattern.replace(/[M]{2}/g, MM);
		pattern = pattern.replace(/[M]{1}/g, M);
		pattern = pattern.replace(/[d]{2}/g, dd);
		pattern = pattern.replace(/[d]{1}/g, d);
		pattern = pattern.replace(/[h]{2}/g, hh);
		pattern = pattern.replace(/[h]{1}/g, h);
		pattern = pattern.replace(/[m]{2}/g, mm);
		pattern = pattern.replace(/[m]{1}/g, m);
		pattern = pattern.replace(/[s]{2}/g, ss);
		pattern = pattern.replace(/[s]{1}/g, s);

		return pattern;
	}
};


/**
 * @api {js} jQuery.iFeedTimeF(s) iFeedTimeF(消息化日期格式化)
 * @apiName iFeedTimeF 
 * @apiGroup jQuery-format
 * @apiDescription 日期格式化
 * @apiVersion 1.0.0
 * 
 * @apiParam {number} time 数据时间戳
 * @apiParam {string} pattern 日期格式 缺省值"yyyy-MM-dd hh:mm:ss"
 * @apiParam {number} nowTime 当前服务器时间戳 缺省 则获取当前用户终端（浏览器所在系统）的时间
 * 
 * @apiSuccess {string} return 格式化后的日期字符串
 * @apiExample {js} 示例:
 * 
 *  var str = $.iFeedTimeF({
 *  			dtime:1457600527725,
 *  			nowTime:1457600527725,
 *  			pattern: "yyyy-MM-dd hh:mm:ss"
 *  			});
 *  
 */
jQuery.iFeedTimeF = function(s){
	if(s==null || s.dtime==null){
		return "";
	}
	if($.isEmpty(s.nowTime)){
		s.nowTime = 0;
	}
	s.nowTime = s.nowTime ||new Date().getTime();
	s.time = s.nowTime-s.dtime;
	s.time = parseInt(s.time/1000);
	if(s.time<10){
		return "刚刚";
	}else if(s.time<60){
		return s.time+"秒前";
	}else if(s.time<3600){
		s.time = Math.ceil(s.time/60.0);
		return s.time+"分前";
	}else{
		s.time = Math.round(s.time/3600.0);
		if(s.time<24){
			return s.time+"小时前";
		}else{
			return $.iFormatDate({time:s.dtime,pattern: s.pattern});
		}
		
	}
};

/**
 * @api {js} jQuery.setICookie(name,value,path,Days) setICookie(设置写入cookie)
 * @apiName setICookie 
 * @apiGroup jQuery-cookie
 * @apiDescription 设置写入一对cookie
 * @apiVersion 1.0.0
 * 
 * @apiParam {string} name 设置写入cookie的名
 * @apiParam {string} value 设置写入cookie的值
 * @apiParam {string} path 设置写入cookie的路径
 * @apiParam {number} Days 设置写入cookie保存的天数
 * 
 * @apiExample {js} 示例:
 * 
 *  $.setICookie("UID","sdfsdf11","/",1);
 *  
 */
jQuery.setICookie = function(name, value, path, Days) {
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	var pstr = (path != null && path != "") ? (";path=" + path) : "";
	exp = Days != null ? (";expires=") + exp.toGMTString() : "";
	document.cookie = name + "=" + escape(value) + exp + pstr;

};

/**
 * @api {js} jQuery.getICookie(name) getICookie(读取cookie)
 * @apiName getICookie 
 * @apiGroup jQuery-cookie
 * @apiDescription 读取name对应的cookie值
 * @apiVersion 1.0.0
 * 
 * @apiParam {string} name 要读取的cookie的名
 * 
 * @apiSuccess {string} return 读取到的name对应的cookie的值
 * @apiExample {js} 示例:
 * 
 *  var str = $.getICookie("UID");
 *  
 */
jQuery.getICookie = function(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
};

/**
 * @api {js} jQuery.delICookie(name) delICookie(删除cookie)
 * @apiName delICookie 
 * @apiGroup jQuery-cookie
 * @apiDescription 删除name对应的cookie
 * @apiVersion 1.0.0
 * 
 * @apiParam {string} name 要删除的cookie的名
 * 
 * @apiExample {js} 示例:
 *  $.delICookie("UID");
 */
jQuery.delICookie = function(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = jQuery.getYKCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
};


/**
 * 克隆对象o的一个副本
 * @param o 需要被克隆的对象
 * @reutrn 克隆出的副本
 */

/**
 * @api {js} jQuery.objectClone(obj) objectClone(克隆对象)
 * @apiName objectClone 
 * @apiGroup jQuery
 * @apiDescription 克隆对象o的一个副本
 * @apiVersion 1.0.0
 * 
 * @apiParam {object} o 被克隆的对象
 * 
 * @apiSuccess {object} return 克隆生成的对象
 * 
 * @apiExample {js} 示例:
 *  
 *  var o = $.objectClone({name:"1123"});
 *  
 */
jQuery.objectClone = function(o){
	var o1= {};
	if(o && typeof o == "object"){
		var v;
		for(var k in o){
			v = o[k];
			if(typeof v == "object"){
				v = $.objectClone(v);
			}
			o1[k]=v;
		}
	}
	
	return o1;
};

/**
 * 百分比验证小于100的两位小数如：30.20%
 */
jQuery.isDouble = function(str){
	var reg = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
	return reg.test(str+"");
};

/**
 * 验证数字或小数点后面两位小数
 */
jQuery.isDoubleCheck = function(str){
	var reg = /^((\d*[0-9])|([0-9]+\.\d{1,2}))$/;
	return reg.test(str+"");
};
/**
 * 只能是数字，英文，下划线
 */
jQuery.isNumberLetterCheck = function(v){
    var reg = /^[0-9a-zA-Z_]{1,}$/;
    return reg.test(v);
};
/**
 * 序列化url参数部分
 * 将参数部分作为json 对象返回
 * 其中 #后面部分 为此json 对象的 _hashO 属性
 */
jQuery.getYKArgs = function() {
	var args = {};
	var query = location.search.substring(1);
	var pairs = query.split("&");
	var pairstem, pos, argname, argvalue;
	for (var i = 0, len = pairs.length; i < len; i++) {
		pairstem = pairs[i];
		pos = pairstem.indexOf("=");
		if (pos == -1)
			continue;
		argname = pairstem.substring(0, pos);
		argvalue = pairstem.substring(pos + 1);
		argvalue = decodeURIComponent(argvalue);
		if(args[argname]){
			if(args[argname] instanceof Array){
				args[argname].push(argvalue);
			}else{
				args[argname] = [args[argname],argvalue];
			}
		
		}else{
			args[argname] = argvalue;
		}
		
	}
	var _hashO = {};
	query = location.hash.substring(1);
	pairs = query.split("&");
	for (var i = 0, len = pairs.length; i < len; i++) {
		pairstem = pairs[i];
		pos = pairstem.indexOf("=");
		if (pos == -1)
			continue;
		argname = pairstem.substring(0, pos);
		argvalue = pairstem.substring(pos + 1);
		_hashO[argname] = argvalue;
	}
	args["_hashO"] = _hashO;
	return args;
};




/**
 * tag标签分隔 主要对空格和英文逗号分隔
 */
jQuery.tagSplit = function(str){
	if(str==null){
		return [];
	}
	
	return str.split(/[\s,]+/g);
}

/**
 * 把字符串转为Date
 * @param str
 */
jQuery.convertDateFromString=function (str) {
	if(str){
		var date = new Date(str.replace(/\-/g, '/'));
		return date;
    }
    return null;
}

/**
 * js日期格式化
 * @param fmt
 * @returns
 */
/**
 * js日期格式化
 * @param fmt
 * @returns
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}



