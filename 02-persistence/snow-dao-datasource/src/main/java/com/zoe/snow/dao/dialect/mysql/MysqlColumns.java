package com.zoe.snow.dao.dialect.mysql;

import com.zoe.snow.dao.dialect.Columns;
import com.zoe.snow.model.annotation.Jsonable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MysqlColumnModel
 *
 * @author Dai Wenqing
 * @date 2015/8/31
 */
@Repository("snow.dao.dialect.column.mysql")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table
public class MysqlColumns extends MysqlTableInfo implements Columns {

    /*
     * TABLE_CATALOG TABLE_SCHEMA TABLE_NAME COLUMN_NAME ORDINAL_POSITION
     * COLUMN_DEFAULT IS_NULLABLE DATA_TYPE CHARACTER_MAXIMUM_LENGTH
     * CHARACTER_OCTET_LENGTH NUMERIC_PRECISION NUMERIC_SCALE DATETIME_PRECISION
     * CHARACTER_SET_NAME COLLATION_NAME COLUMN_TYPE COLUMN_KEY EXTRA PRIVILEGES
     * COLUMN_COMMENT
     */

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
    @Column(name = "CHARACTER_MAXIMUM_LENGTH")
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
