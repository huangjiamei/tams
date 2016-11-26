package cn.edu.cqu.ngtl.dao.tams;

/**
 * Created by tangjing on 16-11-26.
 */
public interface TAMSClassApplyStatusDao {

    boolean toNextStatus(String[] roleIds, String functionId, String classId);

}
