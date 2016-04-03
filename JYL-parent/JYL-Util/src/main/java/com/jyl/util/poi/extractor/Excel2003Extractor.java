package com.jyl.util.poi.extractor;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 针对2003的版本
 * 
 * @author JiangYu
 */

@Component
public class Excel2003Extractor extends ExcelExtractor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public final static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取excel数据的行
	 */
	public List<Object> getRows(InputStream fis) {
		List<Object> rows = new ArrayList<Object>();
		try {
			HSSFWorkbook xwb = null;
			try {
				xwb = new HSSFWorkbook(fis);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error("文件异常，{}", e.getMessage());
				throw new RuntimeException("文件异常");
			}

			HSSFSheet sheet = xwb.getSheetAt(0);

			logger.info("开始解析");

			int rowindex = 1;
			while (true) {
				// 从第二行开始，取出每一行
				HSSFRow row = sheet.getRow(rowindex++);
				if (row == null) {
					break;
				}
				logger.info("解析 excel数据第" + (rowindex - 1) + "行");
				HSSFCell xCellfirst1 = row.getCell(0);
				if (xCellfirst1 == null) {
					break;
				}
				rows.add(row);

			}
			logger.info("解析excel结束：" + "合计" + (rowindex - 1) + "条");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件异常");
		}
		return rows;
	}

	private static String getDateValue(HSSFCell cell) {
		if (cell != null && cell.getDateCellValue() != null) {
			return DEFAULT_DATE_FORMAT.format(cell.getDateCellValue());
		} else {
			return "";
		}
	}

	@Override
	public String getXRowCellData(Object row2, int columnNum) {
		return getXRowCellData(row2, columnNum, false);
	}

	public String getXRowCellData(Object row2, int columnNum,boolean escapeAddress) {

		HSSFRow row = (HSSFRow) row2;
		HSSFCell cell = row.getCell((columnNum - 1));
		if (cell == null) {// 判断取到的cell值是否为null
			return "";
		} 
		String cellvalue = "";
		//时间对象 特殊处理
		int dataFormat = cell.getCellStyle().getDataFormat();
		if (dataFormat == 14 || dataFormat == 178 || dataFormat == 180
				|| dataFormat == 181 || dataFormat == 182) {
			return getDateValue(cell);
		}

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellvalue = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
			cellvalue = cell.getNumericCellValue() + "";

			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellvalue = DEFAULT_DATE_FORMAT.format(date);
			} else {
				if (cellvalue.indexOf(".") != -1
						&& cellvalue.indexOf("E") != -1) {
					java.text.DecimalFormat df = new java.text.DecimalFormat();
					try {
						cellvalue = df.parse(cellvalue).toString();
					} catch (ParseException e) {
						e.printStackTrace();
						// return orignale string
					}
				}
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellvalue = cell.getCellFormula() + "";
			break;
		default:
			cellvalue = "";
			break;
		}
		return strtovalid(cellvalue);
	}

	public String getXRowCellDateData(Object row2, int columnNum) {
		HSSFRow row = (HSSFRow) row2;
		HSSFCell cell = row.getCell((columnNum - 1));
		if (cell == null) {
			return "";
		} // 判断取到的cell值是否为null

		String cellvalue = "";
		try {
			if (cell.getDateCellValue() != null
					&& cell.getDateCellValue().getTime() < DEFAULT_DATE_FORMAT.parse("2000-01-01 00:00:00").getTime()) {
				throw new RuntimeException("日期格式不对");
			}
		} catch (ParseException e1) {
			throw new RuntimeException(e1.getMessage());
		}
		try {
			cellvalue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell
					.getDateCellValue());
		} catch (Exception e) {
		}

		cellvalue = strtovalid(cellvalue);

		return cellvalue;
	}

	@Override
	public int getRowLength(Object row) {
		HSSFRow r = (HSSFRow) row;
		if (r != null) {
			return r.getPhysicalNumberOfCells();
		}
		return 0;
	}

}
