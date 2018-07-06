package com.cimr.push.service;

import java.util.List;

import com.cimr.push.model.PushModel;
import com.cimr.push.util.HttpResult;


public interface AppPushService {

	/**
	 * 推送到指定别名
	 * @param alias
	 * @return
	 */
	public HttpResult pushToAliasList(List<String> alias,PushModel pushModel);
	
	/**
	 * 推送到指定cid
	 * @param cids
	 * @return
	 */
	public HttpResult pushToCidList(List<String> cids,PushModel pushModel);
	
	
	/**
	 * 取消任务
	 * @param taskids
	 * @return
	 */
	public HttpResult CancelPush(List<String> taskids);
}
