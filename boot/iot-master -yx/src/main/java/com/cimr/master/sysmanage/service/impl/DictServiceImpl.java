package com.cimr.master.sysmanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cimr.sysmanage.dao.DictDao;
import com.cimr.sysmanage.model.Dict;
import com.cimr.sysmanage.service.DictService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

/**
 * <p>Title: SysDictServiceImpl</p>
 */
@Service("sysDictService")
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dao;

    @Override
    public boolean isExist(String type, String value) {
        Assist assist = new Assist();
        assist.setRequires(Assist.andEq("value", value));
        assist.setRequires(Assist.andEq("type", type));
        long dictCount= this.getObjRowCount_common(assist);
        if(dictCount > 0){
            return true;
        }else{
            return false;
        }
    }
    
    //公共方法-----开始
    
    @Override
	public long getObjRowCount_common(Assist assist) {
    	if (null == assist) {
    		assist = new Assist();
    	}
    	assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<Dict> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}
	
	@Override
	public PageData<Dict> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<Dict> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("type, order_id", true));
		List<Dict> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}


	@Override
	public Dict selectObjByObj_common(Dict obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public Dict selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(Dict value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(Dict value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<Dict> value) {
		for (Dict temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		Dict temp = new Dict();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		Dict temp = new Dict();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(Dict enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(Dict value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(Dict enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(Dict value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(Dict enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(Dict enti, Assist assist) {
		return 0;
	}

	//公共方法-----结束
}