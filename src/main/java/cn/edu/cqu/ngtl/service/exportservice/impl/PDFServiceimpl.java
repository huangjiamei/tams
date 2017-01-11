package cn.edu.cqu.ngtl.service.exportservice.impl;

import cn.edu.cqu.ngtl.service.exportservice.IPDFService;
import cn.edu.cqu.ngtl.viewobject.adminInfo.ClassFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.adminInfo.TaFundingViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTaApplyViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import cn.edu.cqu.ngtl.viewobject.classinfo.TeachCalendarViewObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.kuali.rice.krad.uif.freemarker.FreeMarkerInlineRenderBootstrap.getServletContext;
import static org.kuali.rice.krad.util.GlobalVariables.getUserSession;

/**
 * Created by tangjing on 2016/12/15.
 */
@Service
public class PDFServiceimpl implements IPDFService {

    @Override
    public void exportPDFByTemplate(String templatePath, String newPDFPath, String[] str) {
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form1 = stamper.getAcroFields();

            int i = 0;
            java.util.Iterator<String> it = form1.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                form1.setField(name, str[i++]);
            }
            stamper.setFormFlattening(true);
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String printNormalTable(String title, String[] headers, List<String[]> T, String fileName)
            throws DocumentException, IOException {
        Document document = new Document();
        String filePath = null;
        try {
            String rootPath = getServletContext().getRealPath("/");
            File folder = new File(rootPath+"exportfolder");
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdir();
            }
            //如果文件存在则在文件后追加
            filePath = "exportfolder"+File.separator+fileName+".pdf";
            OutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
            PdfWriter.getInstance(document, out);

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        document.open();

        //方法二：使用iTextAsian.jar中的字体
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        Font fontTitle = new Font(baseFont, 20, Font.NORMAL);
        if (title != null) {
            Paragraph titleCenter = new Paragraph(title, fontTitle);
            titleCenter.setAlignment(Element.ALIGN_CENTER);
            document.add(titleCenter);
            Paragraph blankCenter = new Paragraph("\n");
            blankCenter.setAlignment(Element.ALIGN_CENTER);
            document.add(blankCenter);
        }

        PdfPTable table = new PdfPTable(headers.length);
        table.setTotalWidth(570);
        table.setLockedWidth(true);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        // 加载列表的表头
        for (int i = 0; i < headers.length; i++) {
            cell.setPhrase(new Paragraph(headers[i], font));
            table.addCell(cell);
        }

        for (int i = 0; i < T.size(); i++) {
            for (int j = 0; j < headers.length; j++) {
                table.addCell(new Paragraph(T.get(i)[j], font));
            }
        }

        try {
            document.add(table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            document.close();
        }
        return filePath;
    }

    @Override
    public void exportPDFByTemplateMutilPage(String templatePath, String newPDFPath, List<String[]> strList) {
        int page = strList.size();
//		int page = 1;
        PdfReader reader;
        FileOutputStream out;
        PdfStamper stamper;
        ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];
        try {
            reader = new PdfReader(templatePath);
            out = new FileOutputStream(newPDFPath);
            for (int item = 0; item < page; item++) {
                baos[item] = new ByteArrayOutputStream();
                stamper = new PdfStamper(reader, baos[item]);
                AcroFields form = stamper.getAcroFields();
                int i = 0;
                java.util.Iterator<String> it = form.getFields().keySet().iterator();
                while (it.hasNext()) {
                    String name = it.next().toString();
                    form.setField(name, strList.get(item)[i++]);
                }
                stamper.setFormFlattening(true);
                stamper.close();
            }

            Document doc = new Document();
            PdfCopy pdfCopy = new PdfCopy(doc, out);
            doc.open();
//			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
//			copy.addPage(importPage);
            PdfImportedPage impPage = null;
            /**取出之前保存的每页内容*/
            for (int i = 0; i < page; i++) {
                impPage = pdfCopy.getImportedPage(new PdfReader(baos[i]
                        .toByteArray()), 1);
                pdfCopy.addPage(impPage);
            }
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportClassInformation(String path, List<ClassTeacherViewObject> classInformation) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            //如果文件存在则在文件后追加
            OutputStream out = new FileOutputStream(path);
            PdfWriter.getInstance(document, out);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        document.open();

        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        Font fontTitle = new Font(baseFont, 20, Font.NORMAL);

        PdfPTable table = new PdfPTable(1);

        for (int i = 0; i < classInformation.size(); i++) {
            PdfPTable nestDoorTable = this.nestClassInfoTable(classInformation.get(i));
            PdfPCell cell = new PdfPCell(nestDoorTable);
            cell.setFixedHeight(800);
            cell.disableBorderSide(-1);
//			cell.setRotation(90);
            table.addCell(cell);
        }

        document.add(table);
        document.close();
    }

    private PdfPTable nestClassInfoTable(ClassTeacherViewObject classInformation) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        Font fontTitle = new Font(baseFont, 45, Font.BOLD);
        Font fontContent = new Font(baseFont, 27, Font.NORMAL);
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell();
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        课程名称  "+classInformation.getCourseName(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        课程代码  "+classInformation.getCourseCode(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        教学班  "+classInformation.getClassNumber(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        耗费工时  "+classInformation.getWorkTime(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        学院  "+classInformation.getDepartmentName(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("        状态  "+classInformation.getStatus(), fontContent));
        cell.disableBorderSide(-1);
        cell.setRotation(270);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(" ", font));
        cell.disableBorderSide(-1);
        table.addCell(cell);

        return table;
    }


    /**create by luojizhou on 2016/12/30
     *
     *封装的方法，用于获取导出的课程经费PDF文件的文件路径
     * @param classFundingViewObjectList 传入的参数
     * @return filePath                   返回的PDF文件路径
     */
    @Override
    public String prepareClassFundingsToPDF(List<ClassFundingViewObject> classFundingViewObjectList){
        SimpleDateFormat curTime = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "课程经费列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + curTime.format(new Date());
        String filePath="";
        try{
            String[] header = {"学院","课程", "课程代码", "教学班号", "教师","申报经费","批准经费","博士津贴","奖励津贴","交通补贴","总经费"};
            List<String[]> Content = new ArrayList(classFundingViewObjectList.size());
            for(ClassFundingViewObject classFundingVOList : classFundingViewObjectList) {
                String Department = classFundingVOList.getDepartment() == null ? "" : classFundingVOList.getDepartment();
                String CourseName = classFundingVOList.getCourseName() == null ? "" : classFundingVOList.getCourseName();
                String CourseCode = classFundingVOList.getCourseCode() == null ? "" : classFundingVOList.getCourseCode();
                String ClassNumber = classFundingVOList.getClassNumber() == null ? "" : classFundingVOList.getClassNumber();
                String InstructorName = classFundingVOList.getInstructorName() == null ? "" : classFundingVOList.getInstructorName();
                String ApplyFunding = classFundingVOList.getApplyFunding() == null ? "" : classFundingVOList.getApplyFunding();
                String AssignedFunding = classFundingVOList.getAssignedFunding() == null ? "" : classFundingVOList.getAssignedFunding();

                String PhdFunding = classFundingVOList.getPhdFunding() == null ? "" : classFundingVOList.getPhdFunding();
                String Bonus = classFundingVOList.getBonus() == null ? "" : classFundingVOList.getBonus();
                String TravelSubsidy = classFundingVOList.getTravelSubsidy() == null ? "" : classFundingVOList.getTravelSubsidy();
                String Total = classFundingVOList.getTotal() == null ? "" : classFundingVOList.getTotal();

                String[] content = {
                        Department, CourseName, CourseCode, ClassNumber,InstructorName,ApplyFunding,AssignedFunding,PhdFunding,Bonus,TravelSubsidy,Total
                };
                Content.add(content);
            }
            filePath = this.printNormalTable("课程信息列表", header, Content, fileName);

        }catch(DocumentException | IOException e){
            String errorMsg="exception";
            return errorMsg;
        }
        return filePath;
    }

    /**create by luojizhou on 2016/12/30
     *
     *封装的方法，用于获取导出的助教经费PDF文件的文件路径
     * @param taFundingViewObjectList     传入的参数
     * @return filePath                   返回的PDF文件路径
     */
    public  String prepareTaFundingToPDF(List<TaFundingViewObject> taFundingViewObjectList){
        SimpleDateFormat curTime = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "助教经费列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + curTime.format(new Date());
        String filePath="";
        try{
            String[] header = {"学院","助教", "学号", "助教类别", "课程","课程代码","教学班号","分配经费","博士津贴","交通补贴","奖励经费","总经费"};
            List<String[]> Content = new ArrayList(taFundingViewObjectList.size());
            for(TaFundingViewObject taFundingVOList : taFundingViewObjectList) {
                String DepartmentName = taFundingVOList.getDepartmentName() == null ? "" : taFundingVOList.getDepartmentName();
                String TaName = taFundingVOList.getTaName() == null ? "" : taFundingVOList.getTaName();
                String StuId = taFundingVOList.getStuId() == null ? "" : taFundingVOList.getStuId();
                String TaType = taFundingVOList.getTaType() == null ? "" : taFundingVOList.getTaType();
                String CourseName = taFundingVOList.getCourseName() == null ? "" : taFundingVOList.getCourseName();
                String CourseCode = taFundingVOList.getCourseCode() == null ? "" : taFundingVOList.getCourseCode();
                String ClassNbr = taFundingVOList.getClassNbr() == null ? "" : taFundingVOList.getClassNbr();
                String AssignedFunding = taFundingVOList.getAssignedFunding() == null ? "" : taFundingVOList.getAssignedFunding();
                String PhdFunding = taFundingVOList.getPhdFunding() == null ? "" : taFundingVOList.getPhdFunding();
                String TravelSubsidy = taFundingVOList.getTravelSubsidy() == null ? "" : taFundingVOList.getTravelSubsidy();
                String Bonus = taFundingVOList.getBonus() == null ? "" : taFundingVOList.getBonus();
                String Total = taFundingVOList.getTotal() == null ? "" : taFundingVOList.getTotal();

                String[] content = {
                        DepartmentName, TaName,StuId,TaType,CourseName, CourseCode, ClassNbr,AssignedFunding,PhdFunding,TravelSubsidy,Bonus,Total
                };
                Content.add(content);
            }
            filePath = this.printNormalTable("助教经费列表", header, Content, fileName);

        }catch(DocumentException | IOException e){
            String errorMsg="exception";
            return errorMsg;
        }
        return filePath;
    }
    /**
     *获取教学日历PDF格式导出的路径
     * @param  TeachCalendarManager
     * @return filePath
     */
    @Override
    public String prepareTeachCalendarPDF(List<TeachCalendarViewObject>  TeachCalendarManager) {
        SimpleDateFormat curTime = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "教学日历列表" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + curTime.format(new Date());
        String filePath = "";
        try {
            String[] header = { "教学主题", "助教任务描述", "周次", "总耗时"};
            List<String[]> Content = new ArrayList(TeachCalendarManager.size());
            for (TeachCalendarViewObject TeachCalendarObj : TeachCalendarManager) {

                String theme = TeachCalendarObj.getTheme() == null ? "" : TeachCalendarObj.getTheme();
                String description = TeachCalendarObj.getDescription() == null ? "" : TeachCalendarObj.getDescription();
                String  weekly=TeachCalendarObj.getWeek()== null ? "" :TeachCalendarObj.getWeek() ;
                String elapsedTime = TeachCalendarObj.getElapsedTime() == null ? "" : TeachCalendarObj.getElapsedTime();
                String[] content = {
                         theme, description,weekly,  elapsedTime
                };
                Content.add(content);
            }
            filePath = this.printNormalTable("教学日历列表", header, Content, fileName);

        } catch (DocumentException | IOException e) {
            String errorMsg = "exception";
            return errorMsg;
        }
        return filePath;
    }
        @Override
        public String printSubmitRequestTable(String title,String course,String teacher,String code,String courseCode,String courseType,String classCode,String period,String credits,String ta,String studentNumber,String totalHours,String budgetTotal,String fileName,String[] calendarHeaders, List<String[]> T)
            throws DocumentException, IOException {
            Document document = new Document();
            String filePath = null;
            try {
                String rootPath = getServletContext().getRealPath("/");
                File folder = new File(rootPath+"exportfolder");
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdir();
                }
                //如果文件存在则在文件后追加
                filePath = "exportfolder"+File.separator+fileName+".pdf";
                OutputStream out = new FileOutputStream(rootPath+File.separator+filePath);
                PdfWriter.getInstance(document, out);

            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (DocumentException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            document.open();

            //方法二：使用iTextAsian.jar中的字体
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            Font font1 = new Font(baseFont,14,Font.NORMAL);
            Font font2 = new Font(baseFont,17,Font.NORMAL);
            Font fontTitle = new Font(baseFont, 20, Font.NORMAL);
            if (title != null) {
                Paragraph titleCenter = new Paragraph(title, fontTitle);
                titleCenter.setAlignment(Element.ALIGN_CENTER);
                document.add(titleCenter);
                Paragraph blankCenter = new Paragraph("\n");
                blankCenter.setAlignment(Element.ALIGN_CENTER);
                document.add(blankCenter);
            }
            PdfPTable table = new PdfPTable(calendarHeaders.length);
            table.setTotalWidth(520);
            table.setLockedWidth(true);

            try {
            document.add(new Paragraph("课程信息",font2));

            document.add(new Phrase("课程：",font1));
            document.add(new Phrase(course,font));
                document.add(new Phrase("\n"));
            document.add(new Phrase("教师：",font1));
            document.add(new Phrase(teacher,font));
            document.add(new Phrase("   职工号：",font1));
            document.add(new Phrase(code,font));
                document.add(new Phrase("\n"));
            document.add(new Phrase("课程代码：",font1));
            document.add(new Phrase(courseCode,font));
            document.add(new Phrase("   课程类别：",font1));
            document.add(new Phrase(courseType,font));
                document.add(new Phrase("\n"));
            document.add(new Phrase("教学班号：",font1));
            document.add(new Phrase(classCode,font));
            document.add(new Phrase("   学时：",font1));
            document.add(new Phrase(period,font));
                document.add(new Phrase("\n"));
            document.add(new Phrase("学分：",font1));
            document.add(new Phrase(credits,font));
            document.add(new Phrase("   学生人数：",font1));
            document.add(new Phrase(studentNumber,font));
                document.add(new Phrase("\n"));
            document.add(new Phrase("拟配备助教数：",font1));
            document.add(new Phrase(ta,font));
                document.add(new Paragraph("\n"));
            document.add(new Phrase("日历列表",font2));
                document.add(new Phrase("\n"));
                if(T.size()!=0) {
                    for (int i = 0; i < calendarHeaders.length; i++) {
                        table.addCell(new Phrase(calendarHeaders[i], font));
                    }
                    for (int i = 0; i < T.size(); i++) {
                        for (int j = 0; j < calendarHeaders.length; j++) {
                            table.addCell(new Phrase(T.get(i)[j], font));
                        }
                    }
                }
            document.add(table);
            document.add(new Phrase("耗时总计（小时）：",font1));
            document.add(new Phrase(totalHours,font));
            document.add(new Phrase("          预算总计：",font1));
            document.add(new Phrase(budgetTotal,font));
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                document.close();
            }
            return filePath;
        }


    /**
     *获取提交申请PDF格式导出的路径
     * @param  applyViewObject
     * @param  allCalendarList
     * @param  totalElapsedTime
     * @param  totalBudget
     * @return filePath
     */

    public String prepareSubmitRequestTable(ClassTaApplyViewObject applyViewObject, List<TeachCalendarViewObject> allCalendarList, String totalElapsedTime, String totalBudget) {
        SimpleDateFormat curTime = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "提交申请详细" + "-" + getUserSession().getLoggedInUserPrincipalId() + "-" + curTime.format(new Date());
        String filePath = "";
        try {
            String[] Calendarheader = {"教学主题", "周次", "预计耗时(小时)", "预算"};
                String course = applyViewObject.getCourseName() == null ? "" : applyViewObject.getCourseName();
                String teacher = applyViewObject.getTeacherName() == null ? "" : applyViewObject.getTeacherName();
                String code = applyViewObject.getTeacherType() == null ? "" : applyViewObject.getTeacherType();
                String courseCode = applyViewObject.getCourseNumber() == null ? "" : applyViewObject.getCourseNumber();
                String courseType = applyViewObject.getCourseType() == null ? "" : applyViewObject.getCourseType();
                String classCode = applyViewObject.getClassNumber() == null ? "" : applyViewObject.getClassNumber();
                String period = applyViewObject.getCourseHour() == null ? "" : applyViewObject.getCourseHour();
                String credits = applyViewObject.getCredit() == null ? "" : applyViewObject.getCredit();
                String ta = applyViewObject.getAssistantNumber() == null ? "" : applyViewObject.getAssistantNumber();
                String totalHours = totalElapsedTime == null ? "" : totalElapsedTime;
                String budgetTotal = totalBudget == null ? "" : totalBudget;
                String studentNumber=applyViewObject.getStudentNumber()==null ? "":applyViewObject.getStudentNumber();
            List<String[]> Content;
            if(allCalendarList!=null) {
                Content = new ArrayList(allCalendarList.size());
                for (TeachCalendarViewObject calendarObj : allCalendarList) {
                    String theme = calendarObj.getTheme() == null ? "" : calendarObj.getTheme();
                    String budget = calendarObj.getBudget() == null ? "" : calendarObj.getBudget();
                    String weekly = calendarObj.getWeek() == null ? "" : calendarObj.getWeek();
                    String elapsedTime = calendarObj.getElapsedTime() == null ? "" : calendarObj.getElapsedTime();
                    String[] content = {
                            theme, weekly, elapsedTime, budget
                    };
                    Content.add(content);
                }
            }
            else {
                Content = new ArrayList(0);
            }
            filePath = this .printSubmitRequestTable("提交申请详细", course, teacher, code, courseCode, courseType, classCode, period, credits, ta,studentNumber, totalHours, budgetTotal, fileName, Calendarheader, Content);
        } catch (DocumentException | IOException e) {
            String errorMsg = "exception";
            return errorMsg;
        }
        return filePath;
    }
    }


