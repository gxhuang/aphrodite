package org.apache.aphrodite.util;


import org.apache.aphrodite.dataset.order.OrderItem;

import java.lang.reflect.Field;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class SqlGenerator {

    private static final String ENTER = "\r\n" ;

    private static final String SPACE = " " ;

    private static final String DOU = "`" ;

    public static String generatorSql(Class<?> clazz){
        StringBuilder builder = new StringBuilder("DROP TABLE IF EXISTS") ;

        String clazzName = clazz.getSimpleName();
        String tableName = split(clazzName) ;

        builder.append(SPACE).append(DOU).append(tableName).append(DOU).append(";").append(ENTER) ;
        builder.append("CREATE TABLE `").append(tableName).append("`").append("(").append(ENTER) ;


        Field[] fields = clazz.getFields() ;
        for(Field field : fields){
            String fieldName = field.getName() ;
            String colName = split(fieldName) ;
            String fieldType = field.getType().getSimpleName() ;
            builder.append(DOU).append(colName).append(DOU).append(" ") ;
            if ("Date".equals(fieldType)){
               builder.append("datetime") ;
            }else if("String".equals(fieldType)){
                builder.append("varchar(32)") ;
            }else if("Integer".equals(fieldType)){
                builder.append("number(16)") ;
            }else if("BigDecimal".equals(fieldType)){
                builder.append("number(16,2)") ;
            }
            builder.append(",").append(ENTER) ;
        }

        builder.append(");") ;

        return builder.toString() ;
    }


    public static String split(String name){
        StringBuilder builder = new StringBuilder() ;
        for(char c : name.toCharArray()){
            if(c >= 'A' && c <= 'Z'){
//                System.out.println(c) ;
                builder.append("_").append(c) ;
            }else{
                builder.append((char)(c-32)) ;
            }
        }
        if(builder.indexOf("_") == 0){
            builder = builder.deleteCharAt(0) ;
        }

        return builder.toString() ;
    }


    public static void main(String[] args){

        System.out.println(generatorSql(OrderItem.class)) ;
    }
}
