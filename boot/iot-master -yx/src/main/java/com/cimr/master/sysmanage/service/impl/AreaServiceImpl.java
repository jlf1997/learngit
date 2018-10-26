package com.cimr.master.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cimr.sysmanage.dao.AreaDao;
import com.cimr.sysmanage.model.Area;
import com.cimr.sysmanage.service.AreaService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

/**
 * <p>Title: SysAreaServiceImpl</p>
 * <p>Description: 地区数据Service接口实现</p>
 */
@Service("areaService")
@Transactional
public class AreaServiceImpl  implements AreaService {

    @Resource
    private AreaDao dao;
    
    @Override
	public int addNewArea(Area area) {
    	Assist assist = null;
        if (StringUtil.empty(area.getPid())) {
            area.setPid("1");
        }
        Area temp = new Area();
        temp.setAreaId(area.getPid());
        temp.setCountryCode(area.getCountryCode());
        Area parent = this.selectObjByObj_common(temp);
        if (parent != null) {
            area.setpTitle(parent.getTitle());
        }
        // 父级对象存在check

        // 1.添加根节点的情况：直接添加
        // 地区父级id pid = 1

        // 2.添加非根节点的情况：check
        if (!StringUtil.empty(area.getPid()) && !"1".equals(area.getPid())) {
        	assist = new Assist();
        	assist.setRequires(Assist.andEq("area_id", area.getPid()));
        	long count = this.getObjRowCount_common(assist);
            if (count == 0) {
                return 0;
            }
        }

        // 3. 添加地区已存在
        assist = new Assist();
        assist.setRequires(Assist.andEq("area_id", area.getAreaId()));
        long count = this.getObjRowCount_common(assist);
        if (count > 0) {
            return 0;
        }

        // 添加数据
        return this.insertNonEmptyObj_common(area);
	}

	@Override
	public int updateArea(Area area) {
		Assist assist = null;
		// 1. 更新对象存在check（不在此处做检查）
        // 2.更新非根节点的情况：check
		if (!StringUtil.empty(area.getPid()) && !"1".equals(area.getPid())) {
        	assist = new Assist();
        	assist.setRequires(Assist.andEq("area_id", area.getPid()));
        	long count = this.getObjRowCount_common(assist);
            if (count == 0) {
                return 0;
            }
        }
		
		//添加根节点的情况
		if (StringUtil.empty(area.getPid())) {
            area.setPid("1");
        }
        Area temp = new Area();
        temp.setAreaId(area.getPid());
        temp.setCountryCode(area.getCountryCode());
        Area parent = this.selectObjByObj_common(temp);
        if (parent != null) {
            area.setpTitle(parent.getTitle());
        }
        // 更新数据
        return this.updateNonEmptyObjById_common(area);
	}
	
	@Override
	public void deleteAreaById(String id) {
		List<String> areaIdsForDelete = new ArrayList<>();
		areaIdsForDelete.add(id);
		Area area = this.selectObjById_common(id);
		getAreaSonsIds(area.getAreaId(), areaIdsForDelete);
		if (areaIdsForDelete.size() > 1) {
			//有子项的全部删除
			Assist assist = new Assist();
			assist.setRequires(Assist.andIn("id", areaIdsForDelete));
			this.deleteObj_common(assist);
		} else{
			//单独删除
			this.deleteAreaById(id);
		}
		
	}
	
