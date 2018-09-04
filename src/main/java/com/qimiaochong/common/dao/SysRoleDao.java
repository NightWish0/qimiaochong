package com.qimiaochong.common.dao;

import com.qimiaochong.common.base.BaseMapper;
import com.qimiaochong.common.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysRoleDao extends BaseMapper<SysRole> {

    List<String> getRoleCodes(@Param("userId") Integer userId);
}
