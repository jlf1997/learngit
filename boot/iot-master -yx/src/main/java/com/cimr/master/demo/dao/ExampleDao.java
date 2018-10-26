package com.cimr.master.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cimr.comm.base.BaseDao;
import com.cimr.demo.model.Example;

/**
 * Created by liqi on 2017/11/24.
 * liqiwork@qq.com
 */
public interface ExampleDao {
    List<Example> selectList(@Param("title") String title);
}
