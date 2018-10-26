var sysLog = {};

sysLog.action = {
    initBody: function () {
        sysLog.interface.initTablePage();
        sysLog.action.bindEvent();
    },
    bindEvent: function () {

        $("#addBtn").click(function () {
            console.log("click");
            sysLog.interface.initsysLogData();
            sysLog.interface.initFormPage();
            sysLog.action.bindEvent();
            return false;
        });
        $("#cancelBtn").click(function () {
            //清空缓存的数据
            //sysLog.data.sysLogData ={};
            sysLog.action.initBody();
            return false;
        });

    },
    bindEventClose: function () {
        $(".close").click(function () {
            this.remove();
        })
    }
}
sysLog.interface = {
    // 加载表格页
    initTablePage: function () {
        var html = template("sysLogList_template");
        $(".sysLog_box").html(html);
        sysLog.interface.initsysLogData();
        layui.use(['table', 'form'], function () {
            var table = layui.table;
            //执行表格渲染
            var tableIns = table.render({
                //height: 476,
                url: AppCommon.url.getBaseURL() + '/sysLog/ajax/getList',
                elem: "#tb_list",
                page: true,
                limit: 10,
                loading: true,
                id: 'id',
                cols: [[
                    {
                        field: 'ip',
                        title: 'IP',
                        //width:200,
                        align: 'left'
                    },
                    {
                        field: 'path',
                        title: '访问路由',
                        align: 'left'
                    }, {
                        field: 'userName',
                        title: '用户名称',
                        align: 'left'
                    },
                    {
                        field: 'commit',
                        title: '提示信息',
                        align: 'left'
                    },
                    {
                        field: 'accessTimeStr',
                        //width:140,
                        title: '访问时间↓',
                        align: 'left',
                    },
                    {
                        field: 'responTime',
                        //width:140,
                        title: '响应时间(毫秒)',
                        align: 'center',
                    }
                ]]
            });

            sysLog.data.tableIns = tableIns;

            table.on('tool(tb_list)', function (obj) {
                //获取当前选中行
                var rowData = obj.data;
                var layEvent = obj.event;
            });
            
            // 查询
            var form = layui.form;
            form.render(null, 'searchForm');
            form.on('submit(searchBtn)', function (data) {
                var postData = {
                    accessTime: $.trim(data.field.accessTime)
                };
                tableIns.reload({
                	page:{curr:1},
                    where: postData
                });
                return false;
            });
        });

    },
    // 初始化相关插件
    initsysLogData: function () {
        //初始化时间控件
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            //常规用法
            laydate.render({
                elem: '#accessTime'
                ,type:'datetime'
                ,range:true
            });
        });
    },
};

sysLog.data = {
    "sysLogData": {},
    //所有地区数据缓存
    "allsysLogData":null
};

sysLog.event = {
    'isReaded': false,
    onReady: function () {
        sysLog.action.initBody();
    }
};

AppCommon.action.ready(function () {
    if (!sysLog.event.isReaded) {
        sysLog.event.isReaded = true;
        sysLog.event.onReady();
    }
});
