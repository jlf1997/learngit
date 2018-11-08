package ${templateInfo.packageName};

/**
 * JAVA实体类${tableModel.domainName}
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */
import com.cimr.comm.base.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.Date;

public class ${tableModel.domainName} extends BaseEntity{
    
    public ${tableModel.domainName}() {
        super();
    }
	
	<#list tableModel.columnList as columnModel>

<#if columnModel.columnName != "id"
        && columnModel.columnName != "createBy"
        && columnModel.columnName != "createTime"
        && columnModel.columnName != "updateBy"
        && columnModel.columnName != "updateTime"
        && columnModel.columnName != "delFlag">

    <#if columnModel.columnType == "String">
    /**
    * ${columnModel.columnChineseName},不能为空
    */
    @NotEmpty(message = "${columnModel.columnChineseName}不能为空")
    </#if>
    private ${columnModel.columnType} ${columnModel.columnName};
</#if>
    </#list>
    
    <#list tableModel.columnList as columnModel>
<#if columnModel.columnName != "id"
        && columnModel.columnName != "createBy"
        && columnModel.columnName != "createTime"
        && columnModel.columnName != "updateBy"
        && columnModel.columnName != "updateTime"
        && columnModel.columnName != "delFlag">
    public void set${columnModel.columnNameFirstUpper}(${columnModel.columnType} ${columnModel.columnName}) {
        this.${columnModel.columnName} = ${columnModel.columnName};
    }
    
    public ${columnModel.columnType} get${columnModel.columnNameFirstUpper}() {
        return this.${columnModel.columnName};
    }
</#if>
    </#list>
}