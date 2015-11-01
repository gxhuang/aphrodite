package org.apache.aphrodite.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class JdbcServiceProxy implements InvocationHandler{

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke......");
        return method.invoke(proxy,args);
    }

    public static void main(String[] args){

//        Proxy.getProxyClass(JdbcService.class.getClassLoader(),)
    }
}