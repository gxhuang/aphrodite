package org.apache.aphrodite.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.aphrodite.dao.JdbcDao;
import org.apache.aphrodite.exception.ServiceException;
import org.apache.aphrodite.util.ApplicationContextUtil;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class ServiceProxy implements InvocationHandler{
	
	private Object service ;
	
	private JdbcDao jdbcDao ;    

	public void setService(Object service) {
		this.service = service;
		jdbcDao = ApplicationContextUtil.getApplicationContext().getBean("jdbcDao", JdbcDao.class) ;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		jdbcDao.begin();
		System.out.println("invoke......");
        Object result = null ;
        try{
        	result = method.invoke(service,args);
        }catch(Throwable t){
        	jdbcDao.rollback();
        	throw new ServiceException(t.getMessage(), t.getCause()) ;
        }finally{
        	jdbcDao.close();
        }
        return result ;
    }

    public static void main(String[] args){

//        Proxy.getProxyClass(JdbcService.class.getClassLoader(),)
    }
}
