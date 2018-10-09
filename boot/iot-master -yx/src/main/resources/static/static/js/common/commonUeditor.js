/**
 *
 * @param domId    初始化元素id
 * @param width    富文本宽
 * @param height   富文本高
 * @constructor
 */
var CommonUeditor = function (domId, width, height) {

    var domId = domId;

    var umeditorOption = new Object();
    umeditorOption.width = width;
    umeditorOption.height = height;

    var um;

    var serverPath = AppCommon.url.getBaseURL() + "/files/ueditorUpload";

    /**
     * 初始化
     */
    this.init = function () {
        um = UM.getEditor(domId, {
            imageUrl: serverPath,
            imagePath: "",
            lang: /^zh/.test(navigator.language || navigator.browserLanguage || navigator.userLanguage) ? 'zh-cn' : 'en',
            langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
            focus: true,
            autoHeightEnabled: false,
            initialFrameWidth: umeditorOption.width != null ? umeditorOption.width : 500,
            initialFrameHeight: umeditorOption.height != null ? umeditorOption.height : 400

        });
    };

    /**
     * 获取html代码
     */
    this.getContentHtml = function () {
        return um.getContent();
    };


    /**
     * 纯文本信息
     * @returns {*}
     */
    this.getContentText = function () {
        return um.getPlainTxt();
    };
};