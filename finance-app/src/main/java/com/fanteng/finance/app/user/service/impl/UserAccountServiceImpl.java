package com.fanteng.finance.app.user.service.impl;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.app.user.dao.UserAccountDao;
import com.fanteng.finance.app.user.service.UserAccountService;
import com.fanteng.finance.entity.UserAccount;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountDao, UserAccount> implements UserAccountService {

}
