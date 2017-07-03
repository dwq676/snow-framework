package com.zoe.snow.dao.dialect.oracle;

import com.zoe.snow.dao.dialect.TableInfo;
import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015/9/2.
 */
@Repository("snow.dao.dialect.table.info.oracle")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table
public class OracleTableInfo extends BaseModelSupport implements TableInfo{
    private String tableName;
    private String comments;
    private String catalog;
    private String schema;

    @Jsonable
    @Column(name = "CATALOG")
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Jsonable
    @Column(name = "OWNER")
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
    @Column(name = "COMMENTS")
    public String getComment() {
        return comments;
    }

    public void setComment(String comments) {
        this.comments = comments;
    }
}
