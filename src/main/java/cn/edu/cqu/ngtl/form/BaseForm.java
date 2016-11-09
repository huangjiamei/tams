package cn.edu.cqu.ngtl.form;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.TestObject;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2016/10/6.
 * Form的基础类
 * 所有其他的Form都需要extends该Form
 * 所有系统所需的公共form数据都放在该Form
 *
 */
public class BaseForm extends UifFormBase  {

    // region # 开发用属性，后期删除
    /* 可方便新添加的page在各controller转换，后期删除*/
    private String inputField1;
    private String inputField2;
    private String inputField3;
    private String inputField4;
    private String inputField5;
    private String inputField6;
    private String inputField7;
    private String inputField8;
    private String inputField9;
    private String inputField10;

    private String editorContent;

    private boolean check1;
    private boolean check2;

    private List<TestObject> collection = new ArrayList<TestObject>();

    public BaseForm() {
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"20", "21", "22"));
        getCollection().add(new TestObject(new Date(2016,10,15),"8", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15),"2", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "15", "16", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15), "20", "21",  "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "143", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "144", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15), "7", "3", "1", "9", "11"));
        getCollection().add(new TestObject(new Date(2016,10,15), "8", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15), "2", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "15", "16", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"20", "21", "22", "23", "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "143", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "144", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "15", "15", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15),"7", "3", "1", "9", "11"));
    }

    public String getInputField1() {
        return inputField1;
    }

    public void setInputField1(String inputField1) {
        this.inputField1 = inputField1;
    }

    public String getInputField2() {
        return inputField2;
    }

    public void setInputField2(String inputField2) {
        this.inputField2 = inputField2;
    }

    public String getInputField3() {
        return inputField3;
    }

    public void setInputField3(String inputField3) {
        this.inputField3 = inputField3;
    }

    public String getInputField4() {
        return inputField4;
    }

    public void setInputField4(String inputField4) {
        this.inputField4 = inputField4;
    }

    public String getInputField5() {
        return inputField5;
    }

    public void setInputField5(String inputField5) {
        this.inputField5 = inputField5;
    }

    public String getInputField6() {
        return inputField6;
    }

    public void setInputField6(String inputField6) {
        this.inputField6 = inputField6;
    }

    public String getInputField7() {
        return inputField7;
    }

    public void setInputField7(String inputField7) {
        this.inputField7 = inputField7;
    }

    public String getInputField8() {
        return inputField8;
    }

    public void setInputField8(String inputField8) {
        this.inputField8 = inputField8;
    }

    public String getInputField9() {
        return inputField9;
    }

    public void setInputField9(String inputField9) {
        this.inputField9 = inputField9;
    }

    public String getInputField10() {
        return inputField10;
    }

    public void setInputField10(String inputField10) {
        this.inputField10 = inputField10;
    }

    public String getEditorContent() {
        return editorContent;
    }

    public void setEditorContent(String editorContent) {
        this.editorContent = editorContent;
    }

    public boolean isCheck1() {
        return check1;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }

    public List<TestObject> getCollection() {
        return collection;
    }

    public void setCollection(List<TestObject> collection) {
        this.collection = collection;
    }

    // endregion


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
