package com.cimr.api.gk.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="tb_user_role")
@Entity
@org.hibernate.annotations.Table(comment="用户角色关系表", appliesTo = "tb_user_role")
public class UserRole {
	@Id
	public String id;

	
	@ManyToOne
	public User user;
	
	@ManyToOne
	public Role role;
}
