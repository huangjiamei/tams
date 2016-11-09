package cn.edu.cqu.ngtl.viewobject.adminInfo;

import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
public class RelationTable {

    private List<String> header;

    private CheckBoxStatus[][] data;

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public CheckBoxStatus[][] getData() {
        return data;
    }

    public void setData(CheckBoxStatus[][] data) {
        this.data = data;
    }
}
