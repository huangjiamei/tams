package cn.edu.cqu.ngtl.dao;

import cn.edu.cqu.ngtl.BaseTest;
import cn.edu.cqu.ngtl.dao.tams.TAMSWorkflowStatusDao;
import cn.edu.cqu.ngtl.dataobject.tams.TAMSWorkflowStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tangjing on 16-11-15.
 */
public class TamsDaoTest extends BaseTest {

    @Autowired
    private TAMSWorkflowStatusDao workflowStatusDao;

    @Test
    public void test1() {
        List<TAMSWorkflowStatus> tests = workflowStatusDao.selectAll();

        System.out.println("Total counts: " + tests.size());
        System.out.println("Id: \t");
        System.out.println("Status: \t");
        for(TAMSWorkflowStatus test : tests) {
            System.out.println(test.getId());
            System.out.println(test.getWorkflowStatus());
        }
    }

}
