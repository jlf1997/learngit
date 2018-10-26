package com.cimr.master.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtil
{
  private static String NO_DEFINE = "no_define";
  private static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";
  private static int DEFAULT_COLOUMN_WIDTH = 17;
  







  public static void exportExcel(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out)
  {
    if (datePattern == null) { datePattern = DEFAULT_DATE_PATTERN;
    }
    HSSFWorkbook workbook = new HSSFWorkbook();
    workbook.createInformationProperties();
    workbook.getDocumentSummaryInformation().setCompany("*****公司");
    SummaryInformation si = workbook.getSummaryInformation();
    si.setAuthor("liqi");
    si.setApplicationName("导出程序");
    si.setLastAuthor("最后保存者信息");
    si.setComments("文件作者信息");
    si.setTitle("文件标题信息");
    si.setSubject("文件主题信息");
    si.setCreateDateTime(new Date());
    
    HSSFCellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setAlignment((short)2);
    HSSFFont titleFont = workbook.createFont();
    titleFont.setFontHeightInPoints((short)20);
    titleFont.setBoldweight((short)700);
    titleStyle.setFont(titleFont);
    
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillPattern((short)1);
    headerStyle.setBorderBottom((short)1);
    headerStyle.setBorderLeft((short)1);
    headerStyle.setBorderRight((short)1);
    headerStyle.setBorderTop((short)1);
    headerStyle.setAlignment((short)2);
    HSSFFont headerFont = workbook.createFont();
    headerFont.setFontHeightInPoints((short)12);
    headerFont.setBoldweight((short)700);
    headerStyle.setFont(headerFont);
    
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setFillPattern((short)1);
    cellStyle.setBorderBottom((short)1);
    cellStyle.setBorderLeft((short)1);
    cellStyle.setBorderRight((short)1);
    cellStyle.setBorderTop((short)1);
    cellStyle.setAlignment((short)2);
    cellStyle.setVerticalAlignment((short)1);
    HSSFFont cellFont = workbook.createFont();
    cellFont.setBoldweight((short)400);
    cellStyle.setFont(cellFont);
    
    HSSFSheet sheet = workbook.createSheet();
    
    org.apache.poi.hssf.usermodel.HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    
    org.apache.poi.hssf.usermodel.HSSFComment comment = patriarch.createComment(new org.apache.poi.hssf.usermodel.HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5));
    

    comment.setString(new org.apache.poi.hssf.usermodel.HSSFRichTextString("可以在POI中添加注释！"));
    
    comment.setAuthor("liqi");
    
    int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;
    int[] arrColWidth = new int[headMap.size()];
    
    String[] properties = new String[headMap.size()];
    String[] headers = new String[headMap.size()];
    int ii = 0;
    for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
      String fieldName = (String)iter.next();
      
      properties[ii] = fieldName;
      headers[ii] = fieldName;
      
      int bytes = fieldName.getBytes().length;
      arrColWidth[ii] = (bytes < minBytes ? minBytes : bytes);
      sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
      ii++;
    }
    
    int rowIndex = 0;
    for (Object obj : jsonArray) {
      if ((rowIndex == 65535) || (rowIndex == 0)) {
        if (rowIndex != 0) { sheet = workbook.createSheet();
        }
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue(title);
        titleRow.getCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, headMap.size() - 1));
        
        HSSFRow headerRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++)
        {
          headerRow.createCell(i).setCellValue(headers[i]);
          headerRow.getCell(i).setCellStyle(headerStyle);
        }
        
        rowIndex = 2;
      }
      JSONObject jo = JSONObject.fromObject(obj);
      HSSFRow dataRow = sheet.createRow(rowIndex);
      for (int i = 0; i < properties.length; i++)
      {
        HSSFCell newCell = dataRow.createCell(i);
        
        Object o = jo.get(properties[i]);
        String cellValue = "";
        if (o == null) { cellValue = "";
        } else if ((o instanceof Date)) cellValue = new SimpleDateFormat(datePattern).format(o); else {
          cellValue = o.toString();
        }
        newCell.setCellValue(cellValue);
        newCell.setCellStyle(cellStyle);
      }
      rowIndex++;
    }
    


    try
    {
      workbook.write(out);
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  







  public static void exportExcelX(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out)
  {
    if (datePattern == null) { datePattern = DEFAULT_DATE_PATTERN;
    }
    SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
    workbook.setCompressTempFiles(true);
    
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setAlignment((short)2);
    Font titleFont = workbook.createFont();
    titleFont.setFontHeightInPoints((short)20);
    titleFont.setBoldweight((short)700);
    titleStyle.setFont(titleFont);
    
    CellStyle headerStyle = workbook.createCellStyle();
    
    headerStyle.setBorderBottom((short)1);
    headerStyle.setBorderLeft((short)1);
    headerStyle.setBorderRight((short)1);
    headerStyle.setBorderTop((short)1);
    headerStyle.setAlignment((short)2);
    Font headerFont = workbook.createFont();
    headerFont.setFontHeightInPoints((short)12);
    headerFont.setBoldweight((short)700);
    headerStyle.setFont(headerFont);
    
    CellStyle cellStyle = workbook.createCellStyle();
    
    cellStyle.setBorderBottom((short)1);
    cellStyle.setBorderLeft((short)1);
    cellStyle.setBorderRight((short)1);
    cellStyle.setBorderTop((short)1);
    cellStyle.setAlignment((short)2);
    cellStyle.setVerticalAlignment((short)1);
    Font cellFont = workbook.createFont();
    cellFont.setBoldweight((short)400);
    cellStyle.setFont(cellFont);
    
    SXSSFSheet sheet = workbook.createSheet();
    
    int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;
    int[] arrColWidth = new int[headMap.size()];
    
    String[] properties = new String[headMap.size()];
    String[] headers = new String[headMap.size()];
    int ii = 0;
    Iterator<String> iter = headMap.keySet().iterator();
    while (iter.hasNext()) {
      String fieldName = (String)iter.next();
      
      properties[ii] = fieldName;
      headers[ii] = ((String)headMap.get(fieldName));
      
      int bytes = fieldName.getBytes().length;
      arrColWidth[ii] = (bytes < minBytes ? minBytes : bytes);
      sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
      ii++;
    }
    
    int rowIndex = 0;
    for (Object obj : jsonArray) {
      if ((rowIndex == 65535) || (rowIndex == 0)) {
        if (rowIndex != 0) { sheet = workbook.createSheet();
        }
        SXSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue(title);
        titleRow.getCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, headMap.size() - 1));
        
        SXSSFRow headerRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++)
        {
          headerRow.createCell(i).setCellValue(headers[i]);
          headerRow.getCell(i).setCellStyle(headerStyle);
        }
        
        rowIndex = 2;
      }
      JSONObject jo = JSONObject.fromObject(obj);
      SXSSFRow dataRow = sheet.createRow(rowIndex);
      for (int i = 0; i < properties.length; i++)
      {
        SXSSFCell newCell = dataRow.createCell(i);
        
        Object o = jo.get(properties[i]);
        String cellValue = "";
        if (o == null) { cellValue = "";
        } else if ((o instanceof Date)) { cellValue = new SimpleDateFormat(datePattern).format(o);
        } else if (((o instanceof Float)) || ((o instanceof Double)))
          cellValue = new java.math.BigDecimal(o.toString()).setScale(2, 4).toString(); else {
          cellValue = o.toString();
        }
        newCell.setCellValue(cellValue);
        newCell.setCellStyle(cellStyle);
      }
      rowIndex++;
    }
    


    try
    {
      workbook.write(out);
      workbook.close();
      workbook.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void downloadExcelFile(String title, Map<String, String> headMap, JSONArray ja, HttpServletResponse response) {
    try {
      java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
      exportExcelX(title, headMap, ja, null, 0, os);
      byte[] content = os.toByteArray();
      java.io.InputStream is = new java.io.ByteArrayInputStream(content);
      
      response.reset();
      
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(new StringBuilder().append(title).append(".xlsx").toString().getBytes(), "iso-8859-1"));
      response.setContentLength(content.length);
      javax.servlet.ServletOutputStream outputStream = response.getOutputStream();
      BufferedInputStream bis = new BufferedInputStream(is);
      java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(outputStream);
      byte[] buff = new byte[' '];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, bytesRead);
      }
      
      bis.close();
      bos.close();
      outputStream.flush();
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
//  public static void main(String[] args) throws IOException {
//    int count = 100000;
//    JSONArray ja = new JSONArray();
//    for (int i = 0; i < 100000; i++) {}
//    
//
//
//
//
//    Map<String, String> headMap = new java.util.LinkedHashMap();
//    headMap.put("bizOrderNo", "姓名");
//    headMap.put("buyerName", "年龄");
//    
//    String title = "测试";
//    OutputStream outXlsx = new java.io.FileOutputStream("../b.xlsx");
//    System.out.println("正在导出xlsx....");
//    Date d2 = new Date();
//    exportExcelX(title, headMap, ja, null, 0, outXlsx);
//    System.out.println("共" + count + "条数据,执行" + (new Date().getTime() - d2.getTime()) + "ms");
//    outXlsx.close();
//  }
}
