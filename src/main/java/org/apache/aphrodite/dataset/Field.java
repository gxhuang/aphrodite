package org.apache.aphrodite.dataset;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Field {

    //查询时开始时间和结束时间对应的查询字段是一样的，但是Id不一样 不同的ID映射到同一个NAME上，如果AND OR这种条件呢？复合组合 mybatis?
    private String id ;

    private String name ;

    private String type ;

    private String dataType ;

    //日期字段专用
    private String format ;


    private String value ;

    //add change
    private String status ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


    public static enum Operator{
        EQUALS,PRE_LIKE,LIKE,GREATER,LESS
    }
}
