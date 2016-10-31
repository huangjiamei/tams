package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;

import java.util.List;

/**
 * Created by 金祖增 on 2016/10/21.
 */
public interface UTSessionDao {

    UTSession getUTSessionById(Integer id);

    UTSession getCurrentSession();

    UTSession setCurrentSession(UTSession utSession);

    List<UTSession> selectAll();

    boolean insertOneByEntity(UTSession session);

    UTSession selectByYearAndTerm(String year, String term);

    boolean updateOneByEntity(UTSession session);

    boolean deleteOneByEntity(UTSession session);
}
