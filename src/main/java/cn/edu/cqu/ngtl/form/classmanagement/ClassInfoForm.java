package cn.edu.cqu.ngtl.form.classmanagement;

import cn.edu.cqu.ngtl.form.BaseForm;
import cn.edu.cqu.ngtl.viewobject.course.ClassTeacherViewObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by awake on 2016-10-21.
 */
public class ClassInfoForm extends BaseForm implements Serializable {


    private static final long serialVersionUID = 230347454225549981L;

    private List<ClassTeacherViewObject> collection = new ArrayList<>();

    public List<ClassTeacherViewObject> getCollection() {
        return collection;
    }

    public void setCollection(List<ClassTeacherViewObject> collection) {
        this.collection = collection;
    }


}
