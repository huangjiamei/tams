package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;

/**
 * Created by tangjing on 16-11-23.
 */
public interface TAMSTimeSettingsDao {

    boolean insetOneByEntity(TAMSTimeSettings timeSetting);

    boolean deleteOneByEntity(TAMSTimeSettings timeSetting);

    boolean updateOneByEntity(TAMSTimeSettings timeSetting);

}
