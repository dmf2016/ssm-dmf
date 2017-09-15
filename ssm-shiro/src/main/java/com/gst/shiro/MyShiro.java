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
				// Ȩ����Ϣ����info,������Ų�����û������еĽ�ɫ��role����Ȩ�ޣ�permission��
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				// �û��Ľ�ɫ����
				info.addRoles(roles);
				// �û��Ľ�ɫ��Ӧ������Ȩ�ޣ����ֻʹ�ý�ɫ�������Ȩ�ޣ������һ�п��Բ�Ҫ
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
				// �����ڣ������û���ŵ���¼��֤info��
				return new SimpleAuthenticationInfo(username, null, name);
			}
		}
		return null;

	}

}
