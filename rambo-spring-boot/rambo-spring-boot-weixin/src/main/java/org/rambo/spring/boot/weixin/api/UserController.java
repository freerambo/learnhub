package org.rambo.spring.boot.weixin.api;


import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import weixin.popular.api.API;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.card.Gift;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.pay.PayNotify;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JsonUtil;


// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/api/accounts/logout")
	public void logout(@RequestParam(value = "token", required = false) String token) {
	}
	/**
	 * 
	 * @param token
	 * @return  all followers
	 *
	 */
	@RequestMapping(value = "/api/followers")
	public FollowResult getFollowers(@RequestParam(value = "token", required = false) String token,@RequestParam(value = "openid", required = false) String openid) {
		
		token = TokenManager.getDefaultToken();
		FollowResult result = UserAPI.userGet(token, openid);
		String response = JsonUtil.toJSONString(result);
		logger.info(response);
		return result;
	}
	
	/**
	 * 
	 * @param token
	 * @return  all followers
	 *
	 */
	@RequestMapping(value = "/api/users/{openid}")
	public User getFollowerById(@RequestParam(value = "token", required = false) String token,@PathVariable(value="openid", required = true) String openid) {
		
		token = TokenManager.getDefaultToken();
		User result = UserAPI.userInfo(token, openid);
		String response = JsonUtil.toJSONString(result);
		logger.info(response);
		return result;
	}
	
	@RequestMapping(value = "/api/test")
	public Button test(@RequestParam(value = "token", required = false) String token) {
		Button test = new Button();
		test.setKey("Test");
		return test;
	}
	
	


}
