package com.imooc.dataobject.mapper;

import com.imooc.dataobject.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lee on 2018/1/9.
 */
@Mapper
public interface UserMapper {
    /** 查看该用户是否存在 */
    int checkUsername(User user);
    /** 用户登陆 */
    User selectLogin(User user);
}
