package cn.edu.cqu.ngtl.service.common.impl;

import cn.edu.cqu.ngtl.dataobject.view.UTClassInformation;
import cn.edu.cqu.ngtl.service.common.ExcelService;
import cn.edu.cqu.ngtl.viewobject.course.ClassTeacherViewObject;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;

import static org.kuali.rice.krad.uif.freemarker.FreeMarkerInlineRenderBootstrap.getServletContext;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelServiceImpl implements ExcelService {
    static Logger logger = Logger.getLogger(ExcelServiceImpl.class);


    /**
     * print Excel到basepath+/folderPath+/filename
     * 最后返回folderPath+/filename
     * controller通过applicationUrl+/folderPath+/filename下载文件
     * @param classlist
     * @param folderPath
     * @param filename
     * @param version
     * @return
     * @throws IOException
     */
    @Override
    public String printClasslistExcel(List<ClassTeacherViewObject> classlist, String folderPath, String filename,
                                      String version) throws IOException {
        Workbook wb;
        Sheet sheet;

        //默认输出版本为03版
        if (version.equals("2007")) {
            wb = new XSSFWorkbook();
        } else {
            wb = new HSSFWorkbook();
        }
        sheet = wb.createSheet("考生导出");

        Map<String, CellStyle> styles = createStyles(wb);
        String[] titles = ClassTeacherViewObject.getAttrTittles();
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 15);
        sheet.addMergedRegion(cra1);

        int rowNum = 0; // 控制在excel的第几行输出文本
        // 顶端一长排字
        Row topRow = sheet.createRow(rowNum++);
        Cell topCell = topRow.createCell(0, Cell.CELL_TYPE_STRING);
        topCell.setCellValue("社考处(全国大学英语四、六级考试)集体报名导入模板");
        topCell.setCellStyle(styles.get("content"));

        // 空一行
        rowNum++;
        CellRangeAddress cra2 = new CellRangeAddress(1, 1, 0, 15);
        sheet.addMergedRegion(cra2);

        // region # 添加各列标题上方的注意事项
        Row infoRow = sheet.createRow(rowNum++);
        Cell InfoCell = infoRow.createCell(0, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("填写序号顺序");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(1, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("学院");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(2, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("课程名称");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(3, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("课程编号");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(4, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("教学班");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(5, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("身教师");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(6, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("耗费工时");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(7, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("津贴估算");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(8, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("拟配备助教数");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(9, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("状态");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(10, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("学时");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(11, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("学分");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(12, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("选修/必修");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(13, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("课程类型");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(14, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("授课年级");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(15, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("专业");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(16, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("学生人数");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(17, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("教学班数");
        InfoCell.setCellStyle(styles.get("content"));

        InfoCell = infoRow.createCell(18, Cell.CELL_TYPE_STRING);
        InfoCell.setCellValue("上课教师数");
        InfoCell.setCellStyle(styles.get("content"));

        // endregion

        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell;
        for (int i = 0; i < titles.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellStyle(styles.get("title"));
        }

        List<String[]> contens = new ArrayList<String[]>();
        for (ClassTeacherViewObject classObj : classlist) {
            contens.add(classObj.getContents());
        }

        for (int i = 0; i < contens.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            String[] content = contens.get(i);

            for (int colNum = 1; colNum <= content.length; colNum++) {
                Cell cell = row.createCell(colNum);
                if (version.equals("2007")) {
                    cell.setCellValue(new XSSFRichTextString(content[colNum - 1]));
                } else {
                    cell.setCellValue(new HSSFRichTextString(content[colNum - 1]));
                }
                cell.setCellStyle(styles.get("content"));
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn((short) i);
        }

//        String rootPath = System.getProperty("catalina.home");
        String rootPath = getServletContext().getRealPath("/");

        File folder = new File(rootPath + File.separator + folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        String filePath=File.separator + folderPath + File.separator + filename;

        FileOutputStream out = new FileOutputStream(rootPath+filePath);
        wb.write(out);
        out.close();

        return filePath;
    }

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

}
