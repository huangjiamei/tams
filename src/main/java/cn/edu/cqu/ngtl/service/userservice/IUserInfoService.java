package cn.edu.cqu.ngtl.service.userservice;

import cn.edu.cqu.ngtl.bo.User;
import org.kuali.rice.krad.UserSession;

/**
 * 用户信息服务接口
 *
 * @author Bill
 */
public interface IUserInfoService {
    /**
     * [hasPermission 目前权限管理所使用的权限判定方法]
     *
     * @param user     [bo-用户]
     * @param permName [权限名]
     * @return [true, false]
     */
    public boolean hasPermission(User user, String permName);

    /**
     * [getUserByUserSession 根据useSession获取bo-user，并获取对应的权限列表]
     *
     * @param userSession [description]
     * @return [description]
     */
    public User getUserByUserSession(UserSession userSession);

    //是否是学院工作人员
    boolean isCollegeStaff(String principalId);

    //是否是教务处工作人员
    boolean isAcademicAffairsStaff(String principalId);

    //是否是系统管理员
    boolean isSysAdmin(String principalId);


    //是否是学生
    boolean isStudent(String principalId);

    //是否是老师
    boolean isInstructor(String principalId);

    boolean isCourseManager(String principalId);
}
