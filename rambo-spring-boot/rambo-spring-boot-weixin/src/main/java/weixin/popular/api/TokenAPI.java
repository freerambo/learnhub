package weixin.popular.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import weixin.popular.bean.token.Token;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.TokenManager;

/**
 * ACCESS_TOKEN API
 * @author LiYi
 *
 */
public class TokenAPI extends BaseAPI{

	/**
	 * 获取access_token
	 * @param appid appid
	 * @param secret secret
	 * @return Token
	 */
	public static Token token(String appid,String secret){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/token")
				.addParameter("grant_type","client_credential")
				.addParameter("appid", appid)
				.addParameter("secret", secret)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Token.class);
	}
	
	
	public static void main(String[] args){
		Token t = token/*("wx20da1fd47c5d9ca8", "f367a067fe6c3224e8f078ee70429a30");*/
		("wx616010009d110493", "6207762312e68215d40de36e1bab3ec0");
		System.out.println(t.getAccess_token());
	}
}
