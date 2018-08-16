package com.cimr.api.dev.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "signals")
public class Signal extends BaseEntity{
    //id
	@Id
    private String id;

    //信号名称
    private String signalName;

    //租户ID
    private String tenantId;

    //项目ID
    private String projectId;

    //解析程序名称
    private String handlerName;

    //备注
    private String remarks;


    //解析程序
    private byte[] handlerClass;


    public Signal(){

    }

    public Signal(Signal signal){
        this.id = signal.getId();
        this.signalName = signal.getSignalName();
        this.tenantId = signal.getTenantId();
        this.projectId = signal.getProjectId();
        this.handlerName = signal.getHandlerName();
        this.remarks = signal.getRemarks();
        setUpdateBy( signal.getUpdateBy());
        setUpdateTime(signal.getUpdateTime());
        setCreateTime(signal.getCreateTime());
        setCreateBy(signal.getCreateBy());
        setDelFlag(signal.getDelFlag());
        this.handlerClass = signal.getHandlerClass();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName == null ? null : signalName.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName == null ? null : handlerName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }


    public byte[] getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(byte[] handlerClass) {
        this.handlerClass = handlerClass;
    }

}