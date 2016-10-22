package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTClass;

import java.util.List;

/**
 * Created by tangjing on 16-10-15.
 */
public interface UTClassDao {

    List<UTClass> selectAllMappedByDepartment();

    UTClass getUTClassByID(Integer id);

}
