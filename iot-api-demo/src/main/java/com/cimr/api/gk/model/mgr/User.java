package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Table
@Entity
public class User extends BaseEntity{

	/**
	 * 用户id
	 */
	@Id
	private Long userid;
	
	/**
	 * 用户编号
	 */
	public String userNum;
	
	public String userName;
	
	
	@ManyToMany(mappedBy="users",targetEntity=ApplicationInfo.class)
	public List<ApplicationInfo> applicationInfos;
	
	@OneToMany(mappedBy="user",targetEntity=Terminal.class)
	public List<Terminal> terminals;
}
