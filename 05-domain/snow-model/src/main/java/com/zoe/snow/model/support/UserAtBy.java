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
    Date getCreateTime();

    /**
     * 设置创建时间。
     *
     * @param createTime 创建时间。
     */
    void setCreateTime(Date createTime);

    /**
     * 获取创建人。
     *
     * @return 创建人。
     */
    String getCreateUser();

    /**
     * 设置创建人。
     *
     * @param createUser 创建人。
     */
    void setCreateUser(String createUser);

    /**
     * 获取修改时间。
     *
     * @return 修改时间。
     */
    Date getModifyTime();

    /**
     * 设置修改时间。
     *
     * @param modifyTime 修改时间。
     */
    void setModifyTime(Date modifyTime);

    /**
     * 获取修改用户。
     *
     * @return 修改用户。
     */
    String getModifyUser();

    /**
     * 设置修改用户。
     *
     * @param modifyUser 修改用户。
     */
    void setModifyUser(String modifyUser);
}
