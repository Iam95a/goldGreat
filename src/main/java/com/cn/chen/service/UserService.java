package com.cn.chen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.chen.dao.UserTMapper;
import com.cn.chen.domain.UserT;

@Service
public class UserService {
	@Autowired
	private UserTMapper userTMapper;
	
	public UserT getUser()
	{
		return userTMapper.selectByPrimaryKey(1);
		
	}

}
