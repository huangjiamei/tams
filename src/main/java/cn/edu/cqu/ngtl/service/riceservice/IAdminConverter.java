package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSDeptFunding;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;

import java.util.List;

/**
 * Created by awake on 2016-10-26.
 */
public interface IAdminConverter {

    List<CourseManagerViewObject> getCourseManagerToTableViewObject(List<TAMSCourseManager> tamsCourseManagerList);

    List<TaFundingViewObject> taFundingToViewObject(List<TAMSTa> tamsTas);

    List<DetailFundingViewObject> detailFundingToViewObject(List<TAMSTa> tamsTas);

    List<ClassFundingViewObject> combineClassFunding(List<ClassFundingViewObject> list);

}
