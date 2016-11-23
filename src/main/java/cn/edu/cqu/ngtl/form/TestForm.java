package cn.edu.cqu.ngtl.form;

import cn.edu.cqu.ngtl.dataobject.TestGroupObject;
import cn.edu.cqu.ngtl.dataobject.TestObject;
import org.aspectj.weaver.ast.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2016/10/6.
 */
public class TestForm extends BaseForm {

    private List<TestGroupObject> groupObjectList = new ArrayList<>();


    public List<TestGroupObject> getGroupObjectList() {
        return groupObjectList;
    }

    public void setGroupObjectList(List<TestGroupObject> groupObjectList) {
        this.groupObjectList = groupObjectList;
    }
}
