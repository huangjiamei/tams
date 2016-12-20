package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTimeSettings;

import java.util.List;

/**
 * Created by tangjing on 16-11-23.
 */
public interface TAMSTimeSettingsDao {

    boolean insetOneByEntity(TAMSTimeSettings timeSetting);

    boolean deleteOneByEntity(TAMSTimeSettings timeSetting);

    boolean updateOneByEntity(TAMSTimeSettings timeSetting);

    TAMSTimeSettings selectByTypeId(String typeId);

    TAMSTimeSettings selectByTypeIdAndSessionId(String typeId, String sessionId);

    List<TAMSTimeSettings> selectAllCurrentSession();
}
