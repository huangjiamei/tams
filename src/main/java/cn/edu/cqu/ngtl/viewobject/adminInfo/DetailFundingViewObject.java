package cn.edu.cqu.ngtl.viewobject.adminInfo;

import java.beans.Transient;
import java.io.Serializable;

/**
 * Created by awake on 2016/12/2.
 */
public class DetailFundingViewObject implements Serializable {

    private String taName; //助教名

    private String stuId; //学号

    private String bankName; //银行

    private String bankId; //银行卡号

    private String identity; //身份证号

    private String classNbr; //converter中会用到
    private String instructorName;

    private String courseName; //课程名

    private String courseCode; //课程代码

    private String monthlySalary1; //1月实发

    private String monthlySalary2;//2月实发

    private String monthlySalary3;//3月实发

    private String monthlySalary4;//4月实发

    private String monthlySalary5;//5月实发

    private String monthlySalary6;//6月实发

    private String monthlySalary7;//7月实发

    private String monthlySalary8;//8月实发

    private String monthlySalary9;//9月实发

    private String monthlySalary10;//10月实发

    private String monthlySalary11;//11月实发

    private String monthlySalary12;//12月实发


    private String total; // 实发总额

    @Transient
    public static String[] getAttrTittles() {
        String[] attrTittles = {
                "助教","学号","银行","银行卡号","身份证号","课程","课程代码","教学班号","3月实发","4月实发","5月实发","6月实发","7月实发","8月实发","实发总额"
        };
        return attrTittles;
    }

    @Transient
    public String[] getContents(){
        String[] contents=new String[15];
        contents[0]=getTaName();
        contents[1]=getStuId();
        contents[2]=getBankName();
        contents[3]=getBankId();
        contents[4]=getIdentity();
        contents[5]=getCourseName();
        contents[6]=getCourseCode();
        contents[7]=getClassNbr();
        contents[8]=getMonthlySalary3();
        contents[9]=getMonthlySalary4();
        contents[10]=getMonthlySalary5();
        contents[11]=getMonthlySalary6();
        contents[12]=getMonthlySalary7();
        contents[13]=getMonthlySalary8();
        contents[14]=getTotal();
        return contents;
    }

    public String getClassNbr() {
        return classNbr;
    }

    public void setClassNbr(String classNbr) {
        this.classNbr = classNbr;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMonthlySalary1() {
        return monthlySalary1;
    }

    public void setMonthlySalary1(String monthlySalary1) {
        this.monthlySalary1 = monthlySalary1;
    }

    public String getMonthlySalary2() {
        return monthlySalary2;
    }

    public void setMonthlySalary2(String monthlySalary2) {
        this.monthlySalary2 = monthlySalary2;
    }

    public String getMonthlySalary3() {
        return monthlySalary3;
    }

    public void setMonthlySalary3(String monthlySalary3) {
        this.monthlySalary3 = monthlySalary3;
    }

    public String getMonthlySalary4() {
        return monthlySalary4;
    }

    public void setMonthlySalary4(String monthlySalary4) {
        this.monthlySalary4 = monthlySalary4;
    }

    public String getMonthlySalary5() {
        return monthlySalary5;
    }

    public void setMonthlySalary5(String monthlySalary5) {
        this.monthlySalary5 = monthlySalary5;
    }

    public String getMonthlySalary6() {
        return monthlySalary6;
    }

    public void setMonthlySalary6(String monthlySalary6) {
        this.monthlySalary6 = monthlySalary6;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getMonthlySalary7() {
        return monthlySalary7;
    }

    public void setMonthlySalary7(String monthlySalary7) {
        this.monthlySalary7 = monthlySalary7;
    }

    public String getMonthlySalary8() {
        return monthlySalary8;
    }

    public void setMonthlySalary8(String monthlySalary8) {
        this.monthlySalary8 = monthlySalary8;
    }

    public String getMonthlySalary9() {
        return monthlySalary9;
    }

    public void setMonthlySalary9(String monthlySalary9) {
        this.monthlySalary9 = monthlySalary9;
    }

    public String getMonthlySalary10() {
        return monthlySalary10;
    }

    public void setMonthlySalary10(String monthlySalary10) {
        this.monthlySalary10 = monthlySalary10;
    }

    public String getMonthlySalary11() {
        return monthlySalary11;
    }

    public void setMonthlySalary11(String monthlySalary11) {
        this.monthlySalary11 = monthlySalary11;
    }

    public String getMonthlySalary12() {
        return monthlySalary12;
    }

    public void setMonthlySalary12(String monthlySalary12) {
        this.monthlySalary12 = monthlySalary12;
    }
}
