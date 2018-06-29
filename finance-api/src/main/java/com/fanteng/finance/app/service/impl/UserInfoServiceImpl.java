package com.fanteng.finance.app.service.impl;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.app.dao.UserInfoDao;
import com.fanteng.finance.app.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}
