package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSClassFundingDraftDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSClassFundingDraft;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/11/25.
 */


@Repository
@Component("TAMSClassFundingDraftDaoJpa")
public class TAMSClassFundingDraftDaoJpa implements TAMSClassFundingDraftDao {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Autowired
    private UTSessionDao sessionDao;


    @Override
    public List<TAMSClassFunding> selectAll() {

        List<TAMSClassFundingDraft> list = KradDataServiceLocator.getDataObjectService().findAll(TAMSClassFundingDraft.class).getResults();
        List<TAMSClassFunding> result = new ArrayList<>(list.size());

        for(TAMSClassFundingDraft per : list ){
            TAMSClassFunding tamsClassFunding = new TAMSClassFunding();
            tamsClassFunding.setApplyFunding(per.getApplyFunding());
            tamsClassFunding.setAssignedFunding(per.getAssignedFunding());
            tamsClassFunding.setBonus(per.getBonus());
            tamsClassFunding.setClassInformation(
                    classInfoDao.getOneById(
                            Integer.parseInt(
                                    per.getClassId()
                            )));
            tamsClassFunding.setSession(
                    sessionDao.getUTSessionById(
                            tamsClassFunding.getClassInformation().getSessionId()
                    )
            );
            tamsClassFunding.setClassId(per.getClassId());
            tamsClassFunding.setPhdFunding(per.getPhdFunding());
            tamsClassFunding.setTravelSubsidy(per.getTravelSubsidy());
            result.add(tamsClassFunding);
        }


        return result;
    }
}
