package org.sdrc.bbbp.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.Role;
import org.sdrc.bbbp.domain.RoleFeaturePermissionScheme;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.domain.UserAreaMapping;
import org.sdrc.bbbp.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.bbbp.models.AreaModel;
import org.sdrc.bbbp.models.ChangePasswordModel;
import org.sdrc.bbbp.models.NewUserModel;
import org.sdrc.bbbp.models.ResetPasswordModel;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.RoleFeaturePermissionRepository;
import org.sdrc.bbbp.repository.RoleRepository;
import org.sdrc.bbbp.repository.UserAreaMappingRepository;
import org.sdrc.bbbp.repository.UserRepository;
import org.sdrc.bbbp.repository.UserRoleFeaturePermissionMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

/**
 * @author Subham Ashish (subham@sdrc.co.in)
 *
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {
	

	@Autowired
	private UserRoleFeaturePermissionMappingRepository userRoleFeaturePermissionMappingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAreaMappingRepository userAreaMappingRepository;

	@Autowired
	private RoleFeaturePermissionRepository roleFeaturePermissionRepository;
	
	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	@Transactional
	public boolean addNewUser(NewUserModel newUserModel) {

		Role role = new Role(newUserModel.getRoleId());

		
		Area area = areaRepository.findByAreaId(newUserModel.getAreaId());
		
		Area parentArea = areaRepository.findByParentAreaId(area.getParentAreaId()).get(0);
		
		User user = new User();
		User findUser = new User();
		
		switch(newUserModel.getRoleId()){
		
		case 1 : 
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
		
				user.setGenpassword(area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));
		
				//check generated user name is present in db or not
				
				findUser = userRepository.findByUserName(user.getUserName());
				
				if(findUser != null)
					return false;
				else
					user = userRepository.save(user);
			
				break;
		
		case 2:
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
	
				user.setGenpassword(area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));
	
				//check generated user name is present in db or not
				findUser = userRepository.findByUserName(user.getUserName());
				
				if(findUser!=null)
					return false;
					
				else
					user = userRepository.save(user);
			
				break;
				
		case 3 : 
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()), area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(area.getAreaName()).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));
	
				user.setGenpassword(area.getAreaName().toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));
	
				//check generated user name is present in db or not
				findUser = userRepository.findByUserName(user.getUserName());
				
				if(findUser!=null)
					return false;
				
				else
					user = userRepository.save(user);
			
				break;
				
		default:
			break;		
		
		
		}
		
		
		if (user != null) {
			
			Timestamp createdDate =  new Timestamp(new java.util.Date().getTime());
			
			UserAreaMapping UserAreaMapping = new UserAreaMapping();
			UserAreaMapping.setUser(user);
			UserAreaMapping.setArea(area);
			UserAreaMapping.setCreatedDate(createdDate);
			UserAreaMapping saveUserAreaMapping = userAreaMappingRepository.save(UserAreaMapping);

			if (saveUserAreaMapping != null) {

				// find feature-permission for that role
				List<RoleFeaturePermissionScheme> roleFeaturePermissionScheme = roleFeaturePermissionRepository.findByRole(role);
				
				List<UserRoleFeaturePermissionMapping> userRoleFeaturePermissionMappingList = new ArrayList<UserRoleFeaturePermissionMapping>();
				if (!roleFeaturePermissionScheme.isEmpty()) {
					
				for(RoleFeaturePermissionScheme rfps : roleFeaturePermissionScheme){
					
					UserRoleFeaturePermissionMapping UserRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();

					UserRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(rfps);
					UserRoleFeaturePermissionMapping.setUserAreaMapping(saveUserAreaMapping);
					
					userRoleFeaturePermissionMappingList.add(UserRoleFeaturePermissionMapping);

				}
				
				List<UserRoleFeaturePermissionMapping> saveUserRoleFeaturePermissionMapping = userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMappingList);
				
				if(!saveUserRoleFeaturePermissionMapping.isEmpty()){
					return true;
				}else
					throw new RuntimeException();
				
				}else
					throw new RuntimeException();
			}else
				throw new RuntimeException();

		}else
			throw new RuntimeException();

	}

	@Override
	public List<Role> displayAllRoles() {
		
		List<Role> roleList = roleRepository.findByOrderByRoleIdAsc();
		
		//IGNORING such list whose role is ADMIN
		
		List<Role> role = roleList.stream().filter(roleName->!"ADMIN".equals(roleName.getRoleName())).collect(Collectors.toList());
				
		return role;
		
		
	}

	@Override
	public Map<String, List<AreaModel>> getAllAreaList() {
		

		
		List<Area> areas = areaRepository.findByOrderByAreaName();
		
		List<AreaModel> areaModelList = new ArrayList<>();
		
		Map<String,List<AreaModel>> areaMap = new LinkedHashMap<>();
		
		
		//setting areas is area-model list
		for(Area area : areas){
			
			AreaModel areaModel = new AreaModel();
			
			areaModel.setAreaCode(area.getAreaCode());
			areaModel.setAreaId(area.getAreaId());
			areaModel.setAreaLevel(area.getLevel().getAreaLevelName());
			areaModel.setAreaName(area.getAreaName());
			areaModel.setCreatedDate(area.getCreatedDate());
			areaModel.setLive(area.isLive());
			areaModel.setParentAreaId(area.getParentAreaId());
			areaModel.setShortName(area.getShortName());
			areaModel.setUpdatedDate(area.getUpdatedDate());
				
			areaModelList.add(areaModel);
		}
		
		//making levelName as a key 
		for(AreaModel areaModel : areaModelList){
			
			
			if(areaMap.containsKey(areaModel.getAreaLevel())){
				
				areaMap.get(areaModel.getAreaLevel()).add(areaModel);
				
			}else{
				
				areaModelList = new ArrayList<>();
				areaModelList.add(areaModel);
				areaMap.put(areaModel.getAreaLevel(), areaModelList);
				
			}
			
			
		}
		return areaMap;
	
	}

	@Override
	public ResponseEntity<Boolean> resetPassword(ResetPasswordModel resetPasswordModel) {
		
		User user = userRepository.findByUserId(resetPasswordModel.getUserId());
		
		user.setPassword(messageDigestPasswordEncoder.encodePassword(user.getUserName(), resetPasswordModel.getNewPassword()));
		
		User saveUser = userRepository.save(user);
		
		if(saveUser!=null){
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false,HttpStatus.CONFLICT);
	}

	@Override
	public Map<String, List<User>> getAllUser() {
		
		//making area name as a key
		List<UserRoleFeaturePermissionMapping> userRoleFeaturePermissionMappingList = userRoleFeaturePermissionMappingRepository.findAll();
		
		//IGNORING such list whose role is ADMIN
		List<UserRoleFeaturePermissionMapping> userRoleFeaturePermissionMappingFilteredList = userRoleFeaturePermissionMappingList.stream().
				filter(roleName->!"ADMIN".equals(roleName.getUserAreaMapping().
						getUserRoleFeaturePermissionMappings().get(0).getRoleFeaturePermissionScheme().getRole().getRoleName()))
				.collect(Collectors.toList());
		
		//user_areaName
		List<String> userAreaList = new ArrayList<>();
		
		Map<String, List<User>> userMap = new LinkedHashMap<String, List<User>>();
		
		List<User> userList = new ArrayList<User>();
		
		for(UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping : userRoleFeaturePermissionMappingFilteredList){
			
			if(userMap.containsKey(userRoleFeaturePermissionMapping.getUserAreaMapping()
					.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getArea().getAreaCode())){
				
				if(userAreaList.isEmpty() || userAreaList.stream().noneMatch(v->v.equals(
						userRoleFeaturePermissionMapping.getUserAreaMapping()
					.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getUser().getUserName()))){
					
				/*adding username(if same exist do not add)-- to avoid duplicacy(as one user can have 
					multiple feature-permission of same area) */
				userAreaList.add(userRoleFeaturePermissionMapping.getUserAreaMapping()
					.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getUser().getUserName());
				
				userMap.get(userRoleFeaturePermissionMapping.getUserAreaMapping()
						.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getArea().getAreaCode()).
				add(userRoleFeaturePermissionMapping.getUserAreaMapping().getUser());
				
				}
				
				
			}else{
				
				userList = new ArrayList<User>();
				
				if(userAreaList.isEmpty() || userAreaList.stream().noneMatch(v->v.equals(
						userRoleFeaturePermissionMapping.getUserAreaMapping()
					.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getUser().getUserName()))){
					
					userAreaList.add(userRoleFeaturePermissionMapping.getUserAreaMapping()
							.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getUser().getUserName());
					
				userList.add(userRoleFeaturePermissionMapping.getUserAreaMapping().getUser());
				
				userMap.put(userRoleFeaturePermissionMapping.getUserAreaMapping()
						.getUserRoleFeaturePermissionMappings().get(0).getUserAreaMapping().getArea().getAreaCode(), userList);
				}
				
			}
			
		}
		
		return userMap;
	
		
	}

	@Override
	public ResponseEntity<String> changePassoword(ChangePasswordModel changePasswordModel) {

		//using it to parse string as json
		Gson gson = new Gson();
		
		// check newpassword is same as confirm password
		if (changePasswordModel.getNewPassword().equals(changePasswordModel.getConfirmPassword())) {

			User user = userRepository.findByUserName(changePasswordModel.getUserName());

			// check user has entered correct old password or not
			if (user.getPassword().equals(messageDigestPasswordEncoder.encodePassword(user.getUserName(),changePasswordModel.getOldPassword()))) {

				// check new password is same as db password or not if same than return
				if (!user.getPassword().equals(messageDigestPasswordEncoder.encodePassword(user.getUserName(),
						changePasswordModel.getNewPassword()))) {

					user.setPassword(messageDigestPasswordEncoder.encodePassword(user.getUserName(),changePasswordModel.getNewPassword()));

					User saveUser = userRepository.save(user);

					if (saveUser != null) {

						return new ResponseEntity<>(gson.toJson(messageSource.getMessage("password.update.success", null,null)), HttpStatus.OK);

					} else {

						return new ResponseEntity<>(gson.toJson(messageSource.getMessage("password.update.failure", null,null)), HttpStatus.CONFLICT);
					}

				} else {
					return new ResponseEntity<>(gson.toJson(messageSource.getMessage("new.password.previous.password", null,null)), HttpStatus.FORBIDDEN);
				}

			} else {

				return new ResponseEntity<>(gson.toJson(messageSource.getMessage("password.not.matching", null,null)), HttpStatus.FORBIDDEN);
			}

		} else
			return new ResponseEntity<>(gson.toJson(messageSource.getMessage("new.password.confirm.password.not.matching", null,null)), HttpStatus.FORBIDDEN);
		
	}
	

}
