package ${templateInfo.packageName};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.${templateInfo.companyName}.ssm.${templateInfo.projectName}.model.${tableModel.domainName};


/**
 * 类${tableModel.domainName}ServiceImpl的实现描述：${tableModel.tableChineseName}
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */

public interface ${tableModel.domainName}Jpa extends JpaRepository<${tableModel.domainName}, ${tableModel.pkClass}>,JpaSpecificationExecutor<${tableModel.domainName}>  {
    
}