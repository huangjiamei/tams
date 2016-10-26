package cn.edu.cqu.ngtl.dao.tams;

import cn.edu.cqu.ngtl.dataobject.tams.TAMSIssueType;

import java.util.List;

/**
 * Created by tangjing on 16-10-26.
 */
public interface TAMSIssueTypeDao {

    List<TAMSIssueType> selectAll();

    boolean insertOneByEntity(TAMSIssueType issueType);

    TAMSIssueType selectOneByTypeName(String typeName);

}
