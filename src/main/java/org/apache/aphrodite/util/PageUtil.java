package org.apache.aphrodite.util;

import org.apache.aphrodite.dataset.sys.User;

import java.lang.reflect.Field;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class PageUtil {

    public static <T> String html(Class<T> clazz){
        StringBuilder html = new StringBuilder() ;
        Field[] fields = clazz.getDeclaredFields() ;
        for(Field field : fields){
            System.out.println("field name:" + field.getName() + " field type:" + field.getType().getName()) ;
        }
        return html.toString() ;
    }

    private <T> String formhtml(Field[] fields){

        StringBuilder formhtml = new StringBuilder("<form id=\"orderForm\" class=\"container-fluid form-horizontal hide\" dataset=\"form\" role=\"form\">") ;
        for (Field field:fields){
            formhtml.append("<div class=\"form-group\">").append("\r\n")
                    .append("<label class=\"col-sm-1 control-label\">").append(field.getName()).append("</label>")
                    .append("<div class=\"col-sm-5\">").append("\r\n").append("<input id=\"").append(field.getName()).append("\"").append(" ")
                    .append("type=\"").append(getType(field)).append("\"").append(" ").append("datetype=\"").append(getDataType(field)).append("\" class=\"form-control\"")
                    .append(" name=\"").append(field.getName()).append(" ").append(" placeholder=\"").append(field.getName()).append("\">")
                    .append("</div>") ;
        }

        return formhtml.toString() ;
    }

    private String getId(Field field){
        String id = "id=\""+field.getName()+"\"" ;
        return  id;
    }

    private String getName(Field field){
        String name = "name=\""+field.getName()+"\"" ;
        return  name;
    }

    private String getPlaceholder(Field field){
        String palceholder = "palceholder=\""+field.getName()+"\"" ;
        return  palceholder;
    }


    private String getformat(Field field){
        String type = field.getType().getSimpleName() ;
        String format = "" ;
        if("date".equals(type)){
            format = "format=\"yyyymmdd\"" ;
        }
        return format ;
    }

    private String getType(Field field){
        String type = "type=\"" ;
        String simpleType = field.getType().getSimpleName() ;
        if("date".equals(simpleType)){
            type = type+"date\"" ;
        }else if("string".equals(simpleType)){
            type = type+"string\"" ;
        }
        return type ;
    }

    private String getDataType(Field field){

        String datatype = "datatype=\"" ;
        String simpleType = field.getType().getSimpleName() ;
        if("date".equals(simpleType)){
            datatype = datatype+"date\"" ;
        }else if("string".equals(simpleType)){
            datatype = datatype+"string\"" ;
        }
        return datatype ;
    }

    private <T> String gridhtml(Class<T> clazz){
        StringBuilder gridhtml = new StringBuilder() ;

        return gridhtml.toString() ;
    }

    public static void main(String[] args){
        PageUtil.html(User.class) ;
    }
}
