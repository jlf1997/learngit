package com.cimr.api.gk.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="tb_permission_role")
@Entity
@org.hibernate.annotations.Table(comment="角色资源关系表", appliesTo = "tb_permission_role")
public class PermissionRole {

	
	@Id
	public String id;

	
	@ManyToOne
	public Permission permission;
	
	@ManyToOne
	public Role role;
}
