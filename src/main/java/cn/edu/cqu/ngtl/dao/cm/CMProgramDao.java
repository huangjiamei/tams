package cn.edu.cqu.ngtl.dao.cm;

import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;

import java.util.List;

/**
 * Created by 金祖增 on 2016/10/21.
 */
public interface CMProgramDao {

    CMProgram getCMProgramById(Integer id);

    List<CMProgram> selectByDepartmentId(String condDepartmentName);
}
