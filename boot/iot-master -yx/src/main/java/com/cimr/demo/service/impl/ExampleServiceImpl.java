package com.cimr.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.demo.dao.ExampleDao;
import com.cimr.demo.model.Example;
import com.cimr.demo.service.ExampleService;

/**
 * Created by liqi on 2017/11/24.
 * liqiwork@qq.com
 */
@Service
public class ExampleServiceImpl  implements ExampleService {

    @Autowired
    private ExampleDao exampleDao;

    @Override
    public List<Example> selectList(String title) {
        return exampleDao.selectList(title);
    }

}
