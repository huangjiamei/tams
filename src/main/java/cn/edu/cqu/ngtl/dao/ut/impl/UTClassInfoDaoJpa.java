package cn.edu.cqu.ngtl.dao.ut.impl;import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;import cn.edu.cqu.ngtl.dataobject.ut.UTSession;import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;import org.kuali.rice.krad.data.KradDataServiceLocator;import org.kuali.rice.krad.service.KRADServiceLocator;import org.springframework.stereotype.Component;import org.springframework.stereotype.Repository;import javax.persistence.EntityManager;import javax.persistence.Query;import java.util.ArrayList;import java.util.List;import java.util.Map;/** * Created by awake on 2016-10-19. */@Repository@Component("UTClassInfoDaoJpa")public class UTClassInfoDaoJpa implements UTClassInfoDao {    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();    @Override    public List<UTClassInformation> getAllCurrentClassInformation(){        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();        Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INFORMATION t WHERE t.SESSION_ID='"+curSession.getId()+"'",UTClassInformation.class);        List<UTClassInformation> result = query.getResultList();        return result;    }    @Override    public UTClassInformation getOneById(Integer id) {        Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INFORMATION t WHERE t.UNIQUEID='"+id+"'",UTClassInformation.class);        List<UTClassInformation> result = query.getResultList();        return result.size() != 0 ? result.get(0) : null;    }    @Override    public List<UTClassInformation> selectBatchByIds(List<Object> classIds) {        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();        List<UTClassInformation> informations = new ArrayList<>(classIds.size());        KradDataServiceLocator.getDataObjectService().flush(UTClassInformation.class);        EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();        for(Object classId : classIds) {            Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INFORMATION t WHERE t.SESSION_ID='" + curSession.getId() + "'" + " AND t.UNIQUEID='" + classId + "'",                    UTClassInformation.class);            informations.addAll(query.getResultList());        }        return informations;    }/*    @Override    public List<UTClassInformation> selectAllCourseInfoByIds(List<Object> ids) {        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();        List<UTClassInformation> informations = new ArrayList<>(ids.size());        for(Object id : ids){            Query qr = em.createNativeQuery("SELECT c.DEPT_NAME, c.COURSE_NAME, c.COURSE_CODE, c.CLASS_NBR, i.NAME, SUM(t.ELAPSED_TIME), p.NAME, c.STATUS, FROM UNITIME_CLASS_INFORMATION c JOIN UNITIME_CLASS_INSTRUCTOR uci ON c.UNIQUEID=uci.CLASS_ID AND c.UNIQUEID='"+ id +"' AND c.SESSION_ID = '"+curSession.getId()+"' JOIN UNITIME_INSTRUCTOR i ON uci.INSTRUCTOR_ID = i.UNIQUEID JOIN TAMS_TEACH_CALENDAR t ON t.CLASS_ID = c.UNIQUEID JOIN CM_PROGRAM_COURSE cp ON c.COURSE_ID = cp.COURSE_ID JOIN CM_PROGRAM p ON cp.PROGRAM_ID = p.UNIQUEID GROUP BY t.CLASS_ID", UTClassInformation.class);            informations.addAll(qr.getResultList());        }        return informations;    }    */    @Override    public List<UTClassInformation> selectByConditions(Map<String, String> conditions) {        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();        int countNull = 0;        for (Map.Entry<String, String> entry : conditions.entrySet()) {            if(entry.getValue() == null) {                conditions.put(entry.getKey(), "%");                countNull++;            }            else                conditions.put(entry.getKey(), "%" + entry.getValue() + "%");        }        if(countNull!=5) {            Query query = em.createNativeQuery("SELECT * FROM UNITIME_CLASS_INFORMATION A WHERE A .UNIQUEID IN ( SELECT U .CLASS_ID FROM UNITIME_CLASS_INSTRUCTOR U WHERE U .INSTRUCTOR_ID IN ( SELECT T.UNIQUEID FROM UNITIME_INSTRUCTOR T WHERE T . NAME LIKE '" + conditions.get("InstructorName") + "')) AND A .CLASS_NBR LIKE '" + conditions.get("ClassNumber") + "' AND A .DEPARTMENT_ID LIKE '" + conditions.get("DepartmentId") + "' AND A .COURSE_NAME LIKE '" + conditions.get("CourseName") + "' AND A .COURSE_CODE LIKE '" + conditions.get("CourseCode") + "' AND A.SESSION_ID ='" + curSession.getId() + "'",                    UTClassInformation.class);            List<UTClassInformation> queryResult = query.getResultList();            for(UTClassInformation information : queryResult) {                List<Object> instructorNames = em.createNativeQuery("SELECT i.NAME FROM UNITIME_INSTRUCTOR i, UNITIME_CLASS_INSTRUCTOR ci WHERE i.UNIQUEID = ci.INSTRUCTOR_ID AND ci.CLASS_ID = '" + information.getId() + "' ORDER BY i.NAME")                        .getResultList();                String name = "|";                if(instructorNames != null && instructorNames.size() != 0)                    for(Object obj : instructorNames)                        name = name + obj.toString() + "|";                else                    name = "信息缺失";                information.setInstructorName(name);            }            return query.getResultList();        }        return this.getAllCurrentClassInformation();    }}