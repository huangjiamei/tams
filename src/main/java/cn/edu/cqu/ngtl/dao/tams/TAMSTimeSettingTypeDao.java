package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettingType;

import java.util.List;

/**
 * Created by tangjing on 16-11-23.
 */
public interface TAMSTimeSettingTypeDao {

    List<TAMSTimeSettingType> selectAll();

    boolean insertOneByEntity(TAMSTimeSettingType settingType);

    boolean deleteOneByEntity(TAMSTimeSettingType settingType);

    boolean updateOneByEntity(TAMSTimeSettingType settingType);

    TAMSTimeSettingType selectByName(String typeName);
}
