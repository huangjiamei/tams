package cn.edu.cqu.ngtl.viewobject.classinfo;

import java.util.List;

/**
 * Created by tangjing on 16-11-16.
 */
public class TeachCalendarViewObject {

    private Boolean checkBox;

    private String code;

    private String theme;

    private String description;

    private String taTask;

    private String taTaskTimes;

    private String startTime;

    private String endTime;

    private String elapsedTime;

    private String budget;

    private String week;

    private List<ActivityViewObject> activityViewObjects;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTaTaskTimes() {
        return taTaskTimes;
    }

    public void setTaTaskTimes(String taTaskTimes) {
        this.taTaskTimes = taTaskTimes;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<ActivityViewObject> getActivityViewObjects() {
        return activityViewObjects;
    }

    public void setActivityViewObjects(List<ActivityViewObject> activityViewObjects) {
        this.activityViewObjects = activityViewObjects;
    }

    public Boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(Boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaTask() {
        return taTask;
    }

    public void setTaTask(String taTask) {
        this.taTask = taTask;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
