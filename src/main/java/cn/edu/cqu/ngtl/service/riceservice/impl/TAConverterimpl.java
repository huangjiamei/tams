package cn.edu.cqu.ngtl.service.riceservice.impl;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgram;
import cn.edu.cqu.ngtl.dataobject.cm.CMProgramCourse;
import cn.edu.cqu.ngtl.dataobject.tams.*;
import cn.edu.cqu.ngtl.dataobject.ut.*;
import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DepartmentFundingViewObject;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.tools.converter.StringDateConverter;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CheckBoxStatus;
import cn.edu.cqu.ngtl.viewobject.adminInfo.RelationTable;
import cn.edu.cqu.ngtl.viewobject.adminInfo.SessionFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TermManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyAssistantViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tangjing on 16-10-19.
 */
@Service
public class TAConverterimpl implements ITAConverter {

    static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    static final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ICourseInfoService courseInfoService;

    @Autowired
    private UTSessionDao sessionDao;

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Override
    public List<ClassTeacherViewObject> classInfoToViewObject(List<UTClassInformation> informationlist) {

        //没有数据的话返回一行空数据，否则表格消失
        if(informationlist == null || informationlist.size() == 0) {
            List<ClassTeacherViewObject> nullObject = new ArrayList<>(1);
            nullObject.add(new ClassTeacherViewObject());
            return nullObject;
        }

        List<ClassTeacherViewObject> viewObjects = new ArrayList<>(informationlist.size());

        for (UTClassInformation information : informationlist) {
            ClassTeacherViewObject viewObject = new ClassTeacherViewObject();

            //if(clazz.getUtInstructors() != null && clazz.getUtInstructors().size() != 0)
            viewObject.setId(information.getId());
            viewObject.setInstructorName("test");

            viewObject.setClassNumber(information.getClassNumber());

            viewObject.setDepartmentName(information.getDeptName());
            viewObject.setCourseName(information.getCourseName());
            //viewObject.setCourseHour(information.getHour());
            viewObject.setCourseCode(information.getCourseCode());
            //viewObject.setCourseCredit(information.getCredit().toString());
            viewObject.setStatus(information.getStatus());
            //if (programCourse != null) {
           // viewObject.setCourseClassification("test");
           // viewObject.setIsRequired("必修");
          //  viewObject.setProgramName("CS");
            //}
            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public ApplyViewObject classInfoToApplyObject(User user, UTClass clazz) {
        ApplyViewObject viewObject = new ApplyViewObject();

        viewObject.setTeacherName(user.getName());

        viewObject.setTeacherType(user.getType());

        viewObject.setClassNumber(clazz.getClassNumber());

        UTCourse course = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getCourse() : null;

        UTSession session = clazz.getCourseOffering() != null ? clazz.getCourseOffering().getSession() : null;

        CMProgramCourse programCourse = new CMProgramCourse();

        if (course != null) {
            viewObject.setCourseName(course.getName());

            viewObject.setCourseNumber(course.getCodeR().toString());

            viewObject.setStudyTime(course.getHour());

            viewObject.setStudyCode(course.getCredit() + "");

            programCourse = courseInfoService.getProgramCourseByCourseId(course.getId());
        }

        if (programCourse != null) {

            viewObject.setRequired((programCourse.getRequired() == 1) ? "必修" : "选修");

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

        viewObject.setAssisstantNumber(1 + "");

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
        application.setEduBackground(form.getEduBackground());

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

    @Override
    public List<TaInfoViewObject> taCombineDetailInfo(List<TAMSTa> allTa) {

        List<TaInfoViewObject> viewObjects = new ArrayList<>(allTa.size());

        for(TAMSTa ta : allTa) {
            TaInfoViewObject viewObject = new TaInfoViewObject();
            UTCourse course = null;
            List<UTInstructor> instructors = null;
            if(ta.getTaClass() != null) {
                viewObject.setClassNumber(ta.getTaClass().getClassNumber());
                instructors = ta.getTaClass().getUtInstructors();
                StringBuilder sb = new StringBuilder();
                if(instructors != null)
                    for(UTInstructor instructor : instructors)
                        sb.append(instructor.getName() + "，");
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

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");
            viewObject.setAppraise("缺失");
            viewObject.setVitality("缺失");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

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

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");
            viewObject.setPayDay("暂未设置");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

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

            //暂时缺失的属性
            viewObject.setTaMasterMajorName("缺失");
            viewObject.setContactPhone("玖洞玖洞玖扒洞");
            viewObject.setAdvisorName("缺失");

            viewObjects.add(viewObject);
        }

        return viewObjects;
    }

    @Override
    public List<SessionFundingViewObject> sessionFundingToViewObject(List<TAMSDeptFunding> allFundingBySession) {
        List<SessionFundingViewObject> viewObjects = new ArrayList<>(allFundingBySession.size());

        for(TAMSDeptFunding deptFunding : allFundingBySession) {
            SessionFundingViewObject viewObject = new SessionFundingViewObject();
            viewObject.setBonus(deptFunding.getBonus());
            viewObject.setApplyFunding(deptFunding.getApplyFunding());
            viewObject.setPhdFunding(deptFunding.getPhdFunding());
            viewObject.setActualFunding(deptFunding.getActualFunding());
            viewObject.setPlanFunding(deptFunding.getPlanFunding());
            Integer total = Integer.valueOf(deptFunding.getBonus()) + Integer.valueOf(deptFunding.getActualFunding()) +
                    Integer.valueOf(deptFunding.getApplyFunding()) + Integer.valueOf(deptFunding.getPhdFunding()) +
                    Integer.valueOf(deptFunding.getPlanFunding());
            viewObject.setTotal(total.toString());
            if(deptFunding.getSession() != null)
                viewObject.setSessionName(deptFunding.getSession().getYear() + "年" +
                        deptFunding.getSession().getTerm() + "季");

            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    @Override
    public List<DepartmentFundingViewObject> departmentFundingToViewObject(List<TAMSDeptFunding> allFundingBySession) {
        List<DepartmentFundingViewObject> viewObjects = new ArrayList<>(allFundingBySession.size());

        for(TAMSDeptFunding deptFunding : allFundingBySession){
            DepartmentFundingViewObject viewObject = new DepartmentFundingViewObject();
            viewObject.setBonus(deptFunding.getBonus());
            viewObject.setApplyFunding(deptFunding.getApplyFunding());
            viewObject.setPhdFunding(deptFunding.getPhdFunding());
            viewObject.setActualFunding(deptFunding.getActualFunding());
            viewObject.setPlanFunding(deptFunding.getPlanFunding());
            viewObject.setDepartment(deptFunding.getDepartment().getName());
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

    @Override
    public RelationTable workflowStatusRtoJson(List<TAMSWorkflowStatusR> workflowStatusRelations) {
        List<TAMSWorkflowStatus> allStatus = workflowStatusDao.selectAll();
        //如果连header都没有返回完全的空
        if(allStatus == null)
            return null;

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
}
