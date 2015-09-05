package org.apache.aphrodite.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class ProxyHandler implements InvocationHandler {

    private Object target ;

    public ProxyHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("invoke.....");
        Object obj = method.invoke(target,args) ;
        return obj ;
    }
}
