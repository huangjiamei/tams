package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaBlackListDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaApplication;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTaBlackList;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.service.riceservice.IClassConverter;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016/12/2.
 */

@Service
public class ClassConverterImpl implements IClassConverter {

    @Autowired
    private TAMSTaBlackListDao tamsTaBlackListDao;


    private static final Logger logger = Logger.getRootLogger();

    @Override
    public List<StuIdClassIdPair> extractIdsFromApplication(List<MyTaViewObject> checkedList) {

        List<StuIdClassIdPair> pairs = new ArrayList<>(checkedList.size());

        for (MyTaViewObject per : checkedList) {
            pairs.add(new StuIdClassIdPair(per.getTaIdNumber(), per.getApplicationClassId()));
        }

        return pairs;
    }

    @Override
    public List<String> extractIdsFromMyTaInfo(List<MyTaViewObject> checkedList) {
        List<String> ids = new ArrayList<>();

        for (MyTaViewObject per : checkedList) {
            ids.add(per.getTaIdNumber());
        }

        return ids;
    }


    @Override
    public List<StuIdClassIdPair> extractIdsAndClassIdsFromMyTaInfo(List<MyTaViewObject> checkedList) {
        List<StuIdClassIdPair> pairs = new ArrayList<>();

        for (MyTaViewObject per : checkedList) {
            StuIdClassIdPair pair = new StuIdClassIdPair(per.getTaIdNumber(),per.getApplicationClassId());
            pairs.add(pair);
        }
        return pairs;
    }


    @Override
    public List<MyTaViewObject> studentInfoToMyTaViewObject(List<UTStudent> studentList) {
        if (studentList == null || studentList.size() == 0) {
            logger.error("数据为空！");
            return null;
        }
        List<MyTaViewObject> viewObjects = new ArrayList<>();
        List<String> blackManList = new ArrayList<>();
        List<TAMSTaBlackList> allBlackMen = tamsTaBlackListDao.getAllBlackList(); //被加入黑名单的无法被查到
        if(allBlackMen!=null&&allBlackMen.size()!=0){
            for(TAMSTaBlackList tamsTaBlackList:allBlackMen) {
                blackManList.add(tamsTaBlackList.getTaId());
            }
        }else{
            blackManList.add("");
        }

        for (UTStudent listone : studentList) {
            if(!blackManList.contains(listone.getId())) {
                MyTaViewObject viewObject = new MyTaViewObject();
                viewObject.setTaName(listone.getName());
                viewObject.setTaIdNumber(listone.getId());
                viewObject.setTaGender(listone.getGender());
                viewObject.setContactPhone("缺失");
                //点击查看详细信息会用到的
                CMProgram program = listone.getProgram();
                if (program == null)
                    viewObject.setTaMajorName("缺失");
                else
                    viewObject.setTaMajorName(listone.getProgram().getName().toString());
                viewObject.setAdvisorName("缺失");
                viewObjects.add(viewObject);
            }
        }
        return viewObjects;
    }

    //添加申请人点击确定。将MyTaViewObject对象转化为TAMSTaApplication对象
    @Override
    public TAMSTaApplication TaViewObjectToTaApplication(MyTaViewObject application, String classid,String phoneNbr) {

        TAMSTaApplication tamsTaApplication = new TAMSTaApplication();
        tamsTaApplication.setApplicationClassId(classid);
        tamsTaApplication.setApplicationId(application.getTaIdNumber());
        tamsTaApplication.setPhoneNbr(phoneNbr);
        //tamsTaApplication.setApplicationStatus("1");
        //tamsTaApplication.setApplicationTime(new StringDateConverter().convertToEntityAttribute(new Date()));
        return tamsTaApplication;
    }

    @Override
    public List<String> extractIdsFromClassList(List<ClassTeacherViewObject> checkedList) {
        List<String> ids = new ArrayList<>();

        for (ClassTeacherViewObject per : checkedList) {
            ids.add(per.getId().toString());
        }

        return ids;
    }
}
