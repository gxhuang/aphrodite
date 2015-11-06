package org.apache.aphrodite.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;


/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:17:49
 *
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
        html.append(formhtml(fields,i)).append(gridhtml(fields,i)) ;
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
        formhtml.append(getSpace(1)).append("<form id=\"orderForm\" class=\"container-fluid form-horizontal\" dataset=\"form\" role=\"form\">")
            .append(ENTER);
        for (int i=0,length=fields.length;i<length;i=i+2){
            Field field = null ;
            formhtml.append(getSpace(level+1)).append("<div class=\"form-group\">").append(ENTER) ;
                    for(int j= i ;j<i+2;j++){
                        field = fields[j] ;
                        formhtml.append(getSpace(level + 2)).append("<label class=\"col-sm-1 control-label\">").append(field.getName()).append("</label>").append(ENTER)
                                .append(getSpace(level + 2)).append("<div class=\"col-sm-5\">").append(ENTER)
                                .append(getSpace(level + 3)).append("<input ").append(getId(field)).append(" ")
                                .append(getType(field)).append(" ").append(getDataType(field)).append(" ").append("class=\"form-control\"")
                                .append(" ").append(getPlaceholder(field)).append(">").append(ENTER)
                                .append(getSpace(level + 2)).append("</div>").append(ENTER) ;
                    }
            formhtml.append(getSpace(level + 1)).append("</div>").append(ENTER) ;
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

    private static String getType(Field field){
        String type = "type=\"" ;
        String simpleType = field.getType().getSimpleName() ;
        if("Date".equals(simpleType)){
            type = type+"text\"" ;
        }else {
            type = type+"text\"" ;
        }
        return type ;
    }

    private static String getDataType(Field field){

        String datatype = "datatype=\"" + field.getType().getSimpleName()+"\"" ;
        return datatype ;
    }

    public static <T> String gridhtml(Field[] fields,int level){
        StringBuilder gridhtml = new StringBuilder() ;
        gridhtml.append(getSpace(1)).append("<div name=\"grid\">").append(ENTER)
                .append(toolbarhtml(level + 1)).append(ENTER)
                .append(getSpace(level+1)).append(ENTER)
                .append(tablehtml(fields,level+1)).append(ENTER)
                .append(pagenationhtml(level + 1)).append(ENTER)
                .append(getSpace(level)).append("</div>") ;
        return gridhtml.toString() ;
    }

    private static String toolbarhtml(int level){
        StringBuilder toolbarhtml = new StringBuilder() ;
        toolbarhtml.append(getSpace(level)).append("<div class=\"row\" name=\"toolbar\">").append(ENTER)
                .append(getSpace(level + 1)).append("<div class=\"col-sm-9\">").append(ENTER)
                .append(getSpace(level + 2)).append("<div class=\"btn-group\">").append(ENTER)
                .append(getSpace(level + 3)).append("<button type=\"button\" class=\"btn btn-success\" name=\"new\">�½�</button>").append(ENTER)
                .append(getSpace(level + 3)).append("<button type=\"button\" class=\"btn btn-primary\" name=\"edit\">�༭</button>").append(ENTER)
                .append(getSpace(level+3)).append("<button type=\"button\" class=\"btn btn-warning\" name=\"delete\">ɾ��</button>").append(ENTER)
                .append(getSpace(level+3)).append("<button type=\"button\" class=\"btn btn-info\" name=\"search\">����ѡ��</button>").append(ENTER)
                .append(getSpace(level+3)).append("<button type=\"button\" class=\"btn btn-warning\">warning</button>").append(ENTER)
                .append(getSpace(level + 2)).append("</div>").append(ENTER)
                .append(getSpace(level+1)).append("</div>").append(ENTER)
                .append(getSpace(level)).append("</div>").append(ENTER) ;
        return toolbarhtml.toString() ;
    }

    private static String tablehtml(Field[] fields,int level){
        StringBuilder tablehtml = new StringBuilder();
        tablehtml.append(getSpace(level)).append("<div class=\"panel panel-default\">").append(ENTER)
                .append(getSpace(level + 1)).append("<div class=\"panel-body\">").append(ENTER)
                .append(getSpace(level + 2)).append("<table class=\"table table-hover\">").append(ENTER)
                .append(theadhtml(fields, level + 3)).append(ENTER)
                .append(tbodyhtml(level+3)).append(ENTER)
                .append(getSpace(level+2)).append("</table>").append(ENTER)
                .append(getSpace(level+1)).append("</div>").append(ENTER)
                .append(getSpace(level)).append("</div>").append(ENTER) ;
        return tablehtml.toString() ;
    }

    private static String theadhtml(Field[] fields,int level){
        StringBuilder theadhtml = new StringBuilder() ;
        theadhtml.append(getSpace(level)).append("<thead>").append(ENTER)
                .append(getSpace(level+1)).append("<tr>").append(ENTER) ;

        for(Field field:fields){
            theadhtml.append(getSpace(level+2)).append("<th width=\"80px\" ").append(getName(field)).append(" ").append(getDataType(field)).append(">").append(getName(field)).append("</th>").append(ENTER);
        }
        theadhtml.append(getSpace(level+1)).append("</tr>").append(ENTER)
                .append(getSpace(level)).append("</thead>").append(ENTER) ;
        return theadhtml.toString() ;
    }
    private static String tbodyhtml(int level){
        StringBuilder tbodyhtml = new StringBuilder() ;
        tbodyhtml.append(getSpace(level)).append("<tbody>").append(ENTER)
                .append(getSpace(level)).append("</tbody>").append(ENTER);
        return tbodyhtml.toString() ;
    }

    private static String pagenationhtml(int level){
        StringBuilder pagenationhtml = new StringBuilder() ;
        pagenationhtml.append(getSpace(level)).append("<ul class=\"pager\">").append(ENTER)
                .append(getSpace(level+1)).append("<li class=\"previous\">").append(ENTER)
                .append(getSpace(level+2)).append("<a href=\"#\">&larr;��һҳ</a>").append(ENTER)
                .append(getSpace(level + 1)).append("</li>").append(ENTER)
                .append(getSpace(level+1)).append("<span class=\"info\">��2ҳ/��20ҳ</span>").append(ENTER)
                .append(getSpace(level+1)).append("<li class=\"next\">").append(ENTER)
                .append(getSpace(level+2)).append("<a href=\"#\">��һҳ&rarr;</a>").append(ENTER)
                .append(getSpace(level+1)).append("</li>").append(ENTER)
                .append(getSpace(level)).append("</ul>").append(ENTER) ;
        return pagenationhtml.toString() ;
    }

    public static void main(String[] args){
        String html = PageUtil.html(PageUtil.class) ;
        File file = new File("D:\\workspace-luna\\aphrodite\\src\\main\\webapp\\menu.html") ;
        FileOutputStream fos = null ;
        try {
            fos = new FileOutputStream(file) ;
            byte[] data = html.getBytes(Charset.forName("utf-8")) ;
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
