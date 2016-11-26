package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created by awake on 2016/11/25.
 */
@Repository
@Component("TAMSClassFundingDao")
public class TAMSClassFundingDaoJpa implements TAMSClassFundingDao {

    EntityManager em =  KRADServiceLocator.getEntityManagerFactory().createEntityManager();

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;



    @Override
    public List<TAMSClassFunding> selectAll(User user) {

        UTSession currentSession = sessionDao.getCurrentSession();

        /**
         * 如果是二级单位管理员
         */
        if(iUserInfoService.isCollegeStaff(user.getCode())) {
            QueryByCriteria.Builder criteria = QueryByCriteria.Builder.create().setPredicates(
                    and(
                            equal("sessionId", currentSession.getId())
                    )
            );
            List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findMatching(TAMSClassFunding.class, criteria.build()).getResults();
            for (TAMSClassFunding per : list) {
                per.setClassInformation(
                        classInfoDao.getOneById(
                                Integer.parseInt(
                                        per.getClassId()
                                )
                        )
                );
            }

            //删除不是当前用户学院的课程
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                TAMSClassFunding tamsClassFunding = (TAMSClassFunding)iterator.next();
                if(!tamsClassFunding.getClassInformation().getDepartmentId().equals(user.getDepartmentId())){
                    iterator.remove();
                }
            }
            return list;
        }


        /**
         * 如果是教师  暂定教师没有权限查看此页面   根据教师取课程可以参照此方法
         */
/*        else if(iUserInfoService.isInstructor(user.getCode())){
            List<Object> classIds = classInstructorDao.selectClassIdsByInstructorId(user.getCode());
            String conditions = "";
            for(Object classid : classIds){
                conditions+="'" + classid.toString()+"',";
            }

            String sql = conditions.substring(0,conditions.length()-1);
            Query query = em.createNativeQuery("SELECT * FROM TAMS_CLASS_FUNDING t WHERE t.CLASS_ID in ("+sql+")",TAMSClassFunding.class);
            List<TAMSClassFunding> list = query.getResultList();
            for (TAMSClassFunding per : list) {
                per.setClassInformation(
                        classInfoDao.getOneById(
                                Integer.parseInt(
                                        per.getClassId()
                                )
                        )
                );
            }
            return list;
        }*/
        return null;
    }
}
