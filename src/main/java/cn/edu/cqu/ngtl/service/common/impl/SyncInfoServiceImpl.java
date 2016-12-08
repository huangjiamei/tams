package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.service.common.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by awake on 2016/12/7.
 */
@Service
public class SyncInfoServiceImpl implements SyncInfoService {
    @Autowired
    private UTDepartmentDao utDepartmentDao;

    @Autowired
    private UTCourseDao utCourseDao;

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private UTInstructorDao utInstructorDao;

    @Autowired
    private UTCourseOfferingDao utCourseOfferingDao;

    @Autowired
    private UTOfferingConfigDao utOfferingConfigDao;

    @Autowired
    private UTConfigDetailDao utConfigDetailDao;

    @Autowired
    private UTClassInstructorDao utClassInstructorDao;

    @Autowired
    private UTClassDao utClassDao;


    @Override
    public Connection getConnection(String hostType, String hostIp, String hostPort, String dbName, String dbUserName,
                                    String dbPassWd) throws SQLException, ClassNotFoundException {
        String url = "";
        Connection con = null;
        switch (hostType) {
            case "0":
                Class.forName("oracle.jdbc.driver.OracleDriver");
                url +=  "jdbc:oracle:thin:@" + hostIp+":"+hostPort+"/"+dbName;
                break;
            case "1":
                //暂未测试
                Class.forName("com.mysql.jdbc.Driver");
                url += "jdbc:mysql://" + hostIp+":"+hostPort+"/"+dbName;
                break;
            case "2":
                //暂未测试
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                url += "jdbc:sqlserver://"+ hostIp+":"+hostPort+";DatabaseName="+dbName;
                break;
        }
        con = DriverManager.getConnection(url,dbUserName, dbPassWd);
//        this.syncCourseInfo(con);
        this.syncClassInfo(con);
        System.out.println("建立了连接");
        return con;
    }


    @Override
    public void closeConnection(Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        if(connection!=null)
            connection.close();
    }


    /**
     * 同步课程信息（相关表UNITIME_COURSE）
     * @param connection
     * @throws SQLException
     */
    @Override
    public void syncCourseInfo(Connection connection) throws SQLException {
        List<UTDepartment> allDepartment = utDepartmentDao.getAllUTDepartments();
        Map departmentMap = new HashMap<>();
        for(UTDepartment utDepartment : allDepartment){
            departmentMap.put(utDepartment.getDeptcode(),utDepartment.getId());
        }

        String queryCourse = "SELECT * FROM LESSONINFO";
        int i = 0;
        PreparedStatement pre = connection.prepareStatement(queryCourse);
        try{
            pre.setQueryTimeout(10000);
            ResultSet res =  pre.executeQuery();
            while(res.next()){
                String coureseName = res.getString("KCMC");
                String courseCode = res.getString("KCDM");
                String departmentName = res.getString("CDDW").substring(0,2);
                Integer deptId = (Integer)departmentMap.get(departmentName);
                String credit = res.getString("XF");
                String kcId = res.getString("KCID");
                UTCourse utCourse = new UTCourse();
                utCourse.setDepartmentId(deptId);
                utCourse.setCodeR(courseCode);
                utCourse.setName(coureseName);
                utCourse.setCredit(credit);
                utCourse.setId(Integer.parseInt("2016"+kcId));
                utCourseDao.InsertOneByEntity(utCourse);
                System.out.println(i++);
            }
        }finally{
            if(pre!=null)
                pre.close();
        }
    }

