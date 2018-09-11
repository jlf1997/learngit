package com.cimr.api.auth.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.auth.jpa.AuthenticationModelJpa;
import com.cimr.api.auth.model.AuthenticationModel;
import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.jpafinder.FindBase;
import com.cimr.boot.utils.IdGener;


@Service
public class AuthenticationModelService extends FindBase<AuthenticationModel, Long>{
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationModelService.class);

	
	@Autowired
	private AuthenticationModelJpa authenticationModel;

	@Override
	public JpaSpecificationExecutor<AuthenticationModel> specjpa() {
		// TODO Auto-generated method stub
		return authenticationModel;
	}

	@Override
	public JpaRepository<AuthenticationModel, Long> jpa() {
		// TODO Auto-generated method stub
		return authenticationModel;
	}

	@Override
	public void addWhere(AuthenticationModel[] t, List<Predicate> predicates, Root<AuthenticationModel> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(AuthenticationModel t) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional
	public HttpResult genKey() throws NoSuchAlgorithmException {
		AuthenticationModel authenticationModel = new AuthenticationModel();
		Long id = IdGener.getInstance().getNormalId();
		authenticationModel.setId(id);
		authenticationModel.setAppid("CIMR"+id);
		String appSecrect = genAppSecrect(30);
		authenticationModel.setAppSecret(appSecrect);
		this.save(authenticationModel);
		HttpResult result = new HttpResult();
		result.setSuccess(true);
		result.setData(authenticationModel);
		log.info("gen key:appid {} appkey:{}","CIMR"+id,appSecrect);
		return result;
	}
	
	
	

	public String guid() {
		UUID uuid = UUID.randomUUID();
		String key = uuid.toString();
		return key;
	}
	

	public String genAppSecrect(int num) throws NoSuchAlgorithmException {
		StringBuilder code = new StringBuilder();
		ArrayList<String> temp = new ArrayList<String>();
		temp.addAll(Arrays.asList(lowercase));
		temp.addAll(Arrays.asList(capital));
		temp.addAll(Arrays.asList(number));
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		for (int i = 0; i < num; i++) {
			   code.append(temp.get(sr.nextInt(temp.size())));
		}
		return code.toString();
	}
	
	 private static String[] lowercase = {
			   "a","b","c","d","e","f","g","h","i","j","k",
			   "l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

			 private static String[] capital = {
			   "A","B","C","D","E","F","G","H","I","J","K",
			   "L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 

			 private static String[] number = {
			   "1","2","3","4","5","6","7","8","9","0"};

			 private static String[] sign = {
			   "~","!","@","#","$","%","^","&","*","(",")","_","+","`","-","=",
			   "{","}","|",":","\"","<",">","?",
			   "[","]","\\",";","'",",",".","/"};


}
