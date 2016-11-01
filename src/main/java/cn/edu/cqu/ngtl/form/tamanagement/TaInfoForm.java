package cn.edu.cqu.ngtl.form.tamanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class TaInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;

    /**
     * 助教列表相关
     */

    private List<TaInfoViewObject> allTaInfo;

    public List<TaInfoViewObject> getAllTaInfo() {
        return allTaInfo;
    }

    public void setAllTaInfo(List<TaInfoViewObject> allTaInfo) {
        this.allTaInfo = allTaInfo;
    }
}
