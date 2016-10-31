package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;

import java.text.ParseException;
import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
public interface ITAConverter {

    List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informations);

    ApplyViewObject classInfoToApplyObject(User user, UTClass clazz);

    ApplyAssistantViewObject applyAssistantToTableViewObject(UTStudent student, UTClass clazz);

    ClassDetailInfoViewObject classInfoToViewObject(UTClass classId);

    TAMSTaApplication submitInfoToTaApplication(ClassInfoForm form);

    List<TermManagerViewObject> termInfoToViewObject(List<UTSession> sessions);

    UTSession termToDataObject(TermManagerViewObject newTerm) throws ParseException;
}
