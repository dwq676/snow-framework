package com.zoe.snow.auth.service;

/**
 * 根据域来判断是为管理员
 *
 * @author Dai Wenqing
 * @date 2016/11/10
 */
public interface BaseDomainService {
    boolean getIsAdmin(String domain);
}
