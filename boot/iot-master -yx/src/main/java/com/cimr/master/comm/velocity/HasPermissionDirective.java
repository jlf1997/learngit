package com.cimr.master.comm.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.util.SpringContextUtil;

public class HasPermissionDirective extends Directive {
	public String getName() {
		return "hasPermission";
	}

	public int getType() {
		return 2;
	}

	public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ViewToolContext context = (ViewToolContext) internalContextAdapter.getInternalUserContext();
		return doRender(internalContextAdapter, context, writer, node);
	}

	protected boolean doRender(InternalContextAdapter internalContext, ViewToolContext context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode snKey = (SimpleNode) node.jjtGetChild(0);
		String key = (String) snKey.value(internalContext);
		String userId = TokenUtil.getUserId();
		Set permissionSet = permissionService.findPermissionByUserId(userId);
		if ((userId != null) && (permissionSet.contains(key))) {
			SimpleNode snContent = (SimpleNode) node.jjtGetChild(1);
			String content = (String) snContent.value(internalContext);
			writer.write(content);
		} else {
			writer.write("");
		}
		return true;
	}

	public static final PermissionService permissionService = (PermissionService) SpringContextUtil.getBean("permissionService", PermissionService.class);
}
