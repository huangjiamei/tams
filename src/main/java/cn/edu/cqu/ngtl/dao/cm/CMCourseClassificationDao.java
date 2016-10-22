package cn.edu.cqu.ngtl.dao.cm;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface CMCourseClassificationDao {
    CMCourseClassification selectOneById(Integer id);
}
