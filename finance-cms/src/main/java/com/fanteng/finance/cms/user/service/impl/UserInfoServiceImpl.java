package com.fanteng.finance.cms.user.service.impl;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.user.dao.UserInfoDao;
import com.fanteng.finance.cms.user.service.UserInfoService;
import com.fanteng.finance.entity.UserInfo;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}
