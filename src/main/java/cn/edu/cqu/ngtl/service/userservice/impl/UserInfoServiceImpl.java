package cn.edu.cqu.ngtl.service.userservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.ut.impl.UTInstructorDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTStudentDaoJpa;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_MBR_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ROLE_PERM_T_DaoJpa;
import cn.edu.cqu.ngtl.dataobject.ut.UTInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_MBR_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_PERM_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ROLE_T;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
	/**
	 * 是否二级单位管理员
	 * @param principalId
	 * @return
	 */
	@Override
	public boolean isCollegeStaff(String principalId) {
		// TODO Auto-generated method stub
		List <String>roleIds = new ArrayList<String>();
		roleIds.add("10025");
		boolean isCollegeStaff= KimApiServiceLocator.getRoleService().principalHasRole(principalId, roleIds, Collections.<String, String>emptyMap());
	    return isCollegeStaff;
	}


	/**
	 * 是否教务处管理员
	 * @param principalId
	 * @return
	 */
	@Override
	public boolean isAcademicAffairsStaff(String principalId) {
		// TODO Auto-generated method stub
		List <String>roleIds = new ArrayList<String>();
		roleIds.add("10026");
		boolean isAcademicAffairsStaff= KimApiServiceLocator.getRoleService().principalHasRole(principalId, roleIds, Collections.<String, String>emptyMap());
	    return isAcademicAffairsStaff;
	}

	/**
	 * 是否系统管理员
	 * @param principalId
	 * @return
	 */
	@Override
	public boolean isSysAdmin(String principalId) {
		// TODO Auto-generated method stub
		List <String>roleIds = new ArrayList<String>();
		roleIds.add("10028");
		boolean isSysAdmin= KimApiServiceLocator.getRoleService().principalHasRole(principalId, roleIds, Collections.<String, String>emptyMap());
	    return isSysAdmin;
	}


	/**
	 * 是否是学生
	 * @param principalId
	 * @return
	 */
	@Override
	public boolean isStudent(String principalId) {
		// TODO Auto-generated method stub
		List <String>roleIds = new ArrayList<String>();
		roleIds.add("10022");
		boolean isStudent= KimApiServiceLocator.getRoleService().principalHasRole(principalId, roleIds, Collections.<String, String>emptyMap());
	    return isStudent;
	}

	/**
	 * 是否是教师
	 * @param principalId
	 * @return
	 */
	@Override
	public boolean isInstructor(String principalId) {
		// TODO Auto-generated method stub
		List <String>roleIds = new ArrayList<String>();
		roleIds.add("10043");
		boolean isStudent= KimApiServiceLocator.getRoleService().principalHasRole(principalId, roleIds, Collections.<String, String>emptyMap());
		return isStudent;
	}

	@Override
	public User getUserByUserSession(UserSession userSession) {
		// TODO Auto-generated method stub
		User user = new User();
		String principalId = userSession.getPrincipalId();
		user.setCode(principalId);
		
		List<KRIM_PERM_T> krimPermTs = new ArrayList<>();
		List<KRIM_ROLE_MBR_T> krimRoleMbrTs = new KRIM_ROLE_MBR_T_DaoJpa().getKrimEntityEntTypTsByMbrId(principalId);
		for(KRIM_ROLE_MBR_T krimRoleMbrT:krimRoleMbrTs){
			if(krimRoleMbrT.getActvToDt()==null || krimRoleMbrT.getActvToDt().after(new Date(System.currentTimeMillis()))){
				KRIM_ROLE_T krimRoleT = krimRoleMbrT.getKrimRoleT();
				if(!krimRoleT.getActive().equals("Y"))
					continue;
				List<KRIM_ROLE_PERM_T> rolePermTs =
						new KRIM_ROLE_PERM_T_DaoJpa().getKrimRolePermTsByRole(krimRoleT);
				for(KRIM_ROLE_PERM_T rolePermT:rolePermTs){
					if(rolePermT.getActive()){
						if(rolePermT.getKrimPermT().getActive().equals("Y") && !krimPermTs.contains(rolePermT.getKrimPermT())){
							krimPermTs.add(rolePermT.getKrimPermT());
						}
					}
				}
			}
		}
		user.setKrimPermTs(krimPermTs);
		UTStudent utStudent = new UTStudentDaoJpa().getUTStudentById(principalId);
		if(utStudent!=null){
			user.setDepartmentId(utStudent.getDepartment().getId());
			user.setDepartment(utStudent.getDepartment().getName());
			user.setName(utStudent.getName());
			user.setType("student");
			user.setTag(utStudent.getId());
		}else{
			UTInstructor utInstructor =
					new UTInstructorDaoJpa().getInstructorByIdWithoutCache(principalId);
			if(utInstructor !=null){
				user.setDepartmentId(utInstructor.getDepartment().getId());
				user.setDepartment(utInstructor.getDepartment().getName());
				user.setName(utInstructor.getName());
				user.setType("instructor");
				user.setTag(utInstructor.getCode());
			}else{
				user.setDepartmentId(-1);
				user.setDepartment("无用户基础信息");
				user.setName("无用户基础信息");
				user.setTag("无用户基础信息");
				user.setType("none");
			}
		}
		return user;
	}

	@Override
	public boolean hasPermission(User user,String permName) {
		// TODO Auto-generated method stub
		for(KRIM_PERM_T krimPermT:user.getKrimPermTs()){
			if(krimPermT.getName().equals(permName))
				return true;
		}
		return false;
	}
}
