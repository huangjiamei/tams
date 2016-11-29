package cn.edu.cqu.ngtl.tools;

/**
 * Created by awake on 2016/11/28.
 */
public class TimeSettingsTypeName {


    public String getTimeSettingsTypeName(String typeId) {
        switch (typeId) {
            case "1":
                return "阶段1";
            case "2":
                return "阶段2";
            case "3":
                return "阶段3";

        }
        return "";
    }


}
