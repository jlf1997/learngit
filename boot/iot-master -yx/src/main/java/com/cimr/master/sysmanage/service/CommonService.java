package com.cimr.sysmanage.service;

/**
 * 公用服务类
 * @author 38464
 *
 */
public interface CommonService{
  /**
   * 通过id类型来获取实体记录需要的ID
   * @param tableName 表名
   * @param idColName id列名
   * @param idRule id生成规则  比如： P0001 TEL0004
   * @return
   */
  String getId(String tableName,String idColName,String idRule);
   
}
