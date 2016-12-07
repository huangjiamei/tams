package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dao.ut.UTCourseDao;
import cn.edu.cqu.ngtl.dao.ut.UTDepartmentDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
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
        this.syncCourseInfo(con);
        System.out.println("建立了连接");
        return con;
    }


    @Override
    public void closeConnection(Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        if(connection!=null)
            connection.close();
    }


    @Override
    public void syncCourseInfo(Connection connection) throws SQLException {
        List<UTDepartment> allDepartment = utDepartmentDao.getAllUTDepartments();
        Map departmentMap = new HashMap<>();
        for(UTDepartment utDepartment : allDepartment){
            departmentMap.put(utDepartment.getName(),utDepartment.getId());
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
                String departmentName = res.getString("CDDW").substring(2);
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

}
