package com.cimr.sysmanage.dao;

import com.cimr.util.Assist;
import org.springframework.stereotype.Repository;

import com.cimr.comm.base.BaseDao;
import com.cimr.sysmanage.model.Group;

import java.util.List;

public interface GroupDao extends BaseDao<Group, String> {
    /**
     * 根据条件查询组织所有子级组织
     * @return
     */
    List<Group> selectSon_common(Group group);

    /**
     * 根据条件查询组织所有父级组织
     * @param groupId
     * @return
     */
    List<Group> selectFather_common(Group group);

    /**
     * 修改时根据条件查询组织所有父级组织
     * @param groupId
     * @return
     */
    List<Group> selectUpdate_common(Group group);

}
