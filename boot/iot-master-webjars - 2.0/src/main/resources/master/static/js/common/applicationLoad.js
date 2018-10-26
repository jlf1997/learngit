var applicationLoad = {};

applicationLoad.action = {
    //页面初始化函数
    initBody: function () {
    	applicationLoad.interface.show();
    },
    /**
     * 事件绑定
     */
    bindEvent: function () {
       
    }
};

//interface类是一些页面渲染函数
applicationLoad.interface ={
	//对所有有 iotPermission的元素进行控制
	show :function(){
		var objs = $(".iotPermission");
//		alert( objs.length);
		console.info("需要权限控制的元素个数："+objs.length);
		if(cimrStringUtil.isNull(objs)==false && objs.length>0){
			for(var i=0;i<objs.length;i++){
				objs[i].style.display="none";
			}
			applicationLoad.interface.loadPermission(objs);
		}
		
		
	},
	canShow : function(obj,permissionList){
		var resid = obj.getAttribute("resourceId");
//		alert(resid);
		if(cimrStringUtil.isNull(resid)==false){
			//不能展示
//			alert(permissionList[resid]);
			if(permissionList[resid]=="0"){
				
				return false;
			}
			return true;
		}
		return true;
	},
	
	loadPermission : function(objs){
		var onSuccess=function(obj){
        	if(obj.success){
        		var permissionList = obj.data;
        		for(var i=0;i<objs.length;i++){
        			if(applicationLoad.interface.canShow(objs[i],permissionList)==true){
        				objs[i].style.display="";
    				}
    			}
        		
        	}else{
        		
        	}
        	
        }
        var onError = function(){
        	  layer.closeAll('loading');
        }
		 var url = "/permission/getList";
		 AppCommon.ajax.execute({
	            'url': url,
	            'success': onSuccess,
	            'error' : onError,
	            'method': 'get'
	        }); 
	}
};
//页面里的一些全局变量
applicationLoad.data ={

};
