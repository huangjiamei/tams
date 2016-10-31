package cn.edu.cqu.ngtl.service.adminservice;

import cn.edu.cqu.ngtl.dataobject.cm.CMCourseClassification;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaCategory;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;

import java.util.List;

/**
 * Created by tangjing on 16-10-25.
 */
public interface IAdminService {

    List<CMCourseClassification> getAllClassification();

    boolean addCourseClassificationOnlyWithName(String name);

    boolean changeCourseClassificationNameById(Integer id, String name);

    boolean removeCourseClassificationById(Integer id);

    List<TAMSCourseManager> getAllCourseManager();

    List<TAMSTaCategory> getAllTaCategories();

    boolean addTaCategory(TAMSTaCategory newTaCategory);

    boolean changeTaCategoryByEntiy(TAMSTaCategory tamsTaCategory);

    boolean removeTaCategoryById(Integer id);

    List<TAMSIssueType> getAllIssueTypes();

    boolean addTaIssueType(TAMSIssueType issueType);

    List<UTSession> getAllSessions();

    boolean addSession(UTSession session);

    boolean changeIssueType(TAMSIssueType issueType);

    boolean removeIssueTypeById(String id);

    boolean changeSession(UTSession session);
}
