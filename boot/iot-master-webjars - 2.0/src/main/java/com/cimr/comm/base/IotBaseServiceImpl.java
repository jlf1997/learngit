package com.cimr.comm.base;


import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

import java.io.Serializable;
import java.util.List;

/**
 * 单表增删改查的service接口实现类
 */
public abstract class IotBaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements IotBaseService<T,PK> {

    protected abstract IotBaseDao<T, PK> getEntityDao();

    @Override
    public long getObjRowCountCommon(Assist assist) {
        if (null == assist) {
            assist = new Assist();
        }
        return this.getEntityDao().getObjRowCountCommon(assist);
    }

    @Override
    public List<T> selectListCommon(Assist assist) {
        if (null == assist) {
            assist = new Assist();
        }
        assist.setRequires(Assist.andEq("del_flag","0"));
        return this.getEntityDao().selectListCommon(assist);
    }

    @Override
    public PageData<T> selectPageCommon(Assist assist, int page, int limit) throws Exception{
        if (null == assist) {
            assist = new Assist();
        }
        assist.setRequires(Assist.andEq("del_flag","0"));
        PageData<T> pageData = new PageData<>();
        //获取数量统计
        Long count = this.getObjRowCountCommon(assist);
        //计算查询起始索引
        int startRow = limit * (page - 1);
        assist.setStartRow(startRow);
        assist.setRowSize(limit);
        //列表查询
        List<T> list = this.getEntityDao().selectListCommon(assist);
        pageData.setCount(count.intValue());
        pageData.setList(list);
        return pageData;
    }

    @Override
    public T selectObjByObjCommon(T obj) {
        return this.getEntityDao().selectObjByObjCommon(obj);
    }

    @Override
    public T selectObjByIdCommon(PK id) {
        return this.getEntityDao().selectObjByIdCommon(id);
    }

    @Override
    public HttpResult insertObjCommon(T value) {
        HttpResult result = new HttpResult(true,"添加成功");
        value.preInsert();
        int code = this.getEntityDao().insertObjCommon(value);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult insertNonEmptyObjCommon(T value) {
        HttpResult result = new HttpResult(true,"添加成功");
        value.preInsert();
        int code = this.getEntityDao().insertNonEmptyObjCommon(value);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult insertObjByBatchCommon(List<T> value) {
        HttpResult result = new HttpResult(true,"添加成功");
        for (T temp : value) {
            temp.preInsert();
        }
        int code = this.getEntityDao().insertObjByBatchCommon(value);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult deleteObjByIdCommon(PK id) {
        HttpResult result = new HttpResult(true,"删除成功");
        int code = getEntityDao().deleteObjByIdCommon(id);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult deleteObjCommon(Assist assist) {
        HttpResult result = new HttpResult(true,"删除成功");
        int code = getEntityDao().deleteObjCommon(assist);
        result.setCode(code);
        return result;
    }


    @Override
    public HttpResult deleteObjByIdLogicCommon(T enti) {
        HttpResult result = new HttpResult(true,"删除成功");
        enti.setDelFlag(1);
        enti.preUpdate();
        int code = this.getEntityDao().updateNonEmptyObjByIdCommon(enti);
        result.setCode(code);
        return result;
    }


    @Override
    public HttpResult deleteObjLogicCommon(T enti, Assist assist) {
        HttpResult result = new HttpResult(true,"删除成功");
        enti.setDelFlag(1);
        enti.preUpdate();
        int code = this.getEntityDao().updateNonEmptyObjCommon(enti, assist);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult updateObjByIdCommon(T enti) {
        HttpResult result = new HttpResult(true,"更新成功");
        enti.preUpdate();
        int code = this.getEntityDao().updateObjByIdCommon(enti);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult updateObjCommon(T value, Assist assist) {
        HttpResult result = new HttpResult(true,"更新成功");
        value.preUpdate();
        int code = this.getEntityDao().updateObjCommon(value, assist);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult updateNonEmptyObjByIdCommon(T enti) {
        HttpResult result = new HttpResult(true,"更新成功");
        enti.preUpdate();
        int code = this.getEntityDao().updateNonEmptyObjByIdCommon(enti);
        result.setCode(code);
        return result;
    }

    @Override
    public HttpResult updateNonEmptyObjCommon(T value, Assist assist) {
        HttpResult result = new HttpResult(true,"更新成功");
        value.preUpdate();
        int code = this.getEntityDao().updateNonEmptyObjCommon(value, assist);
        result.setCode(code);
        return result;
    }
}
