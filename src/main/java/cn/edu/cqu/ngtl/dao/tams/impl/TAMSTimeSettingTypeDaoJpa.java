package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTimeSettingTypeDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettingType;
import org.kuali.rice.krad.data.KradDataServiceLocator;

/**
 * Created by tangjing on 16-11-23.
 */
public class TAMSTimeSettingTypeDaoJpa implements TAMSTimeSettingTypeDao {
    @Override
    public boolean insetOneByEntity(TAMSTimeSettingType settingType) {
        String generatedId = KradDataServiceLocator.getDataObjectService().save(settingType).getId();
        return generatedId != null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSTimeSettingType settingType) {
        try{
            KradDataServiceLocator.getDataObjectService().delete(settingType);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean updateOneByEntity(TAMSTimeSettingType settingType) {
        try {
            KradDataServiceLocator.getDataObjectService().save(settingType);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
