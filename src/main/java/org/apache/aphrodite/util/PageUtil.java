package org.apache.aphrodite.util;

import org.apache.aphrodite.dataset.sys.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class PageUtil {

    private static final String ENTER = "\r\n" ;

    private static final String TAB = "    " ;

    public static <T> String html(Class<T> clazz){
        StringBuilder html = new StringBuilder() ;
        Field[] fields = clazz.getDeclaredFields() ;
//        for(Field field : fields){
//            System.out.println("field name:" + field.getName() + " field type:" + field.getType().getName()) ;
//        }
        int i = 1 ;
        html.append(formhtml(fields,1)).append(gridhtml(fields)) ;
        return html.toString() ;
    }

    public static String getSpace(int level){
        StringBuilder space = new StringBuilder() ;
        for(int i=0 ;i<level;i++){
            space.append(TAB) ;
        }
        return space.toString() ;
    }

    public static <T> String formhtml(Field[] fields,int level){

        StringBuilder formhtml = new StringBuilder() ;
        formhtml.append(getSpace(1)).append("<form id=\"orderForm\" class=\"container-fluid form-horizontal hide\" dataset=\"form\" role=\"form\">")
            .append(ENTER);
        for (Field field:fields){
            formhtml.append(getSpace(level+1)).append("<div class=\"form-group\">").append(ENTER)
                    .append(getSpace(level + 2)).append("<label class=\"col-sm-1 control-label\">").append(field.getName()).append("</label>").append(ENTER)
                    .append(getSpace(level + 2)).append("<div class=\"col-sm-5\">").append(ENTER)
                    .append(getSpace(level+3)).append("<input ").append(field.getName()).append(" ")
                    .append(getType(field)).append(" ").append(getDataType(field)).append(" ").append("class=\"form-control\"")
                    .append(field.getName()).append(" ").append(getPlaceholder(field)).append("\">").append(ENTER)
                    .append(getSpace(level+2)).append("</div>").append(ENTER)
                    .append(getSpace(level+1)).append("</div>").append(ENTER) ;
        }
        formhtml.append(getSpace(level)).append("</form>").append(ENTER);
        return formhtml.toString() ;
    }

    private static String getId(Field field){
        String id = "id=\""+field.getName()+"\"" ;
        return  id;
    }

    private static String getName(Field field){
        String name = "name=\""+field.getName()+"\"" ;
        return  name;
    }

    private static String getPlaceholder(Field field){
        String palceholder = "palceholder=\""+field.getName()+"\"" ;
        return  palceholder;
    }


    private static String getformat(Field field){
        String type = field.getType().getSimpleName() ;
        String format = "" ;
        if("date".equals(type)){
            format = "format=\"yyyymmdd\"" ;
        }
        return format ;
    }

    private static String getType(Field field){
        String type = "type=\"" ;
        String simpleType = field.getType().getSimpleName() ;
        if("Date".equals(simpleType)){
            type = type+"text\"" ;
        }else if("String".equals(simpleType)){
            type = type+"text\"" ;
        }
        return type ;
    }

    private static String getDataType(Field field){

        String datatype = "datatype=\"" ;
        String simpleType = field.getType().getSimpleName() ;
        if("Date".equals(simpleType)){
            datatype = datatype+"Date\"" ;
        }else if("String".equals(simpleType)){
            datatype = datatype+"String\"" ;
        }
        return datatype ;
    }

    public static <T> String gridhtml(Field[] fields){
        StringBuilder gridhtml = new StringBuilder() ;

        return gridhtml.toString() ;
    }

    private static String toolbarhtml(){
        StringBuilder toolbarhtml = new StringBuilder() ;

        return toolbarhtml.toString() ;
    }

    private static String tablehtml(Field[] fields){
        StringBuilder tablehtml = new StringBuilder() ;
        return tablehtml.toString() ;
    }

    private static String theadhtml(Field[] fields){
        StringBuilder theadhtml = new StringBuilder() ;
        return theadhtml.toString() ;
    }

    private static String pagenationhtml(){
        StringBuilder pagenationhtml = new StringBuilder() ;
        return pagenationhtml.toString() ;
    }

    public static void main(String[] args){
        String html = PageUtil.html(User.class) ;
        File file = new File("D:\\workspace-luna\\aphrodite\\src\\main\\webapp\\tmp.html") ;
        FileOutputStream fos = null ;
        try {
            fos = new FileOutputStream(file) ;
            byte[] data = html.getBytes(Charset.forName("UTF-8")) ;
            fos.write(data);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.print(html);
    }
}
