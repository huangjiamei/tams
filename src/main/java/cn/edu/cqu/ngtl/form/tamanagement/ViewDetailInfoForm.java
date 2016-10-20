package cn.edu.cqu.ngtl.form.tamanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.course.ClassTeacherViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class ViewDetailInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;

    private List<ClassTeacherViewObject> collection = new ArrayList<>();

    public List<ClassTeacherViewObject> getCollection() {
        return collection;
    }

    public void setCollection(List<ClassTeacherViewObject> collection) {
        this.collection = collection;
    }
}
