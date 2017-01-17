package cn.edu.cqu.ngtl.viewobject.adminInfo;

/**
 * Created by tangjing on 16-11-8.
 */
public class PieChartsNameValuePair {

    private String name;

    private Integer y;

    public PieChartsNameValuePair(String name, Integer value) {
        if(name != null && value != null) {
            this.name = name;
            this.y = value;
        }
        else{
            this.name = null;
            this.y = null;
        }
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
