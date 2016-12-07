package cn.edu.cqu.ngtl.form;

import cn.edu.cqu.ngtl.bo.User;
import cn.edu.cqu.ngtl.dataobject.TestObject;
import cn.edu.cqu.ngtl.viewobject.common.FileViewObject;
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

     private String sessionTermFinder;

    public String getSessionTermFinder() {
        return sessionTermFinder;
    }

    public void setSessionTermFinder(String sessionTermFinder) {
        this.sessionTermFinder = sessionTermFinder;
    }

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
    private String inputField11;
    private String inputField12;
    private String inputField13;
    private String inputField14;
    private String inputField15;
    private String inputField16;
    private String inputField17;
    private String inputField18;
    private String inputField19;
    private String inputField20;

    private String editorContent;

    private boolean check1;
    private boolean check2;

    private List<TestObject> collection = new ArrayList<TestObject>();
    private List<FileViewObject> fileList=new ArrayList<>();

    public BaseForm() {
        getCollection().add(new TestObject(new Date(2016,10,15), "学习掌握Chap1 极限 1.1小节", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |","17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"无分组", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15),"答疑/讨论", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "答疑/讨论", "2016-11-12 14:13:48", "共0人提交  |  2016-11-12    15：36  |", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15), "无分组", "21",  "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "学习掌握Chap2 极限 1.2小节", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "学习掌握Chap1 极限 1.3小节", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15), "5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15), "7", "3", "1", "9", "11"));
        getCollection().add(new TestObject(new Date(2016,10,15), "8", "9", "10", "11", "12"));
        getCollection().add(new TestObject(new Date(2016,10,15), "2", "3", "4", "5", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"14", "15", "16", "17", "18"));
        getCollection().add(new TestObject(new Date(2016,10,15),"学习掌握Chap1 极限 1.1小节", "21", "22", "23", "24"));
        getCollection().add(new TestObject(new Date(2016,10,15), "143", "151", "126", "137", "164"));
        getCollection().add(new TestObject(new Date(2016,10,15), "144", "155", "166", "177", "188"));
        getCollection().add(new TestObject(new Date(2016,10,15), "14", "15", "15", "17", "17"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "4", "6", "6"));
        getCollection().add(new TestObject(new Date(2016,10,15),"5", "5", "5", "5", "5"));
        getCollection().add(new TestObject(new Date(2016,10,15),"7", "3", "1", "9", "11"));
    }

    public List<FileViewObject> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileViewObject> fileList) {
        this.fileList = fileList;
    }

    public String getInputField11() {
        return inputField11;
    }

    public void setInputField11(String inputField11) {
        this.inputField11 = inputField11;
    }

    public String getInputField12() {
        return inputField12;
    }

    public void setInputField12(String inputField12) {
        this.inputField12 = inputField12;
    }

    public String getInputField13() {
        return inputField13;
    }

    public void setInputField13(String inputField13) {
        this.inputField13 = inputField13;
    }

    public String getInputField14() {
        return inputField14;
    }

    public void setInputField14(String inputField14) {
        this.inputField14 = inputField14;
    }

    public String getInputField15() {
        return inputField15;
    }

    public void setInputField15(String inputField15) {
        this.inputField15 = inputField15;
    }

    public String getInputField16() {
        return inputField16;
    }

    public void setInputField16(String inputField16) {
        this.inputField16 = inputField16;
    }

    public String getInputField17() {
        return inputField17;
    }

    public void setInputField17(String inputField17) {
        this.inputField17 = inputField17;
    }

    public String getInputField18() {
        return inputField18;
    }

    public void setInputField18(String inputField18) {
        this.inputField18 = inputField18;
    }

    public String getInputField19() {
        return inputField19;
    }

    public void setInputField19(String inputField19) {
        this.inputField19 = inputField19;
    }

    public String getInputField20() {
        return inputField20;
    }

    public void setInputField20(String inputField20) {
        this.inputField20 = inputField20;
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
