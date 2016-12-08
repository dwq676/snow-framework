package com.zoe.snow.demo;

/**
 * UserServiceImpl
 *
 * @author Dai Wenqing
 * @date 2016/6/12
 */
public class UserServiceImpl implements UserService {
    public User getUser(Long id) {
        return new User(id, "username" + id);
    }
}
