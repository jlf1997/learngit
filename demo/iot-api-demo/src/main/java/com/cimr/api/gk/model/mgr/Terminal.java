package com.cimr.api.gk.model.mgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cimr.api.comm.Base;
import com.cimr.api.comm.BaseEntity;

@Table(name="td_terminal")
@Entity
@org.hibernate.annotations.Table(comment="终端表", appliesTo = "td_terminal") 
public class Terminal extends Base{

	@Id
	public String id;
	
	@ManyToOne
	@JoinColumn(name="appliction_id",columnDefinition="varchar(255) COMMENT '所属应用 '")
	public ApplicationInfo applicationInfo;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="varchar(255) COMMENT '所属客户 '")
	public Customer customer;
	
	@OneToOne
	@JoinColumn(name="sim_card_id",columnDefinition="varchar(255) COMMENT 'sim卡 '")
	public SimCard simCard;
	
	@ManyToOne
	@JoinColumn(name="supplier_id",columnDefinition="varchar(255) COMMENT '供应商信息 '")
	public Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name="terminal_type_id",columnDefinition="varchar(255) COMMENT '终端类型信息 '")
	public TerminalType terminalType;
	
	
	/**
	 * 状态
	 */
	@Column(name="status",columnDefinition="int COMMENT '状态 '",nullable=false)
	public Integer status;
	
	@Column(name="remark",columnDefinition="varchar(255) COMMENT '备注 '",nullable=false)
	public String remark;
	
	 /**
     * 设备名称
     */
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
     * 通信质量等级
     */
    private String qosValue;

    /**
     * 信号编号长度
     */
    private Integer signalIdLength;

    /**
     * 终端类型
     */
    private String type;

    /**
     * 终端升级程序包ID
     */
    private String terminalUpgradeId;

    /**
     * 公共处理器
     */
    private String publicHandlerName;

    /**
     * 公共预处理器
     */
    private String publicPreHandlerName;

    /**
     * 绑定的预警规则
     */
    private String alarmRuleId;

    /**
     * 故障恢复时间
     */
    private Integer faultRecoverTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SimCard getSimCard() {
		return simCard;
	}

	public void setSimCard(SimCard simCard) {
		this.simCard = simCard;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getSignalIdLength() {
		return signalIdLength;
	}

	public void setSignalIdLength(Integer signalIdLength) {
		this.signalIdLength = signalIdLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTerminalUpgradeId() {
		return terminalUpgradeId;
	}

	public void setTerminalUpgradeId(String terminalUpgradeId) {
		this.terminalUpgradeId = terminalUpgradeId;
	}

	public String getPublicHandlerName() {
		return publicHandlerName;
	}

	public void setPublicHandlerName(String publicHandlerName) {
		this.publicHandlerName = publicHandlerName;
	}

	public String getPublicPreHandlerName() {
		return publicPreHandlerName;
	}

	public void setPublicPreHandlerName(String publicPreHandlerName) {
		this.publicPreHandlerName = publicPreHandlerName;
	}

	public String getAlarmRuleId() {
		return alarmRuleId;
	}

	public void setAlarmRuleId(String alarmRuleId) {
		this.alarmRuleId = alarmRuleId;
	}

	public Integer getFaultRecoverTime() {
		return faultRecoverTime;
	}

	public void setFaultRecoverTime(Integer faultRecoverTime) {
		this.faultRecoverTime = faultRecoverTime;
	}
    
    
}
