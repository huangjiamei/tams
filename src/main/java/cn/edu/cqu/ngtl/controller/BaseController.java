package cn.edu.cqu.ngtl.controller;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dao.ut.UTSessionDao;
import cn.edu.cqu.ngtl.dataobject.ut.UTSession;
import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.form.TestForm;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by awake on 2016/11/28.
 */

@Controller
@RequestMapping("/Base")
public class BaseController extends UifControllerBase {

    /**
     * 所有controller通用的方法放在里面，比如header数据的初始化
     * @param form
     * @return
     */


    @Autowired
    private UTSessionDao sessionDao;


    public ModelAndView baseStart(BaseForm form) {
        User user = (User) GlobalVariables.getUserSession().retrieveObject("user");
        form.setUser(user);
        String code = user.getCode();
        UTSession curSession = sessionDao.getCurrentSession();
        form.setCurrenSessionId(curSession.getId());
        return super.start(form);
    }

    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
