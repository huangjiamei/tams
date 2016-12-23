package cn.edu.cqu.ngtl.viewobject.adminInfo;

/**
 * Created by damei on 16/12/22.
 */
public class HistogramNameValuePair {
    private Integer y; //众坐标，经费
    private String x; //横坐标，批次
    //构造函数
    public HistogramNameValuePair (String x, Integer y) {
        if(x != null && y != null) {
            this.x = x;
            this.y = y;
        }
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
}