	/**
	 * 方法描述: 获取所有多级子项列表
	 * @param areaId
	 * @param areaIdsList
	 * 		void
	 * 作者:    admin
	 * 创建时间: 2018年4月28日 上午11:02:41
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	private void getAreaSonsIds(String areaId, List<String> areaIdsList) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("pid", areaId));
		List<Area> sonsAreaList = this.selectObj_common(assist);
		if (null != sonsAreaList && sonsAreaList.size() > 0) {
			//存在子项
			for (Area sonArea : sonsAreaList) {
				areaIdsList.add(sonArea.getId());
				//迭代获取子项
				getAreaSonsIds(sonArea.getAreaId(), areaIdsList);
			}
		}
	}

    /*@Override
    public List<Area> getList(Area area) {
        return areaDao.selectList_commom(area);
    }

    @Override
    public Integer getListCount(String pid, String areaId, Long countryCode, String title, String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("areaId", areaId);
        map.put("pid", pid);
        map.put("countryCode", countryCode);
        map.put("title", title);
        map.put("code", code);
        return areaDao.findListCount(map);
    }

    @Override
    public void insert(String pid, String areaId, String title,
                       String code, Long countryCode, Float orderId) {

        Area entity = new Area();
        entity.setId(IdUtil.getId().toString());
        if (pid == null || "".equals(pid)) {
            pid = "1";
        }
        Area parent = this.getAreaByAreaId(pid, countryCode);
        if (parent != null) {
            entity.setpTitle(parent.getTitle());
        }
        entity.setPid(pid);
        entity.setAreaId(areaId);
        entity.setTitle(title);
        entity.setCode(code);
        entity.setCountryCode(countryCode);
        entity.setOrderId(orderId);
        entity.setBid(null);

        // 父级对象存在check

        // 1.添加根节点的情况：直接添加
        // 地区父级id pid = 1

        // 2.添加非根节点的情况：check
        if (pid != null && !"".equals(pid) && !"1".equals(pid)) {

            int cnt = this.getListCount(null, pid, null, null, null);

            if (cnt == 0) {
                return;
            }
        }

        // 3. 添加地区已存在
        int cnt = this.getListCount(null, areaId, null, null, null);
        if (cnt != 0) {
            return;
        }

        // 添加数据
        areaDao.insertObject_common(entity);

    }*/

    /*@Override
    public void update(String id, String pid, String areaId, String title,
                       String code, Long countryCode, Float orderId) {

        // 1. 更新对象存在check

        Area sa = areaDao.selectById_common(id);
        if (sa == null) {
            return;
        }
        // 2.更新非根节点的情况：check
        if (pid != null && !"".equals(pid) && !"1".equals(pid)) {

            int cnt = this.getListCount(null, pid, null, null, null);

            if (cnt == 0) {
                // 是子节点&父节点不存在
                return;
            }
        }

        Area entity = new Area();
        entity.setId(id);
        if (pid == null || "".equals(pid)) {
            pid = "1";
        }
        Area parent = this.getAreaByAreaId(pid, countryCode);
        if (parent != null) {
            entity.setpTitle(parent.getTitle());
        }
        // 更新字段
        entity.setPid(pid);
        entity.setAreaId(areaId);
        entity.setTitle(title);
        entity.setCode(code);
        entity.setCountryCode(countryCode);
        entity.setOrderId(orderId);
        // 更新数据
        areaDao.updateObject_common(entity);

    }

    @Override
    public Area getAreaByAreaId(String areaId, Long countryCode) {
        Area area = null;
        Area temp = new Area();
        temp.setAreaId(areaId);
        temp.setCountryCode(countryCode);
        List<Area> list = this.getList(temp);
        if (list.size() > 0) {
            area = list.get(0);
        }
        return area;
    }

    @Override
    public void deleteById(String areaId) {
        Area area = new Area();
        area.setId(areaId);
        areaDao.deleteObject_common(area);
    }

    @Override
    public Area getById(String id) {
        return areaDao.selectById_common(id);
    }*/
    
    //公共方法-------开始
    
    @Override
	public long getObjRowCount_common(Assist assist) {
    	if (null == assist) {
    		assist = new Assist();
    	}
    	assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<Area> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}
	
	@Override
	public PageData<Area> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<Area> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<Area> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public Area selectObjByObj_common(Area obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public Area selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(Area value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(Area value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<Area> value) {
		for (Area temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		Area temp = new Area();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		Area temp = new Area();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(Area enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(Area value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(Area enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(Area value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(Area enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(Area enti, Assist assist) {
		return 0;
	}

	//公共方法-------结束
}
