package com.cimr.api.dev.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.jpafinder.DBFinder;
import com.cimr.boot.jpafinder.Oper;

/**
 * Created by admin on 2018/2/26.
 */
@Entity
@Table(name = "terminal")
public class Terminal extends BaseEntity {
    /**
     * 主键id
     */
	@Id
    private String id;

    /**
     * 设备名称
     */
	@DBFinder(added=true,opType=Oper.LIKE)
    private String terminalName;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     *租户编号
     */
    private String tenantNo;
    /**
     *消息版本
     */
    private String msgVersion;
    /**
     *有效性
     */
    private String validation;
    /**
     *默认信号编号
     */
    private String defaultSignalId;
    /**
     *默认设备编号
     */
    private String defaultDeviceNo;
    /**
     *解析模式
     */
    private String analysisMode;
    /**
     *缓存模式
     */
    private String cacheMode;
    /**
     *心跳检查
     */
    private String heartCheck;
    /**
     *消息确认模式
     */
    private String dataConfirm;
    /**
     *加密模式
     */
    private String encryptMode;
    /**
     * 保存原始数据
     */
    private String originalRetain;
    /**
     * 调试模式
     */
    private String debugMode;
    /**
     *认证秘钥
     */
    private String secretKey;
    /**
     *备注
     */
    private String remark;

    /**
     * 通信质量等级
     */
    private String qosValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getTenantNo() {
        return tenantNo;
    }

    public void setTenantNo(String tenantNo) {
        this.tenantNo = tenantNo;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    public void setMsgVersion(String msgVersion) {
        this.msgVersion = msgVersion;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getDefaultSignalId() {
        return defaultSignalId;
    }

    public void setDefaultSignalId(String defaultSignalId) {
        this.defaultSignalId = defaultSignalId;
    }

    public String getDefaultDeviceNo() {
        return defaultDeviceNo;
    }

    public void setDefaultDeviceNo(String defaultDeviceNo) {
        this.defaultDeviceNo = defaultDeviceNo;
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    public String getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(String cacheMode) {
        this.cacheMode = cacheMode;
    }

    public String getHeartCheck() {
        return heartCheck;
    }

    public void setHeartCheck(String heartCheck) {
        this.heartCheck = heartCheck;
    }

    public String getDataConfirm() {
        return dataConfirm;
    }

    public void setDataConfirm(String dataConfirm) {
        this.dataConfirm = dataConfirm;
    }

    public String getEncryptMode() {
        return encryptMode;
    }

    public void setEncryptMode(String encryptMode) {
        this.encryptMode = encryptMode;
    }

    public String getOriginalRetain() {
        return originalRetain;
    }

    public void setOriginalRetain(String originalRetain) {
        this.originalRetain = originalRetain;
    }

    public String getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQosValue() {
        return qosValue;
    }

    public void setQosValue(String qosValue) {
        this.qosValue = qosValue;
    }
}
