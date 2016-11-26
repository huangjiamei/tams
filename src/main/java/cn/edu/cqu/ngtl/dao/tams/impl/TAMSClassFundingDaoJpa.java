package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSClassFundingDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awake on 2016/11/25.
 */
@Repository
@Component("TAMSClassFundingDao")
public class TAMSClassFundingDaoJpa implements TAMSClassFundingDao {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTSessionDao sessionDao;


    @Override
    public List<TAMSClassFunding> selectAll(User user) {
        List<TAMSClassFunding> list = KradDataServiceLocator.getDataObjectService().findAll(TAMSClassFunding.class).getResults();

        for(TAMSClassFunding per : list) {
            per.setClassInformation(
                    classInfoDao.getOneById(
                            Integer.parseInt(
                                    per.getClassId()
                            )
                    )
            );
            if(per.getClassInformation() !=null)
                per.setSession(
                        sessionDao.getUTSessionById(
                                per.getClassInformation().getSessionId()
                        )
                );
        }
        return list;
    }
}
