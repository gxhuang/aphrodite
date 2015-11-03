package org.apache.aphrodite.service;

import java.lang.reflect.Proxy;

import org.apache.aphrodite.util.ApplicationContextUtil;

public class JdbcServiceProxyFactory {
	
	private static JdbcService proxy ;

	static {
		JdbcService jdbcService = ApplicationContextUtil.getApplicationContext().getBean("jdbcService",
				JdbcService.class);
		JdbcServiceProxy jdbcServiceProxy = new JdbcServiceProxy();
		jdbcServiceProxy.setJdbcService(jdbcService);
		try {
			proxy = (JdbcService) Proxy.getProxyClass(JdbcServiceProxy.class.getClassLoader(), JdbcService.class).newInstance() ;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
