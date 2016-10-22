package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.viewobject.course.ApplyAssistantViewObject;

/**
 * Created by long2ice
 * Date on 2016/10/20
 */
public interface IUTApplyAssistant {
    ApplyAssistantViewObject getApplyAssistantById(User user,Integer id);
}
