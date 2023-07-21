package com.tcs.arms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.common.utils.TokenUtils;
import com.tcs.arms.model.OperationLogType;
import com.tcs.arms.service.LoginService;
import com.tcs.arms.service.ArmsService;
import com.tcs.arms.model.UserInfo;
import com.tcs.annotation.Log;
import com.tcs.common.utils.PageResult;
import com.tcs.common.utils.R;
import com.tcs.common.utils.Result;

/*
 * 文件名：AuthController.java
 * 描述：用户登录、注销信息
 * 作者：Haijun Huang
 * 创建时间：2023-06-01
*/

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ArmsService armsService;

	/*
	 * 方法名：Login
	 * 描述：用户登录
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/login")
	@Log(operModule = "用戶管理-登录", operType = OperationLogType.Login,operDesc = "用戶登录")
	public R Login(HttpServletRequest request,@RequestBody Map<String, String> user) {
		//System.out.println(user);
		String username = user.get("username");
		String password = user.get("password");	
		UserInfo result = loginService.Login(username, password);	
		return R.ok().put("result", result);
	}
	
	
	/*
	 * 方法名：Logout
	 * 描述：用户注销
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/logout")
	@Log(operModule = "用戶管理-注銷", operType = OperationLogType.Logout,operDesc = "用戶注銷")
	public R Logout(HttpServletRequest request) {		
		return R.ok().put("result", "ok");
	}
}