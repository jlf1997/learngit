package ${templateInfo.packageName};

import com.cimr.comm.base.IotBaseDao;
import com.${templateInfo.companyName}.${templateInfo.projectName}.model.${tableModel.domainName};


/**
 * ${tableModel.tableChineseName}的数据访问层
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */
public interface ${tableModel.domainName}Dao extends IotBaseDao<${tableModel.domainName},String> {
    
	//TODO 在此添加其他数据访问的方法
}