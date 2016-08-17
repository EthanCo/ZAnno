package com.ethanco.zanno;

/**
 * Created by EthanCo on 2016/8/17.
 */
public class FieldBean {

    private String fieldName;
    private String fieldType;

    public FieldBean(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
