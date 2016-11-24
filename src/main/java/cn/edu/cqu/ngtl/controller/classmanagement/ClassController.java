package cn.edu.cqu.ngtl.controller.classmanagement;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSTeachCalendar;
import cn.edu.cqu.ngtl.dataobject.ut.UTClass;
import cn.edu.cqu.ngtl.form.classmanagement.ClassInfoForm;
import cn.edu.cqu.ngtl.service.classservice.IClassInfoService;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import cn.edu.cqu.ngtl.service.taservice.ITAService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassDetailInfoViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.service.impl.CollectionControllerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjing on 16-10-20.
 * 课程管理相关的view及function
 */
@Controller
@RequestMapping("/class")
public class ClassController extends UifControllerBase {
    
    @Autowired
    private IClassInfoService classInfoService;

    @Autowired
    private ITAConverter taConverter;

    @Autowired
    private ITAService taService;

    @Autowired
    private ExcelService excelService;

    @RequestMapping(params = "methodToCall=logout")
    public ModelAndView logout(@ModelAttribute("KualiForm") UifFormBase form) throws Exception {
        String redirctURL = ConfigContext.getCurrentContextConfig().getProperty(KRADConstants.APPLICATION_URL_KEY) + "/portal/home?methodToCall=logout&viewId=PortalView";
        return this.performRedirect(form, redirctURL);
    }

