var $,tab,skyconsWeather;
layui.config({
	base : "js/"
}).use(['form','layer','element'],function(){
	var form = layui.form,
		layer = layui.layer;
		element = layui.element;


	//更换皮肤
	function skins(){
		var skin = window.sessionStorage.getItem("skin");
		if(skin){  //如果更换过皮肤
			if(window.sessionStorage.getItem("skinValue") != "自定义"){
				$("body").addClass(window.sessionStorage.getItem("skin"));
			}else{
				$(".layui-layout-admin .layui-header").css("background-color",skin.split(',')[0]);
				$(".layui-bg-black").css("background-color",skin.split(',')[1]);
				$(".hideMenu").css("background-color",skin.split(',')[2]);
			}
		}
	}
	skins();
	$(".changeSkin").click(function(){
		console.log("change skin");
		layer.open({
			title : "更换皮肤",
			area : ["310px","300px"],
			type : "1",
			content : '<div class="skins_box">'+
						'<form class="layui-form">'+
							'<div class="layui-form-item">'+
								'<input type="radio" name="skin" value="默认" title="默认" lay-filter="default" checked="">'+
								'<input type="radio" name="skin" value="橙色" title="橙色" lay-filter="orange">'+
								'<input type="radio" name="skin" value="蓝色" title="蓝色" lay-filter="blue">'+
								'<input type="radio" name="skin" value="自定义" title="自定义" type="color" lay-filter="custom">'+
								'<div class="skinCustom">'+
									'<input class="layui-input topColor" type="text" id="c1" name="topSkin" placeholder="顶部颜色" />'+
									'<input class="layui-input leftColor" type="text" id="c2" name="leftSkin" placeholder="按钮颜色" />'+
									'<input class="layui-input menuColor" type="text" id="c3" name="btnSkin" placeholder="当前页面标识色" />'+
									'<input class="layui-input borderColor" type="text" id="c4" name="borderSkin" placeholder="线框颜色" />'+
								'</div>'+
							'</div>'+
							'<div class="layui-form-item skinBtn">'+
								'<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-normal" lay-submit="" lay-filter="changeSkin">确定更换</a>'+
								'<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-primary" lay-submit="" lay-filter="noChangeSkin">我再想想</a>'+
							'</div>'+
						'</form>'+
					'</div>',
			success : function(index, layero){
				var skin = window.sessionStorage.getItem("skin");
				if(window.sessionStorage.getItem("skinValue")){
					$(".skins_box input[value="+window.sessionStorage.getItem("skinValue")+"]").attr("checked","checked");
				};
				if($(".skins_box input[value=自定义]").attr("checked")){
					$(".skinCustom").css("visibility","inherit");
					$(".topColor").val(skin.split(',')[0]);
					$(".leftColor").val(skin.split(',')[1]);
					$(".menuColor").val(skin.split(',')[2]);
					$(".borderColor").val(skin.split(',')[3]);
				};
				form.render();
				$(".skins_box").removeClass("layui-hide");
				$(".skins_box .layui-form-radio").on("click",function(){
					var skinColor;
					if($(this).find("span").text() == "橙色"){
						skinColor = "orange";
					}else if($(this).find("span").text() == "蓝色"){
						skinColor = "blue";
					}else if($(this).find("span").text() == "默认"){
						skinColor = "used";
					}
					if($(this).find("span").text() != "自定义"){
						$(".topColor,.leftColor,.menuColor,.borderColor").val('');
						$("body").removeAttr("class").addClass("main_body "+skinColor+"");
						$(".skinCustom").removeAttr("style");
						$(".admin-side-toggle,.admin-nav-card .layui-tab-title .layui-this,.layui-layout-admin .layui-header,.admin-nav-card .layui-tab-title,.layui-layout-admin .layui-body,.layui-nav-tree .layui-nav-child dd.layui-this a,.layui-btn-normal,.layui-layer-btn a:first-child").removeAttr("style");
					}else{
						$(".skinCustom").css("visibility","inherit");
					}
				})
				$("#c1").bigColorpicker("c1");
				$("#c2").bigColorpicker("c2");
	            $("#c3").bigColorpicker("c3");
				$("#c4").bigColorpicker("c4","L",6);
				var skinStr,skinColor;
				$(".topColor").blur(function(){
					$(".layui-layout-admin .layui-header,.layui-nav-tree .layui-nav-child dd.layui-this a,.layui-btn-normal,.layui-layer-btn a:first-child").css("background-color",$(this).val());
				})
				$(".leftColor").blur(function(){
					$(".admin-side-toggle").css("background-color",$(this).val());
				})
				$(".menuColor").blur(function(){
					$(".admin-nav-card .layui-tab-title .layui-this").css("background-color",$(this).val());
				})
				$(".borderColor").blur(function(){
					$(".admin-nav-card .layui-tab-title,.layui-layout-admin .layui-body,.layui-layout-admin .layui-header").css("border-color",$(this).val());
				})

				form.on("submit(changeSkin)",function(data){
					if(data.field.skin != "自定义"){
                        var theme;
						if(data.field.skin == "橙色"){
							skinColor = "orange";
                            theme = "{theme:orange}";
						}else if(data.field.skin == "蓝色"){
							skinColor = "blue";
                            theme = "{theme:blue}";
						}else if(data.field.skin == "默认"){
							skinColor = "";
                            theme = "{theme:default}";
						}
						window.sessionStorage.setItem("skin",skinColor);
                        //记忆换肤

                        var params={
                            theme:theme,
                        };
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                layer.msg('换肤成功');
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/userManager/ajax/saveSkin',
                            'data':params,
                            'async':false,
                            'success':onSuccess
                        });
					}else{
						skinStr = $(".topColor").val()+','+$(".leftColor").val()+','+$(".menuColor").val()+','+$(".borderColor").val();
						window.sessionStorage.setItem("skin",skinStr);
                        //记忆换肤
						var theme = "{theme:custom,topColor:"+$(".topColor").val()+",leftColor:"+$(".leftColor").val()+",menuColor:"+$(".menuColor").val()+",borderColor:"+$(".borderColor").val()+"}";
                        var params={
                            theme:theme,
                        };
                        var onSuccess = function (resultData) {
                            if (resultData.success) {
                                layer.msg('换肤成功');
                            }
                        };
                        AppCommon.ajax.execute({
                            'url':AppCommon.url.getBaseURL()+'/userManager/ajax/saveSkin',
                            'data':params,
                            'async':false,
                            'success':onSuccess
                        });
						$("body").removeAttr("class").addClass("main_body");
					}
					window.sessionStorage.setItem("skinValue",data.field.skin);
					layer.closeAll("page");
				});
				form.on("submit(noChangeSkin)",function(){
					$("body").removeAttr("class").addClass("main_body "+window.sessionStorage.getItem("skin")+"");
					$(".admin-side-toggle,.admin-nav-card .layui-tab-title .layui-this,.layui-layout-admin .layui-header,.admin-nav-card .layui-tab-title,.layui-layout-admin .layui-body,.layui-nav-tree .layui-nav-child dd.layui-this a,.layui-btn-normal,.layui-layer-btn a:first-child").removeAttr("style");
					skins();
					layer.closeAll("page");
                    $(".bigpicker").hide();
				});
			},
			cancel : function(){
				$("body").removeAttr("class").addClass("main_body "+window.sessionStorage.getItem("skin")+"");
				$(".admin-side-toggle,.admin-nav-card .layui-tab-title .layui-this,.layui-layout-admin .layui-header,.admin-nav-card .layui-tab-title,.layui-layout-admin .layui-body,.layui-nav-tree .layui-nav-child dd.layui-this a,.layui-btn-normal,.layui-layer-btn a:first-child").removeAttr("style");
				skins();
                $(".bigpicker").hide();
			}
		})
	})

	//退出
	$(".signOut").click(function(){
		window.sessionStorage.removeItem("menu");
		menu = [];
		window.sessionStorage.removeItem("curmenu");
        $(".bigpicker").hide();
	})

});
