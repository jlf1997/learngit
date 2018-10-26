package com.cimr.comm.aop;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.config.AppFileProperties;
import com.cimr.comm.config.AppProperties;
import com.cimr.comm.token.TokenUtil;

@Aspect
public class FullPageAspect {
	Logger logger = LoggerFactory.getLogger(FullPageAspect.class);

	@Pointcut("@annotation(com.cimr.comm.aop.FullPage)")
	protected void annotation_FullPage() {
	}

	@Pointcut("within(com.cimr..*)")
	protected void webController() {
	}

	@AfterReturning(pointcut = "webController() && annotation_FullPage()", returning = "result")
	public void afterReturning_AppIndex(JoinPoint jp, Object result) {
		 adjustPageLayout(jp, result);
	}

	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
		this.layoutKeyMenu = (layoutKey + "MenuBo");
		this.layoutKeySubMenu = (layoutKey + "SubMenu");
		this.layoutKeyPrev = (layoutKey + "Prev");
	}

	private String layoutKey = "layout";
	private String layoutKeyPrev = "layoutPrev";

	private String layoutKeyMenu = "layoutMenu";

	private String layoutKeySubMenu = "layoutSubMenu";

	private String layoutKeyUsername = "layoutUsername";

	private String jsVersion = "jsVersion";

	private String fileServerPath = "fileServerPath";

	private void adjustPageLayout(JoinPoint jp, Object result) {
		Signature jpsignature = jp.getSignature();
		Object[] args = jp.getArgs();
		HttpServletRequest request = null;
		for (Object arg : args) {
			if ((arg != null) && (HttpServletRequest.class.isAssignableFrom(arg.getClass()))) {
				request = (HttpServletRequest) arg;
				break;
			}
		}
		if ((request != null) && ((jpsignature instanceof MethodSignature))) {
			MethodSignature signature = (MethodSignature) jpsignature;
			FullPage annoFullPage = (FullPage) AnnotationUtils.findAnnotation(signature.getMethod(), FullPage.class);
			RequestMapping qequestMapping = (RequestMapping) AnnotationUtils.findAnnotation(signature.getMethod(), RequestMapping.class);
			String[] paths = qequestMapping.path();
			String router = annoFullPage.router();

			boolean isMatched = false;
			int matchLimit = 3;
			for (String path : paths) {
				int idx = path.indexOf(router);
				if ((idx >= 0) && (idx <= 1)) {
					if (request.getRequestURI().indexOf(path) > 0) {
						isMatched = true;
					} else {
						matchLimit--;
						if (matchLimit < 0) {
							break;
						}
					}
				}
			}
			if ((isMatched) && ((result instanceof ModelAndView))) {
				ModelAndView mvcData = (ModelAndView) result;
				String prevLayout = (String) mvcData.getModelMap().get(this.layoutKey);
				ModelMap datamap = mvcData.getModelMap();
				datamap.put(this.layoutKey, annoFullPage.layout());
				datamap.put(this.layoutKeyMenu, annoFullPage.menu());
				datamap.put(this.layoutKeySubMenu, annoFullPage.subMenu());
				datamap.put(this.layoutKeyPrev, prevLayout);
				datamap.put(this.layoutKeyUsername, TokenUtil.getFullname());
				String jsVersionConf = AppProperties.getJsVersion();
				if ((jsVersionConf != null) && (!Objects.equals("", jsVersionConf))) {
					datamap.put(this.jsVersion, jsVersionConf);
				} else {
					datamap.put(this.jsVersion, "20171226");
				}
				String fileServerPathConf = AppFileProperties.getServerPath();
				if ((fileServerPathConf != null) && (!"".equals(fileServerPathConf))) {
					datamap.put(this.fileServerPath, fileServerPathConf);
				}
			}
		}
	}
}
