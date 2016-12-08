package com.zoe.snow.dao.dialect;

import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Jsonable;

/**
 * Column
 *
 * @author Dai Wenqing
 * @date 2016/10/12
 */
public interface Columns extends Model{
    public String getIsPrimaryKey();

    void setIsPrimaryKey(String isPrimaryKey);

    String getIsNullAble();

    void setIsNullAble(String isNullAble);

    float getColumnDataLength();

    void setColumnDataLength(float columnDataLength);

    String getColumnName();

    void setColumnName(String columnName);

    String getColumnComment();

    void setColumnComment(String columnComment);

    float getColumnPrecision();

    void setColumnPrecision(float columnPrecision);

    String getColumnDataType();

    void setColumnDataType(String columnDataType);

}
