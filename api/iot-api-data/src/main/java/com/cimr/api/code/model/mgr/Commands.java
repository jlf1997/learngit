package com.cimr.api.code.model.mgr;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.api.dev.model.mgr.BaseEntity;
import com.cimr.boot.jpafinder.DBFinder;
import com.cimr.boot.jpafinder.Oper;

/**
 * 
 * <p>Title: Projects</p>
 * @author  lailichun
 */
@Table(name="commands")
@Entity
public class Commands extends BaseEntity{



	/**
	 * 主键id
	 */
	@Id
	@DBFinder(added=true,opType=Oper.EQ)
	private String Id;
	
//	/**
//	 * 指令编号
//	 */;
//	private String number;
//	/**
//	 * 指令名称
//	 */;
//	private String name;
//
//	/**
//	 * 指令类型
//	 */;
//	private String type;
//	/**
//	 * 项目编号
//	 */;
//	private String projectId;
//	/**
//	 * 所属租户ID
//	 */;
//	private String tenantId;
//	/**
//	 * 所属租户ID
//	 */;
//	private String allTenantId;
//	/**
//	 * 所属项目名称
//	 */
//	private String projectName;
//	/**
//	 * 所属租户名称
//	 */
//	private String tenantName;
	/**
	 * 应用许可
	 */
	private String appLicense;
//	/**
//	 * 协议版本
//	 */
//	private String protocolVersion;
	/**
	 *指令内容
	 */;
	private String contents;
//	
//	/**
//	 * 备注
//	 */;
//
//	private String remarks;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getAppLicense() {
		return appLicense;
	}
	public void setAppLicense(String appLicense) {
		this.appLicense = appLicense;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}