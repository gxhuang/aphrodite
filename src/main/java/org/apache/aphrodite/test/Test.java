package org.apache.aphrodite.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.Search;
import org.apache.aphrodite.util.GsonUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类描述 ：
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
        Test test = new Test();
        test.searchControlTest(cxt);
        
        

//        BaseDao baseDao = cxt.getBean("baseDao",BaseDao.class ) ;
//        List maps = baseDao.select("findRecords", new SQLAdapter("SELECT * FROM C_USER LIMIT 1")) ;
//        System.out.println(maps.size()) ;

//        TestCallBack test = cxt.getBean("test",TestCallBack.class) ;
//        BaseService baseService = cxt.getBean("baseService",BaseServiceImpl.class) ;
//        baseService.save(new PageView()) ;
//        baseService.update(new PageView()) ;
//        int factor = 0 ;
//        int result = 1/0 ;
//        baseService.doService(test,new Dataset());
//        test.doCall(new Dataset());

//
// .save(new PageView()) ;
    }
    
    public void searchControlTest(ApplicationContext cxt){
    	Search search = new Search();
    	
    	List<Field> fields = new ArrayList<Field>() ;
    	
    	Field id = new Field() ;
    	id.setDataType("string");
    	id.setName("id");
    	fields.add(id) ;
    	
    	Field name = new Field() ;
    	name.setDataType("string");
    	name.setName("name");
    	name.setOp("LIKE");
    	fields.add(name) ;
    	
    	Field url = new Field() ;
    	url.setDataType("string");
    	url.setName("url");
    	fields.add(url) ;
    	
    	Field parendId = new Field() ;
    	parendId.setDataType("string");
    	parendId.setName("parentId");
    	fields.add(parendId) ;
    	search.setFields(fields);
    	
    	search.setTableName("sysMenu");
    	search.setCondition("系统管理");
    	
    	
    	System.out.println(GsonUtil.toJson(search));

    	
    }

}
