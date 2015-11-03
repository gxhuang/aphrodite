package org.apache.aphrodite.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.aphrodite.exception.ServiceException;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class JdbcServiceProxy implements InvocationHandler{
	
	private JdbcService jdbcService ;
	
    public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke......");
        Object result = null ;
        try{
        	result = method.invoke(jdbcService,args);
        }catch(Throwable t){
        	jdbcService.rollback();
        	throw new ServiceException(t.getMessage(), t.getCause()) ;
        }finally{
        	jdbcService.close();
        }
        return result ;
    }

    public static void main(String[] args){

//        Proxy.getProxyClass(JdbcService.class.getClassLoader(),)
    }
}
