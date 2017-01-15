package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ENTITY_ENT_TYP_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_ENTITY_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.krim.impl.KRIM_PRNCPL_T_DaoJpa;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassApplyStatusDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassTaApplicationDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTeachCalendarDao;
import cn.edu.cqu.ngtl.dao.ut.*;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_ENT_TYP_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_ENTITY_T;
import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PRNCPL_T;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassApplyStatus;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassTaApplication;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.service.common.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

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

    @Autowired
    private TAMSClassApplyStatusDao tamsClassApplyStatusDao;

    @Autowired
    private UTStudentTimetableDao utStudentTimetableDao;

    @Autowired
    private TAMSTeachCalendarDao tamsTeachCalendarDao;

    @Autowired
    private TAMSClassTaApplicationDao tamsClassTaApplicationDao;

    @Override
    public Connection getConnection(String hostType, String hostIp, String hostPort, String dbName, String dbUserName,
                                    String dbPassWd, String[] syncInfo) throws SQLException, ClassNotFoundException {
        String url = "";
        Connection con = null;
        switch (hostType) {
            case "0":
                Class.forName("oracle.jdbc.driver.OracleDriver");
                url += "jdbc:oracle:thin:@"+hostIp+":"+hostPort+"/"+dbName;
                break;
            case "1":
                //暂未测试
                Class.forName("com.mysql.jdbc.Driver");
                url += "jdbc:mysql://"+hostIp+":"+hostPort+"/"+dbName;
                break;
            case "2":
                //暂未测试
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                url += "jdbc:sqlserver://"+hostIp+":"+hostPort+";DatabaseName="+dbName;
                break;
        }
        con = DriverManager.getConnection(url, dbUserName, dbPassWd);
        List<String> needToSync = Arrays.asList(syncInfo);

        if (needToSync.contains("1"))
            this.syncCourseInfo(con);  //导入课程信息
        if (needToSync.contains("2"))
            this.syncClassInfo(con);   //导入班次信息
        if (needToSync.contains("3")){
            syncAddedClassInfo(con);
        }
//        this.addNewUser(con);
//            this.syncStudentTimetableInfo(con);  //导入学生课表
//            this.syncChangesOfClasses(con);
//            this.syncSpecificClass(con);
        return con;
    }


    @Override
    public void closeConnection(Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        if (connection != null)
            connection.close();
    }


    /**
     * 同步课程信息（相关表UNITIME_COURSE）
     *
     * @param connection
     * @throws SQLException
     */
    @Override
    public void syncCourseInfo(Connection connection) throws SQLException {
        List<UTDepartment> allDepartment = utDepartmentDao.getAllUTDepartments();
        Map departmentMap = new HashMap<>();
        for (UTDepartment utDepartment : allDepartment) {
            departmentMap.put(utDepartment.getDeptcode(), utDepartment.getId());
        }

        String queryCourse = "SELECT * FROM LESSONINFO";
        int i = 0;
        PreparedStatement pre = connection.prepareStatement(queryCourse);
        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String coureseName = res.getString("KCMC");
                String courseCode = res.getString("KCDM");
                String departmentName = res.getString("CDDW").substring(0, 2);
                Integer deptId = (Integer) departmentMap.get(departmentName);
                String credit = res.getString("XF");
                String courseHour = res.getString("XS");
                String kcId = res.getString("KCID");
                UTCourse utCourse = new UTCourse();
                utCourse.setHour(courseHour);
                utCourse.setDepartmentId(deptId);
                utCourse.setCodeR(courseCode);
                utCourse.setName(coureseName);
                utCourse.setCredit(credit);
                utCourse.setId(Integer.parseInt("2016"+kcId));
                utCourseDao.InsertOneByEntity(utCourse);
                System.out.println(i++);
            }
        } finally {
            if (pre != null)
                pre.close();
        }
    }

    /**
     * 同步教学班信息
     *
     * @param connection
     * @throws SQLException
     */
    public void syncClassInfo(Connection connection) throws SQLException {
        List<UTClass> utClasses = new ArrayList<>();
        List<UTCourseOffering> utCourseOfferings = new ArrayList<>();
        List<UTCourseOfferingConfig> utCourseOfferingConfigs = new ArrayList<>();
        List<UTConfigDetail> utConfigDetails = new ArrayList<>();
        List<UTClassInstructor> utClassInstructors = new ArrayList<>();
        List<TAMSClassApplyStatus> tamsClassApplyStatuses = new ArrayList<>();


        Map courseMap = new HashMap<>();
        Map classInstructorMap = new HashMap<>();
        List<String> classNbrs = new ArrayList<>(); //判断是否有重复的教学班号，如果有，说明一个教学班有多个教师一起上

        List<UTCourse> allCourse = utCourseDao.selectAllMappedByDepartment();
        UTSession curSession = utSessionDao.getCurrentSession();
        String year = curSession.getYear();
        String term = curSession.getTerm();
        if (term.equals("春")) {
            term = "1";
            year = String.valueOf(Integer.parseInt(year)-1);
        } else if (term.equals("秋")) {
            term = "0";
        }
        List<UTInstructor> utInstructorList = utInstructorDao.getAllInstructors();

        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }

        String sessionPrefix = curSession.getYear();
        if (curSession.getTerm().equals("春")) {
            sessionPrefix += "01";
        } else if (curSession.getTerm().equals("秋")) {
            sessionPrefix += "02";
        }

        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }

        for (UTCourse course : allCourse) {
            courseMap.put(course.getCodeR(), course.getId());
        }
        String queryCourse = "SELECT * FROM JSKB t WHERE t.SFRZH IS NOT NULL AND t.XN = '2016' AND t.XQ_ID = '1'";

        String mutiSubpartCourse = "select distinct a.xn,a.xq_id,a.user_kcid from "+
                "(select * from cqdx_jwgl.t_kb_auto_table aa left join cqdx_jwgl.t_jh_setlessoninfo tt on aa.kcid = tt.dm where aa.KC_FLAG = 0 and aa.xn = '"+year+"' and aa.xq_id = '"+term+"') a, "+
                "(select * from cqdx_jwgl.t_kb_auto_table bb left join cqdx_jwgl.t_jh_setlessoninfo yy on bb.kcid = yy.dm where bb.KC_FLAG<>0 and bb.xn = '"+year+"' and bb.xq_id = '"+term+"') b "+
                "where a.kcid=b.kcid ";
        PreparedStatement pre = connection.prepareStatement(queryCourse);
        PreparedStatement pre1 = connection.prepareStatement(mutiSubpartCourse);

        List<String> multiSubpartCourseList = new ArrayList<>();
        try {
            pre1.setQueryTimeout(10000);
            ResultSet res1 = pre1.executeQuery();
            while (res1.next()) {
                multiSubpartCourseList.add(res1.getString("USER_KCID"));  //将多教学环节的课程代码加入list
            }
        } finally {
            if (pre1 != null)
                pre1.close();
        }

        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String courseCode = res.getString("KCDM");
                String classNbr = res.getString("JXBH");
                String editClassNbr = classNbr.replace("-", "");
                String auId = res.getString("SFRZH");
                String courseType = res.getString("KC_FLAG");
                String classTypeName = res.getString("SKFS");
                boolean flag = false;
                if (!multiSubpartCourseList.contains(courseCode) || (multiSubpartCourseList.contains(courseCode) && courseType.equals("0"))) {   //如果课程代码重复且不是理论课的教学班不再导入

                    String queryRoomAndTWeek = "SELECT * FROM KCKB t WHERE t.KCDM = '"+courseCode+"' AND t.JXBH = '"+classNbr+"' AND t.XN = '2016' AND t.XQ_ID = '1'";
                    PreparedStatement pre2 = connection.prepareStatement(queryRoomAndTWeek);
                    List<String> teachWeekList = new ArrayList<>();

                    String teachWeek = "";
                    String roomName = "";
                    try {
                        ResultSet res2 = pre2.executeQuery();
                        flag = true;
                        while (res2.next()) {
                            teachWeek += res2.getString("ANALYSE")+",";  //暂定已这种方式分割开
                            roomName = res2.getString("MC");
                        }

                    } finally {
                        if (pre2 != null)
                            pre2.close();
                    }

                    //如果KCKB里面没有这个教学班的信息
                    if (!teachWeek.equals("") && !roomName.equals("")) {
                        if (!classNbrs.contains(classNbr)) {  //重复的教学班代表该教学班有多个教师
                            classNbrs.add(classNbr);
                            /**
                             * Class对象
                             */
                            String teachWeekResult = "";
                            String[] teachWeekPre = teachWeek.split(",");
                            for (String s : teachWeekPre) {
                                if (!teachWeekResult.contains(s)) {
                                    teachWeekResult += s+"|";
                                }
                            }

                            UTClass utClass = new UTClass();
                            utClass.setRoomName(roomName);
                            utClass.setTeachWeek(teachWeekResult);
                            utClass.setClassNumber(classNbr);
                            utClass.setId(sessionPrefix+editClassNbr);//所有的uniqueid都通用这个值，年份+教学班号，保证唯一不重复
                            utClass.setCourseOfferingId(sessionPrefix+editClassNbr);
                            utClass.setClassType(classTypeName);
                            utClasses.add(utClass);
                            /**
                             * CourseOffering对象
                             */
                            UTCourseOffering utCourseOffering = new UTCourseOffering();
                            utCourseOffering.setId(sessionPrefix+editClassNbr);
                            utCourseOffering.setCourseId((Integer) courseMap.get(courseCode));
                            utCourseOffering.setSessionId(curSession.getId());
                            utCourseOfferings.add(utCourseOffering);
                            /**
                             * offeringConfig对象
                             */
                            UTCourseOfferingConfig utCourseOfferingConfig = new UTCourseOfferingConfig();
                            utCourseOfferingConfig.setId(sessionPrefix+editClassNbr);
                            utCourseOfferingConfig.setCourseOfferingId(sessionPrefix+editClassNbr);
                            utCourseOfferingConfig.setConfigName("1");
                            utCourseOfferingConfigs.add(utCourseOfferingConfig);
                            /**
                             * configDetail对象
                             */
                            UTConfigDetail utConfigDetail = new UTConfigDetail();
                            utConfigDetail.setId(sessionPrefix+editClassNbr);
                            utConfigDetail.setConfigId(sessionPrefix+editClassNbr);
                            utConfigDetail.setKlassId(sessionPrefix+editClassNbr);
                            utConfigDetails.add(utConfigDetail);

                            /**
                             * ApplyStatus对象
                             */
                            TAMSClassApplyStatus tamsClassApplyStatus = new TAMSClassApplyStatus();
                            tamsClassApplyStatus.setClassId(sessionPrefix+editClassNbr);
                            tamsClassApplyStatus.setWorkflowStatusId("1");
                            tamsClassApplyStatus.setId(sessionPrefix+editClassNbr);
                            tamsClassApplyStatuses.add(tamsClassApplyStatus);
                        }
                        //教学班号和身份认证号的关系
//                classInstructorMap.put(classNbr,auId);

                        UTClassInstructor utClassInstructor = new UTClassInstructor();
                        utClassInstructor.setId(sessionPrefix+editClassNbr);
                        utClassInstructor.setClassId(sessionPrefix+editClassNbr);
                        utClassInstructor.setInstructorId((String) classInstructorMap.get(auId));
                        utClassInstructors.add(utClassInstructor);
                    }
                }
            }

            /**
             * 开始按顺序存储
             */
            System.out.println("开始导入CO");
            utCourseOfferingDao.saveCourseOfferingByList(utCourseOfferings);

            System.out.println("开始导入COC");
            utOfferingConfigDao.saveUTOfferingConfigByList(utCourseOfferingConfigs);

            System.out.println("开始导入CD");
            utConfigDetailDao.saveUTConfigDetailDaoByList(utConfigDetails);

            System.out.println("开始导入CLASS");
            utClassDao.saveUTClassesByList(utClasses);

            System.out.println("开始导入CI");
            utClassInstructorDao.saveClassInstructorByList(utClassInstructors);

            System.out.println("开始导入ApplyStatus");
            tamsClassApplyStatusDao.saveApplyStatueByList(tamsClassApplyStatuses);


        } finally {
            if (pre != null)
                pre.close();
        }
    }

    /**
     * 导入学生课表
     *
     * @param connection
     * @throws SQLException
     */
    public void syncStudentTimetableInfo(Connection connection) throws SQLException {

        List<UTStudentTimetable> utStudentTimetables = new ArrayList<>();
        UTSession curSession = utSessionDao.getCurrentSession();

        String sessionPrefix = curSession.getYear();
        if (curSession.getTerm().equals("春")) {
            sessionPrefix += "01";
        } else if (curSession.getTerm().equals("秋")) {
            sessionPrefix += "02";
        }
        int i = 0;
        String queryCourse = "SELECT * FROM XSKB t WHERE  t.XH LIKE '2016%' AND t.XN = '2016' AND t.XQ_ID = '1'";
        PreparedStatement pre = connection.prepareStatement(queryCourse);
        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String studentId = res.getString("XH");
                String classNbr = res.getString("JXBH");
                String editClassNbr = classNbr.replace("-", "");
                UTStudentTimetable utStudentTimetable = new UTStudentTimetable();
                utStudentTimetable.setClassId(sessionPrefix+editClassNbr);
                utStudentTimetable.setStudentId(studentId);
                utStudentTimetable.setSessionId(curSession.getId());
                utStudentTimetables.add(utStudentTimetable);
            }
            utStudentTimetableDao.insertOneByEntityList(utStudentTimetables);
        } finally {
            if (pre != null)
                pre.close();
        }

    }


    public void syncChangesOfClasses(Connection connection) throws SQLException {
        List<UTClass> allClasses = utClassDao.getAllClasses();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        Map classNbrAndStuNbr = new HashMap();
        List<String> classNbrList = new ArrayList<>();

        String queryKCKB = "SELECT * FROM KCKB t WHERE  t.XN = '2016' AND t.XQ_ID = '1'";
        PreparedStatement pre = connection.prepareStatement(queryKCKB);
        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();

            while (res.next()) {
                String classNbr = res.getString("JXBH");
                String stuNbr = res.getString("SKBJ_RS");
                if (!classNbrList.contains(classNbr)) {
                    classNbrList.add(classNbr);
                    classNbrAndStuNbr.put(classNbr, stuNbr);
                }
            }
        } finally {
            if (pre != null)
                pre.close();
        }
        int i = 0;

        for (UTClass utClass : allClasses) {
            String stuNbr = (String) (classNbrAndStuNbr.get(utClass.getClassNumber()) == null ? "0" :classNbrAndStuNbr.get(utClass.getClassNumber()));
            utClass.setLimit(Integer.valueOf(stuNbr));
            utClassDao.insertOneByEntity(utClass);
            System.out.println(i++);
        }
    }
    /**
     * 更新人数，教室，教学周期，授课教师
     *
     */
    public void updateClassesInformation(Connection connection) throws SQLException{
        List<UTInstructor> utInstructorList = utInstructorDao.getAllInstructors();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        Map classInstructorMap = new HashMap();
        List<String> classNbrList = new ArrayList<>();
        List<String> classNumberList = new ArrayList<>();
        List<UTClass> utClasses = new ArrayList<>();
        List<UTClassInstructor> utClassInstructors = new ArrayList<>();
        List<String> identityAuthenticationList = new ArrayList<>();
        List<String> courseNumberList = new ArrayList<>();
        List<String> classIdList = new ArrayList<>();
        UTSession curSession = utSessionDao.getCurrentSession();
        String year = curSession.getYear();
        String term = curSession.getTerm();
        if (term.equals("春")) {
            term = "1";
            year = String.valueOf(Integer.parseInt(year)-1);
        } else if (term.equals("秋")) {
            term = "0";
        }
        String sessionPrefix = curSession.getYear();
        if (curSession.getTerm().equals("春")) {
            sessionPrefix += "01";
        } else if (curSession.getTerm().equals("秋")) {
            sessionPrefix += "02";
        }
        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }
        String queryKCKB = "SELECT * FROM KCKB";
        String queryJSKB = "SELECT * FROM JSKB";
        PreparedStatement pre = connection.prepareStatement(queryKCKB);
        PreparedStatement pre1= connection.prepareStatement(queryJSKB);
        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String classNumber = res.getString("JXBH");
                String stuNbr = res.getString("SKBJ_RS");
                String roomName=res.getString("MC");
                String teachWeek=res.getString("ANALYSE");
                String editClassNumber=classNumber.replace("-","");
                String editTeachWeek=teachWeek.replace(",","|");
                if(!classNbrList.contains(classNumber)){
                    classNbrList.add(classNumber);
                    if(stuNbr==null){
                        stuNbr="0";
                    }
                    UTClass utClass = new UTClass();
                    utClass.setLimit(Integer.valueOf(stuNbr));
                    utClass.setRoomName(roomName);
                    utClass.setTeachWeek(editTeachWeek);
                    utClass.setClassNumber(classNumber);
                    utClass.setId(sessionPrefix+editClassNumber);
                    utClass.setCourseOfferingId(sessionPrefix+editClassNumber);
                    utClasses.add(utClass);
                }
            }
            utClassDao.saveUTClassesByList(utClasses);
        }finally {
            if (pre != null)
                pre.close();
        }
        try{
            pre1.setQueryTimeout(10000);
            ResultSet res1 =pre1.executeQuery();
            int i=1;
            while (res1.next()){
                String identityAuthenticationNumber = res1.getString("SFRZH");
                String courseNumber=res1.getString("KCDM");
                String classNumber=res1.getString("JXBH");
                String editClassNumber =classNumber.replace("-", "");
                String ID=sessionPrefix+editClassNumber;
                if(!identityAuthenticationList.contains(identityAuthenticationNumber)||!courseNumberList.contains(courseNumber)||!classNumberList.contains(classNumber)){
                    identityAuthenticationList.add(identityAuthenticationNumber);
                    courseNumberList.add(courseNumber);
                    classNumberList.add(classNumber);
                    if(classIdList.contains(classNumber)){//班号重复
                        classNumber+=String.valueOf(i);//班号后面加i(i从1开始自增的数)
                        classIdList.add(classNumber);
                        ID+=String.valueOf(i);//主键后面加i(i从1开始自增的数)
                        i++;
                    }
                    else{
                        classIdList.add(classNumber);
                    }
                    UTClassInstructor utClassInstructor = new UTClassInstructor();
                    utClassInstructor.setId(ID);
                    utClassInstructor.setClassId(sessionPrefix+editClassNumber);
                    utClassInstructor.setInstructorId((String) classInstructorMap.get(identityAuthenticationNumber));
                    utClassInstructors.add(utClassInstructor);
                }
            }
            utClassInstructorDao.saveClassInstructorByList(utClassInstructors);
        }finally {
            if(pre1 !=null)
                pre1.close();
        }
    }


    /**
     * 同步特定的班次
     */

    public void syncSpecificClass(Connection connection) throws SQLException {

        List<UTClass> utClasses = new ArrayList<>();
        List<UTCourseOffering> utCourseOfferings = new ArrayList<>();
        List<UTCourseOfferingConfig> utCourseOfferingConfigs = new ArrayList<>();
        List<UTConfigDetail> utConfigDetails = new ArrayList<>();
        List<UTClassInstructor> utClassInstructors = new ArrayList<>();
        List<TAMSClassApplyStatus> tamsClassApplyStatuses = new ArrayList<>();

        List<UTInstructor> utInstructorList = utInstructorDao.getAllInstructors();

        Map classInstructorMap = new HashMap<>();


        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }

        UTSession curSession = utSessionDao.getCurrentSession();
        String year = curSession.getYear();
        String term = curSession.getTerm();
        if (term.equals("春")) {
            term = "1";
            year = String.valueOf(Integer.parseInt(year)-1);
        } else if (term.equals("秋")) {
            term = "0";
        }

        String sessionPrefix = curSession.getYear();
        if (curSession.getTerm().equals("春")) {
            sessionPrefix += "01";
        } else if (curSession.getTerm().equals("秋")) {
            sessionPrefix += "02";
        }

        String queryKCKB = "SELECT * FROM JSKB t WHERE  t.XN = '2016' AND t.XQ_ID = '1'  AND KCDM = 'MGMT35201'";
        PreparedStatement pre = connection.prepareStatement(queryKCKB);
        pre.setQueryTimeout(10000);
        ResultSet res = pre.executeQuery();
        try {
            while (res.next()) {
                String courseCode = res.getString("KCDM");
                String classNbr = res.getString("JXBH");
                String editClassNbr = classNbr.replace("-", "");
                String auId = res.getString("SFRZH");
                String courseType = res.getString("KC_FLAG");
                String classTypeName = res.getString("SKFS");


                String queryRoomAndTWeek = "SELECT * FROM KCKB t WHERE t.KCDM = '"+courseCode+"' AND t.JXBH = '"+classNbr+"' AND t.XN = '2016' AND t.XQ_ID = '1'";
                PreparedStatement pre2 = connection.prepareStatement(queryRoomAndTWeek);
                List<String> teachWeekList = new ArrayList<>();

                String teachWeek = "";
                String roomName = "";
                String stuNbr = "0";
                try {
                    ResultSet res2 = pre2.executeQuery();

                    while (res2.next()) {
                        teachWeek += res2.getString("ANALYSE")+",";  //暂定已这种方式分割开
                        roomName = res2.getString("MC");
                        stuNbr = res2.getString("SKBJ_RS");
                    }

                } finally {
                    if (pre2 != null)
                        pre2.close();
                }

                String teachWeekResult = "";
                if(!teachWeek.equals("")) {
                    String[] teachWeekPre = teachWeek.split(",");
                    for (String s : teachWeekPre) {
                        if (!teachWeekResult.contains(s)) {
                            teachWeekResult += s+"|";
                        }
                    }
                }

                UTClass utClass = new UTClass();
                utClass.setRoomName(roomName);
                utClass.setTeachWeek(teachWeekResult);
                utClass.setClassNumber(classNbr);
                utClass.setId(sessionPrefix+editClassNbr);//所有的uniqueid都通用这个值，年份+教学班号，保证唯一不重复
                utClass.setCourseOfferingId(sessionPrefix+editClassNbr);
                utClass.setClassType(classTypeName);
                utClass.setLimit(Integer.valueOf(stuNbr));
                utClasses.add(utClass);
                /**
                 * CourseOffering对象
                 */
                UTCourseOffering utCourseOffering = new UTCourseOffering();
                utCourseOffering.setId(sessionPrefix+editClassNbr);
                utCourseOffering.setCourseId(2016028334); //固定的
                utCourseOffering.setSessionId(curSession.getId());
                utCourseOfferings.add(utCourseOffering);
                /**
                 * offeringConfig对象
                 */
                UTCourseOfferingConfig utCourseOfferingConfig = new UTCourseOfferingConfig();
                utCourseOfferingConfig.setId(sessionPrefix+editClassNbr);
                utCourseOfferingConfig.setCourseOfferingId(sessionPrefix+editClassNbr);
                utCourseOfferingConfig.setConfigName("1");
                utCourseOfferingConfigs.add(utCourseOfferingConfig);
                /**
                 * configDetail对象
                 */
                UTConfigDetail utConfigDetail = new UTConfigDetail();
                utConfigDetail.setId(sessionPrefix+editClassNbr);
                utConfigDetail.setConfigId(sessionPrefix+editClassNbr);
                utConfigDetail.setKlassId(sessionPrefix+editClassNbr);
                utConfigDetails.add(utConfigDetail);

                /**
                 * ApplyStatus对象
                 */
                TAMSClassApplyStatus tamsClassApplyStatus = new TAMSClassApplyStatus();
                tamsClassApplyStatus.setClassId(sessionPrefix+editClassNbr);
                tamsClassApplyStatus.setWorkflowStatusId("1");
                tamsClassApplyStatus.setId(sessionPrefix+editClassNbr);
                tamsClassApplyStatuses.add(tamsClassApplyStatus);

                //教学班号和身份认证号的关系
                //                classInstructorMap.put(classNbr,auId);

                UTClassInstructor utClassInstructor = new UTClassInstructor();
                utClassInstructor.setId(sessionPrefix+editClassNbr);
                utClassInstructor.setClassId(sessionPrefix+editClassNbr);
                utClassInstructor.setInstructorId((String) classInstructorMap.get(auId)); //固定的ID
                utClassInstructors.add(utClassInstructor);
            }


            System.out.println("开始导入CO");
            utCourseOfferingDao.saveCourseOfferingByList(utCourseOfferings);

            System.out.println("开始导入COC");
            utOfferingConfigDao.saveUTOfferingConfigByList(utCourseOfferingConfigs);

            System.out.println("开始导入CD");
            utConfigDetailDao.saveUTConfigDetailDaoByList(utConfigDetails);

            System.out.println("开始导入CLASS");
            utClassDao.saveUTClassesByList(utClasses);

            System.out.println("开始导入CI");
            utClassInstructorDao.saveClassInstructorByList(utClassInstructors);

            System.out.println("开始导入ApplyStatus");
            tamsClassApplyStatusDao.saveApplyStatueByList(tamsClassApplyStatuses);
        } finally {
            if (pre != null)
                pre.close();
        }
    }


    public void addNewUser(Connection connection) throws SQLException {

        KRIM_ENTITY_T krimEntity = new KRIM_ENTITY_T_DaoJpa().createKrimEntityT();
        KRIM_ENTITY_ENT_TYP_T krimEntityEntTypT =
                new KRIM_ENTITY_ENT_TYP_T_DaoJpa().createKrimEntityEntTypTByEntityId(krimEntity.getId());
        KRIM_PRNCPL_T krimPrncplT =
                new KRIM_PRNCPL_T_DaoJpa().createKrimPrncplT(krimEntityEntTypT.getEntId()
                        , "2017191817"
                        , "02022007"
                        , ""
                        , "Y");
    }



    public void synCalendar() {
        List<Object> classIds = tamsClassApplyStatusDao.selectClassIdByStatus("1");
        for(Object classId : classIds) {
            List<TAMSTeachCalendar> tamsTeachCalendars = tamsTeachCalendarDao.selectAllByClassId(classId.toString());
            Integer totalElalpsedTime = 0;
            Integer applicationFunds = 0;
            if(tamsTeachCalendars != null) {
                for(int i=0; i<tamsTeachCalendars.size(); i++){
                    totalElalpsedTime = totalElalpsedTime + Integer.parseInt(tamsTeachCalendars.get(i).getElapsedTime());
                }
            }
            TAMSClassTaApplication tamsClassTaApplication = tamsClassTaApplicationDao.selectByClassId(classId.toString());
            if(tamsClassTaApplication != null) {
                tamsClassTaApplication.setWorkHour(totalElalpsedTime.toString());
                applicationFunds = totalElalpsedTime * 12;
                tamsClassTaApplication.setApplicationFunds(applicationFunds.toString());
                tamsClassTaApplicationDao.insertOneByEntity(tamsClassTaApplication);
            }
        }
    }


    public void syncAddedClassInfo(Connection connection) throws SQLException {
        List<UTClass> utClasses = new ArrayList<>();
        List<UTCourseOffering> utCourseOfferings = new ArrayList<>();
        List<UTCourseOfferingConfig> utCourseOfferingConfigs = new ArrayList<>();
        List<UTConfigDetail> utConfigDetails = new ArrayList<>();
        List<UTClassInstructor> utClassInstructors = new ArrayList<>();
        List<TAMSClassApplyStatus> tamsClassApplyStatuses = new ArrayList<>();


        Map courseMap = new HashMap<>();
        Map classInstructorMap = new HashMap<>();
        List<String> classNbrs = new ArrayList<>(); //判断是否有重复的教学班号，如果有，说明一个教学班有多个教师一起上

        List<UTCourse> allCourse = utCourseDao.selectAllMappedByDepartment();
        UTSession curSession = utSessionDao.getCurrentSession();
        String year = curSession.getYear();
        String term = curSession.getTerm();
        if (term.equals("春")) {
            term = "1";
            year = String.valueOf(Integer.parseInt(year)-1);
        } else if (term.equals("秋")) {
            term = "0";
        }
        List<UTInstructor> utInstructorList = utInstructorDao.getAllInstructors();

        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }

        String sessionPrefix = curSession.getYear();
        if (curSession.getTerm().equals("春")) {
            sessionPrefix += "01";
        } else if (curSession.getTerm().equals("秋")) {
            sessionPrefix += "02";
        }

        for (UTInstructor utInstructor : utInstructorList) {
            classInstructorMap.put(utInstructor.getIdNumber(), utInstructor.getId());
        }

        for (UTCourse course : allCourse) {
            courseMap.put(course.getCodeR(), course.getId());
        }


        String queryCourse = "SELECT * FROM JSKB t WHERE t.XN = '2016' AND t.XQ_ID = '1'";

        String querySFRZH = "SELECT * FROM CET_TEACHERINFO";


        PreparedStatement pre = connection.prepareStatement(queryCourse);
        PreparedStatement pre1 = connection.prepareStatement(querySFRZH);

        Map jsdmAndSfrzhMap = new HashMap();

        try {
            pre1.setQueryTimeout(10000);
            ResultSet res1 = pre1.executeQuery();

            while (res1.next()) {
                String jsdm = res1.getString("TEAC_NUM");
                String sfrzh = res1.getString("SFRZH") == null?"":res1.getString("SFRZH");

                jsdmAndSfrzhMap.put(jsdm,sfrzh);

//                multiSubpartCourseList.add(res1.getString("USER_KCID"));  //将多教学环节的课程代码加入list
            }
        } finally {
            if (pre1 != null)
                pre1.close();
        }

        try {
            pre.setQueryTimeout(10000);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String courseCode = res.getString("KCDM");
                String classNbr = res.getString("JXBH");
                String editClassNbr = classNbr.replace("-", "");
                String auId = "";
                String[] teacherIds = res.getString("JSDM").split(",");

                String courseType = res.getString("KC_FLAG");
                String classTypeName = res.getString("SKFS");
                boolean flag = false;
//                if (!multiSubpartCourseList.contains(courseCode) || (multiSubpartCourseList.contains(courseCode) && courseType.equals("0"))) {   //如果课程代码重复且不是理论课的教学班不再导入

                String queryRoomAndTWeek = "SELECT * FROM KCKB t WHERE t.KCDM = '"+courseCode+"' AND t.JXBH = '"+classNbr+"' AND t.XN = '2016' AND t.XQ_ID = '1'";
                PreparedStatement pre2 = connection.prepareStatement(queryRoomAndTWeek);
                List<String> teachWeekList = new ArrayList<>();

                String teachWeek = "";
                String roomName = "";
                try {
                    ResultSet res2 = pre2.executeQuery();
                    flag = true;
                    while (res2.next()) {
                        teachWeek += res2.getString("ANALYSE")+",";  //暂定已这种方式分割开
                        roomName = res2.getString("MC");
                    }

                } finally {
                    if (pre2 != null)
                        pre2.close();
                }

                //如果KCKB里面没有这个教学班的信息
//                if (!teachWeek.equals("") && !roomName.equals("")) {
//                    if (!classNbrs.contains(classNbr)) {  //重复的教学班代表该教学班有多个教师
                        classNbrs.add(classNbr);
                        /**
                         * Class对象
                         */
                        String teachWeekResult = "";
                        String[] teachWeekPre = teachWeek.split(",");
                        for (String s : teachWeekPre) {
                            if (!teachWeekResult.contains(s)) {
                                teachWeekResult += s+"|";
                            }
                        }

                        UTClass utClass = new UTClass();
                        utClass.setRoomName(roomName);
                        utClass.setTeachWeek(teachWeekResult);
                        utClass.setClassNumber(classNbr);
                        utClass.setId(sessionPrefix+editClassNbr);//所有的uniqueid都通用这个值，年份+教学班号，保证唯一不重复
                        utClass.setCourseOfferingId(sessionPrefix+editClassNbr);
                        utClass.setClassType(classTypeName);
                        utClasses.add(utClass);
                        /**
                         * CourseOffering对象
                         */
                        UTCourseOffering utCourseOffering = new UTCourseOffering();
                        utCourseOffering.setId(sessionPrefix+editClassNbr);
                        utCourseOffering.setCourseId((Integer) courseMap.get(courseCode));
                        utCourseOffering.setSessionId(curSession.getId());
                        utCourseOfferings.add(utCourseOffering);
                        /**
                         * offeringConfig对象
                         */
                        UTCourseOfferingConfig utCourseOfferingConfig = new UTCourseOfferingConfig();
                        utCourseOfferingConfig.setId(sessionPrefix+editClassNbr);
                        utCourseOfferingConfig.setCourseOfferingId(sessionPrefix+editClassNbr);
                        utCourseOfferingConfig.setConfigName("1");
                        utCourseOfferingConfigs.add(utCourseOfferingConfig);
                        /**
                         * configDetail对象
                         */
                        UTConfigDetail utConfigDetail = new UTConfigDetail();
                        utConfigDetail.setId(sessionPrefix+editClassNbr);
                        utConfigDetail.setConfigId(sessionPrefix+editClassNbr);
                        utConfigDetail.setKlassId(sessionPrefix+editClassNbr);
                        utConfigDetails.add(utConfigDetail);

                        /**
                         * ApplyStatus对象
                         */
                        TAMSClassApplyStatus tamsClassApplyStatus = new TAMSClassApplyStatus();
                        tamsClassApplyStatus.setClassId(sessionPrefix+editClassNbr);
                        tamsClassApplyStatus.setWorkflowStatusId("1");
                        tamsClassApplyStatus.setId(sessionPrefix+editClassNbr);
                        tamsClassApplyStatuses.add(tamsClassApplyStatus);
//                    }
                    //教学班号和身份认证号的关系
//                classInstructorMap.put(classNbr,auId);


                    if(teacherIds.length==1) {
                        UTClassInstructor utClassInstructor = new UTClassInstructor();
                        utClassInstructor.setId(sessionPrefix+editClassNbr);
                        utClassInstructor.setClassId(sessionPrefix+editClassNbr);
                        auId = jsdmAndSfrzhMap.get(teacherIds[0]).toString();
                        utClassInstructor.setInstructorId((String) classInstructorMap.get(auId));
                        utClassInstructors.add(utClassInstructor);
                    }else{
                        for(String s : teacherIds){
                            UTClassInstructor utClassInstructor = new UTClassInstructor();
                            utClassInstructor.setId(sessionPrefix+editClassNbr);
                            utClassInstructor.setClassId(sessionPrefix+editClassNbr);
                            auId = jsdmAndSfrzhMap.get(s).toString();
                            utClassInstructor.setInstructorId((String) classInstructorMap.get(auId));
                            utClassInstructors.add(utClassInstructor);

                        }
                    }
                }
//            }
//            }

            /**
             * 开始按顺序存储
             */
            System.out.println("开始导入CO");
            utCourseOfferingDao.saveCourseOfferingByList(utCourseOfferings);

            System.out.println("开始导入COC");
            utOfferingConfigDao.saveUTOfferingConfigByList(utCourseOfferingConfigs);

            System.out.println("开始导入CD");
            utConfigDetailDao.saveUTConfigDetailDaoByList(utConfigDetails);

            System.out.println("开始导入CLASS");
            utClassDao.saveUTClassesByList(utClasses);

            System.out.println("开始导入CI");
            utClassInstructorDao.saveClassInstructorByList(utClassInstructors);

            System.out.println("开始导入ApplyStatus");
            tamsClassApplyStatusDao.saveApplyStatueByList(tamsClassApplyStatuses);


        } finally {
            if (pre != null)
                pre.close();
        }
    }



}
