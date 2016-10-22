package cn.edu.cqu.ngtl.service.classservice;

import cn.edu.cqu.ngtl.viewobject.classinfo.ClassInfoViewObject;

/**
 * Created by CQU-CST-WuErli on 2016/10/21.
 */
public interface IClassInfoService {
    ClassInfoViewObject getClassInfoById(Integer classId);
}
