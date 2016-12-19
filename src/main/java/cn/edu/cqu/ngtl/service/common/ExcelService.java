package cn.edu.cqu.ngtl.service.common;


import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    String printClassListExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename, String version)
            throws IOException;

    String printTaListExcel(List<TaInfoViewObject> taList, String folderPath, String filename, String version)
            throws IOException;
}
