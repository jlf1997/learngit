package com.cimr.master.util;

import com.cimr.sysmanage.service.CommonService;
import com.xiaoleilu.hutool.crypto.SecureUtil;


/**
 * 获取Id的帮助类
 *
 * @author 38464
 */
public class IdUtil {
    private static final CommonService commService
            = SpringContextUtil.getBean("commonService", CommonService.class);


    public static final String TENANT = "tenant";
    public static final String PROJECTS = "projects";
    public static final String TERMINAL = "terminal";
    public static final String BROKENSERVER = "broken_server";
    public static final String SERVERNODE = "server_node";
    public static final String SERVERDYNAMICPARAM = "server_dynamic_param";
    public static final String SERVERFIXEDPARAM = "server_fixed_param";
    public static final String SIGNAL = "signals";

    /**
     * 获取简单的UUID类型的序列
     *
     * @return
     */
    public static String getId() {
        return SecureUtil.simpleUUID();
    }

    /**
     * 根据类型来获取当前表可用的ID,比如终端编号，租户编号，项目编号
     * idType用常量表示 比如：IdUtil.TENANT IdUtil.PROJECTS
     */
    public static String getId(String tableName) {
        String idColName = getIdColName(tableName);
        String idRule = getIdRule(tableName);
        return commService.getId(tableName, idColName, idRule);
    }

    private static String getIdRule(String tableName) {
        String idRule = "";
        switch (tableName) {
            case IdUtil.TENANT:
                idRule = "T%04d";
                break;
            case IdUtil.PROJECTS:
                idRule = "P%05d";
                break;
            case IdUtil.TERMINAL:
                idRule = "TEL%07d";
                break;
            case IdUtil.BROKENSERVER:
                idRule = "B%03d";
                break;
            case IdUtil.SERVERNODE:
                idRule = "SVR%04d";
                break;
            case IdUtil.SERVERDYNAMICPARAM:
                idRule = "D%03d";
                break;
            case IdUtil.SERVERFIXEDPARAM:
                idRule = "F%03d";
                break;
            case IdUtil.SIGNAL:
                idRule = "SIG%03d";
                break;
            default:
                idRule = "T%04d";
                break;
        }
        return idRule;
    }

    private static String getIdColName(String tableName) {
        String idColName = "";
        switch (tableName) {
            case IdUtil.TENANT:
                idColName = "tenant_id";
                break;
            case IdUtil.PROJECTS:
                idColName = "project_id";
                break;
            case IdUtil.TERMINAL:
                idColName = "terminal_id";
                break;
            case IdUtil.BROKENSERVER:
                idColName = "bs_id";
                break;
            case IdUtil.SERVERNODE:
                idColName = "svr_id";
                break;
            case IdUtil.SERVERDYNAMICPARAM:
                idColName = "dynamic_param_id";
                break;
            case IdUtil.SERVERFIXEDPARAM:
                idColName = "fixed_param_id";
                break;
            case IdUtil.SIGNAL:
                idColName = "id";
                break;
            default:
                idColName = "tenant_id";
                break;
        }
        return idColName;
    }

}
