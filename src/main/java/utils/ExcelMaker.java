package utils;

import dto.ExcelDto;
import exception.UtilsException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * excel生成
 * 基础：http://www.cnblogs.com/Damon-Luo/p/5919656.html
 * 行高列宽：http://blog.csdn.net/feg545/article/details/11983429
 * 样式：http://blog.csdn.net/spp_1987/article/details/13769043
 * Created by Phi on 2017/5/20.
 */
public class ExcelMaker {
    public static String makeExcel(List<ExcelDto> excelList ,String path){
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();

        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");

        //默认字体
        HSSFFont font = wb.getFontAt((short) 0);
        font.setCharSet(HSSFFont.DEFAULT_CHARSET);
        font.setFontHeightInPoints((short) 12);//更改默认字体大小
        font.setFontName("宋体");

        //设置缺省行高
        sheet.setDefaultRowHeightInPoints(18);

        //设置缺省列宽
        sheet.setDefaultColumnWidth(11);

        //设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
        sheet.setColumnWidth(1, 256 * 22);
        sheet.setColumnWidth(2, 256 * 30);
        sheet.setColumnWidth(3, 256 * 22);
        sheet.setColumnWidth(4, 256 * 30);
        sheet.setColumnWidth(5, 256 * 10);
        sheet.setColumnWidth(6, 256 * 32);

        //样式：http://blog.csdn.net/spp_1987/article/details/13769043
        //居中 边框
        HSSFCellStyle cellStyle1 = wb.createCellStyle();

        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

        cellStyle1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下粗
        cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左粗
        cellStyle1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右粗



        //水平、垂直、上粗、左粗、黄
        HSSFCellStyle cellStyle11 = wb.createCellStyle();
        cellStyle11.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle11.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle11.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle11.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左粗
        cellStyle11.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle11.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle11.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle11.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //垂直、上粗
        HSSFCellStyle cellStyle12 = wb.createCellStyle();
        cellStyle12.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle12.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle12.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle12.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle12.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //垂直、水平、上粗、黄
        HSSFCellStyle cellStyle13 = wb.createCellStyle();
        cellStyle13.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle13.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle13.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle13.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle13.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle13.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle13.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle13.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //垂直、上粗、右粗
        HSSFCellStyle cellStyle16 = wb.createCellStyle();
        cellStyle16.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle16.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle16.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle16.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle16.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右粗

        //垂直、水平、左粗、黄
        HSSFCellStyle cellStyle21 = wb.createCellStyle();
        cellStyle21.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle21.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle21.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle21.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左粗
        cellStyle21.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle21.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle21.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle21.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //水平居中 垂直居中 细边框
        HSSFCellStyle cellStyle22 = wb.createCellStyle();
        cellStyle22.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle22.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle22.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle22.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle22.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //水平居中 垂直居中 细边框 黄
        HSSFCellStyle cellStyle23 = wb.createCellStyle();
        cellStyle23.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle23.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle23.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle23.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle23.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle23.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle23.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle23.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //水平居中 垂直居中 细边框 又粗
        HSSFCellStyle cellStyle26 = wb.createCellStyle();
        cellStyle26.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle26.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle26.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle26.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle26.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右粗

        //垂直、水平、左粗、上粗、黄
        HSSFCellStyle cellStyle61 = wb.createCellStyle();
        cellStyle61.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle61.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle61.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle61.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左粗
        cellStyle61.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle61.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle61.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle61.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //垂直、水平、上粗、黄
        HSSFCellStyle cellStyle62 = wb.createCellStyle();
        cellStyle62.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle62.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle62.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle62.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle62.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle62.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle62.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle62.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //垂直、水平、右粗、上粗、黄
        HSSFCellStyle cellStyle66 = wb.createCellStyle();
        cellStyle66.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyle66.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle66.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle66.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle66.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上粗
        cellStyle66.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右粗
        cellStyle66.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置背景色
        cellStyle66.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //水平居中 垂直居中 细边框
        HSSFCellStyle cellStyleM4 = wb.createCellStyle();
        cellStyleM4.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        cellStyleM4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyleM4.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyleM4.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyleM4.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyleM4.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //上粗
        HSSFCellStyle cellStyleEnd = wb.createCellStyle();
        cellStyleEnd.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框

        int allRowNum = 0;
        for (int i = 0; i < excelList.size(); i++) {
            ExcelDto excelDto = excelList.get(i);
            int rowNum = 10;
            int rowBeforeNum = 7;
            int rowMiddleNum = 2;
            HSSFRow row1=sheet.createRow(allRowNum + 1);
            HSSFRow row2=sheet.createRow(allRowNum + 2);
            HSSFRow row3=sheet.createRow(allRowNum + 3);
            HSSFRow row4=sheet.createRow(allRowNum + 4);
            HSSFRow row5=sheet.createRow(allRowNum + 5);
            HSSFRow row6=sheet.createRow(allRowNum + 6);

            Cell c11 = row1.createCell(1);
            c11.setCellValue("功能号");
            c11.setCellStyle(cellStyle11);

            Cell c12 = row1.createCell(2);
            c12.setCellValue(excelDto.getFunctionCode());
            c12.setCellStyle(cellStyle12);

            Cell c13 = row1.createCell(3);
            c13.setCellValue("版本号");
            c13.setCellStyle(cellStyle13);

            Cell c14 = row1.createCell(4);
            c14.setCellValue(excelDto.getVersion());
            c14.setCellStyle(cellStyle12);

            Cell c15 = row1.createCell(5);
            c15.setCellValue("更新日期");
            c15.setCellStyle(cellStyle13);

            Cell c16 = row1.createCell(6);
            c16.setCellValue(excelDto.getUpdateDate());
            c16.setCellStyle(cellStyle16);

            Cell c21 = row2.createCell(1);
            c21.setCellValue("功能名");
            c21.setCellStyle(cellStyle21);

            Cell c22 = row2.createCell(2);
            c22.setCellValue(excelDto.getFunctionName());
            c22.setCellStyle(cellStyle22);

            Cell c23 = row2.createCell(3);
            c23.setCellValue("功能中文名");
            c23.setCellStyle(cellStyle23);

            Cell c24 = row2.createCell(4);
            c24.setCellValue(excelDto.getCnName());
            c24.setCellStyle(cellStyle26);

            row2.createCell(5).setCellStyle(cellStyle26);

            row2.createCell(6).setCellStyle(cellStyle26);

            Cell c31 = row3.createCell(1);
            c31.setCellValue("接口方法");
            c31.setCellStyle(cellStyle21);

            Cell c32 = row3.createCell(2);
            c32.setCellValue(excelDto.getInterfaceFunction());
            c32.setCellStyle(cellStyle22);

            row3.createCell(3).setCellStyle(cellStyle22);

            row3.createCell(4).setCellStyle(cellStyle22);

            row3.createCell(5).setCellStyle(cellStyle22);

            row3.createCell(6).setCellStyle(cellStyle26);

            Cell c41 = row4.createCell(1);
            c41.setCellValue("接口jar包");
            c41.setCellStyle(cellStyle21);

            Cell c42 = row4.createCell(2);
            c42.setCellValue(excelDto.getInterFaceJar());
            c42.setCellStyle(cellStyle22);

            Cell c43 = row4.createCell(3);
            c43.setCellValue("参数jar包");
            c43.setCellStyle(cellStyle23);

            Cell c44 = row4.createCell(4);
            c44.setCellValue(excelDto.getParamJar());
            c44.setCellStyle(cellStyle22);

            row4.createCell(5).setCellStyle(cellStyle22);

            row4.createCell(6).setCellStyle(cellStyle26);

            Cell c51 = row5.createCell(1);
            c51.setCellValue("业务描述");
            c51.setCellStyle(cellStyle21);

            Cell c52 = row5.createCell(2);
            c52.setCellValue(excelDto.getBusinessDescribe());
            c52.setCellStyle(cellStyle22);

            row5.createCell(3).setCellStyle(cellStyle22);

            row5.createCell(4).setCellStyle(cellStyle22);

            row5.createCell(5).setCellStyle(cellStyle22);

            row5.createCell(6).setCellStyle(cellStyle26);

            Cell c61 = row6.createCell(1);
            c61.setCellValue("输入参数");
            c61.setCellStyle(cellStyle61);

            Cell c62 = row6.createCell(2);
            c62.setCellValue("参数名");
            c62.setCellStyle(cellStyle62);

            Cell c63 = row6.createCell(3);
            c63.setCellValue("类型");
            c63.setCellStyle(cellStyle62);

            Cell c64 = row6.createCell(4);
            c64.setCellValue("关系属性");
            c64.setCellStyle(cellStyle62);

            Cell c65 = row6.createCell(5);
            c65.setCellValue("说明");
            c65.setCellStyle(cellStyle62);

            row6.createCell(6).setCellStyle(cellStyle66);

            sheet.addMergedRegion(new CellRangeAddress(allRowNum + 2,allRowNum + 2,4,6));
            sheet.addMergedRegion(new CellRangeAddress(allRowNum + 3,allRowNum + 3,2,6));
            sheet.addMergedRegion(new CellRangeAddress(allRowNum + 4,allRowNum + 4,4,6));
            sheet.addMergedRegion(new CellRangeAddress(allRowNum + 5,allRowNum + 5,2,6));
            sheet.addMergedRegion(new CellRangeAddress(allRowNum + 6,allRowNum + 6,5,6));
            for (int j = 0; j < excelDto.getReqList().size(); j++) {
                HSSFRow rowReq =sheet.createRow(allRowNum + rowBeforeNum + j);
                rowReq.createCell(1).setCellStyle(cellStyle21);

                Cell cm2 = rowReq.createCell(2);
                cm2.setCellValue(excelDto.getReqList().get(j).getName());
                cm2.setCellStyle(cellStyle22);

                Cell cm3 = rowReq.createCell(3);
                cm3.setCellValue(excelDto.getReqList().get(j).getType());
                cm3.setCellStyle(cellStyle22);

                Cell cm4 = rowReq.createCell(4);
                cm4.setCellValue(excelDto.getReqList().get(j).getAttribute());
                cm4.setCellStyle(cellStyleM4);

                Cell cm5 = rowReq.createCell(5);
                cm5.setCellValue(excelDto.getReqList().get(j).getRemark());
                cm5.setCellStyle(cellStyle22);

                rowReq.createCell(6).setCellStyle(cellStyle26);

                sheet.addMergedRegion(new CellRangeAddress(allRowNum + rowBeforeNum + j,allRowNum + rowBeforeNum + j,5,6));
            }
            HSSFRow rrow1 =sheet.createRow(allRowNum + rowBeforeNum + excelDto.getReqList().size());
            HSSFRow rrow2 =sheet.createRow(allRowNum + rowBeforeNum + excelDto.getReqList().size() + 1);

            Cell c71 = rrow1.createCell(1);
            c71.setCellValue("输出类");
            c71.setCellStyle(cellStyle11);
            rrow1.createCell(2).setCellStyle(cellStyle13);
            rrow1.createCell(3).setCellStyle(cellStyle13);
            rrow1.createCell(4).setCellStyle(cellStyle13);
            rrow1.createCell(5).setCellStyle(cellStyle13);
            rrow1.createCell(6).setCellStyle(cellStyle66);

            Cell c81 = rrow2.createCell(1);
            c81.setCellValue("输出参数");
            c81.setCellStyle(cellStyle61);

            Cell c82 = rrow2.createCell(2);
            c82.setCellValue("参数名");
            c82.setCellStyle(cellStyle62);

            Cell c83 = rrow2.createCell(3);
            c83.setCellValue("类型");
            c83.setCellStyle(cellStyle62);

            Cell c84 = rrow2.createCell(4);
            c84.setCellValue("xml标签");
            c84.setCellStyle(cellStyle62);

            Cell c85 = rrow2.createCell(5);
            c85.setCellValue("说明");
            c85.setCellStyle(cellStyle62);

            rrow2.createCell(6).setCellStyle(cellStyle66);

            sheet.addMergedRegion(new CellRangeAddress(allRowNum + rowBeforeNum + excelDto.getReqList().size(),allRowNum + rowBeforeNum + excelDto.getReqList().size(),2,6));
            sheet.addMergedRegion(new CellRangeAddress(allRowNum + rowBeforeNum + excelDto.getReqList().size() + 1,allRowNum + rowBeforeNum + excelDto.getReqList().size() + 1,5,6));
            for (int j = 0; j < excelDto.getResList().size(); j++) {
                int rowTmp = allRowNum + rowBeforeNum + excelDto.getReqList().size() + rowMiddleNum + j;
                HSSFRow rowRes =sheet.createRow(rowTmp);
                rowRes.createCell(1).setCellStyle(cellStyle21);

                Cell cn2 = rowRes.createCell(2);
                cn2.setCellValue(excelDto.getResList().get(j).getName());
                cn2.setCellStyle(cellStyle22);

                Cell cn3 = rowRes.createCell(3);
                cn3.setCellValue(excelDto.getResList().get(j).getType());
                cn3.setCellStyle(cellStyle22);

                Cell cn4 = rowRes.createCell(4);
                cn4.setCellValue(excelDto.getResList().get(j).getAttribute());
                cn4.setCellStyle(cellStyleM4);

                Cell cn5 = rowRes.createCell(5);
                cn5.setCellValue(excelDto.getResList().get(j).getRemark());
                cn5.setCellStyle(cellStyle22);

                rowRes.createCell(6).setCellStyle(cellStyle26);

                sheet.addMergedRegion(new CellRangeAddress(rowTmp,rowTmp,5,6));
            }

            rowNum = rowNum + excelDto.getReqList().size() +  excelDto.getResList().size();

            HSSFRow rowRes =sheet.createRow(allRowNum + rowBeforeNum + excelDto.getReqList().size() + rowMiddleNum + excelDto.getResList().size());

            rowRes.createCell(1).setCellStyle(cellStyleEnd);
            rowRes.createCell(2).setCellStyle(cellStyleEnd);
            rowRes.createCell(3).setCellStyle(cellStyleEnd);
            rowRes.createCell(4).setCellStyle(cellStyleEnd);
            rowRes.createCell(5).setCellStyle(cellStyleEnd);
            rowRes.createCell(6).setCellStyle(cellStyleEnd);

            allRowNum = allRowNum + rowNum;
        }
        //输出Excel文件
        FileOutputStream output= null;
        try {
            output = new FileOutputStream(path);
            wb.write(output);
            output.flush();
        } catch (FileNotFoundException e) {
            throw new UtilsException(e.toString());
        } catch (IOException e) {
            throw new UtilsException(e.toString());
        }

        return null;
    }


}
