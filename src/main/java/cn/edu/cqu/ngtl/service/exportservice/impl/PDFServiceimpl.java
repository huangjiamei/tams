package cn.edu.cqu.ngtl.service.exportservice.impl;

import cn.edu.cqu.ngtl.service.exportservice.IPDFService;
import cn.edu.cqu.ngtl.viewobject.classinfo.ClassTeacherViewObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import static org.kuali.rice.krad.uif.freemarker.FreeMarkerInlineRenderBootstrap.getServletContext;

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
        cell = new PdfPCell(new Paragraph("        课程编号  "+classInformation.getCourseCode(), fontContent));
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
}
