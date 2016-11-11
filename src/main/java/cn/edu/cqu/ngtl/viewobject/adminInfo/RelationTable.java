package cn.edu.cqu.ngtl.viewobject.adminInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjing on 16-11-9.
 */
public class RelationTable {

    private List<String> header;

    private CheckBoxStatus[][] data;

    public RelationTable(String... args) {
        for(String arg : args) {
            if(arg.equals("default")) {
                this.header = new ArrayList<String>();
                this.data = null;
            }
        }
    }

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
