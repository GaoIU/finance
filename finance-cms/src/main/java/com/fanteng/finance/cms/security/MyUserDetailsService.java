package com.fanteng.finance.cms.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.AntPathMatcher;

import com.fanteng.core.Operation;
import com.fanteng.exception.ResourceErrorException;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.util.StringUtil;

public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysResourceService sysResourceService;

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Value("${sys.role.guest.code}")
	private String GUEST;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		SysUser sysUser = sysUserService.findOne("userName", Operation.EQ, userName);
		return new User(userName, sysUser.getPassword(), getRoles(sysUser.getId()));
	}

	private Set<GrantedAuthority> getRoles(String sysUserId) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(0);

		List<SysResource> list = sysResourceService.getResourcesBySysUserId(sysUserId);
		for (SysResource sysResource : list) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
					sysResource.getCode() + "@" + sysResource.getMethod());
			authorities.add(grantedAuthority);
		}

		if (CollectionUtils.isEmpty(authorities)) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(GUEST);
			authorities.add(grantedAuthority);
		}

		return authorities;
	}

	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		List<SysResource> resources = sysResourceService.findOnes("url", Operation.EQ, request.getRequestURI());
		if (CollectionUtils.isEmpty(resources)) {
			throw new ResourceErrorException("无效的访问");
		}

		boolean hasPermission = false;

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			SysUser sysUser = sysUserService.findOne("userName", Operation.EQ, userName);

			List<SysResource> list = sysResourceService.getResourcesBySysUserId(sysUser.getId());
			for (SysResource sysResource : list) {
				if (StringUtil.isBlank(sysResource.getUrl())) {
					continue;
				}

				if (antPathMatcher.match(request.getRequestURI(), sysResource.getUrl())) {
					hasPermission = true;
					break;
				}
			}
		}

		return hasPermission;
	}

}
