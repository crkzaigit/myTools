package com.cdmcs.qx2cdjw.bean;

/**
 * Created by cdmcs on 2015/3/28.
 */
public class ColumnBean {
    private int column_id;
    private String column_name;
    private String comments;
    private String data_type;
    private int data_length;

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public int getData_length() {
        return data_length;
    }

    public void setData_length(int data_length) {
        this.data_length = data_length;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private String nullable;
    private Object value;

    public ColumnBean copy(){
        ColumnBean ret = new ColumnBean();
        ret.setData_length(this.getData_length());
        ret.setValue(this.value);
        ret.setComments(this.comments);
        ret.setColumn_name(this.column_name);
        ret.setColumn_id(this.column_id);
        ret.setNullable(this.nullable);
        ret.setData_type(this.data_type);
        return ret;
    }
}
