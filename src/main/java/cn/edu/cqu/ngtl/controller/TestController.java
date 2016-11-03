package cn.edu.cqu.ngtl.controller;

import cn.edu.cqu.ngtl.form.TestForm;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 2016/10/7.
 */
@Controller
@RequestMapping("/mytest")
public class TestController extends UifControllerBase {

    /**
     * 测试用page
     * 127.0.0.1:8080/tams/portal/mytest?methodToCall=getTestPage&viewId=TestView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTestPage")
    public ModelAndView getTestPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTest");
    }

    /**
     * 获取任务列表页面
     * 127.0.0.1:8080/tams/portal/mytest?methodToCall=getTaskListPage&viewId=TestView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaskListPage")
    public ModelAndView getTaskListPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;

        return this.getModelAndView(testForm, "pageTaskList");
    }

     /* *********** 任务相关 *********** */
    /**
     * 任务详情页面
     * 127.0.0.1:8080/tams/portal/mytest?methodToCall=getTaskDetailPage&viewId=TestView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getTaskDetailPage")
    public ModelAndView getTaskDetailPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageTaskDetail");
    }

    /**
     * 获取添加任务页面
     * 127.0.0.1:8080/tams/portal/mytest?methodToCall=getAddTaskPage&viewId=TestView
     * @param form
     * @return
     */
    @RequestMapping(params = "methodToCall=getAddTaskPage")
    public ModelAndView getAddTaskPage(@ModelAttribute("KualiForm") UifFormBase form) {
        TestForm testForm = (TestForm) form;
        return this.getModelAndView(testForm, "pageAddNewTask");
    }


    /**
     * 此方法只处理editor的content，所以不能直接使用
     * 后台开发时需新建一个method用于处理类型、主题、editor等所有内容，同时修改newtaskpage.xml中btn的methodtocall
     * @param form
     * @param request
     * @param response
     */
    @RequestMapping(params = "methodToCall=submitEditorContent")
    public void submitEditorContent(@ModelAttribute("KualiForm") UifFormBase form ,HttpServletRequest request, HttpServletResponse response){
        TestForm testForm = (TestForm) form;

        String content=testForm.getEditorContent();
        System.out.println(content);

        Pattern pattern = Pattern.compile("<img.+>.+\\.[a-z A-Z 0-9]*");
        Matcher matcher = pattern.matcher(content);

        // 附件list，每个item包括img和a两部分
        List<String> fileList=new ArrayList<>();
        while(matcher.find()){
            fileList.add(matcher.group());
        }
        // 去除附件带来的文本，得到用户输入的纯文本
        String plainContent=matcher.replaceAll("").trim();
        System.out.println("plainContent:\n"+plainContent);

        // 处理每个上传的文件
        for(String file:fileList){
            Document doc = Jsoup.parse(file);
            Element link = doc.getElementsByTag("a").first();
            String herf=link.attr("href");      // 文件实际存储路径，如ueditor/upload/image/213092109472194812.jpg
            String title=link.attr("title");    // 文件原名，test.jpg

            Element img=doc.getElementsByTag("img").first();
            System.out.println(String.format("title=%s\nherf=%s\nimg:%s\n",title,herf,img));
            System.out.printf("title=%s\nherf=%d\nimg:%s\n", title, herf, img);
            // TODO: 2016/10/21 存入数据库
        }
        // TODO: 2016/10/21 给前端返回处理结果
    }

    @Override
    protected UifFormBase createInitialForm() {
        return new TestForm();
    }
}
