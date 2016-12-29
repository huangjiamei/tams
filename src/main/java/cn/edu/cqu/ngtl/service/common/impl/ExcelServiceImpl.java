package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.CourseManagerViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DepartmentFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.DetailFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.SessionFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.tainfo.TaInfoViewObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.krad.uif.freemarker.FreeMarkerInlineRenderBootstrap.getServletContext;

@Service
public class ExcelServiceImpl implements ExcelService {
    static Logger logger = Logger.getLogger(ExcelServiceImpl.class);

    //单元格格式
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 10);
        titleFont.setBold(true);
        titleFont.setFontName("宋体");

        style = wb.createCellStyle();
        style.setFont(titleFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setLocked(false);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setFont(titleFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setLocked(true);
        styles.put("constTitle", style);

        style = wb.createCellStyle();
        Font contentFont = wb.createFont();
        contentFont.setFontHeightInPoints((short) 9);

        style.setFont(contentFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setLocked(false);
        styles.put("content", style);

        style = wb.createCellStyle();
        style.setFont(contentFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setLocked(false);
        styles.put("titleInfo", style);

        style.setFont(contentFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setLocked(true);
        styles.put("constContent", style);

        return styles;
    }

    /**
     * print Excel到basepath+/folderPath+/filename
     * 最后返回folderPath+/filename
     * controller通过applicationUrl+/folderPath+/filename下载文件
     *
     * @param classlist
     * @param folderPath
     * @param filename
     * @param version
     * @return
     * @throws IOException
     */
    @Override
    public String printClassListExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename,
                                      String version) throws IOException {
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("课程信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = ClassTeacherViewObject.getAttrTittles();
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)课程信息导出模板");
        topCell.setCellStyle(styles.get("content"));

        /*// 空一行
        rowNum++;
        CellRangeAddress cra2 = new CellRangeAddress(1, 1, 0, 15);
        sheet.addMergedRegion(cra2);*/

        // region # 添加各列标题上方的注意事项
        /*Row infoRow = sheet.createRow(rowNum++);
        Cell InfoCell = infoRow.createCell(0, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("课程名称");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(1, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("课程编号");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(2, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("教学班");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(3, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("教师");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(4, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("耗费工时");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(5, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("学院");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(6, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("状态");
        InfoCell.setCellStyle(styles.get("content"));*/

        // endregion

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (ClassTeacherViewObject classObj : classlist) {
            content.add(classObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

//        String rootPath = System.getProperty("catalina.home");
        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath + folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath + File.separator + filename;

        FileOutputStream out = new FileOutputStream(rootPath + File.separator + filePath);
        wb.write(out);
        out.close();

        return filePath;
    }

    public  String printCourseManagerExcel(List<CourseManagerViewObject> coursemanagerlist, String folderPath, String filename, String version)
            throws  IOException
    {
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("课程负责人信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = CourseManagerViewObject.getAttrTittles();
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)课程负责人导出模板");
        topCell.setCellStyle(styles.get("content"));


        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (CourseManagerViewObject  courseObj: coursemanagerlist) {
            content.add(courseObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);
   int a=contentStrings.length;
            for (int colNum = 0; colNum <contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

//        String rootPath = System.getProperty("catalina.home");
        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }



    @Override
    public String printTaListExcel(List<TaInfoViewObject> taList, String folderPath, String filename, String version) throws IOException {
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("助教信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = TaInfoViewObject.getAttrTittles();
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)助教信息导出模板");
        topCell.setCellStyle(styles.get("content"));

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (TaInfoViewObject taObj : taList) {
            content.add(taObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

//        String rootPath = System.getProperty("catalina.home");
        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+File.separator+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }



    public String printDetailFundingExcel(List<DetailFundingViewObject> DFList, String folderPath, String filename, String version) throws IOException{
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("经费明细信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = DetailFundingViewObject.getAttrTittles();

        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)经费明细信息导出");
        topCell.setCellStyle(styles.get("content"));

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (DetailFundingViewObject DFObj : DFList) {
            content.add(DFObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+File.separator+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }


    public String printPreviousSessionFundingsExcel(List<SessionFundingViewObject> SFList, String folderPath, String filename, String version) throws IOException{
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("学校历史经费信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = SessionFundingViewObject.getAttrTittles();

        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)学校历史经费信息导出");
        topCell.setCellStyle(styles.get("content"));

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (SessionFundingViewObject SFObj : SFList) {
            content.add(SFObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+File.separator+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }


    public String printDepartmentCurrFundingsExcel(List<DepartmentFundingViewObject> departmentFundingViewObjectList, String folderPath, String filename, String version) throws IOException{
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("学院经费信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = DepartmentFundingViewObject.getAttrTittles();

        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)学院经费信息导出");
        topCell.setCellStyle(styles.get("content"));

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (DepartmentFundingViewObject DFObj : departmentFundingViewObjectList) {
            content.add(DFObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+File.separator+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }

    public String printDepartmentPreFundingsExcel(List<DepartmentFundingViewObject> departmentFundingViewObjectList, String folderPath, String filename, String version) throws IOException{
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("学院历史经费信息导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = DepartmentFundingViewObject.getAttrTittles();

        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("教务处(重庆大学助教管理系统)学院历史经费信息导出");
        topCell.setCellStyle(styles.get("content"));

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> content = new ArrayList<>();
        for (DepartmentFundingViewObject DFObj : departmentFundingViewObjectList) {
            content.add(DFObj.getContents());
        }

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] contentStrings = content.get(i);

            for (int colNum = 0; colNum < contentStrings.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(contentStrings[colNum]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(contentStrings[colNum]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath+File.separator+folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath = folderPath+File.separator+filename;

        FileOutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }
}
