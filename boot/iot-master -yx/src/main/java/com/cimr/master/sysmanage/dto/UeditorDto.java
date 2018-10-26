package com.cimr.sysmanage.dto;

/***
 * @author pxh
 * @date 2017/12/21 13:18
 * @TODO todo
 */
public class UeditorDto {

    /**
     * 输出文件地址
     */
    private String url;
    /**
     * 上传文件名
     */
    private String fileName;
    /**
     * 状态
     */
    private String state;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 原始文件名
     */
    private String originalName;
    /**
     * 文件大小
     */
    private long size = 0;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
