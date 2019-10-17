package org.sdrc.bbbp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sdrc.bbbp.domain.RoleFeaturePermissionScheme;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.domain.UserAreaMapping;
import org.sdrc.bbbp.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private UserService userService;

	/*
	 * 
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 * 
	 * Here,we are overriding spring security loadbyUsername method. Note: We cannot apply @Transactional directly because,
	 * Spring uses JDK-Proxy by default.So AspectJ required for class Injection directly from container.
	 * 
	 * Authorities are ADDED IN format of : ROLE_NAME:Feature,Permission. So @PreAuthorize annotation will be like
	 * 
	 * @PreAuthorize("hasRole('CCI:data_entry,edit')")
	 * 
	 * Note : If you want to remove Role specifics, you can remove Role Name from GrantedAuthority
	 * 
	 * @PreAuthorize("hasRole('data_entry,edit')")
	 */

	@Override
	@Transactional
	public UserModel loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.findByUsername(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password !");
		}
		
		Collection<UserAreaMapping> areaMappings = user.getAreas();
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		Set<String> roles = new HashSet<>();
		Set<Integer> roleIds = new HashSet<>();
		for (UserAreaMapping userAreaMapping : areaMappings) {
			List<UserRoleFeaturePermissionMapping> featureAndPermissions = userAreaMapping.getUserRoleFeaturePermissionMappings();
			for (UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping : featureAndPermissions) {
				RoleFeaturePermissionScheme roleFeaturePermissionScheme = userRoleFeaturePermissionMapping.getRoleFeaturePermissionScheme();
				
				
				roles.add(roleFeaturePermissionScheme.getRole().getRoleName());
				roleIds.add(roleFeaturePermissionScheme.getRole().getRoleId());
				
				//adding authorities using syntax : (Role:Feature,Permission)
				grantedAuthorities.add(new SimpleGrantedAuthority(
						roleFeaturePermissionScheme.getRole().getRoleName().concat(":").
						concat(roleFeaturePermissionScheme.getFeaturePermissionMapping().getFeature().getFeatureName())
						.concat(",")
						.concat
						(roleFeaturePermissionScheme.getFeaturePermissionMapping().getPermission().getPermissionName())));
			
				//adding authorities using syntax : (Feature,Permission)
				grantedAuthorities.add(new SimpleGrantedAuthority(
						roleFeaturePermissionScheme.getFeaturePermissionMapping().getFeature().getFeatureName()
						.concat(",")
						.concat
						(roleFeaturePermissionScheme.getFeaturePermissionMapping().getPermission().getPermissionName())));
			
			
			}
		}
		return new UserModel(user.getUserName(), user.getPassword(), user.isEnabled(), !user.isExpired(), !user.isCredentialexpired(), !user.isLocked(), grantedAuthorities, user.getUserId(), areaMappings,roles,user.getUserId(),roleIds,user.getUuId(),user.getPassword());
	}

}
