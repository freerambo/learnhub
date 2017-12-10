package weixin.popular.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import weixin.popular.support.TokenManager;

public class TokenManagerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//WEB容器 初始化时调用
//		TokenManager.init("wx20da1fd47c5d9ca8", "f367a067fe6c3224e8f078ee70429a30");
		TokenManager.init("wx616010009d110493", "6207762312e68215d40de36e1bab3ec0");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TokenManager.destroyed();
	}
}
