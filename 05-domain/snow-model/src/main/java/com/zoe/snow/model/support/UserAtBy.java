package com.zoe.snow.model.support;

import java.util.Date;

/**
 * UserAtBy
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/26
 */
public interface UserAtBy {
    /**
     * 获取创建时间。
     *
     * @return 创建时间。
     */
    Date getCreatedAt();

    /**
     * 设置创建时间。
     *
     * @param createdAt 创建时间。
     */
    void setCreatedAt(Date createdAt);

    /**
     * 获取创建人。
     *
     * @return 创建人。
     */
    String getCreatedBy();

    /**
     * 设置创建人。
     *
     * @param createdBy 创建人。
     */
    void setCreatedBy(String createdBy);

    /**
     * 获取修改时间。
     *
     * @return 修改时间。
     */
    Date getUpdatedAt();

    /**
     * 设置修改时间。
     *
     * @param updatedAt 修改时间。
     */
    void setUpdatedAt(Date updatedAt);

    /**
     * 获取修改用户。
     *
     * @return 修改用户。
     */
    String getUpdatedBy();

    /**
     * 设置修改用户。
     *
     * @param updatedBy 修改用户。
     */
    void setUpdatedBy(String updatedBy);
}
