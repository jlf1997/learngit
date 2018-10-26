package com.cimr.boot.auth.shiro.cas;

import java.util.LinkedHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;

public class ShiroCasPac4jRealm extends Pac4jRealm{
	
	
	private static final Logger log = LoggerFactory.getLogger(ShiroCasPac4jRealm.class);

	//用于授权
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
//		String userId = TokenUtil.getUserId();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		Set<String> roles = roleService.findRoleByUserId(userId);
//		Set<String> permissions = permissionService.findPermissionByUserId(userId);
//		authorizationInfo.setRoles(roles);
//		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
    }
	
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//		Pac4jToken userToken = (Pac4jToken) authenticationToken;
////      final LinkedHashMap<String, CommonProfile> profiles = token.getProfiles();
////      final Pac4jPrincipal principal = new Pac4jPrincipal(profiles);
//		User user = this.userService.login(userToken.getUsername(), userToken.getPswd());
//		if (user != null) {
//			if (User._0 == user.getStatus()) {
//				throw new DisabledAccountException("帐号已经禁止登录！");
//			}
//			return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
//		}
//
//		throw new AccountException("帐号或密码不正确！");
//	}
	
	
	//用于认证
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken token = (Pac4jToken) authenticationToken;
        final LinkedHashMap<String, CommonProfile> profiles = token.getProfiles();
        final Pac4jPrincipal principal = new Pac4jPrincipal(profiles);
 
        String loginName = principal.getProfile().getId();
 
 
        Session session = SecurityUtils.getSubject().getSession();
       
        session.setAttribute("userSessionId", loginName );
       
 
        return new SimpleAuthenticationInfo(principal, profiles.hashCode(), getName());
    }

	
}
