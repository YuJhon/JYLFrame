package com.jyl.util.poi.extractor;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Excel2007Extractor extends ExcelExtractor
{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<Object> getRows(InputStream fis)
    {
        List<Object> rows = new ArrayList<Object>();
        try
        {
            XSSFWorkbook xwb = null;
            try
            {
                xwb = new XSSFWorkbook(fis);
            }
            catch (RuntimeException e)
            {
                e.printStackTrace();
                logger.error("文件异常，{}", e.getMessage());
                throw new Exception();
            }
            XSSFSheet sheet = xwb.getSheetAt(0);

            logger.info("开始解析");

            int rowindex = 1;
            while (true)
            {
                XSSFRow row = sheet.getRow(rowindex++ ); // 从第二行开始，取出每一行
                if (row == null)
                {
                    break;
                }
                logger.info("解析 excel数据第" + (rowindex - 1) + "行");
                XSSFCell xCellfirst1 = row.getCell(0);
                if (xCellfirst1 == null)
                {
                    break;
                }
                rows.add(row);

            }
            logger.info("解析excel结束：" + "合计" + (rowindex - 1) + "条");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return rows;
    }

    public String getXRowCellDateData(Object row2, int columnNum)
    {
        XSSFRow row = (XSSFRow)row2;
        XSSFCell cell = row.getCell((short)(columnNum - 1));
        if (cell == null)
        {
            return "";
        } // 判断取到的cell值是否为null

        String cellvalue = "";
        try
        {
            if (cell.getDateCellValue() != null && cell.getDateCellValue().getTime() < DEFAULT_DATE_FORMAT.parse("2000-01-01 00:00:00").getTime())
            {
                throw new RuntimeException("日期格式不对");
            }
        }
        catch (ParseException e1)
        {
            throw new RuntimeException(e1.getMessage());
        }
        try
        {
            cellvalue = DEFAULT_DATE_FORMAT.format(cell.getDateCellValue());
        }
        catch (Exception e)
        {

        }
        return cellvalue;
    }

    public String getXRowCellData(Object row2, int columnNum)
    {
        return getXRowCellData(row2, columnNum, false);
    }

    private static String getDateValue(XSSFCell cell)
    {
        if (cell != null && cell.getDateCellValue() != null)
        {
            return DEFAULT_DATE_FORMAT.format(cell.getDateCellValue());
        }
        else
        {
            return "";
        }
    }

    @Override
    public String getXRowCellData(Object row2, int columnNum, boolean escapeAddress)
    {
        XSSFRow row = (XSSFRow)row2;
        XSSFCell cell = row.getCell((short)(columnNum - 1));
        if (cell == null)
        {
            return "";
        } // 判断取到的cell值是否为null

        // 时间对象 特殊处理
        int dataFormat = cell.getCellStyle().getDataFormat();

        if (dataFormat == 14 || dataFormat == 178 || dataFormat == 180 || dataFormat == 181 || dataFormat == 182)
        {
            return getDateValue(cell);
        }

        String cellvalue = "";
        switch (cell.getCellType())
        {
            case XSSFCell.CELL_TYPE_STRING:
                cellvalue = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                cellvalue = cell.getNumericCellValue() + "";

                if (HSSFDateUtil.isCellDateFormatted(cell))
                {
                    double d = cell.getNumericCellValue();
                    Date date = HSSFDateUtil.getJavaDate(d);
                    cellvalue = DEFAULT_DATE_FORMAT.format(date);

                }
                else
                {
                    if (cellvalue.indexOf(".") != -1 && cellvalue.indexOf("E") != -1)
                    {
                        java.text.DecimalFormat df = new java.text.DecimalFormat();
                        try
                        {
                            cellvalue = df.parse(cellvalue).toString();
                        }
                        catch (ParseException e)
                        {
                            // return orignale string
                        }
                    }
                }

                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                cellvalue = cell.getCellFormula() + "";
                break;
            default:
                cellvalue = "";
                break;
        }
        cellvalue = strtovalid(cellvalue);

        return cellvalue;
    }

    @Override
    public int getRowLength(Object row)
    {
        XSSFRow r = (XSSFRow)row;
        if (r != null)
        {
            return r.getPhysicalNumberOfCells();
        }
        return 0;
    }
}
