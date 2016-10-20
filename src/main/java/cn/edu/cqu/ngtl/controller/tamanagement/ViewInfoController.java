package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.form.tamanagement.ViewDetailInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class ViewInfoController extends UifControllerBase {

    @Autowired
    private ICourseInfoService courseInfoService;

    @Autowired
    private ITAConverter taConverter;

    @Override
    protected UifFormBase createInitialForm() {

        return new ViewDetailInfoForm();

    }

    @RequestMapping(params = "methodToCall=getClassesInfoPage")
    public ModelAndView getClassesInfoPage(@ModelAttribute("KualiForm") UifFormBase form){
        ViewDetailInfoForm infoForm=(ViewDetailInfoForm) form;
        infoForm.setCollection(
                taConverter.classInfoToViewObject(
                        courseInfoService.getAllCoursesMappedByDepartment()
                )
        );

        return this.getModelAndView(infoForm, "pageCourseTeacher");
    }

    @RequestMapping(params = "methodToCall=getTAInfoPage")
    public ModelAndView getTAInfoPage(@ModelAttribute("KualiForm") UifFormBase form){
        //TODO
        return null;
    }

}
