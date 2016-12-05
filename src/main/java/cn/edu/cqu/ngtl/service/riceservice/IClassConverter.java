package cn.edu.cqu.ngtl.service.riceservice;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject;

import java.util.List;

/**
 * Created by awake on 2016/12/2.
 */
public interface IClassConverter {

    List<StuIdClassIdPair> extractIdsFromApplication(List<MyTaViewObject> checkedList);

    List<String> extractIdsFromMyTaInfo(List<MyTaViewObject> checkedList);

    List<MyTaViewObject> studentInfoToMyTaViewObject(List<UTStudent> studentList);

    TAMSTaApplication TaViewObjectToTaApplication(MyTaViewObject application, String classid);



}
