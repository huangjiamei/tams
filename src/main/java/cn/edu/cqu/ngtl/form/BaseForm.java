package cn.edu.cqu.ngtl.form;

import cn.edu.cqu.ngtl.bo.User;
import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * Created by hp on 2016/10/6.
 * Form的基础类
 * 所有其他的Form都需要extends该Form
 * 所有系统所需的公共form数据都放在该Form
 *
 */
public class BaseForm extends UifFormBase  {

    private User user;
    private Integer currenSessionId;

    /*
	* 错误信息
	*/
    private String errMsg;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Integer getCurrenSessionId() {
        return currenSessionId;
    }
    public void setCurrenSessionId(Integer currenSessionId) {
        this.currenSessionId = currenSessionId;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
