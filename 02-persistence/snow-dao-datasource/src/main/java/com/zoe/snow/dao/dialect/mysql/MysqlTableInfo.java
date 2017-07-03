package com.zoe.snow.dao.dialect.mysql;

import com.zoe.snow.dao.dialect.TableInfo;
import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.String;

/**
 * MysqlObjectModel
 *
 * @author Dai Wenqing
 * @date 2015/8/27
 */
@Repository("snow.dao.dialect.table.info.mysql")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table
public class MysqlTableInfo extends BaseModelSupport implements TableInfo{

    /*
     * TABLE_CATALOG TABLE_SCHEMA TABLE_NAME TABLE_TYPE ENGINE VERSION
     * ROW_FORMAT TABLE_ROWS AVG_ROW_LENGTH DATA_LENGTH MAX_DATA_LENGTH
     * INDEX_LENGTH DATA_FREE //AUTO_INCREMENT CREATE_TIME UPDATE_TIME
     * CHECK_TIME TABLE_COLLATION CHECKSUM CREATE_OPTIONS TABLE_COMMENT
     */

    private String tableName;
    private String comment;
    private String catalog;
    private String schema;

    @Jsonable
    @Column(name = "TABLE_SCHEMA")
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Jsonable
    @Column(name = "TABLE_NAME")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Jsonable
    @Column(name = "TABLE_COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Jsonable
    @Column(name = "TABLE_CATALOG")
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}