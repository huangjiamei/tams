package cn.edu.cqu.ngtl.service.common;


import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    String printClasslistExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename, String version)
            throws IOException;

}
