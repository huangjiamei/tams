package cn.edu.cqu.ngtl.service.common;


import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    String printClassListExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename, String version)
            throws IOException;

    String printTaListExcel(List<TaInfoViewObject> taList, String folderPath, String filename, String version)
            throws IOException;
    String printCourseManagerExcel(List<CourseManagerViewObject> coursemanagerlist, String folderPath, String filename, String version)
           throws  IOException;
    String printCourseClassExcel(List<ClassFundingViewObject> courseclasslist, String folderPath, String filename, String version)
            throws  IOException;
    String printTafundingExcel(List<TaFundingViewObject> tafundinglist, String folderPath, String filename, String version)
            throws  IOException;
}
