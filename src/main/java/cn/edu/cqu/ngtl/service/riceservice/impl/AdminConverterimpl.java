package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTCourseDaoJpa;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSCourseManager;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSTa;
import cn.edu.cqu.ngtl.dataobject.ut.UTClassInstructor;
import cn.edu.cqu.ngtl.dataobject.ut.UTCourse;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.dataobject.ut.UTStudent;
import cn.edu.cqu.ngtl.service.riceservice.IAdminConverter;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DetailFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by awake on 2016-10-26.
 */
@Service
public class AdminConverterimpl implements IAdminConverter {

    @Autowired
    private UTSessionDao utSessionDao;

    @Autowired
    private UTClassInstructorDao utClassInstructorDao;


    private static final Logger logger = Logger.getRootLogger();

    @Override
    public List<CourseManagerViewObject> getCourseManagerToTableViewObject(List<TAMSCourseManager> tamsCourseManagerList) {
        List<CourseManagerViewObject> courseManagerViewObjectList = new ArrayList(tamsCourseManagerList.size());
        for (TAMSCourseManager tamsCourseManager : tamsCourseManagerList) {
            UTCourse utCourse = new UTCourseDaoJpa().selectOneById(tamsCourseManager.getCourseId());
            if(utCourse!=null) {
                CourseManagerViewObject courseManagerViewObject = new CourseManagerViewObject();
                courseManagerViewObject.setId(tamsCourseManager.getCourseManagerId());
                courseManagerViewObject.setCourseNm(utCourse.getName());
                courseManagerViewObject.setCourseNmb(utCourse.getCodeR());
                courseManagerViewObject.setCourseManager(tamsCourseManager.getUtInstructor().getName());
                courseManagerViewObject.setInstructorCode(tamsCourseManager.getUtInstructor().getCode());
                courseManagerViewObjectList.add(courseManagerViewObject);
            }
        }

        return  courseManagerViewObjectList;
    }

    @Override
    public List<TaFundingViewObject> taFundingToViewObject(List<TAMSTa> tamsTas){
        if(tamsTas == null || tamsTas.size() == 0) {
            //logger.error("数据为空！");
            //return null;
            List<TaFundingViewObject> nullObject = new ArrayList<>();
            nullObject.add(new TaFundingViewObject());
            return nullObject;
        }
        List<TaFundingViewObject> taFundingViewObjects = new ArrayList<>(tamsTas.size());
        for(TAMSTa ta : tamsTas) {
            TaFundingViewObject taFundingViewObject = new TaFundingViewObject();

            if(ta.getTaClass() != null) {
                if (ta.getTaClass().getCourseOffering() != null) {
                    UTCourse course = ta.getTaClass().getCourseOffering().getCourse();
                    if(course != null) {
                        taFundingViewObject.setCourseName(course.getName());
                        taFundingViewObject.setCourseCode(course.getCodeR());
                        taFundingViewObject.setDepartmentName(course.getDepartment().getName());
                    }
                }
            }
            UTStudent taStu = ta.getTa();
            if(taStu != null) {
                taFundingViewObject.setTaName(taStu.getName());
                //taFundingViewObject.setDepartmentName(taStu.getDepartment().getName());
            }

            if (ta.getCurSession() != null ){
                taFundingViewObject.setSessionName(ta.getCurSession().getYear() + "年" +
                        ta.getCurSession().getTerm() + "季");
            }
            taFundingViewObject.setStuId(ta.getTaId());
            //暂时缺失的属性
            taFundingViewObject.setAssignedFunding(ta.getAssignedFunding()==null?"0":ta.getAssignedFunding());
            taFundingViewObject.setBonus(ta.getBonus()==null?"0":ta.getBonus());
            taFundingViewObject.setPhdFunding(ta.getPhdFunding()==null?"0":ta.getPhdFunding());
            taFundingViewObject.setTravelSubsidy(ta.getTravelSubsidy()==null?"0":ta.getTravelSubsidy());
            if(ta.getTamsTaCategory() != null){
                taFundingViewObject.setTaType(ta.getTamsTaCategory().getName());
            }
            else
                taFundingViewObject.setTaType("缺失");
            Integer total =  (Integer.parseInt(taFundingViewObject.getAssignedFunding())+
                    Integer.parseInt(taFundingViewObject.getBonus())+
                    Integer.parseInt(taFundingViewObject.getPhdFunding())+
                    Integer.parseInt(taFundingViewObject.getTravelSubsidy()));
            taFundingViewObject.setTotal(total.toString());
            taFundingViewObjects.add(taFundingViewObject);
        }
        return taFundingViewObjects;
    }

