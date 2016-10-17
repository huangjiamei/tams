package cn.edu.cqu.ngtl.controller.usercontroller;

import cn.edu.cqu.ngtl.form.usermannage.ViewDetailInfoForm;
import cn.edu.cqu.ngtl.service.userservice.ITeacherService;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tangjing on 16-10-15.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends UifControllerBase {

    @Autowired
    private ITeacherService teacherService;

    @Override
    protected UifFormBase createInitialForm() {

        return new ViewDetailInfoForm();

    }

    @RequestMapping(params = "methodToCall=getTeacherCoursePage")
    public ModelAndView getDivPage(@ModelAttribute("KualiForm") UifFormBase form){
        ViewDetailInfoForm infoForm=(ViewDetailInfoForm) form;
        infoForm.setCollection(teacherService.getAllCoursesMappedByDepartment());

        return this.getModelAndView(infoForm, "pageCourseTeacher");
    }

}
