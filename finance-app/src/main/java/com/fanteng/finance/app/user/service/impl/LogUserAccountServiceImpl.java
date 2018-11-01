package com.fanteng.finance.app.user.service.impl;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.app.user.dao.LogUserAccountDao;
import com.fanteng.finance.app.user.service.LogUserAccountService;
import com.fanteng.finance.entity.LogUserAccount;

@Service
public class LogUserAccountServiceImpl extends BaseServiceImpl<LogUserAccountDao, LogUserAccount>
		implements LogUserAccountService {

}
