package ${templateInfo.packageName};

import com.cimr.comm.aop.AccessLog;
import com.cimr.comm.enums.SysEnums;
import com.${templateInfo.companyName}.${templateInfo.projectName}.dto.${tableModel.domainName}Dto;
import com.${templateInfo.companyName}.${templateInfo.projectName}.model.${tableModel.domainName};
import com.${templateInfo.companyName}.${templateInfo.projectName}.service.${tableModel.domainName}Service;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.SelectDto;
import com.cimr.util.Assist;
import com.cimr.util.LogUtil;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;
import com.cimr.comm.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * ${templateInfo.model}模块控制器
 * @author ${author} ${tableModel.currentDate} ${tableModel.currentTime}
 */
@Controller
@RequestMapping("/${templateInfo.model}")
public class ${tableModel.domainName}Controller {

    @Autowired
    private ${tableModel.domainName}Service ${templateInfo.model}Service;

    private static final Logger logger = LoggerFactory.getLogger(${tableModel.domainName}Controller.class);


    /**
     * ${templateInfo.model}列表页面路由跳转
     *
     * @param request
     * @return
     */
    @RequestMapping({"nav/list"})
    @AccessLog(methods = "index", module = "${templateInfo.model}")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("${templateInfo.projectName}/${tableModel.domainName}List");
        return modelAndView;
    }

    /**
     * ${templateInfo.model}表单页面路由跳转
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping({"nav/form"})
    @AccessLog(methods = "form", module = "${templateInfo.model}")
    public ModelAndView form(HttpServletRequest request, String id) {
        ModelAndView modelAndView = new ModelAndView("${templateInfo.projectName}/${tableModel.domainName}Form");
        //处理空id的情况
        modelAndView.addObject("id", StringUtil.empty(id) ? "" : id);
        return modelAndView;
    }

    /**
     * ${templateInfo.model}列表分页
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping({"/ajax/selectPage"})
    @ResponseBody
    @AccessLog(methods = "select${tableModel.domainName}Page", module = "${templateInfo.model}")
    public LayuiTableData select${tableModel.domainName}Page(${tableModel.domainName}Dto ${templateInfo.model}Dto, Integer page, Integer limit, HttpServletRequest request) {
        LayuiTableData result = new LayuiTableData();
        //分页查询
        try {
            Assist assist = new Assist();
<#list tableModel.columnList as columnModel>
<#if columnModel.columnName!="id"
        &&columnModel.columnName!="createBy"
        &&columnModel.columnName!="createTime"
        &&columnModel.columnName!="updateBy"
        &&columnModel.columnName!="updateTime"
        &&columnModel.columnName!="delFlag">
            assist.setRequires(Assist.andEq("${columnModel.dBcolumnName}",${templateInfo.model}Dto.get${columnModel.columnNameFirstUpper}()));
</#if>
</#list>
            PageData<${tableModel.domainName}> pagaData = ${templateInfo.model}Service.selectPageCommon(assist, page, limit);
            //表示请求成功,layui的列表插件会判断这个code
            result.setCode(0);
            result.setCount(pagaData.getCount());
            result.setData(pagaData.getList());
        } catch (Exception e) {
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
            result.setCode(SysEnums.HTTP_500.getCode());
        }
        return result;
    }

    /**
     * ${templateInfo.model}保存或者更新
     *
     * @param session
     * @return
     */
    @RequestMapping({"/ajax/save"})
    @ResponseBody
    @AccessLog(methods = "save${tableModel.domainName}", module = "${templateInfo.model}")
    public HttpResult save${tableModel.domainName}(@Valid ${tableModel.domainName}Dto ${templateInfo.model}Dto, Errors validResult, HttpServletRequest request, HttpSession session) {
        HttpResult result = new HttpResult(true, "保存成功");
        //表单校验
        if (validResult.hasErrors()) {
            for (FieldError error : validResult.getFieldErrors()) {
                result.setSuccess(false);
                result.setCode(SysEnums.ERROR_FORM_VALIDATE.getCode());
                result.setError(error.getDefaultMessage());
                return result;
            }
        }
        try {
            if (StringUtil.empty(${templateInfo.model}Dto.getId())) {
                //新增
                ${templateInfo.model}Dto.setId(IdUtil.getId());
                result = ${templateInfo.model}Service.insertNonEmptyObjCommon(${templateInfo.model}Dto);
            } else {
                //更新
                result = ${templateInfo.model}Service.updateNonEmptyObjByIdCommon(${templateInfo.model}Dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(SysEnums.HTTP_500.getCode());
            result.setError("操作失败，系统出现异常！异常代码:" + SysEnums.HTTP_500.getCode());
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return result;
    }

    /**
     * ${templateInfo.model}删除
     *
     * @param id
     * @return
     */
    @RequestMapping({"/ajax/delete"})
    @ResponseBody
    @AccessLog(methods = "delete${tableModel.domainName}", module = "${templateInfo.model}")
    public HttpResult delete${tableModel.domainName}(@RequestParam(required = true) String id, HttpServletRequest request) {
        HttpResult result = new HttpResult(true, "删除成功");
        try {
            //删除(逻辑删除)
            ${tableModel.domainName} ${templateInfo.model} = new ${tableModel.domainName}();
            ${templateInfo.model}.setId(id);
            result = ${templateInfo.model}Service.deleteObjByIdLogicCommon(${templateInfo.model});
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(SysEnums.HTTP_500.getCode());
            result.setError("操作失败，系统出现异常！异常代码:" + SysEnums.HTTP_500.getCode());
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return result;
    }

    /**
     * ${templateInfo.model}获取单个对象
     *
     * @param id
     * @return
     */
    @RequestMapping({"/ajax/select${tableModel.domainName}Info"})
    @ResponseBody
    @AccessLog(methods = "select${tableModel.domainName}Info", module = "${templateInfo.model}")
    public HttpResult select${tableModel.domainName}Info(@RequestParam(required = true) String id, HttpServletRequest request) {
        HttpResult result = new HttpResult(true, "获取成功");
        try {
            ${tableModel.domainName} ${templateInfo.model} = ${templateInfo.model}Service.selectObjByIdCommon(id);
            result.setData(${templateInfo.model});
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(SysEnums.HTTP_500.getCode());
            result.setError("操作失败，系统出现异常！异常代码:" + SysEnums.HTTP_500.getCode());
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return result;
    }

    /**
     * ${templateInfo.model}获取列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajax/select${tableModel.domainName}List")
    @AccessLog(methods = "select${tableModel.domainName}List", module = "${templateInfo.model}")
    public HttpResult select${tableModel.domainName}List(${tableModel.domainName}Dto ${templateInfo.model}Dto, HttpServletRequest request) {
        HttpResult result = new HttpResult(true, "");
        try {
            Assist assist = new Assist();
<#list tableModel.columnList as columnModel>
<#if columnModel.columnName!="id"
        &&columnModel.columnName!="createBy"
        &&columnModel.columnName!="createTime"
        &&columnModel.columnName!="updateBy"
        &&columnModel.columnName!="updateTime"
        &&columnModel.columnName!="delFlag">
            assist.setRequires(Assist.andEq("${columnModel.dBcolumnName}",${templateInfo.model}Dto.get${columnModel.columnNameFirstUpper}()));
</#if>
</#list>
            List<${tableModel.domainName}> ${templateInfo.model}List = ${templateInfo.model}Service.selectListCommon(assist);
            result.setData(${templateInfo.model}List);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(SysEnums.HTTP_500.getCode());
            result.setError("操作失败，系统出现异常！异常代码:" + SysEnums.HTTP_500.getCode());
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return result;
    }

    /**
     * ${templateInfo.model}适配前台下拉框数据查询
     *
     * @param value
     * @return
     */
    @RequestMapping("/ajax/select${tableModel.domainName}SelectData")
    @ResponseBody
    @AccessLog(methods = "select${tableModel.domainName}SelectData", module = "${templateInfo.model}")
    public HttpResult select${tableModel.domainName}SelectData(String value, String title, HttpServletRequest request) {
        HttpResult result = new HttpResult(true, "");
        //下拉框数据对象集合
        List<SelectDto<String, String>> selectDtoList = new ArrayList<>();
        title = title == null ? "请选择":title;
        selectDtoList.add(new SelectDto<>(title, ""));
        //查询数据
        List<${tableModel.domainName}> ${templateInfo.model}List = ${templateInfo.model}Service.selectListCommon(null);
        try {
            SelectDto<String, String> selectDto;
            for (${tableModel.domainName} ${templateInfo.model} : ${templateInfo.model}List) {
<#list tableModel.columnList as columnModel>
<#if columnModel.columnName!="id"
        &&columnModel.columnName!="createBy"
        &&columnModel.columnName!="createTime"
        &&columnModel.columnName!="updateBy"
        &&columnModel.columnName!="updateTime"
        &&columnModel.columnName!="delFlag"
        &&columnModel.columnType =="String">
                selectDto = new SelectDto<>(${templateInfo.model}.get${columnModel.columnNameFirstUpper}(), ${templateInfo.model}.getId());
<#break>
</#if>
</#list>                //回显选中
                if (${templateInfo.model}.getId().equals(value)) {
                    selectDto.setSelected(true);
                }
                selectDtoList.add(selectDto);
            }
            result.setData(selectDtoList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(SysEnums.HTTP_500.getCode());
            result.setError("操作失败，系统出现异常！异常代码:" + SysEnums.HTTP_500.getCode());
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return result;
    }

}