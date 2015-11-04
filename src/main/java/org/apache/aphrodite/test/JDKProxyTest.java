package org.apache.aphrodite.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class JDKProxyTest implements InvocationHandler{
	
	private Object target ;
	
	public void setTarget(Object target) {
		this.target = target;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoking..........");
		return method.invoke(target, args) ;
	}

	
	public static void main(String[] args) {
		IProxy iproxy = new ProxyImpl() ;
		JDKProxyTest invocation = new JDKProxyTest() ;
		invocation.setTarget(iproxy); 
		IProxy proxy = (IProxy) Proxy.newProxyInstance(JDKProxyTest.class.getClassLoader(), new Class[]{IProxy.class}, invocation);
		proxy.doAction();
	}
}
