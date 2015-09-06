package org.apache.aphrodite.dataset;

import org.apache.aphrodite.exception.DatasetException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 类描述：每个TAB页对应一个PageView,子TAB的查询条件来自你TAB页选中的record，可以通过外键来做关联，也可以通过使用record的查询条件。
 * 每个pageview每条记录被更改后，保回原来的Grid里的对应的记录，这样便可以记录每个修改的数据
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class PageView {

    private static final Logger LOGGER = LogManager.getLogger(PageView.class) ;

    //对就package+class
    private String id ;

    //是否是查询还是增删改 增删改数据从Grid取
    private String action ;

    private String service ;

    //存储相关字段的数据类型及查询操作(like or equal)等信息
    private List<Field> fields ;

    //查询及数据表现用的
    private Form form ;

    private Grid grid ;

    public <T> T toObject(Class<?> clazz){
        T t = null ;
        try {
            t = (T) clazz.newInstance();
            for(Field field : this.fields){
                String fieldName = field.getName() ;
                String setter = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()-1).toUpperCase() ;
                Class paramType = field.getClassType() ;
                t.getClass().getMethod(setter,paramType).invoke(t,field.toObject()) ;
            }
            LOGGER.info("toObject finished");
        } catch (InstantiationException e) {
           throw new DatasetException(e.getMessage(),e) ;
        } catch (IllegalAccessException e) {
            throw new DatasetException(e.getMessage(),e) ;
        } catch (NoSuchMethodException e) {
            throw new DatasetException(e.getMessage(),e) ;
        } catch (InvocationTargetException e) {
            throw new DatasetException(e.getMessage(),e) ;
        }

        return t ;
    }
}
