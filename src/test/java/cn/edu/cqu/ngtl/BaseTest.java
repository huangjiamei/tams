package cn.edu.cqu.ngtl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tangjing on 16-11-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/WEB-INF/krad-servlet.xml",
        "classpath:/WEB-INF/krad-base-servlet.xml",
        "classpath:/cn/edu/cqu/ngtl/BootStrapSpringBeans.xml"})
public class BaseTest {
    @Test
    public void test() {
        System.out.print("hello");
    }
}
