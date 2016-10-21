package cn.edu.cqu.ngtl.controller.tamanagement;

import cn.edu.cqu.ngtl.form.tamanagement.TaInfoForm;
import cn.edu.cqu.ngtl.service.courseservice.ICourseInfoService;
import cn.edu.cqu.ngtl.service.riceservice.ITAConverter;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tangjing on 16-10-19.
 * 助教信息查看的相关view及function
 */
@Controller
@RequestMapping("/ta")
public class TaController extends UifControllerBase {

    @Autowired
    private ICourseInfoService courseInfoService;

    @Autowired
    private ITAConverter taConverter;

    @Override
    protected UifFormBase createInitialForm() {

        return new TaInfoForm();

    }


}
