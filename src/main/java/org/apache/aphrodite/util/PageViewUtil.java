package org.apache.aphrodite.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.SqlContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 类描述 ：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class PageViewUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(PageViewUtil.class) ; 

    public static SqlContext getSql(Enum<SqlType> sqlType, PageView pv) {
        SqlContext sqlCxt = null ;
        if (sqlType.equals(SqlType.INSERT)) {
        	sqlCxt = getInsertSql(pv) ;
        } else if (sqlType.equals(SqlType.UPDATE)) {
        	sqlCxt = getUpdateSql(pv) ;
        } else if (sqlType.equals(SqlType.SELECT)) {
        	sqlCxt = getSelectSql(pv) ;
        } else if (sqlType.equals(SqlType.DELETE)) {
        	sqlCxt = getDeleteSql(pv) ;
        }
        return sqlCxt;
    }
    
    private static SqlContext getDeleteSql(PageView pv){
    	String tail = "ID = ?" ;
    	String head = "ROW_STATUS = '00' ";
    	String[] fieldNames = new String[1] ;
    	fieldNames[0] = "id" ;
    	return new SqlContext(head, tail, fieldNames) ;
    }


    private static SqlContext getInsertSql(PageView pv) {

        List<Field> fields = pv.getFields();
        String[] fieldNames = new String[fields.size()] ;

        //
        StringBuilder head = new StringBuilder("") ;
        StringBuilder tail = new StringBuilder("") ;
        for (int index = 0,max = fields.size()-1 ;index <= max ;index ++ ) {
            fieldNames[index] = fields.get(index).getName() ;

            head.append(ObjectTableUtil.toTableFieldFormat(fieldNames[index]));
            tail.append("?");
            if(index != max){
                head.append(",") ;
                tail.append(",") ;
            }

        }

        return new SqlContext(head.toString(),tail.toString(),fieldNames);
    }

    /**
     * update如何更高效只处理有变化的字段，如果有大表，如此更新，性能会很差的
     * @param pv
     * @return
     */
    private static SqlContext getUpdateSql(PageView pv) {
        List<Field> fields = pv.getFields();
        String[] fieldNames = new String[fields.size()] ;

        //
        StringBuilder body = new StringBuilder("") ;
        int i = 0 ;
        for (int index = 0,max = fields.size()-1 ;index <= max ;index ++ ) {
        	
        	if("id".equals(fields.get(index).getName())){
        		continue ;
        	}
            fieldNames[i] = fields.get(index).getName() ;
            body.append(ObjectTableUtil.toTableFieldFormat(fieldNames[i++])).append(" = ").append("?").append(",");
            
        }
        body.deleteCharAt(body.length()-1) ;
        fieldNames[i] = "id" ;

        return new SqlContext(body.toString()," ID = ?",fieldNames);
    }

    private static SqlContext getSelectSql(PageView pv) {

        List<Field> fields = pv.getFields();
//        Map<String,String> values = pv.getForm().getValues() ;

        

        //
        StringBuilder body = new StringBuilder("") ;
        StringBuilder where = new StringBuilder("") ;
        for (int index = 0,max = fields.size()-1 ;index <= max ;index++ ) {
//            fieldNames[index] = fields.get(index).getName() ;
            body.append(ObjectTableUtil.toTableFieldFormat(fields.get(index).getName()));
            if(index != max){
                body.append(",") ;
            }
        }

    	List<String> fieldNames = new ArrayList<String>() ;
    	 for(int i = 0,max = fields.size() ;i< max ;i++){
    		 if(fields.get(i).getValue() != null && !"".equals(fields.get(i).getValue())){
    			 
    			 fieldNames.add(fields.get(i).getName()) ;
    			 where.append(" ").append(fields.get(i).getName()).append(" ").append(getOperator(fields.get(i).getOp())).append(" AND");
    		 }
         }
    	 if(where.length() > 0){
    		 where.delete(where.length()-4, where.length()) ;
    	 }else{
    		 LOGGER.info("no condition found.");
    		 where.append(" 1=1 ") ;
    	 }
        
       
        return new SqlContext(body.toString(),where.toString(),fieldNames.toArray(new String[0]));
    }
    
    private static String getOperator(String op){
    	String result = " = ?" ;
    	if("LIKE".equals(op)){
    		result = "LIKE ?" ;
    	}else if("GREATER".equals(op)){
    		result = " > ? " ;
    	}else if("IN".equals(op)){
    		result = " IN(?)" ;
    	}
    	return result ;
    }
    
    
    public static void main(String[] args) {
		StringBuilder str = new StringBuilder("abcd AND") ;
		System.out.println(str.delete(str.length()-4, str.length()));
		
	}

}
