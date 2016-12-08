package com.zoe.snow.dao.dialect.oracle;

import com.zoe.snow.model.annotation.Jsonable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015/9/2.
 */
@Repository("snow.dao.dialect.column.oracle")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table
public class OracleColumns extends OracleTableInfo {
    private String columnName;
    private String columnComment;
    private float columnDataLength;
    private float columnPrecision;
    private String columnDataType;
    private String isNullAble;
    private String isPrimaryKey = "0";

    @Jsonable
    @Column(name = "COLUMN_KEY")
    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    @Jsonable
    @Column(name = "IS_NULLABLE")
    public String getIsNullAble() {
        return isNullAble;
    }

    public void setIsNullAble(String isNullAble) {
        this.isNullAble = isNullAble;
    }

    @Jsonable
    @Column(name = "COLUMN_DATA_LENGTH")//COLUMN_DATA_LENGTH//CHARACTER_MAXIMUM_LENGTH
    public float getColumnDataLength() {
        return columnDataLength;
    }

    public void setColumnDataLength(float columnDataLength) {
        this.columnDataLength = columnDataLength;
    }

    @Jsonable
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Jsonable
    @Column(name = "COLUMN_COMMENT")
    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Jsonable
    @Column(name = "NUMERIC_PRECISION")
    public float getColumnPrecision() {
        return columnPrecision;
    }

    public void setColumnPrecision(float columnPrecision) {
        this.columnPrecision = columnPrecision;
    }

    @Jsonable
    @Column(name = "DATA_TYPE")
    public String getColumnDataType() {
        return columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }
}
