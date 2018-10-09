(function(){  
    $.fn.treeTable = function(opts){  
        var t           = this,  
            i           = ["&#xe623;", "&#xe625;"],  
            w           = ['table-row','none'],  
            d           = $.extend({  
                column: 0,// 伸缩图标出现在第几列  
                retract: 16// 伸缩图标缩进多少个像素  
            }, opts),  
            init        = function(){  
                t.find("tbody tr").each(function(){  
                    var i_s = $(this).attr("data-tb-pid") == '0' ? 0 : 1,  
                        icon = $("tbody tr[data-tb-pid='"+($(this).attr("data-tb-id"))+"']").length ? i[0] : '';  
                    // 显示pid为0的根目录，隐藏子目录  
                    $(this).css('display',w[i_s]);  
                    // 添加伸缩图标（最后一级不需要）  
                    $(this).find("td").eq(d.column).prepend('<i class="layui-icon layui-ud">'+icon+'</i>');  
                })  
                // 伸缩图标缩进  
                retract();  
            },  
            retract     = function(){  
                t.find("tbody tr").each(function(){  
                    var id = $(this).attr("data-tb-id"),  
                        px = $(this).find("i").css("margin-left");  
                    // 子级缩进  
                    t.find("tbody tr[data-tb-pid='"+id+"']").find("i").css("margin-left",(parseInt(px)+d.retract)+'px')  
                })  
            },  
            click       = function(){  
                var c_t = $(this),  
                    c_i = c_t.hasClass("sopen") ? 0 : 1,  
                    id  = c_t.parents("tr").attr("data-tb-id");  
                c_t.html(i[c_i]).toggleClass("sopen");  
                tree(id,c_i ? 0 : 1);  
            },  
            tree        = function(id,c_i){  
                t.find("tr[data-tb-pid='"+id+"']").each(function(){  
                    $(this).css("display",w[c_i]);  
                    tree($(this).attr("data-tb-id"),w[c_i]);  
                })  
            };  
        // 点击触发伸缩图标下拉  
        t.on("click","i",click);  
        // 初始化table  
        init();  
    }  
})(window.jQuery)  