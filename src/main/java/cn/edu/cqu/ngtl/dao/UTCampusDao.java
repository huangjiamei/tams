package cn.edu.cqu.ngtl.dao;


import cn.edu.cqu.ngtl.dataobject.UTCampus;

import java.util.List;


public interface UTCampusDao {
    public List<UTCampus> getAllCampuses();
    
    public UTCampus getCampusById(Integer id);
    
    public UTCampus getCampusByShortName(String shortName);
}
