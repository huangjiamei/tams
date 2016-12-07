package cn.edu.cqu.ngtl.service.common;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by awake on 2016/12/7.
 */
public interface SyncInfoService {

     Connection getConnection(String hostType,
                                    String hostIp,
                                    String hostPort,
                                    String dbName,
                                    String dbUserName,
                                    String dbPassWd) throws SQLException,ClassNotFoundException;

     void closeConnection(Connection connection) throws SQLException;


     void syncCourseInfo(Connection connection) throws SQLException;




}
