package com.cimr.sysmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.comm.config.AppProperties;
import com.cimr.comm.exception.NotLoginException;
import com.cimr.comm.token.TokenUtil;
import com.cimr.comm.token.UserToken;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.model.User;
import com.cimr.util.PasswordUtil;
import com.cimr.util.ShiroUtil;
import com.xiaoleilu.hutool.util.StrUtil;

@Controller
@RequestMapping({ "/ajax" })
public class LoginAjaxController {

	
	private static final Logger log = LoggerFactory.getLogger(LoginAjaxController.class);

	
	/**
	 * 方法描述: 后台登录
	 * @param username
	 * @param pswd
	 * @param request
	 * @param data
	 * @return
	 * @throws NotLoginException
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月10日 下午2:48:27
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "login" })
	@ResponseBody
	public HttpResult login(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "pswd", required = false) String pswd, HttpServletRequest request, ModelMap data) throws NotLoginException {
		UserToken token = new UserToken(username, pswd);
		Subject subject = ShiroUtil.getSubject();
		try {
			subject.login(token);
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = "";
			if ((savedRequest == null) || (savedRequest.getRequestUrl() == null) || (savedRequest.getRequestUrl().equalsIgnoreCase("/ajax/login"))) {
				url = AppProperties.getMainIndexPage();
			} else {
				String contextPath = request.getContextPath();
				url = StrUtil.removePrefix(savedRequest.getRequestUrl(), contextPath);
			}
			data.put("url", url);

			HttpResult result = new HttpResult(true, "");
			result.setData(data);
			return result;
		} catch (Exception e) {
			throw new NotLoginException("用户名或密码错误。");
		}
	}

	/**
	 * 方法描述: 后台登录退出
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月10日 下午2:48:44
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "logout" })
	@ResponseBody
	public HttpResult logout() {
		HttpResult result = new HttpResult(true, "");
		try {
			TokenUtil.logout();
		} catch (Exception e) {
			result.setSuccess(false);
			result.setError("退出出错!");
			log.error( "退出出现错误，{}", new Object[] { e.getMessage() });
		}
		return result;
	}
	/**
	 * 方法描述: 密码修改
	 * @param username
	 * @param pswd
	 * @param request
	 * @param data
	 * @return
	 * @throws NotLoginException
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月10日 下午2:48:27
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "pwdEdit" })
	@ResponseBody
	public HttpResult pwdEdit(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "pswd", required = false) String pswd, HttpServletRequest request, ModelMap data) throws NotLoginException {
		User newUser = new User();
		String pswds = PasswordUtil.encrypt(username, pswd);
		UserToken token = new UserToken(username, pswds);
		Subject subject = ShiroUtil.getSubject();
		HttpResult result = new HttpResult(true, "");
		try {
			subject.login(token);

			return result;
		} catch (Exception e) {
			result.setData("1");
			return result;
		}
	}
}
