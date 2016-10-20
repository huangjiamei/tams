package cn.edu.cqu.ngtl.service.taservice.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInfoDao;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAServiceimpl implements ITAService {

    @Autowired
    private UTClassInfoDao classInfoDao;

    @Override
    public UTClassInformation getClassInfoById(Integer id) {
        return classInfoDao.getOneById(id);
    }

}
