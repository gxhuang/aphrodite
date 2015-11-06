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
        SqlContext sql = null ;
        if (sqlType.equals(SqlType.INSERT)) {
        	sql = getInsertSql(pv) ;
        } else if (sqlType.equals(SqlType.UPDATE)) {
        	sql = getUpdateSql(pv) ;
        } else if (sqlType.equals(SqlType.SELECT)) {
        	sql = getSelectSql(pv) ;
        } else if (sqlType.equals(SqlType.DELETE)) {

        }
        return sql;
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

    private static SqlContext getUpdateSql(PageView pv) {
        List<Field> fields = pv.getFields();
        String[] fieldNames = new String[fields.size()] ;

        //
        StringBuilder body = new StringBuilder("") ;
        for (int index = 0,max = fields.size()-1 ;index <= max ;index ++ ) {
            fieldNames[index] = fields.get(index).getName() ;
            body.append(ObjectTableUtil.toTableFieldFormat(fieldNames[index])).append(" = ").append("?");
            if(index != max){
                body.append(",") ;
            }
        }

        return new SqlContext(body.toString()," WHERE ID = ?",fieldNames);
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
