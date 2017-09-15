package com.gst.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;


public class MyShiro extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		if (principal != null) {
			Map<String, Object> attributes = principal.getAttributes();
			if (attributes.size() > 0) {
				//List<String> roles = CommonUtils.arrayStringtoArrayList((String) attributes.get("roles"));
				List<String> roles =null;
				// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				// 用户的角色集合
				info.addRoles(roles);
				// 用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的一行可以不要
				// info.addStringPermissions(user.getPermissionList());

			}
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		String username = principal.getName();

		if (principal != null) {
			Map<String, Object> attributes = principal.getAttributes();
			if (attributes.size() > 0) {
				String name = (String) attributes.get("name");
				// 若存在，将此用户存放到登录认证info中
				return new SimpleAuthenticationInfo(username, null, name);
			}
		}
		return null;

	}

}
