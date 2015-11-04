package org.apache.aphrodite.service;

import java.lang.reflect.Proxy;

public abstract class ServiceProxyFactory {
	
	public static Object getInstance(Object service){
		ServiceProxy serviceProxy = new ServiceProxy() ;
		serviceProxy.setService(service);
		Object proxy = Proxy.newProxyInstance(ServiceProxy.class.getClassLoader(), service.getClass().getInterfaces(), serviceProxy) ;
		return proxy ;
	}

}
