package org.sdrc.bbbp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.AreaLevel;
import org.sdrc.bbbp.domain.Feature;
import org.sdrc.bbbp.domain.FeaturePermissionMapping;
import org.sdrc.bbbp.domain.Permission;
import org.sdrc.bbbp.domain.Role;
import org.sdrc.bbbp.domain.RoleFeaturePermissionScheme;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.domain.UserAreaMapping;
import org.sdrc.bbbp.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.FeaturePermissionMappingRepository;
import org.sdrc.bbbp.repository.FeatureRepository;
import org.sdrc.bbbp.repository.PermissionRepository;
import org.sdrc.bbbp.repository.RoleFeaturePermissionRepository;
import org.sdrc.bbbp.repository.RoleRepository;
import org.sdrc.bbbp.repository.UserAreaMappingRepository;
import org.sdrc.bbbp.repository.UserRepository;
import org.sdrc.bbbp.repository.UserRoleFeaturePermissionMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAreaMappingRepository userAreaMappingRepository;

	@Autowired
	private RoleFeaturePermissionRepository roleFeaturePermissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	FeatureRepository featureRepository;

	@Autowired
	UserRoleFeaturePermissionMappingRepository userRoleFeaturePermissionMappingRepository;

	@Autowired
	FeaturePermissionMappingRepository featurePermissionMappingRepository;

	@Override
	@Transactional
	public boolean createRoleFeaturePermissionScheme() {

		Role roleNational = new Role();
		roleNational.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleNational.setDescription("NATIONAL LEVEL USER");
		roleNational.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleNational.setRoleCode("NATIONAL");
		roleNational.setRoleName("NATIONAL");

		roleNational = roleRepository.save(roleNational);

		Role roleState = new Role();
		roleState.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleState.setDescription("STATE LEVEL USER");
		roleState.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleState.setRoleCode("STATE");
		roleState.setRoleName("STATE");
		roleState = roleRepository.save(roleState);

		Role roleDistrict = new Role();
		roleDistrict.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleDistrict.setDescription("DISTRICT LEVEL USER");
		roleDistrict.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleDistrict.setRoleCode("DISTRICT");
		roleDistrict.setRoleName("DISTRICT");
		roleDistrict = roleRepository.save(roleDistrict);

		Role roleAdmin = new Role();
		roleAdmin.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleAdmin.setDescription("ADMIN USER");
		roleAdmin.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
		roleAdmin.setRoleCode("ADMIN");
		roleAdmin.setRoleName("ADMIN");
		roleAdmin = roleRepository.save(roleAdmin);

		Feature FEATURE_DATA_ENTRY = new Feature();
		FEATURE_DATA_ENTRY.setFeatureCode("FEATURE-001");
		FEATURE_DATA_ENTRY.setFeatureName("data_entry");
		FEATURE_DATA_ENTRY.setDescription("This feature allows data entry");
		FEATURE_DATA_ENTRY.setUpdatedBy("Azar");
		FEATURE_DATA_ENTRY.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));

		FEATURE_DATA_ENTRY = featureRepository.save(FEATURE_DATA_ENTRY);

		Feature FEATURE_USER_MGMT = new Feature();
		FEATURE_USER_MGMT.setFeatureCode("FEATURE-002");
		FEATURE_USER_MGMT.setFeatureName("user_mgmt");
		FEATURE_USER_MGMT.setDescription("This feature allows user Management");
		FEATURE_USER_MGMT.setUpdatedBy("Azhar");
		FEATURE_USER_MGMT.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));

		FEATURE_USER_MGMT = featureRepository.save(FEATURE_USER_MGMT);

		Permission VIEW_PERMISSION = new Permission();
		VIEW_PERMISSION.setDescription("");
		VIEW_PERMISSION.setPermissionCode("PERMISSION-001");
		VIEW_PERMISSION.setPermissionName("view");
		VIEW_PERMISSION.setUpdatedBy("Azar");
		VIEW_PERMISSION.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));

		VIEW_PERMISSION = permissionRepository.save(VIEW_PERMISSION);

		Permission EDIT_PERMISSION = new Permission();
		EDIT_PERMISSION.setDescription("");
		EDIT_PERMISSION.setPermissionCode("PERMISSION-001");
		EDIT_PERMISSION.setPermissionName("edit");
		EDIT_PERMISSION.setUpdatedBy("Azar");
		EDIT_PERMISSION.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));

		EDIT_PERMISSION = permissionRepository.save(EDIT_PERMISSION);

		FeaturePermissionMapping FEATURE_DATA_ENTRY_EDIT_PERMISSION = new FeaturePermissionMapping();
		FEATURE_DATA_ENTRY_EDIT_PERMISSION.setFeature(FEATURE_DATA_ENTRY);
		FEATURE_DATA_ENTRY_EDIT_PERMISSION.setPermission(EDIT_PERMISSION);
		FEATURE_DATA_ENTRY_EDIT_PERMISSION = featurePermissionMappingRepository.save(FEATURE_DATA_ENTRY_EDIT_PERMISSION);

		FeaturePermissionMapping FEATURE_USER_MGMT_EDIT_PERMISSION = new FeaturePermissionMapping();
		FEATURE_USER_MGMT_EDIT_PERMISSION.setFeature(FEATURE_USER_MGMT);
		FEATURE_USER_MGMT_EDIT_PERMISSION.setPermission(EDIT_PERMISSION);
		FEATURE_USER_MGMT_EDIT_PERMISSION = featurePermissionMappingRepository.save(FEATURE_USER_MGMT_EDIT_PERMISSION);

		RoleFeaturePermissionScheme ADMIN_FEATURE_USER_MGMT_EDIT_PERMISSION = new RoleFeaturePermissionScheme();
		ADMIN_FEATURE_USER_MGMT_EDIT_PERMISSION.setRole(roleAdmin);
		ADMIN_FEATURE_USER_MGMT_EDIT_PERMISSION.setFeaturePermissionMapping(FEATURE_USER_MGMT_EDIT_PERMISSION);
		ADMIN_FEATURE_USER_MGMT_EDIT_PERMISSION = roleFeaturePermissionRepository.save(ADMIN_FEATURE_USER_MGMT_EDIT_PERMISSION);

		RoleFeaturePermissionScheme NATIONAL_FEATURE_DATA_ENTRY_EDIT_PERMISSION = new RoleFeaturePermissionScheme();
		NATIONAL_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setRole(roleNational);
		NATIONAL_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setFeaturePermissionMapping(FEATURE_DATA_ENTRY_EDIT_PERMISSION);
		NATIONAL_FEATURE_DATA_ENTRY_EDIT_PERMISSION = roleFeaturePermissionRepository.save(NATIONAL_FEATURE_DATA_ENTRY_EDIT_PERMISSION);

		RoleFeaturePermissionScheme STATE_FEATURE_DATA_ENTRY_EDIT_PERMISSION = new RoleFeaturePermissionScheme();
		STATE_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setRole(roleState);
		STATE_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setFeaturePermissionMapping(FEATURE_DATA_ENTRY_EDIT_PERMISSION);
		STATE_FEATURE_DATA_ENTRY_EDIT_PERMISSION = roleFeaturePermissionRepository.save(STATE_FEATURE_DATA_ENTRY_EDIT_PERMISSION);

		RoleFeaturePermissionScheme DISTRICT_FEATURE_DATA_ENTRY_EDIT_PERMISSION = new RoleFeaturePermissionScheme();
		DISTRICT_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setRole(roleDistrict);
		DISTRICT_FEATURE_DATA_ENTRY_EDIT_PERMISSION.setFeaturePermissionMapping(FEATURE_DATA_ENTRY_EDIT_PERMISSION);
		DISTRICT_FEATURE_DATA_ENTRY_EDIT_PERMISSION = roleFeaturePermissionRepository.save(DISTRICT_FEATURE_DATA_ENTRY_EDIT_PERMISSION);

		return true;

	}

	@Override
	@Transactional
	public boolean createUsers() {

		File file = new File("C:\\Users\\SDRC_DEV\\Desktop\\project\\BBBP\\Beti_Bachao_Beti_Padhao_Area_r1xmsx.xlsx");

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int row = 2; row <= sheet.getLastRowNum(); row++) {
			XSSFRow xssfRow = sheet.getRow(row);
			System.out.println("Row :" + row);
			// Starting cells

			Iterator<Cell> cellIterator = xssfRow.cellIterator();
			int cols = 0;

			String areaCode = null;
			String parentAreaCode = null;
			String areaName = null;
			Integer level = null;
			Integer areaId = null;
			Cell cell = null;
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				switch (cols) {
				case 0:
					areaId = (int) cell.getNumericCellValue();
					break;
				case 1:
					areaCode = cell.getStringCellValue().trim();
					break;
				case 2:
					areaName = cell.getStringCellValue().trim();
					break;
				case 3:
					parentAreaCode = cell.getStringCellValue().trim();

					break;
				case 4:
					level = (int) cell.getNumericCellValue();
					break;

				}
				cols++;
			}
			// First Row Has been read

			Area parentArea = areaRepository.findByAreaCode(parentAreaCode);

			Area area = new Area();
			area.setAreaId(areaId);
			area.setAreaCode(areaCode);
			area.setParentAreaId(parentArea != null ? parentArea.getAreaId() : -1);
			area.setAreaName(areaName);
			area.setAreaCode(areaCode);
			area.setLevel((new AreaLevel(level)));
			area.setLive(true);
			area.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
			area.setShortName(areaName);

			area = areaRepository.save(area);

			// Create national state and district level users

			switch (level) {
			case 1: {

				Role role = roleRepository.findByRoleName("NATIONAL");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("NATIONAL LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("NATIONAL");
					role.setRoleName("NATIONAL");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

				}

			}
				break;
			case 2: {

				Role role = roleRepository.findByRoleName("STATE");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("STATE LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("STATE");
					role.setRoleName("STATE");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

				}

			}
				break;
			case 3: {

				Role role = roleRepository.findByRoleName("DISTRICT");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("DISTRICT LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("DISTRICT");
					role.setRoleName("DISTRICT");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);
				}
			}
				break;

			default:
				break;
			}
		}

		// Finally Create an ADMIN USER and assign Admin RoleFeaturePermissionScheme to the user
		User user = new User();
		user.setCredentialexpired(false);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setName("bbbp_administrator");
		user.setPassword(user.generatedEncodedPassword("bbbp_administrator", "admin123#!"));
		user.setUserName("bbbp_administrator");

		user.setGenpassword("admin123#!");

		user = userRepository.save(user);

		UserAreaMapping userAreaMapping = new UserAreaMapping();
		userAreaMapping.setArea(areaRepository.findByAreaCode("IND"));
		userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		userAreaMapping.setIsActive(true);
		userAreaMapping.setUser(user);

		userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

		List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(roleRepository.findByRoleName("ADMIN"));

		for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

			UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
			userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
			userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

			userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);
		}

		return true;
	}

	@Override
	@Transactional
	public boolean generateUserRoleFeaturePermissionMapping() {

		List<User> users = userRepository.findAll();

		List<RoleFeaturePermissionScheme> rolesAndFeatures = roleFeaturePermissionRepository.findAll();
		for (User user : users) {
			UserAreaMapping userAreaMapping = user.getAreas().get(0);

			if (user.getAreas().get(0).getArea().getLevel().getAreaLevelName().equals("NATIONAL")) {
				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : rolesAndFeatures) {
					if (roleFeaturePermissionScheme.getRole().getRoleCode().equals("NATIONAL")) {
						UserRoleFeaturePermissionMapping userRole = new UserRoleFeaturePermissionMapping();
						userRole.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
						userRole.setUserAreaMapping(userAreaMapping);
						userRole.setUpdatedBy("Azar");
						userRole.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
						userRoleFeaturePermissionMappingRepository.save(userRole);
					}
				}
			} else if (user.getAreas().get(0).getArea().getLevel().getAreaLevelName().equals("STATE")) {
				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : rolesAndFeatures) {
					if (roleFeaturePermissionScheme.getRole().getRoleCode().equals("STATE")) {
						UserRoleFeaturePermissionMapping userRole = new UserRoleFeaturePermissionMapping();
						userRole.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
						userRole.setUserAreaMapping(userAreaMapping);
						userRole.setUpdatedBy("Azar");
						userRole.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
						userRoleFeaturePermissionMappingRepository.save(userRole);
					}
				}
			} else if (user.getAreas().get(0).getArea().getLevel().getAreaLevelName().equals("DISTRICT")) {
				// user.getAreas().get(0).getArea().getLevel() == 3
				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : rolesAndFeatures) {

					if (roleFeaturePermissionScheme.getRole().getRoleCode().equals("DISTRICT")) {
						UserRoleFeaturePermissionMapping userRole = new UserRoleFeaturePermissionMapping();
						userRole.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
						userRole.setUserAreaMapping(userAreaMapping);
						userRole.setUpdatedBy("Azar");
						userRole.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
						userRoleFeaturePermissionMappingRepository.save(userRole);
					}
				}
			}

		}
		return true;

	}

	@Override
	@Transactional
	public boolean createUsersFromAreaTable() {

		List<Area> areas = areaRepository.findAll();
		for (Area area : areas) {

			String areaName = area.getAreaName();
			Integer level = area.getLevel().getAreaLevelId();// area.getLevel()
			Area parentArea = areaRepository.findByParentAreaId(area.getParentAreaId()).get(0);
			// Create national state and district level users

			switch (level) {
			case 1: {

				Role role = roleRepository.findByRoleName("NATIONAL");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("NATIONAL LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("NATIONAL");
					role.setRoleName("NATIONAL");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

				}

			}
				break;
			case 2: {

				Role role = roleRepository.findByRoleName("STATE");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("STATE LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("STATE");
					role.setRoleName("STATE");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

				}

			}
				break;
			case 3: {

				Role role = roleRepository.findByRoleName("DISTRICT");
				if (role == null) {
					role = new Role();
					role.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setDescription("DISTRICT LEVEL USER");
					role.setLastUpdatedDate(new java.sql.Timestamp(new Date().getTime()));
					role.setRoleCode("DISTRICT");
					role.setRoleName("DISTRICT");

					role = roleRepository.save(role);
				}

				User user = new User();
				user.setCredentialexpired(false);
				user.setEnabled(true);
				user.setExpired(false);
				user.setLocked(false);
				user.setName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				user.setUserName("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()));

				user.setGenpassword(areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"));

				user = userRepository.save(user);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(area);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);
				}
			}
				break;

			default:
				break;
			}
		}
		// Finally Create an ADMIN USER and assign Admin RoleFeaturePermissionScheme to the user
		User user = new User();
		user.setCredentialexpired(false);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setName("bbbp_administrator");
		user.setPassword(user.generatedEncodedPassword("bbbp_administrator", "admin123#!"));
		user.setUserName("bbbp_administrator");

		user.setGenpassword("admin123#!");

		user = userRepository.save(user);

		UserAreaMapping userAreaMapping = new UserAreaMapping();
		userAreaMapping.setArea(areaRepository.findByAreaCode("IND"));
		userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		userAreaMapping.setIsActive(true);
		userAreaMapping.setUser(user);

		userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

		List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(roleRepository.findByRoleName("ADMIN"));

		for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

			UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
			userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
			userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

			userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);
		}
		return true;
	}

	 @Override
	@Transactional
	public boolean resetPassword() {

		List<User> users = userRepository.findAll();

		for (User user : users) {
			Role role = user.getAreas().get(0).getUserRoleFeaturePermissionMappings().get(0).getRoleFeaturePermissionScheme().getRole();
			String areaName = user.getAreas().get(0).getArea().getAreaName();
			switch (role.getRoleName()) {
			case "NATIONAL":
				//System.out.println("UserName::"+user.getUserName() +" , Password::" +(("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"))));
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				break;

			case "STATE":
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				break;

			case "DISTRICT":
				Area parentArea = areaRepository.findByAreaId(user.getAreas().get(0).getArea().getParentAreaId());
				user.setPassword(user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")));
				break;
			}
		}
		
		return true;

	}
	 
	 @Override
	 @Transactional
	 public boolean checkPassword() {
		 List<User> users = userRepository.findAll();

			for (User user : users) {
				Role role = user.getAreas().get(0).getUserRoleFeaturePermissionMappings().get(0).getRoleFeaturePermissionScheme().getRole();
				String areaName = user.getAreas().get(0).getArea().getAreaName();
				switch (role.getRoleName()) {
				case "NATIONAL":
				case "STATE":
					//System.out.println("UserName::"+user.getUserName() +" , Password::" +(("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123"))));
					if(!user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and"), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")).equals(user.getPassword())) {
						System.out.println("UserName::"+user.getUserName());
						throw new IllegalStateException("Password mismatch for state");
					}
					
					break;
				case "DISTRICT":
					Area parentArea = areaRepository.findByAreaId(user.getAreas().get(0).getArea().getParentAreaId());
					
					if(!user.generatedEncodedPassword("bbbp_".concat(areaName).replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("_").concat(parentArea.getAreaName().substring(0, 2).toLowerCase()), areaName.toLowerCase().replaceAll(" ", "_").toLowerCase().replaceAll("&", "and").concat("123")).equals(user.getPassword())) {
						System.out.println("UserName::"+user.getUserName());
						throw new IllegalStateException("Password mismatch for district");
					}
					break;
				}
			}
			return true;
	 }
}
