package com.fanteng.finance.cms.user.service.impl;

import org.springframework.stereotype.Service;

import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.cms.user.dao.LogUserAccountDao;
import com.fanteng.finance.cms.user.service.LogUserAccountService;
import com.fanteng.finance.entity.LogUserAccount;

@Service
public class LogUserAccountServiceImpl extends BaseServiceImpl<LogUserAccountDao, LogUserAccount>
		implements LogUserAccountService {

}
