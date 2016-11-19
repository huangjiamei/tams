package cn.edu.cqu.ngtl.bo;

/**
 * Created by tangjing on 16-11-15.
 */
public class StuIdClassIdPair {

    private String stuId;

    private String classId;

    public StuIdClassIdPair(String stuId, String classId) {
        if(stuId != null && classId != null) {
            this.setStuId(stuId);
            this.setClassId(classId);
        }
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getClassId() {

        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
