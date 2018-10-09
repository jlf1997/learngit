package com.cimr.comm.velocity;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

public class UrlDirective extends Directive {
	public String getName() {
		return "url";
	}

	public int getType() {
		return 2;
	}

	public boolean render(InternalContextAdapter internalContext, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ViewToolContext context = (ViewToolContext) internalContext.getInternalUserContext();

		return doRender(internalContext, context, writer, node);
	}

	protected boolean doRender(InternalContextAdapter internalContext, ViewToolContext context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode sn = (SimpleNode) node.jjtGetChild(0);
		String url = (String) sn.value(internalContext);
		String contextPath = context.getRequest().getContextPath();
		if (url.startsWith("/")) {
			if (!url.startsWith("//")) {

				url = contextPath + url;
			}
		} else {
			Integer idx = Integer.valueOf(url.indexOf("://"));

			if ((!url.startsWith("http")) || (idx.intValue() <= 0) || (idx.intValue() > 6)) {

				url = contextPath + "/" + url;
			}
		}

		writer.write(url);
		return true;
	}
}
