package com.cimr.boot.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.LogsUtil;

@ControllerAdvice
public class ExceptionHandle {

	@Autowired
	private HandlleException handlleException;
	
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public HttpResult handle(Exception e){
    	ExceptionModel model = new ExceptionModel();
    	String stackTrace = LogsUtil.getStackTrace(e);
    	model.setMessage(stackTrace);
    	String exceptionName = "";
    	HttpResult result = new HttpResult(false,"未知异常:"+stackTrace);
    	if(e instanceof MethodArgumentTypeMismatchException) {
    		exceptionName = "参数转换异常";
    		model.setExceptionName(exceptionName);
    		model.setTyep(1);
    		result.setMessage(exceptionName);
    	}
    	handlleException.saveLog(model);
    	return  result;
    }

}