    @Override
    public List<DetailFundingViewObject> detailFundingToViewObject(List<TAMSTa> tamsTas){
        if(tamsTas == null || tamsTas.size() == 0) {
            //logger.error("数据为空！");
            //return null;
            List<DetailFundingViewObject> nullObject = new ArrayList<>();
            nullObject.add(new DetailFundingViewObject());
            return nullObject;
        }

        UTSession curSession = utSessionDao.getCurrentSession();
        List<DetailFundingViewObject> detailFundingViewObjects = new ArrayList<>(tamsTas.size());
        for(TAMSTa ta : tamsTas) {
            DetailFundingViewObject detailFundingViewObject = new DetailFundingViewObject();
            UTCourse course = null;
            if(ta.getTaClass() != null) {
                if (ta.getTaClass().getCourseOffering() != null) {
                    course = ta.getTaClass().getCourseOffering().getCourse();
                    if(course != null) {
                        detailFundingViewObject.setCourseName(course.getName());
                        detailFundingViewObject.setCourseCode(course.getCodeR());
                    }
                }
            }
            UTStudent taStu = ta.getTa();
            if(taStu != null) {
                detailFundingViewObject.setTaName(taStu.getName());
                detailFundingViewObject.setStuId(taStu.getId());
            }
            //暂时缺失的属性
            detailFundingViewObject.setBankId("缺失");
            detailFundingViewObject.setBankName("缺失");
            detailFundingViewObject.setIdentity(ta.getTa().getIdNumber());
            detailFundingViewObject.setMonthlySalary1(ta.getMonth1()==null ? "0" : ta.getMonth1());
            detailFundingViewObject.setMonthlySalary2(ta.getMonth2()==null ? "0" : ta.getMonth2());
            detailFundingViewObject.setMonthlySalary3(ta.getMonth3()==null ? "0" : ta.getMonth3());
            detailFundingViewObject.setMonthlySalary4(ta.getMonth4()==null ? "0" : ta.getMonth4());
            detailFundingViewObject.setMonthlySalary5(ta.getMonth5()==null ? "0" : ta.getMonth5());
            detailFundingViewObject.setMonthlySalary6(ta.getMonth6()==null ? "0" : ta.getMonth6());
            detailFundingViewObject.setMonthlySalary7(ta.getMonth7()==null ? "0" : ta.getMonth7());
            detailFundingViewObject.setMonthlySalary8(ta.getMonth8()==null ? "0" : ta.getMonth8());
            detailFundingViewObject.setMonthlySalary9(ta.getMonth9()==null ? "0" : ta.getMonth9());
            detailFundingViewObject.setMonthlySalary10(ta.getMonth10()==null ? "0" : ta.getMonth10());
            detailFundingViewObject.setMonthlySalary11(ta.getMonth11()==null ? "0" : ta.getMonth11());
            detailFundingViewObject.setMonthlySalary12(ta.getMonth12()==null ? "0" : ta.getMonth12());
            Integer total = 0;


            if(curSession.getTerm().equals("春")) {
                total = (Integer.parseInt(detailFundingViewObject.getMonthlySalary3()==null?"0":detailFundingViewObject.getMonthlySalary3()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary4()==null?"0":detailFundingViewObject.getMonthlySalary4()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary5()==null?"0":detailFundingViewObject.getMonthlySalary5()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary6()==null?"0":detailFundingViewObject.getMonthlySalary6()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary7()==null?"0":detailFundingViewObject.getMonthlySalary7()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary8()==null?"0":detailFundingViewObject.getMonthlySalary8()));
            }else if(curSession.getTerm().equals("秋")){
                total = (Integer.parseInt(detailFundingViewObject.getMonthlySalary1()==null?"0":detailFundingViewObject.getMonthlySalary1()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary2()==null?"0":detailFundingViewObject.getMonthlySalary2()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary9()==null?"0":detailFundingViewObject.getMonthlySalary9()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary10()==null?"0":detailFundingViewObject.getMonthlySalary10()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary11()==null?"0":detailFundingViewObject.getMonthlySalary11()) +
                        Integer.parseInt(detailFundingViewObject.getMonthlySalary12()==null?"0":detailFundingViewObject.getMonthlySalary12()));
            }
            detailFundingViewObject.setTotal(total.toString());
            detailFundingViewObjects.add(detailFundingViewObject);
        }

    return  detailFundingViewObjects;
    }

