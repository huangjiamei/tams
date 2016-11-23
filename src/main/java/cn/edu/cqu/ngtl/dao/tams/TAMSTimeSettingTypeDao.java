package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettingType;

/**
 * Created by tangjing on 16-11-23.
 */
public interface TAMSTimeSettingTypeDao {
    boolean insetOneByEntity(TAMSTimeSettingType settingType);

    boolean deleteOneByEntity(TAMSTimeSettingType settingType);

    boolean updateOneByEntity(TAMSTimeSettingType settingType);
}
