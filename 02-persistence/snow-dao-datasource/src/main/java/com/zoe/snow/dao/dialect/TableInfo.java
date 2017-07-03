package com.zoe.snow.dao.dialect;

import com.zoe.snow.model.Model;

/**
 * TableInfo
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
public interface TableInfo extends Model {
    public String getCatalog();

    public void setCatalog(String catalog);

    public String getSchema();

    public void setSchema(String schema);

    public String getTableName();

    public void setTableName(String tableName);

    public String getComment();

    public void setComment(String comments);
}
