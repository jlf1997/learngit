package ${templateInfo.packageName};

/**
 * ${tableModel.domainName}Dto类
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */

import com.${templateInfo.companyName}.${templateInfo.projectName}.model.${tableModel.domainName};

public class ${tableModel.domainName}Dto extends ${tableModel.domainName}{
    
    public ${tableModel.domainName}Dto() {
        super();
    }

    public ${tableModel.domainName}Dto(${tableModel.domainName} ${templateInfo.model}) {
<#list tableModel.columnList as columnModel>
        set${columnModel.columnNameFirstUpper}(${templateInfo.model}.get${columnModel.columnNameFirstUpper}());
</#list>
    }
	
    //创建者名称
    private String createByName;
    //更新者名称
    private String updateByName;
    //格式化创建时间
    private String createTimeStr;
    //格式化更新时间
    private String updateTimeStr;
    
    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

}