package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.common.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
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
        List<UTCourse> allCourse = utCourseDao.selectAllMappedByDepartment();
        UTSession curSession = utSessionDao.getCurrentSession();
        String sessionPrefix = curSession.getYear()+curSession.getTerm();
        Map courseMap = new HashMap<>();
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
                String auId = res.getString("SFRZH");
                UTClass utClass = new UTClass();
                utClass.setClassNumber(classNbr);

                //TODO 新建CO COC CD等几个表的List对象，然后按需调用方法存入数据库
            }
        }finally{
            if(pre!=null)
                pre.close();
        }
    }




}
