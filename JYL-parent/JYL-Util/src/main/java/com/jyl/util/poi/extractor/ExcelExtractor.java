package com.jyl.util.poi.extractor;


import java.io.InputStream;
import java.util.List;

/**
 * <p>功能描述:</br> Excel解析</p>
 * @className  ExcelExtractor
 * @author  jiangyu
 * @date  2016年4月3日 下午5:38:07
 * @version  v1.0
 */
public abstract class ExcelExtractor
{

    public String strtovalid(String str)
    {
        str = str.replaceAll("—", "-").replaceAll("，", ",");
        String cwb = "";
        for (int i = 0; i < str.length(); i++ )
        {
            int asc = (int)str.charAt(i);
            if (asc >= 32 && asc <= 127 || // 英文字符，标点符号，数字
                (str.charAt(i) + "").matches("[\u4e00-\u9fa5]+"))
            { // //判断字符是否为中文
                cwb += str.charAt(i);
            }
        }
        return cwb;
    }

    public abstract String getXRowCellData(Object row, int cwbindex);

    public abstract String getXRowCellData(Object row, int cwbindex, boolean escapeAddress);

    public abstract String getXRowCellDateData(Object row, int cwbindex);

    public abstract List<Object> getRows(InputStream f);

    public abstract int getRowLength(Object row);

}