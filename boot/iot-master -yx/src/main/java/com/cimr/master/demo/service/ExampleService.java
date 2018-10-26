package com.cimr.master.demo.service;

import java.util.List;

import com.cimr.comm.base.BaseService;
import com.cimr.demo.model.Example;

/**
 * Created by liqi on 2017/11/24.
 * liqiwork@qq.com
 */
public interface ExampleService {
    public List<Example> selectList(String title);
}
