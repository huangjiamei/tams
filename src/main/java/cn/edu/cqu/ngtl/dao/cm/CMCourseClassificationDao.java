package cn.edu.cqu.ngtl.dao.cm;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;

import java.util.List;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface CMCourseClassificationDao {

    CMCourseClassification selectOneById(Integer id);

    List<CMCourseClassification> selectAll();

    boolean insertOneByEntity(CMCourseClassification courseClassification);

    boolean updateOneByEntity(CMCourseClassification courseClassification);

    boolean deleteOneByEntity(CMCourseClassification courseClassification);

    CMCourseClassification selectOneByName(String name);
}
