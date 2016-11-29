package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTaDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTSessionDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by tangjing on 16-11-1.
 */
@Repository
@Component("TAMSTaDaoJpa")
public class TAMSTaDaoJpa implements TAMSTaDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    //系统管理员和教务处管理员用户查询全校所有助教
    @Override
    public List<TAMSTa> selectAll() {
        //return KRADServiceLocator.getDataObjectService().findAll(TAMSTa.class).getResults();
        //查询当前学期的所有助教
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("sessionId",curSession.getId()));
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        return qr.getResults();
    }

    //先根据教师id查询所有的课程（classInstructorDao.selectClassIdsByInstructorId(uId)），再根据批量课程id查出所有的助教
    @Override
    public List<TAMSTa> selectByClassId(List<Object> classIds) {
        List<TAMSTa> tas = new ArrayList<>();
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        for(Object classId : classIds) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("taClassId", classId),
                            equal("sessionId",curSession.getId())
                    )
            );
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            tas.addAll(qr.getResults());
        }

        return tas.isEmpty()?null:tas;
    }

/*
    //根据Instructor 统一认证号获取所在学院的id
    @Override
    public String selectCollegeStaffDeptId(String uId){
        Query qr = em.createNativeQuery("SELECT DEPARTMENT_ID FROM UNITIME_INSTRUCTOR WHERE ID_NUMBER = '"+ uId + "' ");
        return qr.getResultList().toString();
    }
    */


    //二级管理员用户直接根据学院id查询所有助教
    public List<TAMSTa> selectByDeptId(String deptId){
        /*
        //获取用户所在的学院id
        UserSession userSession = GlobalVariables.getUserSession();
        User user = (User)userSession.retrieveObject("user");
        */
        UTSession curSession = new UTSessionDaoJpa().getCurrentSession();
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClass.courseOffering.course.departmentId", deptId),
                        equal("sessionId", curSession.getId())
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );
        return qr.getResults().isEmpty()? null : qr.getResults();
    }

    //根据student_id查询class_id
    @Override
    public List<Object> selectClassIdsByStudentId(String uId) {
        Query query = em.createNativeQuery("SELECT TA_CLASS FROM TAMS_TA t WHERE t.TA_ID='" + uId + "'");
        return query.getResultList();
    }

    //根据批量的id查询
    @Override
    public List<TAMSTa> selectBatchByIds(List<String> ids) {
        List<TAMSTa> tas = new ArrayList<>();

        for(String id : ids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("id", id));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            if(qr.getResults().size() != 0)
                tas.add(qr.getResults().get(0));
        }

        return tas.isEmpty()?null:tas;
    }

    //根据批量的student_id查询
    @Override
    public List<TAMSTa> selectBatchByTaIds(List<String> ids) {
        List<TAMSTa> tas = new ArrayList<>();

        for (String id : ids) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(equal("taId", id));
            QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                    TAMSTa.class,
                    criteria.build()
            );
            if(qr.getResults().size() != 0)
                tas.add(qr.getResults().get(0));
        }

        return tas.isEmpty() ? null : tas;
    }

    @Override
    public boolean updateByEntity(TAMSTa ta) {
        return KRADServiceLocator.getDataObjectService().save(ta) != null;
    }

    @Override
    public TAMSTa selectByStudentIdAndClassId(String stuId, String classId) {
        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClassId", classId),
                        equal("taId",stuId)
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );

        return qr.getResults().size() == 0 ? null : qr.getResults().get(0);
    }



    @Override
    public boolean insertByEntity(TAMSTa newTa) {

        return KRADServiceLocator.getDataObjectService().save(newTa) != null;

    }


    @Override
    public List<TAMSTa> selectByClassId(String classId){

        QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                and(
                        equal("taClassId", classId)
                )
        );
        QueryResults<TAMSTa> qr = KradDataServiceLocator.getDataObjectService().findMatching(
                TAMSTa.class,
                criteria.build()
        );

        return qr.getResults().size() == 0 ? null : qr.getResults();


    }
}
