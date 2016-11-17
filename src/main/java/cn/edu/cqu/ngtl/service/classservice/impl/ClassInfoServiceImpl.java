package cn.edu.cqu.ngtl.service.classservice.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTeachCalendarDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTStudentDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTClassDao classDao;

    @Autowired
    private UTStudentDao studentDao;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    @Autowired
    private TAMSTeachCalendarDao teachCalendarDao;

    @Autowired
    private TAMSTaDao taDao;

    @Override
    public List<UTClassInformation> getAllClassesMappedByDepartment() {

        /** Access DataBase */
        List<UTClassInformation> classInformations = classInfoDao.getAllCurrentClassInformation();
//        for (UTClassInformation perInformation : classInformations) {

            /** Access DataBase */
            /** 等待最新的性能解决方案    **/
            //CMProgramCourse programCourse = programCourseDao.selectByCourseId(clazz.getCourseId());

//        }

        return classInformations;
    }

    @Override
    public UTClass getClassInfoById(Integer classId) {

        UTClass clazz = classDao.selectByClassId(classId);

        return clazz;
    }

    @Override
    public UTStudent getStudentInfoById(String stuId) {
        return studentDao.getUTStudentById(stuId);
    }

    @Override
    public List<UTClassInformation> getAllClassesFilterByUid(String uId) {

        //// FIXME: 16-11-4 因为测试加上了非 '!'，正式使用需要去掉
        if(uId.equalsIgnoreCase("admin")){
            return this.getAllClassesMappedByDepartment();   //FIXME 测试代码。需要删除
        }
        if(userInfoService.isSysAdmin(uId))
            return this.getAllClassesMappedByDepartment();
        else if (userInfoService.isInstructor(uId)) {
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);

            return classInfoDao.selectBatchByIds(classIds);
        }
        else if (userInfoService.isStudent(uId)) {
            List<Object> classIds = taDao.selectClassIdsByStudentId(uId);

            return classInfoDao.selectBatchByIds(classIds);
        }
        return null;
    }

    @Override
    public List<UTClassInformation> getAllClassesFilterByUidAndCondition(String uId, Map<String, String> conditions) {
        if(!userInfoService.isSysAdmin(uId)) {
            /** Access DataBase */
            List<UTClassInformation> classInformations = classInfoDao.selectByConditions(conditions);
            return classInformations;
        }
        else if (userInfoService.isInstructor(uId)) {
            //FIXME 按照教师ID去查课程信息
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
//            return classInfoDao.selectBatchByIds(classIds);
        }
        return null;
    }

    @Override
    public List<TAMSTeachCalendar> getAllTaTeachCalendarFilterByUidAndClassId(String uId, String classId) {
        if(userInfoService.isSysAdmin(uId)) {
            /** Access DataBase */
            List<TAMSTeachCalendar> teachCalendar = teachCalendarDao.selectAllByClassId(classId);
            return teachCalendar;
        }
        else if (userInfoService.isInstructor(uId)) {
            return teachCalendarDao.selectAllByClassId(classId);
        }
        return null;
    }

    @Override
    public boolean instructorAddTeachCalendar(String uId, String classId, TAMSTeachCalendar teachCalendar) {
        //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
        if(!userInfoService.isSysAdmin(uId)) {
            return true;
        }
        else if (!userInfoService.isInstructor(uId)) { //// FIXME: 16-11-17 因为测试加上了非 '!'，正式使用需要去掉
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(uId);
            List<String> classIdStrings = new ArrayList();
            for(Object obj : classIds)
                classIdStrings.add(obj.toString());
            if(classIdStrings.contains(classId)) {
                teachCalendar.setClassId(classId);
                return teachCalendarDao.insertByEntity(teachCalendar);
            }
            else
                return false;
        }
        return false;
    }
}
