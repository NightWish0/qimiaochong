package com.qimiaochong.common.dao;

import com.qimiaochong.common.base.BaseMapper;
import com.qimiaochong.common.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysPermissionDao extends BaseMapper<SysPermission> {

    List<String> getPermissionCodes(@Param("userId") Integer userId);
}
