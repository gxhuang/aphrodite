package org.apache.aphrodite.util;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class ObjectTableUtil {

    public static String toTableFieldFormat(String fieldName){
        StringBuilder builder = new StringBuilder() ;
        for(int index =0 ,len = fieldName.length() ;index < len; index++){
            char c = fieldName.charAt(index) ;
            if(c <'Z' && c > 'A'){
                builder.append('_').append(c) ;
            }else{
                builder.append((char)(c-32)) ;
            }
        }
        return builder.toString() ;
    }

    public static String toObjectFieldFormat(String fieldName){
        StringBuilder builder = new StringBuilder() ;
        for(int index =0 ,len = fieldName.length() ;index < len; index++){
            char c = fieldName.charAt(index) ;
            if(c == '_'){
                builder.append(fieldName.charAt(++index)) ;
            }else{
                builder.append((char)(c+32)) ;
            }
        }
        return builder.toString() ;
    }

    public static void main(String[] args){
        String name = "huangYueWen" ;
        System.out.println(ObjectTableUtil.toTableFieldFormat(name));
        System.out.println((char)('H'+32));
        System.out.println(ObjectTableUtil.toObjectFieldFormat("HUANG_YUE_WEN"));

    }
}
