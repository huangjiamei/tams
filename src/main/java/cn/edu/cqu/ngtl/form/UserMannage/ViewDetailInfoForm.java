package cn.edu.cqu.ngtl.form.UserMannage;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.course.CourseTeacherViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-10-12.
 */
public class ViewDetailInfoForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 8220820300868876320L;

    private List<CourseTeacherViewObject> collection = new ArrayList<>();

    public List<CourseTeacherViewObject> getCollection() {
        return collection;
    }

    public void setCollection(List<CourseTeacherViewObject> collection) {
        this.collection = collection;
    }
}
