package cn.edu.cqu.ngtl.form.tamanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.tainfo.IssueViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.MyTaViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class TaInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;

    /**
     * 教师评价相关
     */

    private List<IssueViewObject> allIssues;

    private String evaluateDetail;

    private String evaluate;

    /**
     * '我的助教'管理相关
     */

    private List<MyTaViewObject> allMyTa;

    private List<MyTaViewObject> allApplication;

    /**
     * 助教列表相关
     */

    private List<TaInfoViewObject> allTaInfo;

    /** Getter and Setter **/

    public List<IssueViewObject> getAllIssues() {
        return allIssues;
    }

    public void setAllIssues(List<IssueViewObject> allIssues) {
        this.allIssues = allIssues;
    }

    public String getEvaluateDetail() {
        return evaluateDetail;
    }

    public void setEvaluateDetail(String evaluateDetail) {
        this.evaluateDetail = evaluateDetail;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public List<MyTaViewObject> getAllApplication() {
        return allApplication;
    }

    public void setAllApplication(List<MyTaViewObject> allApplication) {
        this.allApplication = allApplication;
    }

    public List<TaInfoViewObject> getAllTaInfo() {
        return allTaInfo;
    }

    public void setAllTaInfo(List<TaInfoViewObject> allTaInfo) {
        this.allTaInfo = allTaInfo;
    }

    public List<MyTaViewObject> getAllMyTa() {
        return allMyTa;
    }

    public void setAllMyTa(List<MyTaViewObject> allMyTa) {
        this.allMyTa = allMyTa;
    }
}
