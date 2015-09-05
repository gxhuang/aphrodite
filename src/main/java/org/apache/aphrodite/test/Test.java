package org.apache.aphrodite.test;

import org.apache.aphrodite.dao.BaseDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.service.BaseService;
import org.apache.aphrodite.service.BaseServiceImpl;
import org.apache.aphrodite.util.SQLAdapter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Test {

    public static void main(String[] args){
        ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml") ;

        String applicationName = cxt.getApplicationName();
        System.out.println(applicationName) ;

        BaseDao baseDao = cxt.getBean("baseDao",BaseDao.class ) ;
        List maps = baseDao.select("findRecords", new SQLAdapter("SELECT * FROM C_USER LIMIT 1")) ;
        System.out.println(maps.size()) ;

//        TestCallBack test = cxt.getBean("test",TestCallBack.class) ;
//        BaseService baseService = cxt.getBean("baseService",BaseServiceImpl.class) ;
//        baseService.doService(test,new Dataset());
//        test.doCall(new Dataset());

//
// .save(new PageView()) ;
    }

}
