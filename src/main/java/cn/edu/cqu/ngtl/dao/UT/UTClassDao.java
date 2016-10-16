package cn.edu.cqu.ngtl.dao.UT;

import cn.edu.cqu.ngtl.dataobject.UT.UTClass;

import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
public interface UTClassDao {

    List<UTClass> selectAllMappedByDepartment();

}