    /**
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getClassListPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getClassListPage")
    public ModelAndView getClassListPage(@ModelAttribute("KualiForm") UifFormBase form,
                                         HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUid(uId)
                )
        );
        return this.getModelAndView(infoForm, "pageClassList");
    }

    /**
     * pageId限定了只接受来自pageClassList的请求
     * 从classlist跳转到某个class对应的classInfoPage
     *
     * @param form
     * @return
     */
    @RequestMapping(params = {"methodToCall=getClassInfoPage"})
    public ModelAndView getClassInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;
        try {
            /**
             * param in
             */
            CollectionControllerServiceImpl.CollectionActionParameters params =
                    new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
            int index = params.getSelectedLineIndex();

            ClassTeacherViewObject classObject = infoForm.getClassList().get(index);
            /**
             * param in end
             */
            Integer id = classObject.getId();

            UTClass utClass = classInfoService.getClassInfoById(classObject.getId());

            ClassDetailInfoViewObject detailInfoViewObject = taConverter.classInfoToViewObject(
                    utClass
            );

            infoForm.setDetailInfoViewObject(detailInfoViewObject);

            //跳转前加上classId
            infoForm.setCurrClassId(id.toString());

        } catch (Exception e) {

        }
        return this.getModelAndView(infoForm, "pageClassInfo");
    }

    /**
     * 获取学生申请当助教页面(填表)
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getApplyTAPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getApplyTAPage")
    public ModelAndView getApplyTAPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String stuId = userSession.getLoggedInUserPrincipalId();
        //FIXME  不能写死
        stuId = "20131840";

        Integer classId = 290739;

        ClassInfoForm infoForm = (ClassInfoForm) form;

        infoForm.setApplyAssistantViewObject(
                taConverter.applyAssistantToTableViewObject(
                        classInfoService.getStudentInfoById(stuId),
                        classInfoService.getClassInfoById(classId)
                )
        );

        return this.getModelAndView(infoForm, "pageApplyForTaForm");
    }

    @RequestMapping(params = {"pageId=pageApplyForTaForm", "methodToCall=submitTaForm"})
    public ModelAndView submitTaForm(@ModelAttribute("KualiForm") UifFormBase form) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        taService.submitApplicationAssistant(taConverter.submitInfoToTaApplication(infoForm));

        return null;
    }


    /**
     * 获取教学日历页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getTeachingCalendar&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getTeachingCalendar")
    public ModelAndView getTeachingCalendar(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        String classId = infoForm.getCurrClassId();
        if(classId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageTeachingCalendar");

        infoForm.setAllCalendar(
                taConverter.TeachCalendarToViewObject(
                        classInfoService.getAllTaTeachCalendarFilterByUidAndClassId(
                                uId,
                                classId)
                )
        );

        infoForm.setTotalElapsedTime(
                taConverter.countCalendarTotalElapsedTime(
                        infoForm.getAllCalendar()
                )
        );

        return this.getModelAndView(infoForm, "pageTeachingCalendar");
    }

    /**
     * 获取新建教学日历页面
     **/
    @RequestMapping(params = "methodToCall=getAddTeachCalendarPage")
    public ModelAndView getAddTeachCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                            HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        return this.getModelAndView(infoForm, "pageAddTeachCalendar");
    }

    /**
     * 提交新建教学日历页面
     *
     **/
    @RequestMapping(params = "methodToCall=submitTeachCalendarPage")
    public ModelAndView submitTeachCalendarPage(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        UserSession session = GlobalVariables.getUserSession();
        String uId = session.getPrincipalId();

        String classId = infoForm.getCurrClassId();

        String arr[]=infoForm.getAddTeachCTime().split("\\~");
        TAMSTeachCalendar added = infoForm.getTeachCalendar();

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//infoForm.getTeachCalendar().getStartTime()  infoForm.getTeachCalendar().getEndTime()
        try {
            added.setStartTime(
                    outputFormat.format(
                            inputFormat.parse(
                                    arr[0]
                            )
                    )
            );
            added.setEndTime(
                    outputFormat.format(
                            inputFormat.parse(
                                arr[1]
                            )
                    )
            );
        }
        catch (Exception e) {

        }

        if(classInfoService.instructorAddTeachCalendar(uId, classId, added))
            return this.getTeachingCalendar(infoForm, request);
        else //// FIXME: 16-11-18 应当返回错误页面
            return this.getTeachingCalendar(infoForm, request);
    }

    /**
     * 删除教学日历
     */
    @RequestMapping(params = "methodToCall=deleteTeachCalendar")
    public ModelAndView deleteTeachCalendar(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        /** uid **/
        UserSession session = GlobalVariables.getUserSession();
        String uId = session.getPrincipalId();

        /** classid **/
        String classId = infoForm.getCurrClassId();

        CollectionControllerServiceImpl.CollectionActionParameters params =
                new CollectionControllerServiceImpl.CollectionActionParameters(infoForm, true);
        int index = params.getSelectedLineIndex();

        /** calendarid **/
        String teachCalendarId = infoForm.getAllCalendar().get(index).getCode();

        if(classInfoService.removeTeachCalenderById(uId, classId, teachCalendarId))
            return this.getTeachingCalendar(infoForm, request);
        else //// FIXME: 16-11-18 应当返回错误页面
            return this.getTeachingCalendar(infoForm, request);
    }

    /**
     * 获取教学活动页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getTeachActivities&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getTeachActivities")
    public ModelAndView getTeachActivities(@ModelAttribute("KualiForm") UifFormBase form,
                                                HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();

        String classId = infoForm.getCurrClassId();
        if(classId == null) //// FIXME: 16-11-18 不是跳转过来应该跳转到报错页面
            return this.getModelAndView(infoForm, "pageTeachingCalendar");

        infoForm.setAllActivities(
                taConverter.activitiesToViewObject(
                        classInfoService.getAllTaTeachActivityAsCalendarFilterByUidAndClassId(
                                uId, classId)
                )
        );

        return this.getModelAndView(infoForm, "pageTeachActivities");
    }

    /**
     * 获取新建教学活动的页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getAddActivityPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getAddActivityPage")
    public ModelAndView getAddActivityPage(@ModelAttribute("KualiForm") UifFormBase form,
                                           HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        return this.getModelAndView(infoForm, "pageAddActivity");
    }

    /**
     * 根据条件查询班级列表
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=searchClassByCondition")
    public ModelAndView searchClassByCondition(@ModelAttribute("KualiForm") UifFormBase form,
                                               HttpServletRequest request) {
        ClassInfoForm infoForm = (ClassInfoForm) form;

        final UserSession userSession = KRADUtils.getUserSessionFromRequest(request);
        String uId = userSession.getLoggedInUserPrincipalId();
        Map<String, String> conditions = new HashMap<>();

        //put conditions
        conditions.put("ClassNumber", infoForm.getCondClassNumber());
        conditions.put("DepartmentId", infoForm.getCondDepartmentName());
        conditions.put("InstructorName", infoForm.getCondInstructorName());
        conditions.put("CourseName", infoForm.getCondCourseName());
        conditions.put("CourseCode", infoForm.getCondCourseCode());

        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesFilterByUidAndCondition(uId, conditions)
                )
        );

        return this.getModelAndView(infoForm, "pageClassList");
    }


    /**
     * 获取教师申请助教的页面
     * http://127.0.0.1:8080/tams/portal/class?methodToCall=getRequestTaPage&viewId=ClassView
     **/
    @RequestMapping(params = "methodToCall=getRequestTaPage")
    public ModelAndView getRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form,
                                       HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;

        infoForm.setApplyViewObject(new ApplyViewObject());
        infoForm.setClassList(
                taConverter.classInfoToViewObject(
                        classInfoService.getAllClassesMappedByDepartment()
                )
        );

        return this.getModelAndView(infoForm, "pageRequestTa");
    }

    /**
     * 教师提交申请助教的请求
     * */
    @RequestMapping(params = "methodToCall=submitRequestTaPage")
    public ModelAndView submitRequestTaPage(@ModelAttribute("KualiForm") UifFormBase form,
                                         HttpServletRequest request) {

        ClassInfoForm infoForm = (ClassInfoForm) form;

        List<ClassTeacherViewObject> classList=infoForm.getClassList();
        //TODO 未完成

        return this.getModelAndView(infoForm, "pageRequestTa");
    }


    /**
     * 将表格打印为excel，整体可用，各列具体参数还需修改
     *
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = {"pageId=pageClassList", "methodToCall=exportClassListExcel"})
    public ModelAndView exportClassListExcel(@ModelAttribute("KualiForm") UifFormBase form, HttpServletRequest request,
                                             HttpServletResponse response){
        ClassInfoForm infoForm = (ClassInfoForm) form;


        if (infoForm.getClassList() == null) {
            // TODO: 2016/10/21 错误处理
//            examForm.setErrMsg("导出内容为空");
            return this.showDialog("errWarnDialog", true, infoForm);
        }

        List<ClassTeacherViewObject> classList = infoForm.getClassList();
        String fileName = "教学班列表" + "-" + GlobalVariables.getUserSession().getLoggedInUserPrincipalId() + "-" + System.currentTimeMillis() + ".xls";

        try {
            String filePath = excelService.printClasslistExcel(classList, "exportfolder", fileName, "2003");
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                    .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);

            return this.performRedirect(infoForm, baseUrl + File.separator + filePath);
        }
        catch (IOException e) {
            String baseUrl = CoreApiServiceLocator.getKualiConfigurationService()
                    .getPropertyValueAsString(KRADConstants.ConfigParameters.APPLICATION_URL);
            return this.performRedirect(infoForm, baseUrl + "/tams");
        }
    }


    @Override
    protected UifFormBase createInitialForm() {
        return new ClassInfoForm();
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form) {
        //TODO
        return null;
    }

}
