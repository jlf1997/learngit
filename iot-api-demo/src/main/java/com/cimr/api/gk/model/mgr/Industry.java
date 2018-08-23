package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity
public class Industry extends BaseEntity{

	
	public String name;
	
	
	@Id
	public Long id;
	
	@OneToMany(mappedBy="industry")
	public List<Supplier> suppliers;

	
	
}
