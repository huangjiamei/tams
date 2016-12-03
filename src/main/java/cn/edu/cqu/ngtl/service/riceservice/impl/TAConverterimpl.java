package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.StuIdClassIdPair;
import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.cm.impl.CMProgramCourseDaoJpa;
import cn.edu.cqu.ngtl.dao.tams.TAMSActivityDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSTaCategoryDao;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dao.ut.UTClassInstructorDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dao.ut.impl.UTInstructorDaoJpa;
import cn.edu.cqu.ngtl.dao.ut.impl.UTStudentDaoJpa;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.userservice.IUserInfoService;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import cn.edu.cqu.ngtl.viewobject.adminInfo.*;
import cn.edu.cqu.ngtl.viewobject.classinfo.*;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.AppraisalDetailViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.WorkBenchViewObject;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LoggerPatternConverter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAConverterimpl implements ITAConverter {

    static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    static final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = Logger.getRootLogger();

    @Autowired
    private ICourseInfoService courseInfoService;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Autowired
    private TAMSActivityDao activityDao;

    @Autowired
    private TAMSTaCategoryDao taCategoryDao;

    @Autowired
    private TAMSActivityDao tamsActivityDao;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UTClassInstructorDao classInstructorDao;

    @Override
    public List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informationlist) {

        List<UTClassInstructor> utClassInstructors = classInstructorDao.getAllClassInstructor();
        Map classInstructorMap = new HashMap();
        for(UTClassInstructor utClassInstructor : utClassInstructors){
            if(classInstructorMap.get(utClassInstructor.getClassId())!=null)
                classInstructorMap.put(utClassInstructor.getClassId(),utClassInstructor.getUtInstructor().getName()+" "+classInstructorMap.get(utClassInstructor.getClassId()));
            else
                classInstructorMap.put(utClassInstructor.getClassId(),utClassInstructor.getUtInstructor().getName());
        }
        //没有数据的话返回一行空数据，否则表格消失
        if(informationlist == null || informationlist.size() == 0) {
            List<ClassTeacherViewObject> nullObject = new ArrayList<>(1);
            nullObject.add(new ClassTeacherViewObject());
            return nullObject;
        }

        List<ClassTeacherViewObject> viewObjects = new ArrayList<>(informationlist.size());

        for (UTClassInformation information : informationlist) {
            ClassTeacherViewObject viewObject = new ClassTeacherViewObject();

            viewObject.setId(information.getId());
            viewObject.setInstructorName(information.getInstructorName());
            viewObject.setClassNumber(information.getClassNumber());
            viewObject.setDepartmentName(information.getDeptName());
            viewObject.setCourseName(information.getCourseName());
            viewObject.setCourseCode(information.getCourseCode());
            viewObject.setStatus(information.getStatusName());
            viewObject.setInstructorName((String)classInstructorMap.get(information.getId()));

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public ClassTaApplyViewObject classInfoToApplyObject(User user, UTClass clazz) {
        ClassTaApplyViewObject viewObject = new ClassTaApplyViewObject();

        viewObject.setTeacherName(user.getName());

        viewObject.setTeacherType(user.getType());

        viewObject.setClassNumber(clazz.getClassNumber());

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if (course != null) {
            viewObject.setCourseName(course.getName());

            viewObject.setCourseNumber(course.getCodeR());

            viewObject.setStudyTime(course.getHour());

            viewObject.setCredit(course.getCredit() + "");

            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if (programCourse != null) {

            viewObject.setIsRequired((programCourse.getRequired() == 1) ? "必修" : "选修");

            if (programCourse.getClassification() != null)
                viewObject.setCourseType(programCourse.getClassification().getName());

            viewObject.setProgramName(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        if (session != null) {

            Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;

            viewObject.setGrade((Integer.parseInt(session.getYear()) - year) + "");

        }
        //--------------------------目前没有值的加了默认---------------------------------------
        viewObject.setStudentNumber(100 + "");

        viewObject.setAssistantNumber(1 + "");

        return viewObject;
    }

    public ApplyAssistantViewObject applyAssistantToTableViewObject(UTStudent student, UTClass clazz) {

        ApplyAssistantViewObject viewObject = new ApplyAssistantViewObject();

        if (student != null) {

            viewObject.setUsername(student.getName());
            viewObject.setStudentId(student.getId());
            viewObject.setUg_Major(student.getProgram() != null ?
                    student.getProgram().getName() : null);

            /** 需要修改 */
            viewObject.setG_Major("挖掘机");
        }

        if (clazz != null) {
            viewObject.setClassId(clazz.getId());

            StringBuilder sb = new StringBuilder();
            if (clazz.getUtInstructors() != null) {
                for (UTInstructor instructor : clazz.getUtInstructors()) {
                    sb.append(instructor.getName() + ",\n");
                }
            }
            viewObject.setApplyTeacher(sb.toString());
        }

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if (course != null) {
            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());

            viewObject.setClassHour(course.getHour());
        }

        if (programCourse != null) {
            viewObject.setApplyCourseType(programCourse.getProgram() != null ?
                    programCourse.getProgram().getName() : null);
        }

        return viewObject;
    }

    @Override
    public ClassDetailInfoViewObject classInfoToViewObject(UTClass clazz) {
        ClassDetailInfoViewObject classDetailInfoViewObject = new ClassDetailInfoViewObject();

        UTCourse course = new UTCourse();

        if (clazz != null) {
            classDetailInfoViewObject.setClassNumber(clazz.getClassNumber());
            course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

            //classInstructor为空的数据库，无法查询
            StringBuilder sb = new StringBuilder();
            Integer countTeacherNum = 0;
            if (clazz.getUtInstructors() != null) {

                for (UTInstructor instructor : clazz.getUtInstructors()) {
                    countTeacherNum++;
                    sb.append(instructor.getName() + ",\n");
                }
            }
            classDetailInfoViewObject.setInstructor(sb.toString());
            classDetailInfoViewObject.setInstructorNumber(countTeacherNum.toString());
        }

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;
        CMProgramCourse programCourse = new CMProgramCourse();
        CMProgram program = new CMProgram();

        if (course != null) {
            classDetailInfoViewObject.setCourseName(course.getName());
            classDetailInfoViewObject.setCourseId(course.getCodeR());
            classDetailInfoViewObject.setClassHour(course.getHour());   //目前为空
            classDetailInfoViewObject.setCredit(course.getCredit() + "");
            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if (programCourse != null) {

            classDetailInfoViewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");
            if (programCourse.getClassification() != null)
                classDetailInfoViewObject.setCourseClassification(programCourse.getClassification().getName());


            program = programCourse.getProgram() != null ? programCourse.getProgram() : null;


            UTDepartment department = new UTDepartment();
            if (session != null) {

                Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;


                if (program != null) {

                    classDetailInfoViewObject.setDepartmentAndGrade((Integer.parseInt(session.getYear()) - year) + "/" + program.getName());
                    department = program.getDepartment() != null ? program.getDepartment() : null;
                }
                if (department != null) {
                    classDetailInfoViewObject.setCourseDepartment(department.getName());
                }

            }
        }


        return classDetailInfoViewObject;
    }

    @Override
    public List<ClassTeacherViewObject> classToViewObject(List<UTClass> informationlist) {
        if(informationlist == null || informationlist.size() == 0)
            return null;
        List<ClassTeacherViewObject> viewObjects = new ArrayList<>(informationlist.size());

        for (UTClass information : informationlist) {
            ClassTeacherViewObject viewObject = new ClassTeacherViewObject();

            //if(clazz.getUtInstructors() != null && clazz.getUtInstructors().size() != 0)
            viewObject.setId(information.getId());
            viewObject.setInstructorName("test");
            viewObject.setStudentCounts(information.getMinPerWeek().toString());

            viewObject.setClassNumber(information.getClassNumber());

            UTCourse course = information.getCourseOffering() != null ? information.getCourseOffering().getCourse() : null;
            information.getCourseOffering().getSession().getYear();
            if(course != null) {
                viewObject.setSessionYear(information.getCourseOffering().getSession() != null ?
                        information.getCourseOffering().getSession().getYear() : null);

                viewObject.setDepartmentName(course.getDepartment() != null ? course.getDepartment().getName() : null);
                viewObject.setCourseName(course.getName());
                viewObject.setCourseHour(course.getHour());
                viewObject.setCourseCode(course.getCodeR());
                viewObject.setCourseCredit(course.getCredit().toString());
            }

            CMProgramCourse programCourse = information.getProgramCourse();
            if (programCourse != null) {
                viewObject.setCourseClassification(programCourse.getClassification() != null ? programCourse.getClassification().getName() : null);
                viewObject.setIsRequired(programCourse.getRequired() == 1 ? "必修" : "选修");
                viewObject.setProgramName(programCourse.getProgram() != null ? programCourse.getProgram().getName() : null);
            }
            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public TAMSTaApplication submitInfoToTaApplication(ClassInfoForm form) {
        TAMSTaApplication application = new TAMSTaApplication();

        application.setApplicationId(form.getApplyAssistantViewObject().getStudentId());
        application.setApplicationClassId(form.getApplyAssistantViewObject().getClassId().toString());
        application.setApplicationTime(new StringDateConverter().convertToEntityAttribute(new Date()));
        application.setNote(form.getApplyReason());

        return application;
    }

    @Override
    public List<TermManagerViewObject> termInfoToViewObject(List<UTSession> sessions) {

        List<TermManagerViewObject> viewObjects = new ArrayList<>(sessions.size());

        for(UTSession session : sessions) {
            TermManagerViewObject viewObject = new TermManagerViewObject();
            viewObject.setTermName(session.getYear() + "年" + session.getTerm() + "季");
            viewObject.setEndDate(session.getEndDate());
            viewObject.setStartDate(session.getBeginDate());
            viewObject.setActive(session.getActive());

            //// FIXME: 16-10-27 日后需要加上预算信息
            viewObject.setBudget(100000);

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public UTSession termToDataObject(TermManagerViewObject term) throws ParseException {
        UTSession session = sessionDao.selectByYearAndTerm(term.getTermYear(),
                term.getTermTerm().substring(0,1)); //去掉"季度"的'度'

        if(session == null) {
            session = new UTSession();
            //新建的预处理
            session.setYear(term.getTermYear());
            session.setTerm(term.getTermTerm().substring(0,1));  //去掉"季度"的'度'
        }

        //新建和编辑的公共部分
        session.setActive(term.getActive());
        session.setBeginDate(
                outputFormat.format(
                        inputFormat.parse(
                                term.getStartDate()
                        )
                )
        );
        session.setEndDate(
                outputFormat.format(
                        inputFormat.parse(
                                term.getEndDate()
                        )
                )
        );

        return session;
    }


    //全校所有助教界面
    @Override
    public List<TaInfoViewObject> taCombineDetailInfo(List<TAMSTa> allTa) {
        if(allTa == null || allTa.size() == 0) {
            logger.error("数据为空！");
            return null;
        }
        List<TaInfoViewObject> viewObjects = new ArrayList<>(allTa.size());

        for(TAMSTa ta : allTa) {
            TaInfoViewObject viewObject = new TaInfoViewObject();
            viewObject.setTaId(ta.getTaId());
            viewObject.setClassid(ta.getTaClassId());
            viewObject.setApplicationReason(ta.getApplicationNote());
            UTCourse course = null;
            List<UTInstructor> instructors = null;
            if(ta.getTaClass() != null) {
                viewObject.setClassNumber(ta.getTaClass().getClassNumber());
                instructors = ta.getTaClass().getUtInstructors();
                StringBuilder sb = new StringBuilder();
                if(instructors != null)
                    for(UTInstructor instructor : instructors)
                        sb.append(instructor.getName() + "  ");
                viewObject.setInstructorName(sb.toString());
                if (ta.getTaClass().getCourseOffering() != null) {
                    course = ta.getTaClass().getCourseOffering().getCourse();
                    if(course != null) {
                        viewObject.setCourseName(course.getName());
                        viewObject.setCourseCode(course.getCodeR());
                    }
                }
            }
            UTStudent taStu = ta.getTa();
            if(taStu != null) {
                viewObject.setTaName(taStu.getName());
                viewObject.setTaIDNumber(taStu.getId());
                viewObject.setTaGender(taStu.getGender());
                viewObject.setTaBachelorMajorName(taStu.getProgram() != null ? taStu.getProgram().getName() : null);
            }

            viewObject.setId(ta.getId());
            viewObject.setStatus(ta.getStatus());
            viewObject.setAppraise(ta.getOutStandingTaWorkflowStatus() != null ? ta.getOutStandingTaWorkflowStatus().getWorkflowStatus() : "缺失");

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");
            viewObject.setTeacherAppraise("合格");
            viewObject.setStuAppraise("优秀");
            viewObject.setVitality("缺失");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    //我的助教界面助教列表
    @Override
    public List<MyTaViewObject> myTaCombinePayDay(List<TAMSTa> allTaFilteredByUid) {

        List<MyTaViewObject> viewObjects = new ArrayList<>(allTaFilteredByUid.size());

        for(TAMSTa ta : allTaFilteredByUid) {
            MyTaViewObject viewObject = new MyTaViewObject();
            UTStudent taStu = ta.getTa();
            if(taStu != null) {
                viewObject.setTaName(taStu.getName());
                viewObject.setTaIdNumber(taStu.getId());
                viewObject.setTaGender(taStu.getGender());
                viewObject.setTaBachelorMajorName(taStu.getProgram() != null ? taStu.getProgram().getName() : null);
            }

            viewObject.setStatus(ta.getStatus());
            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");
            viewObject.setPayDay("暂未设置");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    //工作台界面相关
    @Override
    public List<WorkBenchViewObject> taCombineWorkbench(List<WorkBenchViewObject> list){

        //List<WorkBenchViewObject> viewObject = new ArrayList<>();
        //WorkBenchViewObject workbenchviewobject = new WorkBenchViewObject();
        for(int i=0; i<list.size(); i++){
            for(int j=i+1; j<list.size(); j++){
                //System.out.println(list.get(i).getClassNbr());
                //System.out.println(list.get(j).getClassNbr());
                if(list.get(i).getClassNbr().toString().equals(list.get(j).getClassNbr().toString())) {
                    list.get(i).setTeacher(list.get(i).getTeacher() + ',' + list.get(j).getTeacher());
                    list.remove(j);
                }
                //viewObject.set(i,list.get(i));
                //break;
            }
            //viewObject.set(i,list.get(i));
        }
        return list;
    }

    //FIXME 迁移后删掉
    @Override
    public List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> myTaCombinePayDayClass(List<TAMSTa> allTaFilteredByUid) {

        List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> viewObjects = new ArrayList<>(allTaFilteredByUid.size());

        for(TAMSTa ta : allTaFilteredByUid) {
            cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject viewObject = new cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject();
            UTStudent taStu = ta.getTa();
            if(taStu != null) {
                viewObject.setTaName(taStu.getName());
                viewObject.setTaIdNumber(taStu.getId());
                viewObject.setTaGender(taStu.getGender());
                viewObject.setTaBachelorMajorName(taStu.getProgram() != null ? taStu.getProgram().getName() : null);
            }
            viewObject.setStatus(ta.getStatus());
            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");
            viewObject.setPayDay("暂未设置");
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    //我的助教界面申请人助教列表
    @Override
    public List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> applicationToViewObjectClass(List<TAMSTaApplication> allApplicationFilterByUid) {
        if(allApplicationFilterByUid == null)
            return null;
        List<cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject> viewObjects = new ArrayList<>(allApplicationFilterByUid.size());

        for(TAMSTaApplication application : allApplicationFilterByUid) {
            cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject viewObject = new cn.edu.cqu.ngtl.viewobject.classinfo.MyTaViewObject();
            UTStudent applicant = application.getApplicant();
            if(applicant != null) {
                viewObject.setTaName(applicant.getName());
                viewObject.setTaIdNumber(applicant.getId());
                viewObject.setTaGender(applicant.getGender());
                viewObject.setTaBachelorMajorName(applicant.getProgram() != null ? applicant.getProgram().getName() : null);
            }

            viewObject.setApplicationClassId(application.getApplicationClassId());

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }


    //我的助教界面申请人助教列表
    @Override
    public List<MyTaViewObject> applicationToViewObject(List<TAMSTaApplication> allApplicationFilterByUid) {
        if(allApplicationFilterByUid == null)
            return null;
        List<MyTaViewObject> viewObjects = new ArrayList<>(allApplicationFilterByUid.size());

        for(TAMSTaApplication application : allApplicationFilterByUid) {
            MyTaViewObject viewObject = new MyTaViewObject();
            UTStudent applicant = application.getApplicant();
            if(applicant != null) {
                viewObject.setTaName(applicant.getName());
                viewObject.setTaIdNumber(applicant.getId());
                viewObject.setTaGender(applicant.getGender());
                viewObject.setTaBachelorMajorName(applicant.getProgram() != null ? applicant.getProgram().getName() : null);
            }

            viewObject.setApplicationClassId(application.getApplicationClassId());

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    //添加申请人对话框，显示查询出符合条件的候选人列表
    @Override
    public List<MyTaViewObject> studentInfoToMyTaViewObject(List<UTStudent> studentList) {
        if(studentList == null || studentList.size() == 0) {
            logger.error("数据为空！");
            return null;
        }
        List<MyTaViewObject> viewObjects = new ArrayList<>();
        for(UTStudent listone : studentList){
            MyTaViewObject viewObject = new MyTaViewObject();
            viewObject.setTaName(listone.getName());
            viewObject.setTaIdNumber(listone.getId());
            viewObject.setTaGender(listone.getGender());
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            //点击查看详细信息会用到的
            CMProgram program = listone.getProgram();
            if(program == null)
                viewObject.setTaBachelorMajorName("缺失");
            else
                viewObject.setTaBachelorMajorName(listone.getProgram().getName().toString());
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setAdvisorName("缺失");
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    //添加申请人点击确定。将MyTaViewObject对象转化为TAMSTaApplication对象
    @Override
    public TAMSTaApplication TaViewObjectToTaApplication(MyTaViewObject application, String classid){
        TAMSTaApplication tamsTaApplication = new TAMSTaApplication();
        tamsTaApplication.setApplicationClassId(classid);
        tamsTaApplication.setApplicationId(application.getTaIdNumber());
        //tamsTaApplication.setApplicationStatus("1");
        //tamsTaApplication.setApplicationTime(new StringDateConverter().convertToEntityAttribute(new Date()));
        return tamsTaApplication;
    }

    @Override
    public List<ClassFundingViewObject> classFundingToViewObject(List<TAMSClassFunding> allFundingByClass) {
        List<ClassFundingViewObject> viewObjects = new ArrayList<>();

        if(allFundingByClass == null||allFundingByClass.size() == 0){
            viewObjects.add(new ClassFundingViewObject());
            return viewObjects;
        }
        for (TAMSClassFunding classFunding : allFundingByClass) {
            ClassFundingViewObject viewObject = new ClassFundingViewObject();
            viewObject.setCourseName(classFunding.getClassInformation().getCourseName());
            viewObject.setCourseCode(classFunding.getClassInformation().getCourseCode());
            viewObject.setDepartment(classFunding.getClassInformation().getDeptName());
            viewObject.setClassNumber(classFunding.getClassInformation().getClassNumber());
            viewObject.setInstructorName("test");
            viewObject.setApplyFunding(classFunding.getApplyFunding());
            viewObject.setAssignedFunding(classFunding.getAssignedFunding());
            viewObject.setPhdFunding(classFunding.getPhdFunding());
            viewObject.setBonus(classFunding.getBonus());
            viewObject.setTravelSubsidy(classFunding.getTravelSubsidy());
            Integer total = Integer.valueOf(classFunding.getAssignedFunding()) +
                    Integer.valueOf(classFunding.getApplyFunding())
                    + Integer.valueOf(classFunding.getPhdFunding())
                    + Integer.valueOf(classFunding.getBonus())
                    + Integer.valueOf(classFunding.getTravelSubsidy());
            viewObject.setTotal(total.toString());
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    //学院经费
    @Override
    public List<DepartmentFundingViewObject> departmentFundingToViewObject(List<TAMSDeptFunding> allFundingBySession) {
        List<DepartmentFundingViewObject> viewObjects = new ArrayList<>();
        if(allFundingBySession == null || allFundingBySession.size() == 0) {
            viewObjects.add(new DepartmentFundingViewObject());
            return viewObjects;
        }
        for(TAMSDeptFunding deptFunding : allFundingBySession){
            DepartmentFundingViewObject viewObject = new DepartmentFundingViewObject();
            viewObject.setBonus(deptFunding.getBonus());
            viewObject.setApplyFunding(deptFunding.getApplyFunding());
            viewObject.setPhdFunding(deptFunding.getPhdFunding());
            viewObject.setActualFunding(deptFunding.getActualFunding());
            viewObject.setPlanFunding(deptFunding.getPlanFunding());
            viewObject.setDepartment(deptFunding.getDepartment().getName());
            viewObject.setTrafficFunding(deptFunding.getTravelSubsidy());
            Integer total = Integer.valueOf(deptFunding.getBonus()) + Integer.valueOf(deptFunding.getActualFunding()) +
                    Integer.valueOf(deptFunding.getApplyFunding()) + Integer.valueOf(deptFunding.getPhdFunding()) +
                    Integer.valueOf(deptFunding.getPlanFunding());
            viewObject.setTotal(total.toString());
            if (deptFunding.getSession() != null ){
                viewObject.setSessionName(deptFunding.getSession().getYear() + "年" +
                        deptFunding.getSession().getTerm() + "季");
            }
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    //学校经费
    @Override
    public List<SessionFundingViewObject> sessionFundingToViewObject(List<TAMSUniversityFunding> allFundingBySession) {
        List<SessionFundingViewObject> viewObjects = new ArrayList<>(allFundingBySession.size());

        for(TAMSUniversityFunding universityFunding : allFundingBySession) {
            SessionFundingViewObject viewObject = new SessionFundingViewObject();
            viewObject.setBonus(universityFunding.getBonus());
            viewObject.setApplyFunding(universityFunding.getApplyFunding());
            viewObject.setPhdFunding(universityFunding.getPhdFunding());
            viewObject.setActualFunding(universityFunding.getActualFunding());
            viewObject.setPlanFunding(universityFunding.getPlanFunding());
            viewObject.setTrafficFunding(universityFunding.getTravelSubsidy());
            Integer total = Integer.valueOf(universityFunding.getTravelSubsidy()) + Integer.valueOf(universityFunding.getBonus()) + Integer.valueOf(universityFunding.getActualFunding()) +
                    Integer.valueOf(universityFunding.getApplyFunding()) + Integer.valueOf(universityFunding.getPhdFunding()) +
                    Integer.valueOf(universityFunding.getPlanFunding());
            viewObject.setTotal(total.toString());
            if(universityFunding.getUtSession() != null)
                viewObject.setSessionName(universityFunding.getUtSession().getYear() + "年" +
                        universityFunding.getUtSession().getTerm() + "季");

            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    @Override
    public RelationTable workflowStatusRtoJson(String functionId, List<TAMSWorkflowStatusR> workflowStatusRelations) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectByFunctionId(functionId);
        //如果连header都没有返回完全的空
        if(allStatus == null) {
            RelationTable rt = new RelationTable("default");
            return rt;
        }

        List<String> headers = new ArrayList<>(allStatus.size());
        for(TAMSWorkflowStatus status : allStatus) {
            headers.add(status.getWorkflowStatus());
        }

        CheckBoxStatus[][] matrix = new CheckBoxStatus[allStatus.size()][allStatus.size()];

        int length = allStatus.size();
        for(int i = 0 ; i < length; i++)
            for(int j = 0 ; j <length; j++)
                matrix[i][j] = new CheckBoxStatus();
        //如果有header没数据则返回默认的全空
        if(workflowStatusRelations == null || workflowStatusRelations.size() == 0) {
            RelationTable rt = new RelationTable();
            rt.setHeader(headers);
            rt.setData(matrix);

            return rt;
        }

        for(TAMSWorkflowStatusR workflowStatusR : workflowStatusRelations) {
            int i = allStatus.indexOf(workflowStatusR.getStatus1());
            int j = allStatus.indexOf(workflowStatusR.getStatus2());
            matrix[i][j].setChecked(true);
        }
        RelationTable rt = new RelationTable();
        rt.setHeader(headers);
        rt.setData(matrix);

        return rt;
    }

    @Override
    public List<String> extractIdsFromTaInfo(List<TaInfoViewObject> checkedlist) {
        List<String> ids = new ArrayList<>();

        for(TaInfoViewObject per : checkedlist) {
            ids.add(per.getId());
        }

        return ids;
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
    public List<StuIdClassIdPair> extractIdsFromApplication(List<MyTaViewObject> checkedList) {
        List<StuIdClassIdPair> pairs = new ArrayList<>(checkedList.size());

        for(MyTaViewObject per : checkedList) {
            pairs.add(new StuIdClassIdPair(per.getTaIdNumber(), per.getApplicationClassId()));
        }

        return pairs;
    }

    @Override
    public List<TeachCalendarViewObject> TeachCalendarToViewObject(List<TAMSTeachCalendar> calendars, boolean needCount) {
        if(calendars == null || calendars.size() == 0)
            return null;

        List<TeachCalendarViewObject> viewObjects = new ArrayList<>(calendars.size());

        for(TAMSTeachCalendar calendar : calendars) {
            TeachCalendarViewObject viewObject = new TeachCalendarViewObject();

            /** 不确定是否应该用id作为编号 **/
            viewObject.setCode(calendar.getId());
            viewObject.setTheme(calendar.getTheme());
            viewObject.setDescription(calendar.getDescription());
            viewObject.setElapsedTime(calendar.getElapsedTime());
            viewObject.setStartTime(calendar.getStartTime());
            viewObject.setEndTime(calendar.getEndTime());
            viewObject.setTaTask(calendar.getTaTask());
            if(needCount) {
                List temp = activityDao.selectAllByCalendarId(calendar.getId());
                viewObject.setTaTaskTimes(temp != null ? String.valueOf(temp.size()) : "0");
                Integer hourlyWage;
                String budget;
                try {
                    hourlyWage = Integer.parseInt(taCategoryDao.selectOneByName("硕士").getHourlyWage());
                    budget = String.valueOf(Integer.parseInt(calendar.getElapsedTime()) * hourlyWage);
                }
                catch (NumberFormatException e) { //格式转换异常
                    budget = "数据异常，请联系管理员";
                }
                catch (RuntimeException e) { //数据库访问异常
                    budget = "数据异常，请联系管理员";
                }
                viewObject.setBudget(budget);
            }

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public String countCalendarTotalElapsedTime(List<TeachCalendarViewObject> allCalendar) {
        Integer count = 0;
        if(allCalendar == null || allCalendar.size() ==0)
            return count.toString();
        else
            for(TeachCalendarViewObject calendar : allCalendar) {
                try {
                    count += Integer.valueOf(calendar.getElapsedTime());
                }
                catch (NumberFormatException e) {
                    count += 0;
                }
                finally {
                    // do nothing
                }
            }
        return count.toString();
    }

    @Override
    public String countCalendarTotalBudget(List<TeachCalendarViewObject> allCalendar) {
        Integer count = 0;
        NumberFormat nf = new DecimalFormat(",###.00元");
        if(allCalendar == null || allCalendar.size() ==0)
            return count.toString();
        else
            for(TeachCalendarViewObject calendar : allCalendar) {
                try {
                    Integer budget = Integer.valueOf(calendar.getBudget());
                    count += budget;

                    calendar.setBudget(nf.format(budget));
                }
                catch (NumberFormatException e) {
                    count += 0;
                }
                finally {
                    // do nothing
                }
            }
        return nf.format(count);
    }

    @Override
    public List<TeachCalendarViewObject> activitiesToViewObject(List<TAMSTeachCalendar> calendarsContainActivities) {
        List<TeachCalendarViewObject> readyContainActivities = this.TeachCalendarToViewObject(calendarsContainActivities, false);
        if(readyContainActivities == null || readyContainActivities.size() == 0)
            return null;

        for(int i = 0; i < readyContainActivities.size(); i ++) {
            List<TAMSActivity> activities = calendarsContainActivities.get(i).getActivityList();
            List<ActivityViewObject> activityViewObjects = new ArrayList<>(activities.size());
            for(TAMSActivity per : activities) {
                ActivityViewObject viewObject = new ActivityViewObject();
                viewObject.setDescription(per.getDescription());
                viewObject.setCreateTime(per.getCreateTime());
                viewObject.setLastUpdateTime(per.getLastUpdateTime());

                activityViewObjects.add(viewObject);
            }
            readyContainActivities.get(i).setActivityViewObjects(activityViewObjects);
        }

        return readyContainActivities;
    }

    @Override
    public ClassTaApplyViewObject instructorAndClassInfoToViewObject(User instructor, UTClass classInfo) {
        ClassTaApplyViewObject viewObject = new ClassTaApplyViewObject();
        if(instructor != null) {
            viewObject.setTeacherName(instructor.getName());
            viewObject.setTeacherType(instructor.getCode());
        }
        if(classInfo != null) {
            UTCourse course = classInfo.getCourseOffering() != null ? classInfo.getCourseOffering().getCourse() : null;
            viewObject.setClassNumber(classInfo.getClassNumber());
            viewObject.setStudentNumber(classInfo.getMinPerWeek().toString());
            if(course != null) {
                UTSession session = classInfo.getCourseOffering().getSession();
                viewObject.setCourseName(course.getName());
                viewObject.setCourseNumber(course.getCodeR());

                CMProgramCourse programCourse = new CMProgramCourseDaoJpa().selectByCourseId(course.getId());
                if(programCourse != null) {
                    viewObject.setCourseType(programCourse.getClassification() != null ? programCourse.getClassification().getName() : null);
                    viewObject.setIsRequired(programCourse.getRequired() == 1 ? "必修" : "选修");
                    CMProgram program = programCourse.getProgram();
                    if(program != null) {
                        viewObject.setProgramName(program.getName());
                        //viewObject.setCredit(program.getCredit().toString());
                        if(session != null) {
                            Integer year = (Integer.parseInt(programCourse.getSemester()) + 1) / 2;
                            Integer grade = Integer.parseInt(session.getYear()) - year;
                            viewObject.setGrade(grade.toString());
                        }
                    }

                }
            }
        }
        return viewObject;
    }


    @Override
    public List<AppraisalDetailViewObject> teachCalendarToAppraisalViewObject(List<TAMSTeachCalendar> teachCalendars){
        List<AppraisalDetailViewObject> result = new ArrayList<>();
        if(teachCalendars!=null) {
            for (TAMSTeachCalendar tamsTeachCalendar : teachCalendars) {
                AppraisalDetailViewObject appraisalDetailViewObject = new AppraisalDetailViewObject();
                appraisalDetailViewObject.setCalendarName(tamsTeachCalendar.getTheme());
                List<TAMSActivity> tamsActivity = tamsActivityDao.selectAllByCalendarId(tamsTeachCalendar.getId());
                appraisalDetailViewObject.setActivityNumber(tamsActivity==null?"0":String.valueOf(tamsActivity.size()));
                /**
                 * 缺失数据填入固定值
                 */
                appraisalDetailViewObject.setCompletion("80%");
                appraisalDetailViewObject.setEngagement("99%");
                appraisalDetailViewObject.setGrade("95");
                result.add(appraisalDetailViewObject);
            }
        }else{
            AppraisalDetailViewObject appraisalDetailViewObject = new AppraisalDetailViewObject();
            appraisalDetailViewObject.setCalendarName("");
            appraisalDetailViewObject.setActivityNumber("");
            appraisalDetailViewObject.setCompletion("");
            appraisalDetailViewObject.setEngagement("");
            appraisalDetailViewObject.setGrade("");
            result.add(appraisalDetailViewObject);
        }
        return result;
    }

    @Override
    public List<FileViewObject> attachmentsToFileViewObject(List<TAMSAttachments> attachments) {
        if(attachments == null || attachments.size() == 0)
            return null;

        List<FileViewObject> viewObjects = new ArrayList<>(attachments.size());
        for(TAMSAttachments attachment : attachments) {
            FileViewObject viewObject = new FileViewObject();
            viewObject.setId(attachment.getId());
            viewObject.setName(attachment.getFileName());
            try {
                long size = Integer.parseInt(attachment.getFileSize());
                viewObject.setSize(size);
            }
            catch (NumberFormatException e) {

            }
            String authorId = attachment.getAuthorId();
            String name;
            if(userInfoService.isInstructor(authorId)) {
                name = new UTInstructorDaoJpa().getInstructorByIdWithoutCache(authorId).getName();
            }
            else if(userInfoService.isStudent(authorId)) {
                name = new UTStudentDaoJpa().getUTStudentById(authorId).getName();
            }
            else
                name = "缺失";
            viewObject.setUploaderName(name);
            viewObject.setUploadDate(attachment.getCreateTime());
            viewObject.setDownloadTimes(attachment.getDownloadTimes() != null ? attachment.getDownloadTimes() : "0");

            viewObjects.add(viewObject);
        }
        return viewObjects;
    }
}