    /**
     * 同步教学班信息
     * @param connection
     * @throws SQLException
     */
    public void syncClassInfo(Connection connection) throws  SQLException{
        List<UTClass> utClasses = new ArrayList<>();
        List<UTCourseOffering> utCourseOfferings = new ArrayList<>();
        List<UTCourseOfferingConfig> utCourseOfferingConfigs = new ArrayList<>();
        List<UTConfigDetail> utConfigDetails = new ArrayList<>();
        List<UTClassInstructor> utClassInstructors = new ArrayList<>();
        Map courseMap = new HashMap<>();
        Map classInstructorMap = new HashMap<>();
        List<String> classNbrs = new ArrayList<>(); //判断是否有重复的教学班号，如果有，说明一个教学班有多个教师一起上

        List<UTCourse> allCourse = utCourseDao.selectAllMappedByDepartment();
        UTSession curSession = utSessionDao.getCurrentSession();
        List<UTInstructor> utInstructorList = utInstructorDao.getAllInstructors();

        String sessionPrefix = curSession.getYear()+curSession.getTerm();

        for(UTInstructor utInstructor:utInstructorList){
            classInstructorMap.put(utInstructor.getIdNumber(),utInstructor.getId());
        }

        for(UTCourse course : allCourse){
            courseMap.put(course.getCodeR(),course.getId());
        }
        String queryCourse = "SELECT * FROM JSKB t WHERE t.SFRZH IS NOT NULL ";
        int i = 0;
        PreparedStatement pre = connection.prepareStatement(queryCourse);
        try{
            pre.setQueryTimeout(10000);
            ResultSet res =  pre.executeQuery();
            while(res.next()){
                String courseCode = res.getString("KCMD");
                String classNbr = res.getString("JXBH");
                String editClassNbr = classNbr.replace("-","");
                String auId = res.getString("SFRZH");
                /**
                 * Class对象
                 */
                if(!classNbrs.contains(classNbr)) {  //重复的教学班代表该教学班有多个教师
                    classNbrs.add(classNbr);

                    UTClass utClass = new UTClass();
                    utClass.setClassNumber(classNbr);
                    utClass.setId(Integer.parseInt(sessionPrefix + editClassNbr));//所有的uniqueid都通用这个值，年份+教学班号，保证唯一不重复
                    utClass.setCourseOfferingId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utClasses.add(utClass);
                    /**
                     * CourseOffering对象
                     */
                    UTCourseOffering utCourseOffering = new UTCourseOffering();
                    utCourseOffering.setId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utCourseOffering.setCourseId((Integer) courseMap.get(courseCode));
                    utCourseOffering.setSessionId(curSession.getId());
                    utCourseOfferings.add(utCourseOffering);
                    /**
                     * offeringConfig对象
                     */
                    UTCourseOfferingConfig utCourseOfferingConfig = new UTCourseOfferingConfig();
                    utCourseOfferingConfig.setId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utCourseOfferingConfig.setCourseOfferingId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utCourseOfferingConfig.setConfigName("1");
                    utCourseOfferingConfigs.add(utCourseOfferingConfig);
                    /**
                     * configDetail对象
                     */
                    UTConfigDetail utConfigDetail = new UTConfigDetail();
                    utConfigDetail.setId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utConfigDetail.setConfigId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utConfigDetail.setKlassId(Integer.parseInt(sessionPrefix + editClassNbr));
                    utConfigDetails.add(utConfigDetail);
                }
                //教学班号和身份认证号的关系
//                classInstructorMap.put(classNbr,auId);

                UTClassInstructor utClassInstructor = new UTClassInstructor();
                utClassInstructor.setClassId(Integer.parseInt(sessionPrefix + editClassNbr));
                utClassInstructor.setInstructorId((String)classInstructorMap.get(auId));
                utClassInstructors.add(utClassInstructor);

            }

            /**
             * 开始按顺序存储
             */
            utCourseOfferingDao.saveCourseOfferingByList(utCourseOfferings);

            utOfferingConfigDao.saveUTOfferingConfigByList(utCourseOfferingConfigs);

            utConfigDetailDao.saveUTConfigDetailDaoByList(utConfigDetails);

            utClassDao.saveUTClassesByList(utClasses);

            utClassInstructorDao.saveClassInstructorByList(utClassInstructors);

        }finally{
            if(pre!=null)
                pre.close();
        }
    }




}
