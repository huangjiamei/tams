package cn.edu.cqu.ngtl.service.exportservice;

import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.TeachCalendarViewObject;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * Created by tangjing on 2016/12/15.
 */
public interface IPDFService {
    public void exportPDFByTemplate(String templatePath, String newPDFPath, String[] str);

    public void exportPDFByTemplateMutilPage(String templatePath, String newPDFPath, List<String[]> str);

    /**
     * 打印普通表格
     *
     * @param String         title	   表格名字
     * @param String[]       headers	   表头内容
     * @param List<String[]> T   表单内容
     * @param String         fileName    文件名
     * @return
     * @author oumingliao
     */
    public String printNormalTable(String title, String[] headers, List<String[]> T, String fileName) throws DocumentException, IOException;

    /**
     * @param path             文件在服务端产生的路径
     * @param classInformation 课程信息
     * @throws DocumentException
     * @throws IOException
     */
    public void exportClassInformation(String path, List<ClassTeacherViewObject> classInformation) throws DocumentException, IOException;


    /**create by luojizhou on 2016/12/30
     *
     *封装的方法，用于获取导出的课程经费PDF文件的文件路径
     * @param classFundingViewObjectList  传入的参数
     * @return filePath                   返回的PDF文件路径
     */
    String prepareClassFundingsToPDF(List<ClassFundingViewObject> classFundingViewObjectList);

    /**create by luojizhou on 2016/12/30
     *
     *封装的方法，用于获取导出的助教经费PDF文件的文件路径
     * @param taFundingViewObjectList     传入的参数
     * @return filePath                   返回的PDF文件路径
     */
    String prepareTaFundingToPDF(List<TaFundingViewObject> taFundingViewObjectList);

    /**
     * 教学日历PDF打印路径create by liuchuan
     * @param  TeachCalendarManager
     * @return filePath
     */
    String prepareTeachCalendarPDF(List<TeachCalendarViewObject>  TeachCalendarManager);
}