    //转换课程经费
    public List<ClassFundingViewObject> combineClassFunding(List<ClassFundingViewObject> list) {
        if (list == null || list.size() == 0) {
            //logger.error("数据为空！");
            //return null;
            List<ClassFundingViewObject> nullObject = new ArrayList<>();
            nullObject.add(new ClassFundingViewObject());
            return nullObject;
        } else {
            for (ClassFundingViewObject listone : list) {
                Integer total =  Integer.parseInt(listone.getAssignedFunding()) + Integer.parseInt(listone.getPhdFunding()) + Integer.parseInt(listone.getBonus()) + Integer.parseInt(listone.getTravelSubsidy());
                listone.setTotal(total.toString());
                List<UTClassInstructor> utClassInstructors = utClassInstructorDao.selectByClassId(listone.getClassId());
                if (utClassInstructors == null || utClassInstructors.size() == 0) {
                    listone.setInstructorName("缺失");
                } else if (utClassInstructors.size() == 1) {
                    listone.setInstructorName(utClassInstructors.get(0).getUtInstructor().getName());
                } else {
                    for (int i = 0; i < utClassInstructors.size(); i++) {
                        for (int j = i + 1; j < utClassInstructors.size(); j++) {
                            if (utClassInstructors.get(i).getClassId().toString().equals(utClassInstructors.get(j).getClassId().toString())) {
                                String name = utClassInstructors.get(i).getUtInstructor().getName() + ',' +
                                        utClassInstructors.get(j).getUtInstructor().getName();
                                listone.setInstructorName(name);
                            }
                        }
                    }
                }
            }
            return list;
        }
    }
        /*
        if(list.size() == 1) {
            Integer total = Integer.parseInt(list.get(0).getApplyFunding()) + Integer.parseInt(list.get(0).getAssignedFunding()) + Integer.parseInt(list.get(0).getPhdFunding()) + Integer.parseInt(list.get(0).getBonus()) + Integer.parseInt(list.get(0).getTravelSubsidy());
            list.get(0).setTotal(total.toString());
        }
        for(int i=0; i<list.size(); i++){
            Integer total = Integer.parseInt(list.get(i).getApplyFunding()) + Integer.parseInt(list.get(i).getAssignedFunding()) + Integer.parseInt(list.get(i).getPhdFunding()) + Integer.parseInt(list.get(i).getBonus()) + Integer.parseInt(list.get(i).getTravelSubsidy());
            list.get(i).setTotal(total.toString());
            for(int j=i+1; j<list.size(); j++){
                if(list.get(i).getClassNumber().toString().equals(list.get(j).getClassNumber().toString())){
                    list.get(i).setInstructorName(list.get(i).getInstructorName()+','+list.get(j).getInstructorName());
                    list.remove(j);
                }
            }
        }
        */
        /*
        for(ClassFundingViewObject listone : list){
            Integer total = Integer.valueOf(listone.getApplyFunding() + listone.getAssignedFunding() + listone.getPhdFunding() + listone.getBonus() + listone.getTravelSubsidy());
            listone.setTotal(total.toString());
            list.add(listone);
        }

        return list;

    }*/

    //转换助教经费
    @Override
    public List<TaFundingViewObject> combineTaFunding(List<TaFundingViewObject> list) {
        if(list == null || list.size() ==0) {
            //logger.error("数据为空！");
            //return null;
            List<TaFundingViewObject> nullObject = new ArrayList<>();
            nullObject.add(new TaFundingViewObject());
            return nullObject;
        }
        if(list.size() == 1){
            Integer total = Integer.parseInt(list.get(0).getAssignedFunding()) + Integer.parseInt(list.get(0).getPhdFunding()) + Integer.parseInt(list.get(0).getBonus()) + Integer.parseInt(list.get(0).getTravelSubsidy());
            list.get(0).setTotal(total.toString());
            return list;
        }
        else{
            for (int i = 0; i < list.size(); i++) {
                Integer total = Integer.parseInt(list.get(i).getAssignedFunding()) + Integer.parseInt(list.get(i).getPhdFunding()) + Integer.parseInt(list.get(i).getBonus()) + Integer.parseInt(list.get(i).getTravelSubsidy());
                list.get(i).setTotal(total.toString());
                for (int j = i+1; j < list.size(); j++) {
                    if (list.get(i).getClassNbr().toString().equals(list.get(j).getClassNbr().toString()) &&
                            list.get(i).getInstrucotrName().toString().equals(list.get(j).getInstrucotrName().toString()) == false) {
                        list.remove(j);
                    }
                }
            }
            return list;
        }
    }

    //转换经费明细
    @Override
    public List<DetailFundingViewObject> combineDetailFunding(List<DetailFundingViewObject> list) {
        if(list == null || list.size() == 0){
            //logger.error("数据为空！");
            //return null;
            List<DetailFundingViewObject> nullObject = new ArrayList<>();
            nullObject.add(new DetailFundingViewObject());
            return nullObject;
        }
        if (list.size() == 1)
            return list;
        else {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i+1; j < list.size(); j++) {
                    if (list.get(i).getClassNbr().toString().equals(list.get(j).getClassNbr().toString()) &&
                            list.get(i).getInstructorName().toString().equals(list.get(j).getInstructorName()) == false) {
                        list.remove(j);
                    }
                }
            }
            return list;
        }
    }
}
