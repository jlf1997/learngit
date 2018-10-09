package com.cimr.comm.base;

import com.cimr.util.Assist;
import com.cimr.util.PageData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by suhuanzhao on 2018/5/17.
 */
public abstract class BaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements BaseService<T,PK>{

    protected abstract BaseDao<T, PK> getEntityDao();



    @Override
    public long getObjRowCount_common(Assist assist) {
        if (null == assist) {
            assist = new Assist();
        }
        assist.setRequires(Assist.andEq("del_flag", 0));
        return this.getEntityDao().getObjRowCount_common(assist);
    }

    @Override
    public List<T> selectObj_common(Assist assist) {
        if (null == assist) {
            assist = new Assist();
        }
        assist.setRequires(Assist.andEq("del_flag", 0));
        return this.getEntityDao().selectObj_common(assist);
    }

    @Override
    public PageData<T> selectObj_common(Assist assist, int page, int limit) {
        if (null == assist) {
            assist = new Assist();
        }
        PageData<T> pageData = new PageData<>();
        Long count = this.getObjRowCount_common(assist);
        int startRow = limit * (page - 1);
        assist.setStartRow(startRow);
        assist.setRowSize(limit);
        List<T> list = this.getEntityDao().selectObj_common(assist);
        pageData.setCount(count.intValue());
        pageData.setList(list);
        return pageData;
    }

    @Override
    public T selectObjByObj_common(T obj) {
        return this.getEntityDao().selectObjByObj_common(obj);
    }

    @Override
    public T selectObjById_common(PK id) {
        return this.getEntityDao().selectObjById_common(id);
    }

    @Override
    public int insertObj_common(T value) {
        value.preInsert();
        return this.getEntityDao().insertObj_common(value);
    }

    @Override
    public int insertNonEmptyObj_common(T value) {
        value.preInsert();
        return this.getEntityDao().insertNonEmptyObj_common(value);
    }

    @Override
    public int insertObjByBatch_common(List<T> value) {
        for (T temp : value) {
            temp.preInsert();
        }
        return this.getEntityDao().insertObjByBatch_common(value);
    }

    @Override
    public int deleteObjById_common(PK id) {
        return getEntityDao().deleteObjById_common(id);
    }

    @Override
    public int deleteObj_common(Assist assist) {
        return getEntityDao().deleteObj_common(assist);
    }


    @Override
    public int deleteObjById_common(T enti) {
        enti.setDelFlag(1);
        enti.preUpdate();
        return this.getEntityDao().updateNonEmptyObjById_common(enti);
    }


    @Override
    public int deleteObj_common(T enti,Assist assist) {
        enti.setDelFlag(1);
        enti.preUpdate();
        return this.getEntityDao().updateNonEmptyObj_common(enti, assist);
    }

    @Override
    public int updateObjById_common(T enti) {
        enti.preUpdate();
        return this.getEntityDao().updateObjById_common(enti);
    }

    @Override
    public int updateObj_common(T value, Assist assist) {
        value.preUpdate();
        return this.getEntityDao().updateObj_common(value, assist);
    }

    @Override
    public int updateNonEmptyObjById_common(T enti) {
        enti.preUpdate();
        return this.getEntityDao().updateNonEmptyObjById_common(enti);
    }

    @Override
    public int updateNonEmptyObj_common(T value, Assist assist) {
        value.preUpdate();
        return this.getEntityDao().updateNonEmptyObj_common(value, assist);
    }
}
