package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity
public class ApplicationInfo {

	@Id
	public Long id;
	
	@ManyToMany
	public List<User> users;
	
	@OneToMany(mappedBy="applicationInfo",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	
}
