package com.taiping.ibot.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taiping.ibot.common.entity.system.SystemUser;

public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
