package cn.edu.cqu.ngtl.service.common;


import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DepartmentFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DetailFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.SessionFundingViewObject;
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

    String printDetailFundingExcel(List<DetailFundingViewObject> detailFundingViewObjectList,String folderPath,String filename,String version)
           throws IOException;

    String printPreviousSessionFundingsExcel(List<SessionFundingViewObject> sessionFundingViewObjectList,String folderPath,String filename,String version)
          throws IOException;

    String printDepartmentCurrFundingsExcel(List<DepartmentFundingViewObject> departmentFundingViewObjectList, String folderPath, String filename, String version)
            throws IOException;

    String printDepartmentPreFundingsExcel(List<DepartmentFundingViewObject> departmentFundingViewObjectList, String folderPath, String filename, String version)
            throws IOException;


}
