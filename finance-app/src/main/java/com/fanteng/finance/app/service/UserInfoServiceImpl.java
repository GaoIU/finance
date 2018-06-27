package com.fanteng.finance.app.service;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.dao.UserInfoDao;
import com.fanteng.finance.entity.UserInfo;
import com.fanteng.finance.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}
