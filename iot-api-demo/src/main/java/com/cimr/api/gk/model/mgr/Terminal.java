package com.cimr.api.gk.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity
public class Terminal extends BaseEntity{

	@Id
	public Long id;
	
	@ManyToOne
	@JoinColumn(name="appliction_id",columnDefinition="bigint COMMENT '应用信息 '")
	public ApplicationInfo applicationInfo;
	
	@ManyToOne
	@JoinColumn(name="user_id",columnDefinition="bigint COMMENT '用户信息 '")
	public User user;
	
	@OneToOne
	public SimCard simCard;
}
