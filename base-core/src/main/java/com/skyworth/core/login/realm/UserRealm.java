package com.skyworth.core.login.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyworth.core.actor.vo.ActorPermissionVo;
import com.skyworth.core.common.Constants;
import com.skyworth.core.login.service.LoginService;
import com.skyworth.core.user.vo.UserVo;

/**
 * <p>User: 魏诚
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 权限认证
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String gh = (String)principals.getPrimaryPrincipal();
        UserVo userVo = loginService.findUserByGh(gh);
        
        /*if(userVo.getGwid() == null){
        	return null;
        }*/
        
        List<ActorPermissionVo> permissionList = loginService.findQx(userVo.getGh());
        int size = permissionList.size();
        
        /*Set<String> actorIdSet = new HashSet<String>(1);
        actorIdSet.add(userVo.getGwid().toString());*/
        
        Set<String> permissionSet = new HashSet<String>(size);
        for (int i = 0; i < size; i++) {
        	ActorPermissionVo permission = permissionList.get(i);
        	permissionSet.add(permission.getQxbm());
		}
        
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.setRoles(actorIdSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String gh = (String)token.getPrincipal();
    	UserVo userVo = loginService.findUserByGh(gh);
        
        if(userVo == null) {
            throw new UnknownAccountException();//错误的帐号
        }

        if(Constants.STATUS_DISABLED.equals(userVo.getZt())) {
            throw new DisabledAccountException();//禁用的帐号
        }
		
        SimpleAuthenticationInfo authenticationInfo = null;
        //数据库密码验证
    	authenticationInfo = new SimpleAuthenticationInfo(
    		gh, //用户名
    		userVo.getMm(), //密码
    		getName()  //realm name
        );
    	
        return authenticationInfo;
	}

}
