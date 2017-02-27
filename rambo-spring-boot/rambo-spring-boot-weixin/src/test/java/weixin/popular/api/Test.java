
package weixin.popular.api;

import weixin.popular.bean.message.*;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.user.*;
import weixin.popular.util.JsonUtil;

public class Test {

	
	static String token = "cLHwImV9q6wdSpRMEwtqgAqFs67R3ugNwIPCz7wSqwiDHWarZtRKOsGDNFC-k6WB1v_PAeKHs7iqM3kySmIVoJ7H9dTWl0tYpOLOJVbCZ93F1dRRpWbSGotl8wNHMosNDDYfADAYYT";
	 public static void main(String[] args){
		/*Token token = TokenAPI.token("wx20da1fd47c5d9ca8","f367a067fe6c3224e8f078ee70429a30");
		
		System.out.println(JsonUtil.toJSONString(token));*/
		
/*		FollowResult resulst = UserAPI.userGet(token, null);
		System.out.println(JsonUtil.toJSONString(resulst));
		
		for(String s : resulst.getData().getOpenid()){
			User user = UserAPI.userInfo(token, s);
			
			System.out.println(JsonUtil.toJSONString(user));
			
		}
		
		CurrentAutoreplyInfo reply = MessageAPI.get_current_autoreply_info(token);
		
		System.out.println(JsonUtil.toJSONString(reply));*/
		
		
//		 Group  g = UserAPI.groupsGet(token);
//		 
//		 System.out.println(JsonUtil.toJSONString(g));
		 
		 	TagsGetResult  g = UserAPI.tagsGet(token);
		 
		 System.out.println(JsonUtil.toJSONString(g));
	
	 }
}
