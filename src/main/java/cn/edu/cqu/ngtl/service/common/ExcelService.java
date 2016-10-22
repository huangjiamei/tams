package cn.edu.cqu.ngtl.service.common;


import cn.edu.cqu.ngtl.viewobject.course.ClassTeacherViewObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {

    String printClasslistExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename, String version)
            throws IOException;

}
