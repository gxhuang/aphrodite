package org.apache.aphrodite.dataset;

import java.util.List;

import org.apache.aphrodite.util.SqlType;

/**
 * 类描述：每个TAB页对应一个PageView,子TAB的查询条件来自你TAB页选中的record，可以通过外键来做关联，也可以通过使用record的查询条件。
 * 每个pageview每条记录被更改后，保回原来的Grid里的对应的记录，这样便可以记录每个修改的数据
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class PageView {


    //对就package+class
    private String id ;

    //tableName
    private String name ;

    //存储相关字段的数据类型及查询操作(like or equal)等信息
    private List<Field> fields ;

    //查询及数据展现用的
    private Form form ;

    private Grid grid ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 性能如果有问题，还是得改呀
     * @param fieldName
     * @return
     */
    public Field getField(String fieldName){
        Field result = null ;
        for(Field field : this.fields){
            if(fieldName.equals(field.getName())){
                result = field ;
            }
        }
        return result ;
    }

    
    public boolean exsits(SqlType sqlType){
    	boolean exsits = false ;
    	for(Record record : this.getGrid().getRecords()){
    		if(record.getStatus().equals(sqlType.name())){
    			exsits = true ;
    			break ;
    		}
    	}
    	return exsits ;
    }
   
}
