package cn.edu.cqu.ngtl.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2016/11/20.
 * 测试datatable嵌套用的class
 * groupObj表示一级菜单，他包含的objList为二级菜单
 */
public class TestGroupObject {
    private boolean checked;

    private String groupName;
    private List<TestObject> objectList=new ArrayList<>();

    public TestGroupObject(String groupName) {
        this.groupName = groupName;
        getObjectList().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |","17", "18"));
        getObjectList().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "3", "4", "5", "6"));
        getObjectList().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |", "17", "18"));
    }

    public TestGroupObject() {

        getObjectList().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |","17", "18"));
        getObjectList().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "3", "4", "5", "6"));
        getObjectList().add(new TestObject(new Date(2016,10,15), "答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |", "17", "18"));
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<TestObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<TestObject> objectList) {
        this.objectList = objectList;
    }
}
