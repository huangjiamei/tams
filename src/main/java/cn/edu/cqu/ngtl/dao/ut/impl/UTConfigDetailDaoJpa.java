package cn.edu.cqu.ngtl.dao.ut.impl;

import cn.edu.cqu.ngtl.dao.ut.UTConfigDetailDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTConfigDetail;
import org.kuali.rice.krad.data.KradDataServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awake on 2016/12/8.
 */
@Repository
@Component("UTConfigDetailDaoJpa")
public class UTConfigDetailDaoJpa implements UTConfigDetailDao {

    @Override
    public void saveUTConfigDetailDaoByList(List<UTConfigDetail> utConfigDetails){
        for(UTConfigDetail utConfigDetail : utConfigDetails){
            KradDataServiceLocator.getDataObjectService().save(utConfigDetail);
        }

    }

}
