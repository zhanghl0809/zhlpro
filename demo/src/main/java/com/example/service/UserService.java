package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     *  根据id查询
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "SelById",key = "#id",condition = "#id>0")
    public User Sel(int id){
        return userMapper.Sel(id);
    }
}
