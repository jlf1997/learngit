var cirrDateUtil = {
		
}

var cimrStringUtil = {
		isNull : function(obj){
			var b = (JSON.stringify(obj) == "{}");
			return b;
		}
}