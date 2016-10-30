package com.cn.chen.dao;

import com.cn.chen.domain.Citycode;

public interface CitycodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Citycode record);

    int insertSelective(Citycode record);

    Citycode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Citycode record);

    int updateByPrimaryKey(Citycode record);

	Citycode selectByCityName(String cityName);
}