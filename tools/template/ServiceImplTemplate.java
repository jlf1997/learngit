package ${templateInfo.packageName};

import com.cimr.comm.base.IotBaseDao;
import com.cimr.comm.base.IotBaseServiceImpl;
import com.${templateInfo.companyName}.${templateInfo.projectName}.model.${tableModel.domainName};
import com.${templateInfo.companyName}.${templateInfo.projectName}.dao.${tableModel.domainName}Dao;
import com.${templateInfo.companyName}.${templateInfo.projectName}.service.${tableModel.domainName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 类${tableModel.domainName}ServiceImpl的实现描述：${tableModel.tableChineseName}
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */
@Service
public class ${tableModel.domainName}ServiceImpl extends IotBaseServiceImpl<${tableModel.domainName},String> implements ${tableModel.domainName}Service  {
    
	@Autowired
    private ${tableModel.domainName}Dao   ${tableModel.domainNameWithFirstCaseLower}Dao;

    @Override
    protected IotBaseDao<${tableModel.domainName}, String> getEntityDao() {
        return ${tableModel.domainNameWithFirstCaseLower}Dao;
    }
}