package com.zoe.snow.demo;

import javax.validation.constraints.Min;

/**
 * UserRestService
 *
 * @author Dai Wenqing
 * @date 2016/6/12
 */
public interface UserRestService {
    User getUser(@Min(value = 1L, message = "User ID must be greater than 1") Long id);
}