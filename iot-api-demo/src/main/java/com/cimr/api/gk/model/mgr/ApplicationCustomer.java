package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="tb_application_customer")
@Entity
@org.hibernate.annotations.Table(comment="客户项目关系表", appliesTo = "tb_application_customer")
public class ApplicationCustomer {
	
	@Id
	public String id;

	
	@ManyToOne
	public Customer customer;
	
	@ManyToOne
	public ApplicationInfo applicationInfo;
	
}
