package org.apache.aphrodite.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:18:09
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Object getBean(String bean) {
        return applicationContext.getBean(bean);
    }

    public <T> T getBean(String bean, Class<T> clazz) {
        return applicationContext.getBean(bean, clazz);
    }
}
