package cn.edu.cqu.ngtl.dao.ut;

import cn.edu.cqu.ngtl.dataobject.ut.UTSession;

/**
 * Created by 金祖增 on 2016/10/21.
 */
public interface UTSessionDao {

    UTSession getUTSessionById(Integer id);

    public UTSession getCurrentSession();
    public UTSession setCurrentSession(UTSession utSession);

}
