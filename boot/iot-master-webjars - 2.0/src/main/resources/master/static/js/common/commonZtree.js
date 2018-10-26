var CommonZtree = function (domId, url, param) {

    var that = this;

    var reqObj = new Object();
    reqObj.url = url;
    reqObj.param = param;

    var ztreeSetting = {
        data: {
            simpleData: {
                enable: true
            }
        }
        , check: null
        , callback: null
        , expandAll:true
    };

    /**
     * 是否显示checkbox
     * @type {{enable: boolean}}
     */
    var ztreeCheck = {
        enable: false
    };

    /**
     * ztree回调
     * @type {{oncheck: null, onClick: null, beforeExpand: null}}
     */
    var ztreeCallback = {
        //选中复选框回调
        onCheck: null
        //单击回调
        , onClick: null
        //节点展开之前回调
        , beforeExpand: null

    };

    var ztreeView = {
        dblClickExpand: false,
        showLine: true
    };

    ztreeSetting.check = ztreeCheck;
    ztreeSetting.callback = ztreeCallback;
    ztreeSetting.view = ztreeView;

    var ztreeObj = new Object();
    ztreeObj.domId = domId;
    ztreeObj.treeObj = null;

    /**
     * 设置是否显示checkbox
     * @param check
     */
    this.setZtreeCheck = function (check) {
        ztreeCheck.enable = check;
    };

    this.setZtreeCallback = function (onClick, onCheck, beforeExpand) {
        if (typeof onClick === "function") {
            ztreeCallback.onClick = onClick;
        }
        if (typeof onCheck === "function") {
            ztreeCallback.onCheck = onCheck;
        }
        if (typeof beforeExpand === "function") {
            ztreeCallback.beforeExpand = beforeExpand;
        }
    };

    /**
     * ztree初始化
     * @param data
     */
    this.ztreeInit = function (data) {
        if (data == null) {
            AppCommon.ajax.execute({
                'url': AppCommon.url.getBaseURL() + reqObj.url,
                'data': reqObj.param,
                'success': function (data) {
                    if (data.success) {
                        $(data.data).each(function (index, item) {
                            item.isParent = item.parent;
                            item.parent = null;
                        });
                        ztreeObj.treeObj = $.fn.zTree.init($("#" + ztreeObj.domId), ztreeSetting, data.data);
                        ztreeObj.treeObj.expandAll(true);
                    }
                }
            });
        } else {
            ztreeObj.treeObj = $.fn.zTree.init($("#" + ztreeObj.domId), ztreeSetting, data);
        }
    };

    /**
     *  勾选全部节点（check=true）、未选中（check=false）的节点
     * @param check
     */
    this.checkAllNodes = function (check) {
        ztreeObj.treeObj.checkAllNodes(check);
    };


    /***
     * 展开或折叠所有节点  flag=true 展开；flag=false折叠
     * @param flag
     */
    this.expandAll = function (flag) {
        ztreeObj.treeObj.expandAll(flag);
    };

    /**
     * 获取 被勾选 的节点集合
     */
    this.getCheckedNodes = function () {
        return ztreeObj.treeObj.getCheckedNodes(true);
    };

    /**
     * 获取 zTree 的全部节点数据
     * @returns {*}
     */
    this.getNodes = function () {
        return ztreeObj.treeObj.getNodes();
    };


    /***
     * callback function
     */

    this.beforeExpand = function (treeId, treeNode) {
        ztreeObj.treeObj.expandAll(false);
    };

    this.transFormToArrray=function(nodes){
        return ztreeObj.treeObj.transformToArray(nodes);
    }

};