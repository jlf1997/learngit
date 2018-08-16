package com.cimr.api.dev.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.model.TerimalModel;
import com.cimr.api.dev.jpa.mgr.TerminalJpa;
import com.cimr.api.dev.model.mgr.Terminal;
import com.cimr.boot.jpafinder.DefindSelect;
import com.cimr.boot.jpafinder.Finder;
import com.cimr.boot.jpafinder.SpringDateJpaOper;

@Service("devInfoService")
public class TerminalService extends Finder<Terminal,Long>{

	@Autowired
	private TerminalJpa terminalJpa;
	
	@Override
	public JpaSpecificationExecutor<Terminal> specjpa() {
		// TODO Auto-generated method stub
		return terminalJpa;
	}

	@Override
	public JpaRepository<Terminal, Long> jpa() {
		// TODO Auto-generated method stub
		return terminalJpa;
	}

	@Override
	public void addWhere(Terminal[] t, List<Predicate> predicates, Root<Terminal> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(Terminal t) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 根据ids 查询设备列表
	 * @param ids
	 * @return
	 */
	public List<Terminal> findDevsByIds(List<String>  ids){
		
		List<Terminal> devs = terminalJpa.findAll(getDefinedSpecification(new DefindSelect<Terminal>() {

			@Override
			public void find(Root<Terminal> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates,
					SpringDateJpaOper<Terminal> sdjo, Terminal... t) {
				// TODO Auto-generated method stub
//				String[] idStr = ids.split(",");
				List<Predicate> predicateList = new ArrayList<>();
				for(String id:ids)  {
					if(id!=null) {
						predicateList.add(sdjo.eq( "terminalId", id));
					}
					
					
				}
				
				Predicate p = cb.or(predicateList.toArray(new Predicate[predicateList.size()]));
				predicates.add(p);
			}

			

			
			
		}));
		return devs;
	}
	
}
