package cn.edu.cqu.ngtl.dao.tams.impl;

import cn.edu.cqu.ngtl.dao.tams.TAMSTimeSettingsDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;
import org.kuali.rice.krad.data.KradDataServiceLocator;

/**
 * Created by tangjing on 16-11-23.
 */
public class TAMSTimeSettingsDaoJpa implements TAMSTimeSettingsDao {

    @Override
    public boolean insetOneByEntity(TAMSTimeSettings timeSetting) {
        String generatedId = KradDataServiceLocator.getDataObjectService().save(timeSetting).getId();
        return generatedId != null;
    }

    @Override
    public boolean deleteOneByEntity(TAMSTimeSettings timeSetting) {
        try{
            KradDataServiceLocator.getDataObjectService().delete(timeSetting);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean updateOneByEntity(TAMSTimeSettings timeSetting) {
        try {
            KradDataServiceLocator.getDataObjectService().save(timeSetting);
            return true;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
/*        try{
            Logger logger = Logger.getLogger(LoginFilter.class);
            if(timeSetting.getId() == null) {
                logger.info("未获取到ID，无法查询数据");
                return false;
            }
            TAMSTimeSettings current = KradDataServiceLocator.getDataObjectService().find(
                    TAMSTimeSettings.class, timeSetting.getId());
            if(current == null) {
                logger.info("未找到数据");
                throw new NullPointerException();
            }
        }
        catch (RuntimeException e) {

        }*/
    }
}
