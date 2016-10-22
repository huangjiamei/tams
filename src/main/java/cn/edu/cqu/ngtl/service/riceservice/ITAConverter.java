package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;

import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAConverter {

    List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informations);

    ApplyViewObject classInfoToApplyObject(User user, UTClass clazz);

    ApplyAssistantViewObject applyAssistantToTableViewObject(User user, UTClass clazz);

    ClassInfoViewObject classInfoToViewObject(UTClass classId);

}
