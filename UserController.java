package com.tcs.arms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.common.utils.PageResult;
import com.tcs.common.utils.R;
import com.tcs.common.utils.Result;
import com.tcs.common.utils.TokenUtils;
import com.tcs.arms.service.LoginService;
import com.tcs.arms.model.UserInfo;

import io.jsonwebtoken.Claims;


/*
 * 文件名：AuthController.java
 * 描述：用户信息
 * 作者：Haijun Huang
 * 创建时间：2023-06-01
*/

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private LoginService loginService;

	/*
	 * 方法名：getRoleList
	 * 描述：用获取用户角色信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/info")
	public R getRoleList(HttpServletRequest request) {
		// 解析Token
		try {
			if (request.getHeader("Token") == null) {
				return R.ok().put("result", null);
			}

			String token = request.getHeader("Token");
			Claims c = loginService.parserToken(token);
			String userName = c.get("username").toString();
			String passWord = c.get("password").toString();
			// 模擬賬號
			String name = userName;
			if (name.equalsIgnoreCase("admin") || name.equalsIgnoreCase("L003828") || name.equalsIgnoreCase("user")) {
				String role = "admin";
				if (name.equalsIgnoreCase("user")) {
					role = "user";
				}
				UserInfo user = loginService.GetUserInfo(userName, userName, userName, role, token);
				return R.ok().put("result", user);
			}

			// 獲取MemberOf權限信息
			UserInfo result = loginService.Login2(userName, passWord, token);
			return R.ok().put("result", result);

		} catch (Exception e) {
			e.printStackTrace();
			return R.ok().put("result", null);
		}
	}

	
	/*
	 * 方法名：getUserMenus
	 * 描述：用获取用户实体信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/nav")
	public R getUserMenus() {
		return R.ok().put("result", null);
	}
}