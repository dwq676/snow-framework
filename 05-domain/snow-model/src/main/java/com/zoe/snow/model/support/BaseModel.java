package com.zoe.snow.model.support;

import com.zoe.snow.model.Model;

import java.util.Date;

/**
 * BaseModel
 *
 * @author Dai Wenqing
 * @date 2016/1/7
 */
public interface BaseModel extends Model, ValidFlag, Domain, UserAtBy {

    /**
     * 获取备注。
     *
     * @return 备注。
     */
    String getRemark();

    /**
     * 设置备注。
     *
     * @param remark 备注。
     */
    void setRemark(String remark);

}
