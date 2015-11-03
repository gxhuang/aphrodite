package org.apache.aphrodite.util;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.SqlContext;

import java.util.List;
import java.util.Map;

/**
 * 类描述 ：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class PageViewUtil {

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
        for (int index = 0,max = fields.size()-1 ;index <= max ;max ++ ) {
            fieldNames[index] = fields.get(index).getName() ;

            head.append(ObjectTableUtil.toTableFieldFormat(fieldNames[index]));
            tail.append("?");
            if(index != max -1){
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
        for (int index = 0,max = fields.size()-1 ;index <= max ;max ++ ) {
            fieldNames[index] = fields.get(index).getName() ;
            body.append(ObjectTableUtil.toTableFieldFormat(fieldNames[index])).append(" = ").append("?");
            if(index != max -1){
                body.append(",") ;
            }
        }

        return new SqlContext(body.toString()," WHERE ID = ?",fieldNames);
    }

    private static SqlContext getSelectSql(PageView pv) {

        List<Field> fields = pv.getFields();
        Map<String,String> values = pv.getForm().getValues() ;

        String[] fieldNames = new String[values.size()] ;

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

        int start = 0 ;
        for(Map.Entry<String,String> entry : values.entrySet()){
            fieldNames[start ++] = entry.getKey() ;
            where.append(" ").append(fieldNames[start -1]).append(" ").append(getOperator(pv.getField(fieldNames[start -1]).getOp()));
            if(start != fieldNames.length){
                where.append(" AND") ;
            }

        }
        return new SqlContext(body.toString(),where.toString(),fieldNames);
    }
    
    private static String getOperator(String op){
    	String result = " = ?" ;
    	if("LIKE".equals(op)){
    		result = "LIKE ?" ;
    	}else if("GREATER".equals(op)){
    		result = " > ? " ;
    	}
    	return result ;
    }

    public enum SqlType {
        INSERT, DELETE, UPDATE, SELECT
    }
}
