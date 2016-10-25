package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;

import java.util.List;

/**
 * Created by awake on 2016-10-19.
 */
public interface UTClassInfoDao {

     List<UTClassInformation> getAllCurrentClassInformation();

    UTClassInformation getOneById(Integer id);

}
